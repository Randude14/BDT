package bdt.game;

import java.awt.image.BufferedImage;
import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Cursor;
import java.util.HashMap;
import java.util.Map;

import bdt.world.World;
import bdt.world.Point;
import bdt.world.Block;
import bdt.world.WorldManager;
import bdt.event.Mouse;
import bdt.event.Pausable;
import bdt.gui.screen.GuiLevelEditor;
import bdt.gui.screen.GuiScreen;
import bdt.tools.MathHelper;
import bdt.tools.FileLoader;
import bdt.tools.KeyOptions;
import bdt.screen.Screen;

public class LevelEditor extends GameSession implements Pausable {
	private GuiLevelEditor bar;
	private GuiScreen pauseScreen;
	private Rectangle dragRect;
	private Cursor pencil;
	private Box[][] grid;
	private World world;
	private FontMetrics metrics;
	private Font font;
	private String path;
	private final String defaultPath = WorldManager.file + "/";
	private boolean mouseClickedGui;
	private boolean mouseClicked;
	private boolean drag;
	private int blockID;
	private int pathID;
	public int mouseX;
	public int mouseY;
	private int clickX;
	private int clickY;

	protected LevelEditor(int id) {
		super(id);
		bar = new GuiLevelEditor(this);
		font = Screen.getFont(20);
		metrics = Screen.getFontMetrics(font);
		dragRect = new Rectangle();
		BufferedImage image = FileLoader.loadImage("Gui/cursor-pencil.png");
		pencil = Screen.getToolkit().createCustomCursor(image,
				new java.awt.Point(0, 31), "Pencil");
		grid = new Box[World.NUM_ROWS][World.NUM_COLS];

		for (int r = 0; r < grid.length; r++) {

			for (int c = 0; c < grid[r].length; c++) {
				grid[r][c] = new Box(r * 30, c * 30);
			}

		}

		grid[0][0].setText("1");

		for (int cntr = 1; cntr < grid[0].length; cntr++) {
			grid[cntr][0].setText("" + (cntr + 1));
		}

		for (int cntr = 1; cntr < grid[0].length; cntr++) {
			grid[0][cntr].setText("" + (cntr + 1));
		}

	}

	public void pause() {
		Screen.pause();
		bar.disableButtons();
	}

	public void resume() {
		Screen.resume();
		bar.enableButtons();
	}

	public void setPauseScreen(GuiScreen screen) {
		pauseScreen = screen;
	}

	public Map<String, Integer> getKeys() {
		Map<String, Integer> keys = new HashMap<String, Integer>();
		keys.put("Path Up", KeyEvent.VK_W);
		keys.put("Path Right", KeyEvent.VK_D);
		keys.put("Path Down", KeyEvent.VK_S);
		keys.put("Path Left", KeyEvent.VK_A);
		keys.put("Path Delete", KeyEvent.VK_DELETE);
		return keys;
	}

	public void mouseMoved(int x, int y) {

		if (Screen.isPaused) {
			pauseScreen.hover(x, y);
			return;
		}

		bar.hover(x, y);
		mouseX = x;
		mouseY = y;

		if (mouseX <= 600) {
			Screen.setCursor(pencil);
		}

		else {
			Screen.setCursor(Cursor.getDefaultCursor());
		}

	}

	public void mouseDragged(Mouse mouse, int x, int y) {

		if (mouseClickedGui) {
			return;
		}
		
		if(!mouseClicked) {
			return;
		}

		int x1 = clickX;
		int y1 = clickY;
		int x2 = x;
		int y2 = y;
		int rx = 0;
		int ry = 0;
		int w = 0;
		int l = 0;
		drag = true;

		if (x1 > x2) {
			l = x1 - x2;
			rx = x2;
		}

		else {
			l = x2 - x1;
			rx = x1;
		}

		if (y1 > y2) {
			w = y1 - y2;
			ry = y2;
		}

		else {
			w = y2 - y1;
			ry = y1;
		}

		dragRect.setBounds(rx, ry, l, w);
	}

	public void mousePressed(Mouse mouse, int x, int y) {

		if (Screen.isPaused) {

			if (mouse == Mouse.LEFT_CLICK) {
				pauseScreen.mouseClicked(x, y);
			}

			return;
		}

		if (mouse == Mouse.LEFT_CLICK) {
			mouseClickedGui = bar.mouseClicked(x, y);
		}

		mouseX = x;
		mouseY = y;
		clickX = x;
		clickY = y;
		Point point = getPointAt(x, y);
		mouseClicked = mouse == Mouse.LEFT_CLICK;

		if (point == null) {
			return;
		}

		if (mouse == Mouse.LEFT_CLICK) {

			if (world.isPathEmpty()) {
				world.addPath(point, this.pathID);
			}

			else {
				int blockID = world.isPath(point) ? this.pathID : this.blockID;
				world.setBlockID(point, blockID);
			}

		}
		
		else if(mouse == Mouse.RIGHT_CLICK) {
			
			if(!world.isPath(point)) {
				world.setBlockID(point, blockID);
			}
			
		}

	}

	public void mouseReleased(Mouse mouse, int x, int y) {

		for (int r = 0; r < world.length; r++) {

			for (int c = 0; c < world.width; c++) {
				int x1 = r * 30;
				int y1 = c * 30;

				if (dragRect.intersects(x1, y1, 29, 29)) {
					int blockID = (world.isPath(r, c)) ? this.pathID
							: this.blockID;
					world.setBlockID(r, c, blockID);
				} 

			}

		}

		dragRect.setBounds(0, 0, 0, 0);
		mouseClicked = false;
		drag = false;
	}

	public void mouseEntered(int x, int y) {
		mouseX = x;
		mouseY = y;
		
		if(drag && !mouseClickedGui) {
			mouseDragged(Mouse.LEFT_CLICK, x, y);
		}
		
	}

	public void mouseExited() {
		mouseX = -1;
		mouseY = -1;	
	}

	public void mouseWheel(int clicks, int x, int y) {
		bar.scroll(clicks, x, y);
	}

	public void keyPressed(int key) {

		if (Screen.isPaused) {
			pauseScreen.keyPressed(key);
			return;
		}

		Point point = world.getLastPointOfPath();

		if (point == null) {
			return;
		}

		int x = point.getX();
		int y = point.getY();

		if (KeyOptions.matchKey("Path Up", key) && world.isValid(x, y - 1)) {
			world.addPath(new Point(x, y - 1), this.pathID);
		}

		else if (KeyOptions.matchKey("Path Right", key)
				&& world.isValid(x + 1, y)) {
			world.addPath(new Point(x + 1, y), this.pathID);
		}

		else if (KeyOptions.matchKey("Path Down", key)
				&& world.isValid(x, y + 1)) {
			world.addPath(new Point(x, y + 1), this.pathID);
		}

		else if (KeyOptions.matchKey("Path Left", key)
				&& world.isValid(x - 1, y)) {
			world.addPath(new Point(x - 1, y), this.pathID);
		}

		else if (KeyOptions.matchKey("Path Delete", key)) {
			world.removePath(blockID);
		}

	}

	private Point getPointAt(int x, int y) {

		for (int r = 0; r < world.length; r++) {

			for (int c = 0; c < world.width; c++) {
				int x1 = r * 30;
				int y1 = c * 30;

				if (MathHelper.doesRectContainPoint(x, y, x1, y1, 30, 30)) {
					return new Point(r, c);
				}

			}

		}

		return null;
	}

	public World getWorld() {
		return world;
	}

	public String getTitle() {
		return "Level Editor: " + world;
	}

	public String getWorldPath() {
		return path;
	}

	public void update() {
		blockID = bar.getSelectedBlockID();
		pathID = bar.getSelectedPathID();
	}

	public void render(Graphics g) {
		world.render(g);

		if (!Screen.isPaused) {
			bar.render(g);
			renderDragBox(g);

			if (bar.shouldShowGrid()) {
				Point point = getPointAt(mouseX, mouseY);

				if (point != null && !drag) {
					int x = point.getX() * 30;
					int y = point.getY() * 30;
					int blockID = (world.isPath(point)) ? this.pathID
							: this.blockID;
					Block block = Block.getBlockAtID(blockID);
					block.render(g, x, y);
					renderGrid(g);
					g.setColor((bar.shouldShowGrid()) ? Color.orange
							: Color.black);
					g.drawRect(x, y, 30, 30);
				}

				else {
					renderGrid(g);
				}

				renderBoxAtEndOfPath(g);
				int x = (mouseX > 600) ? 600 : mouseX;
				int y = (mouseY > 600) ? 600 : mouseY;
				g.setColor(Color.red);
				g.drawLine(x, 0, x, 600);
				g.drawLine(0, y, 600, y);
			}

			else {
				renderBoxAtEndOfPath(g);
				renderBlockAtCursor(g);
			}

		}

		else {
			renderBackGround(g);
			bar.render(g);
			pauseScreen.render(g);
		}

	}

	private void renderBlockAtCursor(Graphics g) {
		
		if(drag) {
			return;
		}
		
		Point point = getPointAt(mouseX, mouseY);

		if (point != null) {
			int x = point.getX() * 30;
			int y = point.getY() * 30;

			if (!world.isPathEmpty()) {
				int blockID = (world.isPath(point)) ? this.pathID
						: this.blockID;
				Block block = Block.getBlockAtID(blockID);
				block.render(g, x, y);
				g.setColor((bar.shouldShowGrid()) ? Color.orange : Color.black);
				g.drawRect(x, y, 30, 30);
			}

			else {
				Block block = Block.getBlockAtID(pathID);
				block.render(g, x, y);
				g.setColor((bar.shouldShowGrid()) ? Color.orange : Color.black);
				g.drawRect(x, y, 30, 30);
			}

		}

	}

	private void renderBoxAtEndOfPath(Graphics g) {
		Point point = world.getLastPointOfPath();

		if (point == null) {
			return;
		}

		g.setColor((bar.shouldShowGrid()) ? Color.orange : Color.black);
		g.drawRect(point.getX() * 30, point.getY() * 30, 30, 30);
	}

	private void renderDragBox(Graphics g) {

		for (int r = 0; r < world.length; r++) {

			for (int c = 0; c < world.width; c++) {
				int x = r * 30;
				int y = c * 30;
				int blockID = (world.isPath(r, c)) ? this.pathID : this.blockID;
				if (dragRect.intersects(x, y, 30, 30)) {
					Block block = Block.getBlockAtID(blockID);
					block.render(g, x, y);
				}

			}

		}

	}

	private void renderGrid(Graphics g) {

		for (int r = 0; r < grid.length; r++) {

			for (int c = 0; c < grid[r].length; c++) {
				grid[r][c].render(g);
			}

		}

	}

	public void init(World world, String path, boolean flag) {
		this.world = world;
		bar.init(flag);
		blockID = bar.getSelectedBlockID();
		pathID = bar.getSelectedPathID();
		this.path = path;
	}

	public void init(World world, boolean flag) {
		init(world, defaultPath + world.getName() + ".world", flag);
	}

	public void init(World world) {
		init(world, false);
	}

	public void init(String name) {
		World world = new World(name);
		init(world);
	}

	public void init(World world, String path) {
		init(world, path, false);
	}

	private class Box {
		private String text;
		private int x, y;

		public Box(int x, int y) {
			this.x = x;
			this.y = y;
			text = null;
		}

		public void setText(String newText) {
			text = newText;
		}

		public void render(Graphics g) {
			g.setColor(Color.black);
			g.drawRect(x, y, 30, 30);

			if (text != null) {
				g.setFont(font);
				int width = metrics.stringWidth(text);
				int height = metrics.getHeight();
				int x1 = ((30 - width) / 2) + x;
				int y1 = ((30 - height) / 2) + y + 20;
				g.drawString(text, x1, y1);
			}

		}

	}

}
