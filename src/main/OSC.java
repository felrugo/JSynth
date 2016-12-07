package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.TreeMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

/**
 * Ez az osztály felel a hanghullámok generálásáért.
 * @author felrugo
 *
 */
public class OSC implements NoteListener, AudioOutput {

	private transient volatile ArrayList<Note> ans;
	private int mode, decr;
	private double amp;
	private double fin, dur, fout;
	
	
	
	public OSC()
	{
		ans = new ArrayList<Note>();
		mode = decr = 0;
		dur = -1.0;
		fout = 0.0;
	}
	
	synchronized public void SetMode(int sm)
	{
		if(sm >= 0 && sm <= 3)
			mode = sm;
	}
	
	synchronized public void SetDecr(int sd)
	{
		if(sd >= 0 && sd <= 3)
			decr = sd;
	}
	
	synchronized public void SetFOutTime(double sft)
	{
		fout = sft;
	}
	
	

	public double GetAmp() {
		return amp;
	}
	
	public int GetMode()
	{
		return mode;
	}
	
	public int GetDecr()
	{
		return decr;
	}
	
	public double GetFout()
	{
		return fout;
	}
	
	/**
	 * Négyszögjelet generál
	 * @param x Fázis (0.0 - 2.0)
	 */
	public double square(double x)
		{
		x = x % 2.0;
		if(x == 0.0 || x == 1.0)
			return 0.0;
		if(x < 1.0)
			return 1.0;
		if(x > 1.0)
			return -1.0;
		return 0.0;
		}
	
	/**
	 * Háromszög jelet generál
	 * @param x Fázis (0.0 - 2.0)
	 */
	public double triangle(double x)
	{
		if(x <= 0.5)
			return 2.0*x;
		if(x > 0.5 && x <= 1.5)
			return (-x - 1) * 2.0;
		if(x > 1.5)
			return (x - 2) * 2.0;
		return 0.0;
	}
	
	
	/**
	 * Fűrészfog jelet generál
	 * @param x Fázis (0.0 - 2.0)
	 */
	public double saw(double x)
	{
		if(x <= 1.0)
			return x;
		if(x > 1.0)
			return (x-2.0);
		return 0.0;
	}
	
	
	/**
	 * Lineáris csillapodás
	 * @param cet A csillapodási fázisban eltöltött idő
	 */
	public double LinearFadeOut(double cet)
	{
		return 1.0 - (cet / fout);
	}
	
	/**
	 * Négyzetes csillapodás
	 * @param cet A csillapodási fázisban eltöltött idő
	 */
	public double SquFadeOut(double cet)
	{
		return 1.0 - Math.pow((cet / fout), 2.0);
	}
	
	/**
	 * Gyökös csillapodás
	 * @param cet A csillapodási fázisban eltöltött idő
	 */
	public double RootFadeOut(double cet)
	{
		return 1.0 - Math.sqrt(cet / fout);
	}
	
	/**
	 * Kör-menti csillapodás
	 * @param cet A csillapodási fázisban eltöltött idő
	 */
	public double CircFadeOut(double cet)
	{
		double x = cet / fout;
		return 1 - Math.sqrt(2 * x - (x*x));
	}
	
	
	public void setAmp(double samp)
	{
		amp = samp;
	}
	
	
	/**
	 * Ez a függvény a mode alapján kiválasztja a megfelelő generátort.
	 * @param x A hanghullám jelenlegi fázisa (0.0 és 2.0 között)
	 * @return A fázishoz tartozó érték
	 */
	double ModeSel(double x)
	{
		switch(mode)
		{
		case 1:
			return square(x);
		case 2:
			return triangle(x);
		case 3:
			return saw(x);
		default:
			return Math.sin(Math.PI * x);
		}
	}
	
	
	/**
	 * Ez a függvény decr alapján kiválasztja a megfelelő csillapítást.
	 * @param x A csillapodási fázisban eltöltött idő (max fout).
	 * @return Az amplitudó pillanatnyi szorzóját (0.0-tól 1.0-ig).
	 */
	double DecrSel(double x)
	{
		switch(decr)
		{
		case 1:
			return SquFadeOut(x);
		case 2:
			return RootFadeOut(x);
		case 3:
			return CircFadeOut(x);
		default:
			return LinearFadeOut(x);
		}
	}
	
	
	/**
	 * Generál egy időrésnyi mintát.
	 * @param af A lejátszó eszköz hangformátuma.
	 * @return Egy időrésnyi minta.
	 */
	@Override
	public synchronized byte[] ReadAudio(AudioFormat af) {
		
		
		update();
		
		
		
		byte[] ret = new byte[441];
		for(Note n : ans)
		{
			for(int i = 0; i < 441; i++)
				{
					if(n.sc == 1)
						ret[i] += (byte) (amp * ModeSel(n.phase));
					if(n.sc == 2)
						ret[i] += (byte) (amp * DecrSel(n.elst + i * 0.01 / 441.0) * ModeSel(n.phase));
					n.phase += 2 * n.getFreq() / af.getSampleRate();
					n.phase = n.phase%(2.0);
					
				}
		n.elst += 0.01;
		
		}
		
		return ret;
	}
	
	/**
	 * Frissíti a lenyomott hangok állapotát.
	 */
	synchronized void update()
	{
//		if(sdl.available() >= 441)
//		{
			for(int i = 0; i < ans.size(); i++)
			{
				Note sn = ans.get(i);
				if(sn.sc == 0)
					if(sn.elst > fin)
					{
						sn.sc = 1;
						sn.elst -= fin;
					}
				if(sn.sc == 1)
					if(dur >= 0 && sn.elst > dur)
					{
						sn.sc = 2;
						sn.elst -= dur;
					}
				if(sn.sc == 2)
					if(sn.elst > fout )
					{
						ans.remove(sn);
						i--;
					}
					
			}
			
//			byte[] buff = ReadAudio(af);
//			
//			sdl.write(buff, 0, buff.length);
		//}
	}

	@Override
	public synchronized void onNotePress(Note n) {
		// TODO Auto-generated method stub
//		if(fis.containsKey(n))
//		{
//			fis.remove(n);
//			fis.put(n, new NoteState());
//		}
//		else
//		fis.put(n, new NoteState());
		
		for(Note sn : ans)
		{
			if(sn.sc == 1 && sn.equals(n))
				ans.remove(sn);
		}
		ans.add(n);
		
	}

	@Override
	public synchronized void onNoteRelease(Note n) {
		// TODO Auto-generated method stub
//		if(fis.containsKey(n))
//		{
//			fis.remove(n);
//			fadeouts.put(n, new NoteState())
//		}
		for(Note sn : ans)
		{
			if(sn.sc == 1 && sn.equals(n))
			{
				sn.sc = 2;
				sn.elst = 0.0;
			}
		}
		
	}



	

}
