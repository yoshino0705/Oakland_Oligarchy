import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class TopMenuTest {

	@Test
	public void testTopMenuPlayerLabel() {
		Player p = new Player("pname", 100);
		TopMenu tm = new TopMenu(p);
		assertEquals(tm.currentTurnPlayerLabel.getText(), "<html>Turn:<br>" + p.getName() + "</html>");
	}

	@Test
	public void testTopMenuButtons() {
		Player p = new Player("pname", 100);
		TopMenu tm = new TopMenu(p);
		assertNotNull(tm.tradeButton);
		assertNotNull(tm.rollButton);
		assertNotNull(tm.newGame);
		assertNotNull(tm.endGame);
		assertNotNull(tm.helpButton);
	}
}