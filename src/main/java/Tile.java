

public class Tile{

    //private - change if necessary
    private String tileName;

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
