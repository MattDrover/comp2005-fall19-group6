import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SettingsMenuTest {

	@Test
	void testMakeGUI() {
		SettingsMenu menu = new SettingsMenu();
		menu.makeGUI();
	}

	@Test
	void testGetShouldAdd() {
		//SettingsMenu menu = new SettingsMenu();
		assertEquals(SettingsMenu.getShouldAdd(), false);
	}

}
