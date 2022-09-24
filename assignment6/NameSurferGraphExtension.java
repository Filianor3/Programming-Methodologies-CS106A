
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

import javax.swing.JFrame;

import java.awt.*;

public class NameSurferGraphExtension extends GCanvas implements NameSurferConstantsExtension, ComponentListener {

	/**
	 * Creates a new NameSurferGraph object that displays the data.
	 */
	public NameSurferGraphExtension() {
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
			line.setColor(color);
			add(line);
			line.setColor(color);
			drawName(currentX, currentY, currentRank, key, color);
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

	public void histogram(NameSurferEntryExtension entry) {
		createHistogramCanvas(entry);
		for (int i = 0; i < NDECADES; i++) {
			int currentRank = entry.getRank(i);
			if (currentRank == 0) {
				continue;
			}
			GRect rect = new GRect(20 + i * (50 + 25), (30 + currentRank), 50,
					(canvas.getHeight() - 60 - currentRank - 30));
			rect.setFilled(true);
			rect.setFillColor(Color.BLUE);
			canvas.add(rect);
		}

	}

	private void createHistogramCanvas(NameSurferEntryExtension entry) {
		JFrame frame = new JFrame("Histogram of " + entry.getName());
		frame.setSize(850, 850);
		frame.setLocationRelativeTo(this);
		canvas = new GCanvas();
		canvas.setSize(850, 850);
		frame.add(canvas);
		frame.setVisible(true);
		int currentYear = START_DECADE;
		for (int i = 0; i < NDECADES; i++) {
			GLabel year = new GLabel(Integer.toString(currentYear));
			year.setFont("-15");
			canvas.add(year, 25 + i * (50 + 25), canvas.getHeight() - 30);
			currentYear += 10;
		}
		// 12.5
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
	public void addEntry(NameSurferEntryExtension entry) {
		ArrayList<Integer> currentList = new ArrayList<>();
		for (int i = 0; i < NDECADES; i++) {
			currentList.add(entry.getRank(i));
		}
		dataBase.put(entry.getName(), currentList);
	}

	public void remove(Object object) {
		dataBase.remove(object);

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
	private GCanvas canvas;

}
