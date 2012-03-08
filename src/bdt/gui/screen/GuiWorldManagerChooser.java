package bdt.gui.screen;

import bdt.game.MainMenu;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;

public class GuiWorldManagerChooser extends GuiWorldChooser {
	private MainMenu menu;
	private GuiButton renameButton;
	private GuiButton deleteButton;

	public GuiWorldManagerChooser(MainMenu menu) {
		this.menu = menu;
		addButton(renameButton = new GuiButton("Rename World", 15, 75, 460, 450));
		addButton(deleteButton = new GuiButton("Delete World", 16, 75, 500, 450));
		addButton(new GuiButton("Cancel", 17, 75, 540, 450));
		renameButton.enabled = false;
		deleteButton.enabled = false;
	}

	public void actionPerformed(AbstractButton button) {
		super.actionPerformed(button);

		if (button.id == 15) {
			menu.setGuiScreen(new GuiWorldRenameScreen(menu, selectedWorld, this));
		}

		else if (button.id == 16) {
			menu.setGuiScreen(new GuiDeleteWorldOrCancelScreen(menu, selectedWorld));
		}

		else if (button.id == 17) {
			menu.setGuiScreen(new GuiMainMenu(menu));
		}

		renameButton.enabled = selectedWorld != null;
		deleteButton.enabled = selectedWorld != null;
	}

}
