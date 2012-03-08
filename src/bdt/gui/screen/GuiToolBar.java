package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.List;

import bdt.game.BDTGame;
import bdt.tools.MathHelper;
import bdt.tower.Tower;
import bdt.screen.Screen;
import bdt.gui.button.TowerButton;
import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;

public class GuiToolBar extends GuiScreen {
	protected TowerButton[] buttons;
	public List<Integer> towers;
	private GuiButton waveButton;
	protected BDTGame game;
	private Font font;
	private FontMetrics metrics;
	public boolean show;
	public boolean enabled;
	protected int hoverIndex;

	public GuiToolBar(BDTGame game) {
		this.game = game;
		towers = Tower.getTowersAsList();
		buttons = new TowerButton[5];
		enabled = true;
		hoverIndex = -1;
		font = Screen.getFont(10);
		metrics = Screen.getFontMetrics(font);

		for (int cntr = 0; cntr < 5; cntr++) {
			int y = 200 + (cntr * 50);
			TowerButton button = new TowerButton(this, cntr + 1, 675, y);
			buttons[cntr] = button;
			addButton(button);
		}

		buttons[0].index = -2;
		buttons[1].index = -1;
		buttons[2].index = 0;
		buttons[3].index = 1;
		buttons[4].index = 2;
		addButton(waveButton = new GuiButton("Start Game", 10, 600, 500, 180));
		addButton(new GuiButton("Pause Game", 11, 600, 540, 180));
	}

	public void init() {
		buttons[0].index = -2;
		buttons[1].index = -1;
		buttons[2].index = 0;
		buttons[3].index = 1;
		buttons[4].index = 2;
		waveButton.setText("Start Game");
	}

	public int getSelectedTowerID() {
		return towers.get(buttons[2].index);
	}

	public void enableButtons() {
		super.enableButtons();
		enabled = true;
	}

	public void disableButtons() {
		super.disableButtons();
		enabled = false;
	}
	
	public void endRound() {
		waveButton.enabled = true;
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			scrollButtons(1);
			scrollButtons(1);
		}

		if (button.id == 2) {
			scrollButtons(1);
		}

		if (button.id == 4) {
			scrollButtons(-1);
		}

		if (button.id == 5) {
			scrollButtons(-1);
			scrollButtons(-1);
		}

		if (button.id == 10) {
			game.startGame();
			waveButton.setText("Next Round");
			waveButton.enabled = false;

		}

		if (button.id == 11) {
			game.pause();
		}

		if (button.id != 10 && button.id != 11) {
			hover(game.mouseX, game.mouseY);
		}

	}

	public void scrollButtons(int clicks) {

		if (clicks < 0) {

			if (buttons[2].index == towers.size() - 1) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				buttons[cntr].index++;
			}

			hover(game.mouseX, game.mouseY);
		}

		else {

			if (buttons[2].index == 0) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				buttons[cntr].index--;
			}

			hover(game.mouseX, game.mouseY);
		}

	}

	public boolean hover(int x, int y) {

		for (TowerButton button : buttons) {

			if (button.hover(x, y) && button.enabled) {
				show = true;
				hoverIndex = button.index;
				return true;
			}

		}

		hoverIndex = -1;
		show = false;
		return super.hover(x, y);
	}

	public boolean scroll(int clicks, int x, int y) {

		if (MathHelper.doesRectContainPoint(x, y, 675, 150, 30, 230)) {
			scrollButtons(clicks);
		}

		return false;
	}

	public void render(Graphics g) {
		super.render(g);

		if (!enabled) {
			return;
		}

		g.setColor(Color.yellow);
		g.drawRect(670, 295, 40, 40);

		if (!show) {
			return;
		}

		if (hoverIndex < 0 || hoverIndex > towers.size() - 1) {
			return;
		}

		if (!Tower.isTower(towers.get(hoverIndex))) {
			return;
		}

		Tower tower = Tower.getTowerById(towers.get(hoverIndex));
		g.setFont(font);
		int mouseX = game.mouseX;
		int mouseY = game.mouseY;
		renderTowerInfoBox(g, mouseX, mouseY - 40, tower);
	}

	public void renderTowerInfoBox(Graphics g, int x, int y, Tower tower) {
		String name = "Name: " + tower.towerName;
		String value = String.format("Price: $%,.2f", tower.getInitialValue());
		String sellable = "Sellable: " + tower.isSellable();
		int nameWidth = metrics.stringWidth(name);
		int valueWidth = metrics.stringWidth(value);
		int sellableWidth = metrics.stringWidth(sellable);
		int width = Math.max(nameWidth, Math.max(valueWidth, sellableWidth)) + 10;
		int height = 40;

		if (width + x >= Screen.getWidth()) {
			x -= width;
		}

		if (y - height < 0) {
			y += height;
		}

		g.setColor(Color.black);
		g.fillRect(x, y, width, height);
		g.setColor(Color.yellow);
		g.drawString(name, ((width - nameWidth) / 2) + x, y + 11);
		g.drawString(value, ((width - valueWidth) / 2) + x,
				y + metrics.getHeight() + 9);
		g.drawString(sellable, ((width - sellableWidth) / 2) + x,
				y + metrics.getHeight() + 20);
	}

}
