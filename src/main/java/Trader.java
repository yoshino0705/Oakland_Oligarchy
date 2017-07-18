import java.awt.Dimension;
import java.awt.Font;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;

public class Trader extends JPanel{
	public int width = 500;
	public int height = 400;
	private int cellWidth = 200;
	private int cellHeight = 50;
	private int contentX = 50;
	
	private double scaleX = 1;
	private double scaleY = 1;
	
	protected JList<Object> propertyOwned;
	protected JLabel balance;
	private JLabel playerName;
	private Font defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, 30);
	
	private Player player;
	private DefaultListModel<String> listModel;
	
	public Trader(Player p, ImplementTiles it, double newScaleX, double newScaleY){		
		// assign player
		player = p;
		
		// set and apply scales
		this.scaleX = newScaleX;
		this.scaleY = newScaleY;
		this.width *= this.scaleX;
		this.height *= this.scaleY;
		this.cellWidth *= this.scaleX;
		this.cellHeight *= this.scaleY;
		this.contentX *= this.scaleX;
		this.defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, (int)(30 * this.scaleX));
		
		//this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		this.setPreferredSize(new Dimension((int)(this.width), (int)(this.height)));
		//this.setLayout(null);
		
		// class variables settings
		playerName = new JLabel(p.getName());
		playerName.setFont(defaultButtonFont);
		balance = new JLabel("$ " + Integer.toString(p.getMoney()));
		balance.setFont(defaultButtonFont);
		
		// initialize propertyOwned content
		ArrayList<String> prop = new ArrayList<String>();
		for(int i = 0; i < GameBoard.TILE_COUNT; i++){
			if(it.getTile(i).isProperty() && ((PropertyTile)it.getTile(i)).isOwned())
				if(it.getTile(i).getOwner() == p) {
					PropertyTile pt = (PropertyTile) it.getTile(i);
					
					if(pt.isMortgaged() == false)
						prop.add(pt.getTileName());
				}
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
		propertyOwned.setFont(defaultButtonFont);
		propertyOwned.setVisibleRowCount(5);
		propertyOwned.setAutoscrolls(true);
		propertyOwned.setFixedCellWidth((int)(this.cellWidth));
		propertyOwned.setFixedCellHeight((int)(this.cellHeight));
		propertyOwned.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane listScroller = new JScrollPane();
        listScroller.setViewportView(propertyOwned);
        propertyOwned.setLayoutOrientation(JList.VERTICAL);
		
		// add to the panel
		playerName.setBounds((int)(this.contentX), (int)(10 * this.scaleY), (int)(this.width * this.scaleX), (int)(playerName.getFont().getSize()*2 * this.scaleY));
		this.add(playerName);
		
		balance.setBounds((int)(this.contentX), (int)(80 * this.scaleY), (int)(this.width * this.scaleX), (int)(balance.getFont().getSize()*2 * this.scaleY));
		this.add(balance);
		
		listScroller.setBounds((int)(this.contentX), (int)(150 * this.scaleY), (int)(300 * this.scaleX), (int)(250 * this.scaleY));
		this.add(listScroller);
		
	}
	
	// does nothing
	public Trader(){
		
		
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
	
	public int getScaledWidth(double scaleX){
		return (int) (this.width * scaleX);
	}
	
	public int getScaledHeight(double scaleY){
		return (int) (this.height * scaleY);
	}


}
