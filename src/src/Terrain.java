package src;


import lib.ITextureLoader;
import lib.Info;

import org.lwjgl.opengl.GL11;

import src.GUI.GUIManager;


public class Terrain {
	private float[][] heightMap;
	private Chunk[][] chunks; 
	
	public void init(float[][] height) {
		terrain.heightMap = height;
		buildChunks();
	}
	
	public void render() {
		GL11.glColor3f(1,1,1);
		ITextureLoader.bindTexture(0, "res/textures/terrain/grass_nice.png");
		ITextureLoader.bindTexture(1, "res/textures/terrain/rock_normal.png");
		int camCX = (int) (Render.render.camera.getX()/64);
		int camCY = (int) (Render.render.camera.getY()/64);

		int size = 10; //2
		for(int x=0; x<chunks.length; x++) {
			for(int y=0; y<chunks[0].length; y++) {
				if(Math.abs(x-camCX)<size && Math.abs(x-camCX)>-size) {
					if(Math.abs(y-camCY)<size && Math.abs(y-camCY)>-size) {
						chunks[x][y].render();
					}
				}
				
			}
		}
		GL11.glColor3f(1,1,1);
		

		

	}
	
	public void destroy() {
		for(int i=0; i<chunks.length; i++) {
			for(int k=0; k<chunks[0].length; k++) {
				chunks[i][k].destroy();
			}
		}
	}
	
	private void buildChunks() {
		chunks = new Chunk[(int) Math.ceil(heightMap.length/64)][(int) Math.ceil(heightMap[0].length/64)];
		
		for(int x=0; x<chunks.length; x++) {
			for(int y=0; y<chunks[0].length; y++) {
				chunks[x][y] = new Chunk(x, y);
				chunks[x][y].build();
				GUIManager.loadingScreen.setLoadingLevel((int)(100 * (float)(y + x*chunks[0].length) / (float)(chunks.length*chunks[0].length - 1)));
			}
		}
	}
	
	public int getTexture(int id) {
		return id == 0 ? 1:2;
	}
	
	

	
	public float get(int x, int y) {
		if(x>=0&&y>=0&&x<heightMap.length&&y<heightMap[0].length) {
			return heightMap[x][y];
		} else {
			return 0;
		}
	}
	
	public float getInterpolated(float x, float y) {
		if(x<0&&y<0&&x>=heightMap.length&&y>=heightMap[0].length) {
			return 0;
		}
		
		float kx = x - (int)x;
		float ky = y - (int)y;
		float h1 = get((int)x,   (int)y);
		float h2 = get((int)x,   (int)y+1);
		float h3 = get((int)x+1, (int)y+1);
		float h4 = get((int)x+1, (int)y);
		
		float i1 = h2*kx + h3*(1F-kx);
		float i2 = h1*kx + h4*(1F-kx);
		return i1*ky + i2*(1F-ky);
	}
	
	
	public int getWidth() {
		return heightMap.length;
	}
	
	public int getHeight() {
		return heightMap[0].length;
	}
	
	static Terrain terrain = new Terrain();
	Info info = new Info();
}
