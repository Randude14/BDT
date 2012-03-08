package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.game.BDTGame;
import bdt.screen.Screen;

public class GuiDeleteOrCancelScreen extends GuiScreen {
	private GuiScreen parentScreen;
	private BDTGame game;
	private String title1;
	private String title2;
	private Font font;
	private FontMetrics metrics;
	private int width;
	private int slot;
	private int x;

	public GuiDeleteOrCancelScreen(GuiScreen screen, BDTGame game, int slot) {
		this.parentScreen = screen;
		this.game = game;
		this.slot = slot;
		width = 450;
		x = 75;
		font = Screen.getFont(30);
		metrics = Screen.getFontMetrics(font);
		title1 = "Are you sure you want";
		title2 = "to delete this game?";
		addButton(new GuiButton("Delete", 1, 75, 250, 225));
		addButton(new GuiButton("Cancel", 2, 300, 250, 225));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			game.deleteGame(slot);
		}

		parentScreen.init();
		game.setPauseScreen(parentScreen);
	}

	public void render(Graphics g) {
		super.render(g);
		g.setFont(font);
		g.setColor(Color.red);
		int title1X = ((width - metrics.stringWidth(title1)) / 2) + x;
		int title2X = ((width - metrics.stringWidth(title2)) / 2) + x;
		int y = 150;
		g.drawString(title1, title1X, y);
		g.drawString(title2, title2X, y + 30);
	}

}
