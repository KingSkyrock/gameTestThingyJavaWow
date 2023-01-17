import java.util.ArrayList;

import processing.core.PApplet;

public class Level {
	
	private ArrayList<Platform> platforms = new ArrayList<Platform>();
	
	public Level() {
		
	}
	
	public ArrayList<Platform> getPlatforms() {
		return platforms;
	}
	
	public void draw(PApplet drawer) {
		for (int i = 0; i < platforms.size(); i++) {
			platforms.get(i).draw(drawer);
		}
	}

}
