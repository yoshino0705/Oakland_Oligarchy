import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import javax.swing.*;

public class GameBoard extends JPanel{
	/*
		usage:
		GameBoard gb = new GameBoard(0, 0);
		window.add(gb, BorderLayout.CENTER);

	*/

	private int shiftX = 0;
	private int shiftY = 0;
	
	/***********CHANGE SCALE HERE***************/
	private double scaleX = 0.63;
	private double scaleY = 0.63;
	
	private double rectWidthHorizontal = 150 * scaleX;
	private double rectHeightHorizontal = 100 * scaleY;
	private double rectWidthVertical = 100 * scaleX;
	private double rectHeightVertical = 150 * scaleY;
	private double cornerWidth = 200 * scaleX;
	private double cornerHeight = 200 * scaleY;
	
	private double subRectWidthHorizontal = 30 * scaleX;
	private double subRectHeightHorizontal = rectHeightHorizontal;
	private double subRectWidthVertical = rectWidthVertical;
	private double subRectHeightVertical = 30 * scaleY;
	
	private double boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
	private double boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;
	
	public GameBoard(int x, int y){
		shiftX = x;
		shiftY = y;

	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
	    drawBasicBoard(g);
	    drawSubRect(g);
	    drawText(g, "Oakland Oligarchy", boardWidth / 2.0 - 200, boardHeight / 2.0, new Font("TimesNewRoman", Font.BOLD, 50));
	    
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
	
	public static void main(String args[]){
		// for testing
		
		JFrame newFrame = new JFrame("Board");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setSize(2000,2000);
		
		GameBoard gb = new GameBoard(300, 300);
		newFrame.add(gb, BorderLayout.CENTER);
		
		newFrame.setVisible(true);
		
		
	}
}
