import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel{
	ArrayList<JLabel> all_labels = new ArrayList<>();

	//constructor for InfoPanel class
	InfoPanel(ArrayList<Player> all_players){
		for(Player p: all_players){
			JLabel player_name = new JLabel(p.getName());
			player_name.setFont(new Font("TimesRoman", Font.BOLD, 16));
			all_labels.add(player_name);
			JLabel player_money = new JLabel("Money: "+ p.getMoney());
			all_labels.add(player_money);
			//properties will eventually be made by a method which finds all the owned props
			JLabel prop_label = new JLabel("Properties:");
			all_labels.add(prop_label);
			JLabel properties = new JLabel("None");
			all_labels.add(properties);
			//add an empty label to seperate the players
			JLabel spacer = new JLabel("");
			all_labels.add(spacer);
			
		}
		//unowned props will eventually be a method which finds all of them
		//this JPanel will need gto be scrollable for this feature
		//JLabel unowned_props = new JLabel("");
	
		//set formatting for the panel
		this.setLayout(new GridLayout(50,1));
		this.setPreferredSize(new Dimension(200,1000));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
		//add all JLabels to the JPanel
		for(JLabel l: all_labels){
			this.add(l);
		}
	}//end of constructor



	//this method will refresh the info
	//might not do this and just destroy the instance and create a new one
	public void refresh(){

	}



}//end of class InfoPanel
