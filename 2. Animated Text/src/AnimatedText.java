import java.applet.Applet;
import java.awt.Graphics;
/*import java.util.Timer;
import java.util.TimerTask;*/

public class AnimatedText extends Applet {

	//private Timer timer;
	private int width, height;
	private String text = "Hello world!";
	
	public void start() {
		//final Graphics graphics = getGraphics();
		setup();
		/*
		timer = new Timer(true);
		timer.scheduleAtFixedRate(
				new TimerTask() {
					public void run() {
						paint(graphics);
					}
				}, 0, 50);*/
	}
	
	public void setup() {
		width = getWidth();
		height = getHeight();
	}
	
	/*
	public void stop() {
		
	}
	*/
	
	public void paint(Graphics g) {
		int textWidth = g.getFontMetrics().stringWidth(text);
		g.drawString(text, 0, 15);
		while (true) {
			for (int x = 0; x < width - textWidth; x++) {
				g.drawString(text, x, 15);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.clearRect(0, 0, width, height);
			}
			for (int x = width - textWidth; x > 0; x--) {
				g.drawString(text, x, 15);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				g.clearRect(0, 0, width, height);
			}
		}
	}

}