import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class TopMenu extends JPanel{

	JLabel currentTurnPlayerLabel;
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton rollButton = new JButton("Roll");
	JButton endTurn = new JButton("End Turn");
	JButton endGame = new JButton("End Game");
	JButton helpButton  = new JButton("Help");
	OaklandOligarchy game;
	private boolean canRoll = true;


	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: TopMenu (constructor)
		~	Parameters: OaklandOligarchy -- reference to OaklandOligarchy 			~
						instantiation containing this menu
		~	Returns: None															~				
			Description: Sets up buttons and action listeners for the top menu
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
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
		
		HelpListener help_listener = new HelpListener();
		helpButton.addActionListener(help_listener);
		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 6);

	}

	class RollListener implements ActionListener {
		final int NUM_TILES = 36;

		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: actionPerformed
		~	Parameters: ActionEvent -- event generated from button click			~
			Returns: None															
		~	Description: Moves player, handles buying property/paying rent/action 	~
						 tiles.
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
		public void actionPerformed(ActionEvent e) {
			// if player has already rolled, yell at them if they try to roll again
			if (!canRoll) {
				JOptionPane.showMessageDialog(null, "Hey! You already rolled! " + 
										"End your turn so the next player can go.");
				return;
			}

			// calculate player's dice rolls
			int roll1 = rollDie();
			int roll2 = rollDie();
			int rollSum = roll1 + roll2;

			System.out.println("roll: " + rollSum);

			// get current player & calculate their new position
			Player curPlayer = game.currentTurnPlayer;
			int newPosition = (curPlayer.getPosition() + rollSum) % NUM_TILES;
			// move player on board
			animatedMovePlayer(game.gb, game.getIndexCurrentTurnPlayer(), curPlayer.getPosition(), rollSum);
			
			System.out.println("Old pos: " + curPlayer.getPosition());

			// update Players positiom
			curPlayer.setPosition(newPosition);

			System.out.println("new pos: " + curPlayer.getPosition());

			// if tile is property, player can either buy or has to pay rent
			Tile curTile = game.tiles.getTile(newPosition);
			if (curTile.isProperty()) {
				PropertyTile pTile = (PropertyTile) curTile;
				doPropertyInteraction(pTile, curPlayer);
			} else {	// tile is action tile and action is performed
				ActionTile aTile = (ActionTile) curTile;
				//TODO: perform action to player
			}
			// toggle canRoll to prevent player from being able to roll multiple times per turn
			canRoll = false;
			//update info panel
			game.info.refresh(game.allPlayers, game.tiles);
		}

		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: doPropertyInteraction
		~	Parameters: PropertyTile -- tile player has landed on 					~
						curPlayer -- player who's turn it is
		~	Returns: None															~
			Description: Handles buying property/paying rent when a player lands on 
		~				 a property tile.											~
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
		private void doPropertyInteraction(PropertyTile pTile, Player curPlayer) {
			if (pTile.isOwned()) {
				// notify player
				JOptionPane.showMessageDialog(null, "You landed on " + pTile.getTileName() + " owned by " + 
													pTile.getOwner().getName() + ". You pay them " + 
													pTile.getRent() + " dollars.");
				// subtract money from curPlayer and add to owner
				curPlayer.setMoney(curPlayer.getMoney() - pTile.getRent());
				pTile.getOwner().setMoney(pTile.getOwner().getMoney() + pTile.getRent());
			} else {
				// check if player has enough money to purchase property
				if (curPlayer.getMoney() < pTile.getValue()) {
					JOptionPane.showMessageDialog(null, "You landed on " + pTile.getTileName() + " but you don't have"
													+ " enough money to purchase it.\n" + pTile.getTileName() + 
													" costs $" + pTile.getValue() + " but you only have $" + 
													curPlayer.getMoney() + ". How sad :(");
					return;
				}
				//give player option to buy tile
				int result = JOptionPane.showConfirmDialog(null, "You landed on " + pTile.getTileName() + 
																", would you like to buy this property for " + 
																pTile.getValue() + " dollars?",
																"Purchase Property", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// subtract cost from player
					curPlayer.setMoney(curPlayer.getMoney() - pTile.getValue());
					// set curPlayer as owner of tile
					pTile.setOwnership(curPlayer);
				} 
			}
		}

		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: animatedMovePlayer
		~	Parameters: GameBoard -- reference to OaklandOligarchy's GameBoard		~
						playerNum -- index of player who's turn it is
		~				startPos -- current position of player 						~
						roll -- value of player's roll
		~	Returns: None															~
			Description: Moves player from startPos to endPos, hopping through each 
		~				 tile in between the two.									~
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
		private void animatedMovePlayer(GameBoard gb, int playerNum, int startPos, int roll) {
			for (int i = 1; i <= roll; i++) {
				gb.movePlayer(playerNum, (i + startPos) % NUM_TILES);
				gb.refreshBoard();
				// sleep so user can see animation
				try {
					Thread.sleep(200);
				} catch (Exception e) {
					System.out.println("I can't fall asleep!");
				}
			}
		}

		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: rollDie
		~	Parameters: None														~
			Returns: int with value between 1 and 6													~
		~	Description: Simulates die roll; returns random number between 1 and 6	~
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
		private int rollDie() {
			Random rand = new Random();
			int min = 1;
			int max = 6;
			return rand.nextInt((max - min) + 1) + min;
		}
	}

	class EndTurnListener implements ActionListener {

		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: actionPerformed
		~	Parameters: ActionEvent -- event generated from button click			~
			Returns: None															
		~	Description: Ends user's turn; increments number of turns and updates	~
						 player who's turn it currently is.
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
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


	//this is the listener for the help button
	//it will spawn a new window made from a local html file
	class HelpListener implements ActionListener{

		public void actionPerformed(ActionEvent e){
			JFrame window = new JFrame("Oakland Oligarchy Help Documentation");
			JEditorPane editorpane= new JEditorPane();
			JScrollPane editorScrollPane = new JScrollPane(editorpane);
			editorScrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			//this is the file path if ran from the root dir like gradle run
			File file = new File("src/main/java/HelpDocs.html");
			try {
				editorpane.setPage(file.toURI().toURL());
			} catch(Exception ex){
				//couldnt load that file, try other path
				try {
					//this is the file path if you are testing from within src/main/java
					File file2 = new File("HelpDocs.html");
					editorpane.setPage(file2.toURI().toURL());
				} catch(Exception exx){
					System.out.println("Error: Couldn't find HelpDocs.html");
				}
			}
			editorpane.setEditable(false);
			window.setSize(600,600);
			window.add(editorScrollPane);
			window.setVisible(true);
	
		}

	}//end of class HelpListener


}
