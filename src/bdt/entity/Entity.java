package bdt.entity;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.io.Serializable;

import bdt.enemy.Enemy;
import bdt.event.Boundable;

public abstract class Entity implements Boundable, Serializable {
	private static final long serialVersionUID = -551875428733738338L;
	protected Rectangle bounds;
	protected double x, y;

	public Entity(double x, double y) {
		this.x = x;
		this.y = y;
		bounds = new Rectangle((int) x, (int) y, 1, 1);
	}

	public void setBounds(double x, double y, double width, double height) {
		bounds.setBounds((int) x, (int) y, (int) height, (int) width);
	}

	public void setBounds(double width, double height) {
		bounds.setBounds((int) x, (int) y, (int) height, (int) width);
	}

	public Rectangle getBounds() {
		return bounds;
	}

	public boolean canHitEnemy(Enemy enemy) {
		return true;
	}

	public boolean removeAfterHit() {
		return false;
	}

	public boolean hasHitEnemy(Enemy enemy) {
		return bounds.intersects(enemy.getBounds());
	}

	public void hit() {

	}

	public void update() {

	}

	public void render(Graphics g) {

	}

}
