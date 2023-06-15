package src.GUI;

import org.lwjgl.input.Keyboard;

import game.Game;

public class GUIOptions extends GUI{
	public GUIOptions() {
		super();
		width = 800;
		height = 700;
		x = cx - width/2;
		y = cy - height/2;
		
		
		addComponent(new CButton(this, width-120, height-80, 80, 40, "Back", "BUT_BACK"));
		addComponent(new CButton(this, width-220, height-80, 80, 40, "Save", "BUT_SAVE"));
		
		//addComponent(new CScroller(this, width-500, height-400, 30, 160, 10, 10, 0, 100, 77, "SCROLL_TEST"));
		String[] s = new String[] {"miker9", "slimon", "travka", "DNIWE", "POVERHNIWE", "OTVERSTIE", "SLIV"
				, "TARTALET", "SAMALET", "4I4IPICA", "/DEV/NULL", "MINNIU_TRALWIK"};
		addComponent(new CList(this, width-500, height-400, 200, 150, s, 15, "SCROLL_TEST"));
	}
	
	public void onInputEvent(int EVENT_TYPE, int key) {
		super.onInputEvent(EVENT_TYPE, key);
		if(EVENT_TYPE == GUIManager.EVENT_KEYBOARD_KEY_PRESSED && key == Keyboard.KEY_ESCAPE) {
			close();
		}
	}
	
	public void onGuiEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUIManager.EVENT_BUTTON_RELEASED) {
			if(component.name == "BUT_BACK") {
				close();
			}
			if(component.name == "BUT_SAVE") {
				//save
				close();
			}
		}
	}
	
	public void draw() {
		GUIManager.drawFrame(x, y, width, height, GUI.frame_boarder, GUI.frame_body);
		super.draw();
	}
	public void open() {
		super.open();
		GUIManager.pause.locked = true;
		GUIManager.setInFocus(this);
		Game.pause = true;
	}
	
	public void close() {
		super.close();
		GUIManager.pause.locked = false;
		GUIManager.removeFocus(this);
		Game.pause = false;
	}
}
