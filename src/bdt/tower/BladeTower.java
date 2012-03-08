package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.World;

public class BladeTower extends Tower {
	private int vel;

	public BladeTower() {
		vel = 4;
	}

	public int getID() {
		return 1;
	}

	public String getTowerName() {
		return "Blade Tower";
	}

	public TowerState getTowerState(World world, int x, int y) {
		return new BladeTowerState(world, x, y, vel);
	}

	public void render(Graphics g, int x, int y) {
		g.setColor(Color.black);
		g.fillRect(x + 5, y + 5, 20, 20);
	}

}
