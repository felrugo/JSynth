package main;

import javax.sound.sampled.AudioFormat;

/**
 * Interface a hanforrásokhoz.
 * @author felrugo
 *
 */
public interface AudioOutput {

	/**
	 * Generál egy időrésnyi mintát.
	 * @param af Hangformátum a generáláshoz.
	 * @return A minta.
	 */
	public byte[] ReadAudio(AudioFormat af);
	
	
}
