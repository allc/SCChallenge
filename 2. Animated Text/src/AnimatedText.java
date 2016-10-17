import java.applet.Applet;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

public class AnimatedText extends Applet implements ActionListener {

	private Timer timer;
	private Graphics graphics;
	private int width, height;
	private String text = "Hello world!";
	private int textWidth;
	private int textX, textY;
	private boolean right;
	private TextField textField;
	
	public void start() {
		graphics = getGraphics();
		setup();
		// draw graphics components
		setLayout(null);
		int componentsHeight = 20;
		/*// label
		Label textLabel = new Label();
		String labelText = "Text: ";
		textLabel.setText(labelText);
		int labelLength = graphics.getFontMetrics().stringWidth(labelText);
		textLabel.setSize(labelLength, componentsHeight);
		textLabel.setLocation(0, 0);
		add(textLabel);*/
		int labelLength = 0;
		// textField
		int textFieldLength = 100;
		textField = new TextField();
		textField.setSize(textFieldLength, componentsHeight);
		textField.setLocation(labelLength, 0);
		add(textField);
		// button
		Button changeText = new Button();
		String buttonLabel = "Change Text!";
		changeText.setLabel(buttonLabel);
		changeText.setSize(graphics.getFontMetrics().stringWidth(buttonLabel) + 10, componentsHeight);
		changeText.setLocation(labelLength + textFieldLength, 0);
		add(changeText);
		changeText.addActionListener(this);
		
		timer = new Timer(true);
		timer.scheduleAtFixedRate(
				new TimerTask() {
					public void run() {
						draw();
					}
				}, 0, 50);
	}
	
	public void setup() {
		width = getWidth();
		height = getHeight();
		changeTextWidth();
		textX = 0;
		textY = 40;
		right = true;
	}
	
	public void stop() {
		timer.cancel();
	}
	
	public void draw() {
		if (right) {
			if (textX < width - textWidth) {
				textX++;
			} else {
				right = false;
			}
		} else {
			if (textX > 0) {
				textX--;
			} else {
				right = true;
			}
		}
		paint(graphics);
	}
	
	public void paint(Graphics g) {
		g.clearRect(0, 30, width, height - 30);
		g.drawString(text, textX, textY);
	}
	
	public void changeTextWidth() {
		textWidth = graphics.getFontMetrics().stringWidth(text);
	}
	
	public void actionPerformed(ActionEvent ae) {
		text = textField.getText().equals("") ? text : textField.getText();
		changeTextWidth();
	}

}