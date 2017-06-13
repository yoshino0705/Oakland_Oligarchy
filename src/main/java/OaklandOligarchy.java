import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	//JLabel currentTurnPlayerLabel;
	

	int numPlayers = 0;//keep track of how many players there are
	int numTurns = 0;//keep track of how many turns to mod with the all_players array
	Player currentTurnPlayer = null;//the player whos turn it is
	GameBoard gb;
	ImplementTiles tiles;
	InfoPanel info;

	//list of all the players in this game
	ArrayList<Player> allPlayers = new ArrayList<Player>();
	public static void main(String[] args){
		new OaklandOligarchy();
	}//end of main method

	//constructor will start the gui
	OaklandOligarchy(){
		StartMenu sm = new StartMenu();

		// load players from StartMenu into allPlayers array
		for (int i = 0; i < sm.getPlayerCount(); i++) {
			Player p = new Player(sm.getPlayerName(i), 1000);
			allPlayers.add(p);
			numPlayers++;
		}

		// set window for actual game
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		//add top menu
		currentTurnPlayer = allPlayers.get(getIndexCurrentTurnPlayer());
		TopMenu tm = new TopMenu(this);
		window.add(tm, BorderLayout.NORTH);
		//add info to the left side of the frame
		info = new InfoPanel(allPlayers);
		window.add(info, BorderLayout.WEST);

		gb = new GameBoard(0, 0, .63, .63);
		//gb.movePlayer(0, 19);
		window.add(gb, BorderLayout.CENTER);
		window.setVisible(true);

		// initialize tiles
		tiles = new ImplementTiles();

/*	*	*	*	*	*	*	*	*	*	*	*	*		
 *	*	THE CODE BELOW ILLUSTRATES ISSUE #2 *	*	
 *	*	*	*	*	*	*	*	*	*	*	*	*	
		int i = 0;
		while (true) {
			gb.movePlayer(0, i % 36);
			i++;
			gb.refreshBoard();
			try {
				Thread.sleep(100);
			} catch (Exception e) {
				System.out.println("");
			}
		}
/*
*	*	*	*	*	*	*	*	*	*	*	*	*/
	}//end of OaklandOligarchy constructor

	//get index of current turn player to use in all_players
	int getIndexCurrentTurnPlayer(){

		System.out.println("OO.numTurns: " + numTurns);
		System.out.println("OO.numPlayers: " + numPlayers);

		if(numTurns == 0){
			return 0;
		}else{
			return (numTurns % numPlayers);
		}
	}


}//end of class OaklandOligarchy
