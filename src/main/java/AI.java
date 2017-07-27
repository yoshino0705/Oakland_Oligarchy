import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.AbstractButton;
import javax.swing.JOptionPane;

public class AI extends Player{
	public static int AI_Count = 1;
	
	private ArrayList<Integer> mortgageValues;
	
	public AI(String name, int money, int playerNumber, boolean lost) {
		super(name, money, playerNumber, lost);
		this.isAI = true;
		mortgageValues = new ArrayList<Integer>();
		
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
			
		}
		
	    r.mouseMove(p.x + button.getWidth() / 2, p.y + button.getHeight() / 2);
	    r.mousePress(InputEvent.BUTTON1_MASK);
	    //try { Thread.sleep(millis); } catch (Exception e) {}
	    r.mouseRelease(InputEvent.BUTTON1_MASK);
	}
	
	private ArrayList<PropertyTile> getMortgagePropertyList() {
		ArrayList<PropertyTile> owned = this.getPropertiesList();
		ArrayList<PropertyTile> mortgageable = new ArrayList<PropertyTile>();
		
		// initiates a list of mortgageable properties
		for(int i = 0; i < owned.size(); i++) {
			if(owned.get(i).isMortgaged() == false)
				mortgageable.add(owned.get(i));
		}
		
		return mortgageable;
		
	}
	
	private boolean canPerformMortgage() {
		return (this.getMortgagePropertyList().size() == 0)? false : true;
		
	}
	
	private void performMortgage(PropertyTile prop) {
		// uses mortgage method from player class
		if(this.canPerformMortgage())
			this.mortgage(prop);		
		
	}
	
	private void mortgageForMoney(int amount) {
		ArrayList<PropertyTile> mortgageable = this.getMortgagePropertyList();
		PropertyTile toMortgage = null;
		for(int i = 0; i < mortgageable.size(); i++) {
			if((mortgageable.get(i).getValue()/2) >= amount)
				toMortgage = mortgageable.get(i);
			
		}
		
		if(toMortgage != null)
			this.performMortgage(toMortgage);
		
	}
	
	public static void displayActionMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
		
	}

}
