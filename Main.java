package snake;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import apcs.Window;

public class Main {
	
	// create food object
	static Food f;
	// create array of snake objects
	static Snake[] snakes = new Snake[8];
	// create an array of longs that store the x position of snakes
	static long[] snakex = new long[8];
	// create an array of longs that store the y position of snakes
	static long[] snakey = new long[8];
	static Events[] eventsx = new Events[8];
	static Events[] eventsy = new Events[8];
	static Events foodx = new Events();
	static Events foody = new Events();
	static Events eventScore = new Events();
	static Events eventPlayer = new Events();
	static int highscore;
	static int highplayer;
	
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
		server.child("highscore").addValueEventListener(eventScore);
		server.child("highplayer").addValueEventListener(eventPlayer);
		

		for (int i = 0; i < snakes.length; i++) {
			server.child("snakex"+i).setValue(snakes[i].x);
			server.child("snakey"+i).setValue(snakes[i].y);
			server.child("snakex"+i).addValueEventListener(eventsx[i] = new Events());
			server.child("snakey"+i).addValueEventListener(eventsy[i] = new Events());
		}
		
		
		while (true) {
			Window.out.background("black");
			Window.out.color("white");
			Window.out.font("monospaced", 25);
			Window.out.print("High Score: " + highscore, 50, 50);
			Window.out.print("High Player: " + highplayer, 50, 70);
			if (!Events.update) {
				
				f.x = (int) foodx.data;
				f.y = (int) foody.data;
				for (int i = 0; i < 8; i++) {
					snakes[i].x = (int) eventsx[i].data;
					snakes[i].y = (int) eventsy[i].data;
				}
				highscore = (int) eventScore.data;
				highplayer = (int) eventPlayer.data;
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
					snakes[i].length++;
					f.reset();
					server.child("foodx").setValue(f.x);
					server.child("foody").setValue(f.y);
					if (highscore < snakes[i].length) {
						highscore = snakes[i].length;
						server.child("highscore").setValue(highscore);
						server.child("highplayer").setValue(i);
					}
					
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
