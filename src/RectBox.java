import processing.core.PApplet;

public class RectBox {
	
	Position corner1, corner2;
	
	public RectBox(Position corner1, Position corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
	}
	
	public RectBox(Position corner1, double width, double height, double depth) {
		this.corner1 = corner1;
		this.corner2 = new Position(corner1.x + width, corner1.y + height, corner1.z + depth);
	}
	
	public void draw(PApplet drawer) {
		drawer.push();
		drawer.fill(128);
		drawer.translate((float)corner1.x,(float)corner1.y,(float)corner1.z);
		drawer.box((float)Math.abs(corner1.x-corner2.x),
				(float)Math.abs(corner1.y-corner2.y),
				(float)Math.abs(corner1.z-corner2.z));
		drawer.pop();
	}
	
	public Position getCenter() {
		return new Position((corner1.x-corner2.x)/2, (corner1.y-corner2.y)/2, (corner1.z-corner2.z)/2);
	}

}
