import javax.swing.*;

public class StartMenu{
	/*
	 * example code:
	 * 
	 * StartMenu sm = new StartMenu(); 
	 * sm.getPlayerNames(0 to 3);
	 * sm.getPlayerCount();
	 * 
	 * */
	
	private String[] playerNames;
	private int playerCount;
	
	private final Object[] playerCountSelections = {2, 3, 4};
	
	public StartMenu(){  
		try{
			playerCount = (int) JOptionPane.showInputDialog(null, "Select number of players", "Initialization", JOptionPane.QUESTION_MESSAGE, null, playerCountSelections, playerCountSelections[0]);
			//System.out.println(playerCount);
		}catch(NullPointerException e){
			playerCount = 2;
			//System.out.println(playerCount);
		}
	    
		playerNames = new String[playerCount];
		
		playerNames[0] = this.ensureFirstPlayerIsntAI();
		
		for(int i = 1; i < playerCount; i++){
			playerNames[i] = JOptionPane.showInputDialog("Enter Player " + (i+1) + "'s Name\nName the player 'ai' to create an ai player");
				
			if(playerNames[i].trim().contentEquals(""))
				playerNames[i] = "Player " + (i+1);
			
		}
	}
	
	public String getPlayerName(int playerIndex){
		return this.playerNames[playerIndex];
		
	}
	
	public int getPlayerCount(){
		return this.playerCount;
		
	}
	
	private String ensureFirstPlayerIsntAI() {
		String firstPlayerName = JOptionPane.showInputDialog("Enter Player 1's Name");
		
		while(firstPlayerName.equalsIgnoreCase("ai")) {
			JOptionPane.showMessageDialog(null, "First player cannot be AI! Please re-enter player name!");
			firstPlayerName = JOptionPane.showInputDialog("Enter Player 1's Name");
		}
		
		if(firstPlayerName.equalsIgnoreCase(""))
			firstPlayerName = "Player 1";
		
		return firstPlayerName;
	}
	
	public static void main(String args[]){
		StartMenu sm = new StartMenu();
		
		for(int i = 0; i < sm.playerCount; i++)
			System.out.println(sm.getPlayerName(i));
		
		System.out.println("A total of " + sm.playerCount + " players.");
	}

}
