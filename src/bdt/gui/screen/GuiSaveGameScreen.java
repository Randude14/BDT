package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.util.Map;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.game.BDTGame.Type;
import bdt.game.BDTGame;
import bdt.screen.Screen;
import bdt.world.World;

public class GuiSaveGameScreen extends GuiScreen {
	private GuiScreen parentScreen;
	private GameStorage[] saves;
	private BDTGame game;
	private FontMetrics metrics;
	private Font font;

	public GuiSaveGameScreen(GuiScreen screen, BDTGame game) {
		this.parentScreen = screen;
		this.game = game;
		saves = new GameStorage[5];
		font = Screen.getFont(9);
		metrics = Screen.getFontMetrics(font);
		int y = 20;

		for (int cntr = 1; cntr <= 5; cntr++) {
			String name = "Save";

			if (this.game.isSlotTaken(cntr)) {
				name = "Delete";
				Map<String, Object> map = game.getSaveAt(cntr);
				World world = (World) map.get("world");
				String date = (String) map.get("date");
				Type type = Type.getType(((Integer) map.get("type")));
				saves[cntr - 1] = new GameStorage(world, date, type);
			}

			addButton(new GuiButton(name, cntr, 350, y += 80, 175));
		}

		addButton(new GuiButton("Cancel", 10, 75, y += 80, 450));
	}

	public void init() {

		for (int cntr = 1; cntr <= 5; cntr++) {

			if (this.game.isSlotTaken(cntr)) {
				Map<String, Object> map = game.getSaveAt(cntr - 1);
				World world = (World) map.get("world");
				String date = (String) map.get("date");
				Type type = Type.getType(((Integer) map.get("type")));
				saves[cntr - 1] = new GameStorage(world, date, type);
			}

			else {
				saves[cntr - 1] = null;
			}

		}

	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 10) {
			game.setPauseScreen(parentScreen);
			return;
		}

		if (!game.isSlotTaken(button.id)) {
			game.saveGame(button.id);
		}

		else {
			game.setPauseScreen(new GuiDeleteOrCancelScreen(this, game, button.id));
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