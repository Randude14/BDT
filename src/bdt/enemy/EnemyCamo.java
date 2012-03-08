package bdt.enemy;

import java.awt.image.BufferedImage;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import bdt.tools.FileLoader;
import bdt.world.Point;

public class EnemyCamo extends Enemy {
	private static final long serialVersionUID = -4800963137436615088L;
	private static BufferedImage camo = FileLoader.loadImage("Enemy/camo.png");

	public EnemyCamo() {
		setStep(2.0);
		setType(Type.CAMO);
	}

	public int getLivesCost() {
		return 10;
	}

	protected void renderBody(Graphics g) {
		g.drawImage(camo, (int) x + 5, (int) y + 5, null);
	}

	public List<Enemy> getEnemies() {
		List<Enemy> enemies = new ArrayList<Enemy>();
		List<Point> path = getPathFromIndex();

		for (int cntr = 0; cntr < 5; cntr++) {
			Enemy enemy = new EnemyWhite().path(path);
			moveEnemy(enemy);
			enemies.add(enemy);
		}

		return enemies;
	}

}
