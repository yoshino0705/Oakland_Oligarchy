import java.util.Random;

public class AI extends Player{
	
	public AI(String name, int money, int playerNumber, boolean lost) {
		super(name, money, playerNumber, lost);
		this.isAI = true;
		
	}
	
	public AI(String name, int money, int playerNumber, boolean lost, int position){
		super(name, money, playerNumber, lost, position);
		this.isAI = true;
		
	}
	
	public void processTheTurn(OaklandOligarchy game) {
		disableAllButtons(game);
		new ProcessTurn(game);		// rolls the dice and move toke
		enableAllButtons(game);
	}
	
	public void disableAllButtons(OaklandOligarchy game) {
		game.getTopMenu().auctionButton.setEnabled(false);
		//game.getTopMenu().endTurn.setEnabled(false);
		game.getTopMenu().mortgageButton.setEnabled(false);
		//game.getTopMenu().rollButton.setEnabled(false);
		game.getTopMenu().saveGame.setEnabled(false);
		game.getTopMenu().helpButton.setEnabled(false);
		game.getTopMenu().tradeButton.setEnabled(false);
		game.getTopMenu().unmortgageButton.setEnabled(false);
		
	}
	
	public void enableAllButtons(OaklandOligarchy game) {
		game.getTopMenu().auctionButton.setEnabled(true);
		//game.getTopMenu().endTurn.setEnabled(true);
		game.getTopMenu().mortgageButton.setEnabled(true);
		//game.getTopMenu().rollButton.setEnabled(true);
		game.getTopMenu().saveGame.setEnabled(true);
		game.getTopMenu().helpButton.setEnabled(true);
		game.getTopMenu().tradeButton.setEnabled(true);
		game.getTopMenu().unmortgageButton.setEnabled(true);
		
	}

}
