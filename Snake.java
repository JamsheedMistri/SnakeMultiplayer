package snake;

import apcs.Window;

public class Snake {

	static final int RIGHT = 0;
	static final int LEFT = 180;
	static final int DOWN = 270;
	static final int UP = 90;

	
	int r = Window.rollDice(255), g = Window.rollDice(255), b = Window.rollDice(255);
	
	
	int x;
	int y;
	int side = 10;
	int direction = RIGHT;
	Snake next;

	public Snake() {
		x = Window.width() / 2;
		y = Window.height() / 2;
	}

	public Snake(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public boolean checkItself(Snake head) {
		
		if (next == null) {
			return false;
		}
		
		if (Math.abs(next.x - head.x) < side && Math.abs(next.y - head.y) < side) {
			return true;
		}
		
		
		return next.checkItself(head);
	}
	
	public void grow() {
		if (next == null) {
			switch(direction) {
			case UP:
				next = new Snake(x, y + side);
				break;
			case DOWN:
				next = new Snake(x, y - side);
				break;
			case RIGHT:
				next = new Snake(x - side, y);
				break;
			case LEFT:
				next = new Snake(x + side, y);
				break;
			}
			next.direction = direction;
		}
		else {
			next.grow();
		}
	}
	
	public boolean checkFood(Food f) {
		if (Math.abs(x - f.x) <= side && Math.abs(y - f.y) <= side) {
			return true;
		}
		
		return false;
	}
	
	public boolean checkBoundaries() {
		if (x < side || y < side || x > Window.width() - side || y > Window.height() - side) {
			return true;
		}
		
		return false;
	}

	public void draw() {
		Window.out.color(r, g, b);
		Window.out.square(x, y, side);
		if (next != null) {
			next.draw();
		}
	}

	public void move() {
		switch (direction) {
		case DOWN:
			y += side;
			break;
		case UP:
			y -= side;
			break;
		case RIGHT:
			x += side;
			break;
		case LEFT:
			x -= side;
			break;
		}
		if (next != null) {
			next.move();
			next.direction = direction;
		}
	}

	public void changeDirection() {
		if (next == null) {
			if (Window.key.pressed("left")) {
				direction = LEFT;
			}
			else if (Window.key.pressed("right")) {
				direction = RIGHT;
			}
			else if (Window.key.pressed("up")) {
				direction = UP;
			}
			else if (Window.key.pressed("down")) {
				direction = DOWN;
			}
		}
		else {
			if (Window.key.pressed("left") && direction != RIGHT) {
				direction = LEFT;
			}
			else if (Window.key.pressed("right") && direction != LEFT) {
				direction = RIGHT;
			}
			else if (Window.key.pressed("up") && direction != DOWN) {
				direction = UP;
			}
			else if (Window.key.pressed("down") && direction != UP) {
				direction = DOWN;
			}
		}
	}


}
