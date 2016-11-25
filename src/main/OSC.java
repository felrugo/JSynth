package main;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.IdentityHashMap;
import java.util.TreeMap;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class OSC implements NoteListener, AudioOutput {

	volatile ArrayList<Note> ans;
//	AudioFormat af;
//	SourceDataLine sdl;
	double amp;
	double fin, dur, fout;
	
	
	
	OSC()
	{
		ans = new ArrayList<Note>();
		
		dur = -1.0;
		fout = 1.0;
//		af = new AudioFormat(44100, 8, 1, true, true);
//		try {
//			sdl = AudioSystem.getSourceDataLine(af);
//			sdl.open(af, 2 * 441);
//			sdl.start();
//		} catch (LineUnavailableException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
	
	
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
	
	
	double LinearFadeOut(double cet)
	{
		return 1 - (cet / fout);
	}
	
	void setAmp(double samp)
	{
		amp = samp;
	}
	
	
	@Override
	public synchronized byte[] ReadAudio(AudioFormat af) {
		
		
		update();
		
		// TODO Auto-generated method stub
//		byte[] ret = new byte[441];
//		
//		Object[] narr = fis.keySet().toArray();
//		for(int nid = 0; nid < narr.length; nid++)
//		{
//		Note n = (Note) narr[nid];
//			
//			for(int i = 0; i < 441; i++)
//			{
//				NoteState ns = fis.remove(n);
//				ret[i] += (byte) (amp * square(ns.curphase));
//				ns.curphase += 2 * n.getFreq() / af.getSampleRate();
//				ns.curphase = ns.curphase%(2.0);
//				ns.elstime += 0.01;
//				fis.put(n, ns);
//			}
		
		byte[] ret = new byte[441];
		for(Note n : ans)
		{
			for(int i = 0; i < 441; i++)
				{
					if(n.sc == 1)
						ret[i] += (byte) (amp * square(n.phase));
					if(n.sc == 2)
						ret[i] += (byte) (amp * LinearFadeOut(n.elst + i * 0.01 / 441.0) * square(n.phase));
					n.phase += 2 * n.getFreq() / af.getSampleRate();
					n.phase = n.phase%(2.0);
					
				}
		n.elst += 0.01;
		
		}
		
		return ret;
	}
	
	
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
