package bdt.enemy;

import java.awt.Image;
import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.Rectangle;
import java.io.Serializable;

import bdt.tools.FileLoader;
import bdt.world.Point;
import bdt.event.Boundable;

public abstract class Enemy implements Boundable, Serializable {
	private static final long serialVersionUID = 6723994372069770057L;
	protected static final Image[] images = {
			FileLoader.loadImage("Enemy/eyes_up.png"),
			FileLoader.loadImage("Enemy/eyes_right.png"),
			FileLoader.loadImage("Enemy/eyes_down.png"),
			FileLoader.loadImage("Enemy/eyes_left.png") };
	protected static final Image frozen = FileLoader
			.loadImage("Enemy/frozen.png");
	protected static final Random random = new Random();

	public final double width;
	public final double height;
	public double x;
	public double y;
	protected double step;
	protected double velx;
	protected double vely;
	protected List<Point> path;
	protected Direction direction;
	protected int freezeCount;
	protected int index;
	protected int health;
	protected int count;
	protected Type type;
	protected Rectangle bounds;

	protected Enemy(double w, double h) {
		width = w;
		height = h;
		step = 1;
		health = 1;
		count = 0;
		type = Type.NORMAL;
	}

	protected Enemy() {
		this(30, 30);
	}

	public int getLivesCost() {
		return 1;
	}

	public Rectangle getBounds() {
		return bounds;
	}

	protected void setType(Type type) {
		this.type = type;
	}

	protected void setHealth(int health) {
		this.health = health;
	}

	public boolean isDead() {
		return health <= 0;
	}

	public boolean canBeFrozen() {
		return true;
	}

	public boolean isFrozen() {
		return freezeCount > 0;
	}

	public void freeze(int count) {
		freezeCount = count;
	}

	public List<Enemy> getEnemies() {
		return null;
	}

	public void hit() {
		health--;
	}

	protected Enemy moveEnemy(Enemy enemy) {

		if (direction == Direction.RIGHT || direction == Direction.DOWN) {

			while (enemy.x + enemy.velx < x || enemy.y + enemy.vely < y
					|| random.nextInt(2) == 0) {
				enemy.update();
			}

		}

		else {

			while (enemy.x + enemy.velx > x || enemy.y + enemy.vely > y) {
				enemy.update();
			}

		}

		return enemy;
	}

	protected void setStep(double step) {
		this.step = step;
	}

	protected void move() {
		x += velx;
		y += vely;
		bounds.setBounds((int) x, (int) y, (int) width, (int) height);
	}

	public void update() {

		if (!isFrozen()) {

			if (!hasEnemyReachedEnd()) {
				move();
				count--;
			}

		}

		else {
			freezeCount--;
		}

		if (hasEnemyReachedPoint()) {
			init();
		}

	}

	public List<Point> getPathFromIndex() {
		List<Point> newPath = new ArrayList<Point>();

		for (int cntr = index; cntr < path.size(); cntr++) {
			newPath.add(path.get(cntr));
		}

		return newPath;
	}

	public Type getType() {
		return (isFrozen()) ? Type.FROZEN : type;
	}

	public Type getOrgType() {
		return type;
	}

	public boolean hasEnemyReachedPoint() {
		return count <= 0;
	}

	public boolean hasEnemyReachedEnd() {
		return index >= path.size() - 1;
	}

	public void init() {
		index++;

		if (hasEnemyReachedEnd()) {
			return;
		}

		count = (int) Math.round(30 / step);
		Point point1 = path.get(index);
		Point point2 = path.get(index + 1);

		x = point1.getX() * 30;
		y = point1.getY() * 30;

		direction = getDirection(point1, point2);
		setVelocities();
	}

	public Enemy path(List<Point> path) {
		this.path = path;
		index = -1;
		init();
		bounds = new Rectangle((int) x, (int) y, (int) width, (int) height);
		return this;
	}

	public void setVelocities() {

		if (direction == Direction.LEFT) {
			velx = -step;
			vely = 0;
		}

		else if (direction == Direction.RIGHT) {
			velx = step;
			vely = 0;
		}

		else if (direction == Direction.UP) {
			velx = 0;
			vely = -step;
		}

		else {
			velx = 0;
			vely = step;
		}
	}

	protected Direction getDirection(Point p1, Point p2) {

		if (p1.getY() < p2.getY()) {
			return Direction.DOWN;
		}

		else if (p1.getY() > p2.getY()) {
			return Direction.UP;
		}

		else if (p1.getX() > p2.getX()) {
			return Direction.LEFT;
		}

		else {
			return Direction.RIGHT;
		}

	}

	protected Color getColor() {
		return Color.red;
	}

	public void render(Graphics g) {
		if (!isFrozen()) {
			g.setColor(getColor());
		}

		else {
			g.setColor(Color.cyan);
		}

		renderBody(g);
		g.drawImage(getImage(), (int) x + 5, (int) y + 5, null);
	}

	protected void renderBody(Graphics g) {
		g.fillRect((int) x + 5, (int) y + 5, 20, 20);
	}

	protected Image getImage() {

		if (isFrozen()) {
			return frozen;
		}

		if (direction == Direction.UP) {
			return images[0];
		}

		else if (direction == Direction.RIGHT) {
			return images[1];
		}

		else if (direction == Direction.DOWN) {
			return images[2];
		}

		return images[3];
	}

	public static Enemy valueOf(int num) {

		if (num == 1) {
			return new EnemyGreen();
		}

		else if (num == 2) {
			return new EnemyBlue();
		}

		else if (num == 2) {
			return new EnemyWhite();
		}

		else if (num == -1) {
			return new EnemyCamo();
		}

		else {
			return new EnemyRed();
		}

	}
	
	public static Enemy valueOf(String name) {

		if (name.equalsIgnoreCase("Green")) {
			return new EnemyGreen();
		}

		else if (name.equalsIgnoreCase("Blue")) {
			return new EnemyBlue();
		}

		else if (name.equalsIgnoreCase("White")) {
			return new EnemyWhite();
		}

		else if (name.equalsIgnoreCase("Camo")) {
			return new EnemyCamo();
		}

		else {
			return new EnemyRed();
		}

	}

	public enum Type {
		NORMAL, FROZEN, METAL, CAMO;
	}

	protected enum Direction {
		LEFT, RIGHT, UP, DOWN;
	}

}
