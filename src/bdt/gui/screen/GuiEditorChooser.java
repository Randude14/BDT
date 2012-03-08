package bdt.gui.screen;

import bdt.screen.Screen;
import bdt.game.GameSession;
import bdt.game.MainMenu;
import bdt.game.LevelEditor;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;

public class GuiEditorChooser extends GuiWorldChooser {
	private MainMenu menu;
	private GuiButton startButton;

	public GuiEditorChooser(MainMenu menu) {
		this.menu = menu;
		addButton(startButton = new GuiButton("Edit World", 15, 75, 460, 450));
		addButton(new GuiButton("Cancel", 16, 75, 500, 450));
		startButton.enabled = false;
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 15) {
			LevelEditor editor = (LevelEditor)GameSession.levelEditor;
			editor.init(selectedWorld);
			Screen.setGameSession(editor);
		}

		else if (button.id == 16) {
			menu.setGuiScreen(new GuiLoadOrCreateScreen(menu));
		}

		else {
			super.actionPerformed(button);
			startButton.enabled = selectedWorld != null;
		}

	}

}
