import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class ActionTileTest {
  @Test
  public void testGetTileFlag() {
    ActionTile tile = new ActionTile(1);
    assertEquals(tile.getTileFlag(), 1);
  }

  @Test
  public void testGetTileInfo() {
    ActionTile tile = new ActionTile(1);
    assertEquals(tile.getTileInfo(), "Look Down!");
  }
}
