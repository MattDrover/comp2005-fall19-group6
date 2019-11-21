import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;

import org.junit.jupiter.api.Test;

class SelectColorMenuTest {


	@Test
	void testSetColor1() {
		//SelectColorMenu menu = new SelectColorMenu();
		SelectColorMenu.setColor1(Color.blue);
		
		 //SelectColorMenu.getColor1();
		assertEquals(SelectColorMenu.getColor1(), Color.blue);
	}


}
