package bdt.gui.button;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Rectangle;

import bdt.screen.Screen;

public class GuiButton extends AbstractButton {
	protected static final Font textFont = Screen.getFont(25);
	protected static final FontMetrics textMetrics = Screen
			.getFontMetrics(textFont);
	protected Rectangle bounds;
	protected int width;
	protected int x, y;
	protected String message;

	public GuiButton(String mess, int id, int x, int y, int w) {
		super(id);
		message = mess;
		this.x = x;
		this.y = y;
		width = w;
		bounds = null;
	}

	public GuiButton(String mess, int id, int x, int y) {
		this(mess, id, x, y, Screen.getWidth());
	}

	public void setText(String mess) {
		message = mess;
		bounds = null;
	}

	public boolean isPressed(int x, int y) {

		if (bounds != null) {
			return bounds.contains(x, y);
		}

		return false;
	}

	public boolean hover(int x, int y) {

		if (bounds != null) {
			return bounds.contains(x, y);
		}

		return false;
	}

	public void render(Graphics g) {
		g.setColor((hover) ? Color.yellow : Color.red);
		g.setFont(textFont);
		int x2 = ((width - textMetrics.stringWidth(message)) / 2) + x;
		g.drawString(message, x2, y + 20);

		if (bounds == null) {
			bounds = textMetrics.getStringBounds(message, g).getBounds();
			bounds.x = x2;
			bounds.y = y - 2;
		}

	}

}
