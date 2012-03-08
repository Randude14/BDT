package bdt.world;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import bdt.tools.FileLoader;

public class BlockDefault extends Block {
	private BufferedImage image;

	public BlockDefault(Material material, String file, String name, int id) {
		super(material, name, id);
		image = FileLoader.loadImage(file);
	}

	public BlockDefault(String file, String name, int id) {
		this(Material.NORMAL, file, name, id);
	}

	public boolean isPath() {
		return false;
	}

	public void render(Graphics g, int x, int y) {
		g.drawImage(image, x, y, null);
	}

	public void renderIcon(Graphics g, int x, int y, int width) {
		g.drawImage(image, x, y, width, width, null);
	}

}
