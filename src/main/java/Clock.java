import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

//this entire class is just a self updating JLabel that keeps time
public class Clock extends JLabel implements Runnable{
	int hours = 0;
	int minutes = 0;
	int seconds = 0;

	//this method will start the counting of the clock when start() is called
	public void run(){
		System.out.println("run called");
		while(true){
			try {
				Thread.sleep(1000);
				tick();//increase clock by 1 second
				this.setText(getTime());
			} catch(Exception e){
				System.out.println("Exception in thread loop:\n"+ e);
				//e.printStackTrace();
			}
		}
	}//end run()


	//for testing purposes
	public static void main(String[] args) {
		JFrame frame = new JFrame("test clock");
		Clock my_clock = new Clock(0,0,0);
		frame.add(my_clock);
		frame.setSize(600,600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		Thread my_thread = new Thread(my_clock);
		my_thread.start();
	}//end main()
	
	public Clock(int hours, int minutes, int seconds){
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
		//set style stuff
		this.setFont(new Font("Times", Font.BOLD, 24));
		this.setPreferredSize(new Dimension(80,50));
		//set the time
		this.setText(getTime());
	}//end Clock()

	//increase time by 1 second
	void tick(){
		if(seconds == 59){
			this.seconds = 0;
			this.minutes++;
			if(this.minutes == 60){
				this.minutes = 0;
				this.hours++;
			}
		}else{
			this.seconds++;
		}
	}//end tick()

	//return a string version of the time in format[00:00:00]
	public String getTime(){
		String time_str = "";
		if(this.hours<10){
			time_str += ("0"+Integer.toString(this.hours));
		}else{
			time_str += Integer.toString(this.hours);
		}
		time_str += ":";
		if(this.minutes<10){
			time_str += ("0"+Integer.toString(this.minutes));
		}else{
			time_str += Integer.toString(this.minutes);
		}
		time_str += ":";
		if(this.seconds<10){
			time_str += ("0"+Integer.toString(this.seconds));
		}else{
			time_str += Integer.toString(this.seconds);
		}
		return time_str;
	}//end getTime()



}//end of class Clock
