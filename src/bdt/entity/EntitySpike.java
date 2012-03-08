package bdt.entity;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Color;
import java.awt.geom.Line2D;

import bdt.enemy.Enemy;

public class EntitySpike extends Entity {
	private static final long serialVersionUID = 2407721368069894466L;
	private Line2D line;

	public EntitySpike(int x, int y, int x1, int y1) {
		super(x, y);
		line = new Line2D.Double(x, y, x1, y1);
	}

	public boolean removeAfterHit() {
		return true;
	}

	public boolean hasHitEnemy(Enemy enemy) {
		return line.intersects(enemy.getBounds());
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		((Graphics2D) g).draw(line);
	}

}
