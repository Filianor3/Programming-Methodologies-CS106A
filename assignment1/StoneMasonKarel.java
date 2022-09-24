
/*
 * File: StoneMasonKarel.java
 * --------------------------
 * The StoneMasonKarel subclass as it appears here does nothing.
 * When you finish writing it, it should solve the "repair the quad"
 * problem from Assignment 1.  In addition to editing the program,
 * you should be sure to edit this comment so that it no longer
 * indicates that the program does nothing.
 */

import stanford.karel.*;

public class StoneMasonKarel extends SuperKarel {

	public void run() {
		while (frontIsClear()) {
			fixPost();
			nextColumn();
		}
		fixPost(); // off by one
	}

	/* goes to the next column which needs to be fixed */
	private void nextColumn() {
		for (int i = 0; i < 4; i++) {
			move();
		}
	}

	/* combination of building column and getting back to the 1st point of column */
	private void fixPost() {
		buildColumn();
		getBack();
	}

	/* builds the column, adds missing beepers to the column */
	private void buildColumn() {
		turnLeft();
		while (frontIsClear()) {
			if (noBeepersPresent()) {
				putBeeper();
			}
			move();
		}
		if (noBeepersPresent())
			putBeeper(); // off by one
	}

	/* gets back to the starting position of the current column */
	private void getBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnLeft();
	}
}
