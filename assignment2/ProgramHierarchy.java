
/*
 * File: ProgramHierarchy.java
 * Name: 
 * Section Leader: 
 * ---------------------------
 * This file is the starter file for the ProgramHierarchy problem.
 */

import acm.graphics.*;
import acm.program.*;
import java.awt.*;

public class ProgramHierarchy extends GraphicsProgram {

	/* the width of the box */
	private static final int BOX_WIDTH = 140;

	/* the height of the box */
	private static final int BOX_HEIGHT = 40;

	/* the gap between the subordinate boxes */
	private static final int BOX_GAP = 20;

	/* describing all the objects that will be placed on the canvas */
	private GRect programRect;
	private GRect dialogRect;
	private GRect graphicsRect;
	private GRect consoleRect;
	private GLine programToGraphics;
	private GLine programToConsole;
	private GLine programToDialog;
	private GLabel program;
	private GLabel gProgram;
	private GLabel cProgram;
	private GLabel dProgram;

	public void run() {
		drawProgramBox();
		labelProgram();
		drawGraphicsBox();
		labelGraphics();
		drawConsoleBox();
		labelConsole();
		drawDialogBox();
		labelDialog();
		drawLines();
	}

	/* adds the text to program box */
	private void labelProgram() {
		program = new GLabel("Program");
		add(program, programRect.getX() + BOX_WIDTH / 2.0 - program.getWidth() / 2,
				programRect.getY() + BOX_HEIGHT / 2.0 + program.getDescent() / 2.0);
	}

	/* adds the text to graphics box */
	private void labelGraphics() {
		gProgram = new GLabel("GraphcisProgram");
		add(gProgram, graphicsRect.getX() + BOX_WIDTH / 2.0 - gProgram.getWidth() / 2,
				graphicsRect.getY() + BOX_HEIGHT / 2.0 + gProgram.getDescent() / 2.0);
	}

	/* adds the text to console box */
	private void labelConsole() {
		cProgram = new GLabel("ConsoleProgram");
		add(cProgram, consoleRect.getX() + BOX_WIDTH / 2.0 - cProgram.getWidth() / 2,
				consoleRect.getY() + BOX_HEIGHT / 2.0 + cProgram.getDescent() / 2.0);
	}

	/* adds the text to dialog box */
	private void labelDialog() {
		dProgram = new GLabel("DialogProgram");
		add(dProgram, dialogRect.getX() + BOX_WIDTH / 2.0 - dProgram.getWidth() / 2,
				dialogRect.getY() + BOX_HEIGHT / 2.0 + dProgram.getDescent() / 2.0);
	}

	/* Draws the rectangle of the program Box */
	private void drawProgramBox() {
		double programRectX = getWidth() / 2 - BOX_WIDTH / 2;
		double programRectY = getHeight() / 2 - BOX_HEIGHT * 2;
		programRect = new GRect(programRectX, programRectY, BOX_WIDTH, BOX_HEIGHT);
		add(programRect);
	}

	/* Draws the rectangle of the graphics Box */
	private void drawGraphicsBox() {
		double graphicsRectX = getWidth() / 2 - BOX_WIDTH / 2 - BOX_GAP - BOX_WIDTH;
		double graphicsRectY = getHeight() / 2;
		graphicsRect = new GRect(graphicsRectX, graphicsRectY, BOX_WIDTH, BOX_HEIGHT);
		add(graphicsRect);
	}

	/* Draws the rectangle of the console Box */
	private void drawConsoleBox() {
		double consoleRectX = getWidth() / 2 - BOX_WIDTH / 2;
		double consoleRectY = getHeight() / 2;
		consoleRect = new GRect(consoleRectX, consoleRectY, BOX_WIDTH, BOX_HEIGHT);
		add(consoleRect);
	}

	/* Draws the rectangle of the dialog Box */
	private void drawDialogBox() {
		double dialogRectX = getWidth() / 2 + BOX_WIDTH / 2 + BOX_GAP;
		double dialogRectY = getHeight() / 2;
		dialogRect = new GRect(dialogRectX, dialogRectY, BOX_WIDTH, BOX_HEIGHT);
		add(dialogRect);
	}

	/* Draws all the necessary lines that connect the Boxes */
	private void drawLines() {
		double botXProgram = getWidth() / 2;
		double botYProgram = getHeight() / 2 - BOX_HEIGHT;

		double topXGraphics = getWidth() / 2 - BOX_WIDTH / 2 - BOX_GAP - BOX_WIDTH / 2;
		double topYGraphics = getHeight() / 2;

		double topXConsole = getWidth() / 2;
		double topYConsole = getHeight() / 2;

		double topXDialog = getWidth() / 2 + BOX_WIDTH / 2 + BOX_GAP + BOX_WIDTH / 2;
		double topYDialog = getHeight() / 2;

		programToGraphics = new GLine(botXProgram, botYProgram, topXGraphics, topYGraphics);
		add(programToGraphics);

		programToConsole = new GLine(botXProgram, botYProgram, topXConsole, topYConsole);
		add(programToConsole);

		programToDialog = new GLine(botXProgram, botYProgram, topXDialog, topYDialog);
		add(programToDialog);
	}
}
