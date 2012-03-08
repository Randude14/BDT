package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.screen.Screen;
import bdt.game.GameSession;
import bdt.game.LevelEditor;
import bdt.game.BDTGame;
import bdt.game.MainMenu;

public class GuiPauseGameScreen extends GuiScreen {
	private GuiSaveGameScreen saveScreen;
	private FontMetrics metrics;
	private Font font;
	private BDTGame game;
	private String title;
	private int width;
	private int x;

	public GuiPauseGameScreen(BDTGame game) {
		this.game = game;
		title = "Game paused.";
		width = 450;
		x = 75;
		int y = 150;
		saveScreen = new GuiSaveGameScreen(this, game);
		font = Screen.getFont(40);
		metrics = Screen.getFontMetrics(font);
		addButton(new GuiButton("Resume game", 1, 75, y += 50, 450));

		if (game.getType() != BDTGame.Type.TEST) {
			addButton(new GuiButton("Save and quit", 2, 75, y += 50, 450));
		}

		addButton(new GuiButton("Start new game", 3, 75, y += 50, 450));

		if (game.getType() != BDTGame.Type.TEST) {
			addButton(new GuiButton("Choose new world", 4, 75, y += 50, 450));
			addButton(new GuiButton("Back to main menu", 5, 75, y += 50, 450));
		}

		else {
			addButton(new GuiButton("Back to level editor", 6, 75, y += 50, 450));
		}

	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			game.resume();
		}

		if (button.id == 2) {
			game.setPauseScreen(saveScreen);
		}

		if (button.id == 3) {
			game.newGame();
			game.resume();
		}

		if (button.id == 4) {
			MainMenu menu = (MainMenu)GameSession.mainMenu;
			menu.setGuiScreen(new GuiGameChooser(menu));
			Screen.setGameSession(menu);
		}

		if (button.id == 5) {
			game.setPauseScreen(new GuiLeaveGameOrCancelScreen(game, this));
		}

		if (button.id == 6) {
			LevelEditor editor = (LevelEditor)GameSession.levelEditor;
			Screen.setGameSession(editor);
			game.resume();
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
