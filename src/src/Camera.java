package src;


import lib.Util;

import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;

public class Camera implements InputListener{
	private float x, y, z;
	private float pitch, yaw, roll;
	private final int max_z = 50, min_z = -10;
	private final float speed = 0.12f;
	private int motionx, motiony;
	private boolean grab = false;
	private int oX, oY;
	
	public Camera(int sx, int sy) {
		z = 25;
		x = sx;
		y = sy;
		pitch = 25;

	}
	
	public void update() {
		x+=motionx*speed*Util.cos(roll)+motiony*speed*Util.cos(roll+90);
		y+=motionx*speed*Util.sin(roll)+motiony*speed*Util.sin(roll+90);
		
		int dwheel = -Mouse.getDWheel()/30;
		if(z+dwheel*speed<=max_z && z+dwheel*speed>=min_z) {
			z+=dwheel*speed*Util.cos(pitch);
			x+=dwheel*speed*Util.sin(pitch)*Util.cos(roll-90);
			y+=dwheel*speed*Util.sin(pitch)*Util.sin(roll-90);
		}
		
		if(Mouse.isButtonDown(1)) {
			if(!grab) {
				grab = true;
				oX = Mouse.getX();
				oY = Mouse.getY();
			} else {
				x += (Mouse.getX() - oX) * 0.003f * Util.cos(roll);
				x += (Mouse.getY() - oY) * 0.003f * -Util.sin(roll);
				y += (Mouse.getY() - oY) * 0.003f * Util.cos(roll);
				y += (Mouse.getX() - oX) * 0.003f * Util.sin(roll);
			}
			
		} else {
			grab = false;
		}
		
		int boarder = 0;
		if(x <boarder) {
			x = boarder;
		}
		if(y<boarder) {
			y = boarder;
		}
		if(x>Terrain.terrain.getWidth() - boarder) {
			x = Terrain.terrain.getWidth() - boarder;
		}
		
		if(y>Terrain.terrain.getHeight() - boarder) {
			y = Terrain.terrain.getHeight() - boarder;
		}
	}
	
	public float getX() {
		return x;
	}
	
	public float getY() {
		return y;
	}
	
	public float getZ() {
		return y;
	}
	
	public void adjustPosition() {
		GL11.glRotatef(-pitch, 1, 0, 0);
		GL11.glRotatef(-yaw, 0, 1, 0);
		GL11.glRotatef(-roll, 0, 0, 1);
		GL11.glTranslatef(-x, -y, -z);
		
		if(InputHandler.isMouseMiddleDown()) {
			roll -= Mouse.getDX();
			pitch += Mouse.getDY();
			return;
		}
		
		
		if(Mouse.getX()>Display.getWidth()-32) {
			motionx = 1;
		} else if(Mouse.getX()<32) {
			motionx = -1;
		} else {
			motionx = 0;
		}
		
		if(Mouse.getY()>Display.getHeight()-32) {
			motiony = 1;
		} else if(Mouse.getY() <32) {
			motiony = -1;
		} else {
			motiony = 0;
		}
		
		
		
	}

	public void onMouseEvent(int button, boolean state) {
		
	}

	public void onKeyboardEvent(int key, boolean state) {
		
	}
}
	
