package main;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

public class Mixer {

	private ArrayList<AudioOutput> aos;
	private AudioFormat af;
	private SourceDataLine sdl;
	private ByteArrayOutputStream baos;
	private Main m;
	private boolean onRec;
	
	/**
	 * Konstruktor.
	 * @param m A főablak osztályát várja.
	 */
	public Mixer(Main m)
	{
		this.m = m;
		onRec = false;
		baos = new ByteArrayOutputStream();
		aos = new ArrayList<AudioOutput>();
		af = new AudioFormat(44100, 8, 1, true, true);
		try {
			sdl = AudioSystem.getSourceDataLine(af);
			sdl.open(af, 2 * 441);
			sdl.start();
		} catch (LineUnavailableException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	/**
	 * Beregisztrál egy forrást.
	 * @param e A regisztrálandó forrás.
	 */
	void RegisterAO(AudioOutput e)
	{
		aos.add(e);
	}
	
	
	/**
	 * Elindítja/megállítja a felvételt.
	 */
	public void TogleRecording()
	{
		if(onRec == false)
		{
			onRec = true;
		}
		else
		{
			onRec = false;
			m.GetVK().SetActive(false);
			JFileChooser fileChooser = new JFileChooser();
			if (fileChooser.showSaveDialog(m) == JFileChooser.APPROVE_OPTION) {
			  File file = fileChooser.getSelectedFile();
			  // save to file
			  ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
				AudioInputStream ais = new AudioInputStream(bais, af, baos.size());
				try {
					AudioSystem.write(ais, AudioFileFormat.Type.WAVE, file);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			m.GetVK().SetActive(true);
			baos.reset();	
			
		}
	}
	
	
	
	
	/**
	 * A főciklus, ez felel az ütemezésért.
	 */
	public void update()
	{
		if(sdl.available() >= 441)
		{
			byte[] buff = new byte[441];
			
			for(AudioOutput ao : aos)
			{
				byte[] outp = ao.ReadAudio(af);
				for(int i = 0; i < buff.length; i++)
				{
					buff[i] += outp[i];
				}
			}
			sdl.write(buff, 0, buff.length);
			
			if(onRec)
			{
				baos.write(buff, 0, buff.length);
			}
			
			
			
		}
	}
	
	
	
}
