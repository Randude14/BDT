package bdt.gui.screen;

import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.game.MainMenu;
import bdt.screen.Screen;

public class GuiMainMenu extends GuiScreen {
	private MainMenu menu;

	public GuiMainMenu(MainMenu menu) {
		this.menu = menu;

		addButton(new GuiButton("SingplePlayer", 1, 75, 250, 450));
		addButton(new GuiButton("Level Editor", 2, 75, 290, 450));
		addButton(new GuiButton("Manage Worlds", 3, 75, 330, 450));
		addButton(new GuiButton("Exit", 4, 75, 370, 450));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			menu.setGuiScreen(new GuiLoadOrNewGameScreen(menu));
		}

		if (button.id == 2) {
			menu.setGuiScreen(new GuiLoadOrCreateScreen(menu));
		}

		if (button.id == 3) {
			menu.setGuiScreen(new GuiWorldManagerChooser(menu));
		}

		if (button.id == 4) {
			Screen.shutdown();
		}

	}

}
