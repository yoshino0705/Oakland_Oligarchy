import java.util.ArrayList;

public class Player{
	String name;
	int money;
	int position;
	String color;
	boolean hasLost;
	boolean isAI = false;
	ArrayList<PropertyTile> propertyOwned = new ArrayList<PropertyTile>();

	Player(String name, int money, int player_number, boolean lost){
		this.name = name.toUpperCase();
		this.setMoney(money);
		this.position = 0;
		this.color = makeColor(player_number);
		this.hasLost = lost;
	}


	Player(String name, int money, int player_number, boolean lost, int position){
		this.name = name.toUpperCase();
		this.setMoney(money);
		this.position = position;
		this.color = makeColor(player_number);
		this.hasLost = lost;
	}

	//mortgage the player's property and set the player's money appropriately
	public boolean mortgage(PropertyTile property){
		for(int i = 0; i < propertyOwned.size(); ++i){
			if(propertyOwned.get(i).getTileName().equals(property.getTileName())){
				//when mortgaging a property the property must return the value it is worth
				//note: set mortgage returns the mortgage value (half the value)
				this.setMoney(this.getMoney() + property.setMortgage(true));
				//when unmortgaging a property it must return what it costs to unmortgage
				return true;
			}
		}
		System.out.println("property not found");
		return false;
	}

	//buys the mortgage back so that the player's money is set appropriately and the property is active
	public boolean buyBackMortgage(PropertyTile property){
		for(int i = 0; i < propertyOwned.size(); ++i){
			if(propertyOwned.get(i).getTileName().equals(property.getTileName())){
				//when mortgaging a property the property must return the value it is worth
				//note: set mortgage returns the mortgage value (half the value)
				this.setMoney(this.getMoney() - property.setMortgage(false));
				//when unmortgaging a property it must return what it costs to unmortgage
				return true;
			}
		}
		System.out.println("property not found");
		return false;
	}

	public boolean hasProperty(){
		if(propertyOwned.size() > 0) return true;
		else return false;
	}

	public ArrayList<PropertyTile> getPropertiesList(){
		return propertyOwned;
	}

	public void addProperty(PropertyTile property){
		propertyOwned.add(property);
	}

	public boolean removeProperty(PropertyTile property){
		for(int i = 0; i < propertyOwned.size(); ++i){
			if(propertyOwned.get(i).getTileName().equals(property.getTileName())){
				propertyOwned.remove(i);
				return true;
			}
		}
		System.out.println("Property not removed");
		return false;
	}

	public int getMoney(){
		return this.money;
	}
	public void setMoney(int money){
		if (money < 0)
			money = 0;
		this.money = money;
	}

	public void setName(String s) {
		this.name = s;
	}
	public String getName(){
		return this.name;
	}

	public void setPosition(int p) {
		this.position = p;
	}

	public int getPosition() {
		return this.position;
	}

	public boolean hasLost() {
		return hasLost;
	}

	public void lose(){
		this.hasLost = true;
	}

	public String makeColor(int player_number){
		String color = "";
		if(player_number==0){
			color = "Red";
		}else if(player_number==1){
			color = "Orange";
		}else if(player_number==2){
			color = "Green";
		}else if(player_number==3){
			color = "Blue";
		}
		return color;
	}

	public String getColor(){
		return this.color;
	}
}//end of class Player
