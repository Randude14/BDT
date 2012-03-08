package bdt.tower;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import bdt.world.World;
import bdt.event.Game;
import bdt.enemy.Enemy;
import bdt.entity.EntityDetectorRay;

@SuppressWarnings("serial")
public class DetectorState extends TowerState {
	private Rectangle area;
	private EntityDetectorRay[] rays;

	public DetectorState(World world, int x, int y) {
		super(world, x, y);
		x = world.getPixel(x);
		y = world.getPixel(y);
		rays = new EntityDetectorRay[2];
		area = new Rectangle(x + 45, y + 45, 120, 120);
		rays[0] = new EntityDetectorRay(x + 3, y + 3, 24);
		rays[1] = new EntityDetectorRay(x - 4, y - 4, 38);
	}

	public void update(Game game) {

		for (EntityDetectorRay ray : rays) {
			ray.update();
		}

		if (!areCamoEnemiesNearby(game)) {
			return;
		}

		for (TowerState state : getNearbyTowerStates(game)) {

			if (state instanceof CamoListener) {
				((CamoListener) state).camoFound();
			}

		}

	}

	public List<TowerState> getNearbyTowerStates(Game game) {
		ArrayList<TowerState> list = new ArrayList<TowerState>();

		for (int x1 = -2; x1 < 3; x1++) {

			for (int y1 = -2; y1 < 3; y1++) {

				if (world.isValid(x1 + x, y1 + y)) {
					TowerState state = game.getTowerState(x1 + x, y1 + y);

					if (state != null) {
						list.add(state);
					}

				}

			}

		}

		return list;
	}

	public boolean areCamoEnemiesNearby(Game game) {

		for (Enemy enemy : game.getEnemyList()) {

			if (area.intersects(enemy.getBounds())
					&& enemy.getOrgType() == Enemy.Type.CAMO) {
				return true;
			}

		}

		return false;
	}

	public void render(Graphics g) {

		for (EntityDetectorRay ray : rays) {
			ray.render(g);
		}

	}

	public interface CamoListener {

		public abstract void camoFound();

	}

}
