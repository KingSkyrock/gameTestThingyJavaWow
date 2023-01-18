import java.awt.Robot;
import java.awt.AWTException;
import java.awt.event.KeyEvent;

import processing.core.PApplet;
import processing.core.PShape;
import processing.core.PVector;

public class Game extends PApplet {

	public static boolean upPressed, downPressed, leftPressed, rightPressed, spacePressed;
	private PVector camPos = new PVector(0, 0, 0);
	private PVector camCenter = new PVector(0, 0, 0);
	public static float yaw, pitch;
	
	public static double gravity = 1;
	
	private Robot robot;
	
	public static PShape playerModel;
	
	private Player p = new Player(new PVector(100, 0, -150));
	public static Level[] levels = {new Level()};
	
	public Game() {
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}
		levels[0].getPlatforms().add(new Platform(new RectBox(new PVector(400, 300, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new PVector(0, 300, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new PVector(-200, 100, -300), 200, 200, 200)));
		levels[0].getPlatforms().add(new Platform(new RectBox(new PVector(0, 100, -500), 200, 200, 200)));
		levels[0].getPlatforms().add(
				new MovingPlatform(
						new RectBox(
								new PVector(600, 300, -300), 200, 200, 200
						), 
						new PVector(0, -2, 1), 400
				)
		);
	}
	
	public void settings() {
		fullScreen(P3D);
	}

	public void setup() {
		playerModel = loadShape("playerModel.obj");
		noCursor();
	}

	public void draw() { 
		push();
		background(255);
		lights();
		
		camPos.x = p.getCenter().x;
		camPos.z = p.getCenter().z;
		camPos.y = p.getCenter().y - 40;
		
		if (focused) {
			yaw -= mouseX-width/2;
			pitch -= mouseY-height/2;
			robot.mouseMove(width/2,height/2);
			if (yaw >= 360)
				yaw -= 360;
			if (yaw <= -360)
				yaw += 360;
			if (pitch >= 179)
				pitch = 179;
			if (pitch <= 1)
				pitch = 1;
			
			camCenter.x = sin(radians(pitch)) * sin(radians(yaw)) + camPos.x;
			camCenter.z = sin(radians(pitch)) * cos(radians(yaw)) + camPos.z;
			camCenter.y = cos(radians(pitch)) + camPos.y;
		}
		
		float fov = PI/2f;
		float nearClippingDistance = 0.01f;
		perspective(fov, (float)(width)/(float)(height), nearClippingDistance, camPos.z*10);
		
		camera(camPos.x, camPos.y, camPos.z, camCenter.x, camCenter.y, camCenter.z, 0, 1, 0);
		levels[0].draw(this);
		p.draw(this);
		
		pop();
		
		//HUD
		hint(DISABLE_DEPTH_TEST);
		fill(0);
		circle(width/2, height/2, 10);
		hint(ENABLE_DEPTH_TEST);
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










