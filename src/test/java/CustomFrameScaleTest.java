import static org.junit.Assert.*;

import org.junit.Test;

public class CustomFrameScaleTest {
	@Test
	public void testGetTradeMenuScaleX() {
		CustomFrameScale cfs = new CustomFrameScale();
		assertEquals(Double.toString(cfs.getTradeMenuScaleX(0)), Double.toString(0));
		
	}

	@Test
	public void testGetTradeMenuScaleY() {
		CustomFrameScale cfs = new CustomFrameScale();
		assertEquals(Double.toString(cfs.getTradeMenuScaleY(0)), Double.toString(0));
	}
}
