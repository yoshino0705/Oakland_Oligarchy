import org.junit.Test;
import static org.junit.Assert.*;

public class ProcessTurnTest {

	@Test
	public void testRollDie() {
		ProcessTurn pt = new ProcessTurn();
		int roll = pt.rollDie();
		assertTrue(roll >= 1 && roll <= 6);
	}

	@Test
	public void testMovePlayer() {
		OaklandOligarchy game = new OaklandOligarchy("test");
		Player p1 = game.allPlayers.get(0);
		int oldPosition = p1.getPosition();
		ProcessTurn pturn = new ProcessTurn();
		pturn.movePlayerNoAnimation(game, 5);
		assertEquals(p1.getPosition(), (oldPosition + 5) % 36);
	}
}
