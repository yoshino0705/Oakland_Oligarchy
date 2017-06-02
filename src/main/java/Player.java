public class Player{
	String name;
	int money;

	Player(String name, int money){
		this.name = name;
		this.money = money;
	}

	public int getMoney(){
		return this.money;	
	}
	public void setMoney(int money){
		this.money = money;
	}
	public String getName(){
		return this.name;
	}

}//end of class Player
	
