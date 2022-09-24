
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvasExtension extends GCanvas {

	/** Resets the display so that only the scaffold appears */
	public void reset() {
		this.removeAll();
		GImage background = new GImage("images/background.jpg");
		add(background);
		addStartingInterface();
		wrongGuesses = new GLabel("", 100, 35);
		wrongGuesses.setFont("SYLFAEN-BOLD-15");
		wrongGuesses.setColor(Color.RED);
		currWord = new GLabel("", 20, 470);
		currWord.setColor(Color.BLACK);
		currWord.setFont("SYLFAEN-BOLD-24");
		currHints = new GLabel("", 10, 35);
		currHints.setColor(Color.BLACK);
		currHints.setFont("SYLFAEN-BOLD-20");
		add(wrongGuesses);
		add(currWord);
		add(currHints);
	}

	private void addStartingInterface() {
		GImage block0 = new GImage("images/oak.png");
		GImage block1 = new GImage("images/oak.png");
		GImage block2 = new GImage("images/oak.png");
		GImage block3 = new GImage("images/oak.png");
		GImage block4 = new GImage("images/oak.png");
		GImage block5 = new GImage("images/oak.png");
		GImage block6 = new GImage("images/oak.png");
		GImage block7 = new GImage("images/oak.png");
		GImage block8 = new GImage("images/oak.png");
		GImage block9 = new GImage("images/oak.png");
		GImage block10 = new GImage("images/oak.png");
		GImage rope = new GImage("images/rope.png");
		add(rope, 150, 100);
		rope.scale(0.25);
		add(block0, 50, 350);
		add(block1, 50, 300);
		add(block2, 50, 250);
		add(block3, 50, 200);
		add(block4, 50, 150);
		add(block5, 50, 100);
		add(block10, 250, 50);
		add(block8, 50, 50);
		add(block6, 100, 50);
		add(block7, 150, 50);
		add(block9, 200, 50);
	}

	/**
	 * Updates the word on the screen to correspond to the current state of the
	 * game. The argument string shows what letters have been guessed so far;
	 * unguessed letters are indicated by hyphens.
	 */
	public void displayWord(String word) {
		currWord.setLabel(word);
	}

	/**
	 * Updates the display to correspond to an incorrect guess by the user. Calling
	 * this method causes the next body part to appear on the scaffold and adds the
	 * letter to the list of incorrect guesses that appears at the bottom of the
	 * window.
	 */
	public void noteIncorrectGuess(char letter) {
		wrongGuesses.setLabel(wrongGuesses.getLabel() + letter);
	}

	/*
	 * displays the amount of the hints we have left on the canvas
	 */ public void displayHints(int nHints) {
		currHints.setLabel("Hints: " + nHints);
	}

	public void updateCanvas(int nLives) {
		if (nLives == 7) {
			drawObject(head, x1, x1, "images/head.png");
		} else if (nLives == 6) {
			drawObject(body, x1, y1, "images/body.png");
		} else if (nLives == 5) {
			drawObject(hand, 149, y1, "images/hand.png");
		} else if (nLives == 4) {
			drawObject(hand, 226, y1, "images/hand.png");
		} else if (nLives == 3) {
			drawObject(leftLeg, x1, y2, "images/leftLeg.png");
		} else if (nLives == 2) {
			drawObject(rightLeg, x2, y2, "images/rightLeg.png");
		} else if (nLives == 1) {
			drawObject(feet, x1, y3, "images/feet.png");
		} else if (nLives == 0) {
			drawObject(feet, x2, y3, "images/feet.png");
		}
	}

	/*
	 * method which takes info of image, x, y, name of the file and adds and scales
	 * it
	 */
	private void drawObject(GImage obj, int x, int y, String input) {
		obj = new GImage(input);
		add(obj, x, y);
		obj.scale(scaleValue);
	}

	private GLabel wrongGuesses;
	private GLabel currWord;
	private GLabel currHints;
	private GImage feet;
	private GImage rightLeg;
	private GImage leftLeg;
	private GImage hand;
	private GImage head;
	private GImage body;
	private static final double scaleValue = 0.5;
	private static final int x1 = 175;
	private static final int x2 = 201;
	private static final int y1 = 227;
	private static final int y2 = 290;
	private static final int y3 = 365;

}
