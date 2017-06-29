public class Player{
	String name;
	int money;
	int position;
	String color;

	Player(String name, int money, int player_number){
		this.name = name;
		this.setMoney(money);
		this.position = 0;
		this.color = makeColor(player_number);
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

