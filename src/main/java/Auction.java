import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class Auction extends JDialog {
	private final int TILE_COUNT = 36;

	private JButton[] bidButtons;
	private JLabel lblCurPrice, lblCurLeader;
	private int curPrice = 0;
	private Player lastBid;
	private boolean hasProperties = true;
	JPanel topPanel;
	JPanel middlePanel;
	JButton conclude_button;
	OaklandOligarchy game;
	String aProp = "";
	
	public Auction(OaklandOligarchy game){
		this.game = game;
		Player owner = game.getCurrentTurnPlayer();	//getCurrentTurnPlayer();
		ArrayList<String> ownerPropertyNames = getPlayerPropertyNames(owner, game);

		// prompt player to select a property to auction
		String[] pChoices = new String[ownerPropertyNames.size()];
		try {
			pChoices = ownerPropertyNames.toArray(pChoices);
			aProp = (String) JOptionPane.showInputDialog(null, "Choose a property to auction:", "Auction",
												JOptionPane.QUESTION_MESSAGE, null, pChoices, pChoices[0]);
		} catch (Exception e) {
			try {
				aProp = pChoices[0];
			} catch(Exception ex){//player has no properties to auction
				System.out.println("no properties to auction");
				this.hasProperties = false;
				JOptionPane.showMessageDialog(game.window, "You have no properties to auction");
			}
		}
		//player has properties and selected one to auction
		if(hasProperties){
			//set up our window
			this.setTitle(aProp + " Auction");
			this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			this.setResizable(false);
			this.setSize(600,200);	
			this.setLayout(new GridLayout(3,1));
			//topPanel holds current bid and current leader
			topPanel = new JPanel();
			topPanel.setLayout(new BorderLayout());

			// add label w/ current price to window
			lblCurPrice = new JLabel("Current Price: " + curPrice);
			topPanel.add(lblCurPrice, BorderLayout.WEST);
			lblCurLeader = new JLabel("Leader: N/A");
			topPanel.add(lblCurLeader, BorderLayout.EAST);
			this.add(topPanel);
			
			//add buttons for each player that can bid
			ArrayList<Player> otherPlayers = getOtherPlayers(game);
			bidButtons = new JButton[otherPlayers.size()];
			//middle panel holds bid buttons for each player
			middlePanel = new JPanel();
			middlePanel.setLayout(new GridLayout(1,otherPlayers.size()));

			//add action listeners to each bid button
			for (int i = 0; i < otherPlayers.size(); i++) {
				Player thisPlayer = otherPlayers.get(i);
				bidButtons[i] = new JButton(otherPlayers.get(i).getName() + " bid +$10");
				bidButtons[i].addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						makeBid(thisPlayer);
					}
				});
				middlePanel.add(bidButtons[i]);
			}//end for
			this.add(middlePanel);
			//this button will end the auction and if a bid was made the prop goes to high bidder
			conclude_button = new JButton("Conclude The Auction");
			ConcludeListener conclude_listener = new ConcludeListener();
			conclude_button.addActionListener(conclude_listener);
			this.add(conclude_button);

			this.setVisible(true);
		}
	}//end of constructor

	//make current high bid currentBid + 10 by player
	private void makeBid(Player p){
		curPrice += 10;
		lastBid = p;
		lblCurPrice.setText("Current Price: " + curPrice);
		lblCurLeader.setText("Leader: " + p.getName());
	}//end makeBid()

	//get all the players who are not the one auctioning the property
	//return these players in an ArrayList
	private ArrayList<Player> getOtherPlayers(OaklandOligarchy game) {
		ArrayList<Player> retList = new ArrayList<Player>();
		retList.addAll(game.allPlayers);

		for (int i = 0; i < retList.size(); i++) {
			if (retList.get(i).getName() == game.getCurrentTurnPlayer().getName()) {
				retList.remove(i);
				return retList;
			}
		}
		return retList;
	}//end getOtherPlayers()

	//get all properties owned by player and return them in an ArrayList
	private ArrayList<String> getPlayerPropertyNames(Player p, OaklandOligarchy game) {
		ArrayList<String> retList = new ArrayList<String>();
		for (int i = 0; i < TILE_COUNT; i++) {
			Tile curTile = game.tiles.getTile(i);	//game.getTiles().getTile(i)
			if (curTile.isProperty() && playerOwnsProperty((PropertyTile) curTile, p))
				retList.add(curTile.getTileName());
		}

		return retList;
	}//end getPlayerpropertyNames()

	private boolean playerOwnsProperty(PropertyTile prop, Player p) {
		return prop.getOwner() == p;
	}//end playerOwnsProperty()


	//listener for the conclude button
	//ends the auction, transfers property to high bidder if a bid was made
	class ConcludeListener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			remove(topPanel);
			remove(middlePanel);
			remove(conclude_button);

			setLayout(new GridLayout(1,1));
			JLabel msg = new JLabel("");
			//if noone has bid
			if(lblCurLeader.getText().equals("Leader: N/A")){
				msg.setText("Sorry, nobody wants your property");
				add(msg);
			}else{//someone has bid, sell property to that player
				msg.setText("<html>High bid was "+curPrice+" from "+lastBid.getName()+"."
						+"<br>The property will be sold to that player.</html>");
				add(msg);
				//set new owner
				for(int i = 0; i < TILE_COUNT; i++){
					Tile curTile = game.tiles.getTile(i);
					if(curTile.isProperty() && curTile.getTileName().equals(aProp)){
						System.out.println("transfering " + curTile.getTileName());
						curTile.removeOwnership();
						curTile.setOwnership(lastBid);
					}
				}//end for loop
				lastBid.setMoney(lastBid.getMoney()-curPrice);
				Player owner = game.getCurrentTurnPlayer();	//getCurrentTurnPlayer();
				owner.setMoney(owner.getMoney()+curPrice);
				game.refreshInfoPanel();
			}


			validate();
			repaint();
			setVisible(true);
		}//end of actionPerformed()
	}//end of class ConcludeListener











	public static void main(String[] args) {
		;
	}//end main





}//end class Auction

