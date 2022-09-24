
/*
 * File: CollectNewspaperKarel.java
 * --------------------------------
 * At present, the CollectNewspaperKarel subclass does nothing.
 * Your job in the assignment is to add the necessary code to
 * instruct Karel to walk to the door of its house, pick up the
 * newspaper (represented by a beeper, of course), and then return
 * to its initial position in the upper left corner of the house.
 */

import stanford.karel.*;

public class CollectNewspaperKarel extends SuperKarel {
	public void run() {
		goToNewsPaper();
		pickNewspaper();
		turnAround();
		backToStart();
		turnRight();
	}

	/* this method goes from 3x4 to 6x3 */
	private void goToNewsPaper() {
		move();
		move();
		turnRight();
		move();
		turnLeft();
		move();

	}

	/* picks the newspaper */
	private void pickNewspaper() {
		pickBeeper();
	}

	/* this method gets back to the starting position */
	private void backToStart() {
		for (int i = 0; i < 3; i++) {
			move();
		}
		turnRight();
		move();
	}

}
