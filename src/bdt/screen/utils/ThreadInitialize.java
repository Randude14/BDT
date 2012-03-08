package bdt.screen.utils;

import java.awt.Font;
import java.io.File;
import java.io.InputStream;

import bdt.world.World;
import bdt.world.WorldManager;
import bdt.tools.KeyOptions;
import bdt.tools.FileLoader;
import bdt.game.GameSession;
import bdt.game.LevelEditor;
import bdt.game.MainMenu;
import bdt.screen.Screen;

public class ThreadInitialize extends Thread {
	private String[] args;

	public ThreadInitialize(String[] args) {
		super("Thread Startup");
		this.args = args;
		setPriority(Thread.MAX_PRIORITY);
		start();
	}

	public void run() {
		System.out.println("Initializing BDT...");
		KeyOptions.loadKeys();
		WorldManager.loadWorlds();
		loadFont();
		Screen.setFont(new Font("Ariels", Font.BOLD, 1));
		setGameSession();
		Screen.start();
		System.out.println("BDT Initialized.");
	}

	private void setGameSession() {

		if (args.length == 0) {
			MainMenu menu = (MainMenu) GameSession.mainMenu;
			Screen.setGameSession(menu);
			return;
		}

		File file = new File(args[0]);

		if (args[0].endsWith(".world") && file.exists()) {
			LevelEditor editor = (LevelEditor) GameSession.levelEditor;
			World world = WorldManager.loadWorld(file);
			editor.init(world, args[0], true);
			Screen.setGameSession(editor);
		}

	}

	private void loadFont() {

		try {
			InputStream input = FileLoader.loadStream("Fonts/font.ttf");
			Font font = Font.createFont(Font.TRUETYPE_FONT, input);
			Screen.setFont(font);
			System.out.println("Font loaded.");
		} catch (Exception ex) {
			System.out.println("Failed to load font.");
			Screen.setFont(new Font("Ariels", Font.BOLD, 0));
		}

	}

}
