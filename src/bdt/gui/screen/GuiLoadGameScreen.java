package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Map;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.game.GameSession;
import bdt.game.MainMenu;
import bdt.game.BDTGame.Type;
import bdt.game.BDTGame;
import bdt.screen.Screen;
import bdt.world.World;

public class GuiLoadGameScreen extends GuiScreen {
	private GameStorage[] saves;
	private BDTGame game;
	private MainMenu menu;
	private FontMetrics metrics;
	private Font font;

	public GuiLoadGameScreen(MainMenu menu) {
		this.menu = menu;
		saves = new GameStorage[5];
		font = Screen.getFont(9);
		metrics = Screen.getFontMetrics(font);
		game = (BDTGame)GameSession.clientGame;
		int y = 20;

		for (int cntr = 1; cntr <= 5; cntr++) {
			String name = "Empty Slot";

			if (game.isSlotTaken(cntr)) {
				name = "Load Game";
				Map<String, Object> map = game.getSaveAt(cntr);
				World world = (World) map.get("world");
				String date = (String) map.get("date");
				Type type = Type.getType(((Integer) map.get("type")));
				saves[cntr - 1] = new GameStorage(world, date, type);
			}

			addButton(new GuiButton(name, cntr, 300, y += 80, 225));
		}

		addButton(new GuiButton("Cancel", 10, 75, y += 80, 450));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 10) {
			menu.setGuiScreen(new GuiLoadOrNewGameScreen(menu));
			return;
		}

		if (game.isSlotTaken(button.id)) {
			Map<String, Object> loadMap = game.getSaveAt(button.id);
			game.init(loadMap);
			Screen.setGameSession(game);
		}

	}

	public void render(Graphics g) {
		super.render(g);
		int y = 85;
		g.setFont(font);

		for (GameStorage storage : saves) {

			if (storage != null) {
				storage.world.renderIcon(g, 140, y, 3);
				g.setColor(Color.white);
				String date = storage.date;
				String name = storage.type.name;
				int dateWidth = metrics.stringWidth(date);
				int nameWidth = metrics.stringWidth(name);
				int x = ((dateWidth - nameWidth) / 2) + 205;
				g.drawString(date, 205, y + 20);
				g.drawString(name, x, y + 35);
			}

			y += 80;
		}

	}

	class GameStorage {
		public World world;
		public String date;
		public Type type;

		public GameStorage(World world, String date, Type type) {
			this.world = world;
			this.date = date;
			this.type = type;
		}

	}

}