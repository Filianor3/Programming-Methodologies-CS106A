
/*
 * File: CheckerboardKarel.java
 * ----------------------------
 * When you finish writing it, the CheckerboardKarel class should draw
 * a checkerboard using beepers, as described in Assignment 1.  You
 * should make sure that your program works for all of the sample
 * worlds supplied in the starter folder.
 */

import stanford.karel.*;

public class CheckerboardKarel extends SuperKarel {

	public void run() {
		while (leftIsClear()) {
			oneLine();
			getBack();
			nextLine();
			secondLine();
			getBack();
			nextLine();
		}
		checkOddorEven();
	}

	/* edge case when the height of the board is odd */
	private void checkOddorEven() {
		if (rightIsClear()) {
			turnRight();
			move();
			if (noBeepersPresent()) {
				turnLeft();
				nextLine();
				oneLine();
			} else {
				turnAround();
				move();
				turnRight();
			}
		} else if (frontIsBlocked()) {
			putBeeper();
		} else {
			oneLine();
		}
	}

	/* fills the row which starts with the beeper */
	private void oneLine() {
		if (noBeepersPresent())
			putBeeper();
		while (frontIsClear()) {
			move();
			if (frontIsClear()) {
				move();
				putBeeper();
			}
		}
	}

	/* fills the row which starts with the emtpy cell, then beeper */
	private void secondLine() {
		if (frontIsClear()) {
			move();
			oneLine();
		}
	}

	/* return to the starting point of the row */
	private void getBack() {
		turnAround();
		while (frontIsClear()) {
			move();
		}
		turnAround();
	}

	/* gets to the next line */
	private void nextLine() {
		if (leftIsClear()) {
			turnLeft();
			move();
			turnRight();
		}
	}

}
