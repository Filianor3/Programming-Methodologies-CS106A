
/*
 * File: Breakout.java
 * -------------------
 * Name:
 * Section Leader:
 * 
 * This file will eventually implement the game of Breakout.
 */

import acm.graphics.*;
import acm.program.*;
import acm.util.*;

import java.util.ArrayList;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class BreakoutExtension extends GraphicsProgram {

	/** Width and height of application window in pixels */
	public static final int APPLICATION_WIDTH = 400;
	public static final int APPLICATION_HEIGHT = 600;

	/** Dimensions of game board (usually the same) */
	private static final int WIDTH = APPLICATION_WIDTH;
	private static final int HEIGHT = APPLICATION_HEIGHT;

	/** Dimensions of the paddle */
	private static final int PADDLE_WIDTH = 60;
	private static final int PADDLE_HEIGHT = 10;

	/** Offset of the paddle up from the bottom */
	private static final int PADDLE_Y_OFFSET = 30;

	/** Number of bricks per row */
	private static final int NBRICKS_PER_ROW = 10;

	/** Number of rows of bricks */
	private static final int NBRICK_ROWS = 10;

	/** Separation between bricks */
	private static final int BRICK_SEP = 4;

	/** Width of a brick */
	private static final int BRICK_WIDTH = (WIDTH - (NBRICKS_PER_ROW - 1) * BRICK_SEP) / NBRICKS_PER_ROW;

	/** Height of a brick */
	private static final int BRICK_HEIGHT = 8;

	/** Radius of the ball in pixels */
	private static final int BALL_RADIUS = 10;

	/** Offset of the top brick row from the top */
	private static final int BRICK_Y_OFFSET = 70;

	/** Number of turns */
	private static final int NTURNS = 3;

	private static final int BALL_ADDITION_POINTS = 500;

	/* Paddle Widths throughout the game */
	private static final int LEVEL_ONE = 1000;
	private static final int LEVEL_TWO = 2000;
	private static final int LEVEL_THREE = 3000;
	/*
	 * The list where we will keep tracking of all the current balls and add or
	 * remove then
	 */
	private ArrayList<GOval> balls;
	/* The list where we will keep the X velocity of the certain balls */
	private ArrayList<Double> speedsX;
	/* The list where we will keep the Y velocity of the certain balls */
	private ArrayList<Double> speedsY;
	private ArrayList<GRect> bricks = new ArrayList<GRect>();
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private AudioClip bounceClip = MediaTools.loadAudioClip("bounce.au");
	private AudioClip winClip = MediaTools.loadAudioClip("winaudio.au");
	private AudioClip loseClip = MediaTools.loadAudioClip("loseaudio.au");
	private GImage gimage, gimage1, gimage2;
	private GRect frame, frame2, racket;
	private GLabel winner, loser, welcome, points;
	private int pointCounter = 0;
	private int HP = NTURNS;
	private int brickCount = NBRICKS_PER_ROW * NBRICK_ROWS;

	public void init() {
		balls = new ArrayList<>();
		speedsX = new ArrayList<>();
		speedsY = new ArrayList<>();
		addHP();
		addPointsBar();
		addMouseListeners();
	}

	/** Runs the Breakout program. */
	public void run() {
		boolean georgianMarch = gameMode();
		if (georgianMarch) {
			georgianMarchFlag();
		} else {
			addBlocks();
		}
		addRacket();
		addWelcome();
		waitForClick();
		remove(welcome);
		addBall();
		playGame(georgianMarch);
	}

	/* draws the HP bar with GImages */
	private void addHP() {
		gimage = new GImage("heart.png");
		gimage1 = new GImage("heart1.png");
		gimage2 = new GImage("heart2.png");
		add(gimage);
		add(gimage1, 25, 0);
		add(gimage2, 50, 0);
		frame = new GRect(75, 25);
		add(frame);
	}

	/* creates point indicator label and its frame */
	private void addPointsBar() {
		frame2 = new GRect(100, 26);
		add(frame2, 300, 2);
		points = new GLabel(String.valueOf(pointCounter));
		add(points, 325, 25);
		points.setFont("Sylfaen-bold-25");
	}

	/*
	 * click to play warning
	 */
	private void addWelcome() {
		welcome = new GLabel("Click to play", getWidth() / 2 - 160, getHeight() / 2 - 20);
		add(welcome);
		welcome.setFont("Sylfaen-bold-50");
	}

	/* what happens on win screen */
	private void winner() {
		winner = new GLabel("YOU WIN ! POG", getWidth() / 2 - 160, getHeight() / 2 - 20);
		add(winner);
		winner.setFont("Sylfaen-bold-40");
	}

	/* what happens on lose screen */
	private void loser() {
		loser = new GLabel("YOU LOSE", getWidth() / 2 - 130, getHeight() / 2 - 20);
		add(loser);
		loser.setFont("Sylfaen-bold-50");
	}

	/*
	 * displays a panel before the game start in order for the user to choose the
	 * game mode
	 */
	private boolean gameMode() {
		JPanel temp = new JPanel();
		Object arr[] = { "LGBTQ+", "Georgian March Member" };
		JOptionPane chooseMode = new JOptionPane();
		int result = chooseMode.showOptionDialog(this.getGCanvas(),
				"                                    Choose Game Mode", "Game Mode", JOptionPane.YES_NO_OPTION,
				JOptionPane.PLAIN_MESSAGE, null, arr, null);
		return result == 1;
	}

	/*
	 * draws the blocks on the board, 2 lines each of 5 colour
	 */
	private void addBlocks() {
		int tempX = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
		int tempY = BRICK_Y_OFFSET;
		int n = 0;
		Color tempColor = null;
		while (n < 10) {
			if (n < 2)
				tempColor = Color.RED;
			else if (n < 4)
				tempColor = Color.ORANGE;
			else if (n < 6)
				tempColor = Color.YELLOW;
			else if (n < 8)
				tempColor = Color.GREEN;
			else
				tempColor = Color.CYAN;
			for (int i = 0; i < NBRICK_ROWS; i++) {
				GRect rect = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				add(rect, tempX, tempY);
				rect.setFilled(true);
				rect.setColor(tempColor);
				tempX += BRICK_WIDTH + BRICK_SEP;
				bricks.add(rect);
			}
			n++;
			tempX = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
			tempY = tempY + BRICK_HEIGHT + BRICK_SEP;
		}
	}

	/*
	 * In case the user chooses to play this game mode, it draws the blocks in a
	 * slightly different way
	 */
	private void georgianMarchFlag() {
		int tempX = 2;
		int tempY = BRICK_Y_OFFSET;
		Color tempColor = null;
		for (int j = 0; j < 10; j++) {
			for (int i = 0; i < NBRICK_ROWS; i++) {
				if (j < 3 && i < 3) {
					tempColor = Color.BLACK;
				} else if (j < 6 && i < 3) {
					tempColor = Color.WHITE;
				} else {
					tempColor = new Color(169, 15, 0);
				}
				GRect rect = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				add(rect, tempX, tempY);
				rect.setFilled(true);
				rect.setFillColor(tempColor);
				tempX += BRICK_WIDTH + BRICK_SEP;
				bricks.add(rect);
			}
			tempY = tempY + BRICK_HEIGHT + BRICK_SEP;
			tempX = 2;
		}

	}

	/*
	 * draws the racket on the board
	 */
	private void addRacket() {
		int tempX = WIDTH / 2 - PADDLE_WIDTH / 2;
		int tempY = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
		racket = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(racket, tempX, tempY);
		racket.setFilled(true);
	}

	/* Controls the racket movement */
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < WIDTH - racket.getWidth() && e.getX() > 0)
			racket.setLocation(e.getX(), getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT));
	}

	/*
	 * add balls to the board and gives it random velocity between given ranges
	 */
	private void addBall() {
		GOval ball = new GOval(WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, BALL_RADIUS * 2,
				BALL_RADIUS * 2);
		balls.add(ball);
		ball.setFilled(true);
		double vy = 3;
		double vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		speedsX.add(vx);
		speedsY.add(vy);
		add(ball);
	}

	/*
	 * handles the logic of ball collisions for all the balls in the game
	 */
	private void playGame(boolean georgianMarch) {
		while (true) {
			for (int i = 0; i < balls.size(); i++) {
				GOval ball = balls.get(i);
				ball.move(speedsX.get(i), speedsY.get(i));
			}
			collideWithWall();
			collideWithBrick(georgianMarch);
			if (HP == 0) {
				for (GOval ball : balls) {
					remove(ball);
				}
				loser();
				loseClip.play();
				break;
			}
			if (brickCount == 0) {
				winner();
				winClip.play();
				break;
			}
			pause(10);
		}
	}

	/*
	 * if collides with north, west and east walls -> bounces, else drops down and
	 * loses HP
	 */
	private void collideWithWall() {
		for (int i = 0; i < balls.size(); i++) {
			GOval ball = balls.get(i);
			double vy = speedsY.get(i);
			double vx = speedsX.get(i);
			if (ball.getY() <= 0)
				vy = -vy;
			if (ball.getX() < 0)
				vx = -vx;
			if (ball.getX() + BALL_RADIUS * 2 > WIDTH)
				vx = -vx;
			speedsX.set(i, vx);
			speedsY.set(i, vy);
			/* when ball goes past canvas it gets removed from everywhere */
			if (ball.getY() + BALL_RADIUS * 2 >= getHeight()) {
				remove(ball);
				balls.remove(i);
				speedsX.remove(i);
				speedsY.remove(i);
			}
		}
		if (balls.size() == 0) {
			HP -= 1;
			if (HP == 2)
				remove(gimage2);
			if (HP == 1)
				remove(gimage1);
			if (HP == 0)
				remove(gimage);
			if (HP != 0) {
				addBall();
				waitForClick();
			}
		}
	}

	/*
	 * if collided with brick, reduces the brick count, removes the brick and
	 * bounces in the opposite side, if collided with racket bounces back
	 */
	private void collideWithBrick(boolean georgianMarch) {
		boolean oneCollision = false; // tracks if one of the balls encounter a collision
		for (int n = 0; n < balls.size(); n++) {
			GOval ball = balls.get(n);
			double vy = speedsY.get(n);
			double vx = speedsX.get(n);
			GObject collider = getCollidingObject(ball);
			if (collider != null && collider != racket && collider != frame && collider != frame2
					&& collider != points) {
				pointCounter += 50;
				if (pointCounter != 0 && pointCounter != 5000 && pointCounter % BALL_ADDITION_POINTS == 0) {
					addBall();
					racket.sendToFront();
				}
				racketResize();
				points.setLabel(String.valueOf(pointCounter)); // updates the score
				remove(collider);
				if (ball.getY() - BALL_RADIUS * 2 <= collider.getY()) {
					vy = -vy;
				}
				brickCount--;
				ball.setColor(collider.getColor());
				oneCollision = true;
			} else if (collider == racket) {
				ball.setLocation(ball.getX(), racket.getY() - BALL_RADIUS * 2);
				vx = reflection(vx, ball);
				vy = -vy;
				oneCollision = true;
			}
			speedsY.set(n, vy);
			speedsX.set(n, vx);
		}
		if (oneCollision) {
			bounceClip.play(); // plays the audio when collides
			for (int i = 0; i < bricks.size(); i++) {
				if (!georgianMarch) {
					bricks.get(i).setColor(rgen.nextColor());
				}
			}
		}
	}

	/* makes the racket smaller through the game */
	private void racketResize() {
		if (pointCounter == LEVEL_ONE)
			racket.setSize(50, PADDLE_HEIGHT);
		if (pointCounter == LEVEL_TWO)
			racket.setSize(40, PADDLE_HEIGHT);
		if (pointCounter == LEVEL_THREE)
			racket.setSize(30, PADDLE_HEIGHT);
	}

	/*
	 * fixing the reflection algorithm, instead of randomising the direction, user
	 * can aim now
	 */
	private double reflection(double vx, GOval ball) {
		double surface = ball.getX() - (racket.getX() + 30);
		if (surface <= -30)
			vx = -3;
		else if (surface <= -15)
			vx = -1.5;
		else if (surface == 0)
			vx = -vx;
		else if (surface <= 15)
			vx = 1.5;
		else if (surface <= 30)
			vx = 3;
		return vx;
	}

	/*
	 * returns the object which the ball collided with
	 */
	private GObject getCollidingObject(GOval ball) {
		GObject object = null;
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			object = getElementAt(ball.getX(), ball.getY());
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
		} else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			object = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
		}
		if (object != null && object.getClass() != GOval.class) {
			return object;
		}
		return null;
	}

}