import static org.junit.Assert.*;

import org.junit.Test;

import main.VirtualKeyboard;

public class VKTest {

	@Test
	public void ActiveTogletest() {
		VirtualKeyboard vk = new VirtualKeyboard();
		vk.SetActive(false);
		assertFalse(vk.isActive());
	}

}
