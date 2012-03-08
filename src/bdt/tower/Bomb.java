package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.Material;
import bdt.world.World;
import bdt.world.Block;

public class Bomb extends Tower {
	String str;
	private int width;

	public Bomb() {
		str = String.format("%d", 3);
		width = metrics.stringWidth(str);
	}

	public boolean isTowerPlaceableOnBlock(int ID) {
		return Block.getBlockMaterial(ID) == Material.NORMAL || Block.isPath(ID);
	}

	public TowerState getTowerState(World world, int x, int y) {
		return new BombState(world, x, y);
	}

	public boolean isSellable() {
		return false;
	}

	public double getInitialValue() {
		return 50;
	}

	public String getTowerName() {
		return "Bomb";
	}

	public int getID() {
		return 5;
	}

	public boolean renderInGame() {
		return false;
	}

	public void render(Graphics g, int x, int y) {
		g.setFont(font);
		g.setColor(Color.black);
		g.fillRect(x + 5, y + 5, 20, 20);
		g.setColor(Color.yellow);
		g.drawString(str, ((32 - width) / 2) + x, y + 20);
	}

}
