import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class BoardDataTest {
  @Test
  public void testIsHighPriorityTile() {
    OaklandOligarchy game = new OaklandOligarchy("test");
    BoardData board = new BoardData(game);
    Tile prop = game.tiles.getTile(15);
    assertEquals(board.isHighPriorityTile(prop), true);
  }
}
