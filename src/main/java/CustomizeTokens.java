import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

public class CustomizeTokens {
	
	private Image[] image = new Image[4];
	
	public CustomizeTokens() {
		// initializes the image array
		for(int i = 0; i < 4; i++)
			image[i] = null;
		
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
		if(this.isImageNull(playerIndex) == false) {
			// draws the selected image
			Image scaledImage = this.getScaledImage(this.image[playerIndex], width, height);
			g.drawImage(scaledImage, x, y, null);
		
		}else {
			// draws the default colored squares
			g.fillRect(x, y, width, height);
		}
		
	}

}
