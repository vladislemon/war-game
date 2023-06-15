package src.GUIOLD;

import lib.Info;
import org.lwjgl.opengl.GL11;
import src.Render;

public class GUI {
	
	public static final int MSG_YesNo = 0;
	public static final int EVENT_BUTTON_PRESSED=11;
	public static final int EVENT_MSG_YES=12;
	public static final int EVENT_MSG_NO = 13;
	public static final int EVENT_GUI_BACK = 14;
	public static final int EVENT_CHECKBOX_CHECKED = 15;
	public static final int EVENT_CHECKBOX_UNCHECKED = 16;
	public static final int EVENT_SLIDER_MOVED = 17;
	

	public static void drawCornercutFillRect(int width, int height, int fallof) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(0,    fallof);
			GL11.glVertex2f(width,fallof);
			GL11.glVertex2f(width,height-fallof);
			GL11.glVertex2f(0,    height-fallof);
			
			GL11.glVertex2f(fallof,   	 0);
			GL11.glVertex2f(width-fallof,0);
			GL11.glVertex2f(width-fallof,fallof);
			GL11.glVertex2f(fallof,  	 fallof);
			
			GL11.glVertex2f(fallof,  	 height-fallof);
			GL11.glVertex2f(width-fallof,height-fallof);
			GL11.glVertex2f(width-fallof,height);
			GL11.glVertex2f(fallof, 	 height);
		GL11.glEnd();
		GL11.glBegin(GL11.GL_TRIANGLES);
			GL11.glVertex2f(fallof, 0);
			GL11.glVertex2f(fallof, fallof);
			GL11.glVertex2f(0 ,     fallof);
			
			GL11.glVertex2f(fallof, height);
			GL11.glVertex2f(fallof, height-fallof);
			GL11.glVertex2f(0,      height-fallof);
			
			GL11.glVertex2f(width-fallof, height);
			GL11.glVertex2f(width-fallof, height-fallof);
			GL11.glVertex2f(width,        height-fallof);
			
			GL11.glVertex2f(width-fallof, 0);
			GL11.glVertex2f(width-fallof, fallof);
			GL11.glVertex2f(width ,       fallof);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	public static void drawFramedRect(int width, int height, int boarder) {
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBegin(GL11.GL_QUADS);
			GL11.glVertex2f(width, 0);
			GL11.glVertex2f(0,     0);
			GL11.glVertex2f(0,     height);
			GL11.glVertex2f(width, height);
		GL11.glEnd();
		GL11.glColor3f(1, 1, 1);
		GL11.glBegin(GL11.GL_LINE_LOOP);
			GL11.glVertex2f(width, 0);
			GL11.glVertex2f(0,     0);
			GL11.glVertex2f(0,     height);
			GL11.glVertex2f(width, height);
		GL11.glEnd();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}
	
	
	
	
	public static void draw() {
		Render.render.enableOrtho();
			debug.draw();
			pause.draw();
		Render.render.disableOrtho();
	}
	
	public static void onKeyboardEvent(int key, boolean state) {
		debug.onKeyboardEvent(key, state);
		exitMsg.onKeyboardEvent(key, state);
		options.onKeyboardEvent(key, state);
		pause.onKeyboardEvent(key, state);
	}
	
	public static void onMouseEvent(int key, boolean state) {
		debug.onMouseEvent(key, state);
		exitMsg.onMouseEvent(key, state);
		options.onMouseEvent(key, state);
		pause.onMouseEvent(key, state);
	}
	
	
	public static GUIDebugPanel debug = new GUIDebugPanel(10, 10, 250, 400, "gui_debug");
	public static GUITextMessage exitMsg = new GUITextMessage(GUI.MSG_YesNo, "Are you sure want to exit?", "msg_exit");
	public static GUIOptions options = new GUIOptions("gui_options");
	public static GUIPauseMenu pause = new GUIPauseMenu(400, 300, "gui_pause");
	
	protected static Info info = new Info();
}
