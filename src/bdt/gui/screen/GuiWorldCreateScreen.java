package bdt.gui.screen;

import bdt.game.GameSession;
import bdt.game.LevelEditor;
import bdt.game.MainMenu;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.screen.Screen;
import bdt.world.WorldManager;

public class GuiWorldCreateScreen extends GuiTextEditor {

	private GuiButton createButton;
	private MainMenu menu;

	public GuiWorldCreateScreen(MainMenu menu) {
		super("Enter world name.", 310);
		this.menu = menu;

		addButton(createButton = new GuiButton("Create World", 1, 75, 350, 450));
		addButton(               new GuiButton("Cancel", 2, 75, 400, 450));
		createButton.enabled = false;
	}

	public boolean keyPressed(int key) {
		boolean flag = super.keyPressed(key);

		if (flag) {
			createButton.enabled = text.length() != 0
					&& !WorldManager.doesNameExist(text);
			return true;
		}

		return false;
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			LevelEditor editor = (LevelEditor) GameSession.levelEditor;
			editor.init(text);
			Screen.setGameSession(editor);
		}

		if (button.id == 2) {
			menu.setGuiScreen(new GuiLoadOrCreateScreen(menu));
		}

	}

}
