package bdt.screen;

import java.awt.Toolkit;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.FileDialog;

import bdt.screen.utils.WindowScreen;
import bdt.screen.utils.ThreadShutDown;
import bdt.screen.utils.ThreadInitialize;
import bdt.game.GameSession;

public final class Screen {
	public static final int PORT = 7218;
	private static WindowScreen screen;
	public static GameSession session;
	private static Font font;
	public static boolean isPaused;
	public static int transX;
	public static int transY;

	public static void toggle() {
		screen.toggleFullScreen();
	}

	public static void translate(Point point) {
		screen.translate(point);
	}

    public static void pause(){
    	isPaused = true;
    }

	public static void resume() {
		isPaused = false;
	}
	
	public static void start() {
		screen.start();
	}
	
	public static void stop() {
		screen.stop();
	}
	
	public static void setFont(Font font) {
		Screen.font = font;
	}

	public static boolean isFocused() {
		return screen.isFocused();
	}

	public static boolean inFullScreenMode() {
		return screen.isFullScreen();
	}

	public static boolean canGoFullScreen() {
		return screen.canGoFullScreen();
	}

	public static int getDisplayModeWidth() {
		return screen.getDisplayModeWidth();
	}

	public static int getDisplayModeHeight() {
		return screen.getDisplayModeHeight();
	}

	public static int getScreenWidth() {
		return 600;
	}

	public static int getScreenHeight() {
		return 180;
	}

	public static int getWidth() {
		return (Screen.inFullScreenMode()) ? 775 : 780;
	}

	public static int getHeight() {
		return 600;
	}

	public static String openFileDialog(String title, String directory) {
		Screen.pause();
		FileDialog dialog = new FileDialog(screen, title, FileDialog.LOAD);
		dialog.setDirectory(directory);
		dialog.setVisible(true);
		Screen.resume();
		return dialog.getFile();
	}

	public static String saveFileDialog(String title, String directory) {
		Screen.pause();
		FileDialog dialog = new FileDialog(screen, title, FileDialog.SAVE);
		dialog.setDirectory(directory);
		dialog.setVisible(true);
		Screen.resume();
		return dialog.getFile();
	}

	public static FontMetrics getFontMetrics(Font font) {
		return screen.getFontMetrics(font);
	}

	public static Font getFont(int size) {
		return font.deriveFont(Font.BOLD, size);
	}

	public static Toolkit getToolkit() {
		return screen.getToolkit();
	}

	public static void setCursor(Cursor newCursor) {
		screen.setCursor(newCursor);
	}

	public static boolean isKeyPressed(int key) {
		return screen.isKeyPressed(key);
	}

	public static void setGameSession(GameSession session) {
		session.init();
		screen.setTitle(session.getTitle());
		Screen.isPaused = false;
		Screen.session = session;
	}

	public static void repaint() {
		screen.repaint();
	}

	public static void shutdown() {
		new ThreadShutDown();
		Screen.stop();
	}

	private static void initialize(String[] args) {
		new ThreadInitialize(args);
	}

	public static void main(String[] args) {
		screen = new WindowScreen();
		screen.setVisible(true);
		Screen.isPaused = false;
		Screen.transX = screen.getTransX();
		Screen.transY = screen.getTransY();
		Screen.setFont(new Font("Ariels", Font.BOLD, 0));
		Screen.initialize(args);
	}
}
