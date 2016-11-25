package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Impulse implements Runnable {

	SourceDataLine sdl;
	AudioFormat af;
	double freq;
	long k, end;
	Thread runner;
	
	Impulse(double freq, double time, AudioFormat format)
	{
	try {
		af = format;
		this.freq = freq;
		k = 0;
		end = (long) (time * af.getFrameRate());
		sdl = AudioSystem.getSourceDataLine(format);
		sdl.open(af);
		sdl.start();
	} catch (LineUnavailableException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}	
	
	runner = new Thread(this);
	runner.start();
	
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(k < end)
		{
			byte[] data = new byte[1];
			data[0] = (byte) (50 * (end - k) / (float)end * Math.sin(freq * Math.PI * 2.0 * k / af.getFrameRate()));
			sdl.write(data, 0, 1);
			k++;
		}
		
		
	}
	
}
