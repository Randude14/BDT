package bdt.gui.screen;

import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;

import bdt.screen.Screen;
import bdt.game.LevelEditor;
import bdt.tools.KeyOptions;
import bdt.gui.button.AbstractButton;
import bdt.gui.button.GuiButton;
import bdt.gui.button.KeyButton;

public class GuiEditorKeyControls extends GuiScreen {
	private FontMetrics metrics;
	private Font font;
	private String[] keyNames;
	private String title;
	private LevelEditor editor;
	private GuiScreen parent;
	private int selectedID;

	public GuiEditorKeyControls(LevelEditor editor, GuiScreen screen,
			String[] keyNames) {
		parent = screen;
		selectedID = -1;
		this.editor = editor;
		this.keyNames = keyNames;
		font = Screen.getFont(40);
		metrics = Screen.getFontMetrics(font);
		title = "Key Controls.";
		int y = 150;

		for (int cntr = 0; cntr < keyNames.length; cntr++) {
			addButton(new KeyButton(keyNames[cntr],
					KeyOptions.getKey(keyNames[cntr]), cntr, 75, y += 50, 450));
		}

		addButton(new GuiButton("Back", 10, 75, y += 50, 450));
	}

	public void actionPerformed(AbstractButton button) {

		if (button.id == 10) {
			editor.setPauseScreen(parent);
			return;
		}

		if (selectedID == -1) {
			selectedID = button.id;
			((KeyButton) button).setText(keyNames[button.id] + ": ?");
		}

		else {
			((KeyButton) getButtonAt(selectedID)).setText(keyNames[selectedID]
					+ ": " + KeyOptions.getKey(keyNames[selectedID]));
			selectedID = button.id;
			((KeyButton) button).setText(keyNames[button.id] + ": ?");
		}

	}

	public boolean keyPressed(int key) {

		if (selectedID != -1) {
			((KeyButton) getButtonAt(selectedID)).setKey(key);
			KeyOptions.setKey(keyNames[selectedID], key);
			selectedID = -1;
			return true;
		}

		return false;
	}

	public void render(Graphics g) {
		super.render(g);
		g.setColor(Color.red);
		g.setFont(font);
		int width = metrics.stringWidth(title);
		g.drawString(title, ((450 - width) / 2) + 75, 70);
	}

}
