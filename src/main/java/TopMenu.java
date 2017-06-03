import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class TopMenu extends JPanel{

	JLabel currentTurnPlayerLabel;
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton rollButton = new JButton("Roll");
	JButton newGame = new JButton("New Game");
	JButton endGame = new JButton("End Game");
	JButton helpButton  = new JButton("Help");

	TopMenu(Player curPlayer) {
		this.setPreferredSize(new Dimension(1000, 100));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(0, 7));

		// title label
		JLabel title = new JLabel("<html>Oakland<br>Oligarchy</html>");
		title.setFont(new Font("Times", Font.PLAIN, 30));
		this.add(title, 0, 0);
		
		// current player
		currentTurnPlayerLabel = new JLabel("<html>Turn:<br>" + curPlayer.getName() + "</html>", SwingConstants.CENTER);
		currentTurnPlayerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(currentTurnPlayerLabel,0,1);

		tradeButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(tradeButton, 0, 2);
		rollButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(rollButton,0,3);
		//set font for buttons in menu panel
		newGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(newGame, 0, 4);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(endGame, 0, 5);

		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 6);

	}
}