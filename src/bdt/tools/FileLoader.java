package bdt.tools;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.Scanner;
import java.io.File;
import java.io.InputStream;

public class FileLoader {

	public static BufferedImage loadImage(String file) {

		try {
			BufferedImage image = ImageIO.read(FileLoader.class
					.getResource(file));
			return image;
		} catch (Exception ex) {
			throw new RuntimeException(new StringBuffer()
					.append("could not load image file: ").append(file)
					.toString());
		}

	}
	
	public static BufferedImage loadImage(File file) {
		return FileLoader.loadImage(file.getPath());
	}

	public static Scanner loadScanner(String file) {

		try {
			return new Scanner(FileLoader.class.getResourceAsStream(file));
		} catch (Exception ex) {
			throw new RuntimeException(new StringBuffer()
					.append("could not load scanner file: ").append(file)
					.toString());
		}

	}
	
	public static Scanner loadScanner(File file) {
		return FileLoader.loadScanner(file.getPath());
	}

	public static InputStream loadStream(String file) {

		try {
			return FileLoader.class.getResourceAsStream(file);
		} catch (Exception ex) {
			throw new RuntimeException(new StringBuffer()
					.append("could not load inputstream file: ").append(file)
					.toString());
		}

	}
	
	public static InputStream loadStream(File file) {
		return FileLoader.loadStream(file.getPath());
	}

}
