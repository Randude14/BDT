package bdt.tower;

import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bdt.world.Material;
import bdt.world.World;
import bdt.world.Block;
import bdt.event.Renderable;
import bdt.event.Game;
import bdt.screen.Screen;

public abstract class Tower implements Renderable {
	public final int towerID;
	public final String towerName;

	public Tower() {
		int ID = getID();

		if (towerMap.containsKey(ID)) {
			throw new IllegalArgumentException("Tower ID " + ID + " is already taken!");
		}

		else if (ID < 1) {
			throw new IllegalArgumentException("Tower ID " + ID + " must be posivitve!");
		}

		else {
			towerName = getTowerName();
			towerID = ID;
			towerMap.put(towerID, this);
		}

	}

	public void onTowerPlaced(Game game, World world, int x, int y) {
		TowerState state = getTowerState(world, x, y);

		if (state != null) {
			game.addTowerState(state, x, y);
		}

	}

	public void onTowerRemoved(Game game, World world, int x, int y) {
		game.removeTowerState(x, y);
	}

	public boolean renderInGame() {
		return true;
	}

	public boolean isTowerPlaceableOnBlock(int ID) {
		return Block.getBlockMaterial(ID) == Material.NORMAL;
	}

	public boolean isSellable() {
		return true;
	}
	
	public boolean canHaveNearbyTowers() {
		return true;
	}

	public double getInitialValue() {
		return 300;
	}

	public void onRoundBegin(Game game, World world, int x, int y) {

	}

	public void onRoundEnd(Game game, World world, int x, int y) {

	}

	public void render(Graphics g, int x, int y) {

	}

	public void renderIcon(Graphics g, int x, int y) {
		render(g, x, y);
	}

	public TowerState getTowerState(World world, int x, int y) {
		return null;
	}

	public int getPixel(int num) {
		return num * 30;
	}

	public int getCoord(int num) {
		return num / 30;
	}

	public abstract int getID();

	public abstract String getTowerName();

	public static boolean isTower(int towerID) {
		return towerMap.containsKey(towerID);
	}

	public static Tower getTowerById(int towerID) {

		if (!towerMap.containsKey(towerID)) {
			throw new RuntimeException("Tower ID " + towerID + " not found!");
		}

		else {
			return towerMap.get(towerID);
		}

	}

	public static List<Integer> getTowersAsList() {
		return new ArrayList<Integer>(towerMap.keySet());
	}

	protected static final Font font = new Font("Ariels", Font.BOLD, 15);
	protected static final FontMetrics metrics = Screen.getFontMetrics(font);
	private static final Map<Integer, Tower> towerMap;
	public static final Tower bladeTower;
	public static final Tower freezeTower;
	public static final Tower moneyPrinter;
	public static final Tower detectorTower;
	public static final Tower bomb;
	public static final Tower spike;

	static {
		towerMap = new HashMap<Integer, Tower>();
		bladeTower = new BladeTower();
		freezeTower = new Freezer();
		moneyPrinter = new MoneyPrinter();
		detectorTower = new Detector();
		bomb = new Bomb();
		spike = new Spike();
	}

}
