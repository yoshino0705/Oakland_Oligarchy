import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.*;

public class GameBoard extends JPanel{
	/*
		1.
		usage:
		new GameBoard (x coordinate, y coordinate, scale of x axis, scale of y axis)

		example code:

		JFrame window = new JFrame();
		GameBoard gb = new GameBoard(0, 0, 0.63, 0.63);
		window.add(gb, BorderLayout.CENTER);

		2.
		move player:
		example code:

		GameBoard gb = new GameBoard(0, 0, 0.63, 0.63);
		gb.movePlayer(playerNum, 0 to 35);	// playerNum could be substituted by GameBoard.PLAYER_1, GameBoard.PLAYER_2, etc...
		gb.refreshBoard();

		3.
		showTileDetails:
		example code:

		MouseEvent e... some way to get the mouse event object
		GameBoard gb = new GameBoard( ... );
		gb.showTileDetails(e.getX(), e.getY(), 20);	// 20 is font size, can be set to any preferrable number

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

	private double subRectWidthVertical = 30;
	private double subRectHeightVertical = rectHeightHorizontal;
	private double subRectWidthHorizontal = rectWidthVertical;
	private double subRectHeightHorizontal = 30;

	private double boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
	private double boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;
	
	private JButton changeToken;

	public static final int PLAYER_1 = 0;
	public static final int PLAYER_2 = 1;
	public static final int PLAYER_3 = 2;
	public static final int PLAYER_4 = 3;
	public static final int TILE_COUNT = 36;
	
	public static final int PROPERTY_ICON = 0;
	public static final int BANK_ICON = 1;
	public static final int ACTION_ICON = 2;
	private final String filePath = "src/main/java/";
	
	private double buttonWidth = 300;
	private double buttonHeight = 100;
	private Font buttonFont;
	
	private int playerIconWidth = 30;
	private int playerIconHeight = 30;
	
	private int easterEgg = 0;
	
	private int iconSelection = 0;
	
	private int playerOnTile[];

	private int centerX[];
	private int centerY[];

	private int subRectCoordX[];
	private int subRectCoordY[];
	
	private CustomizeTokens tokenTexture;

	private BoardData stats;				// for recording data for AI design

 	public GameBoard(int x, int y, double scaleX, double scaleY, OaklandOligarchy game){
		shiftX = x;
		shiftY = y;
		this.scaleX = scaleX;
		this.scaleY = scaleY;
		
		this.buttonWidth *= this.scaleX;
		this.buttonHeight *= this.scaleY;
		
		playerIconWidth *= this.scaleX;
		playerIconHeight *= this.scaleY;

		rectWidthHorizontal = 150 * scaleX;
		rectHeightHorizontal = 100 * scaleY;
		rectWidthVertical = 100 * scaleX;
		rectHeightVertical = 150 * scaleY;
		cornerWidth = 200 * scaleX;
		cornerHeight = 200 * scaleY;

		subRectWidthVertical = 30 * scaleX;
		subRectHeightVertical = rectHeightHorizontal;
		subRectWidthHorizontal = rectWidthVertical;
		subRectHeightHorizontal = 30 * scaleY;

		boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
		boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;

		// -1 suggests player isn't visible
		playerOnTile = new int[4];
		playerOnTile[0] = -1;
		playerOnTile[1] = -1;
		playerOnTile[2] = -1;
		playerOnTile[3] = -1;

		initCenterArray();
		initSubRectCoord();
		
		buttonFont = new Font("TimesNewRoman", Font.BOLD, (int)(30 * this.scaleX));
		
		tokenTexture = new CustomizeTokens();
		
		changeToken = new JButton("Change Icon");
		changeToken.setFont(buttonFont);
		
		changeToken.addActionListener(new ActionListener() {
		    @Override
		    public void actionPerformed(ActionEvent e) {        
		    	tokenTexture.showSelectionDialog(game.getIndexCurrentTurnPlayer());
		        tokenTexture.setPlayerColor(game);
		        game.refreshInfoPanel();
		        game.getGameBoard().refreshBoard();
		    }
		});
		
		this.add(changeToken);
		
		stats = new BoardData(game);
	}

	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    drawBasicBoard(g);
	    drawSubRect(g);
	    Font font = new Font("TimesNewRoman", Font.BOLD, 50);
	    
	    drawText(g, "Oakland Oligarchy", boardWidth/2 - getStringLengthOnBoard(g, "Oakland Oligarchy", font)/2, boardHeight / 2.0, font);
	    
	    changeToken.setBounds((int) (boardWidth / 2.0 - buttonWidth/2) + shiftX, (int) (boardHeight / 2.0 + shiftY - buttonHeight*2), (int) buttonWidth, (int) buttonHeight);
		  
	    drawPlayersOnBoard(g);
	    
	    if(this.easterEgg == 0)
	    	drawIcon(g);
	    else
	    	drawRandomLaboon(g);
	}

	public void refreshBoard(){
		clearDrawingBoard(this.getGraphics());
		drawBasicBoard(this.getGraphics());
		drawSubRect(this.getGraphics());
		Font font = new Font("TimesNewRoman", Font.BOLD, 50);
		drawText(this.getGraphics(), "Oakland Oligarchy", boardWidth/2 - getStringLengthOnBoard(this.getGraphics(), "Oakland Oligarchy", font)/2, boardHeight / 2.0, font);
		
		changeToken.setBounds((int) (boardWidth / 2.0 - buttonWidth/2) + shiftX, (int) (boardHeight / 2.0 + shiftY - buttonHeight*2), (int) buttonWidth, (int) buttonHeight);
		
		
		drawPlayersOnBoard(this.getGraphics());
		if(this.easterEgg == 0)
	    	drawIcon(this.getGraphics());
	    else
	    	drawRandomLaboon(this.getGraphics());
		
	}

	private void fillSubRect(Graphics g, int tileID, Color color, ArrayList<Integer> arr){
		//System.out.println(this.getGraphics());
		if(tileID >= 1 && tileID <= 8){
			// bottom row
			drawHorizontalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID], color);
		}else if(tileID >= 10 && tileID <= 17){
			// left row
			drawVerticalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID], color);
		}else if(tileID >= 19 && tileID <= 26){
			// top row
			drawHorizontalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID], color);
		}else if(tileID >= 28 && tileID <= 35){
			// right row
			drawVerticalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID], color);
		}else{
			System.out.println("Error in fillColorIntoSmallBox(): Invalid tileID");
		}

		//System.out.println("removed: " + tileID);
		arr.remove(arr.indexOf(tileID));

	}

	public void movePlayer(int playerNum, int toThisTileID){
		if(playerNum > 4 || playerNum < 0){
			System.out.println("Error in GameBoard movePlayer(): invalid playerNum");

			return;
		}

		playerOnTile[playerNum] = toThisTileID;
		// stats.addLandOnTile(toThisTileID);
	}

	private void initCenterArray(){
		centerX = new int[TILE_COUNT];
		centerY = new int[TILE_COUNT];

		for(int i = 0; i < TILE_COUNT; i++){
			switch(getTileCategoryID(i)){
				// corner : 0
				// bottom : 1
				// left   : 2
				// top    : 3
				// right  : 4

				case 0:
					// four cases for four corners
					switch(i){
						case 0:
							centerX[i] = (int)(boardWidth - cornerWidth/2) + shiftX;
							centerY[i] = (int)(boardHeight - cornerHeight/2) + shiftY;
							break;

						case 9:
							centerX[i] = (int)(cornerWidth/2) + shiftX;
							centerY[i] = (int)(boardHeight - cornerHeight/2) + shiftY;
							break;

						case 18:
							centerX[i] = (int)(cornerWidth/2) + shiftX;
							centerY[i] = (int)(cornerHeight/2) + shiftY;
							break;

						case 27:
							centerX[i] = (int)(boardWidth - cornerWidth/2) + shiftX;
							centerY[i] = (int)(cornerHeight/2) + shiftY;
							break;

					}

					break;

				case 1:
					// bottom

					centerX[i] = (int)(boardWidth - cornerWidth - rectWidthVertical * (i - 1 + 0.5)) + shiftX;
					centerY[i] = (int)(boardHeight - (rectHeightVertical/2)) + shiftY;
					break;

				case 2:
					// left

					centerX[i] = (int)(rectWidthHorizontal/2) + shiftX;
					centerY[i] = (int)(boardHeight - cornerHeight - rectHeightHorizontal * (i - 10 + 0.5)) + shiftY;
					break;

				case 3:
					// top

					centerX[i] = (int)(cornerWidth + rectWidthVertical * (i - 19 + 0.5)) + shiftX;
					centerY[i] = (int)(rectHeightVertical/2) + shiftY;
					break;

				case 4:
					// right

					centerX[i] = (int)(boardWidth - rectWidthHorizontal/2) + shiftX;
					centerY[i] = (int)(cornerHeight + rectHeightHorizontal * (i - 28 + 0.5)) + shiftY;
					break;

			}
		}
	}

	private void initSubRectCoord(){
		subRectCoordX = new int[TILE_COUNT];
		subRectCoordY = new int[TILE_COUNT];

		for(int i = 0; i < TILE_COUNT; i++){
			switch(getTileCategoryID(i)){
				// corner : 0
				// bottom : 1
				// left   : 2
				// top    : 3
				// right  : 4

				case 0:
					subRectCoordX[i] = -1;
					subRectCoordY[i] = -1;
					break;

				case 1:
					// bottom
					subRectCoordX[i] = centerX[i] - (int)(rectWidthVertical/2);
					subRectCoordY[i] = centerY[i] - (int)(rectHeightVertical/2);
					break;

				case 2:
					// left
					subRectCoordX[i] = centerX[i] + (int)((rectWidthHorizontal/2) - subRectWidthVertical) + 1;
					subRectCoordY[i] = centerY[i] - (int)(rectHeightHorizontal/2);
					break;

				case 3:
					// top
					subRectCoordX[i] = centerX[i] - (int)(rectWidthVertical/2);
					subRectCoordY[i] = centerY[i] + (int)((rectHeightVertical/2) - subRectHeightHorizontal) + 1;
					break;

				case 4:
					// right
					subRectCoordX[i] = centerX[i] - (int)(rectWidthHorizontal/2);
					subRectCoordY[i] = centerY[i] - (int)(rectHeightHorizontal/2);
					break;

			}
		}
	}

	public int getTileID(int mouseX, int mouseY){
		// corner : 0
		// bottom : 1
		// left   : 2
		// top    : 3
		// right  : 4

		int tileCategory = 0;

		for(int i = 0; i < TILE_COUNT; i++){
			tileCategory = getTileCategoryID(i);
			if(tileCategory == 0){
				// corners
				if(isMouseInRange(mouseX, mouseY, centerX[i] - (cornerWidth/2), centerY[i] - (cornerHeight/2), centerX[i] + (cornerWidth/2), centerY[i] + (cornerHeight/2) ))
					return i;

			}else if(tileCategory == 1 || tileCategory == 3){
				// vertical boxes
				if(isMouseInRange(mouseX, mouseY, centerX[i] - (rectWidthVertical/2), centerY[i] - (rectHeightVertical/2), centerX[i] + (rectWidthVertical/2), centerY[i] + (rectHeightVertical/2) ))
					return i;

			}else{
				// horizontal boxes
				if(isMouseInRange(mouseX, mouseY, centerX[i] - (rectWidthHorizontal/2), centerY[i] - (rectHeightHorizontal/2), centerX[i] + (rectWidthHorizontal/2), centerY[i] + (rectHeightHorizontal/2) ))
					return i;

			}


		}

		return -1;
	}

	private boolean isMouseInRange(int mouseX, int mouseY, double x1, double y1, double x2, double y2){
		// (x1, y1) is top left
		// (x2, y2) is bottom right

		if(mouseX > x1 && mouseX < x2){
			if(mouseY > y1 && mouseY < y2)
				return true;
		}

		return false;
	}

	private void drawPlayersOnBoard(Graphics g){
		// corner : 0
		// bottom : 1
		// left   : 2
		// top    : 3
		// right  : 4

		// -1 as not visible
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

		while(tileID > TILE_COUNT){
			tileID -= TILE_COUNT;
		}

		//System.out.println("TileID: " + tileID);

		// -1 as not visible
		if(tileID < 0)
			return -1;

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

	public void showTileDetails(int mouseX, int mouseY, int fontSize, ImplementTiles it){
		int tileIndex = getTileID(mouseX, mouseY);
		String toolTipText;

		String owner = "Unowned";

		if(tileIndex != -1 && it.getTile(tileIndex).isProperty()){
			if (it.getTile(tileIndex).getOwner() != null)
				owner = it.getTile(tileIndex).getOwner().getName();

			toolTipText = "<html><font size=" + fontSize + "> "
					+ "Property Name: " + "<b>" + it.getTile(tileIndex).getTileName() + "</b>"
					+ "<br>Property Cost: " + "<b>" + ((PropertyTile)(it.getTile(tileIndex))).getValue() + "</b>"
					+ "<br>Property Rent: " + "<b>" + ((PropertyTile)(it.getTile(tileIndex))).getRent() + "</b>"
					+ "<br>Property Owner: " + "<b>" + owner + "</b>"
					+ "</font></html>";
			this.setToolTipText(toolTipText);

		}
		else if (tileIndex != -1) {
			toolTipText = "<html><font size=" + fontSize + "> "
					+ "Action Tile:" + "</br>"
					+ "<b>" + ((ActionTile)it.getTile(tileIndex)).getTileInfo() + "</b>"
					+ "</font></html>";
			this.setToolTipText(toolTipText);
			//this.setToolTipText("<html><font size=20>Nothing to show here. </font></html>");
		}
	}

	public void showCustomDetails(int fontSize, String customMessage){
		String toolTipText = "<html><font size=" + fontSize + "> "
							+ customMessage
							+ "</font></html>";

		setToolTipText(toolTipText);
	}

	private int getStringLengthOnBoard(Graphics g, String text, Font font){
		FontMetrics metrics = g.getFontMetrics(font);
		return metrics.stringWidth(text);

	}

	private void clearDrawingBoard(Graphics g){
		g.setColor(Color.lightGray);
		g.fillRect(shiftX, shiftY, (int)boardWidth, (int)boardHeight);
		this.repaint();
	}

	private void drawCorner(Graphics g, double x, double y){
		g.drawRect( (int) x,  (int) y, (int) cornerWidth, (int) cornerHeight);
	}

	private void drawVerticalBox(Graphics g, double x, double y){
		g.drawRect( (int) x,  (int) y,  (int) rectWidthVertical, (int) rectHeightVertical);
	}

	private void drawHorizontalBox(Graphics g, double x, double y){
		g.setColor(Color.BLACK);
		g.drawRect( (int) x, (int) y, (int) rectWidthHorizontal, (int) rectHeightHorizontal);
	}

	private void drawHorizontalSubBox(Graphics g, double x, double y, Color color){
		g.setColor(color);
		g.fillRect( (int) x,  (int) y,  (int) subRectWidthHorizontal, (int) subRectHeightHorizontal);
	}

	private void drawHorizontalSubBox(Graphics g, double x, double y){
		g.setColor(Color.BLACK);
		g.drawRect( (int) x,  (int) y,  (int) subRectWidthHorizontal, (int) subRectHeightHorizontal);
	}

	private void drawVerticalSubBox(Graphics g, double x, double y, Color color){
		g.setColor(color);
		g.fillRect( (int) x, (int) y, (int) subRectWidthVertical, (int) subRectHeightVertical);
	}

	private void drawVerticalSubBox(Graphics g, double x, double y){
		g.drawRect( (int) x,  (int) y,  (int) subRectWidthVertical, (int) subRectHeightVertical);
	}

	public void drawText(Graphics g, String text, double x, double y, Font font){
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
		ArrayList<Integer> arr = new ArrayList<Integer>();	// to keep track of remaining unpainted sub rectangles (small boxes)
		for(int i = 0; i < TILE_COUNT; i++){
			arr.add(i);
		}

		// fill in colors for sub rectangles (small boxes)
		for(int i = 0; i < TILE_COUNT; i++){
			if(i%9 != 0 && i != 0)
				fillSubRect(g, i, getRandomColor(), arr);
		}

		// draw out the remaining boxes
		for(int i = 0; i < arr.size(); i++){
			int tileID = arr.get(i);
			if(tileID >= 1 && tileID <= 8){
				// bottom row
				drawHorizontalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID]);
			}else if(tileID >= 10 && tileID <= 17){
				// left row
				drawVerticalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID]);
			}else if(tileID >= 19 && tileID <= 26){
				// top row
				drawHorizontalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID]);
			}else if(tileID >= 28 && tileID <= 35){
				// right row
				drawVerticalSubBox(g, subRectCoordX[tileID], subRectCoordY[tileID]);
			}

		}

	}

	private void drawPlayerOnCorner(Graphics g, int playerNum, int tileID){
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
				tokenTexture.drawPlayer(g, player1RelativePosX + cornerXShiftDistance, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_1);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				tokenTexture.drawPlayer(g, player2RelativePosX + cornerXShiftDistance, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_2);

				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				tokenTexture.drawPlayer(g, player3RelativePosX + cornerXShiftDistance, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_3);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				tokenTexture.drawPlayer(g, player4RelativePosX + cornerXShiftDistance, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_4);

				break;

			default:
				System.out.println("Error in GameBoard.java drawPlayerOnCorner(): invalid playerNum " + playerNum + "!");
				break;
		}

	}

	private void drawPlayerOnBottomRow(Graphics g, int playerNum, int tileID){
		int fixedValueX = 5;
		int fixedValueY = 5;

		int player1RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(boardHeight - (rectHeightVertical) + subRectHeightHorizontal) + shiftY + fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player3RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player3RelativePosY = (int)(boardHeight - (playerIconHeight)) + shiftY - fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(boardHeight - (rectHeightVertical) + subRectHeightHorizontal) + shiftY + fixedValueY;

		// tileID range: 1-8
		int cornerXShiftDistance = (int)(rectWidthVertical * (tileID - 1) * -1);

		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				tokenTexture.drawPlayer(g, player1RelativePosX + cornerXShiftDistance, player1RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_1);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				tokenTexture.drawPlayer(g, player2RelativePosX + cornerXShiftDistance, player2RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_2);

				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				tokenTexture.drawPlayer(g, player3RelativePosX + cornerXShiftDistance, player3RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_3);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				tokenTexture.drawPlayer(g, player4RelativePosX + cornerXShiftDistance, player4RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_4);

				break;

			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
		}
	}

	private void drawPlayerOnTopRow(Graphics g, int playerNum, int tileID){
		int fixedValueX = 5;
		int fixedValueY = 5;

		int player1RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightVertical - subRectHeightHorizontal - playerIconHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player2RelativePosY = shiftY + fixedValueY;
		int player3RelativePosX = (int)(boardWidth - (rectWidthVertical) - cornerWidth) + shiftX + fixedValueX;
		int player3RelativePosY = shiftY + fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth - cornerWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightVertical - subRectHeightHorizontal - playerIconHeight) + shiftY - fixedValueY;

		// tileID range: 19-26
		int cornerXShiftDistance = (int)(rectHeightHorizontal * (26 - tileID) * -1);

		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				tokenTexture.drawPlayer(g, player1RelativePosX + cornerXShiftDistance, player1RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_1);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				tokenTexture.drawPlayer(g, player2RelativePosX + cornerXShiftDistance, player2RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_2);

				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				tokenTexture.drawPlayer(g, player3RelativePosX + cornerXShiftDistance, player3RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_3);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				tokenTexture.drawPlayer(g, player4RelativePosX + cornerXShiftDistance, player4RelativePosY, playerIconWidth, playerIconHeight, GameBoard.PLAYER_4);

				break;

			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
		}

	}

	private void drawPlayerOnLeftRow(Graphics g, int playerNum, int tileID){
		int fixedValueX = 5;
		int fixedValueY = 5;



		int player1RelativePosX = shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(rectWidthHorizontal - subRectWidthVertical - playerIconWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player3RelativePosX = shiftX + fixedValueX;
		int player3RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player4RelativePosX = (int)(rectWidthHorizontal - subRectWidthVertical - playerIconWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;

		// tileID range: 10-17
		int cornerYShiftDistance = (int)(rectHeightHorizontal * (17 - tileID) * 1);

		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				tokenTexture.drawPlayer(g, player1RelativePosX, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_1);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				tokenTexture.drawPlayer(g, player2RelativePosX, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_2);

				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				tokenTexture.drawPlayer(g, player3RelativePosX, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_3);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				tokenTexture.drawPlayer(g, player4RelativePosX, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_4);

			break;

			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
				break;
			}

	}

	private void drawPlayerOnRightRow(Graphics g, int playerNum, int tileID){
		int fixedValueX = 5;
		int fixedValueY = 5;

		int player1RelativePosX = (int)(boardWidth - rectWidthHorizontal + subRectWidthVertical) + shiftX + fixedValueX;
		int player1RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;
		int player2RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player2RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player3RelativePosX = (int)(boardWidth - rectWidthHorizontal + subRectWidthVertical) + shiftX + fixedValueX;
		int player3RelativePosY = (int)(cornerHeight) + shiftY + fixedValueY;
		int player4RelativePosX = (int)(boardWidth - playerIconWidth) + shiftX - fixedValueX;
		int player4RelativePosY = (int)(rectHeightHorizontal - playerIconHeight + cornerHeight) + shiftY - fixedValueY;

		// tileID range: 28-35
		int cornerYShiftDistance = (int)(rectHeightHorizontal * (tileID - 28) * 1);

		switch(playerNum){
			case 0:
				// top left
				g.setColor(Color.red);
				tokenTexture.drawPlayer(g, player1RelativePosX, player1RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_1);

				break;
			case 1:
				// bottom right
				g.setColor(Color.orange);
				tokenTexture.drawPlayer(g, player2RelativePosX, player2RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_2);

				break;
			case 2:
				// bottom left
				g.setColor(Color.green);
				tokenTexture.drawPlayer(g, player3RelativePosX, player3RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_3);

				break;
			case 3:
				// top right
				g.setColor(Color.blue);
				tokenTexture.drawPlayer(g, player4RelativePosX, player4RelativePosY + cornerYShiftDistance, playerIconWidth, playerIconHeight, GameBoard.PLAYER_4);

			break;

			default:
				System.out.println("Error in GameBoard.java drawPlayerOnBottomRow(): invalid playerNum " + playerNum + "!");
			break;
		}


	}	

	public void animatedMovePlayer(int playerNum, int startPos, int roll) {
		boolean back = false;
		if(roll < 0){
			roll *= -1;
			back = true;
		}
		
		for (int i = 1; i <= roll; i++) {
			if(back == false){
				this.movePlayer(playerNum, (i + startPos) % TILE_COUNT);
			}else{
				this.movePlayer(playerNum, (startPos - i) % TILE_COUNT);
			}

			this.refreshBoard();
			// sleep so user can see animation
			try {
				Thread.sleep(200);
			} catch (Exception e) {
				System.out.println("I can't fall asleep!");
			}
		}
		
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	private void drawPropertyIcon(Graphics g) {
		int x = (int) (this.boardWidth / 2 + this.shiftX);
		int y = (int) (this.boardHeight / 2 + this.shiftY + 50*this.scaleY);
		int imgWidth = (int) (300 * this.scaleX);
		int imgHeight = (int) (300 * this.scaleY);
		try {
			Image aImage = ImageIO.read(new File(filePath + "house.png"));
			aImage = this.getScaledImage(aImage, imgWidth, imgHeight);
			g.drawImage(aImage, x - imgWidth/2, y, null);
			
		} catch (IOException e) {
			//System.out.println("Cannot open image");
		}
	}
	
	private void drawActionIcon(Graphics g) {
		int x = (int) (this.boardWidth / 2 + this.shiftX);
		int y = (int) (this.boardHeight / 2 + this.shiftY + 50*this.scaleY);
		int imgWidth = (int) (300 * this.scaleX);
		int imgHeight = (int) (300 * this.scaleY);
		try {
			Image aImage = ImageIO.read(new File(filePath + "action.png"));
			aImage = this.getScaledImage(aImage, imgWidth, imgHeight);
			g.drawImage(aImage, x - imgWidth/2, y, null);
			
		} catch (IOException e) {
			//System.out.println("Cannot open image");
		}
	}
	
	private void drawBankIcon(Graphics g) {
		int x = (int) (this.boardWidth / 2 + this.shiftX);
		int y = (int) (this.boardHeight / 2 + this.shiftY + 50*this.scaleY);
		int imgWidth = (int) (300 * this.scaleX);
		int imgHeight = (int) (300 * this.scaleY);
		try {
			Image aImage = ImageIO.read(new File(filePath + "bank.png"));
			aImage = this.getScaledImage(aImage, imgWidth, imgHeight);
			g.drawImage(aImage, x - imgWidth/2, y, null);
			
		} catch (IOException e) {
			//System.out.println("Cannot open image");
		}
	}	
	
	private void drawIcon(Graphics g) {	    
	    switch(this.iconSelection) {
		case 0:
			this.drawPropertyIcon(g);
			//this.repaint();
			break;
		case 1:
			this.drawBankIcon(g);
			//this.repaint();
			break;
		case 2:
			this.drawActionIcon(g);
			//this.repaint();
			break;
			
		default:
			this.drawPropertyIcon(g);
			break;
	
	    }
		
	}
	
	private void drawLaboon(Graphics g, int num) {
		int x = (int) (this.boardWidth / 2 + this.shiftX);
		int y = (int) (this.boardHeight / 2 + this.shiftY + 50*this.scaleY);
		int imgWidth = (int) (300 * this.scaleX);
		int imgHeight = (int) (300 * this.scaleY);
		try {
			Image aImage = ImageIO.read(new File(filePath + "laboon" + num + ".png"));
			aImage = this.getScaledImage(aImage, imgWidth, imgHeight);
			g.drawImage(aImage, x - imgWidth/2, y, null);
			
		} catch (IOException e) {
			//System.out.println("Cannot open image");
		}
		
	}
	
	private void drawRandomLaboon(Graphics g) {
		Random rand = new Random();
		this.drawLaboon(g, rand.nextInt(4)+1);
		
	}
	
	public void drawIcon(int selection) {
		this.iconSelection = selection;
	}
	
	public void toggleEasterEgg() {
		this.easterEgg = 1 - this.easterEgg;
		
	}

	public void enableEasterEgg() {
		this.easterEgg = 1;
		
	}
	
	public void disableEasterEgg() {
		this.easterEgg = 0;
		
	}
	
	public Color getRandomColor(){
		Random rand = new Random();
		float r = rand.nextFloat();
		float g = rand.nextFloat();
		float b = rand.nextFloat();

		return new Color(r, g, b);
	}
	
	public double getBoardWidth() {
		return this.boardWidth;
		
	}
	
	public double getBoardHeight() {
		return this.boardHeight;
		
	}
	
	public double getScaleX() {
		return this.scaleX;
	}
	
	public double getScaleY() {
		return this.scaleY;
	}
	
	public int getShiftX() {
		return this.shiftX;
	}
	
	public int getShiftY() {
		return this.shiftY;
	}
	
	public CustomizeTokens getTokenTextures() {
		return this.tokenTexture;
		
	}

	public static void main(String args[]){
		// for testing

//		JFrame newFrame = new JFrame("Board");
//		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		newFrame.setSize(2000,2000);
//
//		GameBoard gb = new GameBoard(100, 100, 1, 1);
//		newFrame.add(gb, BorderLayout.CENTER);
//
//		gb.movePlayer(0, 30);
//		gb.movePlayer(1, 30);
//		gb.movePlayer(2, 30);
//		gb.movePlayer(3, 30);
//		
//		gb.drawIcon(GameBoard.BANK_ICON);
//		
//		newFrame.setVisible(true);
//		gb.enableEasterEgg();
	}


}
