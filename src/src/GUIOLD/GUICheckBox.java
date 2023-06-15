package src.GUIOLD;

import org.lwjgl.opengl.GL11;

public class GUICheckBox extends GUIComponent{
	private boolean checked = false;
	
	public GUICheckBox(int x, int y, int width, int height, String name) {
		super(x, y, width, height, name);
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	
	public void draw() {
		if(isVisible()) {
			GL11.glPushMatrix();
			GL11.glTranslatef(x, y, 0);
			if(mouseOn) GL11.glColor3f(0, 0.1F, 0.5F); else GL11.glColor3f(0, 0.1F, 0.7F);
			GUI.drawFramedRect(width, height, 1);
			if(checked) {
				GL11.glPushMatrix();
				GL11.glTranslatef(4, 4, 0);
				GL11.glColor3f(1F, 1F, 1F);
				GUI.drawFramedRect(width-8, height-8, 1);
				GL11.glPopMatrix();
			}
			
			GL11.glPopMatrix();
		}
		super.draw();
	}
	
	public void onMouseEvent(int button, boolean state) {
		if(mouseOn && state && button == 0) {
			checked = !checked;
			
			if(parent!=null) {
				if(checked) {
					parent.GUIEvent(GUI.EVENT_CHECKBOX_CHECKED, this);
				} else {
					parent.GUIEvent(GUI.EVENT_CHECKBOX_UNCHECKED, this);
				}
			}
		}
		super.onMouseEvent(button, state);
	}
	
	
}
