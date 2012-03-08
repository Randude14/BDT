package bdt.game;

import java.awt.Graphics;
import java.awt.Color;

import bdt.gui.screen.GuiLoadOrNewGameScreen;
import bdt.gui.screen.GuiLoadOrCreateScreen;
import bdt.gui.screen.GuiMainMenu;
import bdt.gui.screen.GuiScreen;
import bdt.world.World;
import bdt.event.Mouse;

public class MainMenu extends GameSession {
	private GuiScreen screen;
	private World world = new World("fill");
	private Title title = new Title();

	protected MainMenu(int id) {
		super(id);
	}

	public void init() {
		setGuiScreen(new GuiMainMenu(this));
	}

	public void keyPressed(int key) {
		screen.keyPressed(key);
	}

	public void mouseMoved(int x, int y) {
		screen.hover(x, y);
	}

	public void mousePressed(Mouse mouse, int x, int y) {

		if (mouse == Mouse.LEFT_CLICK) {
			screen.mouseClicked(x, y);
		}

	}

	public void setGuiScreen(GuiScreen screen) {

		if (!canShowTitle(this.screen) && canShowTitle(screen)) {
			title.reset();
		}

		this.screen = screen;
	}

	public void render(Graphics g) {
		world.render(g);
		renderBackGround(g);
		screen.render(g);

		if (canShowTitle(screen)) {
			title.render(g);
		}

	}

	private boolean canShowTitle(GuiScreen screen) {
		return screen instanceof GuiMainMenu
				|| screen instanceof GuiLoadOrNewGameScreen
				|| screen instanceof GuiLoadOrCreateScreen || screen == null;
	}

	public void update() {
		screen.update();

		if (canShowTitle(screen)) {
			title.update();
		}

	}

	public String getTitle() {
		return "Main Menu";
	}

	class Title {
		private TitleRow[] titleRows;
		private boolean flag;
		private int time;

		public Title() {
			titleRows = new TitleRow[46];
			Color eye = new Color(0, 19, 127);
			flag = false;
			time = 0;

			for (int cntr = 0; cntr < titleRows.length; cntr++) {
				titleRows[cntr] = new TitleRow(136, ((cntr * 4) + 32),
						titleRows.length - cntr);
			}

			// //////////////////B
			colorRect(Color.red, 14, 0, 6, 2);
			colorRect(Color.red, 14, 2, 2, 2);
			colorRect(Color.red, 20, 2, 2, 2);
			colorRect(Color.red, 14, 4, 6, 2);
			colorRect(Color.red, 14, 6, 2, 2);
			colorRect(Color.red, 20, 6, 2, 2);
			colorRect(Color.red, 14, 8, 2, 2);
			colorRect(Color.red, 20, 8, 2, 2);
			colorRect(Color.red, 14, 10, 2, 2);
			colorRect(Color.red, 20, 10, 2, 2);
			colorRect(Color.red, 14, 12, 6, 2);
			// ////////////////

			// ///////////////////L
			colorRect(Color.yellow, 24, 0, 2, 14);
			colorRect(Color.yellow, 26, 12, 6, 2);
			// ////////////////

			// ///////////////////O
			colorRect(Color.green, 34, 0, 10, 14);
			colorRect(Color.white, 35, 1, 3, 3);
			colorRect(Color.white, 40, 1, 3, 3);
			colorRect(eye, 36, 3, 1, 1);
			colorRect(eye, 41, 3, 1, 1);
			// //////////////////

			// ///////////////////C
			colorRect(Color.blue, 48, 0, 6, 2);
			colorRect(Color.blue, 46, 2, 2, 2);
			colorRect(Color.blue, 54, 2, 2, 2);
			colorRect(Color.blue, 46, 4, 2, 2);
			colorRect(Color.blue, 46, 6, 2, 2);
			colorRect(Color.blue, 46, 8, 2, 2);
			colorRect(Color.blue, 46, 10, 2, 2);
			colorRect(Color.blue, 54, 10, 2, 2);
			colorRect(Color.blue, 48, 12, 6, 2);
			// //////////////////

			// ///////////////////K
			colorRect(Color.red, 58, 0, 2, 2);
			colorRect(Color.red, 66, 0, 2, 2);
			colorRect(Color.red, 58, 2, 2, 2);
			colorRect(Color.red, 64, 2, 2, 2);
			colorRect(Color.red, 58, 4, 6, 2);
			colorRect(Color.red, 58, 6, 2, 2);
			colorRect(Color.red, 64, 6, 2, 2);
			colorRect(Color.red, 58, 8, 2, 2);
			colorRect(Color.red, 66, 8, 2, 2);
			colorRect(Color.red, 58, 10, 2, 2);
			colorRect(Color.red, 66, 10, 2, 2);
			colorRect(Color.red, 58, 12, 2, 2);
			colorRect(Color.red, 66, 12, 2, 2);
			// //////////////////

			// //////////////////D
			colorRect(Color.yellow, 0, 16, 8, 2);
			colorRect(Color.yellow, 0, 18, 2, 2);
			colorRect(Color.yellow, 8, 18, 2, 2);
			colorRect(Color.yellow, 0, 20, 2, 2);
			colorRect(Color.yellow, 8, 20, 2, 2);
			colorRect(Color.yellow, 0, 22, 2, 2);
			colorRect(Color.yellow, 8, 22, 2, 2);
			colorRect(Color.yellow, 0, 24, 2, 2);
			colorRect(Color.yellow, 8, 24, 2, 2);
			colorRect(Color.yellow, 0, 26, 2, 2);
			colorRect(Color.yellow, 8, 26, 2, 2);
			colorRect(Color.yellow, 0, 28, 8, 2);
			// /////////////////

			// /////////////E
			colorRect(Color.green, 12, 16, 10, 2);
			colorRect(Color.green, 12, 18, 2, 2);
			colorRect(Color.green, 12, 20, 6, 2);
			colorRect(Color.green, 12, 22, 2, 2);
			colorRect(Color.green, 12, 24, 2, 2);
			colorRect(Color.green, 12, 26, 2, 2);
			colorRect(Color.green, 12, 28, 10, 2);
			// /////////////

			// /////////////F
			colorRect(Color.blue, 24, 16, 10, 2);
			colorRect(Color.blue, 24, 18, 2, 2);
			colorRect(Color.blue, 24, 20, 6, 2);
			colorRect(Color.blue, 24, 22, 2, 2);
			colorRect(Color.blue, 24, 24, 2, 2);
			colorRect(Color.blue, 24, 26, 2, 2);
			colorRect(Color.blue, 24, 28, 2, 2);
			// /////////////

			// /////////////E
			colorRect(Color.red, 36, 16, 10, 2);
			colorRect(Color.red, 36, 18, 2, 2);
			colorRect(Color.red, 36, 20, 6, 2);
			colorRect(Color.red, 36, 22, 2, 2);
			colorRect(Color.red, 36, 24, 2, 2);
			colorRect(Color.red, 36, 26, 2, 2);
			colorRect(Color.red, 36, 28, 10, 2);
			// /////////////

			// ////////////N
			colorRect(Color.yellow, 48, 16, 2, 2);
			colorRect(Color.yellow, 56, 16, 2, 2);
			colorRect(Color.yellow, 48, 18, 4, 2);
			colorRect(Color.yellow, 56, 18, 2, 2);
			colorRect(Color.yellow, 48, 20, 2, 2);
			colorRect(Color.yellow, 52, 20, 2, 2);
			colorRect(Color.yellow, 56, 20, 2, 2);
			colorRect(Color.yellow, 48, 22, 2, 2);
			colorRect(Color.yellow, 54, 22, 4, 2);
			colorRect(Color.yellow, 48, 24, 2, 2);
			colorRect(Color.yellow, 56, 24, 2, 2);
			colorRect(Color.yellow, 48, 26, 2, 2);
			colorRect(Color.yellow, 56, 26, 2, 2);
			colorRect(Color.yellow, 48, 28, 2, 2);
			colorRect(Color.yellow, 56, 28, 2, 2);
			// /////////////

			// ////////////S
			colorRect(Color.green, 62, 16, 8, 2);
			colorRect(Color.green, 60, 18, 2, 2);
			colorRect(Color.green, 62, 20, 6, 2);
			colorRect(Color.green, 68, 22, 2, 2);
			colorRect(Color.green, 68, 24, 2, 2);
			colorRect(Color.green, 60, 26, 2, 2);
			colorRect(Color.green, 68, 26, 2, 2);
			colorRect(Color.green, 62, 28, 6, 2);
			// /////////////

			// /////////////E
			colorRect(Color.blue, 72, 16, 10, 2);
			colorRect(Color.blue, 72, 18, 2, 2);
			colorRect(Color.blue, 72, 20, 6, 2);
			colorRect(Color.blue, 72, 22, 2, 2);
			colorRect(Color.blue, 72, 24, 2, 2);
			colorRect(Color.blue, 72, 26, 2, 2);
			colorRect(Color.blue, 72, 28, 10, 2);
			// /////////////

			// ///////////T
			colorRect(Color.red, 12, 32, 10, 2);
			colorRect(Color.red, 16, 34, 2, 12);
			// /////////////

			// ///////////////////O
			colorRect(Color.yellow, 24, 32, 10, 14);
			colorRect(Color.white, 25, 33, 3, 3);
			colorRect(Color.white, 30, 33, 3, 3);
			colorRect(eye, 27, 34, 1, 1);
			colorRect(eye, 32, 34, 1, 1);
			// //////////////////

			// /////////////////W
			colorRect(Color.green, 36, 32, 2, 2);
			colorRect(Color.green, 44, 32, 2, 2);
			colorRect(Color.green, 36, 34, 2, 2);
			colorRect(Color.green, 44, 34, 2, 2);
			colorRect(Color.green, 36, 36, 2, 2);
			colorRect(Color.green, 44, 36, 2, 2);
			colorRect(Color.green, 36, 38, 2, 2);
			colorRect(Color.green, 44, 38, 2, 2);
			colorRect(Color.green, 36, 40, 2, 2);
			colorRect(Color.green, 40, 40, 2, 2);
			colorRect(Color.green, 44, 40, 2, 2);
			colorRect(Color.green, 36, 42, 4, 2);
			colorRect(Color.green, 42, 42, 4, 2);
			colorRect(Color.green, 36, 44, 2, 2);
			colorRect(Color.green, 44, 44, 2, 2);
			// //////////////////

			// /////////////E
			colorRect(Color.blue, 48, 32, 10, 2);
			colorRect(Color.blue, 48, 34, 2, 2);
			colorRect(Color.blue, 48, 36, 6, 2);
			colorRect(Color.blue, 48, 38, 2, 2);
			colorRect(Color.blue, 48, 40, 2, 2);
			colorRect(Color.blue, 48, 42, 2, 2);
			colorRect(Color.blue, 48, 44, 10, 2);
			// /////////////

			// ////////////R
			colorRect(Color.red, 60, 32, 8, 2);
			colorRect(Color.red, 60, 34, 2, 2);
			colorRect(Color.red, 68, 34, 2, 2);
			colorRect(Color.red, 60, 36, 8, 2);
			colorRect(Color.red, 60, 38, 2, 2);
			colorRect(Color.red, 68, 38, 2, 2);
			colorRect(Color.red, 60, 40, 2, 2);
			colorRect(Color.red, 68, 40, 2, 2);
			colorRect(Color.red, 60, 42, 2, 2);
			colorRect(Color.red, 68, 42, 2, 2);
			colorRect(Color.red, 60, 44, 2, 2);
			colorRect(Color.red, 68, 44, 2, 2);
			// /////////////
		}

		public void update() {

			for (TitleRow row : titleRows) {
				row.update();
			}

			if (time++ == 1000) {

				if (flag) {

					for (TitleRow row : titleRows) {
						row.resetVertical();
					}

				}

				else {

					for (TitleRow row : titleRows) {
						row.resetHorizontal();
					}

				}

				time = 0;
				flag = !flag;
			}

		}

		public void reset() {

			for (TitleRow row : titleRows) {
				row.resetVertical();
			}

			time = 0;
			flag = false;
		}

		public void colorRect(Color color, int dx, int dy, int w, int h) {

			for (int x = dx; x < dx + w; x++) {

				for (int y = dy; y < dy + h; y++) {
					titleRows[y].setColor(x, color);
				}

			}

		}

		public void render(Graphics g) {

			for (int cntr = 0; cntr < titleRows.length; cntr++) {
				titleRows[cntr].render(g);
			}

		}

		class TitleRow {
			private Color[] colors;
			private int cntr;
			private int stopx;
			private int stopy;
			private int velx;
			private int vely;
			private int x, y;

			public TitleRow(int x, int y, int cntr) {
				colors = new Color[82];
				stopx = x;
				stopy = y;
				this.cntr = cntr;
				this.x = stopx;
				this.y -= 50 + (cntr * 12);
				velx = 0;
				vely = 2;
			}

			public void resetVertical() {
				x = stopx;
				y = 0;
				y -= 50 + (cntr * 12);
				velx = 0;
				vely = 2;
			}

			public void resetHorizontal() {
				y = stopy;
				x = (cntr % 2 == 0) ? stopx + 650 : stopx - 650;
				velx = (cntr % 2 == 0) ? -5 : 5;
				vely = 0;
			}

			public void setColor(int index, Color color) {
				colors[index] = color;
			}

			public boolean update() {

				if (x != stopx) {
					x += velx;
				}

				if (y != stopy) {
					y += vely;
				}

				return x == stopx && y == stopy;
			}

			public void render(Graphics g) {

				for (int cntr = 0; cntr < colors.length; cntr++) {
					int x = (cntr * 4) + this.x;

					if (colors[cntr] != null) {
						g.setColor(colors[cntr]);
						g.fillRect(x, y, 4, 4);
					}

				}

			}

		}

	}

}
