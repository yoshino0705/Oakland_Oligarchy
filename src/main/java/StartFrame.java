import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;

public class StartFrame{

	boolean done = false;

	JFrame start_window;
	int number_players;
	int num_turns;//will abuse this to offset who goes first
	ArrayList<Player> all_players = new ArrayList<Player>();
	JPanel player_info_panel = new JPanel();
	JPanel get_num_players_panel = new JPanel();
	JLabel num_players_label = new JLabel("Enter number of players(2-4): ");
	JTextField num_players_field = new JTextField(1);
	JButton num_players_button = new JButton("START");
	JLabel num_players_error_label = new JLabel();
	
	//wrappers for sizing
	JPanel field_panel = new JPanel();
	JPanel button_panel = new JPanel();

	ArrayList<JTextField> name_fields = new ArrayList<JTextField>();

	public StartFrame(){
		start_window = new JFrame("Welcome to Oakland Oligarchy");
		start_window.setSize(1000,200);
		start_window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		//add listener to num_players_button
		ActionListener num_listener = new Num_players_listener();
		num_players_button.addActionListener(num_listener);

		//set wrapper panels for looks
		//field_panel.setLayout(new BorderLayout());
		//field_panel.add(num_players_field, BorderLayout.CENTER);
		field_panel.setLayout(new GridBagLayout());
		GridBagConstraints center = new GridBagConstraints();
		center.anchor = GridBagConstraints.CENTER;
		center.fill = GridBagConstraints.NONE;
		field_panel.add(num_players_field, center);
		button_panel.setLayout(new GridBagLayout());
		button_panel.add(num_players_button, center);

		//set up the jpanel for num players
		get_num_players_panel.setLayout(new GridLayout(1,4));
		get_num_players_panel.add(num_players_label);
		//get_num_players_panel.add(num_players_field);
		//get_num_players_panel.add(num_players_button);
		
		get_num_players_panel.add(field_panel);
		get_num_players_panel.add(button_panel);
		get_num_players_panel.add(num_players_error_label);

		start_window.add(get_num_players_panel);
		//start_window.add(player_info_panel);
		start_window.setVisible(true);
	}

	public ArrayList<Player> getAllPlayersArrayList(){
		return all_players;
	}

	public int getNumPlayers(){
		return number_players;
	}

	public int getNumTurns(){
		return num_turns;
	}
	
	public boolean isDone(){
		return done;
	}

	public void doneWithStartFrame(){
		//System.out.println("done with frame called");
		start_window.setVisible(false);
		start_window.dispose();
	}


	//for testing purposes
	public static void main(String[] args) {
		new StartFrame();
	}

	
	class Num_players_listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			String num_players_str = num_players_field.getText();
			int num_players = Integer.parseInt(num_players_str);
			if(num_players > 0 && num_players <= 4){
				number_players = num_players;
				//System.out.println("num players = "+number_players);

				//need to create x players here via name panels
				start_window.getContentPane().remove(get_num_players_panel);
				//start_window.add(new JPanel());
				start_window.add(getNamePanel(num_players));
				start_window.getContentPane().invalidate();
				start_window.getContentPane().validate();
				start_window.setVisible(true);
			}else{
					//set error msg for invalid number
			}
		}
	}//end of listener


	JPanel getNamePanel(int num_players){
		JPanel name_panel = new JPanel();
		name_panel.setLayout(new GridLayout(num_players+1, 2));
		for(int i = 0; i<num_players; i++){
			JLabel player_label = new JLabel("<html>Enter name<br>for player "+(i+1)+"</html>");
			JTextField player_field = new JTextField(20);
			name_fields.add(player_field);
			name_panel.add(player_label);
			name_panel.add(player_field);
		}	
		//JLabel name_label = new JLabel("<html>Enter name<br>for player 1</html>");
		//JTextField name_field = new JTextField(20);
		JButton name_button = new JButton("SET NAMES");
		JLabel name_error_label = new JLabel("");

		
		ActionListener name_listener = new Name_listener();
		name_button.addActionListener(name_listener);


		//name_panel.add(name_label);
		//name_panel.add(name_field);
		name_panel.add(name_button);
		name_panel.add(name_error_label);


		return name_panel;
	}//end of getNamePanel


	class Name_listener implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			for(JTextField tf: name_fields){
				all_players.add(new Player(tf.getText(),10000));
			}
			//debug prints
			//for(Player p: all_players){
			//	System.out.println(p.getName());
			//}
			done = true;
		}
	}//end of name_listener


}
