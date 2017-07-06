import org.junit.Test;
import static org.junit.Assert.*;

public class ImplementTilesTest {
  @Test
  public void testGetTile() {
    ImplementTiles tiles = new ImplementTiles();
    Tile testTile = new PropertyTile("Peter's Pub", 60, 12);
    tiles.setTile(testTile, 1);
    assertEquals(tiles.getTile(1), testTile);
  }
}
