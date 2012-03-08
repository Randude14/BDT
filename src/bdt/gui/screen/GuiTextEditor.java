package bdt.gui.screen;

import java.awt.event.KeyEvent;
import java.awt.Graphics;
import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Font;
import java.util.HashMap;
import java.util.Map;

import bdt.screen.Screen;

public abstract class GuiTextEditor extends GuiScreen {
	private static final Font fontTitle = Screen.getFont(40);
	private static final FontMetrics metricsTitle = Screen.getFontMetrics(fontTitle);
	private static final Font font = Screen.getFont(35);
	private static final FontMetrics metrics = Screen.getFontMetrics(font);

	protected String text;
	private String title;
	private boolean flag;
	private int count;
	private int y;

	public GuiTextEditor(String title, int y) {
		this.title = title;
		text = "";
		count = 0;
		flag = false;
		this.y = y;
	}

	public boolean keyPressed(int key) {
		System.out.println(key);

		if (key == KeyEvent.VK_BACK_SPACE && text.length() != 0) {
			text = text.substring(0, text.length() - 1);
			Screen.repaint();
			return true;
		}

		if (text.length() > 14) {
			return false;
		}

		String character = null;
		
		if(Screen.isKeyPressed(KeyEvent.VK_SHIFT)) {
			character = upperChars.get(key);
		}
		
		else {
			character = lowerChars.get(key);
		}
		
		if(character != null) {
			text += character;
			return true;
		}

		return false;
	}

	public void update() {
		count++;

		if (count == 30) {
			flag = !flag;
			count = 0;
		}

	}

	public void render(Graphics g) {
		super.render(g);
		String text = this.text;
		int width = metrics.stringWidth(text);
		
		if(flag) {
			text += "_";
		}
		
		int x = ((450 - (width + 20)) / 2) + 75;
		g.setFont(font);
		g.setColor(Color.red);
		g.drawString(text, x, y);
		
		int titleWidth = metricsTitle.stringWidth(title);
		int titleX = ((450 - titleWidth) / 2) + 75;
		g.setFont(fontTitle);
		g.drawString(title, titleX, y - 140);
        
	}
	
	

	private static final Map<Integer, String> lowerChars;
	private static final Map<Integer, String> upperChars;
	
	

	static {
		lowerChars = new HashMap<Integer, String>();
		upperChars = new HashMap<Integer, String>();
		lowerChars.put(KeyEvent.VK_SPACE, " ");
		upperChars.put(KeyEvent.VK_SPACE, " ");

		for (int cntr = 65; cntr < 91; cntr++) {
			lowerChars.put(cntr, String.format("%c", cntr).toLowerCase());
			upperChars.put(cntr, String.format("%c", cntr));
		}

		for (int cntr = 96; cntr < 106; cntr++) {
			lowerChars.put(cntr, "" + (cntr - 96));
			upperChars.put(cntr, "" + (cntr - 96));
		}

		for (int cntr = 48; cntr < 58; cntr++) {
			lowerChars.put(cntr, "" + (cntr - 48));
		}
		
		upperChars.put(KeyEvent.VK_1, "!");
		upperChars.put(KeyEvent.VK_2, "@");
		upperChars.put(KeyEvent.VK_3, "#");
		upperChars.put(KeyEvent.VK_4, "$");
		upperChars.put(KeyEvent.VK_5, "%");
		upperChars.put(KeyEvent.VK_6, "^");
		upperChars.put(KeyEvent.VK_7, "&");
		upperChars.put(KeyEvent.VK_8, "*");
		upperChars.put(KeyEvent.VK_9, "(");
		upperChars.put(KeyEvent.VK_0, ")");
		

	}

}
