package src;

import lib.Info;

import game.Game;

public class Timer extends Thread {
	
	int ticks = 200;
	private long lastTick;
	
	void updateGame() {
		Render.render.camera.update();
	}
	
	public void run() {
		Thread.currentThread().setName("Timer");
		lastTick= System.nanoTime()/1000000;
		while(true) {
			
			while(Game.isRunning && Game.pause) {
				try {
					Thread.sleep(100);
					lastTick = System.nanoTime()/1000000;
				} catch (InterruptedException e) {
					Game.onError(e);
				}
			}
			
			
			if(System.nanoTime()/1000000 - lastTick >= 1000/ticks) {
				lastTick = lastTick+1000/ticks;
				updateGame();
			} else {
				try {
					Thread.sleep(Math.max(lastTick + 1000/ticks - System.nanoTime()/1000000, 0));
				} catch (InterruptedException e) {
					Game.onError(e);
				}
			}
			
			if(!Game.isRunning) {
				Info.writeln("Stopping game...");
				break;
			}
			
		}
	}
	
	static Info info = new Info();
	public static Timer timer = new Timer();
}
