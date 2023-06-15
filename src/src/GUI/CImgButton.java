package src.GUI;

import org.lwjgl.opengl.GL11;

public class CImgButton extends GUIComponent{
	private String image;
	private boolean isPressed = false;

	public CImgButton(GUI gui, int x, int y, int width, int height, String image, String workname) {
		super(gui, x, y, width, height, workname);
		this.image = image;
		visible = true;
	}
	
	public boolean isPressed() {
		return isPressed;
	}

	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);

		if(isActive()) {
			if(isPressed) {
				GL11.glScalef(0.8F, 0.8F, 1);
			}
			if(mouseOn) {
				GUIManager.drawTexturedRect(width, height, GUI.imgbutton_selected, image);
			} else {
				GUIManager.drawTexturedRect(width, height, GUI.imgbutton_idle, image);
			}
		} else {
			GUIManager.drawTexturedRect(width, height, GUI.imgbutton_disabled, image);
		}
		GL11.glPopMatrix();
		super.draw();
	}
	
	public void onInputEvent(int EVENT_TYPE, int key) {
		if(isActive() && key == 0) {
			if(mouseOn && EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_PRESSED) {
				if(parent != null) {
					parent.GuiEvent(GUIManager.EVENT_BUTTON_PRESSED, this);
				} else {
					gui.onGuiEvent(GUIManager.EVENT_BUTTON_PRESSED, this);
				}
				isPressed = true;
			} else if(mouseOn && EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_RELEASED) {
				if(parent != null) {
					parent.GuiEvent(GUIManager.EVENT_BUTTON_RELEASED, this);
				} else {
					gui.onGuiEvent(GUIManager.EVENT_BUTTON_RELEASED, this);
				}
				isPressed = false;
			}
			if(EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_RELEASED) {
				isPressed = false;
			}
		}
		super.onInputEvent(EVENT_TYPE, key);
	}
}
