import org.junit.Test;
import static org.junit.Assert.*;

public class TradeTest {
  @Test
  public void testChangeOwnership() {
    OaklandOligarchy game = new OaklandOligarchy("test");
    Player p1 = game.allPlayers.get(0);
    Player p2 = game.allPlayers.get(1);
    ImplementTiles tiles = game.tiles;
    Trader trader1 = new Trader(p1, tiles, 400, 400);
    Trader trader2 = new Trader(p2, tiles, 400, 400);
    Trade trade = new Trade();
    trader1.propertyOwned.setSelectedIndex(1);
    trade.changeOwnership(trader1, trader2, game, 1);
    PropertyTile tile = (PropertyTile)game.tiles.getTile(1);
    assertEquals(tile.getOwner(), p2);
  }
}
