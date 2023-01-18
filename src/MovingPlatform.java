import processing.core.PApplet;
import processing.core.PVector;

public class MovingPlatform extends Platform {
	
	private PVector movementVector;
	private int movementFrames, count;
	
	public MovingPlatform(RectBox box, PVector movementVector, int movementFrames) {
		super(box);
		this.movementVector = movementVector;
		this.count = this.movementFrames = movementFrames;
	}
	
	public void update() {
		count--;
		super.getBox().move(movementVector);
		if (count == 0) {
			count = movementFrames;
			movementVector.mult(-1);
		}
	}
	
	public void draw(PApplet drawer) {
		update();
		super.draw(drawer);
	}
	
}
