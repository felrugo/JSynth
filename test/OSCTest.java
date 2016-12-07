import static org.junit.Assert.*;

import org.junit.Test;

import main.OSC;

public class OSCTest {

	
	@Test
	public void sawtest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		assertTrue(to.saw(0.0) == 0.0 && to.saw(1.0) == 1.0);
	}
	
	@Test
	public void overflowtest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		boolean over = false;
		for(int i = 0; i < 20000; i++)
		{
			if(to.triangle(i / 10000.0) > 1.0)
				over = true;
		}
		assertFalse(over);
	}
	
	@Test
	public void Lineartest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		assertTrue(to.LinearFadeOut(0.0) == 1.0 && to.LinearFadeOut(1.0) == 0.0);
	}
	
	@Test
	public void SquareTest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		assertTrue(to.SquFadeOut(0.0) == 1.0 && to.SquFadeOut(1.0) == 0.0);
	}
	
	@Test
	public void Roottest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		assertTrue(to.RootFadeOut(0.0) == 1.0 && to.RootFadeOut(1.0) == 0.0);
	}
	
	@Test
	public void Circtest() {
		OSC to = new OSC();
		to.SetFOutTime(1.0);
		assertTrue(to.CircFadeOut(0.0) == 1.0 && to.CircFadeOut(1.0) == 0.0);
	}

}
