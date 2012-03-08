package bdt.game;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.awt.image.BufferedImage;
import java.awt.Graphics;

import bdt.event.Mouse;
import bdt.event.MouseListener;
import bdt.event.KeyListener;
import bdt.event.WindowListener;
import bdt.tools.FileLoader;

public abstract class GameSession implements MouseListener, KeyListener,
		WindowListener {

	protected GameSession(int id) {

		if (sessions.containsKey(id)) {
			throw new IllegalArgumentException("Session ID " + id
					+ " is already taken!");
		} else {
			sessions.put(id, this);
			this.id = id;
		}

	}

	public void mouseMoved(int x, int y) {
	}

	public void mouseDragged(Mouse mouse, int x, int y) {
	}

	public void mouseClicked(Mouse mouse, int x, int y) {
	}

	public void mouseReleased(Mouse mouse, int x, int y) {
	}

	public void mousePressed(Mouse mouse, int x, int y) {
	}

	public void mouseEntered(int x, int y) {
	}

	public void mouseExited() {
	}

	public void mouseWheel(int clicks, int x, int y) {
	}

	public void keyPressed(int key) {
	}

	public void keyReleased(int key) {
	}

	public void toggleScreen() {
	}

	public Map<String, Integer> getKeys() {
		return null;
	}

	public void renderBackGround(Graphics g) {
		g.drawImage(background, 75, 0, null);
	}

	public void render(Graphics g) {
	}

	public void init() {
	}

	public String toString() {
		return getTitle();
	}

	public abstract void update();

	public abstract String getTitle();

	public static final Map<Integer, GameSession> sessions;
	public static final GameSession levelEditor;
	public static final GameSession clientGame;
	public static final GameSession mainMenu;
	private static final BufferedImage background;
	public final int id;

	static {
		sessions = new HashMap<Integer, GameSession>();
		levelEditor = new LevelEditor(0x218A);
		clientGame = new BDTGame(0x218B);
		mainMenu = new MainMenu(0x218C);
		background = FileLoader.loadImage("Gui/foreground.png");
	}

	public static GameSession getGameSession(int id) {
		return sessions.get(id);
	}

	public static List<Integer> getSessionsAsList() {
		return new ArrayList<Integer>(sessions.keySet());
	}

}
