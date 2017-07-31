import java.awt.AWTException;
import java.awt.Point;
import java.awt.Robot;
import java.awt.event.InputEvent;
import java.util.ArrayList;

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

	private ArrayList<PropertyTile> getMortgageablePropertyList() {
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
		return (this.getMortgageablePropertyList().size() == 0)? false : true;	
	}
	
	private void performMortgage(PropertyTile prop) {
		// uses mortgage method from player class

		if(this.canPerformMortgage()) {
			this.mortgage(prop);		
			AI.displayActionMessage(this.getName() + " mortgaged: " + prop.getTileName());
		}
	}
	
	public int propTotalMortgageValue() {
		int total = 0;
		ArrayList<PropertyTile> mortgageable = this.getMortgageablePropertyList();
		
		for(int i = 0; i < mortgageable.size(); i++) {
			total += mortgageable.get(i).getValue()/2;
			
		}		
		
		return total;
	}
	
	public void mortgageForMoney(int amount) {
		if(propTotalMortgageValue() < amount) {
			// current property mortgage value is less than requested amount
			return;
			
		}
		
		ArrayList<PropertyTile> mortgageable = this.getMortgageablePropertyList();
		int currentTotal = 0;
		
		if((mortgageable.get(0).getValue()/2) >= amount)
			this.performMortgage(mortgageable.get(0));
		else {
			for(int i = 0; i < mortgageable.size(); i++) {
				if(currentTotal >= amount)
					break;
				
				this.performMortgage(mortgageable.get(i));
				currentTotal += mortgageable.get(i).getValue()/2;
			}
		}
		
	}
	
	private ArrayList<PropertyTile> getMortgagedTiles(){
		ArrayList<PropertyTile> owned = this.getPropertiesList();
		ArrayList<PropertyTile> mortgaged = new ArrayList<PropertyTile>();
		
		// initiates a list of mortgaged properties
		for(int i = 0; i < owned.size(); i++) {
			if(owned.get(i).isMortgaged() == true)
				mortgaged.add(owned.get(i));
		}
		
		return mortgaged;	
		
	}
	
	private PropertyTile getBestPropertyToBuyBack() {
		PropertyTile result = null;
		int balance = this.getMoney();	// current balance remaining
		ArrayList<PropertyTile> mortgaged = this.getMortgagedTiles();
		ArrayList<PropertyTile> toBuyBack = new ArrayList<PropertyTile>();
		
		for(int i = 0; i < mortgaged.size(); i++) {
			if(mortgaged.get(i).getValue()/2 <= balance)
				toBuyBack.add(mortgaged.get(i));
			
		}	
		
		if(toBuyBack.size() > 0) {
			for(int i = 0; i < toBuyBack.size(); i++) {
				if(BoardData.isHighPriorityTile(toBuyBack.get(i))) {
					// if it is the highest priority tile, buy it back now
					result = toBuyBack.get(i);
					break;
					
				}else {
					// store it as an option
					result = toBuyBack.get(i);
					
				}
				
			}
			
		}else {
			result = null;
			
		}
		
		return result;
	}
	
	public void performBuyBack() {
		PropertyTile prop = this.getBestPropertyToBuyBack();
		if(prop != null) {
			this.buyBackMortgage(prop);
			AI.displayActionMessage(this.getName() + " has bought back " + prop.getTileName());
		}
	}
	
	public static void displayActionMessage(String msg) {
		JOptionPane.showMessageDialog(null, msg);
		
	}

}
