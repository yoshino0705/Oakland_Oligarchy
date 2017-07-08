import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
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
	private TopMenu tm;
	ArrayList<Player> allPlayers = new ArrayList<Player>(); // list of all the players in this game
	
	private final int TILE_COUNT = 36;

	public static void main(String[] args){
		boolean loadedGame = true;
		if(loadedGame){//if game is being loaded from file
			JFileChooser fc = new JFileChooser();
			File workingDirectory = new File(System.getProperty("user.dir"));
			fc.setCurrentDirectory(workingDirectory);
			int rVal = fc.showOpenDialog(null);
			File file;
			if(rVal == JFileChooser.APPROVE_OPTION){
				file = fc.getSelectedFile();
				System.out.println("Loading game from file: "+fc.getSelectedFile().getName());
				new OaklandOligarchy(file);
			}else{
				System.out.println("No file selected, aborting");
				System.exit(1);
			}
		}else{//else start new game
			new OaklandOligarchy();
		}
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
			Player p = new Player(sm.getPlayerName(i), 1000, i, false);
			allPlayers.add(p);
			numPlayers++;
		}

		// set window for actual game
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		//add top menu
		currentTurnPlayer = allPlayers.get(getIndexCurrentTurnPlayer());
		tm = new TopMenu(this);
		window.add(tm, BorderLayout.NORTH);

		tiles = new ImplementTiles();

		//add info to the left side of the frame
		info = new InfoPanel(allPlayers, tiles, 0, 0, 0);
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

	//OaklandOligarchy constructor used for resuming a game from a file
	OaklandOligarchy(File file){

	}

	// An Oakland Oligarchy constructor used for testing purposes
	OaklandOligarchy(String s) {
		if (s.equals("test")) {
			for (int i = 0; i < 4; i++) {
				Player p = new Player("p" + i, 1000, i, false);
				allPlayers.add(p);
				numPlayers++;
			}	

			//initial globals
			tiles = new ImplementTiles();
			info = new InfoPanel(allPlayers, tiles, 0, 0, 0);
			gb = new GameBoard(0, 0, .63, .63);
			currentTurnPlayer = allPlayers.get(getIndexCurrentTurnPlayer());
			tm = new TopMenu(this);

			// give player0 some properties for testing
			Player p = allPlayers.get(0);
			PropertyTile prop = (PropertyTile) tiles.getTile(1);
			prop.setOwnership(p);
			prop = (PropertyTile) tiles.getTile(3);
			prop.setOwnership(p);
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

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: checkWon
	~	Parameters: None														~
		Returns: boolean indicating whether game has been won									
	~	Description: determines whether the game has been won or not 			~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public boolean checkWon() {
		int numLost = 0;
		for (int i = 0; i < allPlayers.size(); i++) {
			if (allPlayers.get(i).hasLost())
				numLost++;
		}

		if (allPlayers.size() - numLost == 1)
			return true;
		else
			return false;
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: getWinner
	~	Parameters: None														~
		Returns: player who has won the game				
	~	Description: finds the player who has won the game and returns it		~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public Player getWinner() {
		if (!checkWon())
			return null;

		for (int i = 0; i < allPlayers.size(); i++) {
			if (!allPlayers.get(i).hasLost())
				return allPlayers.get(i);
		}
		return null;
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: playerLose
	~	Parameters: player who has lost											~
		Returns: None				
	~	Description: executes logic for player who has lost game 				~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public void playerLose(Player p) {
		// remove any owned properties from player
		for (int i = 0; i < TILE_COUNT; i++) {
			PropertyTile pTile;
			Tile curTile = tiles.getTile(i);
			if (curTile.isProperty())
				pTile = (PropertyTile) curTile;
			else
				continue;

			if (pTile.isOwned() && pTile.getOwner() == p) {
				pTile.removeOwnership();
			}
		}

		// remove token from board
		gb.movePlayer(getIndexCurrentTurnPlayer(), -1);

		// update player name
		p.setName(p.getName() + " - LOSER");
		p.lose();

		endTurn();
		refreshInfoPanel();
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: endTurn
	~	Parameters: None 														~
		Returns: None				
	~	Description: executes logic to end one player's turn and begin the next	~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public void endTurn() {
		do {
			numTurns++;
			int nextTurnPlayer = getIndexCurrentTurnPlayer();
			setCurrentTurnPlayer(nextTurnPlayer);
		} while (currentTurnPlayer.hasLost());

		tm.updateCurrentTurnLabel(currentTurnPlayer);
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: getOwnedProperties
	~	Parameters: Player who's owned properties we wish to find				~
		Returns: list of properties owned by given player			
	~	Description: generates list of properties owned by a given player 		~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public ArrayList<PropertyTile> getOwnedProperties(Player p) {
		ArrayList<PropertyTile> retList = new ArrayList<PropertyTile>();
		for (int i = 0; i < TILE_COUNT; i++) {
			Tile curTile = tiles.getTile(i);	//game.getTiles().getTile(i)
			if (curTile.isProperty() && playerOwnsProperty((PropertyTile) curTile, p))
				retList.add((PropertyTile) curTile);
		}
		return retList;
	}

	public boolean playerOwnsProperty(PropertyTile prop, Player p) {
		return prop.getOwner() == p;
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
	
	public InfoPanel getInfoPanel(){
		return info;
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
