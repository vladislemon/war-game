package src.GUIOLD;

import lib.FontRenderer;

import org.lwjgl.opengl.GL11;

public class GUIButton extends GUIComponent{
	private String text; 
	
	
	public GUIButton(int x, int y, int width, int height, String text, String name) {
		super(x, y, width, height, name);
		this.text = text;
	}
	
	
	
	public void draw() {
		if(isVisible()) {
			GL11.glPushMatrix();
				GL11.glTranslatef(x, y, 0);
				if(active) {
					GL11.glColor3f(0, 0, mouseOn ? 0.5F:0.7F);
				} else {
					GL11.glColor3f(0.5F, 0.5F, 0.5F);
				}
				GUI.drawFramedRect(width, height, 2);
				FontRenderer.drawStringCentred(width/2, height/2, text);
			GL11.glPopMatrix();
		}
		super.draw();
	}
	
	public void onMouseEvent(int button, boolean state) {
		if(mouseOn && state && button == 0) {
			if(parent!=null && active) {
				parent.GUIEvent(GUI.EVENT_BUTTON_PRESSED, this);
			}
		}
		super.onMouseEvent(button, state);
	}
	
	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}
}
