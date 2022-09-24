
/*
 * File: Pyramid.java
 * Name: 
 * Section Leader: 
 * ------------------
 * This file is the starter file for the Pyramid problem.
 * It includes definitions of the constants that match the
 * sample run in the assignment, but you should make sure
 * that changing these values causes the generated display
 * to change accordingly.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Pyramid extends GraphicsProgram {

	/** Width of each brick in pixels */
	private static final int BRICK_WIDTH = 30;

	/** Width of each brick in pixels */
	private static final int BRICK_HEIGHT = 12;

	/** Number of bricks in the base of the pyramid */
	private static final int BRICKS_IN_BASE = 14;

	private GRect rect;

	public void run() {
		double startingX = getWidth() / 2 - BRICK_WIDTH * BRICKS_IN_BASE / 2.0;
		double startingY = getHeight() - BRICK_HEIGHT;
		drawPyramid(startingX, startingY);
	}

	/* draws the pyramid on the canvas */
	private void drawPyramid(double x, double y) {
		for (int i = BRICKS_IN_BASE; i > 0; i--) {
			for (int j = i; j > 0; j--) {
				rect = new GRect(x, y, BRICK_WIDTH, BRICK_HEIGHT);
				add(rect);
				x += BRICK_WIDTH;
			}
			y -= BRICK_HEIGHT;
			x -= (i - 1) * BRICK_WIDTH + BRICK_WIDTH / 2;
		}
	}
}
