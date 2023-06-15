package src.GUIOLD;

import game.Game;
import lib.FontRenderer;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GUIPauseMenu extends GUIComponent{
	public GUIPauseMenu(int width, int height, String name) {
		super(Display.getWidth()/2-width/2, Display.getHeight()/2-height/2, width, height, name);
		this.visible = false;
		this.addChild(new GUIButton((int)(x+0.1*width), y+60,  (int)(width*0.8), 40, "Back to game", "but_resume"));
		this.addChild(new GUIButton((int)(x+0.1*width), y+100, (int)(width*0.8), 40, "Load", "but_load"));
		this.addChild(new GUIButton((int)(x+0.1*width), y+140, (int)(width*0.8), 40, "Save", "but_save"));
		this.addChild(new GUIButton((int)(x+0.1*width), y+180, (int)(width*0.8), 40, "Options", "but_options"));
		this.addChild(new GUIButton((int)(x+0.1*width), y+220, (int)(width*0.8), 40, "Exit", "but_exit"));
		this.addChild(GUI.exitMsg);
		this.addChild(GUI.options);
	}
	
	public void draw() {
		if(isVisible()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, 0);
			GL11.glColor4f(0.0F, 0.1F, 0.4f, 1F);
			GUI.drawCornercutFillRect(width, height, 10);
			GL11.glTranslatef(4, 4, 0);
			GL11.glColor4f(0.0F, 0.1F, 0.6f, 1F);
			GUI.drawCornercutFillRect(width-8, height-8, 10);
			GL11.glTranslatef(-4, -4, 0);
			
			FontRenderer.drawStringCentred(width/2, height*0.06F, "PAUSE");
			GL11.glColor4f(1, 1, 1, 1);
			GL11.glPopMatrix();
		}
		super.draw();
	}
	
	public void GUIEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUI.EVENT_BUTTON_PRESSED) {
			if(component.name == "but_exit") {
				GUI.exitMsg.visible= true;
				this.visible = false;
				this.active = false;
			}
			if(component.name == "but_resume") {
				visible = !visible;
				Game.pause = visible;
			}
			if(component.name == "but_options") {
				GUI.options.reNew();
				GUI.options.visible = true;
				this.visible = false;
				this.active = false;
			}
		}
		if(EVENT_TYPE == GUI.EVENT_MSG_YES) {
			if(component.name=="msg_exit") {
				Game.isRunning = false;
			}
		}
		if(EVENT_TYPE == GUI.EVENT_MSG_NO) {
			if(component.name=="msg_exit") {
				this.active = true;
				Game.pause = false;
			}
		}
		if(EVENT_TYPE == GUI.EVENT_GUI_BACK) {
			if(component.name=="gui_options") {
				this.active = true;
				this.visible = true;
			}
		}
	}
	
	
	
	public void onKeyboardEvent(int key, boolean state) {
		if(state==true && key == Keyboard.KEY_ESCAPE) {
			if(isActive()) {
				setVisible(!isVisible());
				Game.pause = isVisible();
			}
		}
	}
	
}
