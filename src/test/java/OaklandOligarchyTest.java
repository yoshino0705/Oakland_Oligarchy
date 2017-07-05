import org.junit.Test;
import static org.junit.Assert.*;

import java.util.ArrayList;

public class OaklandOligarchyTest {

	@Test
	public void testCheckWon() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		assertFalse(oo.checkWon());
		for (int i = 0; i < oo.allPlayers.size()-1; i++) {
			oo.allPlayers.get(i).lose();
		}
		assertTrue(oo.checkWon());
	}

	@Test
	public void testGetWinner() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		assertNull(oo.getWinner());
		for (int i = 0; i < oo.allPlayers.size()-1; i++) {
			oo.allPlayers.get(i).lose();
		}
		assertNotNull(oo.getWinner());
	}

	@Test
	public void testPlayerLose0() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		Player p = oo.allPlayers.get(0);
		ArrayList<PropertyTile> list = oo.getOwnedProperties(p);
		assertEquals(list.size(), 2);
		oo.playerLose(p);
		list = oo.getOwnedProperties(p);
		assertEquals(list.size(), 0);
	}

	@Test
	public void testPlayerLose1() {
		OaklandOligarchy oo = new OaklandOligarchy("test");
		Player p = oo.allPlayers.get(0);
		assertFalse(p.hasLost());
		oo.playerLose(p);
		assertTrue(p.hasLost());
	}

}