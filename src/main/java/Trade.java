import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Trade extends JDialog{
	// host = current player, player 123 = the other players
	
	private JButton tradeWithPlayerButton_1;
	private JButton tradeWithPlayerButton_2;
	private JButton tradeWithPlayerButton_3;
	
	private JButton tradeWithHostButton_1;
	private JButton tradeWithHostButton_2;
	private JButton tradeWithHostButton_3;
	
	private JButton payPlayerButton_1;
	private JButton payPlayerButton_2;
	private JButton payPlayerButton_3;
	
	private JButton payHostButton_1;
	private JButton payHostButton_2;
	private JButton payHostButton_3;
	
	private double scaleX;
	private double scaleY;
	
	// used to be constant, but in order to be adjustable, had to make them variables
	private double frameWidth = 2500;
	private double frameHeight = 1800;
	private double buttonWidth = 300;
	private double buttonHeight = 150;
	private Font defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, 30);

	private Player currentPlayer;
	
	public Trade(Player curPlayer, ArrayList<Player> otherPlayers, OaklandOligarchy game, double scaleInX, double scaleInY){
		// set and apply scales
		this.scaleX = scaleInX;
		this.scaleY = scaleInY;
		this.buttonWidth *= this.scaleX;
		this.buttonHeight *= this.scaleY;
		this.defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, (int)(30 * this.scaleX));
		this.currentPlayer = curPlayer;
		
		//Trader temp = new Trader();
		//this.frameWidth = temp.getScaledWidth(this.scaleX)*3 + 100;
		this.frameWidth *= this.scaleX;
		this.frameHeight *= this.scaleY;
		
		// error checking
		if(otherPlayers.size() > 4 || otherPlayers.size() < 1){
			System.out.println("Error in Trade Constructor: too few or too many guests (" + otherPlayers.size() + " guests)");
			System.exit(1);
		}
		
		// JFrame settings
		this.setTitle("Trade Menu");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize((int)frameWidth, (int)frameHeight);
		this.setLayout(null);				// swing layouts are so hard to adjust
		this.setResizable(false);
		this.toFront();
		this.setModal(true);
		
		// add in traders (other players) and host (curPlayer)
		Trader host = new Trader(curPlayer, game.tiles, this.scaleX, this.scaleY);
		host.setBounds((int)(this.scaleX * (this.frameWidth - host.width)/2), (int)(this.frameHeight - host.height - 100), (int)(host.width), (int)(host.height));
		this.add(host);
		
		// first trading button for the current player
		if(otherPlayers.size() > 0){
			Trader guest1 = new Trader(otherPlayers.get(0), game.tiles, this.scaleX, this.scaleY);
			guest1.setBounds((int)(50 * this.scaleX), (int)(50 * this.scaleY), (int)(guest1.width), (int)(guest1.height));
			this.add(guest1);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_1 = initTradePropertyButton(host, guest1);				
			tradeWithPlayerButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest1, game, getSelectedPropertyIndex(host.propertyOwned, game.tiles));
			        	
			        }
			    }
			});
			
			this.add(tradeWithPlayerButton_1);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_1 = this.initTradePropertyButton(guest1, host);				
			tradeWithHostButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest1.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest1, host, game, getSelectedPropertyIndex(guest1.propertyOwned, game.tiles));
			        	
			        }
			    }
			});

			this.add(tradeWithHostButton_1);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payHostButton_1 = this.initExchangeBalanceButton(guest1, host);
			payHostButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        makePayment(guest1, host);
			    }
			});
			this.add(payHostButton_1);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the current player to another player
			payPlayerButton_1 = this.initExchangeBalanceButton(host, guest1);
			payPlayerButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        makePayment(host, guest1);
			    }
			});
			this.add(payPlayerButton_1);
			
		}
		
		// second trading button for the current player
		if(otherPlayers.size() >= 2){
			Trader guest2 = new Trader(otherPlayers.get(1), game.tiles, this.scaleX, this.scaleY);
			guest2.setBounds((int)((50 + 500 + (buttonWidth + 50)) * this.scaleX), (int)(50 * this.scaleY), (int)(guest2.width), (int)(guest2.height));
			this.add(guest2);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_2 = this.initTradePropertyButton(host, guest2);
			tradeWithPlayerButton_2.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest2, game, getSelectedPropertyIndex(host.propertyOwned, game.tiles));
			        	
			        }
			    }
			});

			this.add(tradeWithPlayerButton_2);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_2 = this.initTradePropertyButton(guest2, host);
			tradeWithHostButton_2.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest2.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest2, host, game, getSelectedPropertyIndex(guest2.propertyOwned, game.tiles));
			        	
			        }
			    }
			});

			this.add(tradeWithHostButton_2);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payHostButton_2 = this.initExchangeBalanceButton(guest2, host);
			payHostButton_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					makePayment(guest2, host);
				}
			});
			
			this.add(payHostButton_2);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payPlayerButton_2 = this.initExchangeBalanceButton(host, guest2);
			payPlayerButton_2.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        makePayment(host, guest2);
			    }
			});
			this.add(payPlayerButton_2);
			
		}
		
		// third trading button for the current player
		if(otherPlayers.size() >= 3){
			Trader guest3 = new Trader(otherPlayers.get(2), game.tiles, this.scaleX, this.scaleY);	
			guest3.setBounds((int)((50 + 1000 + (buttonWidth + 50)*2) * this.scaleX), (int)(50 * this.scaleY), (int)(guest3.width), (int)(guest3.height));
			this.add(guest3);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_3 = this.initTradePropertyButton(host, guest3);			
			tradeWithPlayerButton_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest3, game, getSelectedPropertyIndex(host.propertyOwned, game.tiles));
			        	
			        }
			    }
			});

			this.add(tradeWithPlayerButton_3);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_3 = this.initTradePropertyButton(guest3, host);					
			tradeWithHostButton_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest3.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest3, host, game, getSelectedPropertyIndex(guest3.propertyOwned, game.tiles));
			        	
			        }
			    }
			});

			this.add(tradeWithHostButton_3);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payHostButton_3 = this.initExchangeBalanceButton(guest3, host);
			payHostButton_3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					makePayment(guest3, host);
				}
			});
						
			this.add(payHostButton_3);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payPlayerButton_3 = this.initExchangeBalanceButton(host, guest3);
			payPlayerButton_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        makePayment(host, guest3);
			    }
			});
			this.add(payPlayerButton_3);
		}		
		
		this.setVisible(true);		
		
	}
	
	// gets the selected index for a JList
	private int getSelectedPropertyIndex(JList<Object> list, ImplementTiles tiles){
		for(int i = 0; i < 36; i++){
			if(tiles.getTile(i).getTileName().equalsIgnoreCase(list.getSelectedValue().toString()))
				return i;
		}		
		
		// found nothing
		return -1;
	}
	
	// change ownership of a property
	private void changeOwnership(Trader originalOwner, Trader newOwner, OaklandOligarchy game, int propertyIndex){
		PropertyTile curTile = (PropertyTile)game.tiles.getTile(propertyIndex);
		originalOwner.removeSelectedItem();
		newOwner.addItem(curTile.getTileName());
		curTile.removeOwnership();
		curTile.setOwnership(newOwner.getPlayer());
		
		System.out.println("new owner of " + curTile.getTileName() + " is " + game.tiles.getTile(propertyIndex).getOwner().getName());

	}
	
	// give money to a player
	private void makePayment(Trader sender, Trader receiver){
		int amount = 0;
		
		try{
			amount = Integer.parseInt(JOptionPane.showInputDialog("Enter Amount"));
				
		}catch(Exception e){
			// parsing error
			JOptionPane.showMessageDialog(null, "Invalid number!");
			return;
			
		} 
		

		if(amount > sender.getPlayer().money){
			// giving too much than one can afford
			JOptionPane.showMessageDialog(null, sender.getPlayer().getName() + ", " + "you don't have that much!");
				
		}else{
			// giving an affordable amount
			sender.getPlayer().money -= amount;
			receiver.getPlayer().money += amount;
				
			// update UI
			sender.balance.setText("$ " + Integer.toString(sender.getPlayer().getMoney()));
			receiver.balance.setText("$ " + Integer.toString(receiver.getPlayer().getMoney()));
				
			// success message
			JOptionPane.showMessageDialog(null, "Done transfer!");
		}			

		
	}
	
	// initializes general settings of a button based on values from Trader class
	private JButton initTradePropertyButton(Trader sender, Trader receiver){
		JButton aButton = new JButton("<html><center>Give Selected to <br>" + receiver.getPlayer().name + "</center></html>");
		aButton.setFont(defaultButtonFont);
		
		if(sender.getPlayer() == this.currentPlayer)
			aButton.setBounds(sender.getX(), (int)(sender.getY() - buttonHeight - 10), (int)buttonWidth, (int)buttonHeight);
		else
			aButton.setBounds(sender.getX(), (int)(sender.getY() + sender.height + 10), (int)buttonWidth, (int)buttonHeight);
		
		//printButtonInfo(aButton);
		
		return aButton;
	}
	
	// initializes general settings of a button based on values from Trader class
	private JButton initExchangeBalanceButton(Trader sender, Trader receiver){
		JButton aButton = new JButton("<html><center>Pay <br>" + receiver.getPlayer().getName() + "</center></html>");
		aButton.setFont(defaultButtonFont);
		
		if(sender.getPlayer() == this.currentPlayer)
			aButton.setBounds((int)(sender.getX() + this.buttonWidth + 10), (int)(sender.getY() - buttonHeight - 10), (int)buttonWidth, (int)buttonHeight);
		else
			aButton.setBounds((int)(sender.getX() + this.buttonWidth + 10), (int)(sender.getY() + sender.height + 10), (int)buttonWidth, (int)buttonHeight);
		
		//printButtonInfo(aButton);
		
		return aButton;
	}
	
	public void printButtonInfo(JButton aButton){
		System.out.println(aButton.getText() + " X " + aButton.getX() + " Y " + aButton.getY() + " Width " + aButton.getWidth() + " Height " + aButton.getHeight());

	}
	

}
