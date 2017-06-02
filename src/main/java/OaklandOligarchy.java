import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	JButton newGame = new JButton("New Game");
	JButton endGame = new JButton("End Game");

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
		all_players.add(p2);
		all_players.add(p3);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		//add menu panel accross the top of the game UI
		JPanel menu = new JPanel();
		menu.setPreferredSize(new Dimension(1000, 100));
		window.add(menu, BorderLayout.NORTH);
		menu.setLayout(new GridLayout(0, 6));

		//set game title in top left
		JLabel title = new JLabel("<html>Oakland<br>Oligarchy</html>");
		title.setFont(new Font("Times", Font.PLAIN, 30));
		menu.add(title, 0, 0);

		//set font for buttons in menu panel
		newGame.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(newGame, 0, 1);

		endGame.setFont(new Font("Courier", Font.PLAIN, 20));
		menu.add(endGame, 0, 2);

		//add info to the left side of the frame
		InfoPanel info = new InfoPanel(all_players);
		window.add(info, BorderLayout.WEST);


		window.setVisible(true);
	}//end of OaklandOligarchy constructor



}//end of class OaklandOligarchy
