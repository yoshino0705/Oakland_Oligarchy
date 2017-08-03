import javax.swing.*;

public class NewOrLoad{
	private boolean isLoadedGame;
	private final String[] options = {"New Game", "Load Game", "Demo Mode"};

	NewOrLoad(){
		try {
			String choice = (String) JOptionPane.showInputDialog(null,"Welcome, would you like to start a new game or load a saved one?","Welcome", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if(choice.equals("New Game")){
				isLoadedGame = false;
			}else if(choice.equals("Demo Mode")) {
				new DemoMode();
				
			}else{
				isLoadedGame = true;
			}
		} catch(Exception e){
			//e.printStackTrace();
			System.out.println("error setting choice of new or saved game");
		}

	}


	//return whether the game is a loaded game, new game is false
	public boolean getIsLoadedGame(){
		return isLoadedGame;
	}














}//end of class NewOrLoad
