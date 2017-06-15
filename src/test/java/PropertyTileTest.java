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
}