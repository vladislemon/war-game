package src.GUI;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class CScroller extends GUIComponent {
	
	private int scrollerSize, buttonSize;
	private boolean grab, mouseOnScroller;
	private float min, max, var, pos = buttonSize;
	private CImgButton up, down;

	public CScroller(GUI gui, int x, int y, int width, int height, int scrollerSize,
			int buttonSize, float min, float max, float defaultVar, String workname) {
		super(gui, x, y, width, height, workname);
		this.scrollerSize = scrollerSize;
		this.buttonSize = buttonSize;
		this.min = min;
		this.max = max;
		setVariable(defaultVar);
		visible = true;
		up = new CImgButton(gui, x, y, width, buttonSize, "res/textures/arrow_up.png", "BUT_UP");
		down = new CImgButton(gui, x, y + height - buttonSize, width, buttonSize, "res/textures/arrow_down.png", "BUT_DOWN");
		addChild(up);
		addChild(down);
	}
	
	public void setScrollerSize(int size) {
		scrollerSize = size;
	}
	
	public int getScrollerSize() {
		return scrollerSize;
	}
	
	public void setVariable(float var) {
		if(var >= min && var <= max) {
			this.var = var;
			pos = (var - min) * (float)(height-buttonSize*2-scrollerSize) / (max - min) + buttonSize;
		}
	}
	
	public float getVariable() {
		return var;
	}
	
	public void onInputEvent(int EVENT_TYPE, int key) {
		if(isActive()) {
			if(mouseOnScroller && EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_PRESSED && key == 0) {
				grab = true;
			}
			else if(EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_RELEASED && key == 0) {
				grab = false;
				if(parent != null) {
					parent.GuiEvent(GUIManager.EVENT_SCROLLER_MOVED, this);
				} else {
					gui.onGuiEvent(GUIManager.EVENT_SCROLLER_MOVED, this);
				}
			}
			if(EVENT_TYPE == GUIManager.EVENT_MOUSE_BUTTON_PRESSED && key == 0 && mouseOn && !mouseOnScroller) {
				int newPos = Display.getHeight() - Mouse.getY() - getY();
				if(newPos > height - buttonSize - scrollerSize && newPos < height - buttonSize) {
					pos = height - buttonSize - scrollerSize;
				} else if(newPos > buttonSize && newPos < height - buttonSize) {
					pos = newPos;
				}
				updateVariable();
			}
		}
		super.onInputEvent(EVENT_TYPE, key);
	}
	
	public void draw() {
		GL11.glPushMatrix();
		GL11.glTranslatef(x, y, 0);
		GUIManager.drawRect(width, height, GUI.scroller_body);
		GL11.glPopMatrix();
		
		GL11.glPushMatrix();
		GL11.glTranslatef(x+1, y + pos, 0);
		if(isActive()) {
			if(mouseOnScroller || grab) {
				GUIManager.drawFramedRect(width-1, scrollerSize, 1, GUI.scroller_selected);
			} else {
				GUIManager.drawFramedRect(width-1, scrollerSize, 1, GUI.scroller_idle);
			}
		} else {
			GUIManager.drawFramedRect(width-1, scrollerSize, 1, GUI.scroller_disabled);
		}
		GL11.glPopMatrix();
		super.draw();
	}
	
	private void updateVariable() {
		var = min + (max - min) * ((pos - buttonSize) / (float)(height-buttonSize*2-scrollerSize));
	}
	
	public void update() {
		super.update();
		mouseOnScroller = mouseOnScroller();
		if(grab) {
			float dy = (Display.getHeight() - Mouse.getY()) - (getY() + buttonSize);
			if(dy >= height - (float)scrollerSize*1.5f - buttonSize)
				pos = height - scrollerSize - buttonSize;
			else if(dy <= buttonSize - scrollerSize/2)
				pos = buttonSize;
			else {
				pos = dy + scrollerSize/2;
			}
		}
		
		if(up.isPressed() && pos > buttonSize) {
			pos--;
		}
		if(down.isPressed() && pos < height - buttonSize - scrollerSize) {
			pos++;
		}
		if((parent != null && parent.mouseOn) || mouseOn) {
			float newPos = pos - Mouse.getDWheel()/20;
			if(newPos > height - buttonSize - scrollerSize) {
				pos = height - buttonSize - scrollerSize;
			} else if(newPos < buttonSize) {
				pos = buttonSize;
			} else {
				pos = newPos;
			}
		}
		updateVariable();
	}
	
	private boolean mouseOnScroller() {
		int mouseY = Display.getHeight() - Mouse.getY();
		int mouseX = Mouse.getX();
		if(mouseY <= getY() + pos + scrollerSize && mouseY >= getY() + pos &&
				mouseX <= getX() + width && mouseX >= getX())
			return true;
		return false;
	}
}
