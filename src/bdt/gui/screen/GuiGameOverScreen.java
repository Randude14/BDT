package bdt.gui.screen;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

import bdt.game.BDTGame;
import bdt.game.GameSession;
import bdt.game.MainMenu;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.screen.Screen;

public class GuiGameOverScreen extends GuiScreen {
	private FontMetrics metrics;
	private Font font;
	private BDTGame game;
	private String title;
	private int width;
	private int x;

	public GuiGameOverScreen(BDTGame game) {
		this.game = game;
		title = "Game Over";
		font = Screen.getFont(40);
		metrics = Screen.getFontMetrics(font);
		addButton(new GuiButton("New Game", 1, 75, 125, 450));
		addButton(new GuiButton("Back to Main Menu", 2, 75, 175, 450));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			game.newGame();
		}

		if (button.id == 2) {
			MainMenu menu = (MainMenu) GameSession.mainMenu;
			Screen.setGameSession(menu);
		}

	}

	public void render(Graphics g) {
		super.render(g);
		g.setFont(font);
		int stringWidth = metrics.stringWidth(title);
		g.setColor(Color.red);
		g.drawString(title, ((width - stringWidth) / 2) + x, 70);
	}

}
