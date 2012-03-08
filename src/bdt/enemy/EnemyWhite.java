package bdt.enemy;

import java.awt.Color;
import java.util.List;
import java.util.ArrayList;

import bdt.world.Point;

public class EnemyWhite extends Enemy {
	private static final long serialVersionUID = 5586791632276977247L;
	private static final Color white = new Color(242, 242, 242);

	public EnemyWhite() {
		setStep(2.0);
	}

	public Color getColor() {
		return white;
	}

	public int getLivesCost() {
		return 4;
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		List<Point> path = getPathFromIndex();

		for (int cntr = 0; cntr < 2; cntr++) {
			Enemy enemy = new EnemyWhite().path(path);
			moveEnemy(enemy);
			enemies.add(enemy);
		}

		return enemies;
	}

}
