
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class TopMenu extends JPanel{

	JLabel currentTurnPlayerLabel;
	JButton auctionButton = new JButton("<html>Sell<br>Property</html>");
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton mortgageButton = new JButton("<html>Mortgage</html>");
	JButton unmortgageButton = new JButton("<html>Buy Back Mortgage</html>");
	JButton rollButton = new JButton("Roll");
	JButton endTurn = new JButton("<html>End<br>Turn</html>");
	JButton saveGame = new JButton("<html>Save<br>Game</html>");
	JButton helpButton  = new JButton("Help");
	OaklandOligarchy game;


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
		this.setLayout(new GridLayout(0, 5));

		// title label
		JLabel title = new JLabel("<html>Oakland<br>Oligarchy</html>");
		title.setFont(new Font("Times", Font.PLAIN, 25));
		this.add(title, 0, 0);

		// current player
		currentTurnPlayerLabel = new JLabel("<html>Turn:<br>" + game.getCurrentTurnPlayer().getName() + "</html>", SwingConstants.CENTER);
		currentTurnPlayerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(currentTurnPlayerLabel,0,1);

		TradeListener trade_listener = new TradeListener();
		tradeButton.addActionListener(trade_listener);
		tradeButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(tradeButton, 0, 2);

		AuctionListener auction_listener = new AuctionListener();
		auctionButton.addActionListener(auction_listener);
		auctionButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(auctionButton,0,3);

		MortgageListener mortgage_listener = new MortgageListener();
		mortgageButton.addActionListener(mortgage_listener);
		mortgageButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(mortgageButton,0,4);

		rollButton.setFont(new Font("Courier", Font.PLAIN, 20));
		RollListener rl = new RollListener();
		rollButton.addActionListener(rl);
		this.add(rollButton,0,5);
		//set font for buttons in menu panel
		endTurn.setFont(new Font("Courier", Font.PLAIN, 20));
		EndTurnListener etl = new EndTurnListener();
		endTurn.addActionListener(etl);
		toggleJButtonEnabled(endTurn);
		this.add(endTurn, 0, 6);

		saveGame.setFont(new Font("Courier", Font.PLAIN, 20));
		SaveGameListener save_game_listener = new SaveGameListener();
		saveGame.addActionListener(save_game_listener);
		this.add(saveGame, 0, 7);

		HelpListener help_listener = new HelpListener();
		helpButton.addActionListener(help_listener);
		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 8);

		UnmortgageListener unmortgage_listener = new UnmortgageListener();
		unmortgageButton.addActionListener(unmortgage_listener);
		unmortgageButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(unmortgageButton,0,9);


	}

	public void toggleRollButton() {
		toggleJButtonEnabled(rollButton);
	}

	public void toggleEndTurnButton() {
		toggleJButtonEnabled(endTurn);
	}

	public void updateCurrentTurnLabel(Player p) {
		currentTurnPlayerLabel.setText("<html>Turn:<br>" + p.getName() + "</html>");
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: toggleJButtonEnabled
	~	Parameters: JButton -- Button to toggle 								~
	~	Returns: None															~
		Description: Toggles enabled state of given JButton
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	private void toggleJButtonEnabled(JButton b) {
		if (b.isEnabled()) {
			b.setEnabled(false);
		} else {
			b.setEnabled(true);
		}
	}

	class RollListener implements ActionListener {
		/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
			Function: actionPerformed
		~	Parameters: ActionEvent -- event generated from button click			~
			Returns: None
		~	Description: launches ProcessTurn instance to handle turn logic			~
		~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
		public void actionPerformed(ActionEvent e) {
			new ProcessTurn(game);
			//update info panel
			game.refreshInfoPanel();
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
			// if player lands on their own tile don't do anything
			if (pTile.getOwner() == curPlayer)
				return;

			ArrayList<PropertyTile> forecloseProps = new ArrayList<PropertyTile>();
			if (pTile.isOwned()) {
				//if the property is mortgaged and it is not owned by the current player.
				if(pTile.isMortgaged() && !pTile.getOwner().equals(curPlayer)){
					JOptionPane.showMessageDialog(null, "This properpty is mortgaged. Your lucky day!");
					return;
				}

				//if player doesn't have enough money to pay, but they have properties,
				// the bank will foreclose their properties until they can pay rent
				if (curPlayer.getMoney() < pTile.getRent()) {

					ArrayList<PropertyTile> ownedProps = game.getOwnedProperties(curPlayer);
					// auction until player can afford rent
					for (PropertyTile prop : ownedProps) {
						// if they have enough to pay rent after selling, break out of loop
						if (curPlayer.getMoney() > pTile.getRent()) {
							break;
						}
						int price = prop.getValue();
						curPlayer.setMoney(curPlayer.getMoney() + (price/2));
						prop.removeOwnership();
						curPlayer.removeProperty(prop)
						forecloseProps.add(prop);
						ownedProps = game.getOwnedProperties(curPlayer);
					}

					// if after auctioning all properties, they still cant pay rent
					// then the player loses
					if (curPlayer.getMoney() < pTile.getRent()) {
						// tell the player they lost
						JOptionPane.showMessageDialog(null, "You ran out of money and properties. You lose!");
						game.playerLose(curPlayer);
						game.endTurn();

						//check to see if a player has won and if so notify them
						if (game.checkWon()) {
							Player winner = game.getWinner();
							JOptionPane.showMessageDialog(null, winner.getName() + " has won the game!");
						}

						game.getGameBoard().refreshBoard();
						return;
					}
				}

				if (forecloseProps.size() > 0) {
					String msg = "You didn't have enough money to pay rent so they bank foreclosed these properties:\n";
					for (PropertyTile prop : forecloseProps)
						msg += prop.getTileName() + "\n";
					JOptionPane.showMessageDialog(null, msg);
				} else
					// notify player that they owe someone rent
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
					//set pTile as a tile of curPlayer
					curPlayer.addProperty(pTile);
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
		public void animatedMovePlayer(GameBoard gb, int playerNum, int startPos, int roll) {
				boolean back = false;
				if(roll < 0){
					roll *= -1;
					back = true;
				}
				for (int i = 1; i <= roll; i++) {
					if(back == false){
						gb.movePlayer(playerNum, (i + startPos) % 36);
					}else{
						gb.movePlayer(playerNum, (startPos - i) % 36);
					}

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

	class UnmortgageListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			Player curPlayer = game.getCurrentTurnPlayer();
			ArrayList<PropertyTile> owned = curPlayer.getPropertiesList();

			//count how many properties are unmortgaged
			int mortgageCount = 0;
			for(int i = 0; i < owned.size(); ++i){
				if(owned.get(i).isMortgaged())
					++mortgageCount;
			}
			String[] options = new String[mortgageCount];

			//build list of properties that can be mortgaged
			int optionCount = 0;
			for(int i = 0; i < owned.size(); ++i){
				if(owned.get(i).isMortgaged()){
					options[optionCount] = owned.get(i).getTileName();
					++optionCount;
				}
			}

			String choice;
			//check if the user is capable of mortgaging any properties.
			if(options.length > 0){
				choice = (String) JOptionPane.showInputDialog(null,"Choose which property to buy back.","Buy Back", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				for(int i = 0; i < owned.size(); ++i){
					if(owned.get(i).getTileName().equals(choice)){
						curPlayer.buyBackMortgage(owned.get(i));
						break;
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "You do not have any properties to buy back at this time.");
				return;
			}
			game.refreshInfoPanel();
		}
	}

	class MortgageListener implements ActionListener{
		public void actionPerformed(ActionEvent e){

			Player curPlayer = game.getCurrentTurnPlayer();
			ArrayList<PropertyTile> owned = curPlayer.getPropertiesList();

			//count how many properties are unmortgaged
			int unmortgageCount = 0;
			for(int i = 0; i < owned.size(); ++i){
				if(!owned.get(i).isMortgaged())
					++unmortgageCount;
			}
			String[] options = new String[unmortgageCount];

			//build list of properties that can be mortgaged
			int optionCount = 0;
			for(int i = 0; i < owned.size(); ++i){
				if(!owned.get(i).isMortgaged()){
					options[optionCount] = owned.get(i).getTileName();
					++optionCount;
				}
			}

			String choice;
			//check if the user is capable of mortgaging any properties.
			if(options.length > 0){
				choice = (String) JOptionPane.showInputDialog(null,"Choose which property to mortgage.","Mortgage", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

				for(int i = 0; i < owned.size(); ++i){
					if(owned.get(i).getTileName().equals(choice)){
						curPlayer.mortgage(owned.get(i));
						break;
					}
				}
			}else{
				JOptionPane.showMessageDialog(null, "You do not have any properties to mortgage at this time.");
				return;
			}
			game.refreshInfoPanel();
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
			game.endTurn();

			// toggle turn buttons
			toggleJButtonEnabled(rollButton);
			toggleJButtonEnabled(endTurn);
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
			File file = new File("src/main/java/HelpDocs.htm");
			try {
				editorpane.setPage(file.toURI().toURL());
			} catch(Exception ex){
				//couldnt load that file, try other path
				try {
					//this is the file path if you are testing from within src/main/java
					File file2 = new File("HelpDocs.htm");
					editorpane.setPage(file2.toURI().toURL());
				} catch(Exception exx){
					System.out.println("Error: Couldn't find HelpDocs.htm");
				}
			}
			editorpane.setEditable(false);
			window.setSize(600,600);
			window.add(editorScrollPane);
			window.setVisible(true);

		}

	}//end of class HelpListener

	class TradeListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			ArrayList<Player> otherPlayers = new ArrayList<Player>(game.allPlayers);
			otherPlayers.remove(game.getCurrentTurnPlayer());	// this arraylist should only contain players excluding current turn player
			CustomFrameScale cfs = new CustomFrameScale();

			new Trade(game.getCurrentTurnPlayer(), otherPlayers, game, cfs.getTradeMenuScaleX(), cfs.getTradeMenuScaleY());
		}
	}//end of class TradeListener



	class AuctionListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("Auction Started");
			new Auction(game);
		}
	}//end of class AuctionListener


	class SaveGameListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			System.out.println("saving game");
			new Saver(game);
		}
	}//end of SaveGameListener


}
