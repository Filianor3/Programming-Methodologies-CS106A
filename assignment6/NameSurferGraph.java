
/*
 * File: NameSurferGraph.java
 * ---------------------------
 * This class represents the canvas on which the graph of
 * names is drawn. This class is responsible for updating
 * (redrawing) the graphs whenever the list of entries changes or the window is resized.
 */

import acm.graphics.*;
import java.awt.event.*;
import java.util.*;
import java.awt.*;

public class NameSurferGraph extends GCanvas implements NameSurferConstants, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraph() {
		addComponentListener(this);
	}

	/* Draws the background which is common for all the stages of the application */
	private void initialiseBackground() {
		double gap = getWidth() / NDECADES;
		double currentX = 0;
		int currentYear = START_DECADE;
		GLine upperLine = new GLine(0, GRAPH_MARGIN_SIZE, getWidth(), GRAPH_MARGIN_SIZE);
		GLine lowerLine = new GLine(0, getHeight() - GRAPH_MARGIN_SIZE, getWidth(), getHeight() - GRAPH_MARGIN_SIZE);
		add(upperLine);
		add(lowerLine);
		for (int i = 0; i < NDECADES; i++) {
			GLine column = new GLine(currentX, 0, currentX, getHeight());
			GLabel year = new GLabel(Integer.toString(currentYear));
			add(year, currentX + LINE_YEAR_OFFSET, getHeight() - YEAR_OFFSET);
			add(column);
			currentX += gap;
			currentYear += 10;
		}
	}

	/*
	 * Draws all of the names that have been written in the textfield, this method
	 * also indicates the color of the current name
	 */
	private void drawAll() {
		int counter = 0;
		for (String key : dataBase.keySet()) {
			if (counter % 4 == 0)
				color = Color.BLACK;
			else if (counter % 4 == 1)
				color = Color.RED;
			else if (counter % 4 == 2)
				color = Color.BLUE;
			else if (counter % 4 == 3)
				color = Color.YELLOW;
			oneGuy(key, color);
			counter++;
		}
	}

	/* Draws the graph and staticis for the recently added guy */
	private void oneGuy(String key, Color color) {
		double currentX = 0;
		double currentY = 0;
		double nextY = 0;
		double gap = getWidth() / NDECADES;
		for (int i = 0; i < NDECADES - 1; i++) {
			int currentRank = dataBase.get(key).get(i);
			int nextRank = dataBase.get(key).get(i + 1);
			if (currentRank == 0) {
				currentY = (getHeight() - GRAPH_MARGIN_SIZE);
			} else {
				currentY = (getHeight() - GRAPH_MARGIN_SIZE * 2) * currentRank / MAX_RANK + GRAPH_MARGIN_SIZE;
			}
			if (nextRank == 0) {
				nextY = (getHeight() - GRAPH_MARGIN_SIZE);
			} else {
				nextY = (getHeight() - GRAPH_MARGIN_SIZE * 2) * nextRank / MAX_RANK + GRAPH_MARGIN_SIZE;
			}
			GLine line = new GLine(currentX, currentY, currentX + gap, nextY);
			add(line);
			drawName(currentX, currentY, currentRank, key, color);
			line.setColor(color);
			currentX += gap;
		}
		// off by one
		if (dataBase.get(key).get(10) == 0) {
			currentY = (getHeight() - GRAPH_MARGIN_SIZE);
		} else {
			currentY = (getHeight() - GRAPH_MARGIN_SIZE * 2) * dataBase.get(key).get(10) / MAX_RANK + GRAPH_MARGIN_SIZE;
		}
		drawName(currentX, currentY, dataBase.get(key).get(10), key, color);
	}

	/* adds the single GLabel for the recently added name */
	private void drawName(double X, double Y, int rank, String name, Color color) {
		if (rank != 0) {
			GLabel label = new GLabel(name + " " + rank);
			add(label, X, Y);
			label.setColor(color);
		} else {
			GLabel label = new GLabel(name + " " + "*");
			add(label, X, Y);
			label.setColor(color);
		}
	}

	/**
	 * Clears the list of name surfer entries stored inside this class.
	 */
	public void clear() {
		removeAll();
		initialiseBackground();
		dataBase.clear();
	}

	/* Method: addEntry(entry) */
	/**
	 * Adds a new NameSurferEntry to the list of entries on the display. Note that
	 * this method does not actually draw the graph, but simply stores the entry;
	 * the graph is dran by calling update.
	 */
	public void addEntry(NameSurferEntry entry) {
		ArrayList<Integer> currentList = new ArrayList<>();
		for (int i = 0; i < NDECADES; i++) {
			currentList.add(entry.getRank(i));
		}
		dataBase.put(entry.getName(), currentList);
	}

	/**
	 * Updates the display image by deleting all the graphical objects from the
	 * canvas and then reassembling the display according to the list of entries.
	 * Your application must call update after calling either clear or addEntry;
	 * update is also called whenever the size of the canvas changes.
	 */
	public void update() {
		removeAll();
		initialiseBackground();
		drawAll();
	}

	/* Implementation of the ComponentListener interface */
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		update();
	}

	public void componentShown(ComponentEvent e) {
	}

	private Map<String, ArrayList<Integer>> dataBase = new LinkedHashMap<String, ArrayList<Integer>>();
	private Color color;

}
