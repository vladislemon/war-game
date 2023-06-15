package game;

import lib.Info;
import src.GameLoader;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Game {
	
	public static boolean isRunning = true;
	public static boolean pause = false;

	
	public static void start() {
        System.setProperty("org.lwjgl.librarypath", "D:\\dev\\java\\standalone\\War_project\\War\\lib\\lwjgl\\native");
		Info.writeln("Starting game...");
		GameLoader.instance.start();
	}
	
	public static void onError(Exception e) {
		isRunning = false;
		Info.error("A critical error has been detected");
		try {
			PrintWriter out = new PrintWriter("crash.txt");
			out.println(Info.getDateAndTime());
			out.println();
			out.println("Critical error at " + e.getClass());
			out.println("[STACK TRACE]");
			StackTraceElement[] trace = e.getStackTrace();
			for(int i=0; i<trace.length; i++) {
				out.println("    " + trace[i].toString());
			}
			out.println("[CAUSED BY]");
			out.println("    " + e.getCause());	
			out.println("    " + e.getMessage());
			out.println();
			out.println("It will be very kind and helpfull if you send this to 00miker9@gmail.com");
			out.flush();
			out.close();
		} catch (FileNotFoundException e1) {}
		
		
	}
	
	
	public static void main(String args[]) {
		start();
	}
	
}


