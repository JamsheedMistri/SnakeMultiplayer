package snake;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import apcs.Window;

public class Main {
	
	static Food f;
	static Snake[] snakes = new Snake[8];
	static long[] snakex = new long[8];
	static long[] snakey = new long[8];
	static Events[] eventsx = new Events[8];
	static Events[] eventsy = new Events[8];
	static Events foodx = new Events();
	static Events foody = new Events();
	
	public static void main (String[] args) {
		Window.size(800, 600);
		f = new Food();
		
		
		for (int i = 0; i < snakes.length; i++) {
			snakes[i] = new Snake(i * 50 + 50, 100);
		}
		
		Firebase server = new Firebase("https://multiplayersnake.firebaseio.com");
		server.child("foodx").setValue(f.x);
		server.child("foody").setValue(f.y);
		server.child("foodx").addValueEventListener(foodx);
		server.child("foody").addValueEventListener(foody);
		

		for (int i = 0; i < snakes.length; i++) {
			server.child("snakex"+i).setValue(snakes[i].x);
			server.child("snakey"+i).setValue(snakes[i].y);
			server.child("snakex"+i).addValueEventListener(eventsx[i] = new Events());
			server.child("snakey"+i).addValueEventListener(eventsy[i] = new Events());
		}
		
		
		while (true) {
			Window.out.background("black");
			for (int i = 0; i < Window.width() + 2; i += 11) {
				for (int j = 0; j < Window.width() + 1; j += 11) {
					Window.out.color(30,30,30);
					Window.out.square(i - 2, j - 3, 10);
				}

			}
			if (!Events.update) {
				
				f.x = (int) foodx.data;
				f.y = (int) foody.data;
				for (int i = 0; i < 8; i++) {
					snakes[i].x = (int) eventsx[i].data;
					snakes[i].y = (int) eventsy[i].data;
				}
			}
			
			f.draw();
			
			
			for (int i = 0; i < snakes.length; i++) {
				snakes[i].draw();
				snakes[i].changeDirection();
				snakes[i].move();
				
				if (snakes[i].checkBoundaries()) {
					snakes[i] = new Snake();
				}
				if (snakes[i].checkFood(f)) {
					snakes[i].grow();
					f.reset();
					server.child("foodx").setValue(f.x);
					server.child("foody").setValue(f.y);
				}
				if (snakes[i].checkItself(snakes[i])) {
					snakes[i] = new Snake();
				}
				
				
			}
			
			server.child("snakex6").setValue(snakes[6].x);
			server.child("snakey6").setValue(snakes[6].y);
			
			Window.frame();
		}
	}
}
