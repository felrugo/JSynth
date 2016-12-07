import static org.junit.Assert.*;

import org.junit.Test;

import main.Note;

public class NoteTest {

	@Test
	public void ConvAtest() {
		Note n = new Note("A", 4);
		assertTrue(n.getFreq() == 440.0);
	}
	
	@Test
	public void EquTest() {
		Note n = new Note("C", 4);
		Note m = new Note("C", 4);
		assertTrue(n.equals(m) && n.getFreq() == m.getFreq());
	}
	
	@Test
	public void CompTest() {
		Note n = new Note("C", 4);
		Note m = new Note("C", 5);
		assertTrue(n.compareTo(m) == -1);
	}

}
