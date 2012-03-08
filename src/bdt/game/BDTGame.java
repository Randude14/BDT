package bdt.game;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.TimeZone;
import java.text.SimpleDateFormat;
import java.io.ObjectOutputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.FileInputStream;
import java.io.File;

import bdt.world.World;
import bdt.world.Point;
import bdt.tower.Tower;
import bdt.tower.TowerState;
import bdt.enemy.Enemy;
import bdt.enemy.LevelGenerator;
import bdt.event.Game;
import bdt.event.Mouse;
import bdt.event.Pausable;
import bdt.screen.Screen;
import bdt.tools.MathHelper;
import bdt.tools.FileLoader;
import bdt.gui.screen.GuiGameOverScreen;
import bdt.gui.screen.GuiPauseGameScreen;
import bdt.gui.screen.GuiToolBar;
import bdt.gui.screen.GuiScreen;

public class BDTGame extends GameSession implements Game, Pausable {
	private Tower tower;
	private final SimpleDateFormat formatter;
	private final String format = "EEE, d MMM yyyy HH:mm:ss";
	private final Map<Integer, Map<String, Object>> saves = loadSaves();
	private final FontMetrics metricsInfo, metricsBar;
	private final File saveFile = new File("Saves");
	private final Font fontInfo, fontBar;
	private GuiScreen pauseScreen;
	private GuiToolBar toolBar;
	private BufferedImage live;
	private TowerState[][] towerStates;
	private Integer[][] towers;
	private List<Enemy> list;
	private List<Enemy> unloadedList;
	private Difficulty difficulty;
	private World world;
	private Type type;
	private boolean running;
	private boolean GAME_OVER;
	private boolean start;
	public int mouseX, mouseY;
	private double money;
	private int lives;
	private int timer;
	private int reset;
	private int wave;

	protected BDTGame(int id) {
		super(id);
		formatter = new SimpleDateFormat(format);
		fontInfo = Screen.getFont(10);
		fontBar = Screen.getFont(20);
		metricsInfo = Screen.getFontMetrics(fontInfo);
		metricsBar = Screen.getFontMetrics(fontBar);
		live = FileLoader.loadImage("Gui/live.png");
		toolBar = new GuiToolBar(this);
		new File("Saves").mkdirs();
		tower = null;
	}

	public void pause() {
		Screen.pause();
		toolBar.disableButtons();
	}

	public void resume() {
		Screen.resume();
		toolBar.enableButtons();
	}

	public Type getType() {
		return type;
	}

	public void toggleScreen() {
		pause();
	}

	public BufferedImage capture() {
		BufferedImage screenshot = new BufferedImage(780, 600,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = screenshot.createGraphics();
		world.render(g);
		renderTower(g);
		renderTowers(g);
		renderEnemies(g);
		renderSideBar(g);
		toolBar.render(g);
		return screenshot;
	}

	public void newGame() {
		init(world, type, difficulty);
	}

	public void killEnemy(Enemy enemy) {
		list.remove(enemy);
		enemy.hit();
		List<Enemy> enemies = enemy.getEnemies();
		list.addAll(enemies);
		money += 2;
	}

	public void spawnEnemy(Enemy enemy) {
		enemy.path(world.getPath());
		list.add(enemy);
	}

	public boolean addTower(int towerID, int x, int y) {

		if (!world.isValid(x, y)) {
			throw new IllegalArgumentException(getTitle()
					+ " - out of bounds (" + x + ", " + y + ")");
		}

		if (!Tower.isTower(towerID)) {
			throw new IllegalArgumentException("Tower ID " + towerID
					+ " does not exist!");
		}

		if (towers[x][y] != -1) {
			return false;
		}

		Tower tower = Tower.getTowerById(towerID);

		if (!tower.isTowerPlaceableOnBlock(world.getBlockID(x, y))) {
			return false;
		}

		towers[x][y] = towerID;
		tower.onTowerPlaced(this, world, x, y);
		return true;
	}

	public Tower removeTower(int x, int y) {

		if (!world.isValid(x, y)) {
			throw new IllegalArgumentException(getTitle()
					+ " - out of bounds (" + x + ", " + y + ")");
		}

		if (towers[x][y] == -1) {
			throw new IllegalArgumentException(getTitle() + " - location at ("
					+ x + ", " + y + ")" + " is already empty");
		}

		Tower tower = Tower.getTowerById(towers[x][y]);
		towers[x][y] = -1;
		tower.onTowerRemoved(this, world, x, y);
		return tower;
	}

	public void addTowerState(TowerState storage, int x, int y) {

		if (!world.isValid(x, y)) {
			throw new IllegalArgumentException(getTitle()
					+ " - out of bounds (" + x + ", " + y + ")");
		}

		towerStates[x][y] = storage;
	}

	public TowerState getTowerState(int x, int y) {

		if (!world.isValid(x, y)) {
			throw new IllegalArgumentException(getTitle()
					+ " - out of bounds (" + x + ", " + y + ")");
		}

		return towerStates[x][y];
	}

	public void removeTowerState(int x, int y) {

		if (!world.isValid(x, y)) {
			throw new IllegalArgumentException(getTitle()
					+ " - out of bounds (" + x + ", " + y + ")");
		}

		towerStates[x][y] = null;
	}

	public void mouseMoved(int x, int y) {
		mouseX = x;
		mouseY = y;

		if (Screen.isPaused) {
			pauseScreen.hover(mouseX, mouseY);
			return;
		}

		toolBar.hover(mouseX, mouseY);
	}

	public void mousePressed(Mouse mouse, int x, int y) {

		if (Screen.isPaused) {

			if (mouse == Mouse.LEFT_CLICK) {
				pauseScreen.mouseClicked(x, y);
			}

			return;
		}

		if (mouse == Mouse.WHEEL_CLICK) {
			return;
		}

		if (mouse == Mouse.LEFT_CLICK && toolBar.mouseClicked(x, y)) {
			return;
		}

		Point cursorPoint = getPointAtCursor();

		if (cursorPoint == null) {
			return;
		}

		int x1 = cursorPoint.getX();
		int y1 = cursorPoint.getY();

		if (mouse == Mouse.LEFT_CLICK) {

			if (tower == null && towers[x1][y1] != -1) {
			}

			else if (money >= tower.getInitialValue()
					&& !canTowerBePlacedHere(x1, y1)
					&& addTower(tower.towerID, x1, y1)) {
				money -= tower.getInitialValue();
			}

		}

		else {
			Tower tower;

			if (towers[x1][y1] != -1
					&& (tower = Tower.getTowerById(towers[x1][y1]))
							.isSellable()) {
				removeTower(x1, y1);
				money += tower.getInitialValue() * .8;
			}

		}

	}

	public void mouseEntered(int x, int y) {
		mouseX = x;
		mouseY = y;
	}

	public void mouseExited() {
		mouseX = -1;
		mouseY = -1;
	}

	public void mouseWheel(int clicks, int x, int y) {

		if (Screen.isPaused) {
			pauseScreen.hover(x, y);
			return;
		}

		if (toolBar.scroll(clicks, x, y)) {
			return;
		}

	}

	public List<Enemy> getEnemyList() {
		return list;
	}

	public void keyPressed(int key) {

		if (key == KeyEvent.VK_P) {
			pause();
			return;
		}

		Point point = getPointAtCursor();

		if (point == null) {
			return;
		}

		if (key != KeyEvent.VK_DELETE) {
			return;
		}

		int x = point.getX();
		int y = point.getY();

		if (towers[x][y] == -1) {
			return;
		}

		removeTower(point.getX(), point.getY());
	}

	public void startGame() {

		if (running) {
			return;
		}

		wave = (start) ? wave : ++wave;
		running = true;
		start = false;
		unloadedList = LevelGenerator.loadLevel(world.getPath(), wave);
		reset = (int) ((50 - wave) * .67);
		reset = (reset < 10) ? 10 : reset;
		timer = reset;
	}

	public void update() {
		updateTowerStorages();

		if (running && !GAME_OVER) {
			updateEnemies();
		}

		if (GAME_OVER && lives <= 0) {
			pause();
			pauseScreen = new GuiGameOverScreen(this);
		}

		int id = toolBar.getSelectedTowerID();
		tower = Tower.getTowerById(id);
	}

	private void updateTowerStorages() {

		for (int r = 0; r < world.length; r++) {

			for (int c = 0; c < world.width; c++) {

				if (towerStates[r][c] != null) {
					towerStates[r][c].update(this);

					if (towerStates[r][c].remove()) {
						removeTowerState(r, c);
						removeTower(r, c);
					}

				}

			}

		}

	}

	private void updateEnemies() {
		for (int cntr = 0; cntr < list.size(); cntr++) {
			Enemy enemy = list.get(cntr);
			enemy.update();

			if (enemy.hasEnemyReachedEnd()) {
				list.remove(enemy);
				lives -= enemy.getLivesCost();

				if (lives <= 0) {
					lives = 0;
					running = false;
					GAME_OVER = true;
				}

				if (!Screen.isFocused()) {
					pause();
				}

			}

		}

		if (GAME_OVER || !running) {
			return;
		}

		if (!unloadedList.isEmpty() && timer-- <= 0) {
			Enemy enemy = unloadedList.remove(0);
			list.add(enemy);
			timer = reset;
			return;
		}

		if (isRoundOver()) {
			running = false;
			toolBar.endRound();
		}

	}

	private boolean isRoundOver() {
		return list.isEmpty() && unloadedList.isEmpty();
	}

	private boolean canTowerBePlacedHere(int x, int y) {

		if (world.isValid(x, y + 1) && towers[x][y + 1] != -1) {
			Tower tower = Tower.getTowerById(towers[x][y + 1]);

			if (tower != null && !tower.canHaveNearbyTowers()) {
				return false;
			}

		}

		if (world.isValid(x, y - 1) && towers[x][y - 1] != -1) {
			Tower tower = Tower.getTowerById(towers[x][y - 1]);

			if (tower != null && !tower.canHaveNearbyTowers()) {
				return false;
			}

		}

		if (world.isValid(x + 1, y) && towers[x + 1][y] != -1) {
			Tower tower = Tower.getTowerById(towers[x + 1][y]);

			if (tower != null && !tower.canHaveNearbyTowers()) {
				return false;
			}

		}

		if (world.isValid(x - 1, y) && towers[x - 1][y] != -1) {
			Tower tower = Tower.getTowerById(towers[x - 1][y]);

			if (tower != null && !tower.canHaveNearbyTowers()) {
				return false;
			}

		}

		return true;
	}

	private Point getPointAtCursor() {

		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world.width; c++) {
				if (MathHelper.doesRectContainPoint(mouseX, mouseY, r * 30,
						c * 30, 30, 30)) {
					return new Point(r, c);
				}
			}
		}

		return null;
	}

	public String getTitle() {
		return "Game: " + world;
	}

	public void setPauseScreen(GuiScreen screen) {
		pauseScreen = screen;
	}

	public void render(Graphics g) {
		world.render(g);
		renderTower(g);
		renderTowers(g);
		renderEnemies(g);
		renderSideBar(g);
		renderTowerInfoBox(g);
		renderErrorMessage(g, mouseX, mouseY - 20);

		if (Screen.isPaused) {
			renderBackGround(g);
			pauseScreen.render(g);
		}

		toolBar.render(g);
	}

	private void renderTowerInfoBox(Graphics g) {
		Point point = getPointAtCursor();

		if (point == null || Screen.isPaused) {
			return;
		}

		int x = point.getX();
		int y = point.getY();
		Tower tower = null;

		if (towers[x][y] != -1) {

			if ((tower = Tower.getTowerById(towers[x][y])).isSellable()) {
				renderTowerInfoBox(g, mouseX, mouseY - 30, tower);
			}

		}

	}

	private void renderEnemies(Graphics g) {

		for (Enemy enemy : list) {
			enemy.render(g);
		}

	}

	private void renderSideBar(Graphics g) {
		g.setFont(fontBar);
		g.setColor(Color.black);
		renderBar(g, 600, 0, 181, 601);
		renderBar(g, 600, 165, 180, 300);
		g.setColor(Color.gray);
		g.fillRect(605, 5, 170, 160);
		g.setColor(Color.yellow);
		String money = String.format("$%,.2f", this.money);
		String live = String.format("%,d", this.lives);
		String wave = String.format("Wave %,d", this.wave);
		int moneyWidth = metricsBar.stringWidth(money);
		int liveWidth = metricsBar.stringWidth(live) + this.live.getWidth(null);
		int waveWidth = metricsBar.stringWidth(wave);
		int liveX = ((180 - liveWidth) / 2) + 600;
		int waveX = ((180 - waveWidth) / 2) + 600;

		g.drawString(money, ((180 - moneyWidth) / 2) + 600, 89);
		g.drawString(live, liveX + this.live.getWidth(null), 44);
		g.drawString(wave, waveX, 134);
		g.drawImage(this.live, liveX, 20, null);
	}

	private void renderErrorMessage(Graphics g, int x, int y) {
		String mess = null;
		Point point = getPointAtCursor();

		if (point == null || Screen.isPaused) {
			return;
		}

		int x1 = point.getX();
		int y1 = point.getY();

		if (tower == null) {
			return;
		}

		else if (money < tower.getInitialValue()) {
			mess = "You do not have enough money";
		}

		else if (!tower.isTowerPlaceableOnBlock(world.getBlockID(x1, y1))
				&& !canTowerBePlacedHere(x1, y1)) {
			mess = "Tower cannot go here";
		}

		if (mess == null) {
			return;
		}

		if (towers[x1][y1] != -1) {
			return;
		}

		int messWidth = metricsInfo.stringWidth(mess);
		g.setFont(fontInfo);
		g.setColor(Color.black);

		if (y < 0) {
			y += 20;
		}

		g.fillRect(x, y, messWidth + 10, 20);
		g.setColor(Color.red);
		g.drawString(mess, x + 5, y + 12);
	}

	private void renderBar(Graphics g, int x, int y, int width, int height) {

		for (int cntr = 0; cntr < 5; cntr++) {
			g.drawRect(x++, y++, width -= 2, height -= 2);
		}

	}

	private void renderTower(Graphics g) {

		if (Screen.isPaused) {
			return;
		}

		Point point = getPointAtCursor();

		if (point == null) {
			return;
		}

		int x = point.getX() * 30;
		int y = point.getY() * 30;

		if (tower != null) {
			tower.render(g, x, y);
		}

	}

	public void renderTowers(Graphics g) {
		for (int r = 0; r < towers.length; r++) {
			for (int c = 0; c < towers[r].length; c++) {
				if (towers[r][c] != -1) {
					if (towerStates[r][c] != null) {
						towerStates[r][c].render(g);
					}
					Tower tower = Tower.getTowerById(towers[r][c]);
					if (tower.renderInGame()) {
						tower.render(g, r * 30, c * 30);
					}
				}
			}
		}
	}

	public void renderTowerInfoBox(Graphics g, int x, int y, Tower tower) {
		g.setFont(fontInfo);
		String name = "Name: " + tower.towerName;
		String value = String.format("Sell for: $%,.2f",
				tower.getInitialValue() * 0.8);
		int nameWidth = metricsInfo.stringWidth(name);
		int valueWidth = metricsInfo.stringWidth(value);
		int width = Math.max(nameWidth, valueWidth) + 10;
		int height = 30;

		if (width + x >= Screen.getWidth()) {
			x -= width;
		}

		if (y < 0) {
			y += height;
		}

		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.yellow);
		g.drawString(name, ((width - nameWidth) / 2) + x, y + 11);
		g.drawString(value, ((width - valueWidth) / 2) + x,
				y + metricsInfo.getHeight() + 9);
	}

	public Map<Integer, Map<String, Object>> getSaves() {
		return saves;
	}

	public Map<String, Object> getSaveAt(int index) {
		return saves.get(index);
	}

	private Map<Integer, Map<String, Object>> loadSaves() {
		Map<Integer, Map<String, Object>> list = new HashMap<Integer, Map<String, Object>>();

		for (int cntr = 1; cntr <= 5; cntr++) {
			Map<String, Object> loadMap = loadGame(cntr);

			if (loadMap != null) {
				list.put(cntr, loadMap);
			}

		}

		return list;
	}

	private String getCurrentDate() {
		Date currentDate = new GregorianCalendar(TimeZone.getDefault())
				.getTime();
		String date = formatter.format(currentDate);
		return date;
	}

	public boolean isSlotTaken(int slot) {
		return saves.get(slot) != null;
	}

	public void deleteGame(int slot) {
		new File("Saves/slot" + slot + ".game").delete();
		saves.put(slot, null);
	}

	public void saveGame(int slot) {
		Map<String, Object> saveMap = new HashMap<String, Object>();
		saveMap.put("money", money);
		saveMap.put("lives", lives);
		saveMap.put("towers", towers);
		saveMap.put("tower states", towerStates);
		saveMap.put("enemy list", list);
		saveMap.put("unloaded enemy list", unloadedList);
		saveMap.put("timer", timer);
		saveMap.put("reset timer", reset);
		saveMap.put("world name", world.getName());
		saveMap.put("world", world.serialize());
		saveMap.put("type", type.id);
		saveMap.put("running", running);
		saveMap.put("difficulty", difficulty.id);
		saveMap.put("date", getCurrentDate());

		try {
			saveFile.mkdirs();
			File save = new File(saveFile, "slot" + slot + ".game");
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(save));
			oos.writeObject(saveMap);
			oos.flush();
			oos.close();
			saves.put(slot, saveMap);
			System.out.println("Game slot " + slot + " saved");
		} catch (Exception ex) {
			System.out.println("Failed to save game slot " + slot);
		}

	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> loadGame(int slot) {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File("Saves/slot" + slot + ".game")));
			Map<String, Object> loadMap = (Map<String, Object>) ois
					.readObject();
			ois.close();
			System.out.println("Game slot " + slot + " loaded!");
			return loadMap;
		} catch (Exception ex) {
			System.out.println("Failed to load game slot " + slot);
			return null;
		}

	}

	public void init(World world, Type type, Difficulty difficulty) {
		this.world = world;
		this.type = type;
		this.difficulty = difficulty;
		start = true;
		towers = new Integer[world.length][world.width];
		towerStates = new TowerState[world.length][world.width];
		pauseScreen = new GuiPauseGameScreen(this);
		running = GAME_OVER = false;
		wave = 1;

		for (int r = 0; r < world.length; r++) {
			for (int c = 0; c < world.width; c++) {
				towers[r][c] = -1;
			}
		}

		money = (type != Type.NORMAL) ? 1000000 : difficulty.money;
		lives = (type != Type.NORMAL) ? 1000000 : difficulty.lives;

		list = new ArrayList<Enemy>();
		reset = (int) ((50 - wave) * .67);
		reset = (reset < 10) ? 10 : reset;
		timer = reset;
		toolBar.init();
		resume();
	}

	public void init(World world, Type type) {
		this.init(world, type, Difficulty.NORMAL);
	}

	@SuppressWarnings("unchecked")
	public void init(Map<String, Object> loadMap) {
		money = (Double) loadMap.get("money");
		lives = (Integer) loadMap.get("lives");
		towers = (Integer[][]) loadMap.get("towers");
		towerStates = (TowerState[][]) loadMap.get("tower states");
		list = (List<Enemy>) loadMap.get("enemy list");
		unloadedList = (List<Enemy>) loadMap.get("unloaded enemy list");
		timer = (Integer) loadMap.get("timer");
		reset = (Integer) loadMap.get("reset timer");
		String worldName = (String) loadMap.get("world name");
		world = World.deserialize((Map<String, Object>) loadMap.get("world"),
				worldName);
		difficulty = Difficulty.getDifficulty((Integer) loadMap
				.get("difficutly"));
		type = Type.getType((Integer) loadMap.get("type"));
		running = (Boolean) loadMap.get("running");
		pauseScreen = new GuiPauseGameScreen(this);
		toolBar.init();
		resume();
	}

	public enum Type {

		NORMAL(0x0101A, "Normal"), FREEPLAY(0x0101B, "FreePlay"), TEST(0x0101C,
				"Test");

		private Type(int id, String name) {
			this.id = id;
			this.name = name;
		}

		public String toString() {
			return name;
		}

		public static Type getType(int id) {
			return types.get(id);
		}

		public final String name;
		public final int id;

		private static final Map<Integer, Type> types = new HashMap<Integer, Type>();

		static {

			for (Type type : Type.values()) {
				types.put(type.id, type);
			}

		}

	}

	public enum Difficulty {

		EASY("Easy", 500.00, 100, 0x0102A), NORMAL("Normal", 400.00, 80,
				0x0102B), HARD("Hard", 300.00, 50, 0x0102C);

		private Difficulty(String name, double money, int lives, int id) {
			this.id = id;
			this.name = name;
			this.money = money;
			this.lives = lives;
		}

		public String toString() {
			return name;
		}

		public static Difficulty getDifficulty(int id) {
			return difficulties.get(id);
		}

		public final String name;
		public final double money;
		public final int lives;
		public final int id;

		private static final Map<Integer, Difficulty> difficulties = new HashMap<Integer, Difficulty>();

		static {

			for (Difficulty type : Difficulty.values()) {
				difficulties.put(type.id, type);
			}

		}

	}

}
