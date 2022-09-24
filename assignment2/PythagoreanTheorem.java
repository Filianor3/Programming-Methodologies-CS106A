
/*
 * File: PythagoreanTheorem.java
 * Name: 
 * Section Leader: 
 * -----------------------------
 * This file is the starter file for the PythagoreanTheorem problem.
 */

import acm.program.*;

public class PythagoreanTheorem extends ConsoleProgram {
	public void run() {
		welcomeToPythagora();
		insertCathetus();
	}

	/* prints the welcome text of the task Pythagorean theorem */
	private void welcomeToPythagora() {
		println("Enter the values to compute Pythagorean theorem.");
	}

	/* asks for the input and prints what the hypotenuse is */
	private void insertCathetus() {
		double a = readDouble("a: ");
		double b = readDouble("b: ");
		println("c = " + Math.sqrt(a * a + b * b));
	}
}
