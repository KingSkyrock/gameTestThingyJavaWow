import java.util.ArrayList;

import processing.core.PApplet;

public class Player {
	
	private XYZ pos, v = new XYZ(0,0,0);
	private RectBox body;
	private boolean jumping = false;
	private int speed = 5;
	
	public Player(XYZ pos) {
		this.pos = pos;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public boolean isColliding(XYZ newPos) {
		ArrayList<Platform> platforms = Game.levels[0].getPlatforms();
		for (int i = 0; i < platforms.size(); i++) {
			if (new RectBox(newPos, 20, 20, 20).intersects(platforms.get(i).getBox())) {
				return true;
			}
		}
		return false;
	}
	
	public void update() {
		//Things that the player can collide with
		ArrayList<Platform> platforms = Game.levels[0].getPlatforms();
		
		v.x = 0;
		v.z = 0;
		v.y += Game.gravity;
		
		if (Game.leftPressed) {
			v.x = -speed;
		}
		if (Game.rightPressed) {
			v.x = speed;
		} 
		if (Game.upPressed) {
			v.z = -speed;
		}
		if (Game.downPressed) {
			v.z = speed;
		} 
		if (Game.spacePressed && !jumping) {
			v.y = -20;
			jumping = true;
		} 
		
		//This is quite a strange way of doing collisions
		//hopefully wont cause problems in future (it probably will cause problems)
		for (int i = 0, d = (v.x < 0 ? -1 : 1); i != v.x + d; i += d) {
			if (!isColliding(new XYZ(pos.x+(v.x-i), pos.y, pos.z))) {
				v.x = (v.x-i);
				break;
			}
		}
		for (int i = 0, d = (v.y < 0 ? -1 : 1); i != v.y + d; i += d) {
			if (!isColliding(new XYZ(pos.x, pos.y+(v.y-i), pos.z))) {
				v.y = (v.y-i);
				break;
			} else if (d > 0){
				jumping = false;
			}
		}
		for (int i = 0, d = (v.z < 0 ? -1 : 1); i != v.z + d; i += d) {
			if (!isColliding(new XYZ(pos.x, pos.y, pos.z+(v.z-i)))) {
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
		//body.draw(drawer);
	}
	
	public XYZ getPos() {
		return pos;
	}
	
	public XYZ getCenter() {
		return body.getCenter();
	}
	
	public void move(int x, int y, int z) {
		pos.x += x;
		pos.y += y;
		pos.z += z;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public XYZ getV() {
		return v;
	}
	
	public void setV(XYZ newV) {
		v = newV;
	}
}
