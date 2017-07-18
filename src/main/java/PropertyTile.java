
//property class - subclass of the Tile class
public class PropertyTile extends Tile{

    //class attributes - private for now - change if necessary
    private int value;
    private int rent;
    private boolean isPropertyOwned;
    private Player owner;
    private boolean isMortgaged;

    PropertyTile(){
        this.setTileName("");
        this.value = 0;
        this.rent = 0;
        this.isPropertyOwned = false;
        this.owner = null;
        this.setProperty(true);
        this.isMortgaged = false;
    }

    PropertyTile(String newName, int newVal, int newRent){
        this.setTileName(newName);
        this.value = newVal;
        this.rent = newRent;
        this.isPropertyOwned = false;
        this.owner = null;
        this.setProperty(true);
    }

    public int setMortgage(boolean newVal){
        this.isMortgaged = newVal;
        return this.getValue() / 2;
    }

    public void setRent(int newRent){
        this.rent = newRent;
    }

    public int getRent(){
        return this.rent;
    }

	public Player getOwner(){
		return this.owner;
	}

    //@param pName - takes the player to pass ownership of property to
    //@return - true if property is unowned, false if already owned
    public boolean setOwnership(Player pName){
        if(this.isPropertyOwned == false){
            this.isPropertyOwned = true;
            this.owner = pName;
            return true;
        }else{
            return false;
        }
    }

    //remove ownership of the property
    public void removeOwnership(){
        this.isPropertyOwned = false;
        this.owner = null;
    }

    //determine if the property currently has an owner
    //@return - true if the property is owned, false if the property is not owned
    public boolean isOwned(){
        return this.isPropertyOwned;
    }

    //retuns true if property is mortgaged
    //returns fals if not
    public boolean isMortgaged(){
        return this.isMortgaged;
    }

    public int getValue() {
        return this.value;
    }
}
