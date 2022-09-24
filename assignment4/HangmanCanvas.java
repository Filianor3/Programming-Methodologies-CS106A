
/*
 * File: HangmanCanvas.java
 * ------------------------
 * This file keeps track of the Hangman display.
 */

import java.awt.Color;

import acm.graphics.*;

public class HangmanCanvas extends GCanvas {

	/**
	 * Resets the display so that only the scaffold appears and the new word is
	 * setup the wrongGuesses are cleared up too
	 */
	public void reset() {
		GLine SCAFFOLD = new GLine(this.getWidth() / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET, this.getWidth() / 2 - BEAM_LENGTH,
				SCAFFOLD_Y_OFFSET + SCAFFOLD_HEIGHT);
		GLine BEAM = new GLine(this.getWidth() / 2 - BEAM_LENGTH, SCAFFOLD_Y_OFFSET, this.getWidth() / 2, SCAFFOLD_Y_OFFSET);
		GLine ROPE = new GLine(this.getWidth() / 2, SCAFFOLD_Y_OFFSET, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH);
		wrongGuesses = new GLabel("", 20, 455);
		wrongGuesses.setFont("SYLFAEN-15");
		wrongGuesses.setColor(Color.RED);
		currWord = new GLabel("", 20, 425);
		currWord.setColor(Color.BLUE);
		currWord.setFont("SYLFAEN-BOLD-18");
		add(wrongGuesses);
		add(currWord);
		add(SCAFFOLD);
		add(BEAM);
		add(ROPE);
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

	/* Updates the canvas based on the current lives left of the user */
	public void updateCanvas(int nLives) {
		if (nLives == 7) {
			GOval HEAD = new GOval((this.getWidth() / 2) - HEAD_RADIUS, SCAFFOLD_Y_OFFSET + ROPE_LENGTH, HEAD_RADIUS * 2,
					HEAD_RADIUS * 2);
			add(HEAD);
		} else if (nLives == 6) {
			stickBodyPart(BODY, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2, this.getWidth() / 2,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2);
		} else if (nLives == 5) {
			stickBodyPart(LEFT_ARM, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD,
					this.getWidth() / 2 - UPPER_ARM_LENGTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
			stickBodyPart(LEFT_HAND, this.getWidth() / 2 - UPPER_ARM_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, this.getWidth() / 2 - UPPER_ARM_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		} else if (nLives == 4) {
			stickBodyPart(RIGHT_ARM, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD,
					this.getWidth() / 2 + UPPER_ARM_LENGTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD);
			stickBodyPart(RIGHT_HAND, this.getWidth() / 2 + UPPER_ARM_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD, this.getWidth() / 2 + UPPER_ARM_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + HEAD_RADIUS * 2 + ARM_OFFSET_FROM_HEAD + LOWER_ARM_LENGTH);
		} else if (nLives == 3) {
			stickBodyPart(LEFT_HIP, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2,
					this.getWidth() / 2 - HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2);
			stickBodyPart(LEFT_LEG, this.getWidth() / 2 - HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2,
					this.getWidth() / 2 - HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH);
		} else if (nLives == 2) {
			stickBodyPart(RIGHT_HIP, this.getWidth() / 2, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2,
					this.getWidth() / 2 + HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2);
			stickBodyPart(RIGHT_LEG, this.getWidth() / 2 + HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2,
					this.getWidth() / 2 + HIP_WIDTH, SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH);
		} else if (nLives == 1) {
			stickBodyPart(LEFT_FOOT, this.getWidth() / 2 - HIP_WIDTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH,
					this.getWidth() / 2 - HIP_WIDTH - FOOT_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH);
		} else if (nLives == 0) {
			stickBodyPart(RIGHT_FOOT, this.getWidth() / 2 + HIP_WIDTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH,
					this.getWidth() / 2 + HIP_WIDTH + FOOT_LENGTH,
					SCAFFOLD_Y_OFFSET + ROPE_LENGTH + BODY_LENGTH + HEAD_RADIUS * 2 + LEG_LENGTH);
		}
	}

	/* draws the stick body parts of the man */
	private void stickBodyPart(GLine line, double x1, double y1, double x2, double y2) {
		line = new GLine(x1, y1, x2, y2);
		add(line);
	}

	/* Constants for the simple version of the picture (in pixels) */
	private static final int SCAFFOLD_HEIGHT = 360;
	private static final int BEAM_LENGTH = 144;
	private static final int ROPE_LENGTH = 18;
	private static final int HEAD_RADIUS = 36;
	private static final int BODY_LENGTH = 144;
	private static final int ARM_OFFSET_FROM_HEAD = 28;
	private static final int UPPER_ARM_LENGTH = 72;
	private static final int LOWER_ARM_LENGTH = 44;
	private static final int HIP_WIDTH = 36;
	private static final int LEG_LENGTH = 108;
	private static final int FOOT_LENGTH = 28;
	private static final int SCAFFOLD_Y_OFFSET = 25;
	private GLabel wrongGuesses;
	private GLabel currWord;
	private GLine BODY;
	private GLine RIGHT_FOOT;
	private GLine LEFT_FOOT;
	private GLine RIGHT_HIP;
	private GLine RIGHT_LEG;
	private GLine LEFT_HIP;
	private GLine LEFT_LEG;
	private GLine RIGHT_ARM;
	private GLine RIGHT_HAND;
	private GLine LEFT_ARM;
	private GLine LEFT_HAND;

}
