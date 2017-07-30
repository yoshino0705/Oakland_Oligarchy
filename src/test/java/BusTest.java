import org.junit.Test;
import static org.junit.Assert.*;

public class BusTest {

    @Test
    public void TestAddProperty(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);

        assertFalse(p.addProperty(pt));
        assertEquals(50, pt.getRent());
    }

    @Test
    public void TestAddProperty2(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        p.addProperty(pt);

        assertFalse(p.addProperty(pt2));
        assertEquals(100, pt.getRent());
        assertEquals(100, pt2.getRent());
    }

    @Test
    public void TestAddProperty3(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        PropertyTile pt3 = new PropertyTile("61B", 200, 50);

        p.addProperty(pt);
        p.addProperty(pt2);

        assertFalse(p.addProperty(pt3));
        assertEquals(200, pt.getRent());
        assertEquals(200, pt2.getRent());
        assertEquals(200, pt3.getRent());
    }

    @Test
    public void TestAddPropertyTrue(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        PropertyTile pt3 = new PropertyTile("61C", 200, 50);
        PropertyTile pt4 = new PropertyTile("61D", 200, 50);

        p.addProperty(pt);
        p.addProperty(pt2);
        p.addProperty(pt3);

        assertTrue(p.addProperty(pt4));
    }

    @Test
    public void TestRemovePropertyTrue(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        PropertyTile pt3 = new PropertyTile("61C", 200, 50);

        p.addProperty(pt);
        p.addProperty(pt2);
        p.addProperty(pt3);

        assertTrue(p.removeProperty(pt3));
    }

    @Test
    public void TestRemovePropertyRent(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        PropertyTile pt3 = new PropertyTile("61C", 200, 50);
        //PropertyTile pt4 = new PropertyTile("61D", 200, 50);

        p.addProperty(pt);
        p.addProperty(pt2);
        p.addProperty(pt3);
        p.removeProperty(pt);

        assertEquals(100, pt2.getRent());
        assertEquals(100, pt3.getRent());
    }

    @Test
    public void TestRemoveMultipleRent(){
        Player p = new Player("test", 1000000, 0, false);
        PropertyTile pt = new PropertyTile("61A", 200, 50);
        PropertyTile pt2 = new PropertyTile("61B", 200, 50);
        PropertyTile pt3 = new PropertyTile("61C", 200, 50);
        //PropertyTile pt4 = new PropertyTile("61D", 200, 50);

        p.addProperty(pt);
        p.addProperty(pt2);
        p.addProperty(pt3);

        assertEquals(200, pt2.getRent());
        assertEquals(200, pt3.getRent());
        assertEquals(200, pt.getRent());

        p.removeProperty(pt2);

        assertEquals(100, pt.getRent());
        assertEquals(100, pt3.getRent());
        assertEquals(50, pt2.getRent());

        p.removeProperty(pt3);

        assertEquals(50, pt.getRent());
        assertEquals(50, pt3.getRent());
        assertEquals(50, pt2.getRent());

        p.removeProperty(pt);

        assertEquals(50, pt.getRent());
        assertEquals(50, pt3.getRent());
        assertEquals(50, pt2.getRent());
    }
}
