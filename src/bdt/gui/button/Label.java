package bdt.gui.button;

public class Label extends GuiButton {

	public Label(String mess, int x, int y, int w) {
		super(mess, -1, x, y, w);
	}

	public Label(String mess, int x, int y) {
		super(mess, -1, x, y);
	}

	public boolean isPressed(int x, int y) {
		return false;
	}

	public boolean hover(int x, int y) {
		return false;
	}

}
