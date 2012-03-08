package bdt.tower;

import java.awt.Rectangle;
import java.awt.Graphics;

import bdt.event.Game;
import bdt.world.World;
import bdt.entity.EntityFreezeRay;
import bdt.enemy.Enemy;

public class FreezerState extends TowerState implements
		DetectorState.CamoListener {
	private static final long serialVersionUID = -2302148159415714253L;
	private EntityFreezeRay freezer;
	private Rectangle area;
	protected boolean update;
	private int count;

	public FreezerState(World world, int x, int y) {
		super(world, x, y);
		x = world.getPixel(x);
		y = world.getPixel(y);
		freezer = new EntityFreezeRay(x + 8, y + 8);
		area = new Rectangle(x - 45, y - 45, 120, 120);
		update = false;
		count = 0;
	}

	public void camoFound() {
		update = true;
	}

	public void update(Game game) {

		if (update) {
			freezer.update();
			count++;

			for (Enemy enemy : game.getEnemyList()) {

				if (freezer.getBounds().intersects(enemy.getBounds())
						&& !enemy.isFrozen() && freezer.isVisible()
						&& enemy.canBeFrozen()) {
					enemy.freeze(20);
				}

			}

			if (count++ >= 120) {
				freezer.reset();
				count = 0;
				update = false;
			}

		}

		if (checkForNearbyEnemies(game)) {
			update = true;
		}

	}

	private boolean checkForNearbyEnemies(Game game) {

		for (Enemy enemy : game.getEnemyList()) {

			if (area.intersects(enemy.getBounds())
					&& enemy.getType() != Enemy.Type.CAMO) {
				return true;
			}

		}

		return false;
	}

	public void render(Graphics g) {
		freezer.render(g);
	}

}
