package bdt.enemy;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import bdt.world.Point;

public class EnemyGreen extends Enemy {
	private static final long serialVersionUID = 263162193389090862L;

	public EnemyGreen() {
		setStep(1.5);
	}

	public int getLivesCost() {
		return 2;
	}

	public Color getColor() {
		return Color.green;
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		List<Point> path = getPathFromIndex();

		for (int cntr = 0; cntr < 3; cntr++) {
			Enemy enemy = new EnemyRed().path(path);
			moveEnemy(enemy);
			enemies.add(enemy);
		}

		return enemies;
	}

}
