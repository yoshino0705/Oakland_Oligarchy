import org.junit.Test;
import static org.junit.Assert.*;

public class ProcessTurnTest {

	@Test
	public void testRollDie() {
		ProcessTurn pt = new ProcessTurn();
		int roll = pt.rollDie();
		assertTrue(roll >= 1 && roll <= 6);
	}
}