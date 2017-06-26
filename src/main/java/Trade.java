import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Trade extends JFrame{
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
	
	private final int FRAME_WIDTH = 2500;
	private final int FRAME_HEIGHT = 1800;
	private final int BUTTON_WIDTH = 300;
	private final int BUTTON_HEIGHT = 150;
	private final int DEFAULT_BUTTON_Y = 800;
	private Font BUTTON_FONT = new Font("TimesNewRoman", Font.BOLD, 30);
	
	public Trade(Player curPlayer, ArrayList<Player> otherPlayers, OaklandOligarchy game){
		// error checking
		if(otherPlayers.size() > 4 || otherPlayers.size() < 1){
			System.out.println("Error in Trade Constructor: too few or too many guests (" + otherPlayers.size() + " guests)");
			System.exit(1);
		}
		
		// JFrame settings
		this.setTitle("Trade Menu");
		this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		this.setSize(FRAME_WIDTH, FRAME_HEIGHT);
		this.setLayout(null);				// swing layouts are so hard to adjust
		this.setResizable(false);
		
		// add in traders (other players) and host (curPlayer)
		Trader host = new Trader(curPlayer, game.tiles);
		host.setBounds((this.getWidth() - host.width)/2, this.getHeight() - 500, host.width, host.height);
		this.add(host);
		
		// first trading button for the current player
		if(otherPlayers.size() > 0){
			Trader guest1 = new Trader(otherPlayers.get(0), game.tiles);
			guest1.setBounds(50, 50, guest1.width, guest1.height);
			this.add(guest1);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_1 = initButton("<html>Give Selected to <br> <center>" + otherPlayers.get(0).name + "</center></html>",
					100, DEFAULT_BUTTON_Y + 300, BUTTON_WIDTH, BUTTON_HEIGHT);
					
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
			tradeWithHostButton_1 = initButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(0).name + "</center></html>",
					100, DEFAULT_BUTTON_Y - 300, BUTTON_WIDTH, BUTTON_HEIGHT + 50);
					
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
			payHostButton_1 = initButton("<html><center>Pay <br>" + host.getPlayer().getName() + "</center></html>", 
					tradeWithHostButton_1.getX() + (BUTTON_WIDTH + 50), tradeWithHostButton_1.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
			payHostButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        makePayment(guest1, host);
			    }
			});
			this.add(payHostButton_1);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payPlayerButton_1 = initButton("<html><center>Pay <br>" + guest1.getPlayer().getName() + "</center></html>", 
					tradeWithPlayerButton_1.getX() + (BUTTON_WIDTH + 50), tradeWithPlayerButton_1.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
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
			Trader guest2 = new Trader(otherPlayers.get(1), game.tiles);
			guest2.setBounds(50 + 500 + (BUTTON_WIDTH + 50), 50, guest2.width, guest2.height);
			this.add(guest2);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_2 = initButton("<html>Give Selected to <br><center>" + otherPlayers.get(1).name + "</center></html>",
					100 + 500 + (BUTTON_WIDTH + 50), DEFAULT_BUTTON_Y + 300, BUTTON_WIDTH, BUTTON_HEIGHT);

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
			tradeWithHostButton_2 = initButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(1).name + "</center></html>",
					100 + 500 + (BUTTON_WIDTH + 50), DEFAULT_BUTTON_Y - 300, BUTTON_WIDTH, BUTTON_HEIGHT + 50);

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
			payHostButton_2 = initButton("<html><center>Pay <br>" + host.getPlayer().getName() + "</center></html>", 
					tradeWithHostButton_2.getX() + (BUTTON_WIDTH + 50), tradeWithHostButton_2.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
			payHostButton_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					makePayment(guest2, host);
				}
			});
			
			this.add(payHostButton_2);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payPlayerButton_2 = initButton("<html><center>Pay <br>" + guest2.getPlayer().getName() + "</center></html>", 
					tradeWithPlayerButton_2.getX() + (BUTTON_WIDTH + 50), tradeWithPlayerButton_2.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
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
			Trader guest3 = new Trader(otherPlayers.get(2), game.tiles);	
			guest3.setBounds(50 + 1000 + (BUTTON_WIDTH + 50)*2, 50, guest3.width, guest3.height);
			this.add(guest3);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_3 = initButton("<html>Give Selected to <br><center>" + otherPlayers.get(2).name + "</center></html>",
					100 + 1000 + (BUTTON_WIDTH + 50)*2, DEFAULT_BUTTON_Y + 300, BUTTON_WIDTH, BUTTON_HEIGHT);
			
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
			tradeWithHostButton_3 = initButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(2).name + "</center></html>",
					100 + 1000 + (BUTTON_WIDTH + 50)*2, DEFAULT_BUTTON_Y - 300, BUTTON_WIDTH, BUTTON_HEIGHT + 50);
					
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
			payHostButton_3 = initButton("<html><center>Pay <br>" + host.getPlayer().getName() + "</center></html>", 
				tradeWithHostButton_3.getX() + (BUTTON_WIDTH + 50), tradeWithHostButton_3.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
			payHostButton_3.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					makePayment(guest3, host);
				}
			});
						
			this.add(payHostButton_3);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing payments from the other players to current player
			payPlayerButton_3 = initButton("<html><center>Pay <br>" + guest3.getPlayer().getName() + "</center></html>", 
					tradeWithPlayerButton_3.getX() + (BUTTON_WIDTH + 50), tradeWithPlayerButton_3.getY(), BUTTON_WIDTH, BUTTON_HEIGHT);
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
	
	// initializes general settings of a button and returns it
	private JButton initButton(String buttonTitle, int buttonX, int buttonY, int buttonWidth, int buttonHeight){
		JButton aButton = new JButton(buttonTitle);
		aButton.setBounds(buttonX, buttonY, buttonWidth, buttonHeight);
		aButton.setFont(BUTTON_FONT);
		return aButton;
		
	}

}
