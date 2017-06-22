public class Player{
	String name;
	int money;
	int position;

	Player(String name, int money){
		this.name = name;
		this.setMoney(money);
		this.position = 0;
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

	public void setPosition(int p) {
		this.position = p;
	}

	public int getPosition() {
		return this.position;
	}

}//end of class Player
	
