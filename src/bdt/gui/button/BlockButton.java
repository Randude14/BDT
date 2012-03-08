package bdt.gui.button;

import java.awt.Rectangle;
import java.awt.Graphics;
import java.util.List;

import bdt.world.Block;

public class BlockButton extends AbstractButton {
	private int x, y;
	public int index;
	private Rectangle bounds;
	private List<Integer> blocks;

	public BlockButton(List<Integer> blocks, int id, int x, int y) {
		super(id);
		this.blocks = blocks;
		bounds = new Rectangle(x, y, 30, 30);
		this.x = x;
		this.y = y;
		index = 0;
	}

	public boolean isPressed(int x, int y) {
		return bounds.contains(x, y);
	}

	public boolean hover(int x, int y) {
		return bounds.contains(x, y);
	}

	public void render(Graphics g) {

		if (index < 0 || index > blocks.size() - 1) {
			return;
		}

		Block block = Block.getBlockAtID(blocks.get(index));
		block.render(g, x, y);
	}

}
