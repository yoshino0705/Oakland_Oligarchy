import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BoardData {
	private static ArrayList<PropertyTile> highPriorityTiles;
	private static ArrayList<PropertyTile> secondaryPriorityTiles;
	private ArrayList<Integer> landedOn;
	private ArrayList<Tile> allTile;
	private OaklandOligarchy theGame;
	
	public BoardData(OaklandOligarchy game) {
		landedOn = new ArrayList<Integer>();
		theGame = game;
		allTile = new ArrayList<Tile>();
		for(int i = 0; i < GameBoard.TILE_COUNT; i++) {
			landedOn.add(i, 0);
			allTile.add(theGame.tiles.getTile(i));
			
		}
		
		highPriorityTiles = new ArrayList<PropertyTile>();
		// pre-recorded data
		
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(15));
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(16));
		
		// 61 bus
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(22));
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(31));
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(13));
		highPriorityTiles.add((PropertyTile) game.tiles.getTile(4));
		
		secondaryPriorityTiles = new ArrayList<PropertyTile>();
		// pre-recorded data
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(8));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(9));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(11));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(25));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(27));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(28));
		secondaryPriorityTiles.add((PropertyTile) game.tiles.getTile(30));
	}
	
	public void addLandOnTile(int propPos) {
		int oldVal = landedOn.get(propPos);
		landedOn.set(propPos, oldVal + 1);
		save();
		
	}
	
	private void save() {
		try {
			BufferedWriter out = new BufferedWriter(new FileWriter(new File("Statistics.txt")));
			String str = "";
			
			for(int i = 0; i < GameBoard.TILE_COUNT; i++) {
				str += allTile.get(i).getTileName() + " " + landedOn.get(i) + "\n";
				
			}
			
			out.write(str);
			out.close();
			
		} catch (IOException e) {
			//e.printStackTrace();
		}
		
		
	}
	
	public static boolean isHighPriorityTile(Tile prop) {
		if(prop.isProperty() == false)
			return false;
		
		for(int i = 0; i < highPriorityTiles.size(); i++) {
			if(prop.getTileName().equalsIgnoreCase(highPriorityTiles.get(i).getTileName()))
				return true;
			
		}
		
		return false;
	}
	
	public static boolean isSecondaryPriorityTile(Tile prop) {
		if(prop.isProperty() == false)
			return false;
		
		for(int i = 0; i < secondaryPriorityTiles.size(); i++) {
			if(prop.getTileName().equalsIgnoreCase(secondaryPriorityTiles.get(i).getTileName()))
				return true;
			
		}
		
		return false;
	}
	
	
}
