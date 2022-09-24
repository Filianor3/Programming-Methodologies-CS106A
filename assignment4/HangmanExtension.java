
/*
 * File: Hangman.java
 * ------------------
 * This program will eventually play the Hangman game from
 * Assignment #4.
 */

import acm.program.*;
import acm.util.*;
import java.applet.AudioClip;
import java.awt.event.ActionEvent;
import javax.swing.JButton;

public class HangmanExtension extends ConsoleProgram {

	public void init() {
		this.setFont("SYLFAEN-20");
		lexicon = new HangmanLexiconExtension();
		println("Welcome to Hangman! If you wish to restart the game, type \"RESTART\" ");
		canvas = new HangmanCanvasExtension();
		add(canvas);
		hint = new JButton("HINT");
		add(hint, SOUTH);
		hint.addActionListener(this);
		addActionListeners();
		this.setSize(800, 600);
	}

	/*
	 * If the user uses the HINT button, The first unknown character in the word
	 * will be revealed, IMPORTANT NOTE: if the word was APPPLE, and guessing
	 * process is A**LE, and the user uses HINT button, both P-s will be revealed
	 */

	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == hint && nHints != 0) {
			nHints--;
			for (int i = 0; i < guessingProcess.length(); i++) {
				if (guessingProcess.charAt(i) == '-') {
					guessingProcess = guessingProcess.substring(0, i) + wordToGuess.charAt(i)
							+ guessingProcess.substring(i + 1);
					for (int j = 0; j < guessingProcess.length(); j++) {
						if (wordToGuess.charAt(i) == wordToGuess.charAt(j)) {
							guessingProcess = guessingProcess.substring(0, j) + wordToGuess.charAt(i)
									+ guessingProcess.substring(j + 1);
						}
					}
					canvas.displayWord(guessingProcess);
					canvas.displayHints(nHints);
					break;
				}
			}
		}
	}

	public void run() {
		gameInitialization();
		gameProcess();
	}

	/*
	 * Giving the user the word to guess, resetting canvas and randomising the word
	 */
	private void gameInitialization() {
		nHints = 1;
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

	/* whole game process */
	private void gameProcess() {
		checkRestart = false;
		while (nLives != 0) {
			println("The word now looks like this: " + guessingProcess);
			canvas.displayWord(guessingProcess);
			canvas.displayHints(nHints);
			if (wordToGuess.equals(guessingProcess)) {
				break;
			}
			println("You have " + nLives + " guesses left");
			String input = readLine("Your guess: ");
			if (input.toUpperCase().equals("RESTART")) {
				checkRestart = true;
				break;
			}
			if (letterOrNot(input)) {
				char ch = input.charAt(0);
				ch = Character.toUpperCase(ch);
				if (checkLetter(wordToGuess, ch)) {
					replaceLetter(wordToGuess, ch);
					canvas.updateCanvas(nLives);
					canvas.displayHints(nHints);
				} else {
					nLives--;
					println("There are no " + ch + "'s in the word");
					canvas.noteIncorrectGuess(ch);
					canvas.updateCanvas(nLives);
					canvas.displayHints(nHints);
				}
			} else {
				println("Invalid input, please try again.");
			}
		}
		if (checkRestart) {
			run();
		} else if (nLives == 0) {
			println("The word was: " + wordToGuess);
			println("You Lose");
			defeatClip.play();
			newGame();
		} else {
			println("You win!");
			victoryClip.play();
			newGame();
		}
	}

	/*
	 * The message which the user gets if the game is over, and what should happen
	 * in that case
	 */
	private void newGame() {
		boolean again = false;
		while (true) {
			String answer = readLine("If you would like to play again, type Y, if not type N: ");
			if (answer.toUpperCase().equals("Y")) {
				again = true;
				break;
			} else if (answer.toUpperCase().equals("N")) {
				break;
			} else {
				println("Please type valid input");
			}
		}
		if (again)
			run(); // game restart if the player wants to play again
	}

	/*
	 * replaces the '-' with input if there is such character in the word to guess
	 */
	private void replaceLetter(String wordToGuess, char ch) {
		for (int i = 0; i < guessingProcess.length(); i++) {
			if (wordToGuess.charAt(i) == ch) {
				guessingProcess = guessingProcess.substring(0, i) + ch + guessingProcess.substring(i + 1);
			}
		}
	}

	/* checks the size of the input */
	private boolean letterOrNot(String input) {
		if (input.length() != 1)
			return false;
		return Character.isLetter(input.charAt(0));
	}

	/* checks if the word contains the input */
	private boolean checkLetter(String wordToGuess, char ch) {
		return wordToGuess.indexOf(ch) != -1;
	}

	private HangmanLexiconExtension lexicon;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private String guessingProcess;
	private int nLives;
	private HangmanCanvasExtension canvas;
	private JButton hint;
	private int nHints;
	private String wordToGuess;
	private boolean checkRestart;
	AudioClip victoryClip = MediaTools.loadAudioClip("victory.au");
	AudioClip defeatClip = MediaTools.loadAudioClip("defeat.au");
}
