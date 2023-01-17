
import java.awt.Dimension;

import javax.swing.JFrame;

import processing.awt.PSurfaceAWT;
import processing.core.PApplet;

public class Main {

	public static void main(String args[]) {
		Game drawing = new Game();
		PApplet.runSketch(new String[]{"Game"}, drawing);
		drawing.windowResizable(true);
	}

}
