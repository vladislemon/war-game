package src.GUIOLD;

import lib.FontRenderer;

import org.newdawn.slick.Color;

public class GUILabel extends GUIComponent{
	protected String text;
	protected Color color;
	
	public GUILabel(int x, int y, String text, String name) {
		super(x, y, 0, 0, name);
		this.text = text;
	}
	
	public void draw() {
		if(isVisible()) {
			FontRenderer.drawString(x, y, text);
		}
		super.draw();
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public Color getColor() {
		return color;
	}


	public void setColor(Color color) {
		this.color = color;
	}
	
}
