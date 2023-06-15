package src.GUIOLD;

import java.util.ArrayList;

import lib.FontRenderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import src.InputHandler;
import src.LevelLoader;
import src.Render;



public class GUIDebugPanel extends GUIComponent{
	private ArrayList<String> varibles = new ArrayList<String>();
	private ArrayList<String> values = new ArrayList<String>();
	public GUIDebugPanel(int x, int y, int width, int height, String name) {
		super(x, y, width, height, name);
		
		addVarible("FPS");
		addVarible("pos_X");
		addVarible("pos_Y");
		addVarible("map_size");
		addVarible("buildings");
		addVarible("units");
		addVarible("render", "normal");
	}
	
	public void draw() {
		if(visible) {
			setVarible("FPS", Render.render.fps);
			setVarible("pos_X", Render.render.camera.getX());
			setVarible("pos_Y", Render.render.camera.getY());
			setVarible("units", 0);
			setVarible("map_size", LevelLoader.getSize());
			
			GL11.glPushMatrix();
				GL11.glTranslatef(x, y, 0);
				GL11.glColor4f(0.3F, 0.3F, 0.3F, 0.4F);
				GUI.drawCornercutFillRect(width, height, 10);
				FontRenderer.drawStringCentred(width/2, 30, "- DEBUG -");
				String temp = "";
				for(int i=0; i<varibles.size(); i++) {
					temp += varibles.get(i)+" = "+ values.get(i) + "\n";
				}
				FontRenderer.drawString(20, 60, temp);
			GL11.glPopMatrix();
			
			//GL11.glColor4f(1, 1, 1, 1);
		}
		super.draw();
	}
	
	public void addVarible(String caption) {
		varibles.add(caption);
		values.add("");
	}
	
	public void addVarible(String caption, Object value) {
		varibles.add(caption);
		values.add(value.toString());
	}
	
	public void setVarible(String caption, Object value) {
		if(varibles.contains(caption)) {
			values.set(varibles.indexOf(caption), value.toString());
		} else {
			addVarible(caption, value);
		}
	}
	
	public void removeVarible(String caption) {
		varibles.remove(caption);
		values.remove(varibles.indexOf(caption));
	}
	
	public String getValue(String caption) {
		if(varibles.contains(caption)) {
			return values.get(varibles.indexOf(caption));
		}
		return "";
	}
	
	
	public void onKeyboardEvent(int key, boolean state) {
		if(state==true && (key == Keyboard.KEY_D || key == Keyboard.KEY_LSHIFT || key == Keyboard.KEY_LMENU)) {
			if(InputHandler.isKeyDown(Keyboard.KEY_D) && InputHandler.isKeyDown(Keyboard.KEY_LSHIFT) && InputHandler.isKeyDown(Keyboard.KEY_LMENU)) {
				visible = !visible;
			}
		}
		super.onKeyboardEvent(key, state);
	}

}
