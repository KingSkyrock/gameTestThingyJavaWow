import processing.core.PApplet;

public class Platform {
	
	private RectBox box;
	
	public Platform(RectBox box) {
		this.box = box;
	}
	
	public RectBox getBox() {
		return box;
	}
	
	public void draw(PApplet drawer) {
		box.draw(drawer);
	}
	
}
