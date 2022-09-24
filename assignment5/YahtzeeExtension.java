
/*
 * File: Yahtzee.java
 * ------------------
 * This program will eventually play the Yahtzee game.
 */

import java.applet.AudioClip;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;

import acm.graphics.GLabel;
import acm.io.*;
import acm.program.*;
import acm.util.*;
import javafx.util.Pair;

public class YahtzeeExtension extends GraphicsProgram implements YahtzeeConstants {

	public static void main(String[] args) {
		new YahtzeeExtension().start(args);
	}

	public void init() {
		this.setSize(800, this.getHeight());
	}

	public void run() {
		IODialog dialog = getDialog();
		nPlayers = dialog.readInt("Enter number of players");
		playerNames = new String[nPlayers];
		for (int i = 1; i <= nPlayers; i++) {
			playerNames[i - 1] = dialog.readLine("Enter name for player " + i + " "
					+ ", if u want to play with BOT type any string that contains 'bot'");

		}
		display = new YahtzeeDisplay(getGCanvas(), playerNames);
		initialise();
		playGame();
	}

	/*
	 * creates a matrix where will be nPlayer columns and N_CATEG ORIES + 1 rows for
	 * categories
	 */
	private void initialise() {
		list = new int[nPlayers][N_CATEGORIES + 1];
		points = new int[nPlayers][N_CATEGORIES + 1];
		readScores();
		GLabel label = new GLabel("SCOREBOARD");
		label.setFont("-BOLD-30");
		label.setColor(Color.RED);
		add(label, 535, 35);

	}

	/* reades the scoreboard points from file */
	private void readScores() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("highscores.txt"));
			while (true) {
				String current = br.readLine();
				if (current == null)
					break;
				Pair<Integer, String> pair = new Pair<>(Integer.parseInt(current.substring(current.indexOf(' ') + 1)),
						current.substring(0, current.indexOf(' ')));
				highScores.add(pair);
			}
			br.close();
		} catch (Exception e) {

		}
		highScores.sort((a, b) -> a.getKey() > b.getKey() ? -1 : a.getKey() == b.getKey() ? 0 : 1);
		drawScoreboard();
	}

	/* draws the glabels on the scoreboard side of the board */
	private void drawScoreboard() {
		double x = 560;
		double y = 60;
		double gap = getHeight() / 65;
		for (int i = 0; i < 10; i++) {
			GLabel label = new GLabel(highScores.get(i).getValue() + "        " + highScores.get(i).getKey());
			add(label, x, y);
			label.setFont("-BOLD-15");
			scores[i] = label;
			y += label.getHeight() + gap;
		}
	}

	/* updates the textFile where we store our high scores */
	private void updateHighScores() {
		try {
			FileWriter fwOb = new FileWriter("highscores.txt", false);
			PrintWriter pwOb = new PrintWriter(fwOb, false);
			pwOb.flush();
			pwOb.close();
			fwOb.close();
		} catch (IOException E) {

		}

		try {
			BufferedWriter wr = new BufferedWriter(new FileWriter("highscores.txt"));
			for (int i = 0; i < 10; i++) {
				wr.write(highScores.get(i).getValue() + " " + highScores.get(i).getKey() + "\n");
			}
			wr.close();
		} catch (Exception e) {

		}
	}

	/* Game process, first roll and other two rolls for existing players */
	private void playGame() {
		for (int i = 0; i < N_SCORING_CATEGORIES; i++) {
			for (int j = 0; j < nPlayers; j++) {
				display.printMessage(playerNames[j] + "'s turn. Click \"Roll Dice\" button to roll the dice.");
				display.waitForPlayerToClickRoll(j + 1);
				firstRoll();
				if (playerNames[j].contains("Bot") || playerNames[j].contains("BOT")
						|| playerNames[j].contains("bot")) {
					int tempCat = AI(j);
					display.updateScorecard(tempCat, j + 1, setScore(tempCat));
					updatePoints(j, tempCat);
				//	pause(1800); // little time to take a look at bot's dices
				} else { // this else is for only actual players
					for (int m = 0; m < REROLL_COUNT; m++) {
						display.printMessage("Select the dice you wish to re-roll and click \"Roll Again\".");
						notTheFirstRoll();
					}
					display.printMessage("Select a category for this roll.");
					int chosenCat = pickValidCat(j);
					display.updateScorecard(chosenCat, j + 1, setScore(chosenCat));
					updatePoints(j, chosenCat);
				}
			}
		}
		for (int i = 0; i < nPlayers; i++) {
			endGame(i);
		}
		announceWinner();
		updateHighScores();
	}

	/*
	 * When bot is playing the game, the best possible category is automaticall
	 * chosen with the highest points poissible
	 */
	private int AI(int nThPlayer) {
		list[nThPlayer][UPPER_BONUS] = 1;
		list[nThPlayer][UPPER_SCORE] = 1;
		list[nThPlayer][LOWER_SCORE] = 1;
		list[nThPlayer][TOTAL] = 1;
		int currentCat = -1;
		int currentMax = -1;
		for (int i = 1; i <= N_CATEGORIES; i++) {
			if (list[nThPlayer][i] == 0) {
				int x = setScore(i);
				if (x > currentMax) {
					currentMax = x;
					currentCat = i;
				}
			}
		}
		points[nThPlayer][currentCat] = currentMax;
		list[nThPlayer][currentCat] = 1;
		return currentCat;
	}

	/*
	 * When the game is over and all 13 categories are filled for all existing
	 * players, the method calculates the one with the highest score and announces
	 * them as a winner
	 */
	private void announceWinner() {
		String name = "";
		int winnerPoints = 0;
		for (int i = 0; i < nPlayers; i++) {
			if (points[i][TOTAL] > winnerPoints) {
				winnerPoints = points[i][TOTAL];
				name = playerNames[i];
			}
		}
		display.printMessage(
				"Congratulations, " + name + ", you're the winner with a total score of " + winnerPoints + "!");
		for (int i = 0; i < nPlayers; i++) {
			highScores.add(new Pair<>(points[i][TOTAL], playerNames[i]));
		}
		highScores.sort((a, b) -> a.getKey() > b.getKey() ? -1 : a.getKey() == b.getKey() ? 0 : 1);
		for (int i = 0; i < 10; i++) {
			remove(scores[i]);
			scores[i] = null;
		}
		drawScoreboard();
	}

	/* updates the point matrix with the certain point */
	private void updatePoints(int j, int cat) {
		int total = 0;
		points[j][cat] = setScore(cat);
		for (int i = ONES; i < TOTAL; i++) {
			total += points[j][i];
		}
		points[j][UPPER_SCORE] = upperSC; // sets value of upper score in points matrix
		points[j][LOWER_SCORE] = lowerSC; // sets value of lower score in points matrix
		points[j][TOTAL] = total;
		display.updateScorecard(TOTAL, j + 1, total);
	}

	/*
	 * When the game is over, sets the values on lower and upper scores + calculates
	 * the upper bonus if it can be added, summarizes the whole game
	 */
	private void endGame(int j) {
		// counts upper score
		for (int i = ONES; i <= SIXES; i++) {
			upperSC += points[j][i];
		}

		// counts lower score
		for (int i = THREE_OF_A_KIND; i <= CHANCE; i++) {
			lowerSC += points[j][i];
		}

		if (upperSC >= UPPER_RANGE) {
			points[j][UPPER_BONUS] = UPPER_BONUS_VALUE;
		}

		display.updateScorecard(UPPER_SCORE, j + 1, upperSC);
		display.updateScorecard(LOWER_SCORE, j + 1, lowerSC);
		display.updateScorecard(UPPER_BONUS, j + 1, points[j][UPPER_BONUS]);
		lowerSC = 0;
		upperSC = 0;
	}

	/* returns the int that should be given to the certain category */
	private int setScore(int cat) {
		boolean p = checkCat(diceList, cat);
		int sum = 0;
		for (int i = 0; i < diceList.length; i++) {
			sum += diceList[i];
		}
		if (p && cat == FULL_HOUSE)
			return 25;
		if (p && cat == SMALL_STRAIGHT)
			return 30;
		if (p && cat == LARGE_STRAIGHT)
			return 40;
		if (p && cat == YAHTZEE)
			return 50;
		if (p && cat == THREE_OF_A_KIND)
			return sum;
		if (p && cat == FOUR_OF_A_KIND)
			return sum;
		if (cat == CHANCE)
			return sum;
		if (cat == ONES)
			return simpleChecks(cat);
		if (cat == TWOS)
			return simpleChecks(cat);
		if (cat == THREES)
			return simpleChecks(cat);
		if (cat == FOURS)
			return simpleChecks(cat);
		if (cat == FIVES)
			return simpleChecks(cat);
		if (cat == SIXES)
			return simpleChecks(cat);

		return 0;
	}

	/* Checks whether the current diceList is compatible with the category chosen */
	private boolean checkCat(int[] diceList, int cat) {
		int[] frequency = new int[6];
		for (int i = 0; i < diceList.length; i++) {
			frequency[diceList[i] - 1]++;
		}

		Arrays.sort(diceList);

		// to cheat and avoid using arraylists
		for (int i = 0; i < frequency.length; i++) {
			if (frequency[i] == 0)
				frequency[i] = 9;
		}

		if (cat == THREE_OF_A_KIND) {
			for (int i = 0; i < frequency.length; i++) {
				if (frequency[i] >= 3 && frequency[i] <= 6)
					return true;
			}
		}

		if (cat == FOUR_OF_A_KIND) {
			for (int i = 0; i < frequency.length; i++) {
				if (frequency[i] >= 4 && frequency[i] <= 6)
					return true;
			}
		}

		if (cat == YAHTZEE) {
			for (int i = 0; i < frequency.length; i++) {
				if (frequency[i] == 5)
					return true;
			}
		}

		if (cat == FULL_HOUSE) {
			Arrays.sort(frequency);
			if (frequency[0] == 2 && frequency[1] == 3)
				return true;
		}

		// there are !=9s bcs i turned frequency array 0s to 9s for sorting (for them to
		// be at the last place)
		if (cat == SMALL_STRAIGHT) {
			if ((frequency[0] >= 1 && frequency[0] != 9) && (frequency[1] >= 1 && frequency[1] != 9)
					&& (frequency[2] >= 1 && frequency[2] != 9) && (frequency[3] >= 1 && frequency[3] != 9))
				return true;
			if ((frequency[1] >= 1 && frequency[1] != 9) && (frequency[2] >= 1 && frequency[2] != 9)
					&& (frequency[3] >= 1 && frequency[3] != 9) && (frequency[4] >= 1 && frequency[4] != 9))
				return true;
			if ((frequency[2] >= 1 && frequency[2] != 9) && (frequency[3] >= 1 && frequency[3] != 9)
					&& (frequency[4] >= 1 && frequency[4] != 9) && (frequency[5] >= 1 && frequency[5] != 9))
				return true;
		}

		if (cat == LARGE_STRAIGHT) {
			int counter = 0;
			for (int i = 1; i < diceList.length; i++) {
				if ((diceList[i] == diceList[i - 1] + 1))
					counter++;
			}
			if (counter == 4) {
				return true;
			}
		}

		return false;
	}

	/* returns the score for categories ones,twos,threes,fours,fives,sixes */
	private int simpleChecks(int cat) {
		int checker = 0;
		int sum = 0;
		if (cat == ONES)
			checker = ONES;
		if (cat == TWOS)
			checker = TWOS;
		if (cat == THREES)
			checker = THREES;
		if (cat == FOURS)
			checker = FOURS;
		if (cat == FIVES)
			checker = FIVES;
		if (cat == SIXES)
			checker = SIXES;

		for (int i = 0; i < diceList.length; i++) {
			if (diceList[i] == checker) {
				sum += checker;
			}
		}
		return sum;
	}

	/* first roll of the dice, randoming 6 numbers from 1 to 6 */
	private void firstRoll() {
		diceRoll.play();
		for (int i = 0; i < N_DICE; i++) {
			int rolledNum = rgen.nextInt(1, 6);
			diceList[i] = rolledNum;
		}
		display.displayDice(diceList);
	}

	/* this method is for rerolls, second and third roll */
	private void notTheFirstRoll() {
		diceRoll.play();
		display.waitForPlayerToSelectDice();
		for (int i = 0; i < N_DICE; i++) {
			int rerolledNum = rgen.nextInt(1, 6);
			if (display.isDieSelected(i)) {
				diceList[i] = rerolledNum;
			}
		}
		display.displayDice(diceList);
	}

	/*
	 * checks whether the category, has been selected by current player who is
	 * rolling the dice, if so, the program won't let you overwrite the data, it
	 * will not allow u to move on, unless you choose the correct category. If the
	 * category wasn't chosen before but is chosen now, the certain cell in the max
	 * will get the value '1' (which was 0 for all cells in the beginning)
	 */
	private int pickValidCat(int currPlayer) {
		int currCategory;
		while (true) {
			currCategory = display.waitForPlayerToSelectCategory();
			if (list[currPlayer][currCategory] == 0) {
				list[currPlayer][currCategory] = 1;
				return currCategory;
			} else {
				display.printMessage("Please select another category.");
				continue; // to keep the loop going
			}
		}

	}

	/* Private instance variables */
	private int nPlayers;
	private String[] playerNames;
	private YahtzeeDisplay display;
	private int[][] list;
	private int[][] points;
	private RandomGenerator rgen = new RandomGenerator();
	private GLabel[] scores = new GLabel[10];
	private int[] diceList = new int[N_DICE];
	private int upperSC = 0;
	private int lowerSC = 0;
	private AudioClip diceRoll = MediaTools.loadAudioClip("diceRoll.au");
	private ArrayList<Pair<Integer, String>> highScores = new ArrayList<>();
}
