package src.GUIOLD;

import org.newdawn.slick.Color;
import lib.FontRenderer;

public class GUIChoosePanel extends GUIComponent{
	private String[] options;
	private int pointer = 0;
	public GUIChoosePanel(int x, int y, int width, int height, String name, String[] options) {
		super(x, y, width, height, name);
		this.options = options;
		this.addChild(new GUIButton(x, y, height, height, "<", "but_previous"));
		this.addChild(new GUIButton(x+width-height, y, height, height, ">", "but_next"));
	}
	
	public void draw() {
		if(isVisible()) {
			FontRenderer.drawStringCentred(x+width/2, y+height/2, options[pointer], active ? Color.white:Color.gray);
		}
		super.draw();
	}
	
	public void GUIEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUI.EVENT_BUTTON_PRESSED) {
			if(component.name == "but_next") {
				if(pointer<options.length-1) {
					pointer++;
				}
			}
			if(component.name == "but_previous") {
				if(pointer>0) {
					pointer--;
				}
			}
		}
	}
	
	public String getCurrentSelected() {
		return options[pointer];
	}
	
	public void setSelected(int num) {
		pointer = num;
	}
	
	public void setActive(boolean active) {
		super.setActive(active);
		children.get(0).setActive(active);
		children.get(1).setActive(active);
	}
	
	public String[] getOptions() {
		return options;
	}
}
