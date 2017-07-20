import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

	@Test
	public void testPlayerName() {
		Player p = new Player("pname", 3000, 0, false);
		assertEquals(p.getName(), "PNAME");
	}

	@Test
	public void testPlayerMoney() {
		Player p = new Player("pname", 9001, 0, false);
		assertEquals(p.getMoney(), 9001);
		p.setMoney(9002);
		assertEquals(p.getMoney(), 9002);
		p.setMoney(-10);
		assertEquals(p.getMoney(), 0);
		Player p2 = new Player("p2name", -20, 0, false);
		assertEquals(p2.getMoney(), 0);
	}

	@Test
	public void testMortgage() {
		OaklandOligarchy game = new OaklandOligarchy("test");
		Player p = game.allPlayers.get(0);
		int startMoney = p.getMoney();
		PropertyTile prop = (PropertyTile)game.tiles.getTile(1);
		p.mortgage(prop);
		assertEquals(p.getMoney(), startMoney + (prop.getValue() / 2));
		assertEquals(prop.isMortgaged(), true);
	}

	@Test
	public void testBuyBackMortgage() {
		OaklandOligarchy game = new OaklandOligarchy("test");
		Player p = game.allPlayers.get(0);
		int startMoney = p.getMoney();
		PropertyTile prop = (PropertyTile)game.tiles.getTile(1);
		p.buyBackMortgage(prop);
		assertEquals(p.getMoney(), startMoney - (prop.getValue() / 2));
		assertEquals(prop.isMortgaged(), false);
	}

}
