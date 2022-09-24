
/*
 * File: NameSurfer.java
 * ---------------------
 * When it is finished, this program will implements the viewer for
 * the baby-name database described in the assignment handout.
 */

import acm.program.*;
import java.awt.event.*;
import javax.swing.*;

@SuppressWarnings("serial")
public class NameSurferExtension extends Program implements NameSurferConstantsExtension {

	/* Method: init() */
	/**
	 * This method has the responsibility for reading in the data base and
	 * initializing the interactors at the bottom of the window.
	 */
	public void init() {
		nameSurferDataBaseExtension = new NameSurferDataBaseExtension(NAMES_DATA_FILE);
		graphExtension = new NameSurferGraphExtension();
		add(graphExtension);
		name = new JLabel("Name");
		nameField = new JTextField(NAME_FIELD_SIZE);
		nameField.addActionListener(this);
		nameField.setActionCommand("Graph");
		graphButton = new JButton("Graph");
		histogram = new JButton("Histogram");
		clear = new JButton("Clear");
		names = new JComboBox<String>();
		remove = new JButton("Remove");
		add(name, SOUTH);
		add(nameField, SOUTH);
		add(graphButton, SOUTH);
		add(histogram, SOUTH);
		add(clear, SOUTH);
		add(names, SOUTH);
		add(remove, SOUTH);
		welcome();
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
			NameSurferEntryExtension entry = nameSurferDataBaseExtension.findEntry(nameField.getText());
			if (entry != null) {
				graphExtension.addEntry(entry);
				boolean addOrNot = comboBoxContains(entry);
				if (addOrNot)
					names.addItem(entry.getName());
				graphExtension.update();
			} else if (entry == null) {
				noNameException();
			}
		}

		if (clicked == clear) {
			graphExtension.clear();
			names.removeAllItems();
		}

		if (clicked == remove && names.getSelectedItem() != null) {
			graphExtension.remove(names.getSelectedItem());
			names.removeItem(names.getSelectedItem());
			graphExtension.update();
		}

		if (clicked == histogram) {
			NameSurferEntryExtension entry = nameSurferDataBaseExtension.findEntry(nameField.getText());
			if (entry != null) {
				graphExtension.histogram(entry);
			}
		}

	}

	/*
	 * This method checks whether the input name is added to the combobox item list
	 * or not, such check is not necessary for adding things in hasmap because keys
	 * are unique
	 */
	private boolean comboBoxContains(NameSurferEntryExtension entry) {
		for (int i = 0; i < names.getItemCount(); i++) {
			if (names.getItemAt(i).equals(entry.getName())) {
				return false;
			}
		}
		return true;
	}

	/* Pops dialog box and adds it relative to the GCanvas */
	private void welcome() {
		JOptionPane.showMessageDialog(graphExtension, "Welcome to the Georgian NameSurfer");
		JOptionPane.showMessageDialog(graphExtension,
				"Please consider that information displayed here is mostly assumptions \n                   and aren't based on any specific data",
				"Welcome Message", JOptionPane.WARNING_MESSAGE);
	}

	private void noNameException() {
		JOptionPane.showMessageDialog(graphExtension, "No such name exists in our data", "No Name",
				JOptionPane.WARNING_MESSAGE);
	}

	private JLabel name;
	private JTextField nameField;
	private JButton graphButton;
	private JButton clear;
	private NameSurferGraphExtension graphExtension;
	private NameSurferDataBaseExtension nameSurferDataBaseExtension;
	private JComboBox<String> names;
	private JButton remove;
	private JButton histogram;
}
