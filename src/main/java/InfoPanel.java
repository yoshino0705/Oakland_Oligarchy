import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class InfoPanel extends JPanel{
	JLabel infoLabel = new JLabel("", SwingConstants.LEFT);//this is the label with all of the info

	//constructor for InfoPanel class
	InfoPanel(ArrayList<Player> all_players, ImplementTiles tiles){
		
		refresh(all_players, tiles);

		infoLabel.setFont(new Font("Times", Font.PLAIN, 14));

		infoLabel.setHorizontalTextPosition(SwingConstants.LEFT);
		infoLabel.setHorizontalAlignment(SwingConstants.LEFT);
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.add(infoLabel); 
		//set formatting for the panel
		this.setPreferredSize(new Dimension(200,1000));
		this.setBorder(BorderFactory.createLineBorder(Color.black));
	}//end of constructor

	/*This method will refresh the game info in the InfoPanel object
	 *@param all_players the arraylist containing all of the players in the game
	 *@param tiles       the tile object which has an array of all the tiles
	 *@return none
	 */
	public void refresh(ArrayList<Player> all_players, ImplementTiles tiles){
		String str = new String("");//this string will be used to build str for info JLabel
		
		final int NUM_TILES = 36;//make final int to avoid magic number, this is num tiles in tile array

		str = str + "<html><br>";//begin the opening html tag
		int player_number = 0;
		for(Player p : all_players){//loop through all the players and get their info
			str = str + "<div style='font-size:16'><b><u>" + p.getName() + "</u></b></div> <br>";//get player name
			String color = "Color: ";
			if(player_number==0){
				color = color + "Red";
			}else if(player_number==1){
				color = color + "Orange";
			}else if(player_number==2){
				color = color + "Green";
			}else if(player_number==3){
				color = color + "Blue";
			}else{
				System.out.println("Error in setting color");
			}
			str = str + color + "<br>";
			str = str + "Money: " + p.getMoney() + " <br>";//get the money
			str = str + "Properties: <br>";//now list all properties owned
			
			int num_props = 0;//keep track of number of owned props
			for(int i = 0; i < NUM_TILES; i++){//loop through all props and find ones that this player owns
				if(tiles.getTile(i).getOwner() == p){
					str = str + tiles.getTile(i).getTileName() + "<br>";
					num_props++;
				}
			}
			//if there are no owned properties write "None"
			if(num_props == 0){
				str = str + "None";
			}
			
			str = str + "<br><br>";//extra break for spacing between players
			player_number++;//increment as we switch to next player
		}
		

		str = str + "</html>";//close to html tag

		//set the new JLabel text to be the new info
		infoLabel.setText(str);

	}//end of refresh



}//end of class InfoPanel
