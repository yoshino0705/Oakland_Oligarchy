import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;

public class AuctionTest {
  @Test
  public void testGetPlayerPropertyNames() {
    OaklandOligarchy game = new OaklandOligarchy("test");
    Auction auction = new Auction(game, "test");
    ArrayList ownedTiles = new ArrayList<String>();
    ownedTiles.add("Peter's Pub");
    ownedTiles.add("Market-to-Go");
    Player p = game.allPlayers.get(0);
    assertEquals(auction.getPlayerPropertyNames(p, game), ownedTiles);
  }

  @Test
  public void testGetPlayerOwnsProperty() {
    OaklandOligarchy game = new OaklandOligarchy("test");
    Auction auction = new Auction(game, "test");
    PropertyTile prop = new PropertyTile("Peter's Pub", 60, 12);
    Player p = game.allPlayers.get(0);
    prop.setOwnership(p);
    assertEquals(auction.playerOwnsProperty(prop, p), true);
  }
}
