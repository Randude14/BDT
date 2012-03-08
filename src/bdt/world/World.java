package bdt.world;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;
import java.io.Serializable;

import bdt.event.BlockNotFoundException;
import bdt.event.WorldOutOfBoundsException;
import bdt.event.Capturable;

public class World implements Serializable, Capturable {
	private static final long serialVersionUID = -8567734648154950210L;

	// ///DEFAULT NUMBER OF ROWS
	public static final int NUM_ROWS = 20;

	// ///DEFAULT NUMBER OF COLS
	public static final int NUM_COLS = 20;

	protected List<Point> path;
	protected Integer[][] blocks;
	protected String worldName;
	public final int length;
	public final int width;

	private World(Integer[][] blocks, List<Point> path, String name) {
		this.path = path;
		this.blocks = blocks;
		worldName = name;
		length = blocks.length;
		width = blocks[0].length;
		init();
	}

	public World(String name) {
		this(makeGrid(), new ArrayList<Point>(), name);
	}

	private static Integer[][] makeGrid() {
		Integer[][] grid = new Integer[NUM_ROWS][NUM_COLS];

		for (int r = 0; r < grid.length; r++) {

			for (int c = 0; c < grid[r].length; c++) {
				grid[r][c] = Block.GRASS.ID;
			}

		}

		return grid;
	}

	private void init() {

		for (int r = 0; r < blocks.length; r++) {

			for (int c = 0; c < blocks[r].length; c++) {
				Integer ID = blocks[r][c];

				if (!Block.isBlock(ID)) {
					blocks[r][c] = Block.GRASS.ID;
				}

				if (!isPath(r, c) && Block.isPath(ID)) {
					blocks[r][c] = Block.GRASS.ID;
				}

			}

		}

		for (Point point : path) {
			int ID = this.getBlockID(point.getX(), point.getY());

			if (!Block.isPath(ID)) {
				this.setBlockID(point, Block.STONE_PATH.ID);
			}

		}

	}

	public int getPixel(int num) {
		return num * 30;
	}

	public int getCoord(int num) {
		return num / 30;
	}

	public void removePath(int blockID) {

		if (!path.isEmpty()) {
			Point point = path.remove(path.size() - 1);
			setBlockID(point, blockID);
			System.out.println(point);
		}

	}

	public void addPath(Point point, int pathID) {
		path.add(point);
		setBlockID(point, pathID);
	}

	public List<Point> getPath() {
		return path;
	}

	public boolean isPathEmpty() {
		return path.isEmpty();
	}

	public Point getLastPointOfPath() {

		if (!path.isEmpty()) {
			return path.get(path.size() - 1);
		}

		return null;
	}

	public int getBlockID(Point point) {
		return this.getBlockID(point.getX(), point.getY());
	}

	public int getBlockID(int x, int y) {
		if (!isValid(x, y)) {
			throw new WorldOutOfBoundsException("World: " + worldName
					+ ", out of bounds(" + x + ", " + y + ")");
		}

		return blocks[x][y];
	}

	public void setBlockID(Point point, int ID) {
		setBlockID(point.getX(), point.getY(), ID);
	}

	public void setBlockID(int x, int y, int ID) {
		if (!isValid(x, y)) {
			throw new WorldOutOfBoundsException("World: " + worldName
					+ ", out of bounds(" + x + ", " + y + ")");
		}

		if (!Block.isBlock(ID)) {
			throw new BlockNotFoundException("Block ID " + ID
					+ " does not exist!");
		}

		if (isPath(x, y) && !Block.isPath(ID)) {
			return;
		}

		blocks[x][y] = ID;
	}

	public boolean isPath(Point point) {
		return this.isPath(point.getX(), point.getY());
	}

	public boolean isPath(int x, int y) {

		for (Point point : path) {

			if (point.equals(x, y)) {
				return true;
			}

		}

		return false;
	}

	public boolean isValid(int x, int y) {
		return x >= 0 && x < blocks.length && y >= 0 && y < blocks[0].length;
	}

	public void render(Graphics g) {

		for (int r = 0; r < blocks.length; r++) {

			for (int c = 0; c < blocks[r].length; c++) {
				Block block = Block.getBlockAtID(blocks[r][c]);
				block.render(g, (r * 30), (c * 30));
			}

		}

	}

	public void renderIcon(Graphics g, int x, int y) {
		renderIcon(g, x, y, 2);
	}

	public void renderIcon(Graphics g, int x, int y, int width) {

		for (int r = 0; r < blocks.length; r++) {

			for (int c = 0; c < blocks[r].length; c++) {
				int x1 = (r * width) + x;
				int y1 = (c * width) + y;
				Block block = Block.getBlockAtID(blocks[r][c]);
				block.renderIcon(g, x1, y1, width);
			}

		}

	}

	public BufferedImage capture() {
		BufferedImage screenshot = new BufferedImage(length * 30, width * 30,
				BufferedImage.TYPE_INT_RGB);
		Graphics g = screenshot.createGraphics();
		this.render(g);
		return screenshot;
	}

	public int hashCode() {
		return worldName.hashCode();
	}

	public boolean equals(Object obj) {

		if (!(obj instanceof World)) {
			return false;
		}

		World world = (World) obj;

		return world.worldName.equalsIgnoreCase(this.worldName);
	}

	public String getName() {
		return worldName;
	}

	public String toString() {
		return worldName;
	}

	public Map<String, Object> serialize() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("blocks", blocks);
		map.put("path", path);
		return map;
	}

	@SuppressWarnings("unchecked")
	public static World deserialize(Map<String, Object> map, String name) {
		Integer[][] blocks = (Integer[][]) map.get("blocks");
		ArrayList<Point> path = (ArrayList<Point>) map.get("path");

		return new World(blocks, path, name);
	}

}
