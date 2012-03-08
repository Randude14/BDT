package bdt.tower;

import bdt.world.World;
import bdt.entity.EntitySpike;

public class SpikeState extends EntityState {
	private static final long serialVersionUID = 785435400144335092L;

	public SpikeState(World world, int x, int y) {
		super(world, x, y);
		x = world.getPixel(x);
		y = world.getPixel(y);

		for (int cntr = 5; cntr < 30; cntr += 5) {
			add(new EntitySpike(x + cntr, y, x + cntr, y + 30));
		}

		for (int cntr = 5; cntr < 30; cntr += 5) {
			add(new EntitySpike(x, y + cntr, x + 30, y + cntr));
		}

	}

}
