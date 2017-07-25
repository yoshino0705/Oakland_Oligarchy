import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CustomizeTokens {
	
	private Image[] image = new Image[4];
	private Color[] playerColor = new Color[4];
	private final String[] options = {"Choose Color", "Choose Image"};
	
	public CustomizeTokens() {
		// initializes the arrays
		for(int i = 0; i < 4; i++) {
			image[i] = null;
			playerColor[i] = null; 
		}
		
	}
	
	public void setImage(int playerIndex) {
		JFileChooser fc = new JFileChooser();
		File workingDirectory = new File(System.getProperty("user.dir"));
		fc.setCurrentDirectory(workingDirectory);
		int rVal = fc.showSaveDialog(null);
		
		if(rVal == JFileChooser.APPROVE_OPTION){
			File imageFile = fc.getSelectedFile();
			
			try {
				this.image[playerIndex] = ImageIO.read(imageFile);
				JOptionPane.showMessageDialog(null, "Icon Changed!");
				
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, "Invalid Image!");
			}
			
		}
		
	}
	
	public boolean isImageNull(int playerIndex) {
		if (this.image[playerIndex] == null)
			return true;
		else 
			return false;
		
	}
	
	public Image getTokenImage(int playerIndex) {
		return this.image[playerIndex];
		
	}
	
	private Image getScaledImage(Image srcImg, int w, int h){
	    BufferedImage resizedImg = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D g2 = resizedImg.createGraphics();

	    g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
	    g2.drawImage(srcImg, 0, 0, w, h, null);
	    g2.dispose();

	    return resizedImg;
	}
	
	public void drawPlayer(Graphics g, int x, int y, int width, int height, int playerIndex) {
		// sets to custom color if it has been specified by setPlayerColor(int) already
		if(this.playerColor[playerIndex] != null)
			g.setColor(this.playerColor[playerIndex]);
		
		if(this.isImageNull(playerIndex) == false) {
			// draws the selected image
			Image scaledImage = this.getScaledImage(this.image[playerIndex], width, height);
			g.drawImage(scaledImage, x, y, null);
		
		}else {
			// draws the default colored squares
			g.fillRect(x, y, width, height);
		}
		
	}
	
	public void setPlayerColor(int playerIndex) {
		try {
			Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
			this.playerColor[playerIndex] = newColor;
			
		}catch(Exception e) {}
		
	}
	
	public void setPlayerColor(OaklandOligarchy game) {
		for(int i = 0; i < game.allPlayers.size(); i++) {
			if(this.playerColor[i] != null) {
				String hex = String.format("#%02x%02x%02x", this.playerColor[i].getRed(), this.playerColor[i].getGreen(), this.playerColor[i].getBlue()); 
				game.allPlayers.get(i).color = hex;
			}
		}
		
	}
	
	public Color getPlayerColor(int playerIndex) {
		if(playerIndex > 0 && playerIndex < 4) {
			return this.playerColor[playerIndex];
			
		}else {
			// return default color
			return Color.BLACK;
			
		}
		
	}
	
	public void showSelectionDialog(int playerIndex) {
		String choice = (String) JOptionPane.showInputDialog(null,"Please select an option","Change Icon", JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
		try {
			if(choice.equals(this.options[0])){
				// select from colors
				this.setPlayerColor(playerIndex);
				
			}else{
				// select from images
				this.setImage(playerIndex);
			}
			
		}catch(Exception e) {}
	}
	
	public static void main(String args[]) {
		Color newColor = JColorChooser.showDialog(null, "Choose a color", Color.RED);
		System.out.println(newColor.toString());
	}

}
