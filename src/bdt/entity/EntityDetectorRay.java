package bdt.entity;

import java.awt.Graphics;
import java.awt.Color;

public class EntityDetectorRay extends Entity {
	private static final long serialVersionUID = -437523497462822747L;
	private int defaultWidth, width;
	private int steps, stop, time;
	private int defaultx, defaulty;
	private boolean visible;

	public EntityDetectorRay(int x, int y, int width) {
		super(x, y);
		defaultWidth = width;
		this.width = defaultWidth;
		defaultx = x;
		defaulty = y;
		stop = 15;
		steps = time = 0;
		visible = true;
	}

	public void update() {

		if (visible && steps < stop) {
			x -= 1.5;
			y -= 1.5;
			steps++;
			width += 3;
			setBounds(width, width);

			if (steps == stop) {
				visible = false;
				time = 15;
			}

			return;
		}

		if (time-- > 0) {
		}

		else {
			visible = true;
			steps = 0;
			x = defaultx;
			y = defaulty;
			width = defaultWidth;
		}

	}

	public void render(Graphics g) {

		if (!visible) {
			return;
		}

		g.setColor(Color.red);
		g.drawRect((int) x, (int) y, width, width);
	}

}
