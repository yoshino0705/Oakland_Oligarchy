

public class Tile{

    //private - change if necessary
    private String tileName;

	//this method stub is needed to call this on an array of all types of tiles
	//it will be overridden in the proprties tile class
	public Player getOwner(){return null;};

    Tile(){
        this.tileName = "";
    }

    public void setTileName(String newName){
        this.tileName = newName;
    }
    public String getTileName(){
        return this.tileName;
    }
}