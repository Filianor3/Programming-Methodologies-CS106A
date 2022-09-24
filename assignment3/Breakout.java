
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

import java.applet.*;
import java.awt.*;
import java.awt.event.*;

public class Breakout extends GraphicsProgram {

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

	/* velocity of the ball */
	private double vx, vy;

	private GRect racket;
	private GOval ball;
	private RandomGenerator rgen = RandomGenerator.getInstance();
	private int HP = NTURNS;
	private int brickCount = NBRICKS_PER_ROW * NBRICK_ROWS;

	/* Method: run() */
	/** Runs the Breakout program. */

	public void run() {
		addBlocks();
		addRacket();
		addBall();
	}

	public void init() {
		addMouseListeners();
	}

	/* draws the blocks on the board, 2 lines each of 5 colour */
	private void addBlocks() {
		int tempX = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
		int tempY = BRICK_Y_OFFSET;
		int n = 0;
		Color currColor = null;
		while (n < 10) {
			if (n < 2)
				currColor = Color.RED;
			else if (n < 4)
				currColor = Color.ORANGE;
			else if (n < 6)
				currColor = Color.YELLOW;
			else if (n < 8)
				currColor = Color.GREEN;
			else
				currColor = Color.CYAN;
			for (int i = 0; i < NBRICK_ROWS; i++) {
				GRect rect = new GRect(BRICK_WIDTH, BRICK_HEIGHT);
				add(rect, tempX, tempY);
				rect.setFilled(true);
				rect.setColor(currColor);
				tempX += BRICK_WIDTH + BRICK_SEP;
			}
			n++;
			tempX = (WIDTH - (NBRICKS_PER_ROW * BRICK_WIDTH) - ((NBRICKS_PER_ROW - 1) * BRICK_SEP)) / 2;
			tempY = tempY + BRICK_HEIGHT + BRICK_SEP;
		}
	}

	/* draws the racket on the board */
	private void addRacket() {
		int tempX = WIDTH / 2 - PADDLE_WIDTH / 2;
		int tempY = getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT);
		racket = new GRect(PADDLE_WIDTH, PADDLE_HEIGHT);
		add(racket, tempX, tempY);
		racket.setFilled(true);
	}

	/* controlling the movement of the racket */
	public void mouseMoved(MouseEvent e) {
		if (e.getX() < WIDTH - PADDLE_WIDTH && e.getX() > 0)
			racket.setLocation(e.getX(), getHeight() - (PADDLE_Y_OFFSET + PADDLE_HEIGHT));
	}

	/*
	 * add balls to the board and gives it random velocity between given ranges and
	 * this method also indicates when the ball should be removed (if the HP bar
	 * drops to zero)
	 */
	private void addBall() {
		ball = new GOval(WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
		add(ball);
		ball.setFilled(true);
		vy = 3;
		vx = rgen.nextDouble(1.0, 3.0);
		if (rgen.nextBoolean(0.5))
			vx = -vx;
		while (true) {
			ball.move(vx, vy);
			pause(10);
			collideWithWall();
			collideWithBrick();
			if (HP == 0) {
				remove(ball);
				loser();
				break;
			}
			if (brickCount == 0) {
				winner();
				break;
			}
		}
	}

	/*
	 * if collides with north, west and east walls -> bounces, else drops down and
	 * loses HP
	 */
	private void collideWithWall() {
		if (ball.getY() <= 0) {
			vy = -vy;
		}
		if (ball.getX() <= 0) {
			vx = -vx;
		}
		if (ball.getX() + BALL_RADIUS * 2 > getWidth()) {
			vx = -vx;
		}
		if (ball.getY() + BALL_RADIUS * 2 > getHeight()) {
			HP -= 1;
			if (HP != 0) {
				ball.setLocation(WIDTH / 2 - BALL_RADIUS, getHeight() / 2 - BALL_RADIUS);
			}
		}
	}

	/*
	 * if collided with brick, reduces the brick count, removes the brick and
	 * bounces in the opposite side, if collided with racket bounces back
	 */
	private void collideWithBrick() {
		GObject collider = getCollidingObject();
		if (collider != null && collider != racket) {
			remove(collider);
			vy = -vy;
			brickCount--;
		} else if (collider == racket) {
			ball.setLocation(ball.getX(), racket.getY() - BALL_RADIUS * 2);
			vy = -vy;
		}
	}

	/* returns the object which the ball collided with */
	private GObject getCollidingObject() {
		GObject object = null;
		if (getElementAt(ball.getX(), ball.getY()) != null) {
			object = getElementAt(ball.getX(), ball.getY());
			return object;
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY()) != null) {
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY());
			return object;
		} else if (getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS) != null) {
			object = getElementAt(ball.getX(), ball.getY() + 2 * BALL_RADIUS);
			return object;
		} else if (getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS) != null) {
			object = getElementAt(ball.getX() + 2 * BALL_RADIUS, ball.getY() + 2 * BALL_RADIUS);
			return object;
		}
		return null;
	}

	/* what happens on win screen */
	private void winner() {
		GLabel winner = new GLabel("YOU WIN ! POG", getWidth() / 2 - 160, getHeight() / 2 - 20);
		add(winner);
		winner.setFont("Sylfaen-bold-40");
	}

	/* what happens on lose screen */
	private void loser() {
		GLabel loser = new GLabel("YOU LOSE", getWidth() / 2 - 130, getHeight() / 2 - 20);
		add(loser);
		loser.setFont("Sylfaen-bold-50");
	}

}
