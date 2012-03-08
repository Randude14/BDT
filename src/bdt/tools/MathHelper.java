package bdt.tools;

import bdt.world.Point;

public final class MathHelper {

	public static boolean doesRectContainPoint(int x1, int y1, int x2, int y2,
			int width, int height) {
		return x1 >= x2 && x1 < x2 + width && y1 >= y2 && y1 < y2 + height;
	}

	public static boolean doesCircleContainPoint(int x1, int y1, int x2,
			int y2, int radius) {
		double d1 = Math.pow(((x2 + radius) - x1), 2);
		double d2 = Math.pow(((y2 + radius) - y1), 2);

		return Math.sqrt(d1 + d2) <= radius;
	}

	public static double distance(int x1, int y1, int x2, int y2) {
		double d1 = Math.pow(x2 - x1, 2);
		double d2 = Math.pow(y2 - y1, 2);

		return Math.sqrt(d1 + d2);
	}

	public static double distance(Point p1, Point p2) {
		return distance(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

	public static double slope(int x1, int y1, int x2, int y2) {
		return (y2 - y1) / (x2 - x1);
	}

	public static double slope(Point p1, Point p2) {
		return slope(p1.getX(), p1.getY(), p2.getX(), p2.getY());
	}

}
