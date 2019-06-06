package bdt.tower;

import java.awt.Rectangle;
import java.awt.Graphics2D;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

import bdt.world.World;
import bdt.event.Game;
import bdt.enemy.Enemy;

public class BombState extends TowerState {
	private static final long serialVersionUID = 8366050236656893962L;
	private Rectangle bomb;
	private boolean explode;
	private int count;
	private int time;
	private int red;

	public BombState(World world, int x, int y) {
		super(world, x, y);
		x = world.getPixel(x);
		y = world.getPixel(y);
		bomb = new Rectangle(x - 20, y - 20, 100, 100);
		explode = false;
		count = 0;
		time = 3;
		red = 150;
	}

	public boolean remove() {
		return count >= 300;
	}

	public void update(Game game) {
		red = (red < 255 && count % 2 == 0) ? ++red : red;

		if (count++ >= 285) {

			if (count > 299) {
				List<Enemy> list = game.getEnemyList();

				for (int cntr = 0; cntr < list.size(); cntr++) {
					Enemy enemy = list.get(cntr);

					if (bomb.intersects(enemy.getBounds())) {
						game.killEnemy(enemy);
						cntr--;
					}

				}

			}

			explode = true;
		}

		if (count % 100 == 0) {
			time--;
		}

	}

	public void render(Graphics g) {
		String count = "" + time;
		int x = world.getPixel(super.x);
		int y = world.getPixel(super.y);
		g.setFont(font);
		g.setColor(new Color(red, 0, 0));
		g.fillRect(x + 5, y + 5, 20, 20);
		g.setColor(Color.black);
		int width = metrics.stringWidth(count);
		g.drawString(count, ((31 - width) / 2) + x, y+20);

		if (explode) {
			g.setColor(Color.red);
			((Graphics2D) g).fill(bomb);
		}

	}

}
