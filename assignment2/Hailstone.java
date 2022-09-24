
/*
 * File: Hailstone.java
 * Name: 
 * Section Leader: 
 * --------------------
 * This file is the starter file for the Hailstone problem.
 */

import acm.program.*;

public class Hailstone extends ConsoleProgram {
	public void run() {
		int stepCounter = 0;
		int steps = reachingOne(stepCounter);
		printResult(steps);
	}

	/*
	 * the whole while loop process, that it takes to reach 1 by dividing numbers
	 * either by 2 or multiplying it by 3 and adding 1. The method returns the
	 * amount of steps it took.
	 */
	private int reachingOne(int stepCounter) {
		int a = readInt("Enter a number: ");
		while (a != 1) {
			if (a % 2 == 0) {
				println(a + " is even so I take half: " + a / 2);
				a = a / 2;
				stepCounter++;
			} else {
				println(a + " is odd, so I make it 3n+1: " + (3 * a + 1));
				a = 3 * a + 1;
				stepCounter++;
			}
		}
		return stepCounter;
	}

	/* returns the number of steps that took the program to reach 1 */
	private void printResult(int stepCounter) {
		println("The process took " + stepCounter + " to reach 1");
	}
}
