

public class Tile{

    //private - change if necessary
    private String tileName;
    private boolean isProperty = false;

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
