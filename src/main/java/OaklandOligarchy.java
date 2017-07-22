import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.io.*;
import java.util.*;

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

//	private final int TILE_COUNT = 36;

	public static void main(String[] args){
		NewOrLoad new_or_load = new NewOrLoad();
		boolean isLoadedGame = new_or_load.getIsLoadedGame();
		if(isLoadedGame){//if game is being loaded from file
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
				System.out.println("----Error: No file selected, aborting----");
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
		Player.setPlayerGame(this);
		StartMenu sm = new StartMenu();

		// load players from StartMenu into allPlayers array
		for (int i = 0; i < sm.getPlayerCount(); i++) {
			Player p = new Player(sm.getPlayerName(i), 1000, i, false);
			if(p.getName().equalsIgnoreCase("AI")) {				
				p = new AI("AI " + AI.AI_Count++, 1000, i, false);
			}
			
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

		gb = new GameBoard(0, 0, .63, .63, this);
		gb.addMouseMotionListener(this);

		for(int i = 0; i < allPlayers.size(); i++) {
			if(allPlayers.get(i).getName().contains("LABOON")) {
				gb.enableEasterEgg();
				break;

			}
		}


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
		Player.setPlayerGame(this);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);
		//make tiles for the game
		tiles = new ImplementTiles();
		//make gameboard
		gb = new GameBoard(0, 0, .63, .63, this);
		gb.addMouseMotionListener(this);

		try {
			Scanner scan = new Scanner(file);//set scanner to read from save file
			//read time from file
			String time_str = scan.nextLine();
			String[] split_time = time_str.split(":");
			int hours = Integer.parseInt(split_time[0]);
			int minutes = Integer.parseInt(split_time[1]);
			int seconds = Integer.parseInt(split_time[2]);
			//get the name of current turn player
			String curTurnPlayerName = scan.nextLine();
			//System.out.println("current turn: "+curTurnPlayerName);
			//get whether roll button is enabled
			boolean roll_button_boolean = Boolean.parseBoolean(scan.nextLine());
			//get whether end turn button is enabled
			boolean end_button_boolean = Boolean.parseBoolean(scan.nextLine());
			int num_players = Integer.parseInt(scan.nextLine());
			//System.out.println("num players:"+num_players);
			//loop through and get info to create each player and their properties
			for(int i = 0; i < num_players; i++){
				String pname = scan.nextLine();//get player name
				//System.out.println(pname);
				int pmoney = Integer.parseInt(scan.nextLine());//get player money
				//System.out.println(pmoney);
				boolean plost = Boolean.parseBoolean(scan.nextLine());//get if the player has lost
				//System.out.println(plost);
				int position = Integer.parseInt(scan.nextLine());//get player position
				int num_props = Integer.parseInt(scan.nextLine());//get number of owned properties
				//System.out.println(num_props);
				String[] prop_names = new String[num_props];
				String[] prop_bools = new String[num_props];

				//get list of all the owned properties and their mortgage status
				for(int j = 0; j < num_props; j++){
					String prop_str = scan.nextLine();
					String[] split_prop = prop_str.split(":");
					String prop_name = split_prop[0];
					String prop_bool = split_prop[1];
					//System.out.println(prop_name);
					prop_names[j] = prop_name;
					prop_bools[j] = prop_bool;
				}//end for j
				
				boolean isAIValue = Boolean.parseBoolean(scan.nextLine());
				Player thePlayer;
				
				if(isAIValue == false) {
					thePlayer = new Player(pname, pmoney, i, plost, position);
					thePlayer.isAI = false;
				}else {
					thePlayer = new AI(pname, pmoney, i, plost, position);
					thePlayer.isAI = true;
					
				}

				if(pname.equals(curTurnPlayerName)){//if this player is the current turn player
					currentTurnPlayer = thePlayer;
					numTurns = i;
					//System.out.println("found equal name");
				}
				allPlayers.add(thePlayer);
				numPlayers++;
				//set the properties owned by this player
				for(int k = 0; k < 36; k++){//loop through all 36 tiles
					Tile t = tiles.getTile(k);
					for(int h = 0; h < num_props; h++){//loop though all props owned by player
						if(t.getTileName().equals(prop_names[h])){//if tile is owned then set ownership
							t.setOwnership(thePlayer);
							thePlayer.addProperty((PropertyTile) t);
							t.setMortgage(Boolean.parseBoolean(prop_bools[h]));
						}//end if
					}//end for h
				}//end for k
				gb.movePlayer(i, position);
			}//and for i

			//add top menu
			tm = new TopMenu(this);
			tm.rollButton.setEnabled(roll_button_boolean);
			tm.endTurn.setEnabled(end_button_boolean);
			window.add(tm, BorderLayout.NORTH);

			info = new InfoPanel(allPlayers, tiles, hours, minutes, seconds);
			window.add(info, BorderLayout.WEST);

			window.add(gb, BorderLayout.CENTER);
			window.setVisible(true);

		} catch(Exception e){
		//	e.printStackTrace();
			System.out.println("----Error reading game info from file, not in proper format----");
		}
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
			gb = new GameBoard(0, 0, .63, .63, this);
			currentTurnPlayer = allPlayers.get(getIndexCurrentTurnPlayer());
			tm = new TopMenu(this);

			// give player0 some properties for testing
			Player p = allPlayers.get(0);
			PropertyTile prop = (PropertyTile) tiles.getTile(1);
			prop.setOwnership(p);
			p.addProperty(prop);
			prop = (PropertyTile) tiles.getTile(3);
			prop.setOwnership(p);
			p.addProperty(prop);
			// give player1 some properties for testing
			p = allPlayers.get(1);
			prop = (PropertyTile) tiles.getTile(2);
			prop.setOwnership(p);
			p.addProperty(prop);
			prop = (PropertyTile) tiles.getTile(4);
			prop.setOwnership(p);
			p.addProperty(prop);
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
		for (int i = 0; i < GameBoard.TILE_COUNT; i++) {
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

		// remove token from board//change from current to p
		gb.movePlayer(getPlayerIndex(p), -1);

		// update player name
		p.setName(p.getName() + " - LOSER");
		p.lose();

		endTurn();
		refreshInfoPanel();
	}

	public int getPlayerIndex(Player p){
		int ret_index = 0;
		for(Player pl : allPlayers){
			if(pl == p){
				return ret_index;
			}
			ret_index++;
		}
		return -1;
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
		
		if(currentTurnPlayer.isAI) {
			AI ai = (AI) currentTurnPlayer;
			ai.processTheTurn(this);

		}
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: getOwnedProperties
	~	Parameters: Player who's owned properties we wish to find				~
		Returns: list of properties owned by given player
	~	Description: generates list of properties owned by a given player 		~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public ArrayList<PropertyTile> getOwnedProperties(Player p) {
		ArrayList<PropertyTile> retList = new ArrayList<PropertyTile>();
		for (int i = 0; i < GameBoard.TILE_COUNT; i++) {
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

	public TopMenu getTopMenu() {
		return this.tm;
	}

	public void animatedMovePlayer(int playerNum, int startPos, int roll) {
		gb.animatedMovePlayer(playerNum, startPos, roll);
	}

	@Override
	public void mouseDragged(MouseEvent arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		gb.showTileDetails(e.getX(), e.getY(), 20, tiles);


	}

}//end of class OaklandOligarchy
