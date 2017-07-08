import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class TopMenuTest {

	@Test
	public void testTopMenuButtons() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		TopMenu tm = new TopMenu(oo);
		assertNotNull(tm.tradeButton);
		assertNotNull(tm.rollButton);
		assertNotNull(tm.endTurn);
		assertNotNull(tm.saveGame);
		assertNotNull(tm.helpButton);
	}

	@Test
	public void testToggleJBUttonEnabled() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		TopMenu tm = new TopMenu(oo);
		assertTrue(tm.rollButton.isEnabled());
		tm.toggleRollButton();
		assertFalse(tm.rollButton.isEnabled());
	}
}
