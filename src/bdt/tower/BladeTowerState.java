package bdt.tower;

import java.awt.Rectangle;

import bdt.event.Game;
import bdt.world.World;
import bdt.enemy.Enemy;
import bdt.entity.EntityBlade;
import bdt.entity.Entity;

@SuppressWarnings("serial")
public class BladeTowerState extends EntityState implements
		DetectorState.CamoListener {
	private Rectangle area;
	protected boolean update;
	private int count;

	public BladeTowerState(World world, int x, int y, int vel) {
		super(world, x, y);
		x = world.getPixel(x);
		y = world.getPixel(y);
		add(new EntityBlade(x + 6, y + 6, 0, -vel));
		add(new EntityBlade(x + 6, y + 6, -vel, 0));
		add(new EntityBlade(x + 6, y + 6, vel, 0));
		add(new EntityBlade(x + 6, y + 6, 0, vel));
		area = new Rectangle(x - 45, y - 45, 120, 120);
	}

	public void camoFound() {
		update = true;
	}

	public void update(Game game) {

		if (update) {
			super.update(game);

			if (count++ >= 40) {

				for (Entity entity : entities) {
					((EntityBlade) entity).reset();
				}

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

}
