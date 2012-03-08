package bdt.gui.screen;

import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.game.MainMenu;

public class GuiLoadOrCreateScreen extends GuiScreen {
	private MainMenu menu;

	public GuiLoadOrCreateScreen(MainMenu menu) {
		this.menu = menu;

		addButton(new GuiButton("Load World", 1, 75, 250, 450));
		addButton(new GuiButton("Create World", 2, 75, 290, 450));
		addButton(new GuiButton("Cancel", 3, 75, 330, 450));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			menu.setGuiScreen(new GuiEditorChooser(menu));
		}

		if (button.id == 2) {
			menu.setGuiScreen(new GuiWorldCreateScreen(menu));
		}

		if (button.id == 3) {
			menu.setGuiScreen(new GuiMainMenu(menu));
		}

	}

}
