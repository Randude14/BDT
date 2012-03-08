package bdt.entity;

import java.awt.Graphics;
import java.awt.Color;

public class EntityDisk extends Entity {
	private static final long serialVersionUID = 2108249059578935788L;
	private double velx, vely;

	public EntityDisk(int x, int y, double velx, double vely) {
		super(x, y);
		this.velx = velx;
		this.vely = vely;
	}

	public void update() {
		x += velx;
		y += vely;
		setBounds(18, 18);
	}

	public void render(Graphics g) {
		g.setColor(Color.black);
		g.fillRect((int) x, (int) y, 18, 18);
	}

}
