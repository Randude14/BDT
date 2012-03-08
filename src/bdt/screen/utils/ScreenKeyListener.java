package bdt.screen.utils;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.BitSet;

import bdt.screen.Screen;

public class ScreenKeyListener extends KeyAdapter {
	private BitSet keys;

	public ScreenKeyListener() {
		keys = new BitSet();
	}

	public boolean isKeyPressed(Integer key) {
		return keys.get(key);
	}

	public void keyPressed(KeyEvent event) {

		if (Screen.session == null) {
			return;
		}

		int key = event.getKeyCode();
		Screen.session.keyPressed(key);
		keys.set(key);
	}

	public void keyReleased(KeyEvent event) {
		int key = event.getKeyCode();

		if (key == KeyEvent.VK_F10 && Screen.canGoFullScreen()) {
			Screen.toggle();
			return;
		}

		if (Screen.session == null) {
			return;
		}

		Screen.session.keyReleased(key);
		keys.set(key, false);
	}

}
