package bdt.world;

import java.io.Serializable;

public class Point implements Serializable {
	private static final long serialVersionUID = 3112676144118040947L;
	private int x, y;

	public Point(int x, int y) {
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public boolean equals(Object obj) {

		if (!(obj instanceof Point)) {
			return false;
		}

		Point point = (Point) obj;

		return x == point.x && y == point.y;
	}

	public boolean equals(int x, int y) {
		return this.x == x && this.y == y;
	}
	
	public int hashCode() {
		return toString().hashCode();
	}

	public String toString() {
		return "(" + x + ", " + y + ")";
	}

}
