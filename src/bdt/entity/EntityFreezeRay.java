package bdt.entity;

import java.awt.Graphics;
import java.awt.Color;

public class EntityFreezeRay extends Entity {
	private static final long serialVersionUID = 4114884817105023499L;
	private double blastX, blastY;
	private boolean visible;
	private double width;
	private int steps, stop;

	public EntityFreezeRay(double x, double y) {
		super(x, y);
		blastX = x;
		blastY = y;
		width = 14;
		steps = 0;
		stop = 70;
		visible = true;
	}

	public boolean isVisible() {
		return visible;
	}

	public void update() {

		if (steps < stop) {
			blastX -= 0.25;
			blastY -= 0.25;
			width += 0.5;
			steps++;
			setBounds(blastX, blastY, width, width);

			if (steps == stop) {
				visible = false;
			}

			return;
		}

	}

	public void reset() {
		blastX = x;
		blastY = y;
		width = 14;
		steps = 0;
		setBounds(0, 0, 0, 0);
		visible = true;
	}

	public void render(Graphics g) {

		if (!visible) {
			return;
		}

		g.setColor(Color.cyan);
		g.fillRect((int) blastX, (int) blastY, (int) width, (int) width);
	}

}
