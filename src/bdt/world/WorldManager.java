package bdt.world;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.File;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;
import java.util.ArrayList;
import java.util.List;

public final class WorldManager {
	public static final String file = "Worlds";
	private static Map<World, File> worlds;

	@SuppressWarnings("unchecked")
	public static World loadWorld(File file) {

		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					file));
			Map<String, Object> saveMap = ((Map<String, Object>) ois
					.readObject());
			String worldName = WorldManager.getWorldName(file);
			World world = World.deserialize(saveMap, worldName);
			ois.close();
			System.out.println("World " + worldName + " loaded.");
			return world;

		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed to load World " + file);
			return null;
		}

	}

	public static void saveWorld(World world, File file) {

		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(file));
			oos.writeObject(world.serialize());
			oos.flush();
			oos.close();
			System.out.println("World " + world.worldName + " saved.");
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed to save world " + world.worldName);
		}

	}

	public static void renameWorld(World world, String name) {

		if (WorldManager.doesNameExist(name)) {
			return;
		}
		
		File file = worlds.remove(world);

		if (file == null) {
			return;
		}

		file.delete();
		world.worldName = name;
		File newFile = new File(WorldManager.file + "/" + world.worldName
				+ ".world");
		worlds.put(world, newFile);
		WorldManager.saveWorld(world, newFile);
	}

	public static List<World> getWorlds() {
		WorldManager.updateWorlds();
		return new ArrayList<World>(worlds.keySet());
	}

	public static boolean doesNameExist(String name) {

		for (World world : worlds.keySet()) {

			if (world.worldName.equalsIgnoreCase(name)) {
				return true;
			}

		}

		return false;
	}

	private static void updateWorlds() {

		File saves = new File(file);
		saves.mkdirs();
		File[] worldFiles = saves.listFiles();

		for (File file : worldFiles) {

			if (WorldManager.isWorld(file) && file.isFile()
					&& !doesNameExist(WorldManager.getWorldName(file))) {
				World world = WorldManager.loadWorld(file);

				if (world != null) {
					addWorld(world, file);
				}

			}

		}

		Iterator<World> iterator = worlds.keySet().iterator();

		while (iterator.hasNext()) {
			World world = iterator.next();
			File file = worlds.get(world);

			if (!file.exists()) {
				iterator.remove();
			}

		}

	}

	private static boolean isWorld(File file) {
		return file.getName().endsWith(".world");
	}

	private static String getWorldName(File file) {
		String fileName = file.getName();
		int index = fileName.lastIndexOf(".");
		return fileName.substring(0, index);
	}

	public static void addWorld(World world, File file) {
		WorldManager.worlds.put(world, file);
	}

	public static void deleteWorld(World world) {
		File file = worlds.remove(world);

		if (file != null) {
			file.delete();
			System.out.println("World " + world.worldName + " deleted!");
		}

	}

	public static void loadWorlds() {
		worlds = new TreeMap<World, File>(new WorldComparator());
		File saves = new File(file);
		saves.mkdirs();
		File[] worldFiles = saves.listFiles();

		for (File file : worldFiles) {

			if (WorldManager.isWorld(file) && file.isFile()) {
				World world = WorldManager.loadWorld(file);

				if (world != null) {
					WorldManager.addWorld(world, file);
				}

			}

		}

	}

	private static class WorldComparator implements Comparator<World> {

		public int compare(World w1, World w2) {

			if (w1 == null || w2 == null) {
				return 0;
			}

			String worldName1 = w1.worldName.toLowerCase();
			String worldName2 = w2.worldName.toLowerCase();
			return worldName1.compareTo(worldName2);
		}

	}

}
