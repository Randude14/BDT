package bdt.gui.button;

import java.awt.event.KeyEvent;

public class KeyButton extends GuiButton {
	protected String keyName;

	public KeyButton(String keyName, int key, int id, int x, int y, int w) {
		super(null, id, x, y, w);
		this.keyName = keyName;
		setKey(key);
	}

	public void setKey(int key) {
		String str = KeyEvent.getKeyText(key);
		setText(keyName + ": " + str);
	}

}
