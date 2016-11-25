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

public class Mixer {

	ArrayList<AudioOutput> aos;
	AudioFormat af;
	SourceDataLine sdl;
	ByteArrayOutputStream baos;
	
	Mixer()
	{
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
	
	
	void RegisterAO(AudioOutput e)
	{
		aos.add(e);
	}
	
	
	void StartRecording(File rf)
	{
		if(baos == null)
		{
			baos = new ByteArrayOutputStream();
		}
	}
	
	
	void StopRecording()
	{
		if(baos != null)
		{
			ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
			AudioInputStream ais = new AudioInputStream(bais, af, baos.size());
			try {
				AudioSystem.write(ais, AudioFileFormat.Type.WAVE, new File("Test.wav"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	
	void update()
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
			
			if(baos != null)
			{
				baos.write(buff, 0, buff.length);
			}
			
			
			
		}
	}
	
	
	
}
