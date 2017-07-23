import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class AI extends Player{	
	public static int AI_Count = 1;
	
	public AI(String name, int money, int playerNumber, boolean lost) {
		super(name, money, playerNumber, lost);
		this.isAI = true;
		
	}
	
	public AI(String name, int money, int playerNumber, boolean lost, int position){
		super(name, money, playerNumber, lost, position);
		this.isAI = true;
		
	}
	
	public void processTheTurn(OaklandOligarchy game){				
		this.click(game.getTopMenu().rollButton);
		this.disableButtons(game);
		new ProcessTurn(game);		// rolls the dice and move token
		this.enableButtons(game);
		this.click(game.getTopMenu().endTurn);
	}
	
	public void disableButtons(OaklandOligarchy game) {
		game.getTopMenu().auctionButton.setEnabled(false);
		//game.getTopMenu().endTurn.setEnabled(false);
		game.getTopMenu().mortgageButton.setEnabled(false);
		//game.getTopMenu().rollButton.setEnabled(false);
		game.getTopMenu().saveGame.setEnabled(false);
		game.getTopMenu().helpButton.setEnabled(false);
		game.getTopMenu().tradeButton.setEnabled(false);
		game.getTopMenu().unmortgageButton.setEnabled(false);
		
	}
	
	public void enableButtons(OaklandOligarchy game) {
		game.getTopMenu().auctionButton.setEnabled(true);
		//game.getTopMenu().endTurn.setEnabled(true);
		game.getTopMenu().mortgageButton.setEnabled(true);
		//game.getTopMenu().rollButton.setEnabled(true);
		game.getTopMenu().saveGame.setEnabled(true);
		game.getTopMenu().helpButton.setEnabled(true);
		game.getTopMenu().tradeButton.setEnabled(true);
		game.getTopMenu().unmortgageButton.setEnabled(true);
		
	}
	
	public void click(AbstractButton button) {
	    Point p = button.getLocationOnScreen();
	    Robot r = null;
		try {
			r = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		
	    r.mouseMove(p.x + button.getWidth() / 2, p.y + button.getHeight() / 2);
	    r.mousePress(InputEvent.BUTTON1_MASK);
	    //try { Thread.sleep(millis); } catch (Exception e) {}
	    r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	public static void displayActionMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
		
	}

}
