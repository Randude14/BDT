package bdt.screen.utils;

import javax.swing.JFrame;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Point;

@SuppressWarnings("serial")
public class WindowScreen extends JFrame implements SwingConstants {
	private ScreenGraphicsEnvironment environment;
	private ScreenMouseListener mouseListener;
	private ScreenKeyListener keyListener;
	private ScreenPainter paintScreen;
	private Timer timer;

	public WindowScreen() {
		timer = new Timer(10, new ScreenActionListener());
		environment = new ScreenGraphicsEnvironment(this);
		paintScreen = new ScreenPainter();
		add(paintScreen);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		initListeners();
		setResizable(false);
		pack();
		setLocationRelativeTo(null);
	}

	public void initListeners() {
		mouseListener = new ScreenMouseListener();
		keyListener = new ScreenKeyListener();
		paintScreen.addMouseListener(mouseListener);
		paintScreen.addMouseMotionListener(mouseListener);
		paintScreen.addMouseWheelListener(mouseListener);
		addKeyListener(keyListener);
	}
	
	public void start(){
		timer.start();
	}
	
	public void stop(){
		timer.stop();
	}

	public void toggleFullScreen() {
		environment.toggleScreen();
	}

	public void translate(Point point) {
		environment.translate(point);
	}

	public boolean canGoFullScreen() {
		return environment.canGoFullScreen();
	}

	public int getDisplayModeWidth() {
		return environment.getDisplayModeWidth();
	}

	public int getDisplayModeHeight() {
		return environment.getDisplayModeHeight();
	}
	
	public boolean isFullScreen(){
		return environment.fullScreen();
	}
	
	public int getTransX() {
	return environment.transX;
	}
	
	public int getTransY() {
	return environment.transY;
	}

	public boolean isKeyPressed(int key) {
		return keyListener.isKeyPressed(key);
	}

	public ScreenPainter getScreenPainter() {
		return paintScreen;
	}

}
