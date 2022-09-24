
/* 
 * File: FacePamphlet.java
 * -----------------------
 * When it is finished, this program will implement a basic social network
 * management system.
 */

import acm.program.*;
import acm.graphics.*;
import acm.util.*;
import java.awt.event.*;
import javax.swing.*;

public class FacePamphlet extends Program implements FacePamphletConstants {

	/**
	 * This method has the responsibility for initializing the interactors in the
	 * application, and taking care of any other initialization that needs to be
	 * performed.
	 */
	public void init() {
		initEverything();
		addEverything();
		addActionListeners();
		data = new FacePamphletDatabase();
		board = new FacePamphletCanvas();
		add(board);
		currentUser = null;
	}

	/**
	 * This class is responsible for detecting when the buttons are clicked or
	 * interactors are used, so you will have to add code to respond to these
	 * actions.
	 */
	public void actionPerformed(ActionEvent e) {
		Object clicked = e.getSource(); // setting an object to the recent textfield or button that was called
		// making sure that the input of status, image or friend name is not empty to
		// avoid nullpointer or just empty status
		if (clicked == addUser && !nameInput.getText().equals("")) {
			addingUser();
		}

		if (clicked == deleteUser && !nameInput.getText().equals("")) {
			deleteUser();
		}

		if (clicked == lookupUser && !nameInput.getText().equals("")) {
			lookupUser();
		}

		if ((clicked == changeStatus || clicked == status) && !status.getText().equals("")) {
			changeStatus();
		}

		if ((clicked == changeImage || clicked == image) && !image.getText().equals("")) {
			changeImage();
		}

		if ((clicked == addFriendB || clicked == addFriend) && !addFriend.getText().equals("")) {
			addFriend();
		}

	}

	/*
	 * adds friend to the current profile active, if current profile is null, then
	 * showMessage method is called and it tells us to select a profile to which we
	 * want to add friend to
	 */
	private void addFriend() {
		if (!(currentUser == null) && !(currentUser.getName().equals(addFriend.getText()))) {
			if (data.containsProfile(addFriend.getText())) {
				currentUser.addFriend(addFriend.getText());
				FacePamphletProfile tempUser = data.getProfile(addFriend.getText());
				tempUser.addFriend(currentUser.getName());
				board.displayProfile(currentUser);
				board.showMessage(tempUser.getName() + " added as a friend");
			} else {
				board.displayProfile(currentUser);
				board.showMessage("No such profile exists.");
			}
		} else {
			board.displayProfile(currentUser);
			board.showMessage("Please select a profile to add friend to");
		}
	}

	/*
	 * when the user types name of the image file if such file can be found in the
	 * system its updated to the profile picture, else it just throws Error
	 * Exception
	 */
	private void changeImage() {
		GImage GIimage = null;
		try {
			GIimage = new GImage("images/" + image.getText());
		} catch (ErrorException ex) {
			System.out.println(ex);
		}
		if (!(currentUser == null)) {
			currentUser.setImage(GIimage);
			board.displayProfile(currentUser);
			board.showMessage("Profile picture updated");
		} else {
			board.displayProfile(currentUser);
			board.showMessage("Please select a profile to set Image to");
		}

	}

	/* changes the status of the user with certain input */
	private void changeStatus() {
		if (!(currentUser == null)) {
			currentUser.setStatus(status.getText());
			board.displayProfile(currentUser);
			board.showMessage("Status updated to " + status.getText());
		} else {
			board.displayProfile(currentUser);
			board.showMessage("Please select a profile to set status to");
		}
	}

	/*
	 * when the add button is pressed this method is called and if there is no
	 * profile with the certain name already here, new pamphlet profile object is
	 * created and is being added to the database with custom status, image and
	 * empty friendlist
	 */
	private void addingUser() {
		if (!data.containsProfile(nameInput.getText())) {
			currentUser = new FacePamphletProfile(nameInput.getText());
			data.addProfile(currentUser);
			board.displayProfile(currentUser);
			board.showMessage("New Profile Created");
		} else {
			currentUser = data.getProfile(nameInput.getText());
			board.displayProfile(currentUser);
			board.showMessage("Profile with this name already exists");
		}
	}

	/*
	 * if user exists in the database their entry/object is removed from the base
	 */
	private void deleteUser() {
		if (data.containsProfile(nameInput.getText())) {
			currentUser = data.getProfile(nameInput.getText());
			data.deleteProfile(nameInput.getText());
			currentUser = null;
			board.displayProfile(currentUser);
			board.showMessage("The profile of " + nameInput.getText() + " was deleted.");
		} else {
			board.displayProfile(currentUser);
			board.showMessage("No such user was found");
		}
	}

	/*
	 * if profile with such name exist in our database it is looked up then
	 * displayed and currentprofile becomes the profile displayed, if no profile
	 * exists with this name, certain message is displayed
	 */
	private void lookupUser() {
		if (data.containsProfile(nameInput.getText())) {
			currentUser = data.getProfile(nameInput.getText());
			board.displayProfile(currentUser);
			board.showMessage("Displaying " + currentUser.getName());
		} else {
			currentUser = null;
			board.displayProfile(currentUser);
			board.showMessage("A Profile with name " + nameInput.getText() + " doesn't exist.");
		}
	}

	/* initialising every J Object in the program */
	private void initEverything() {
		name = new JLabel("Name");
		nameInput = new JTextField(TEXT_FIELD_SIZE);
		addUser = new JButton("Add");
		deleteUser = new JButton("Delete");
		lookupUser = new JButton("Lookup");
		status = new JTextField(TEXT_FIELD_SIZE);
		status.addActionListener(this);
		changeStatus = new JButton("Change Status");
		image = new JTextField(TEXT_FIELD_SIZE);
		image.addActionListener(this);
		changeImage = new JButton("Change Picture");
		addFriend = new JTextField(TEXT_FIELD_SIZE);
		addFriend.addActionListener(this);
		addFriendB = new JButton("Add Friend");
		empty1 = new JLabel(EMPTY_LABEL_TEXT);
		empty2 = new JLabel(EMPTY_LABEL_TEXT);
	}

	/* adds all the J Objects in the program */
	private void addEverything() {
		add(name, NORTH);
		add(nameInput, NORTH);
		add(addUser, NORTH);
		add(deleteUser, NORTH);
		add(lookupUser, NORTH);
		add(status, WEST);
		add(changeStatus, WEST);
		add(empty1, WEST);
		add(image, WEST);
		add(changeImage, WEST);
		add(empty2, WEST);
		add(addFriend, WEST);
		add(addFriendB, WEST);
	}

	private FacePamphletProfile currentUser;
	private FacePamphletDatabase data;
	private FacePamphletCanvas board;
	private JLabel name;
	private JTextField nameInput;
	private JButton addUser;
	private JButton deleteUser;
	private JButton lookupUser;
	private JTextField status;
	private JButton changeStatus;
	private JTextField image;
	private JButton changeImage;
	private JTextField addFriend;
	private JButton addFriendB;
	private JLabel empty1;
	private JLabel empty2;
}
