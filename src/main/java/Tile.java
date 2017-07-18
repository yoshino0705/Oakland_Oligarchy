import java.awt.Color;

public class Tile{

    //private - change if necessary
    private String tileName;
	private Color color;
    private boolean isProperty = false;
	//this method stub is needed to call this on an array of all types of tiles
	//it will be overridden in the proprties tile class
	public Player getOwner(){return null;};
	public boolean setOwnership(Player p){return false;};//will override in PropertyTile
	public void removeOwnership(){};//will override in PopertyTile
	public boolean isMortgaged(){return false;};//will override in PropertyTile
	public int setMortgage(boolean b){return -1;};

    Tile(){
        this.tileName = "";
    }

	public void setColor(Color newColor){
		this.color = newColor;
	}
	public Color getColor(){
		return this.color;
	}
    public void setTileName(String newName){
        this.tileName = newName;
    }
    public String getTileName(){
        return this.tileName;
    }

    public boolean isProperty() {
        return this.isProperty;
    }
    public void setProperty(boolean t) {
        this.isProperty = t;
    }
}
