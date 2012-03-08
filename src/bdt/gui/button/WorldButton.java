package bdt.gui.button;

import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

import bdt.world.World;
import bdt.tools.MathHelper;

public class WorldButton extends AbstractButton {
	private List<World> worlds;
	public int index;
	private int x;
	private int y;

	public WorldButton(List<World> worlds, int id, int x, int y) {
		super(id);
		this.worlds = worlds;
		this.x = x;
		this.y = y;
		index = 0;
	}

	public boolean isPressed(int x, int y) {
		return MathHelper.doesRectContainPoint(x, y, this.x, this.y, 100, 100)
				&& indexInBounds();
	}

	public boolean hover(int x, int y) {
		return isPressed(x, y);
	}

	public boolean indexInBounds() {
		return index >= 0 && index < worlds.size();
	}

	public void render(Graphics g) {

		if (hover) {
			g.setColor(Color.yellow);
			g.drawRect(x, y, 100, 100);
		}

		if (!indexInBounds()) {
			return;
		}

		World world = worlds.get(index);
		world.renderIcon(g, x + 10, y + 10, 4);
	}

}
