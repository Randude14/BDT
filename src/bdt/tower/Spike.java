package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.World;
import bdt.world.Block;

public class Spike extends Tower {

	public Spike() {

	}

	public boolean renderInGame() {
		return false;
	}

	public boolean isSellable() {
		return false;
	}

	public boolean isTowerPlaceableOnBlock(int ID) {
		return Block.isPath(ID);
	}

	public TowerState getTowerStorage(World world, int x, int y) {
		return new SpikeState(world, x, y);
	}

	public double getInitialValue() {
		return 50;
	}

	public int getID() {
		return 7;
	}

	public String getTowerName() {
		return "Spike";
	}

	public void render(Graphics g, int x, int y) {
		g.setColor(Color.black);

		for (int cntr = 5; cntr < 30; cntr += 5) {
			g.drawLine(x + cntr, y, x + cntr, y + 30);
		}

		for (int cntr = 5; cntr < 30; cntr += 5) {
			g.drawLine(x, y + cntr, x + 30, y + cntr);
		}

	}

}
