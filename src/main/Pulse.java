package main;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Pulse implements NoteListener {

	AudioFormat af;
	
	Pulse()
	{
		af = new AudioFormat(44100, 8, 1, true, false);
	}

	
	
	void impulse(double freq, double time)
	{
		new Impulse(freq, time, af);
	}
	
	@Override
	public void onNotePress(Note n) {
		// TODO Auto-generated method stub
		impulse(n.getFreq(), 1.0);
	}

	@Override
	public void onNoteRelease(Note n) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
	
	
}
