package bdt.tools;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.File;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import bdt.event.Capturable;

public class ScreenShot {
	private static final DateFormat formatter = new SimpleDateFormat(
			"yyyy-MM-dd_HH.mm.ss");
	private static final File file = new File("Screenshots");

	public static String takeScreenShot(Capturable capturable) {

		try {
			String name = formatter.format(new Date()) + ".png";
			file.mkdirs();
			BufferedImage image = capturable.capture();
			File file1 = new File(file, name);
			ImageIO.write(image, "png", file1);
			System.out.println("Screenshot saved.");
			return name;
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("Failed to save screenshot.");
			return null;
		}

	}

}
