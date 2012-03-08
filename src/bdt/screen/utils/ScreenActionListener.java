package bdt.screen.utils;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import bdt.screen.Screen;

public class ScreenActionListener implements ActionListener {

	public void actionPerformed(ActionEvent event) {

		try {

			if (Screen.session == null) {
				return;
			}

			if (!Screen.isPaused) {
				Screen.session.update();
			}

			Screen.repaint();

		} catch (OutOfMemoryError error) {
			error.printStackTrace();
			System.gc();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

}
