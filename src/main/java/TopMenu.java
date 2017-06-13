import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class TopMenu extends JPanel{

	JLabel currentTurnPlayerLabel;
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton rollButton = new JButton("Roll");
	JButton endTurn = new JButton("End Turn");
	JButton endGame = new JButton("End Game");
	JButton helpButton  = new JButton("Help");
	OaklandOligarchy game;
	private boolean canRoll = true;

	TopMenu(OaklandOligarchy oo) {
		this.game = oo;
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
		endTurn.setFont(new Font("Courier", Font.PLAIN, 20));
		EndTurnListener etl = new EndTurnListener();
		endTurn.addActionListener(etl);
		this.add(endTurn, 0, 4);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(endGame, 0, 5);

		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 6);

	}

	class RollListener implements ActionListener {
		final int NUM_TILES = 36;

		public void actionPerformed(ActionEvent e) {
			// if player has already rolled, yell at them if they try to roll again
			if (!canRoll) {
				JOptionPane.showMessageDialog(null, "Hey! You already rolled! " + 
										"End your turn so the next player can go.");
				return;
			}
			// calculate player's roll
			Random rand = new Random();
			int min = 1;
			int max = 6;
			int roll1 = rand.nextInt((max - min) + 1) + min;
			int roll2 = rand.nextInt((max - min) + 1) + min;
			int rollSum = roll1 + roll2;

			// logic to move player
			Player curPlayer = game.currentTurnPlayer;
			int newPosition = (curPlayer.getPosition() + rollSum) % NUM_TILES;
			// move player on board
			animatedMovePlayer(game.gb, game.getIndexCurrentTurnPlayer(), curPlayer.getPosition(), newPosition);
			// update Players positiom
			curPlayer.setPosition(newPosition);

			// if tile is property, player can either buy or has to pay rent
			Tile curTile = game.tiles.getTile(newPosition);
			if (curTile.isProperty()) {
				PropertyTile pTile = (PropertyTile) curTile;
				if (pTile.isOwned()) {
					// notify player
					JOptionPane.showMessageDialog(null, "You landed on a tile owned by " + pTile.getOwner().getName() + 
														". You pay them " + pTile.getRent() + " dollars.");
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
				System.out.println("Player " + curPlayer.getName() + " money: " + curPlayer.getMoney());
			} else {	// tile is action tile and action is performed
				ActionTile aTile = (ActionTile) curTile;
				//TODO: perform action to player
				;
			}
			// toggle canRoll to prevent player from being able to roll multiple times per turn
			canRoll = false;
			// *	*	*	*	*	*	*	
			// TODO: update side menu!!!!
			// game.info.update();
			// *	*	*	*	*	*	*	
		}

		private void animatedMovePlayer(GameBoard gb, int playerNum, int startPos, int endPos) {
			for (int i = startPos + 1; i < endPos; i++) {
				gb.movePlayer(playerNum, i);
				gb.refreshBoard();
				// sleep so user can see animation
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					System.out.println("I can't fall asleep!");
				}
			}
		}
	}

	class EndTurnListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			// increment turn count
			game.numTurns = game.numTurns + 1;
			int nextTurnPlayer = game.getIndexCurrentTurnPlayer();
			// update current player & label
			game.currentTurnPlayer = (game.allPlayers.get(nextTurnPlayer));
			currentTurnPlayerLabel.setText("<html>Turn:<br>" + game.currentTurnPlayer.getName() + "</html>");

			// toggle canRoll for new turn
			canRoll = true;
		}
	}
}