package bdt.enemy;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import bdt.world.Point;

public class EnemyBlue extends Enemy {
	private static final long serialVersionUID = 8443824324506744658L;

	public EnemyBlue() {
		setStep(1.5);
	}

	public Color getColor() {
		return Color.blue;
	}

	public int getLivesCost() {
		return 3;
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		List<Point> path = getPathFromIndex();

		for (int cntr = 0; cntr < 2; cntr++) {
			Enemy enemy = new EnemyGreen().path(path);
			moveEnemy(enemy);
			enemies.add(enemy);
		}

		return enemies;
	}

}
