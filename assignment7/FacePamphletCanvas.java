
/*
 * File: FacePamphletCanvas.java
 * -----------------------------
 * This class represents the canvas on which the profiles in the social
 * network are displayed.  NOTE: This class does NOT need to update the
 * display when the window is resized.
 */

import acm.graphics.*;
import java.awt.*;
import java.util.*;

public class FacePamphletCanvas extends GCanvas implements FacePamphletConstants {

	/**
	 * Constructor This method takes care of any initialization needed for the
	 * display
	 */
	public FacePamphletCanvas() {

	}

	/**
	 * This method displays a message string near the bottom of the canvas. Every
	 * time this method is called, the previously displayed message (if any) is
	 * replaced by the new message text passed in.
	 */
	public void showMessage(String msg) {
		GLabel label = new GLabel(msg);
		label.setFont(MESSAGE_FONT);
		add(label, getWidth() / 2 - label.getWidth() / 2, getHeight() - BOTTOM_MESSAGE_MARGIN);
	}

	/**
	 * This method displays the given profile on the canvas. The canvas is first
	 * cleared of all existing items (including messages displayed near the bottom
	 * of the screen) and then the given profile is displayed. The profile display
	 * includes the name of the user from the profile, the corresponding image (or
	 * an indication that an image does not exist), the status of the user, and a
	 * list of the user's friends in the social network.
	 */
	public void displayProfile(FacePamphletProfile profile) {
		// removes all the objects that were displayed on the canvas from the past times
		// when displayProfile was called
		removeAll();
		if (profile != null) {
			username(profile);
			userImage(profile);
			userStatus(profile);
			userFriends(profile);
		}
	}

	/* method which draws the friends and the friend GLabel on the canvas */
	private void userFriends(FacePamphletProfile profile) {
		double Y = 0;
		friends = new GLabel("Friends:");
		friends.setFont(PROFILE_FRIEND_LABEL_FONT);
		if (profile.getImage() != null) {
			add(friends, getWidth() / 2, img.getY() + friends.getAscent());
		} else {
			add(friends, getWidth() / 2, rect.getY() + friends.getAscent());
		}
		Y = friends.getY(); // getting the Y of the friends Label
		// getting the friend list so we can iterate thru it
		Iterator<String> it = profile.getFriends();
		while (it.hasNext()) {
			Y += friends.getHeight(); // updating the Y coordinate every time for the next existing friend in the list
			String currName = it.next();
			GLabel tempLabel = new GLabel(currName);
			tempLabel.setFont(PROFILE_FRIEND_FONT);
			add(tempLabel, friends.getX(), Y);
		}

	}

	/*
	 * draws the status of the user to the south of the profile picture of the user
	 */
	private void userStatus(FacePamphletProfile profile) {
		status = new GLabel(profile.getStatus());
		status.setFont(PROFILE_STATUS_FONT);
		if (profile.getImage() != null) {
			add(status, LEFT_MARGIN, img.getY() + img.getHeight() + STATUS_MARGIN + status.getAscent());
		} else {
			add(status, LEFT_MARGIN, rect.getY() + rect.getHeight() + STATUS_MARGIN + status.getAscent());
		}
	}

	/*
	 * sets the image of the user to GImage if such exists for the current profile,
	 * else just draws a rect and no image label inside it
	 */
	private void userImage(FacePamphletProfile profile) {
		if (profile.getImage() != null) {
			img = profile.getImage();
			img.setSize(IMAGE_WIDTH, IMAGE_HEIGHT);
			add(img, LEFT_MARGIN, name.getY() + IMAGE_MARGIN);
		} else {
			rect = new GRect(LEFT_MARGIN, name.getY() + IMAGE_MARGIN, IMAGE_WIDTH, IMAGE_HEIGHT);
			add(rect);
			noimg = new GLabel("No Image");
			noimg.setFont(PROFILE_IMAGE_FONT);
			add(noimg, rect.getX() + IMAGE_WIDTH / 2 - noimg.getWidth() / 2,
					rect.getY() + IMAGE_HEIGHT / 2 + noimg.getDescent() / 2);
		}
	}

	/* draws the name and surname of the user with blue color on the canvas */
	private void username(FacePamphletProfile profile) {
		name = new GLabel(profile.getName());
		name.setColor(Color.BLUE);
		name.setFont(PROFILE_NAME_FONT);
		add(name, LEFT_MARGIN, TOP_MARGIN + name.getAscent());
	}

	private GLabel name;
	private GImage img;
	private GRect rect;
	private GLabel noimg;
	private GLabel status;
	private GLabel friends;
}
