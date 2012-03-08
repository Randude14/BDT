package bdt.screen.utils;

import javax.swing.JComponent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;

import bdt.game.GameSession;
import bdt.screen.Screen;
import bdt.tools.FileLoader;

@SuppressWarnings("serial")
public class ScreenPainter extends JComponent {
	private BufferedImage toolBar;

	public ScreenPainter() {
		setPreferredSize(new Dimension(780, 600));
		toolBar = FileLoader.loadImage("Gui/toolbar.png");
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		if (Screen.inFullScreenMode()) {
			g.setColor(Color.black);
			g.fillRect(0, 0, 10, 600);
			g.fillRect(790, 0, 10, 600);
			g.translate(-Screen.transX, -Screen.transY);
		}

		g.drawImage(toolBar, 600, 0, null);
		GameSession session = Screen.session;

		if (session == null) {
			return;
		}

		session.render(g);

		if (Screen.inFullScreenMode()) {
			g.setColor(Color.black);
			g.fillRect(-10, 0, 10, 600);
			g.fillRect(780, 0, 10, 600);
		}

	}

	public int getToolBarWidth() {
		return toolBar.getWidth(null);
	}

	public int getToolBarHeight() {
		return toolBar.getHeight(null);
	}

}
