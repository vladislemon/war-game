package src.GUI;

import game.Game;

public class GUIPauseMenu extends GUI{
	public boolean locked = false;
	
	
	public GUIPauseMenu() {
		super();
		width = 400;
		height = 300;
		x = cx-width/2;
		y = cy-height/2;
		
		
		addComponent(new CButton(this, 50, 50, 300, 40, "Resume", "BUT_RESUME"));
		CButton load = new CButton(this, 50, 90, 300, 40, "Load", "BUT_LOAD");
		CButton save = new CButton(this, 50, 130, 300, 40, "Save", "BUT_SAVE");
		load.setActive(false);
		save.setActive(false);
		addComponent(load);
		addComponent(save);
		addComponent(new CButton(this, 50, 170, 300, 40, "Options", "BUT_OPTIONS"));
		addComponent(new CButton(this, 50, 210, 300, 40, "Exit", "BUT_EXIT"));
		
		
		
	}
	
	
	public void open() {
		super.open();
		GUIManager.setInFocus(this);
		Game.pause = true;
	}
	
	public void close() {
		super.close();
		GUIManager.removeFocus(this);
		Game.pause = false;
	}
	
	public void draw() {
		GUIManager.drawFrame(x, y, width, height, GUI.frame_boarder, GUI.frame_body);
		super.draw();
	}
	
	public void onGuiEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUIManager.EVENT_BUTTON_RELEASED) {
			if(component.getName() == "BUT_RESUME") {
				close();
			}
			if(component.getName() == "BUT_EXIT") {
				Game.isRunning = false;
			}
			if(component.getName() == "BUT_OPTIONS") {
				close();
				GUIManager.options.open();
			}
		}
	}
}
