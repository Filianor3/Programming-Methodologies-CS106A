
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

public class NameSurfer extends Program implements NameSurferConstants {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		nameSurferDataBase = new NameSurferDataBase(NAMES_DATA_FILE);
		graph = new NameSurferGraph();
		add(graph);
		name = new JLabel("Name");
		nameField = new JTextField(NAME_FIELD_SIZE);
		nameField.addActionListener(this);
		nameField.setActionCommand("Graph");
		graphButton = new JButton("Graph");
		clear = new JButton("Clear");
		add(name, SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(clear, SOUTH);
		addActionListeners();
	}

	/* Method: actionPerformed(e) */
	/**
	 * This class is responsible for detecting when the buttons are clicked, so you
	 * will have to define a method to respond to button actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object clicked = e.getSource();
		if (clicked == graphButton || e.getActionCommand().equals("Graph")) {
			NameSurferEntry entry = nameSurferDataBase.findEntry(nameField.getText());
			if (entry != null) {
				graph.addEntry(entry);
				graph.update();
			}
		}
		if (clicked == clear) {
			graph.clear();
		}

	}

	private JLabel name;
	private JTextField nameField;
	private JButton graphButton;
	private JButton clear;
	private NameSurferGraph graph;
	private NameSurferDataBase nameSurferDataBase;
}
