import java.util.ArrayList;

import processing.core.PApplet;
import processing.core.PVector;

public class Player {
	
	private PVector pos, v = new PVector(0,0,0);
	private RectBox body;
	private boolean jumping = false;
	private int speed = 5;
	
	public Player(PVector pos) {
		this.pos = pos;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public boolean isColliding(PVector newPos) {
		ArrayList<Platform> platforms = Game.levels[0].getPlatforms();
		for (int i = 0; i < platforms.size(); i++) {
			if (new RectBox(newPos, 20, 20, 20).intersects(platforms.get(i).getBox())) {
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		v.x = 0;
		v.z = 0;
		v.y += Game.gravity;
		int directionsPressed = 0;
		if (Game.leftPressed) directionsPressed++;
		if (Game.rightPressed) directionsPressed++;
		if (Game.upPressed) directionsPressed++;
		if (Game.downPressed) directionsPressed++;
		
		if (directionsPressed <= 2) {
			if (Game.leftPressed) {
				v.z += (PApplet.cos(PApplet.radians(Game.yaw + 90)) * speed);
				v.x += (PApplet.sin(PApplet.radians(Game.yaw + 90)) * speed);
				if (Game.upPressed || Game.downPressed) {
					v.z *= Math.sqrt(2)/2;
					v.x *= Math.sqrt(2)/2;
				}
			}
			if (Game.rightPressed) {
				v.z += (PApplet.cos(PApplet.radians(Game.yaw - 90)) * speed);
				v.x += (PApplet.sin(PApplet.radians(Game.yaw - 90)) * speed);
				if (Game.upPressed || Game.downPressed) {
					v.z *= Math.sqrt(2)/2;
					v.x *= Math.sqrt(2)/2;
				}
			} 
			if (Game.upPressed) {
				v.z += (PApplet.cos(PApplet.radians(Game.yaw)) * speed);
				v.x += (PApplet.sin(PApplet.radians(Game.yaw)) * speed);
				if (Game.leftPressed || Game.rightPressed) {
					v.z *= Math.sqrt(2)/2;
					v.x *= Math.sqrt(2)/2;
				}
			}
			if (Game.downPressed) {
				v.z += (-PApplet.cos(PApplet.radians(Game.yaw)) * speed);
				v.x += (-PApplet.sin(PApplet.radians(Game.yaw)) * speed);
				if (Game.leftPressed || Game.rightPressed) {
					v.z *= Math.sqrt(2)/2;
					v.x *= Math.sqrt(2)/2;
				}
			} 
		}
		
		if (Game.spacePressed && !jumping) {
			v.y = -20;
			jumping = true;
		} 
		if(!Game.spacePressed && v.y < -10f) {
			v.y = -10f;
		}
		
		
		//This works for now. There is probably a much better way of doing this
		float tempX = pos.x, tempY = pos.y, tempZ = pos.z;
		int[] counts = new int[] {0,0,0,0,0,0};
		while (isColliding(pos)) {
			pos.x++;
			counts[0]++;
		}
		pos.x = tempX;
		while (isColliding(pos)) {
			pos.x--;
			counts[1]++;
		}
		pos.x = tempX;
		while (isColliding(pos)) {
			pos.y++;
			counts[2]++;
		}
		pos.y = tempY;
		while (isColliding(pos)) {
			pos.y--;
			counts[3]++;
		}
		pos.y = tempY;
		while (isColliding(pos)) {
			pos.z++;
			counts[4]++;
		}
		pos.z = tempZ;
		while (isColliding(pos)) {
			pos.z--;
			counts[5]++;
		}
		pos.z = tempZ;
		int min = Integer.MAX_VALUE;
		int minIndex = 0;
		for (int i = 0; i < 6; i++) {
			if (counts[i] < min) {
				min = counts[i];
				minIndex = i;
			}
		}
		
		switch(minIndex) {
			case 0:
				pos.x += counts[minIndex];
				break;
			case 1:
				pos.x -= counts[minIndex];
				break;
			case 2:
				pos.y += counts[minIndex];
				break;
			case 3:
				pos.y -= counts[minIndex];
				break;
			case 4:
				pos.z += counts[minIndex];
				break;
			case 5:
				pos.z -= counts[minIndex];
				break;
		}
		
		if (isColliding(new PVector(pos.x+v.x, pos.y, pos.z))) {
			float d = v.x;
			v.x = 0;
			pos.x = (int)(pos.x + v.x);
			while (isColliding(pos)) {
				if (d < 0) pos.x++;
				if (d > 0) pos.x--;
				if (d == 0) break;
			}
		} else {
			move(v.x, 0, 0);
		}
		if (isColliding(new PVector(pos.x, pos.y+v.y, pos.z))) {
			float d = v.y;
			if (d > 0) jumping = false;
			v.y = 0;
			pos.y = (int)(pos.y + v.y);
			while (isColliding(pos)) {
				if (d < 0) pos.y++;
				if (d > 0) pos.y--;
				if (d == 0) break;
			}
		} else {
			move(0, v.y, 0);
		}
		if (isColliding(new PVector(pos.x, pos.y, pos.z+v.z))) {
			float d = v.z;
			v.z = 0;
			pos.z = (int)(pos.z + v.z);
			while (isColliding(pos)) {
				if (d < 0) pos.z++;
				if (d > 0) pos.z--;
				if (d == 0) break;
			}
		} else {
			move(0, 0, v.z);
		}
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public void draw(PApplet drawer) {
		update();
		drawer.push();
		drawer.translate(pos.x+20, pos.y+20, pos.z);
		drawer.rotate(PApplet.PI);
		drawer.scale(320);
		drawer.shape(Game.playerModel);
		drawer.pop();
	}
	
	public PVector getPos() {
		return pos;
	}
	
	public PVector getCenter() {
		return body.getCenter();
	}
	
	public void move(float x, float y, float z) {
		pos.x += x;
		pos.y += y;
		pos.z += z;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public PVector getV() {
		return v;
	}
	
	public void setV(PVector newV) {
		v = newV;
	}
}
