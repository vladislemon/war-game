package src.GUI;

import lib.FontRenderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import src.Render;

public class GUILoadingScreen extends GUI{
	private String text;
	private int loaded;
	
	public GUILoadingScreen() {
		super();
		text = "";
		loaded = 0;
	}
	
	public void draw() {
		GUIManager.drawFrameCentred(400, 150, frame_boarder, frame_body);
		FontRenderer.drawStringCentred(Display.getWidth()/2, Display.getHeight()/2 - 50, text);
		GL11.glPushMatrix();
		GL11.glTranslatef(Display.getWidth()/2-180, Display.getHeight()/2+15, 0);
		GUIManager.drawFramedRect(360, 40, 1, loadingbar_back);
		GL11.glTranslatef(5, 5, 0);
		GUIManager.drawRect(loaded*350/100, 30, loadingbar_bar);
		GL11.glPopMatrix();
	}
	
	public void setText(String s) {
		text = s;
		Render.render.falseFrame();
	}
	
	public String getText() {
		return text;
	}
	
	public void setLoadingLevel(int a) {
		if(a>100) {
			a = 100;
		}
		loaded = a;
		Render.render.falseFrame();
	}
	
	public int getLoadingLevel() {
		return loaded;
	}
	
	public void open() {
		super.open();
		GUIManager.pause.locked = true;
		Display.setVSyncEnabled(false);
	}
	
	public void close() {
		super.close();
		GUIManager.pause.locked = false;
		//Display.setVSyncEnabled(/*Boolean.parseBoolean(Options.get("vsync"))*/ true);
	}
	
	public boolean doesTakeInput() {
		return false;
	}
}
