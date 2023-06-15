package src.GUI;

import lib.FontRenderer;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class CList extends GUIComponent {
	
	String[] strings;
	float pos, textSize;
	int var, boarder = 15, size = FontRenderer.getSize();
	CScroller scroller;
	boolean mouseOnList;

	public CList(GUI gui, int x, int y, int width, int height, String[] strings, int textSize, String workname) {
		super(gui, x, y, width, height, workname);
		this.strings = strings;
		this.textSize = textSize;
		scroller = new CScroller(gui, x + width, y, 15, height, 15, 15, 0f, 100f, 0f, "LIST_SCROLLER");
		addChild(scroller);
		visible = true;
	}
	
	public int getVariable() {
		return var;
	}
	
	public String getText() {
		return strings[var];
	}
	
	public void setVariable(int var) {
		this.var = var;
	}
	
	public void setText(String text) {
		for(int i = 0; i < strings.length; i++) {
			if(strings[i] == text) {
				var = i;
				return;
			}
		}
	}
	
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y + boarder, 0);
		GUIManager.drawFramedRect(width, height - boarder, 2, GUI.scroller_body);
		GL11.glPopMatrix();
		
		for(int i = 0; i < strings.length; i++) {
			if(pos < textSize*i + size && pos > textSize*i - height +  boarder*2) {
				if(i == var) {
					GL11.glPushMatrix();
					GL11.glTranslatef(x, y + boarder + i * textSize - pos, 0);
					GUIManager.drawRect(width, size, GUI.scroller_selected);
					GL11.glPopMatrix();
				}
				GL11.glPushMatrix();
				GL11.glTranslatef(x, y + boarder + i * textSize - pos, 0);
				FontRenderer.drawString(5, 0, strings[i]);
				GL11.glPopMatrix();
			}
		}
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GUIManager.drawFramedRect(width, boarder, 1, GUI.scroller_selected);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y + height - boarder, 0);
		GUIManager.drawFramedRect(width, boarder, 1, GUI.scroller_selected);
		GL11.glPopMatrix();
		
		super.draw();
	}
	
	public void onInputEvent(int EVENT_TYPE, int key) {
		if(isActive()) {
			if(EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_PRESSED && key == 0 && mouseOnList) {
				int mouseY = Display.getHeight() - Mouse.getY() - getY() - boarder;
				var = (int)(Math.floor(pos + mouseY) / textSize);
			}
		}
		super.onInputEvent(EVENT_TYPE, key);
	}
	
	public void update() {
		super.update();
		mouseOnList = mouseOnList();
		size = FontRenderer.getSize();
		pos = Math.max(scroller.getVariable() / 100f * (textSize * strings.length - height + boarder*2 - textSize + size), 0);
	}
	
	private boolean mouseOnList() {
		int mouseY = Display.getHeight() - Mouse.getY() - getY();
		if(mouseOn && mouseY > boarder && mouseY < height - boarder) {
			return true;
		}
		return false;
	}
}
