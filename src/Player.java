import processing.core.PApplet;

public class Player {
	
	private Position pos;
	private RectBox body;
	
	public Player(Position pos) {
		this.pos = pos;
		body = new RectBox(pos, 20, 20, 20);
	}
	
	public void draw(PApplet drawer) {
		body.draw(drawer);
	}
	
	public Position getPos() {
		return pos;
	}
	
	public Position getCenter() {
		return body.getCenter();
	}
	
	public void move(double x, double y, double z) {
		pos.x += x;
		pos.y += y;
		pos.z += z;
		body = new RectBox(pos, 20, 20, 20);
	}

}
