package bdt.gui.screen;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import bdt.gui.button.AbstractButton;

public abstract class GuiScreen {
	private List<AbstractButton> list;

	public GuiScreen() {
		list = new ArrayList<AbstractButton>();
	}

	public AbstractButton getButtonAt(int index) {
		return list.get(index);
	}

	public void init() {	
	}

	public void update() {
	}

	public void enableButtons() {

		for (AbstractButton button : list) {
			button.enabled = true;
			button.hover = false;
		}

	}

	public void disableButtons() {

		for (AbstractButton button : list) {
			button.enabled = false;
			button.hover = false;
		}

	}

	public boolean mouseClicked(int x, int y) {

		for (AbstractButton button : list) {

			if (button.isPressed(x, y) && button.enabled) {
				actionPerformed(button);
				button.hover = false;
				return true;
			}

		}

		return false;
	}

	public boolean hover(int x, int y) {
		boolean flag = false;

		for (AbstractButton button : list) {

			if (button.hover(x, y) && button.enabled) {
				button.hover = true;
				flag = true;
			}

			else {
				button.hover = false;
			}

		}

		return flag;
	}

	public boolean keyPressed(int key) {
		return false;
	}

	public void addButton(AbstractButton button) {
		list.add(button);
	}

	public abstract void actionPerformed(AbstractButton button);

	public void render(Graphics g) {

		for (AbstractButton button : list) {

			if (button.enabled) {
				button.render(g);
			}

		}

	}

}
