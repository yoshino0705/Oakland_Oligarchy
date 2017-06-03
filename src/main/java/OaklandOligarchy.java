import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	JLabel currentTurnPlayerLabel;
	JButton tradeButton = new JButton("<html>Make<br>Trade</html>");
	JButton rollButton = new JButton("Roll");
	JButton newGame = new JButton("New Game");
	JButton endGame = new JButton("End Game");
	JButton helpButton  = new JButton("Help");

	int numPlayers = 0;//keep track of how many players there are
	int numTurns = 0;//keep track of how many turns to mod with the all_players array
	Player currentTurnPlayer = null;//the player whos turn it is

	//list of all the players in this game
	ArrayList<Player> all_players = new ArrayList<>();
	//manual creation of players for skeleton purposes
	Player p1 = new Player("player1", 800);
	Player p2 = new Player("player2", 12000);
	Player p3 = new Player("player3HasAReallyLongNameSoLetsSeeWhadfasdf", 800);
	public static void main(String[] args){
		new OaklandOligarchy();
	}//end of main method

	//constructor will start the gui
	OaklandOligarchy(){
		//manually add players for skeleton
		all_players.add(p1);
		numPlayers++;
		all_players.add(p2);
		numPlayers++;
		all_players.add(p3);
		numPlayers++;
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		//add menu panel accross the top of the game UI
		JPanel menu = new JPanel();
		menu.setPreferredSize(new Dimension(1000, 100));
		menu.setBorder(BorderFactory.createLineBorder(Color.black));
		window.add(menu, BorderLayout.NORTH);
		menu.setLayout(new GridLayout(0, 7));

		//set game title in top left
		JLabel title = new JLabel("<html>Oakland<br>Oligarchy</html>");
		title.setFont(new Font("Times", Font.PLAIN, 30));
		menu.add(title, 0, 0);

		//add component to keep track of turns
		currentTurnPlayer = all_players.get(getIndexCurrentTurnPlayer());
		currentTurnPlayerLabel = new JLabel("<html>Turn:<br>"+currentTurnPlayer.getName()+"</html>", SwingConstants.CENTER);
		currentTurnPlayerLabel.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(currentTurnPlayerLabel,0,1);

		tradeButton.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(tradeButton,0,2);
		rollButton.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(rollButton,0,3);
		//set font for buttons in menu panel
		newGame.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(newGame, 0, 4);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(endGame, 0, 5);

		
		helpButton.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(helpButton, 0, 6);

		//add info to the left side of the frame
		InfoPanel info = new InfoPanel(all_players);
		window.add(info, BorderLayout.WEST);


		window.setVisible(true);
	}//end of OaklandOligarchy constructor

	//get index of current turn player to use in all_players
	int getIndexCurrentTurnPlayer(){
		if(numTurns == 0){
			return 0;
		}else{
			return (numTurns % numPlayers);
		}
	}


}//end of class OaklandOligarchy
