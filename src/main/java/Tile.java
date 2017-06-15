

public class Tile{

    //private - change if necessary
    private String tileName;
    private boolean isProperty = false;

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
    public boolean isProperty() {
        return this.isProperty;
    }
    public void setProperty(boolean t) {
        this.isProperty = t;
    }
}
