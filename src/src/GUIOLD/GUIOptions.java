package src.GUIOLD;

import lib.FontRenderer;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

import src.Options;
public class GUIOptions extends GUIComponent {
	private GUIChoosePanel resolution;
	private GUICheckBox fullscr;
	private GUICheckBox vsync;
	
	public GUIOptions(String name) {
		super((int)(Display.getWidth()*0.1F),(int)(Display.getHeight()*0.1F), (int)(Display.getWidth()*0.8F), (int)(Display.getHeight()*0.8F), name);
		this.visible = false;
		
		resolution = new GUIChoosePanel(x+200, y+80, 150, 20, "choose_resolution", new String[] {"1000x800", "800x600", "1280x960", "720x460"});
		fullscr = new GUICheckBox(x+200, y+110, 20, 20, "check_fullscreen");
		vsync = new GUICheckBox(x+200, y+140, 20, 20, "check_vsync");
		
		this.addChild(new GUIButton((int)(x+width-120), (int)(y+height-80), 80, 40, "Back", "but_back"));
		this.addChild(new GUIButton((int)(x+width-210), (int)(y+height-80), 80, 40, "Save", "but_save"));
		this.addChild(new GUILabel(x+20, y+75, "Screen resolution", "label_res"));
		this.addChild(new GUILabel(x+20, y+105, "Full screen", "label_res"));
		this.addChild(new GUILabel(x+20, y+135, "VSync", "label_vsync"));
		
		
		//not working
		this.addChild(new GUILabel(x+width-370, y+75, "Volume", "label_volume"));
		
		this.addChild(new GUILabel(x+width-370, y+105, "Effects", "label_effects"));
		
		this.addChild(new GUILabel(x+width-370, y+135, "Music", "label_music"));
		
		
		//----------
		this.addChild(resolution);
		this.addChild(fullscr);
		this.addChild(vsync);
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
			
			FontRenderer.drawStringCentred(width/2, height*0.06F, "OPTIONS");
			GL11.glColor4f(1, 1, 1, 1);
			GL11.glPopMatrix();
		}
		super.draw();
	}
	
	public void GUIEvent(int EVENT_TYPE, GUIComponent component) {
		if(EVENT_TYPE == GUI.EVENT_BUTTON_PRESSED) {
			if(component.name == "but_back") {
				this.visible = false;
				parent.GUIEvent(GUI.EVENT_GUI_BACK, this);
			}
			
			if(component.name == "but_save") {
				Options.set("full_screen", Boolean.toString(fullscr.isChecked()));
				if(Boolean.parseBoolean(Options.get("full_screen")) != fullscr.isChecked()) {
				}
				if(Boolean.parseBoolean(Options.get("vsync")) != vsync.isChecked()) {
					Options.set("vsync", Boolean.toString(vsync.isChecked()));
				}
				if(Integer.parseInt(Options.get("window_width")) != parseRes(resolution.getCurrentSelected())[0] || Integer.parseInt(Options.get("window_height")) != parseRes(resolution.getCurrentSelected())[1]) {
					Options.set("window_width", Integer.toString(parseRes(resolution.getCurrentSelected())[0]));
					Options.set("window_height", Integer.toString(parseRes(resolution.getCurrentSelected())[1]));
				}
				Options.save();
				this.visible = false;
				parent.GUIEvent(GUI.EVENT_GUI_BACK, this);
			}
		}
		
		if(EVENT_TYPE == GUI.EVENT_CHECKBOX_CHECKED) {
			if(component.name == "check_fullscreen") {
				resolution.setActive(false);
			}
		}
		
		if(EVENT_TYPE == GUI.EVENT_CHECKBOX_UNCHECKED) {
			if(component.name == "check_fullscreen") {
				resolution.setActive(true);
			}
		}
	}
	
	public void reNew() {
		fullscr.setChecked(Boolean.parseBoolean(Options.get("full_screen")));
		vsync.setChecked(Boolean.parseBoolean(Options.get("vsync")));
		resolution.setActive(!Boolean.parseBoolean(Options.get("full_screen")));
	}
	
	private int[] parseRes(String res) {
		int width = 0;
		int height = 0;
		
		width = Integer.parseInt(res.substring(0, res.indexOf("x")));
		height = Integer.parseInt(res.substring(res.indexOf("x")+1));
		return new int[]{width, height};
	}
	
	public boolean isVisible() {
			return visible;
	}
}
