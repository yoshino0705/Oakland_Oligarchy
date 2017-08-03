import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DemoMode extends JDialog implements Runnable{
	
	// starts demo mode 
	public DemoMode() {
		// starts a new game
		ArrayList<Player> theAIs = new ArrayList<Player>();
		for(int i= 0; i < 4; i++) {
			Player p = new AI("AI " + AI.AI_Count++, 1000, i, false);
			theAIs.add(p);
			
		}
		
		new OaklandOligarchy(theAIs);					
		
		Thread seperateProgram = new Thread(this);
		seperateProgram.start();

	}

	public static void main(String[] args) {
		new DemoMode();

	}

	@Override
	public void run() {
		// creates a close button for the demo game
				JFrame frame = new JFrame("End Program");
				JPanel panel = new JPanel();
				JButton close = new JButton("End Demo");
				close.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						System.exit(0);
					}
				});
				
				panel.setLayout(new BorderLayout());
				panel.add(close);
				frame.add(panel);
				frame.setSize(200, 200);		
				frame.setVisible(true);	
		
	}

}
