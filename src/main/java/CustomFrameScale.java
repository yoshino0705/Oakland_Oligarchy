import java.awt.Dimension;
import java.awt.Toolkit;

public class CustomFrameScale {
	
	private double screenWidth;
	private double screenHeight;
	
	private double sampleXPixel = 3840;
	private double sampleYPixel = 2160;
	private double baseScale = 1/1.5;
	
	public CustomFrameScale(){
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight = screenSize.getHeight();
		
	}
	
	public double getScreenWidth(){
		return screenWidth;
	}
	
	public double getScreenHeight(){
		return screenHeight;
	}
	
	public double getGameBoardScaleX(){		
		return 0.63;
	}
	
	public double getGameBoardScaleY(){		
		return 0.63;
	}
	
	public double getTradeMenuScaleX(){
		return baseScale * (this.getScreenWidth()/sampleXPixel);
	}
	
	public double getTradeMenuScaleY(){
		return baseScale * (this.getScreenHeight()/sampleYPixel);
	}
	
	public double getTradeMenuScaleX(double xPixel){
		return baseScale * (xPixel/sampleXPixel);
	}
	
	public double getTradeMenuScaleY(double yPixel){
		return baseScale * (yPixel/sampleYPixel);
	}
	
	public static void main(String[] args) {
		CustomFrameScale cfs = new CustomFrameScale();
		System.out.println("X Scale: " + cfs.getTradeMenuScaleX(1440));
		System.out.println("Y Scale: " + cfs.getTradeMenuScaleY(900));
	}

}
