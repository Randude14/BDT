package bdt.entity;

import java.awt.Graphics;
import java.awt.Color;

import bdt.enemy.Enemy;

public class EntityBlade extends Entity {
	private static final long serialVersionUID = 7189563942626549146L;
	private double resetx, resety;
	private int velx, vely;
	private int stop, steps;
	private boolean visible;

	public EntityBlade(double x, double y, int vx, int vy) {
		super(x, y);
		resetx = x;
		resety = y;
		velx = vx;
		vely = vy;
		stop = 10;
		visible = true;
		steps = 0;
	}

	public boolean canHitEnemy(Enemy enemy) {
		return enemy.getType() != Enemy.Type.METAL && visible;
	}

	public void dead() {
		steps = (int) stop;
		visible = false;
		setBounds(0, 0, 0, 0);
	}

	public void hit() {
		dead();
	}

	public void update() {

		if (steps < stop && visible) {
			x += velx;
			y += vely;
			steps++;
			setBounds(x, y, 18, 18);

			if (steps >= stop) {
				dead();
			}

			return;
		}

	}

	public void reset() {
		visible = true;
		x = resetx;
		y = resety;
		steps = 0;
		setBounds(0, 0, 0, 0);
	}

	public void render(Graphics g) {

		if (!visible) {
			return;
		}

		g.setColor(Color.gray);
		g.fillRect((int) x, (int) y, 18, 18);
	}

}
