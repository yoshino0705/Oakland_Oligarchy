public class Player{
	String name;
	int money;

	Player(String name, int money){
		this.name = name;
		this.money = money;
	}

	public int  getMoney(){
		return money;	
	}
	public int setMoney(int money){
		this.money = money;
		return this.money;
	}


}//end of class Player
	
