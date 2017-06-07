import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

import org.junit.Test;
import static org.junit.Assert.*;

public class InfoPanelTest {

	@Test
	public void testInfoPanelJLabels() {
		ArrayList<Player> list = generatePlayerList();
		InfoPanel ip = new InfoPanel(list);
		assertEquals(ip.all_labels.size(), 20);
		list.remove(list.size() - 1);
		ip = new InfoPanel(list);
		assertEquals(ip.all_labels.size(), 15);
	}

	private ArrayList<Player> generatePlayerList() {
		ArrayList<Player> list = new ArrayList<Player>();
		Player p0 = new Player("p0", 1000);
		list.add(p0);
		Player p1 = new Player("p1", 2000);
		list.add(p1);
		Player p2 = new Player("p2", 3333);
		list.add(p2);
		Player p3 = new Player("p3", 4545);
		list.add(p3);
		return list;
	}
}