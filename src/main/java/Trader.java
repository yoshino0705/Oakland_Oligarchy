import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class Trader extends JPanel{
	public final int width = 500;
	public final int height = 400;
	private final int cellWidth = 200;
	private final int cellHeight = 50;
	private final int contentX = 50;
	
	private JList<Object> propertyOwned;
	private JLabel balance;
	private JLabel playerName;
	private final int TILE_COUNT = 36;
	
	public Trader(Player p, ImplementTiles it){		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setLayout(null);
		
		// class variables settings
		playerName = new JLabel(p.getName());
		playerName.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		balance = new JLabel("$ " + Integer.toString(p.getMoney()));
		balance.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		
		// initialize propertyOwned content
		ArrayList<String> prop = new ArrayList<String>();
		for(int i = 0; i < TILE_COUNT; i++){
			if(it.getTile(i).isProperty() && ((PropertyTile)it.getTile(i)).isOwned())
				if(it.getTile(i).getOwner() == p)
					prop.add(it.getTile(i).getTileName());
			
		}
		
		// initialize JList
		try{
			propertyOwned = new JList<Object>(prop.toArray());
			
		}catch(Exception e){
			propertyOwned = new JList<Object>();
			
		}
		
		// propertyOwned boundaries settings
		propertyOwned.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		propertyOwned.setVisibleRowCount(5);
		propertyOwned.setAutoscrolls(true);
		propertyOwned.setFixedCellWidth(this.cellWidth);
		propertyOwned.setFixedCellHeight(this.cellHeight);
		
		JScrollPane scrollPane = new JScrollPane(propertyOwned);
		scrollPane.setMaximumSize(new Dimension(300, 250));
		scrollPane.setMinimumSize (new Dimension (300, 250));
		scrollPane.setAlignmentX(LEFT_ALIGNMENT);
		
		// add to the panel
		playerName.setBounds(this.contentX, 10, this.width, playerName.getFont().getSize()*2);
		this.add(playerName);
		
		balance.setBounds(this.contentX, 80, this.width, balance.getFont().getSize()*2);
		this.add(balance);
		
		scrollPane.setBounds(this.contentX, 150, 300, 250);
		this.add(scrollPane);
		
	}
	
}
