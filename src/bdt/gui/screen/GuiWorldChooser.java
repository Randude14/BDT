package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.util.List;

import bdt.world.World;
import bdt.world.WorldManager;
import bdt.screen.Screen;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.WorldButton;
import bdt.gui.button.GuiButton;
import bdt.gui.button.Label;

public abstract class GuiWorldChooser extends GuiScreen {
	private static final Font font = Screen.getFont(10);
	private static final FontMetrics metrics = Screen.getFontMetrics(font);

	private WorldButton[][] buttons;
	private List<World> worlds;
	private GuiButton nextButton;
	private GuiButton prevButton;
	private Label worldLabel;
	private Label pageLabel;
	protected World selectedWorld;
	private int mouseX;
	private int mouseY;
	private int currentPage;
	private int pages;
	private int hoverIndex;

	public GuiWorldChooser() {
		worlds = WorldManager.getWorlds();
		buttons = new WorldButton[3][3];

		pages = getPages(worlds);
		selectedWorld = null;
		currentPage = 1;
		hoverIndex = -1;
		int count = 0;

		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				WorldButton button = new WorldButton(worlds, count,
						150 + (c * 100), 50 + (r * 100));
				button.index = count++;
				addButton(button);
				buttons[r][c] = button;
			}

		}

		addButton(prevButton = new GuiButton("Prev Page", 10, 75, 380, 225));
		addButton(nextButton = new GuiButton("Next Page", 11, 300, 380, 225));
		addButton(pageLabel = new Label("Page 1/" + pages, 75, 20, 450));
		addButton(worldLabel = new Label("World Selected: ?", 75, 420, 450));
		nextButton.enabled = currentPage < pages;
		prevButton.enabled = currentPage > 1;
	}
	
	public void init() {
		int count = 0;
		currentPage = 1;
		
		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				
				buttons[r][c].index = count++;
				
			}

		}
		
		pageLabel.setText("Page 1/" + pages);
		worldLabel.setText("World Selected: ?");
		nextButton.enabled = currentPage < pages;
		prevButton.enabled = currentPage > 1;
	}

	public int getPages(List<?> list) {
		int pages = 1;
		int size = list.size();

		while ((size -= 9) > 0) {
			pages++;
		}

		return pages;
	}

	public boolean mouseClicked(int x, int y) {

		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				WorldButton button = buttons[r][c];

				if (button.isPressed(x, y)) {
					actionPerformed(button);
					return true;
				}

			}

		}

		return super.mouseClicked(x, y);
	}

	public boolean hover(int x, int y) {
		boolean flag = false;

		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				WorldButton button = buttons[r][c];

				if (button.hover(x, y)) {
					hoverIndex = button.index;
					mouseX = x;
					mouseY = y;
					flag = true;
				}

			}

		}

		if (!flag) {
			hoverIndex = -1;
			mouseX = -1;
			mouseY = -1;
		}

		return super.hover(x, y);
	}

	public void nextPage() {

		if (currentPage >= pages) {
			return;
		}

		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				WorldButton button = buttons[r][c];
				button.index = button.index + 9;
			}

		}

		currentPage++;
		nextButton.enabled = currentPage < pages;
		prevButton.enabled = currentPage > 1;
		pageLabel.setText("Page " + currentPage + "/" + pages);
	}

	public void prevPage() {

		if (currentPage <= 1) {
			return;
		}

		for (int r = 0; r < buttons.length; r++) {

			for (int c = 0; c < buttons[r].length; c++) {
				WorldButton button = buttons[r][c];
				button.index = button.index - 9;
			}

		}

		currentPage--;
		nextButton.enabled = currentPage < pages;
		prevButton.enabled = currentPage > 1;
		pageLabel.setText("Page " + currentPage + "/" + pages);
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 10) {
			prevPage();
		}

		else if (button.id == 11) {
			nextPage();
		}

		else if (button instanceof WorldButton) {
			WorldButton wButton = (WorldButton) button;
			int index = wButton.index;
			selectedWorld = worlds.get(index);
			worldLabel.setText("World Selected: " + selectedWorld.getName());
		}

	}

	public void render(Graphics g) {
		super.render(g);

		if (hoverIndex < 0 || hoverIndex > worlds.size() - 1) {
			return;
		}

		World world = worlds.get(hoverIndex);
		renderWorldInfoBox(g, world, mouseX, mouseY - 15);
	}

	public void renderWorldInfoBox(Graphics g, World world, int x, int y) {
		String worldName = "World: " + world.getName();
		int width = metrics.stringWidth(worldName);
		g.setColor(Color.black);
		g.fillRect(x, y, width + 10, 15);
		g.setColor(Color.yellow);
		g.setFont(font);
		g.drawString(worldName, x + 5, y + 11);
	}

}
