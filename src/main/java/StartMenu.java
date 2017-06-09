import javax.swing.*;

public class StartMenu{
	/*
	 * example code:
	 * 
	 * StartMenu sm = new StartMenu(); 
	 * sm.getPlayerNames(0 to 4);
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
		
		for(int i = 0; i < playerCount; i++){
			playerNames[i] = JOptionPane.showInputDialog("Enter Player " + (i+1) + "'s Name");
				
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
	
	public static void main(String args[]){
		StartMenu sm = new StartMenu();
		
		for(int i = 0; i < sm.playerCount; i++)
			System.out.println(sm.getPlayerName(i));
		
		System.out.println("A total of " + sm.playerCount + " players.");
	}

}
