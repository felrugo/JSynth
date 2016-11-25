package main;

public interface MidiOutput {

	int getKeyboardSize();
	
	boolean isKeyPressed(int kid);
	
	double getKeyFreq(int kid);
	
}
