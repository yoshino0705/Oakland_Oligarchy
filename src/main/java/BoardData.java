import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class BoardData {
	private static ArrayList<PropertyTile> highPriorityTiles;
	private static ArrayList<PropertyTile> secondaryPriorityTiles;
	private static ArrayList<Integer> landedOn;
	private static ArrayList<Double> chanceOfLanding;
	private static ArrayList<Tile> allTile;
	private OaklandOligarchy theGame;
	
	// initializations
	public BoardData(OaklandOligarchy game) {
		landedOn = new ArrayList<Integer>();
		theGame = game;
		allTile = new ArrayList<Tile>();
		chanceOfLanding = new ArrayList<Double>();
		for(int i = 0; i < GameBoard.TILE_COUNT; i++) {
			landedOn.add(0);
			allTile.add(theGame.tiles.getTile(i));
			chanceOfLanding.add(0.0);
			
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
	
	// get the index of a tile
	private static int getTileIndex(Tile tile) {
		for(int i = 0; i < allTile.size(); i++) {
			if(tile.getTileName().equalsIgnoreCase(allTile.get(i).getTileName()))
				return i;
			
		}
		
		return -1;		// not found
		
	}
	
	// calculate the chance of landing on a certain property
	private static void calculateProbability() {
		int totalCount = 0;
		for(int count : landedOn)
			totalCount += count;
		for(int i = 0; i < landedOn.size(); i++)
			chanceOfLanding.set(i, (double) (landedOn.get(i) * 1.0 / totalCount));
		
	}
	
	// update the two arraylists, highPriorityTiles and secondaryPriorityTiles
	private static void updatePriorityList() {
		highPriorityTiles = new ArrayList<PropertyTile>();
		secondaryPriorityTiles = new ArrayList<PropertyTile>();
		Double chance = 0.0;
		PropertyTile prop = null;
		Tile tile = null;
		
		// 61 bus
		highPriorityTiles.add((PropertyTile) allTile.get(22));
		highPriorityTiles.add((PropertyTile) allTile.get(31));
		highPriorityTiles.add((PropertyTile) allTile.get(13));
		highPriorityTiles.add((PropertyTile) allTile.get(4));
		
		for(int i = 0; i < chanceOfLanding.size(); i++) {
			chance = chanceOfLanding.get(i);
			tile = allTile.get(i);
			
			if(tile.isProperty() == true)
				prop = (PropertyTile) tile;
			else
				continue;
			
			if(chance >= 0.75)
				highPriorityTiles.add(prop);
			else if(chance < 0.75 && chance > 0.50)
				secondaryPriorityTiles.add(prop);
			
		}	
		
	}
	
	//	add one to the calculation of chances of landing on a property
	public static void addLandOnTile(Tile currentTile) {
		if(currentTile.isProperty() == false)
			return;			// not property
		
		int tileIndex = getTileIndex(currentTile);
		int oldVal = landedOn.get(tileIndex);
		landedOn.set(tileIndex, oldVal + 1);
		
		calculateProbability();
		updatePriorityList();
		
	}
	
	// check if a tile is highest priority
	public static boolean isHighPriorityTile(Tile prop) {
		if(prop.isProperty() == false)
			return false;
		
		for(int i = 0; i < highPriorityTiles.size(); i++) {
			if(prop.getTileName().equalsIgnoreCase(highPriorityTiles.get(i).getTileName()))
				return true;
			
		}
		
		return false;
	}
	
	// check if a tile is secondary priority
	public static boolean isSecondaryPriorityTile(Tile prop) {
		if(prop.isProperty() == false)
			return false;
		
		for(int i = 0; i < secondaryPriorityTiles.size(); i++) {
			if(prop.getTileName().equalsIgnoreCase(secondaryPriorityTiles.get(i).getTileName()))
				return true;
			
		}
		
		return false;
	}
	
	// print out the chance of landing on each property tiles
	public static void printChanceOfLanding() {
		int count = chanceOfLanding.size();
		System.out.println("[Index] ");
		
		for(int i = 0; i < count; i++)
			System.out.print(i + "\t");
		
		System.out.println("\n [Chance] ");
		
		for(int i = 0; i < count; i++)
			System.out.print(chanceOfLanding.get(i) + "\t");
		
		System.out.println();
	}
	
}
