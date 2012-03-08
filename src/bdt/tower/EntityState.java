package bdt.tower;

import java.awt.Graphics;
import java.util.List;
import java.util.ArrayList;

import bdt.event.Game;
import bdt.world.World;
import bdt.entity.Entity;
import bdt.enemy.Enemy;

public class EntityState extends TowerState {
	private static final long serialVersionUID = 6507315883402953865L;
	protected List<Entity> entities;

	public EntityState(World world, int x, int y) {
		super(world, x, y);
		entities = new ArrayList<Entity>();
	}

	public EntityState add(Entity entity) {
		entities.add(entity);
		return this;
	}

	public boolean remove() {
		return entities.isEmpty();
	}

	public void update(Game game) {
		List<Enemy> list = game.getEnemyList();

		for (int cntr = 0; cntr < entities.size(); cntr++) {
			Entity entity = entities.get(cntr);
			entity.update();

			for (int i = 0; i < list.size(); i++) {
				Enemy enemy = list.get(i);

				if (entity.canHitEnemy(enemy)) {

					if (entity.hasHitEnemy(enemy)) {
						game.killEnemy(enemy);
						entity.hit();

						if (entity.removeAfterHit()) {
							entities.remove(entity);
						}

					}

				}

			}

		}

	}

	public void render(Graphics g) {
		for (Entity entity : entities) {
			entity.render(g);
		}
	}

}
