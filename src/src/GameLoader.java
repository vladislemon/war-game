package src;

import lib.FontRenderer;
import lib.ITextureLoader;
import lib.Info;
import lib.ModelLoader;
import lib.ShaderLoader;
import src.GUI.GUIManager;

public class GameLoader extends Thread{
	
	public void run() {
		loadGame();
		Render.render.run();
	}
	
	
	
	public void loadGame() {
		Info.info("Loading...");
		
		
		Options.load();
		Render.render.create();
		Render.render.init();
		FontRenderer.load();
		ITextureLoader.init();
		for(int i = 0; i < ITextureLoader.getSRCtextureCount(); i+=5) {
			ITextureLoader.loadTextures(5);
		}
		Info.info("Loaded " + ITextureLoader.getTextureCount() + "/" + ITextureLoader.getSRCtextureCount());
		
		GUIManager.init();
		GUIManager.loadingScreen.open();
		GUIManager.loadingScreen.setText("Loading shaders");
		
		ShaderLoader.loadShaders();
		ShaderLoader.setMainProgram("light");
		
		GUIManager.loadingScreen.setText("Loading models");
		
		for(int i = 0; i < ModelLoader.getSRCModelNum(); i++) {
			ModelLoader.load(1);
			GUIManager.loadingScreen.setLoadingLevel(ModelLoader.getModelNum()*100/ModelLoader.getSRCModelNum());
		}
		
		GUIManager.loadingScreen.setText("Initialising terrain");
		
		
		LevelLoader.load("maps/" + Options.get("map"));
		Timer.timer.start();
		
		GUIManager.loadingScreen.close();
	}
	
	public static GameLoader instance = new GameLoader();
}
