import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class OaklandOligarchy{
	//main window for the application
	JFrame window = new JFrame("OaklandOligarchy");
	//list of all the players in this game
	ArrayList<Player> all_players = new ArrayList<>();
	//manual creation of players for skeleton purposes
	Player p1 = new Player("player1", 800);
	Player p2 = new Player("player2", 800);
	public static void main(String[] args){
		new OaklandOligarchy();	
	}//end of main method

	//constructor will start the gui
	OaklandOligarchy(){
		//manually add players for skeleton
		all_players.add(p1);
		all_players.add(p2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setSize(1000,1000);

		
		//add info to the left side of the frame
		InfoPanel info = new InfoPanel(all_players);
		window.add(info, BorderLayout.WEST);


		window.setVisible(true);
	}//end of OaklandOligarchy constructor



}//end of class OaklandOligarchy

















