package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.World;

public class Detector extends Tower {

	public Detector() {
	}

	public int getID() {
		return 4;
	}

	public String getTowerName() {
		return "Detector";
	}

	public TowerState getTowerState(World world, int x, int y) {
		return new DetectorState(world, x, y);
	}

	public void render(Graphics g, int x, int y) {
		g.setColor(Color.gray);
		g.fillRect(x + 5, y + 5, 20, 20);
	}

}
