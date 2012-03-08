package bdt.world;

import java.awt.Graphics;
import java.awt.Color;

public class BlockColor extends Block {

	public static final String[] names = { "Red", "Orange", "Yellow", "Green",
			"Blue", "Purple", "Pink", "Black" };
	private Color color;

	public BlockColor(String name, int id) {
		super(Material.CUSTOM, name, id);
		color = BlockColor.valueOf(name);
	}

	public void render(Graphics g, int x, int y) {
		g.setColor(color);
		g.fillRect(x, y, 30, 30);
	}

	public void renderIcon(Graphics g, int x, int y, int width) {
		g.setColor(color);
		g.fillRect(x, y, width, width);
	}

	private static final Color valueOf(String color) {

		if (color.equals("Red")) {
			return Color.red;
		}

		else if (color.equals("Orange")) {
			return Color.orange;
		}

		else if (color.equals("Yellow")) {
			return Color.yellow;
		}

		else if (color.equals("Green")) {
			return Color.green;
		}

		else if (color.equals("Blue")) {
			return Color.blue;
		}

		else if (color.equals("Purple")) {
			return Color.magenta;
		}
		
		else if (color.equals("Pink")) {
			return Color.pink;
		}

		else {
			return Color.black;
		}

	}

}
