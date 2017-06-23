import java.util.ArrayList;
import javax.swing.JFrame;

public class Trade extends JFrame{
	public Trade(Player owner, ArrayList<Player> guests, ImplementTiles tiles){
		if(guests.size() > 4 || guests.size() < 1){
			System.out.println("Error in Trade Constructor: too few or too many guests (" + guests.size() + " guests)");
			System.exit(1);
		}
		
		this.setTitle("Trade Menu");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setSize(1500, 1300);
		this.setLayout(null);
		this.setResizable(false);
		
		Trader host = new Trader(owner, tiles);
		Trader guest1 = new Trader(guests.get(0), tiles);
		Trader guest2 = new Trader(guests.get(1), tiles);
		Trader guest3 = new Trader(guests.get(2), tiles);
		
		host.setBounds((this.getWidth() - host.width)/2, 50, host.width, host.height);
		this.add(host);
		
		guest1.setBounds(50, this.getHeight() - 600, guest1.width, guest1.height);
		this.add(guest1);
		
		guest2.setBounds(50 + 500, this.getHeight() - 600, guest2.width, guest2.height);
		this.add(guest2);
		
		guest3.setBounds(50 + 1000, this.getHeight() - 600, guest3.width, guest3.height);
		this.add(guest3);
		
		this.setVisible(true);
		
		
	}
	
	
	public static void main(String args[]){
		Player GM = new Player("Host", 999999); 		// for testing purpose
		
		Player p = new Player("Yoshino", 1000); 		// for testing purpose
		Player p2 = new Player("Hibiki", 30000); 		// for testing purpose
		Player p3 = new Player("Chino", 500000); 		// for testing purpose
		
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(p);
		players.add(p2);
		players.add(p3);
		
		ImplementTiles tiles = new ImplementTiles();
		
		for(int i = 1; i < 10; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p);
			
		}
		
		for(int i = 10; i < 21; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p2);
			
		}
		
		for(int i = 21; i < 35; i++){
			((PropertyTile)tiles.getTile(i)).setOwnership(p3);
			
		}
		
		new Trade(GM, players, tiles);
		
	}
}
