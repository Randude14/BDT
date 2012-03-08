package bdt.gui.button;

import java.awt.Rectangle;
import java.awt.Graphics;

import bdt.tower.Tower;
import bdt.gui.screen.GuiToolBar;

public class TowerButton extends AbstractButton {
	private int x, y;
	public int index;
	private Rectangle bounds;
	private GuiToolBar parent;

	public TowerButton(GuiToolBar screen, int id, int x, int y) {
		super(id);
		parent = screen;
		bounds = new Rectangle(x, y, 30, 30);
		this.x = x;
		this.y = y;
		index = 0;
	}

	public boolean isPressed(int x, int y) {
		return bounds.contains(x, y);
	}

	public boolean hover(int x, int y) {
		return bounds.contains(x, y);
	}

	public void render(Graphics g) {

		if (index < 0 || index > parent.towers.size() - 1) {
			return;
		}

		Tower tower = Tower.getTowerById(parent.towers.get(index));
		tower.renderIcon(g, x, y);
	}

}
