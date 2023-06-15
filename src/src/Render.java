package src;

import game.Game;
import lib.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Cursor;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.glu.GLU;
import src.GUI.GUIManager;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Render implements InputListener{
	
	public int fps, fpsCount;
	private long frame;
	private boolean wireframe = false;
	public String caption = "War";
	public Camera camera = new Camera(20, 20);
	
	
	public void create() {
		try {
			if(!Boolean.parseBoolean(Options.get("full_screen"))) {
				Display.setDisplayMode(new DisplayMode(Integer.parseInt(Options.get("window_width")), Integer.parseInt(Options.get("window_height"))));
			} else {
				Display.setFullscreen(true);
			}
			Display.setResizable(false);
			Display.setVSyncEnabled(Boolean.parseBoolean(Options.get("vsync")));
			Display.setTitle(caption);
			Display.setIcon(getIcon());
			Display.create();
		} catch (LWJGLException e) {
			Game.onError(e);
		}
	}
	
	
	public void init() {
		glClearColor(0f, 0f, 0f, 1.0f);
		
		glEnable(GL_DEPTH_TEST);
		glDepthFunc(GL_LEQUAL);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
		glEnable(GL_BLEND);
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
		glShadeModel(GL_SMOOTH);
		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);
		
		glMatrixMode(GL_PROJECTION);
		glLoadIdentity();
		GLU.gluPerspective(70,  (float)Display.getWidth()/(float)Display.getHeight(), 0.1F, 1000);
		glMatrixMode(GL_MODELVIEW);
		glLoadIdentity();
		
		removeNativeCursor();
		
		InputHandler.register(this);
		
		ShaderLoader.setProgram("main");
		
		ShaderLoader.setUniform("tex0", 1, 0, 0, 0, 0);
		ShaderLoader.setUniform("tex1", 1, 1, 0, 0, 0);
		ShaderLoader.setUniform("tex2", 1, 2, 0, 0, 0);
		ShaderLoader.setUniform("tex3", 1, 3, 0, 0, 0);
		ShaderLoader.setUniform("tex4", 1, 4, 0, 0, 0);
		ShaderLoader.setUniform("tex5", 1, 5, 0, 0, 0);
		ShaderLoader.setUniform("tex6", 1, 6, 0, 0, 0);
		ShaderLoader.setUniform("tex7", 1, 7, 0, 0, 0);
		
		ShaderLoader.setProgram("none");
		
	}
	
	public void run() {
		frame = System.nanoTime() / 1000000;
		
		while(Game.isRunning) {
			if(Display.isCloseRequested()) {
				Game.isRunning = false;
			}
			if(!Display.isActive()) {
				if(!Game.pause) {
					GUIManager.pause.open();
				}
			}
			render();
            //Display.sync(60);
		}
	}
	
	public void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		
		
		glPushMatrix();
		{
			camera.adjustPosition();
			ShaderLoader.setProgram("main");
			
			FloatBuffer matrix = Util.emptyFloatBuffer(16);
			glGetFloat(GL_MODELVIEW_MATRIX, matrix);
			ShaderLoader.setUniformMatrix("in_modelview", matrix);
			glGetFloat(GL_PROJECTION_MATRIX, matrix);
			ShaderLoader.setUniformMatrix("in_projection", matrix);

			//ShaderLoader.setUniform("tick", 1, (float)System.currentTimeMillis()/1000, 0, 0, 0);
            //GL20.glUniform1f(GL20.glGetUniformLocation(ShaderLoader.getCurrentProgram(), "tick"), (float)System.currentTimeMillis()/10);

            Terrain.terrain.render();
			

			ShaderLoader.setProgram("none");
			
			
			//TEST
			glTranslatef(50, 50, Terrain.terrain.get(50, 50));
			glScalef(1F, 1F, 1F);
			glRotatef(185, 0, 1, 0);
			glRotatef(8, 1, 0, 0);
			ITextureLoader.bindTexture("res/textures/objects/tank.png");
			//GL11.glScalef(0.1F, 0.1F, 0.1F);
			ModelLoader.get("tank.obj").draw();

			//Airport
			glTranslatef(-50, 50, Terrain.terrain.get(10, 10) - 8.25F);
			glScalef(3F, 3F, 3F);
			glRotatef(180, 0, 1, 0);
			glRotatef(8, 1, 0, 0);
			glRotatef(-4, 0, 1, 0);
			ITextureLoader.bindTexture("res/textures/objects/Airport.png");
			//GL11.glScalef(0.1F, 0.1F, 0.1F);
			ModelLoader.get("Airport.obj").draw();
			
		}
		glPopMatrix();
		
		
		InputHandler.handleInput();
		GUIManager.draw();
		updateFps();
		Display.update();
	
	}
	
	
	public void enableOrtho() {
		glDisable(GL_DEPTH_TEST);
		if(wireframe) glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
		glMatrixMode(GL_PROJECTION);
		glPushMatrix();
		glLoadIdentity();
		GLU.gluOrtho2D(0, Display.getWidth(), Display.getHeight(), 0);
		glMatrixMode(GL_MODELVIEW);
		glPushMatrix();
		glLoadIdentity();
	}
	
	public void disableOrtho() {
		if(wireframe) glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
		glEnable(GL_DEPTH_TEST);
		glMatrixMode(GL_PROJECTION);
		glPopMatrix();
		glMatrixMode(GL_MODELVIEW);
		glPopMatrix();
	}
	
	
	public void onMouseEvent(int button, boolean state) {}
	public void onKeyboardEvent(int key, boolean state) {
		if(key==Keyboard.KEY_F3) {
			if(state) {
				ScreenShoter.saveScreen();
			}
		}
		
		if(key==Keyboard.KEY_F2) {
			if(state) {
				wireframe = !wireframe;
				if(wireframe) {
					GUIManager.debug.setVarible("render", "wireframe");
					glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
				} else {
					GUIManager.debug.setVarible("render", "normal");
					glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
				}
				
			}
		}
		
		if(key==Keyboard.KEY_F4 && state) {
			ShaderLoader.reloadShaders();
			Info.writeln("Shaders have been reloaded!");
		}
	}

	
	public void removeNativeCursor() {
		try {
			Mouse.setNativeCursor(new Cursor(1, 1, 0, 0, 1, BufferUtils.createIntBuffer(1), null));
		} catch (LWJGLException e) {
			Game.onError(e);
		}
	}
 	
	public void updateFps() {
		fpsCount++;
		if(System.nanoTime() / 1000000 > frame + 1000) {
			fps = fpsCount;
			fpsCount = 0;
			frame += 1000;
		}
	}
	
	public void falseFrame() {
		GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT);
		GUIManager.draw();
		Display.update();
	}
	
	public void destroy() {
		Display.destroy();
		Game.isRunning = false;
	}
	
	private ByteBuffer[] getIcon() {
        return new ByteBuffer[] {
            ITextureLoader.getImageData("res/icon/icon32.png"),
            ITextureLoader.getImageData("res/icon/icon16.png")
        };
	}
	
	public static Render render = new Render();
	Info info = new Info();
}