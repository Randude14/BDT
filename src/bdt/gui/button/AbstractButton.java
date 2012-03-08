package bdt.gui.button;

import java.awt.Graphics;

public abstract class AbstractButton {
	public boolean enabled;
	public boolean hover;
	public final int id;

	public AbstractButton(int id) {
		this.id = id;
		enabled = true;
		hover = false;
	}

	public boolean hover(int x, int y) {
		return false;
	}

	public void render(Graphics g) {
	}

	public abstract boolean isPressed(int x, int y);

}
