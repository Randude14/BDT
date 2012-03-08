package bdt.enemy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//import bdt.tools.FileLoader;
import bdt.world.Point;

public final class LevelGenerator {
	private static final Random rand = new Random("BDT".hashCode());

	public static List<Enemy> loadLevel(List<Point> path, int level) {
		String filePath = "Levels/" + level + ".level";

		try {
			List<Enemy> list = new ArrayList<Enemy>();
			Scanner scan = new Scanner(new File(filePath));

			while (scan.hasNext()) {
				String str = scan.next();
				int count = scan.nextInt();

				for (int cntr = 0; cntr < count; cntr++) {
					Enemy enemy = Enemy.valueOf(str);
					enemy.path(path);
					list.add(enemy);
				}

			}
			return list;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Could not load " + filePath);
			return LevelGenerator.generateRandomWave(path, level);
		}

	}

	private static List<Enemy> generateRandomWave(List<Point> path, int level) {
		List<Enemy> list = new ArrayList<Enemy>();
		int stop = rand.nextInt(rand.nextInt(level*5));
		stop += (stop < 20) ? rand.nextInt(level*5) : 20;
		
		for(int cntr = 0;cntr < stop;cntr++) {
			int enemyID = rand.nextInt();
			Enemy enemy = Enemy.valueOf(enemyID);
			list.add(enemy);
		}
		
		return list;
	}

}
