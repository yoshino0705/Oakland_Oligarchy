
//property class - subclass of the Tile class
public class PropertyTile extends Tile{

    //class attributes
    int rent;
    boolean isPropertyOwned;
    Player owner;

    Property(){
        this.rent = 0;
        this.isPropertyOwned = false;
        this.owner = null;
    }

    Property(int newRent){
        this.rent = newRent;
        this.isPropertyOwned = false;
        this.owner = null;
    }

    public void setRent(int newRent){
        this.rent = newRent;
    }

    public int getRent(){
        return this.rent;
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
}
