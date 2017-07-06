import org.junit.Test;
import static org.junit.Assert.*;

public class ClockTest {
  @Test
  public void testSetTime() {
    Clock myClock = new Clock(0,0,0);
    assertEquals(myClock.getTime(), "00:00:00");
  }

  @Test
  public void testIncrementTime() {
    Clock myClock = new Clock(0,0,0);
    myClock.tick();
    assertEquals(myClock.getTime(), "00:00:01");
  }

  @Test
  public void testTimeRollover() {
    Clock myClock = new Clock(0,0,59);
    myClock.tick();
    assertEquals(myClock.getTime(), "00:01:00");
  }
}
