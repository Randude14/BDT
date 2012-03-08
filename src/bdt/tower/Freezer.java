package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.World;

public class Freezer extends Tower {
	private final Color freezer = new Color(0, 200, 255);

	public Freezer() {
	}

	public TowerState getTowerStorage(World world, int x, int y) {
		return new FreezerState(world, x, y);
	}

	public double getInitialValue() {
		return 100;
	}

	public int getID() {
		return 2;
	}

	public String getTowerName() {
		return "Freezer";
	}

	public void render(Graphics g, int x, int y) {
		g.setColor(freezer);
		g.fillRect(x + 5, y + 5, 20, 20);
	}

}
