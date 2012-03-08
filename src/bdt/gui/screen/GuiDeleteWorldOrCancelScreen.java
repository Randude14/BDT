package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.game.MainMenu;
import bdt.screen.Screen;
import bdt.world.World;
import bdt.world.WorldManager;

public class GuiDeleteWorldOrCancelScreen extends GuiScreen {
	private MainMenu menu;
	private String title1;
	private String title2;
	private Font font;
	private FontMetrics metrics;
	private World world;
	private int width;
	private int x;

	public GuiDeleteWorldOrCancelScreen(MainMenu menu, World world) {
		this.menu = menu;
		width = 450;
		x = 75;
		font = Screen.getFont(30);
		metrics = Screen.getFontMetrics(font);
		this.world = world;
		title1 = "Are you sure you want";
		title2 = "to delete this world?";
		addButton(new GuiButton("Delete", 1, 75, 250, 225));
		addButton(new GuiButton("Cancel", 2, 300, 250, 225));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 1) {
			WorldManager.deleteWorld(world);
		}

		menu.setGuiScreen(new GuiMainMenu(menu));
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
