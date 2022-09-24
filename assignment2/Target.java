
/*
 * File: Target.java
 * Name: 
 * Section Leader: 
 * -----------------
 * This file is the starter file for the Target problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class Target extends GraphicsProgram {
	/** Radius of the biggest Circle */
	private static final double bigRadius = 72.0;
	
	/** Radius of the middle sized circle converted to pixels from centimetres using the given conditions */
	private static final double midRadius = bigRadius * 1.65 / 2.54;
	
	/** Radius of the smallest circle converted to pixels from centimetres using the given conditions */
	private static final double smallRadius = bigRadius * 0.76 / 2.54;
	
	private GOval oval1;
	private GOval oval2;
	private GOval oval3;

	public void run() {
		addBigCircle();
		addMiddleCircle();
		addSmallCircle();
	}

	/* draws the biggest red circle */
	private void addBigCircle() {
		oval1 = new GOval(getWidth() / 2 - bigRadius, getHeight() / 2 - bigRadius, bigRadius * 2, bigRadius * 2);
		oval1.setColor(Color.RED);
		oval1.setFilled(true);
		add(oval1);
	}

	/* draws the middle Sized white circle over the first big red circle */
	private void addMiddleCircle() {
		oval2 = new GOval(getWidth() / 2 - midRadius, getHeight() / 2 - midRadius, midRadius * 2, midRadius * 2);
		oval2.setColor(Color.WHITE);
		oval2.setFilled(true);
		add(oval2);

	}

	/* draws the smallest red circle over the past two circles */
	private void addSmallCircle() {
		oval3 = new GOval(getWidth() / 2 - smallRadius, getHeight() / 2 - smallRadius, smallRadius * 2,
				smallRadius * 2);
		oval3.setColor(Color.RED);
		oval3.setFilled(true);
		add(oval3);
	}
}
