import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

public class Trader extends JPanel{
	public final int width = 500;
	public final int height = 400;
	private final int cellWidth = 200;
	private final int cellHeight = 50;
	private final int contentX = 50;
	
	protected JList<Object> propertyOwned;
	protected JLabel balance;
	private JLabel playerName;
	private final int TILE_COUNT = 36;
	
	private Player player;
	private DefaultListModel<String> listModel;
	
	public Trader(Player p, ImplementTiles it){		
		// assign player
		player = p;
		
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
		
		listModel = new DefaultListModel<String>();
		
		// initialize JList
		try{
			for(String tileName: prop){
				listModel.addElement(tileName);
				
			}
			propertyOwned = new JList(listModel);
			
		}catch(Exception e){
			System.out.println("propertyOwned somehow couldn't initialize properly");
			propertyOwned = new JList();
			
		}
		
		// propertyOwned boundaries settings
		propertyOwned.setFont(new Font("TimesNewRoman", Font.BOLD, 30));
		propertyOwned.setVisibleRowCount(5);
		propertyOwned.setAutoscrolls(true);
		propertyOwned.setFixedCellWidth(this.cellWidth);
		propertyOwned.setFixedCellHeight(this.cellHeight);
		propertyOwned.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(propertyOwned);
        propertyOwned.setLayoutOrientation(JList.VERTICAL);
		
		// add to the panel
		playerName.setBounds(this.contentX, 10, this.width, playerName.getFont().getSize()*2);
		this.add(playerName);
		
		balance.setBounds(this.contentX, 80, this.width, balance.getFont().getSize()*2);
		this.add(balance);
		
		listScroller.setBounds(this.contentX, 150, 300, 250);
		this.add(listScroller);
		
	}
	
	public Player getPlayer(){
		return player;
		
	}
	
	public void removeItem(int index){
		((DefaultListModel)propertyOwned.getModel()).remove(index);
		
	}
	
	public void removeSelectedItem(){
		((DefaultListModel)propertyOwned.getModel()).remove(propertyOwned.getSelectedIndex());
		
	}
	
	public void addItem(Object item){
		((DefaultListModel)propertyOwned.getModel()).addElement(item);
		
	}


}
