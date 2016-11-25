package main;

import javax.sound.sampled.AudioFormat;

public interface AudioOutput {

	byte[] ReadAudio(AudioFormat af);
	
	
}
