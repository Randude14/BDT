package bdt.tower;

import java.awt.Graphics;
import java.awt.Color;

import bdt.world.World;

public class MoneyPrinter extends Tower {
	private String money;
	private int width;

	public MoneyPrinter() {
		money = "$";
		width = metrics.stringWidth(money);
	}

	public void onRoundEnd(World world, int x, int y) {
	}
	
	public TowerState getTowerState(World world, int x, int y) {
		return new MoneyTowerState(world, x, y);
	}

	public int getID() {
		return 3;
	}

	public String getTowerName() {
		return "Money Printer";
	}

	public void render(Graphics g, int x, int y) {
		g.setFont(font);
		g.setColor(Color.green);
		g.fillRect(x + 5, y + 5, 20, 20);
		g.setColor(Color.yellow);
		g.drawString(money, ((32 - width) / 2) + x, y + 20);
	}

}
