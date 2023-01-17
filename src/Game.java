


import java.awt.Point;
import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PShape;



public class Game extends PApplet {

	public static boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	private float camX = width/2, camY = height/2 - 300, camZ = 500;
	private float camCenterX = width/2, camCenterY = height/2, camCenterZ;
	
	public static double gravity = 1;
	
	public static PShape playerModel;
	
	private Player p = new Player(new XYZ(100, 0, -150));
	public static Level[] levels = {new Level()};
	
	public Game() {
		levels[0].getPlatforms().add(new Platform(new RectBox(new XYZ(400, 300, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new XYZ(0, 300, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new XYZ(-200, 100, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new XYZ(0, 100, -500), 200, 200, 200)));
	}
	
	public void settings() {
		fullScreen(P3D);
	}

	public void setup() {
		playerModel = loadShape("playerModel.obj");
	}

	public void draw() { 
		background(255);
		lights();
		
		if (p != null) {
			camX = p.getPos().x;
			camCenterX = p.getPos().x;
		}
		
		camera(camX, camY, camZ, camCenterX, camCenterY, camCenterZ, 0, 1, 0);
		levels[0].draw(this);
		p.draw(this);
	}
	
	
	public void mousePressed() {
		
	}
	
	public void keyPressed() {
		if (keyCode == KeyEvent.VK_A) {
			leftPressed = true;
		} else if (keyCode == KeyEvent.VK_D) {
			rightPressed = true;
		} else if (keyCode == KeyEvent.VK_W) {
			upPressed = true;
		} else if (keyCode == KeyEvent.VK_S) {
			downPressed = true;
		} else if (keyCode == KeyEvent.VK_SPACE) {
			spacePressed = true;
		}
	}
	public void keyReleased() {
		if (keyCode == KeyEvent.VK_A) {
			leftPressed = false;
		} else if (keyCode == KeyEvent.VK_D) {
			rightPressed = false;
		} else if (keyCode == KeyEvent.VK_W) {
			upPressed = false;
		} else if (keyCode == KeyEvent.VK_S) {
			downPressed = false;
		} else if (keyCode == KeyEvent.VK_SPACE) {
			spacePressed = false;
		}
	}

	
}










