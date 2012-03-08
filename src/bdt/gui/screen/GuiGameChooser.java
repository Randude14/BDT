package bdt.gui.screen;

import bdt.screen.Screen;
import bdt.game.GameSession;
import bdt.game.MainMenu;
import bdt.game.BDTGame.Type;
import bdt.game.BDTGame;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;

public class GuiGameChooser extends GuiWorldChooser {
	private MainMenu menu;
	private GuiButton typeButton;
	private GuiButton startButton;
	private int typeID;

	public GuiGameChooser(MainMenu menu) {
		typeID = Type.NORMAL.id;
		this.menu = menu;
		addButton(typeButton = new GuiButton("Game Mode: Normal", 15, 75, 460,
				450));
		addButton(startButton = new GuiButton("Start Game", 16, 75, 500, 450));
		addButton(new GuiButton("Cancel", 17, 75, 540, 450));
		startButton.enabled = false;
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 15) {
			typeID = (typeID == Type.NORMAL.id) ? Type.FREEPLAY.id
					: Type.NORMAL.id;
			Type type = Type.getType(typeID);
			typeButton.setText("Game Mode: " + type);
		}

		else if (button.id == 16) {
			Type type = Type.getType(typeID);
			BDTGame game = (BDTGame)GameSession.clientGame;
			game.init(selectedWorld, type);
			Screen.setGameSession(game);
		}

		else if (button.id == 17) {
			menu.setGuiScreen(new GuiLoadOrNewGameScreen(menu));
		}

		else {
			super.actionPerformed(button);
			startButton.enabled = selectedWorld != null;
		}

	}

}
