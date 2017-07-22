import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

public class ProcessTurn {

	public ProcessTurn(OaklandOligarchy game) {
		// roll the dice
		int roll1 = rollDie();
		int roll2 = rollDie();
		int rollSum = roll1 + roll2;

		// move the player
		movePlayer(game, rollSum);

		// interact with the tile they landed on
		Player curPlayer = game.getCurrentTurnPlayer();

		boolean positionChange = false;

		if(curPlayer.getName().equalsIgnoreCase("laboon"))
				game.getGameBoard().enableEasterEgg();

		do {
			Tile curTile = game.tiles.getTile(curPlayer.getPosition());
			positionChange = false;
			if (curTile.isProperty()) {
				game.getGameBoard().drawIcon(GameBoard.PROPERTY_ICON);
				PropertyTile pTile = (PropertyTile) curTile;
				doPropertyInteraction(game, pTile, curPlayer);
			} else {	// tile is action tile and action is performed
				game.getGameBoard().drawIcon(GameBoard.ACTION_ICON);
				ActionTile aTile = (ActionTile) curTile;
				aTile.performAction(curPlayer, game.allPlayers, game);

				//action tile 6 causes player to move again
				if (aTile.getTileFlag() == 6) {
					positionChange = true;
				}
			}
		} while (positionChange == true);

		// update top menu buttons
		
		game.getTopMenu().toggleEndTurnButton();
		game.getTopMenu().toggleRollButton();
		
	}

	// constructor for testing purposes
	public ProcessTurn() {
		return;
	}

	private void movePlayer(OaklandOligarchy game, int roll) {
		// get current player & calculate their new position
		Player curPlayer = game.getCurrentTurnPlayer();
		int newPosition = (curPlayer.getPosition() + roll) % GameBoard.TILE_COUNT;
		// move player on board
		game.animatedMovePlayer(game.getIndexCurrentTurnPlayer(), curPlayer.getPosition(), roll);

		//System.out.println("Old pos: " + curPlayer.getPosition());

		// update Players position
		curPlayer.setPosition(newPosition);

		System.out.println("new pos: " + curPlayer.getPosition());
	}

	//For testing move player with no animation
	public void movePlayerNoAnimation(OaklandOligarchy game, int roll) {
		// get current player & calculate their new position
		Player curPlayer = game.getCurrentTurnPlayer();
		int newPosition = (curPlayer.getPosition() + roll) % GameBoard.TILE_COUNT;

		System.out.println("Old pos: " + curPlayer.getPosition());

		// update Players position
		curPlayer.setPosition(newPosition);

		//System.out.println("new pos: " + curPlayer.getPosition());
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: doPropertyInteraction
	~	Parameters: PropertyTile -- tile player has landed on 					~
					curPlayer -- player who's turn it is
	~	Returns: None															~
		Description: Handles buying property/paying rent when a player lands on
	~				 a property tile.											~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	private void doPropertyInteraction(OaklandOligarchy game, PropertyTile pTile, Player curPlayer) {
		// if player lands on their own tile don't do anything
		
		if (pTile.getOwner() == curPlayer)
			return;

		ArrayList<PropertyTile> forecloseProps = new ArrayList<PropertyTile>();
		if (pTile.isOwned()) {
			
			//if the property is mortgaged and it is not owned by the current player.
			if(pTile.isMortgaged() && !pTile.getOwner().equals(curPlayer) && curPlayer.isAI == false){
				JOptionPane.showMessageDialog(null, "This properpty is mortgaged. Your lucky day!");
				return;
			}

			//if player doesn't have enough money to pay, but they have properties,
			// the bank will foreclose their properties until they can pay rent
			if (curPlayer.getMoney() < pTile.getRent()) {
				ArrayList<PropertyTile> ownedProps = game.getOwnedProperties(curPlayer);
				// auction until player can afford rent
				for (PropertyTile prop : ownedProps) {
					// if they have enough to pay rent after selling, break out of loop
					if (curPlayer.getMoney() > pTile.getRent()) {
						break;
					}
					int price = prop.getValue();
					curPlayer.setMoney(curPlayer.getMoney() + (price/2));
					prop.removeOwnership();
					forecloseProps.add(prop);
					ownedProps = game.getOwnedProperties(curPlayer);
				}

				// if after auctioning all properties, they still cant pay rent
				// then the player loses
				if (curPlayer.getMoney() < pTile.getRent()) {
					// tell the player they lost
					
					if(curPlayer.isAI == false)
						JOptionPane.showMessageDialog(null, "You ran out of money and properties. You lose!");
					else {
						AI.displayActionMessage(curPlayer.getName() + " has ran out of money and properties, the AI has lost!");
						
					}						

					game.playerLose(curPlayer);
					game.endTurn();

					//check to see if a player has won and if so notify them
					if (game.checkWon()) {
						Player winner = game.getWinner();
						JOptionPane.showMessageDialog(null, winner.getName() + " has won the game!");
					}

					game.getGameBoard().refreshBoard();
					return;
				}
			}

			if (forecloseProps.size() > 0) {
				String msg = "You didn't have enough money to pay rent so the bank foreclosed these properties:\n";
				for (PropertyTile prop : forecloseProps)
					msg += prop.getTileName() + "\n";
				
				if(curPlayer.isAI == false)
					JOptionPane.showMessageDialog(null, msg);
				else {
					AI.displayActionMessage("The bank has forclosed the following properties: " + msg);
					
				}
				
			} else
				// notify player that they owe someone rent
				
				if(curPlayer.isAI == false)
					JOptionPane.showMessageDialog(null, "You landed on " + pTile.getTileName() + " owned by " +
							pTile.getOwner().getName() + ". You pay them " +
							pTile.getRent() + " dollars.");
				else {
					AI.displayActionMessage("Paid rent $" + pTile.getRent() + " to " + pTile.getOwner().getName());
				}
			
				// subtract money from curPlayer and add to owner
				curPlayer.setMoney(curPlayer.getMoney() - pTile.getRent());
				pTile.getOwner().setMoney(pTile.getOwner().getMoney() + pTile.getRent());
				
		} else {
			// check if player has enough money to purchase property
			if (curPlayer.getMoney() < pTile.getValue()) {
				// only display message to actual players
				
				if(curPlayer.isAI == false)
					JOptionPane.showMessageDialog(null, "You landed on " + pTile.getTileName() + " but you don't have"
							+ " enough money to purchase it.\n" + pTile.getTileName() +
							" costs $" + pTile.getValue() + " but you only have $" +
							curPlayer.getMoney() + ". How sad :(");
				else {
					AI.displayActionMessage("I can't buy this (sad face)");
					
				}
				
				return;
			}
			
			if(curPlayer.isAI == false) {
				//give player option to buy tile
				int result = JOptionPane.showConfirmDialog(null, "You landed on " + pTile.getTileName() +
					", would you like to buy this property for " +
					pTile.getValue() + " dollars?",
					"Purchase Property", JOptionPane.YES_NO_OPTION);
				if (result == JOptionPane.YES_OPTION) {
					// subtract cost from player
					curPlayer.setMoney(curPlayer.getMoney() - pTile.getValue());
					// set curPlayer as owner of tile
					pTile.setOwnership(curPlayer);
					curPlayer.addProperty(pTile);
				}
				
			}else {
				AI.displayActionMessage("The AI has bought " + pTile.getTileName() + " for " + pTile.getValue() + " dollars.");
				// AI auto buys the tile
				// subtract cost from player
				curPlayer.setMoney(curPlayer.getMoney() - pTile.getValue());
				// set curPlayer as owner of tile
				pTile.setOwnership(curPlayer);
				curPlayer.addProperty(pTile);
				
			}

		}
	}

	/*	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~
		Function: rollDie
	~	Parameters: None														~
		Returns: int with value between 1 and 6													~
	~	Description: Simulates die roll; returns random number between 1 and 6	~
	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	~	*/
	public int rollDie() {
		Random rand = new Random();
		int min = 1;
		int max = 6;
		return rand.nextInt((max - min) + 1) + min;
	}
}
