package snake;

import apcs.Window;

public class Food {
	
	int x;
	int y;
	int side = 10;
	
	public Food() {
		x = Window.rollDice(Window.width() - side / 2) + side / 2;
		y = Window.rollDice(Window.height() - side / 2) + side / 2;
	}
	
	public void draw() {
		Window.out.color("red");
		Window.out.square(x, y, side);
	}
	
	public void reset() {
		x = Window.rollDice(Window.width() - side / 2) + side / 2;
		y = Window.rollDice(Window.height() - side / 2) + side / 2;
	}
}
