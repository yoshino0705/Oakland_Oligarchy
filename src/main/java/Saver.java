import javax.swing.*;
import java.io.*;
public class Saver{

	//constructor will take in the game and save all necessary info
	Saver(OaklandOligarchy game){
		//open file chooser to let them select a save file
		JFileChooser fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		int rVal = fc.showSaveDialog(null);

		//if file was chosen then save to file
		if(rVal == JFileChooser.APPROVE_OPTION){
			System.out.println("writing file: " + fc.getSelectedFile().getName());

			try {
				File saveFile = fc.getSelectedFile();
				BufferedWriter out = new BufferedWriter(new FileWriter(saveFile));
				//build the string to save to file
				String str = "";
				str += game.getInfoPanel().info_clock.getTime() + "\n";//write the time of the game
				str += game.getCurrentTurnPlayer().getName() + "\n";//write the name of the current turn player
				//write whether roll button is enabled
				str += game.getTopMenu().rollButton.isEnabled() + "\n";
				//write whether end turn button is enabled
				str += game.getTopMenu().endTurn.isEnabled() + "\n";
				int num_players = game.allPlayers.size();
				str += num_players + "\n";//write num players
				//write each players data and the properties that they own
				for(int i = 0; i < num_players; i++){
					Player p = game.allPlayers.get(i);
					str += p.getName()+"\n";//write player name
					str += p.getMoney()+"\n";//write player money
					str += Boolean.toString(p.hasLost()) + "\n";//write if player has lost game already
					str += p.getPosition() + "\n";
					//write the players number of owned properties and then list them
					str += getPlayerProperties(game, p);
				}//end for loop

				str = cipher(str, 3);
				//write string to file
				out.write(str);
				//done writing, close the file
				out.close();
			} catch(Exception e){
			//	e.printStackTrace();
				System.out.println("Error writing to file.");
			}

		}else{//no file was chosen
			System.out.println("no file was chosen");
		}


	}//end of constructor

	// for testing
	Saver() {

	}

	//return string with number of owned properties and then the list of them combined with their mortgage status
	public static String getPlayerProperties(OaklandOligarchy game, Player p){
		int num_props = 0;//keep track of number of properties owned by player (needed for reading in)
		String props = "";
		String ret_str = "";
		for(int i = 0; i < 36; i++){
			Player player = game.tiles.getTile(i).getOwner();
			if(player == p){//if this tile is owned by the player
				num_props++;
				props += game.tiles.getTile(i).getTileName() + ":" + game.tiles.getTile(i).isMortgaged() + "\n";
			}
		}//end for
		ret_str = num_props + "\n" + props;

		return ret_str;
	}//and of getPlayerProperties()



	//testing purposes
	public static void main(String[] args) {

	}

	



	
	public static String cipher(String msg, int shift){
		String s = "";
		int len = msg.length();
		for(int x = 0; x < len; x++){
			char c = (char)(msg.charAt(x) + shift);
			if (c > 'z')
				s += (char)(msg.charAt(x) - (26-shift));
			else
				s += (char)(msg.charAt(x) + shift);
		}
		return s;
	}

}//end class Saver
