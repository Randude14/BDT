package bdt.screen.utils;

import bdt.tools.KeyOptions;
import bdt.screen.Screen;

public class ThreadShutDown extends Thread {

	public ThreadShutDown() {
		super("Exit Thread");
		setPriority(Thread.MAX_PRIORITY);
		start();
	}

	public void run() {
		System.out.println("Stopping BDT...");
		KeyOptions.saveKeys();

		if (Screen.inFullScreenMode()) {
			Screen.toggle();
		}
		
		System.out.println("BDT stopped.");
		System.exit(0);
	}

}
