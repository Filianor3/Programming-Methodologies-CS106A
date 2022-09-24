
/*
 * File: Hangman.java

 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;

public class Hangman extends ConsoleProgram {

	public void init() {
		lexicon = new HangmanLexicon();
		println("Welcome to Hangman!");
		canvas = new HangmanCanvas();
		add(canvas);
	}

	public void run() {
		gameInitialization();
		gameProcess();
	}

	/*
	 * Initialising everything that is necessary for the game, 8 starting lives,
	 * Randomising the word and turning word into '-' symbols
	 */
	private void gameInitialization() {
		nLives = 8;
		guessingProcess = "";
		canvas.reset();
		wordToGuess = lexicon.getWord(rgen.nextInt(0, lexicon.getWordCount() - 1));
		wordToGuess = wordToGuess.toUpperCase();
		for (int i = 0; i < wordToGuess.length(); i++) {
			if (wordToGuess.charAt(i) == ' ')
				guessingProcess += ' ';
			else
				guessingProcess += '-';
		}
	}

	/*
	 * the process of playing the game a while loop which doesn't end until the user
	 * runs out of 8 lives, every time a new input is typed in the console the
	 * canvas refreshes and gives us new result image
	 */
	private void gameProcess() {
		while (nLives != 0) {
			println("The word now looks like this: " + guessingProcess);
			canvas.displayWord(guessingProcess);
			if (wordToGuess.equals(guessingProcess)) {
				break;
			}
			println("You have " + nLives + " guesses left");
			String input = readLine("Your guess: ");
			if (letterOrNot(input)) {
				char ch = input.charAt(0);
				ch = Character.toUpperCase(ch);
				if (checkLetter(wordToGuess, ch)) {
					replaceLetter(wordToGuess, ch);
					canvas.updateCanvas(nLives);
				} else {
					nLives--;
					println("There are no " + ch + "'s in the word");
					canvas.noteIncorrectGuess(ch);
					canvas.updateCanvas(nLives);
				}
			} else {
				println("Invalid input, please try again.");
			}
		}
		if (nLives == 0)
			loseCon();
		else
			println("You win!");

	}
	
	/* What happens in case the user loses the game */
	private void loseCon() {
		println("You are completely hung.");
		println("The word was: " + wordToGuess);
		println("You Lose");
	}

	/*
	 * this method replaced the letter (the user gave to the console), if such
	 * character is in the word which has been set to guess
	 */
	private void replaceLetter(String wordToGuess, char ch) {
		for (int i = 0; i < guessingProcess.length(); i++) {
			if (wordToGuess.charAt(i) == ch) {
				guessingProcess = guessingProcess.substring(0, i) + ch + guessingProcess.substring(i + 1);
			}
		}
	}

	/*
	 * this method checks if the input user typed in the console is a letter or not
	 */
	private boolean letterOrNot(String input) {
		if (input.length() != 1)
			return false;
		return Character.isLetter(input.charAt(0));
	}

	/* checks if the word contains the letter and returns boolean */
	private boolean checkLetter(String wordToGuess, char ch) {
		return wordToGuess.indexOf(ch) != -1;
	}

	private HangmanLexicon lexicon;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String guessingProcess;
	private int nLives;
	private HangmanCanvas canvas;
	private String wordToGuess;
}
