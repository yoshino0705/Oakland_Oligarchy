import org.junit.Test;
import static org.junit.Assert.*;

public class SaverTest {
  @Test
  public void testGetPlayerProperties() {
    OaklandOligarchy game = new OaklandOligarchy("test");
    Saver saver = new Saver();
    Player p = game.allPlayers.get(0);
    assertEquals(saver.getPlayerProperties(game, p), "2\nPeter's Pub:false\nMarket-to-Go:false\n");
  }
}
