package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class BeepTest implements NoteListener {

	volatile boolean pressed;
	AudioFormat af;
	SourceDataLine sdl;
	volatile double phase;
	
	BeepTest()
	{
		phase = 0.0;
		pressed = false;
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
	
	
	public void Update()
	{
		if(pressed && sdl.available() >= 441)
		{
		byte[] ret = new byte[441];
		
			for(int i = 0; i < 441; i++)
			{
			phase = phase % (2 * Math.PI);
			ret[i] = (byte) (50 * (Math.sin(phase)));
			phase += 2 * Math.PI * 440.0 / 44100;
			}
			sdl.write(ret, 0, ret.length);
		}	
			
	}
	
	@Override
	public void onNotePress(Note n) {
		// TODO Auto-generated method stub
		phase = 0.0;
		pressed = true;
	}

	@Override
	public void onNoteRelease(Note n) {
		// TODO Auto-generated method stub
		pressed = false;
	}
	
	
}
