package bdt.gui.screen;

import java.util.List;
import java.awt.FontMetrics;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Color;

import bdt.gui.button.GuiButton;
import bdt.gui.button.AbstractButton;
import bdt.game.GameSession;
import bdt.game.LevelEditor;
import bdt.game.BDTGame;
import bdt.screen.Screen;
import bdt.gui.button.BlockButton;
import bdt.tools.MathHelper;
import bdt.tools.ScreenShot;
import bdt.world.Block;

public class GuiLevelEditor extends GuiScreen {
	private GuiEditorOptions options;
	private BlockButton[] blockButtons;
	private BlockButton[] pathButtons;
	private GuiButton gridButton;
	private LevelEditor editor;
	private FontMetrics metrics;
	private List<Integer> blocks;
	private List<Integer> pathBlocks;
	private Font font;
	private boolean grid;

	public GuiLevelEditor(LevelEditor editor) {
		this.editor = editor;
		blockButtons = new BlockButton[5];
		pathButtons = new BlockButton[5];
		addButton(new GuiButton("Screenshot", 0, 600, 430, 180));
		addButton(gridButton = new GuiButton("Grid: Off", 1, 600, 470, 180));
		addButton(new GuiButton("Test Game", 2, 600, 510, 180));
		addButton(new GuiButton("Options", 3, 600, 550, 180));
		options = new GuiEditorOptions(editor);
		blocks = Block.getBlocksAsList();
		pathBlocks = Block.getPathsAsList();
		font = Screen.getFont(10);
		metrics = Screen.getFontMetrics(font);

		for (int cntr = 0; cntr < 5; cntr++) {
			int y = 45 + (cntr * 50);
			BlockButton button = new BlockButton(blocks, cntr + 10, 700, y);
			blockButtons[cntr] = button;
			addButton(button);
		}
		
		for (int cntr = 0; cntr < 5; cntr++) {
			int y = 45 + (cntr * 50);
			BlockButton button = new BlockButton(pathBlocks, cntr + 15, 650, y);
			pathButtons[cntr] = button;
			addButton(button);
		}

		grid = false;
	}

	public void init(boolean flag) {
		blockButtons[0].index = -2;
		blockButtons[1].index = -1;
		blockButtons[2].index = 0;
		blockButtons[3].index = 1;
		blockButtons[4].index = 2;
		pathButtons[0].index = -2;
		pathButtons[1].index = -1;
		pathButtons[2].index = 0;
		pathButtons[3].index = 1;
		pathButtons[4].index = 2;
		options.init(flag);
		this.enableButtons();
	}

	public int getSelectedBlockID() {
		return blocks.get(blockButtons[2].index);
	}
	
	public int getSelectedPathID() {
		return pathBlocks.get(pathButtons[2].index);
	}

	public boolean shouldShowGrid() {
		return grid;
	}

	public int getBlockAtCursor(int x, int y) {

		for (BlockButton button : blockButtons) {

			if (button.hover(x, y)) {
				return button.index;
			}

		}

		return -1;
	}
	
	public int getPathAtCursor(int x, int y) {
		
		for (BlockButton button : pathButtons) {

			if (button.hover(x, y)) {
				return button.index;
			}

		}
		
		return -1;
	}

	public synchronized void scrollBlockButtons(int clicks) {

		if (clicks < 0) {

			if (blockButtons[2].index == blocks.size() - 1) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				blockButtons[cntr].index++;
			}

			hover(editor.mouseX, editor.mouseY);
		}

		else {

			if (blockButtons[2].index == 0) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				blockButtons[cntr].index--;
			}

			hover(editor.mouseX, editor.mouseY);
		}

	}
	
	public synchronized void scrollPathButtons(int clicks) {

		if (clicks < 0) {

			if (pathButtons[2].index >= pathBlocks.size() - 1) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				pathButtons[cntr].index++;
			}

			hover(editor.mouseX, editor.mouseY);
		}

		else {

			if (pathButtons[2].index <= 0) {
				return;
			}

			for (int cntr = 0; cntr < 5; cntr++) {
				pathButtons[cntr].index--;
			}

			hover(editor.mouseX, editor.mouseY);
		}

	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 0) {
			ScreenShot.takeScreenShot(editor.getWorld());
		}

		if (button.id == 1) {
			grid = !grid;
			String str = (grid) ? "On" : "Off";
			gridButton.setText("Grid: " + str);
		}

		if (button.id == 2) {
			BDTGame game = (BDTGame) GameSession.clientGame;
			game.init(editor.getWorld(), BDTGame.Type.TEST);
			Screen.setGameSession(game);
		}

		if (button.id == 3) {
			editor.setPauseScreen(options);
			editor.pause();
		}

		if (button.id == 10) {
			scrollBlockButtons(1);
			scrollBlockButtons(1);
		}

		if (button.id == 11) {
			scrollBlockButtons(1);
		}

		if (button.id == 13) {
			scrollBlockButtons(-1);
		}

		if (button.id == 14) {
			scrollBlockButtons(-1);
			scrollBlockButtons(-1);
		}
		
		if(button.id == 15) {
			scrollPathButtons(1);
			scrollPathButtons(1);
		}
		
		if(button.id == 16) {
			scrollPathButtons(1);
		}
		
		if(button.id == 17) {
			scrollPathButtons(-1);
		}
		
		if(button.id == 18) {
			scrollPathButtons(-1);
			scrollPathButtons(-1);	
		}

	}

	public boolean scroll(int clicks, int x, int y) {

		if(MathHelper.doesRectContainPoint(x, y, 700, 45, 30, 230)) {
			scrollBlockButtons(clicks);
		}
		
		if(MathHelper.doesRectContainPoint(x, y, 650, 45, 30, 230)) {
			scrollPathButtons(clicks);
		}
		
		return false;
	}

	public void renderBar(Graphics g, int x, int y, int length, int width) {
		g.setColor(Color.black);

		for (int cntr = 0; cntr < 5; cntr++) {
			g.drawRect(x++, y++, length -= 2, width -= 2);
		}

	}

	public void render(Graphics g) {
		super.render(g);
		renderBar(g, 600, 0, 181, 601);
		g.fillRect(600, 320, 180, 5);
		g.setColor(Color.yellow);
		g.drawRect(640, 135, 100, 50);

		int blockIndex = getBlockAtCursor(editor.mouseX, editor.mouseY);

		if (blockIndex >= 0 && blockIndex < blocks.size()) {
			int ID = blocks.get(blockIndex);
			renderBlockInfoBox(g, "Block Name: ", editor.mouseX, editor.mouseY - 15, ID);
		}
		
		int pathIndex = getPathAtCursor(editor.mouseX, editor.mouseY);

		if (pathIndex >= 0 && pathIndex < pathBlocks.size()) {
			int ID = pathBlocks.get(pathIndex);
			renderBlockInfoBox(g, "Path Name: ", editor.mouseX, editor.mouseY - 15, ID);
		}
		
	}

	public void renderBlockInfoBox(Graphics g, String message, int x, int y, int ID) {
		Block block = Block.getBlockAtID(ID);
		String name = message + block.blockName;
		int width = metrics.stringWidth(name);

		if (y < 0) {
			y += 15;
		}

		if (x + width + 10 > Screen.getWidth()) {
			x -= width + 10;
		}

		g.setFont(font);
		g.setColor(Color.black);
		g.fillRect(x, y, width + 10, 15);
		g.setColor(Color.yellow);
		g.drawString(name, x + 5, y + 11);
	}

}
