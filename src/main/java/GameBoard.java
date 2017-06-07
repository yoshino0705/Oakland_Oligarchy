import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.AffineTransform;

import javax.swing.*;

public class GameBoard extends JPanel{
	/*
		usage:
		new GameBoard (x coordinate, y coordinate, scale of x axis, scale of y axis)
		
		example code:
		
		JFrame window = new JFrame();
		GameBoard gb = new GameBoard(0, 0, 0.63, 0.63);
		window.add(gb, BorderLayout.CENTER);
		
		move player:
		usage:
		GameBoard gb = new GameBoard(0, 0, 0.63, 0.63);
		gb.movePlayer(playerNum, 0 to 35);	// playerNum could be substituted by GameBoard.PLAYER_1, GameBoard.PLAYER_2, etc...
		gb.refreshBoard();

	*/

	private int shiftX = 0;
	private int shiftY = 0;

	private double scaleX = 1;
	private double scaleY = 1;
	
	private double rectWidthHorizontal = 150;
	private double rectHeightHorizontal = 100;
	private double rectWidthVertical = 100;
	private double rectHeightVertical = 150;
	private double cornerWidth = 200;
	private double cornerHeight = 200;
	
	private double subRectWidthHorizontal = 30;
	private double subRectHeightHorizontal = rectHeightHorizontal;
	private double subRectWidthVertical = rectWidthVertical;
	private double subRectHeightVertical = 30;
	
	private double boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
	private double boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;
	
	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;
	public static final int PLAYER_3 = 2;
	public static final int PLAYER_4 = 3;
	
	private int playerOnTile[];
	
	public GameBoard(int x, int y, double scaleX, double scaleY){
		shiftX = x;
		shiftY = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		rectWidthHorizontal = 150 * scaleX;
		rectHeightHorizontal = 100 * scaleY;
		rectWidthVertical = 100 * scaleX;
		rectHeightVertical = 150 * scaleY;
		cornerWidth = 200 * scaleX;
		cornerHeight = 200 * scaleY;
		
		subRectWidthHorizontal = 30 * scaleX;
		subRectHeightHorizontal = rectHeightHorizontal;
		subRectWidthVertical = rectWidthVertical;
		subRectHeightVertical = 30 * scaleY;
		
		boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
		boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;
		
		playerOnTile = new int[4];
		playerOnTile[0] = 0;
		playerOnTile[1] = 0;
		playerOnTile[2] = 0;
		playerOnTile[3] = 0;

	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    drawBasicBoard(g);
	    drawSubRect(g);
	    Font font = new Font("TimesNewRoman", Font.BOLD, 50);
	    drawText(g, "Oakland Oligarchy", boardWidth/2 - getStringLengthOnBoard(g, "Oakland Oligarchy", font)/2, boardHeight / 2.0, font);
	    drawPlayersOnBoard(g);
	    
//	    Graphics2D g2d = (Graphics2D) g;
//	    AffineTransform at = new AffineTransform();
//        at.rotate(- Math.PI / 2);
//        g2d.setTransform(at);
//        g2d.setColor(Color.BLACK);
//        g2d.drawString("Tile Name", -300, 200);
//        
//        AffineTransform at2 = AffineTransform.getQuadrantRotateInstance(1);
//        g2d.setTransform(at2);
//         
//        g2d.drawString("Hello World", -100, -280);

	    
	}
	
	public void refreshBoard(){
		clearDrawingBoard(this.getGraphics());
		drawBasicBoard(this.getGraphics());
		drawSubRect(this.getGraphics());
		Font font = new Font("TimesNewRoman", Font.BOLD, 50);
		drawText(this.getGraphics(), "Oakland Oligarchy", boardWidth/2 - getStringLengthOnBoard(this.getGraphics(), "Oakland Oligarchy", font)/2, boardHeight / 2.0, font);
		drawPlayersOnBoard(this.getGraphics());
		
		
	}
	
	public void movePlayer(int playerNum, int toThisTileID){
		if(playerNum > 4 || playerNum < 0){
			System.out.println("Error in GameBoard movePlayer(): invalid playerNum");
			System.exit(1);
		}
		
		playerOnTile[playerNum] = toThisTileID;
	}
	
	private void drawPlayersOnBoard(Graphics g){
		// corner : 0
		// bottom : 1
		// left   : 2
		// top    : 3
		// right  : 4
		
		for(int playerNum = 0; playerNum < 4; playerNum++){
			switch(getTileCategoryID(playerOnTile[playerNum])){
				case 0:
					drawPlayerOnCorner(g, playerNum, playerOnTile[playerNum]);
					break;
					
				case 1:
					drawPlayerOnBottomRow(g, playerNum, playerOnTile[playerNum]);
					break;
					
				case 2:
					drawPlayerOnLeftRow(g, playerNum, playerOnTile[playerNum]);
					break;
			
				case 3:
					drawPlayerOnTopRow(g, playerNum, playerOnTile[playerNum]);
					break;
					
				case 4:
					drawPlayerOnRightRow(g, playerNum, playerOnTile[playerNum]);
					break;
			
			}
			
		}
		
	}
	
	public int getTileCategoryID(int tileID){
		// corner : 0
		// bottom : 1
		// left   : 2
		// top    : 3
		// right  : 4
		
		if(tileID >= 1 && tileID <= 8)
			return 1;
		else if(tileID >= 10 && tileID <= 17)
			return 2;
		else if(tileID >= 19 && tileID <= 26)
			return 3;
		else if(tileID >= 28 && tileID <= 35)
			return 4;
		else
			return 0;
		
	}
	
	private int getStringLengthOnBoard(Graphics g, String text, Font font){		
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(text);
		
	}

	private void clearDrawingBoard(Graphics g){
		g.setColor(Color.lightGray);
		g.fillRect(shiftX, shiftY, (int)boardWidth, (int)boardHeight);
		
	}
	
	private void drawCorner(Graphics g, double x, double y){
		g.drawRect( (int) x,  (int) y, (int) cornerWidth, (int) cornerHeight);
	}
	
	private void drawVerticalBox(Graphics g, double x, double y){
		g.drawRect( (int) x,  (int) y,  (int) rectWidthVertical, (int) rectHeightVertical);
	}
	
	private void drawHorizontalBox(Graphics g, double x, double y){
		g.drawRect( (int) x, (int) y, (int) rectWidthHorizontal, (int) rectHeightHorizontal);
	}
	
	private void drawVerticalSubBox(Graphics g, double x, double y){
		g.setColor(Color.CYAN);
		g.fillRect( (int) x,  (int) y,  (int) subRectWidthVertical, (int) subRectHeightVertical);
	}
	
	private void drawHorizontalSubBox(Graphics g, double x, double y){
		g.setColor(Color.YELLOW);
		g.fillRect( (int) x, (int) y, (int) subRectWidthHorizontal, (int) subRectHeightHorizontal);
	}
	
	private void drawText(Graphics g, String text, double x, double y, Font font){
		g.setColor(Color.BLACK);
		g.setFont(font);
		g.drawString(text, (int) x + shiftX, (int) y + shiftY);
	}
	
	private void drawBasicBoard(Graphics g){
		// top left corner
	    drawCorner(g, 0 + shiftX, 0 + shiftY);
	    
	    // top row
	    for(int i = 0; i < 8; i++)
	    	drawVerticalBox(g, (cornerWidth + i * rectWidthVertical) + shiftX, 0 + shiftY);
	    
	    // top right corner
	    drawCorner(g, (boardWidth - cornerWidth) + shiftX, 0  + shiftY);
	    
	    // left row
	    for(int i = 0; i < 8; i++)
	    	drawHorizontalBox(g, 0 + shiftX, (cornerHeight + i * rectHeightHorizontal)+ shiftY);
	    
	    // bottom left corner
	    drawCorner(g, 0 + shiftX, (boardHeight - cornerHeight) +  + shiftY);
	    
	    // right row
	    for(int i = 0; i < 8; i++)
	    	drawHorizontalBox(g, (boardWidth - rectWidthHorizontal) + shiftX, (cornerHeight + i * rectHeightHorizontal) + shiftY);
	    
	    // bottom right corner
	    drawCorner(g, (boardWidth - cornerWidth) + shiftX, (boardHeight - cornerHeight) + shiftY);
	    
	    // bottom row
	    for(int i = 0; i < 8; i++)
	    	drawVerticalBox(g, (cornerWidth + i * rectWidthVertical) + shiftX, (boardHeight - rectHeightVertical) + shiftY);
		
		
	}
	
	private void drawSubRect(Graphics g){
		 // top row
	    for(int i = 0; i < 8; i++)
	    	drawVerticalSubBox(g, (cornerWidth + i * rectWidthVertical) + shiftX, (rectHeightVertical - subRectHeightVertical) + shiftY);
	    
	    // left row
	    for(int i = 0; i < 8; i++)
	    	drawHorizontalSubBox(g, (rectWidthHorizontal - subRectWidthHorizontal) + shiftX, (cornerHeight + i * rectHeightHorizontal)+ shiftY);
	    
	    // right row
	    for(int i = 0; i < 8; i++)
	    	drawHorizontalSubBox(g, (boardWidth - rectWidthHorizontal) + shiftX + 1, (cornerHeight + i * rectHeightHorizontal) + shiftY);
	    
	    // bottom row
	    for(int i = 0; i < 8; i++)
	    	drawVerticalSubBox(g, (cornerWidth + i * rectWidthVertical) + shiftX, (boardHeight - rectHeightVertical) + shiftY + 1);		
		
	}
	
	private void drawPlayerOnCorner(Graphics g, int playerNum, int tileID){	
		int playerIconWidth = 20;
		int playerIconHeight = 20;
		int fixedValueX = 15;
		int fixedValueY = 15;
		
		int player1RelativePosX = (int)(boardWidth - cornerWidth) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(boardHeight - (cornerHeight)) + shiftY + fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player3RelativePosX = (int)(boardWidth - cornerWidth) + shiftX + fixedValueX;
		int player3RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(boardHeight - (cornerHeight)) + shiftY + fixedValueY;
		
		int cornerXShiftDistance = (int)(rectWidthVertical * 8 + cornerWidth);
		int cornerYShiftDistance = (int)(rectHeightHorizontal * 8 + cornerHeight);
		
		switch(tileID){
			case 0:
				cornerXShiftDistance *= 0;
				cornerYShiftDistance *= 0;
				break;
				
			case 9:
				cornerXShiftDistance *= -1;
				cornerYShiftDistance *= 0;
				break;
				
			case 18:
				cornerXShiftDistance *= -1;
				cornerYShiftDistance *= -1;
				break;
				
			case 27:
				cornerXShiftDistance *= 0;
				cornerYShiftDistance *= -1;
				break;
				
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnCorner(): invalid tileID " + tileID + "!");
				break;
		
		}
		
		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				g.fillRect(player1RelativePosX + cornerXShiftDistance, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				g.fillRect(player2RelativePosX + cornerXShiftDistance, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);
			
				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				g.fillRect(player3RelativePosX + cornerXShiftDistance, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				g.fillRect(player4RelativePosX + cornerXShiftDistance, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

				break;
				
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnCorner(): invalid playerNum " + playerNum + "!");
				break;
		}				
				
	}
	
	private void drawPlayerOnBottomRow(Graphics g, int playerNum, int tileID){
		int playerIconWidth = 20;
		int playerIconHeight = 20;
		int fixedValueX = 5;
		int fixedValueY = 5;
		
		int player1RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(boardHeight - (rectHeightVertical) + subRectHeightVertical) + shiftY + fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player3RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player3RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(boardHeight - (rectHeightVertical) + subRectHeightVertical) + shiftY + fixedValueY;
		
		// tileID range: 1-8
		int cornerXShiftDistance = (int)(rectWidthVertical * (tileID - 1) * -1);		
		
		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				g.fillRect(player1RelativePosX + cornerXShiftDistance, player1RelativePosY, playerIconWidth, playerIconHeight);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				g.fillRect(player2RelativePosX + cornerXShiftDistance, player2RelativePosY, playerIconWidth, playerIconHeight);
		
				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				g.fillRect(player3RelativePosX + cornerXShiftDistance, player3RelativePosY, playerIconWidth, playerIconHeight);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				g.fillRect(player4RelativePosX + cornerXShiftDistance, player4RelativePosY, playerIconWidth, playerIconHeight);

			break;
			
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
		}				
	}
	
	private void drawPlayerOnTopRow(Graphics g, int playerNum, int tileID){
		int playerIconWidth = 20;
		int playerIconHeight = 20;
		int fixedValueX = 5;
		int fixedValueY = 5;
		
		int player1RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightVertical - subRectHeightVertical - playerIconHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player2RelativePosY = shiftY + fixedValueY;
		int player3RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player3RelativePosY = shiftY + fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightVertical - subRectHeightVertical - playerIconHeight) + shiftY - fixedValueY;
				
		// tileID range: 19-26
		int cornerXShiftDistance = (int)(rectHeightHorizontal * (26 - tileID) * -1);		
		
		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				g.fillRect(player1RelativePosX + cornerXShiftDistance, player1RelativePosY, playerIconWidth, playerIconHeight);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				g.fillRect(player2RelativePosX + cornerXShiftDistance, player2RelativePosY, playerIconWidth, playerIconHeight);
		
				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				g.fillRect(player3RelativePosX + cornerXShiftDistance, player3RelativePosY, playerIconWidth, playerIconHeight);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				g.fillRect(player4RelativePosX + cornerXShiftDistance, player4RelativePosY, playerIconWidth, playerIconHeight);

				break;
			
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
		}				
		
	}

	private void drawPlayerOnLeftRow(Graphics g, int playerNum, int tileID){
		int playerIconWidth = 20;
		int playerIconHeight = 20;
		int fixedValueX = 5;
		int fixedValueY = 5;
		

		
		int player1RelativePosX = shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player3RelativePosX = shiftX + fixedValueX;
		int player3RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player4RelativePosX = (int)(rectWidthHorizontal - subRectWidthHorizontal - playerIconWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
				
		// tileID range: 10-17
		int cornerYShiftDistance = (int)(rectHeightHorizontal * (tileID - 9) * 1);		
				
		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				g.fillRect(player1RelativePosX, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				g.fillRect(player2RelativePosX, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);
				
				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				g.fillRect(player3RelativePosX, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);
				
				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				g.fillRect(player4RelativePosX, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

			break;
					
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
			}							
	
	}
	
	private void drawPlayerOnRightRow(Graphics g, int playerNum, int tileID){
		int playerIconWidth = 20;
		int playerIconHeight = 20;
		int fixedValueX = 5;
		int fixedValueY = 5;
		
		int player1RelativePosX = (int)(boardWidth - rectWidthHorizontal + subRectWidthHorizontal) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(rectWidthHorizontal - subRectWidthHorizontal - playerIconWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player3RelativePosX = (int)(boardWidth - rectWidthHorizontal + subRectWidthHorizontal) + shiftX + fixedValueX;
		int player3RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
		
		// tileID range: 28-35
		int cornerYShiftDistance = (int)(rectHeightHorizontal * (tileID - 28) * 1);		
						
		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				g.fillRect(player1RelativePosX, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				g.fillRect(player2RelativePosX, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);
						
				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				g.fillRect(player3RelativePosX, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);
						
				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				g.fillRect(player4RelativePosX, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight);

			break;
							
			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
			break;
		}
				
		
	}
	
	public static void main(String args[]){
		// for testing
		
		JFrame newFrame = new JFrame("Board");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setSize(2000,2000);
		
		GameBoard gb = new GameBoard(100, 100, 1.5, 1.5);
		newFrame.add(gb, BorderLayout.CENTER);
		
		//gb.movePlayer(0, 35);
		
		newFrame.setVisible(true);
		
		
	}
	
}
