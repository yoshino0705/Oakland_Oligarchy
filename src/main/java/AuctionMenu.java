import java.awt.Font;
import java.util.ArrayList;

import javax.swing.JDialog;

public class AuctionMenu extends JDialog{
	private double frameWidth = 2500;
	private double frameHeight = 1800;
	private double buttonWidth = 300;
	private double buttonHeight = 150;
	private Font defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, 30);

	private Player currentPlayer;
	
	private double scaleX;
	private double scaleY;
	
	public AuctionMenu(Player curPlayer, ArrayList<Player> otherPlayers, ImplementTiles it, double scaleInX, double scaleInY) {
		// set and apply scales
		this.scaleX = scaleInX;
		this.scaleY = scaleInY;
		this.buttonWidth *= this.scaleX;
		this.buttonHeight *= this.scaleY;
		this.defaultButtonFont = new Font("TimesNewRoman", Font.BOLD, (int)(30 * this.scaleX));
		this.currentPlayer = curPlayer;
		
		this.frameWidth *= this.scaleX;
		this.frameHeight *= this.scaleY;
		
		// error checking
		if(otherPlayers.size() > 4 || otherPlayers.size() < 1){
			System.out.println("Error in Trade Constructor: too few or too many guests (" + otherPlayers.size() + " guests)");
			System.exit(1);
		}
		
	}
	
	
	
	
}