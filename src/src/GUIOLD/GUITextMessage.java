package src.GUIOLD;

import lib.FontRenderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class GUITextMessage extends GUIComponent{
	private String text = "";
	public GUITextMessage(int type, String msg, String name) {
		super(Display.getWidth()/2-200, Display.getHeight()/2-75, 400, 150, name);
		if(type == GUI.MSG_YesNo) {
			this.addChild(new GUIButton((int)(x+0.1*width), (int)(y+height*0.6F), (int)(width*0.2), 40, "Yes", "but_yes"));
			this.addChild(new GUIButton((int)(x+0.7*width), (int)(y+height*0.6F), (int)(width*0.2), 40, "No", "but_no"));
			this.visible = false;
		}
		text = msg;
	}
	
	
	public void draw() {
		if(isVisible()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, 0);
			GL11.glColor4f(0F, 0.1F, 0.4f, 1F);
			GUI.drawCornercutFillRect(width, height, 10);
			GL11.glTranslatef(4, 4, 0);
			GL11.glColor4f(0F, 0.1F, 0.6f, 1F);
			GUI.drawCornercutFillRect(width-8, height-8, 10);
			GL11.glTranslatef(-4, -4, 0);
			
			FontRenderer.drawStringCentred(width/2, height*0.2F, text);
			GL11.glColor4f(1, 1, 1, 1);
			GL11.glPopMatrix();
		}
		super.draw();
	}
	public boolean isVisible() {
		return visible;
	}
	
	public void GUIEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUI.EVENT_BUTTON_PRESSED) {
			if(component.name == "but_yes") {
				if(parent!=null) {
					this.visible = false;
					parent.GUIEvent(GUI.EVENT_MSG_YES, this);
					
				}
			}
			if(component.name == "but_no") {
				if(parent!=null) {
					this.visible = false;
					parent.GUIEvent(GUI.EVENT_MSG_NO, this);
				}
			}
		}
	}
	
	
}
