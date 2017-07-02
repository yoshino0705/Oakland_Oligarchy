import static org.junit.Assert.*;

import org.junit.Test;

public class CustomFrameScaleTest {
	@Test
	public void testGetTradeMenuScaleX() {
		CustomFrameScale cfs = new CustomFrameScale();
		assertEquals(cfs.getTradeMenuScaleX(0), 0);
		
	}

	@Test
	public void testGetTradeMenuScaleY() {
		CustomFrameScale cfs = new CustomFrameScale();
		assertEquals(cfs.getTradeMenuScaleY(0), 0);
	}
}
