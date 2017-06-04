import java.awt.BorderLayout;
import java.awt.Graphics;
import javax.swing.*;

public class GameBoard extends JPanel{
	private int shiftX = 0;
	private int shiftY = 0;
	
	private int rectWidthHorizontal = 150;
	private int rectHeightHorizontal = 100;
	private int rectWidthVertical = 100;
	private int rectHeightVertical = 150;
	private int cornerWidth = 200;
	private int cornerHeight = 200;
	
	private int boardWidth = cornerWidth * 2 + rectWidthVertical * 8;
	private int boardHeight = cornerHeight * 2 + rectHeightHorizontal * 8;
	
	public GameBoard(int x, int y){
		shiftX = x;
		shiftY = y;
		
	}
	
	public void paintComponent(Graphics g) {
	    super.paintComponent(g);
	    
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
	
	private void drawCorner(Graphics g, int x, int y){
		g.drawRect(x, y, cornerWidth, cornerHeight);
	}
	
	private void drawVerticalBox(Graphics g, int x, int y){
		g.drawRect(x, y, rectWidthVertical, rectHeightVertical);
	}
	
	private void drawHorizontalBox(Graphics g, int x, int y){
		g.drawRect(x, y, rectWidthHorizontal, rectHeightHorizontal);
	}
	
	public static void main(String args[]){
		// for testing
		
		JFrame newFrame = new JFrame("Board");
		newFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		newFrame.setSize(2000,2000);
		
		GameBoard gb = new GameBoard(300,300);
		newFrame.add(gb, BorderLayout.CENTER);
		
		newFrame.setVisible(true);
		
		
	}
}
