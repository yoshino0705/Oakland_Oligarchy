import org.junit.Test;
import static org.junit.Assert.*;

public class PlayerTest {

	@Test
	public void testPlayerName() {
		Player p = new Player("pname", 3000);
		assertEquals(p.getName(), "pname");
	}

	@Test
	public void testPlayerMoney() {
		Player p = new Player("pname", 9001);
		assertEquals(p.getMoney(), 9001);
		p.setMoney(9002);
		assertEquals(p.getMoney(), 9002);
		p.setMoney(-10);
		assertEquals(p.getMoney(), 0);
		Player p2 = new Player("p2name", -20);
		assertEquals(p2.getMoney(), 0);
	}

}