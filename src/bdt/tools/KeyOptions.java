package bdt.tools;

import java.io.PrintWriter;
import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.Map;

import bdt.game.GameSession;

public final class KeyOptions {
	private static final Map<String, Integer> keys = new HashMap<String, Integer>();

	public static void setKey(String name, Integer key) {
		keys.put(name, key);
	}

	public static Integer getKey(String name) {

		for (String str : keys.keySet()) {

			if (str.equalsIgnoreCase(name)) {
				return keys.get(name);
			}

		}

		return null;
	}

	public static boolean hasKey(String name) {
		return getKey(name) != null;
	}
	
	public static boolean matchKey(String name, int key) {
		Integer keyCode = getKey(name);
		
		if(keyCode == null) {
			return false;
		}
		
		return keyCode == key;
	}

	public static boolean saveKeys() {

		try {
			PrintWriter writer = new PrintWriter("options.txt");

			for (String name : keys.keySet()) {
				writer.println(name + ": " + keys.get(name));
			}

			writer.close();
			System.out.println("Keys saved.");
			return true;
		} catch (Exception ex) {
			System.out.println("Failed to save keys.");
			ex.printStackTrace();
			return false;
		}

	}

	public static boolean loadKeys() {

		try {
			Scanner scan = new Scanner(new File("options.txt"));

			while (scan.hasNextLine()) {
				String line = scan.nextLine();
				int index = line.lastIndexOf(":");
				String name = line.substring(0, index);
				Integer key = Integer.parseInt(line.substring(index + 2));

				keys.put(name, key);
			}

			System.out.println("Keys loaded.");
			return true;
		} catch (Exception ex) {
			System.out.println("Failed to load keys.");

			for (Integer sessionID : GameSession.getSessionsAsList()) {
				GameSession session = GameSession.getGameSession(sessionID);
				Map<String, Integer> keys = session.getKeys();

				if (keys != null) {

					for (String name : keys.keySet()) {
						KeyOptions.keys.put(name, keys.get(name));
					}

				}

			}

			return false;
		}

	}

}
