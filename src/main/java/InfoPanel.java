import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel{
	ArrayList<JLabel> all_labels = new ArrayList<>();
	InfoPanel(ArrayList<Player> all_players){
		this.setLayout(new GridLayout(100,1));
		for(Player p: all_players){
			JLabel player_name = new JLabel(p.getName());
			all_labels.add(player_name);
			JLabel player_money = new JLabel("Money: "+ p.getMoney());
			all_labels.add(player_money);
			//properties will eventually be made by a method which finds all the owned props
			JLabel properties = new JLabel("None");
			all_labels.add(properties);
			
		}
		//unowned props will eventually be a method which finds all of them
		//this JPanel will need gto be scrollable for this feature
		//JLabel unowned_props = new JLabel("");
		
		//add all JLabels to the JPanel
		this.setPreferredSize(new Dimension(200,1000));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		for(JLabel l: all_labels){
			this.add(l);
		}
	}//end of constructor



	//this method will refresh the info
	public void refresh(){

	}



}//end of class InfoPanel
