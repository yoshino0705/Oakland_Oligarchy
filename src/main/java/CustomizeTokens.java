import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.List;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;
import javax.swing.JColorChooser;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CustomizeTokens {
	
	private Image[] image = new Image[4];
	private String[] imagePath = new String[4];	// stores paths of images
	private Color[] playerColor = new Color[4];
	private final String[] options = {"Choose Color", "Choose Image"};
	private int[] type = new int[4];		// 0 for color, 1 for image
	private final String DELIMITER = "=";
	
	public CustomizeTokens() {
		// initializes the arrays
		for(int i = 0; i < 4; i++) {
			image[i] = null;
			playerColor[i] = null; 
			type[i] = -1;
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
				this.imagePath[playerIndex] = imageFile.getPath();
				this.type[playerIndex] = 1;
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
		if(this.type[playerIndex] == 0) {
			g.setColor(this.playerColor[playerIndex]);
			g.fillRect(x, y, width, height);
		}
		else if(this.type[playerIndex] == 1) {
			// draws the selected image
			Image scaledImage = this.getScaledImage(this.image[playerIndex], width, height);
			g.drawImage(scaledImage, x, y, null);
		
		}else {
			// draws the default colored squares
			g.fillRect(x, y, width, height);
		}
		
	}
	
	public void setPlayerColor(int playerIndex) {
		this.type[playerIndex] = 0;
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
				//this.type[playerIndex] = 0;
				
			}else{
				// select from images
				this.setImage(playerIndex);
				//this.type[playerIndex] = 1;
			}
			
		}catch(Exception e) {}
	}
	
	public String toString(int playerIndex) {
		if(this.type[playerIndex] == 0) {
			String hex = "" + this.playerColor[playerIndex].getRed() + DELIMITER + this.playerColor[playerIndex].getGreen() + DELIMITER + this.playerColor[playerIndex].getBlue(); 
			return "" + playerIndex + DELIMITER + this.type[playerIndex] + DELIMITER + hex;
		}else
			return "" + playerIndex + DELIMITER + this.type[playerIndex] + DELIMITER + this.imagePath[playerIndex];
		
	}
	
	public void loadFromString(String str) {
		String tokens[] = str.split(DELIMITER);

		try {
			int playerIndex = Integer.parseInt(tokens[0]);
			int type = Integer.parseInt(tokens[1]);
			
			if(type == 0) {
				// color
				this.type[playerIndex] = 0;
				this.playerColor[playerIndex] = new Color(Integer.parseInt(tokens[2]), Integer.parseInt(tokens[3]), Integer.parseInt(tokens[4]));
				//System.out.println("Color changed for player index: " + playerIndex + " Color: " + this.playerColor[playerIndex].getRGB());
			}
			else if(type == 1) {
				// image
				this.type[playerIndex] = 1;
				this.imagePath[playerIndex] = tokens[2];
				this.image[playerIndex] = ImageIO.read(new File(this.imagePath[playerIndex]));
				//System.out.println("Image changed for player index: " + playerIndex + " Path: " + this.imagePath[playerIndex]);

			}				
			else {
				this.type[playerIndex] = -1;
				//System.out.println("Nothing changed");
			}
				
			
		}catch(Exception e){System.out.println("Error Occurred");}
		
	}
	
	public static void main(String args[]) {
		String str = "A\\B\\C";
		String str2 = str.replace("\\", "/");
		System.out.println(str2);
	}

}
