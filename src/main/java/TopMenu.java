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
	JButton rollButton = new JButton("Roll");
	JButton endTurn = new JButton("<html>End<br>Turn</html>");
	JButton endGame = new JButton("<html>End<br>Game</html>");
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
		this.setLayout(new GridLayout(0, 8));

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

		rollButton.setFont(new Font("Courier", Font.PLAIN, 20));
		RollListener rl = new RollListener();
		rollButton.addActionListener(rl);
		this.add(rollButton,0,4);
		//set font for buttons in menu panel
		endTurn.setFont(new Font("Courier", Font.PLAIN, 20));
		EndTurnListener etl = new EndTurnListener();
		endTurn.addActionListener(etl);
		toggleJButtonEnabled(endTurn);
		this.add(endTurn, 0, 5);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(endGame, 0, 6);

		HelpListener help_listener = new HelpListener();
		helpButton.addActionListener(help_listener);
		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		this.add(helpButton, 0, 7);


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
}
