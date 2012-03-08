package bdt.screen.utils;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseEvent;
import java.awt.Point;

import bdt.event.Mouse;
import bdt.screen.Screen;

public class ScreenMouseListener extends MouseAdapter {

	public ScreenMouseListener() {
	}

	public void mouseMoved(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mouseMoved(point.x, point.y);
	}

	public void mouseDragged(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Mouse mouse = Mouse.getMouseById(event.getButton());
		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mouseDragged(mouse, point.x, point.y);
	}

	public void mousePressed(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Mouse mouse = Mouse.getMouseById(event.getButton());
		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mousePressed(mouse, point.x, point.y);
	}

	public void mouseReleased(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Mouse mouse = Mouse.getMouseById(event.getButton());
		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mouseReleased(mouse, point.x, point.y);
	}

	public void mouseEntered(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mouseEntered(point.x, point.y);
	}

	public void mouseExited(MouseEvent event) {

		if (Screen.session == null) {
			return;
		}

		Screen.session.mouseExited();
	}

	public void mouseWheelMoved(MouseWheelEvent event) {

		if (Screen.session == null) {
			return;
		}

		int clicks = event.getWheelRotation();
		Point point = event.getPoint();
		Screen.translate(point);
		Screen.session.mouseWheel(clicks, point.x, point.y);
	}

}
