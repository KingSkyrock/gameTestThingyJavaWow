import processing.core.PApplet;

public class RectBox {
	
	XYZ corner1, corner2;
	
	public RectBox(XYZ corner1, XYZ corner2) {
		this.corner1 = corner1;
		this.corner2 = corner2;
	}
	
	public RectBox(XYZ corner1, int width, int height, int depth) {
		this.corner1 = corner1;
		this.corner2 = new XYZ(corner1.x + width, corner1.y + height, corner1.z + depth);
	}
	
	public void draw(PApplet drawer) {
		drawer.push();
		drawer.fill(128);
		drawer.translate(getCenter().x,getCenter().y,getCenter().z);
		drawer.box(Math.abs(corner1.x-corner2.x),
				Math.abs(corner1.y-corner2.y),
				Math.abs(corner1.z-corner2.z));
		drawer.pop();
	
	}
	
	public XYZ getCenter() {
		return new XYZ(corner1.x+Math.abs(corner1.x-corner2.x)/2,
				corner1.y+Math.abs(corner1.y-corner2.y)/2,
				corner1.z+Math.abs(corner1.z-corner2.z)/2);
	}
	
	public boolean intersects(RectBox other) {
		return corner1.x < other.corner2.x && other.corner1.x < corner2.x
			&& corner1.y < other.corner2.y && other.corner1.y < corner2.y
			&& corner1.z < other.corner2.z && other.corner1.z < corner2.z;
	}

}
