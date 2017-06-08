
//representing an action tile on the gameboard
//actions can be positive or negative outcomes (hence conflicting attributes types)
public class ActionTile extends Tile{

    private String tileInfo;

    ActionTile(String description){
        this.tileInfo = description;
    }
}
