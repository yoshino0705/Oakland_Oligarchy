public class Player{
	String name;
	int money;

	Player(String name, int money){
		this.name = name;
		this.setMoney(money);
	}

	public int getMoney(){
		return this.money;	
	}
	public void setMoney(int money){
		if (money < 0)
			money = 0;
		this.money = money;
	}
	public String getName(){
		return this.name;
	}

}//end of class Player
	
