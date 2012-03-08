package bdt.tower;

import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Graphics;
import java.io.Serializable;

import bdt.event.Game;
import bdt.world.World;
import bdt.screen.Screen;

public abstract class TowerState implements Serializable {
	private static final long serialVersionUID = -5880524892920971464L;
	protected static final Font font = new Font("Ariels", Font.BOLD, 15);
	protected static final FontMetrics metrics = Screen.getFontMetrics(font);

	protected World world;
	protected int x;
	protected int y;

	public TowerState(World w, int x, int y) {
		world = w;
		this.x = x;
		this.y = y;
	}

	public boolean remove() {
		return false;
	}

	public void render(Graphics g) {

	}

	public abstract void update(Game game);

}
