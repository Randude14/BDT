package bdt.world;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.awt.Graphics;

import bdt.event.Renderable;

public abstract class Block implements Renderable {

	public Block(Material material, String name, int id) {

		if (id < 1) {
			throw new IllegalArgumentException("Block ID " + id
					+ " must be positive!");
		}

		else if (blocks.containsKey(id)) {
			throw new IllegalArgumentException("Block ID " + id
					+ " is already taken!");
		}

		else {
			this.ID = id;
			this.blockName = name;
			this.material = material;
			blocks.put(ID, this);
		}

	}

	public Block(String name, int id) {
		this(Material.NORMAL, name, id);
	}

	public boolean isPath() {
		return false;
	}

	public abstract void render(Graphics g, int x, int y);

	public abstract void renderIcon(Graphics g, int x, int y, int width);

	public static Block getBlockAtID(int ID) {
		return blocks.get(ID);
	}

	public static boolean isBlock(int ID) {
		return blocks.containsKey(ID);
	}

	public static boolean isPath(int ID) {
		return Block.isBlock(ID) && Block.getBlockAtID(ID).isPath();
	}

	public static Material getBlockMaterial(int ID) {

		if (!Block.isBlock(ID)) {
			return null;
		}

		Block block = Block.getBlockAtID(ID);
		return block.material;
	}

	public static List<Integer> getBlocksAsList() {
		List<Integer> blocks = new ArrayList<Integer>(Block.blocks.keySet());

		Iterator<Integer> iterator = blocks.iterator();

		while (iterator.hasNext()) {
			Integer ID = iterator.next();
			Block block = Block.getBlockAtID(ID);

			if (block.isPath()) {
				iterator.remove();
			}

		}

		return blocks;
	}

	public static List<Integer> getPathsAsList() {
		List<Integer> paths = new ArrayList<Integer>(Block.blocks.keySet());

		Iterator<Integer> iterator = paths.iterator();

		while (iterator.hasNext()) {
			Integer ID = iterator.next();
			Block block = Block.getBlockAtID(ID);

			if (!block.isPath()) {
				iterator.remove();
			}

		}

		return paths;
	}

	private static final Map<Integer, Block> blocks;
	public static final Block GRASS;
	public static final Block WATER;
	public static final Block TREE;
	public static final Block SAND;
	public static final Block CACTUS;
	public static final Block SNOW;
	public static final Block ICE;
	public static final Block HOUSE;
	public static final Block PYRAMID;
	public static final Block SNOW_BUSH;
	public static final Block DIRT;
	public static final Block[] colors;
	public static final Block STONE_PATH;
	public static final Block SANDSTONE_PATH;
	public static final Block BRICK_PATH;
	public static final Block SNOW_PATH;
	public final int ID;
	public final String blockName;
	public final Material material;

	static {
		blocks = new TreeMap<Integer, Block>();
		GRASS = new BlockDefault("Box/grass.png", "Grass", 1);
		WATER = new BlockDefault(Material.WATER, "Box/water.png", "Water", 2);
		TREE = new BlockDefault(Material.PLANT, "Box/tree.png", "Tree", 3);
		SAND = new BlockDefault("Box/sand.png", "Sand", 7);
		CACTUS = new BlockDefault(Material.PLANT, "Box/cactus.png", "Cactus", 8);
		SNOW = new BlockDefault("Box/snow.png", "Snow", 9);
		ICE = new BlockDefault(Material.CUSTOM, "Box/ice.png", "Ice", 10);
		HOUSE = new BlockDefault(Material.CUSTOM, "Box/house.png", "House", 13);
		PYRAMID = new BlockDefault(Material.CUSTOM, "Box/pyramid.png",
				"Pyramid", 14);
		SNOW_BUSH = new BlockDefault("Box/snowbush.png", "Snow Bush", 12);
		DIRT = new BlockDefault("Box/dirt.png", "Dirt", 15);
		STONE_PATH = new BlockPath("Box/stone_path.png", "Stone", 4);
		SANDSTONE_PATH = new BlockPath("Box/sand_path.png", "SandStone", 5);
		BRICK_PATH = new BlockPath("Box/brick_path.png", "Brick", 6);
		SNOW_PATH = new BlockPath("Box/snow_path.png", "Snow", 11);

		String[] names = BlockColor.names;
		colors = new Block[names.length];

		for (int cntr = 0; cntr < colors.length; cntr++) {
			colors[cntr] = new BlockColor(names[cntr], cntr + 20);
		}

	}

}
