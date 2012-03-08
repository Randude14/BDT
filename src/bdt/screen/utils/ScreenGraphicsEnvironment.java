package bdt.screen.utils;

import java.awt.GraphicsEnvironment;
import java.awt.GraphicsDevice;
import java.awt.DisplayMode;
import java.awt.Dimension;
import java.awt.Point;

import bdt.screen.Screen;

public class ScreenGraphicsEnvironment {
	private WindowScreen screen;
	private DisplayMode normal;
	private DisplayMode dm;
	private GraphicsEnvironment ge;
	private GraphicsDevice gd;
	private boolean fullScreen;
	public int transX;
	public int transY;

	public ScreenGraphicsEnvironment(WindowScreen screen) {
		ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		gd = ge.getDefaultScreenDevice();
		normal = gd.getDisplayMode();
		this.screen = screen;
		fullScreen = false;

		for (DisplayMode display : gd.getDisplayModes()) {

			if (display.getWidth() == 800 && display.getHeight() == 600) {
				dm = display;
				break;
			}

		}

		if (dm != null) {
			transX = -10;
			transY = 0;
		}

		else {
			transX = transY = 0;
			dm = null;
		}

	}

	public void toggleScreen() {

		if (!canGoFullScreen()) {
			return;
		}

		if (fullScreen) {
			gd.setDisplayMode(normal);
			screen.setVisible(false);
			screen.getScreenPainter().setPreferredSize(new Dimension(780, 600));
			screen.pack();
			screen.dispose();
			screen.setUndecorated(false);
			gd.setFullScreenWindow(null);
			screen.setLocationRelativeTo(null);
			screen.setVisible(true);
		}

		else {
			screen.setVisible(false);
			screen.getScreenPainter().setPreferredSize(new Dimension(800, 600));
			screen.pack();
			screen.dispose();
			screen.setUndecorated(true);
			gd.setFullScreenWindow(screen);
			gd.setDisplayMode(dm);
			screen.setVisible(true);
		}

		if (Screen.session != null) {
			Screen.session.toggleScreen();
		}

		Screen.repaint();
		fullScreen = !fullScreen;
	}

	public boolean fullScreen() {
		return fullScreen;
	}

	public void translate(Point point) {

		if (fullScreen) {
			point.x -= 10;
		}

	}

	public int getDisplayModeWidth() {
		return dm.getWidth();
	}

	public int getDisplayModeHeight() {
		return dm.getHeight();
	}

	public boolean canGoFullScreen() {
		return dm != null;
	}

}
