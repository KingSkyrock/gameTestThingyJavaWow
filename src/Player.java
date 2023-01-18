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
		
		//this collision is so bad
		//i will eventually fix it someday
		for (int i = 0, d = (v.x < 0 ? -1 : 1); i != v.x + d; i += d) {
			if (!isColliding(new PVector(pos.x+(v.x-i), pos.y, pos.z))) {
				v.x = (v.x-i);
				break;
			}
		}
		for (int i = 0, d = (v.y < 0 ? -1 : 1); i != v.y + d; i += d) {
			if (!isColliding(new PVector(pos.x, pos.y+(v.y-i), pos.z))) {
				v.y = (v.y-i);
				break;
			} else if (d > 0){
				jumping = false;
			}
		}
		for (int i = 0, d = (v.z < 0 ? -1 : 1); i != v.z + d; i += d) {
			if (!isColliding(new PVector(pos.x, pos.y, pos.z+(v.z-i)))) {
				v.z = (v.z-i);
				break;
			}
		}

		move(v.x, v.y, v.z);
		
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
