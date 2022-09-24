
/*
 * File: FindRange.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the FindRange problem.
 */

import acm.program.*;

public class FindRange extends ConsoleProgram {

	/* The number which breaks the while loop */
	private static final int SENTINEL = 0;

	public void run() {
		welcomeToFindRange();
		findMinMax();
	}

	/* prints the welcome text in the console */
	private void welcomeToFindRange() {
		println("This Program find the largest and smallest numbers.");
	}

	/* prints the smallest and the largest number from the user input numbers */
	private void findMinMax() {
		int minimum = Integer.MAX_VALUE;
		int maximum = Integer.MIN_VALUE;
		while (true) {
			int input = readInt("? ");
			if (input == SENTINEL)
				break;
			if (input < minimum)
				minimum = input;
			if (input > maximum)
				maximum = input;

		}
		println("smallest: " + minimum);
		println("largest: " + maximum);
	}

}
