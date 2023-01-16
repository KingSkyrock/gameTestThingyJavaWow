


import java.awt.Point;
import java.awt.event.KeyEvent;

import processing.core.PApplet;



public class DrawingSurface extends PApplet {

	private boolean upPressed, downPressed, leftPressed, rightPressed;
	private float camX = width/2, camY = height/2 - 200, camZ = 300;
	private float camCenterX = width/2, camCenterY = height/2, camCenterZ;
	
	private Player p = new Player(new Position(500, 350, -300));
	
	public DrawingSurface() {
		 
	}
	
	public void settings() {
		fullScreen(P3D);
	}

	public void setup() {
		
	}

	public void draw() { 
		background(255);
		
		if (leftPressed) {
			p.move(-5, 0, 0);
		}
		if (rightPressed) {
			p.move(5, 0, 0);
		}
		if (upPressed) {
			p.move(0, 0, -5);
		}
		if (downPressed) {
			p.move(0, 0, 5);
		}
		
		if (p != null) {
			camX = (float) p.getPos().x;
			camCenterX = (float) p.getPos().x;
		}
		
		camera(camX, camY, camZ, camCenterX, camCenterY, camCenterZ, 0, 1, 0);
		new RectBox(new Position(300, 300, -300), new Position(500, 500, -500)).draw(this);
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
		}
	}

	
}










