import java.util.ArrayList;

import processing.core.PApplet;

public class Player {
	
	private XYZ pos, v = new XYZ(0,0,0);
	private RectBox body;
	private boolean jumping = false;
	
	public Player(XYZ pos) {
		this.pos = pos;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public void update() {
		v.x = 0;
		v.z = 0;
		v.y += Game.gravity;
		move(0, v.y, 0);
		ArrayList<Platform> platforms = Game.levels[0].getPlatforms();
		for (int i = 0; i < platforms.size(); i++) {
			if (platforms.get(i).getBox().intersects(body)) {
				v.y = 0;
				move(0, platforms.get(i).getBox().corner1.y - body.corner2.y, 0);
				jumping = false;
			}
		}
		if (Game.leftPressed) {
			v.x = -5;
		}
		if (Game.rightPressed) {
			v.x = 5;
		} 
		if (Game.upPressed) {
			v.z = -5;
		}
		if (Game.downPressed) {
			v.z = 5;
		} 
		if (Game.spacePressed && !jumping) {
			v.y = -20;
			jumping = true;
		} 
		move(v.x, 0, v.z);
	}
	
	public void draw(PApplet drawer) {
		update();
		body.draw(drawer);
	}
	
	public XYZ getPos() {
		return pos;
	}
	
	public XYZ getCenter() {
		return body.getCenter();
	}
	
	public void move(double x, double y, double z) {
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
