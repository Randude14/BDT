package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.io.File;

import bdt.world.World;
import bdt.game.GameSession;
import bdt.game.LevelEditor;
import bdt.game.MainMenu;
import bdt.world.WorldManager;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.screen.Screen;

public class GuiEditorOptions extends GuiScreen {
	private String[] keyNames = { "Path Up", "Path Right", "Path Down",
			"Path Left", "Path Delete" };
	private LevelEditor editor;
	private FontMetrics metrics;
	private GuiButton exitButton;
	private Font font;
	private String title;
	private boolean flag;

	public GuiEditorOptions(LevelEditor editor) {
		this.editor = editor;
		addButton(new GuiButton("Resume", 1, 75, 200, 450));
		addButton(new GuiButton("Key Controls", 2, 75, 250, 450));
		addButton(exitButton = new GuiButton("Save and Quit", 3, 75, 300, 450));
		font = Screen.getFont(40);
		metrics = Screen.getFontMetrics(font);
		title = "Options.";
	}

	public void init(boolean flag) {
		this.flag = flag;
		exitButton.setText("Save and Exit");
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			editor.resume();
		}

		if (button.id == 2) {
			editor.setPauseScreen(new GuiEditorKeyControls(editor, this, keyNames));
		}

		if (button.id == 3) {
			World world = editor.getWorld();
			File file = new File(editor.getWorldPath());
			WorldManager.saveWorld(world, file);
			WorldManager.addWorld(world, file);

			if (flag) {
				Screen.shutdown();
			}

			else {
				MainMenu menu = (MainMenu) GameSession.mainMenu;
				Screen.setGameSession(menu);
			}

		}

	}

	public void render(Graphics g) {
		super.render(g);
		int width = metrics.stringWidth(title);
		g.setColor(Color.red);
		g.setFont(font);
		g.drawString(title, ((450 - width) / 2) + 75, 70);
	}

}
