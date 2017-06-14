import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	//JLabel currentTurnPlayerLabel;
	
	ImplementTiles tiles;

	int numPlayers = 0;//keep track of how many players there are
	int numTurns = 0;//keep track of how many turns to mod with the all_players array
	Player currentTurnPlayer = null;//the player whos turn it is

	//list of all the players in this game
	ArrayList<Player> all_players;
	public static void main(String[] args){
		new OaklandOligarchy();
	}//end of main method

	//constructor will start the gui
	OaklandOligarchy(){
		StartFrame sf = new StartFrame();
		while(!sf.isDone()){
			//System.out.println("waiting on startup");
			//wait for startup to finish
			try {//this is needed to wait on this variable
				Thread.sleep(10);
			} catch(Exception e){
				e.printStackTrace();
			}
		}
		//System.out.println("startup finished");
		sf.doneWithStartFrame();
		all_players = sf.getAllPlayersArrayList();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		//add top menu
		currentTurnPlayer = all_players.get(getIndexCurrentTurnPlayer());
		TopMenu tm = new TopMenu(currentTurnPlayer);
		window.add(tm, BorderLayout.NORTH);

		tiles = new ImplementTiles();

		//add info to the left side of the frame
		InfoPanel info = new InfoPanel(all_players, tiles);
		window.add(info, BorderLayout.WEST);

		GameBoard gb = new GameBoard(0, 0, .63, .63);
		gb.movePlayer(1, 23);	// for skeleton purposes
		gb.movePlayer(2, 14);	// for skeleton purposes
		window.add(gb, BorderLayout.CENTER);
		window.setVisible(true);
	}//end of OaklandOligarchy constructor

	//get index of current turn player to use in all_players
	int getIndexCurrentTurnPlayer(){
		if(numTurns == 0){
			return 0;
		}else{
			return (numTurns % numPlayers);
		}
	}


}//end of class OaklandOligarchy
