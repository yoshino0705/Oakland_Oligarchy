import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;

public class Trade extends JFrame{
	private JButton tradeWithPlayerButton_1;
	private JButton tradeWithPlayerButton_2;
	private JButton tradeWithPlayerButton_3;
	
	private JButton tradeWithHostButton_1;
	private JButton tradeWithHostButton_2;
	private JButton tradeWithHostButton_3;
	
	private final int buttonWidth = 300;
	private final int buttonHeight = 150;
	private final int buttonY = 800;
	private Font buttonFont = new Font("TimesNewRoman", Font.BOLD, 30);
	
	public Trade(Player curPlayer, ArrayList<Player> otherPlayers, ImplementTiles tiles){
		// error checking
		if(otherPlayers.size() > 4 || otherPlayers.size() < 1){
			System.out.println("Error in Trade Constructor: too few or too many guests (" + otherPlayers.size() + " guests)");
			System.exit(1);
		}
		
		// JFrame settings
		this.setTitle("Trade Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500, 1500);
		this.setLayout(null);
		this.setResizable(false);
		
		// add in traders (other players) and host (curPlayer)
		Trader host = new Trader(curPlayer, tiles);
		host.setBounds((this.getWidth() - host.width)/2, this.getHeight() - 500, host.width, host.height);
		this.add(host);
		
		// first trading button for the current player
		if(otherPlayers.size() > 0){
			Trader guest1 = new Trader(otherPlayers.get(0), tiles);
			guest1.setBounds(50, 50, guest1.width, guest1.height);
			this.add(guest1);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_1 = new JButton("<html>Give Selected to <br> <center>" + otherPlayers.get(0).name + "</center></html>");
			tradeWithPlayerButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest1, tiles, getSelectedPropertyIndex(host.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithPlayerButton_1.setFont(buttonFont);
			tradeWithPlayerButton_1.setBounds(100, buttonY, buttonWidth, buttonHeight);
			this.add(tradeWithPlayerButton_1);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_1 = new JButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(0).name + "</center></html>");
			tradeWithHostButton_1.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest1.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest1, host, tiles, getSelectedPropertyIndex(guest1.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithHostButton_1.setFont(buttonFont);
			tradeWithHostButton_1.setBounds(100, buttonY - 300, buttonWidth, buttonHeight + 50);
			this.add(tradeWithHostButton_1);
			
		}
		
		// second trading button for the current player
		if(otherPlayers.size() >= 2){
			Trader guest2 = new Trader(otherPlayers.get(1), tiles);
			guest2.setBounds(50 + 500, 50, guest2.width, guest2.height);
			this.add(guest2);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_2 = new JButton("<html>Give Selected to <br><center>" + otherPlayers.get(1).name + "</center></html>");
			tradeWithPlayerButton_2.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest2, tiles, getSelectedPropertyIndex(host.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithPlayerButton_2.setFont(buttonFont);
			tradeWithPlayerButton_2.setBounds(100 + 500, buttonY, buttonWidth, buttonHeight);
			this.add(tradeWithPlayerButton_2);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_2 = new JButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(1).name + "</center></html>");
			tradeWithHostButton_2.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest2.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest2, host, tiles, getSelectedPropertyIndex(guest2.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithHostButton_2.setFont(buttonFont);
			tradeWithHostButton_2.setBounds(100 + 500, buttonY - 300, buttonWidth, buttonHeight + 50);
			this.add(tradeWithHostButton_2);
			
		}
		
		// third trading button for the current player
		if(otherPlayers.size() >= 3){
			Trader guest3 = new Trader(otherPlayers.get(2), tiles);	
			guest3.setBounds(50 + 1000, 50, guest3.width, guest3.height);
			this.add(guest3);
			
			// add in buttons for doing trading
			tradeWithPlayerButton_3 = new JButton("<html>Give Selected to <br><center>" + otherPlayers.get(2).name + "</center></html>");
			tradeWithPlayerButton_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			    	if(!host.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(host, guest3, tiles, getSelectedPropertyIndex(host.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithPlayerButton_3.setFont(buttonFont);
			tradeWithPlayerButton_3.setBounds(100 + 1000, buttonY, buttonWidth, buttonHeight);
			this.add(tradeWithPlayerButton_3);
			
			// --------------------------------------------------------------------------
			// add in buttons for doing trading from the other players to current player
			tradeWithHostButton_3 = new JButton("<html>Give Selected to <br> <center>" + curPlayer.name + "<br>" + "as " + otherPlayers.get(2).name + "</center></html>");
			tradeWithHostButton_3.addActionListener(new ActionListener() {
			    @Override
			    public void actionPerformed(ActionEvent e) {
			        if(!guest3.propertyOwned.isSelectionEmpty()){
			        	// has selected something
			        	changeOwnership(guest3, host, tiles, getSelectedPropertyIndex(guest3.propertyOwned, tiles));
			        	
			        }
			    }
			});
			
			tradeWithHostButton_3.setFont(buttonFont);
			tradeWithHostButton_3.setBounds(100 + 1000, buttonY - 300, buttonWidth, buttonHeight + 50);
			this.add(tradeWithHostButton_3);
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
	private void changeOwnership(Trader originalOwner, Trader newOwner, ImplementTiles tiles, int propertyIndex){
		PropertyTile curTile = (PropertyTile)tiles.getTile(propertyIndex);
		originalOwner.removeSelectedItem();
		newOwner.addItem(curTile.getTileName());
		curTile.setOwnership(newOwner.getPlayer());
		
	}
	
	// testing main method
	public static void main(String args[]){
		Player GM = new Player("Current Player", 999999); 		// for testing purpose
		
		Player p = new Player("Yoshino", 1000); 		// for testing purpose
		Player p2 = new Player("Hibiki", 30000); 		// for testing purpose
		Player p3 = new Player("Chino", 500000); 		// for testing purpose
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);
		players.add(p2);
		players.add(p3);
		
		ImplementTiles tiles = new ImplementTiles();
		
		for(int i = 1; i < 10; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p);
			
		}
		
		for(int i = 10; i < 21; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p2);
			
		}
		
		for(int i = 21; i < 30; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p3);
			
		}
		
		for(int i = 30; i < 36; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(GM);
			
		}
		
		new Trade(GM, players, tiles);
		
	}
}
