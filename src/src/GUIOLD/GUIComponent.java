package src.GUIOLD;

import java.util.ArrayList;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import src.InputHandler;
import src.InputListener;

public class GUIComponent implements InputListener{
	protected int x, y, width, height;
	public String name;
	protected ArrayList<GUIComponent> children = new ArrayList<GUIComponent>();
    protected GUIComponent parent;
	protected boolean visible = true;
	protected boolean mouseOn = false;
	protected boolean active = true;
	public GUIComponent() {
		InputHandler.register(this);
	}
	
	public void GUIEvent(int EVENT_TYPE, GUIComponent component) {
		
	}
	
	public GUIComponent(int x, int y, int width, int height, String workname) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.name = workname;
		
	}
	
	
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}
	
	public void addChild(GUIComponent child) {
		children.add(child);
		child.setParent(this);
	}
	
	public void removeChild(GUIComponent child) {
		children.remove(child);
		child.setParent(this);
	}

	public void onMouseEvent(int button, boolean state) {
		for(int i=0; i<children.size(); i++) {
			children.get(i).onMouseEvent(button, state);
		}
	}

	public void onKeyboardEvent(int key, boolean state) {
		for(int i=0; i<children.size(); i++) {
			children.get(i).onKeyboardEvent(key, state);
		}
	}
	
	
	public void draw() {
		//GL11.glDisable(GL11.GL_TEXTURE_2D);
		
		int mouseY = Display.getHeight() - Mouse.getY();
		if(isVisible() && Mouse.getX()>x && Mouse.getX()<x+width && mouseY<y+height && mouseY>y) {
			mouseOn = true;
		} else {
			mouseOn = false;
		}
		
		for(int i=0; i<children.size(); i++) {
			children.get(i).draw();
		}
	}

	public boolean isVisible() {
		if(parent != null) {

			return visible && parent.isVisible();
		} else {
			return visible;
		}
	}
	

	public void setVisible(boolean visible) {
		this.visible = visible;
	}

	public GUIComponent getParent() {
		return parent;
	}

	public void setParent(GUIComponent parent) {
		this.parent = parent;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}
	
	

}
