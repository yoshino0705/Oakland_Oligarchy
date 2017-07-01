import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy implements MouseMotionListener{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	//JLabel currentTurnPlayerLabel;
	
	ImplementTiles tiles;
	// in order to show message while hovering on board, GameBoard gb has to be a class object
	private GameBoard gb;

	int numPlayers = 0; // keep track of how many players there are
	int numTurns = 0; // keep track of how many turns to mod with the all_players array
	private Player currentTurnPlayer = null; // the player whos turn it is
	private InfoPanel info;
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
			Player p = new Player(sm.getPlayerName(i), 1000, i);
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

		tiles = new ImplementTiles();

		//add info to the left side of the frame
		info = new InfoPanel(allPlayers, tiles);
		window.add(info, BorderLayout.WEST);

		gb = new GameBoard(0, 0, .63, .63);
		gb.addMouseMotionListener(this);

		// add players to gameboard to start
		for (int i = 0; i < numPlayers; i++) {
			gb.movePlayer(i, 0);
		}
		window.add(gb, BorderLayout.CENTER);
		window.setVisible(true);

		// initialize tiles
		tiles = new ImplementTiles();

	}

	// An Oakland Oligarchy constructor used for testing purposes
	OaklandOligarchy(String s) {
		if (s.equals("test")) {
			for (int i = 0; i < 4; i++) {
				Player p = new Player("p" + i, 1000, i);
				allPlayers.add(p);
				numPlayers++;
			}	

			//initial globals
			tiles = new ImplementTiles();
			info = new InfoPanel(allPlayers, tiles);
			gb = new GameBoard(0, 0, .63, .63);
			currentTurnPlayer = allPlayers.get(getIndexCurrentTurnPlayer());
		} else throw new IllegalArgumentException("Incorrect usage of Oakland Oligarchy class.");
	}
	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: getIndexCurrentTurnPlayer
	~	Parameters: None														~
		Returns: int value representing player who's turn it is											~				
	~	Description: Calculates which player's turn it currently is 			~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public int getIndexCurrentTurnPlayer() {
		if (numTurns == 0) {
			return 0;
		} else {
			return (numTurns % numPlayers);
		}
	}

	public GameBoard getGameBoard() {
		return this.gb;
	}

	public Player getCurrentTurnPlayer() {
		return this.currentTurnPlayer;
	}

	public void setCurrentTurnPlayer(int index) {
		this.currentTurnPlayer = allPlayers.get(index);
	}

	public void refreshInfoPanel() {
		info.refresh(this.allPlayers, this.tiles);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		//System.out.println("MouseX: " + e.getX() + " MouseY: " + e.getY() + " TileID: " + gb.getTileID(e.getX(), e.getY()));
		gb.showTileDetails(e.getX(), e.getY(), 20, tiles);
		
	}



}//end of class OaklandOligarchy
