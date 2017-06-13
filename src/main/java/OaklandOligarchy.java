import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	//JLabel currentTurnPlayerLabel;
	

	int numPlayers = 0; // keep track of how many players there are
	int numTurns = 0; // keep track of how many turns to mod with the all_players array
	Player currentTurnPlayer = null; // the player whos turn it is
	GameBoard gb;
	ImplementTiles tiles;
	InfoPanel info;
	ArrayList<Player> allPlayers = new ArrayList<Player>(); // list of all the players in this game
	
	public static void main(String[] args){
		new OaklandOligarchy();
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: OaklandOligarchy (constructor)
	~	Parameters: None														~
		Returns: None																			
	~	Description: Launches start menu. Sets up main GUI window and adds 		~
					different game components to said window.					
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
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
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: getIndexCurrentTurnPlayer
	~	Parameters: None														~
		Returns: int value representing player who's turn it is											~				
	~	Description: Calculates which player's turn it currently is 			~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	int getIndexCurrentTurnPlayer() {
		if (numTurns == 0) {
			return 0;
		} else {
			return (numTurns % numPlayers);
		}
	}


}//end of class OaklandOligarchy
