package bdt.gui.screen;

import bdt.game.MainMenu;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.world.World;
import bdt.world.WorldManager;

public class GuiWorldRenameScreen extends GuiTextEditor {

	private GuiButton createButton;
	private GuiScreen parentScreen;
	private MainMenu menu;
	private World world;

	public GuiWorldRenameScreen(MainMenu menu, World world, GuiScreen screen) {
		super("Rename world.", 310);
		this.menu = menu;
		this.world = world;
		parentScreen = screen;

		addButton(createButton = new GuiButton("Rename World", 1, 75, 350, 450));
		addButton(new GuiButton("Cancel", 2, 75, 400, 450));
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
			WorldManager.renameWorld(world, text);
			menu.setGuiScreen(new GuiMainMenu(menu));
		}
		
		else {
			parentScreen.init();
			menu.setGuiScreen(parentScreen);			
		}
		
	}

}
