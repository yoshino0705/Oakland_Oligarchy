import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class TopMenu extends JPanel{

	JLabel currentTurnPlayerLabel;
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton rollButton = new JButton("Roll");
	JButton newGame = new JButton("New Game");
	JButton endGame = new JButton("End Game");
	JButton helpButton  = new JButton("Help");
	OaklandOligarchy game;

	TopMenu(OaklandOligarchy oo) {
		game = oo;
		this.setPreferredSize(new Dimension(1000, 100));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		this.setLayout(new GridLayout(0, 7));

		// title label
		JLabel title = new JLabel("<html>Oakland<br>Oligarchy</html>");
		title.setFont(new Font("Times", Font.PLAIN, 30));
		this.add(title, 0, 0);
		
		// current player
		currentTurnPlayerLabel = new JLabel("<html>Turn:<br>" + game.currentTurnPlayer.getName() + "</html>", SwingConstants.CENTER);
		currentTurnPlayerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(currentTurnPlayerLabel,0,1);

		tradeButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(tradeButton, 0, 2);
		rollButton.setFont(new Font("Courier", Font.PLAIN, 20));
		RollListener rl = new RollListener();
		rollButton.addActionListener(rl);
		this.add(rollButton,0,3);
		//set font for buttons in menu panel
		newGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(newGame, 0, 4);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(endGame, 0, 5);

		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 6);

	}

	class RollListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// calculate player's roll
			Random rand = new Random();
			int min = 1;
			int max = 6;
			int roll1 = rand.nextInt((max - min) + 1) + min;
			int roll2 = rand.nextInt((max - min) + 1) + min;
			int rollSum = roll1 + roll2;

			// move player
			Player curPlayer = game.currentTurnPlayer;
			System.out.println("Current Position: " + curPlayer.getPosition());
			System.out.println("Roll: " + rollSum);
			int position = (curPlayer.getPosition() + rollSum) %  36;
			System.out.println("New position: " + position);
			game.gb.movePlayer(game.getIndexCurrentTurnPlayer(), position);
			game.gb.refreshBoard();

			// update Players positiom
			curPlayer.setPosition(position);

			// if tile is property, player can either buy or has to pay rent
			Tile curTile = game.tiles.getTile(position);
			if (curTile.isProperty()) {
				PropertyTile pTile = (PropertyTile)curTile;
				if (pTile.isOwned()) {
					// notify player
					JOptionPane.showMessageDialog("You landed on a tile owned by " + pTile.getOwner().getName() + 
														". You pay them " + pTile.getRent() + "dollars.");
					// subtract money from curPlayer and add to owner
					curPlayer.setMoney(curPlayer.getMoney() - pTile.getRent());
					pTile.getOwner().setMoney(pTile.getOwner().getMoney() + pTile.getRent());
				} else {
					//give player option to buy tile
					int result = JOptionPane.showConfirmDialog(null, "Would you like to buy this property for " + pTile.getValue() + " dollars?",
															"Purchase Property", JOptionPane.YES_NO_OPTION);
					if (result == JOptionPane.YES_OPTION) {
						// subtract cost from player
						curPlayer.setMoney(curPlayer.getMoney() - pTile.getValue());
						// set curPlayer as owner of tile
						pTile.setOwnership(curPlayer);
					}

				}
			} else {	// tile is action tile and action is performed
				//TODO: perform action to player
				;
			}
			// *	*	*	*	*	*	*	
			//TODO: update side menu!!!!
			// *	*	*	*	*	*	*	
		}
	}
}