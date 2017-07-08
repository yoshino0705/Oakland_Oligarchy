import org.junit.Test;
import static org.junit.Assert.*;

public class PropertyTileTest {

	@Test
	public void testSetRent() {
		PropertyTile t = new PropertyTile("Prop", 1000, 100);
		assertEquals(t.getRent(), 100);
		t.setRent(432);
		assertEquals(t.getRent(), 432);
	}

	@Test
	public void testSetOwner() {
		PropertyTile t = new PropertyTile("Prop", 1000, 100);
		Player p = new Player("pname", 100, 0, false);
		assertEquals(t.isOwned(), false);
		t.setOwnership(p);
		assertEquals(t.getOwner(), p);
	}

	@Test
	public void testSetOwnerWhenOwned() {
		PropertyTile t = new PropertyTile("Prop", 1000, 100);
		Player p = new Player("pname", 100, 0, false);
		Player q = new Player("qname", 100, 0, false);
		t.setOwnership(p);
		assertEquals(t.setOwnership(q), false);
		assertEquals(t.getOwner(), p);
	}

	@Test
	public void testRemoveOwner() {
		PropertyTile t = new PropertyTile("Prop", 1000, 100);
		Player p = new Player("pname", 100, 0, false);
		t.setOwnership(p);
		assertEquals(t.getOwner(), p);
		t.removeOwnership();
		assertEquals(t.isOwned(), false);
		assertEquals(t.getOwner(), null);
	}
}
