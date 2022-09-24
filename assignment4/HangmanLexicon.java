
/*
 * File: HangmanLexicon.java
 * -------------------------
 * This file contains a stub implementation of the HangmanLexicon
 * class that you will reimplement for Part III of the assignment.
 */

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;


public class HangmanLexicon {

	public int getWordCount() {
		return list.size();
	}

	public HangmanLexicon() {
		try {
			BufferedReader br = new BufferedReader(new FileReader("HangmanLexicon.txt"));
			while (true) {
				String line = br.readLine();
				if (line == null)
					break;
				list.add(line);
			}

		} catch (Exception e) {

		}

	}

	/** Returns the word at the specified index. */
	public String getWord(int index) {
		return list.get(index);
	}

	private ArrayList<String> list = new ArrayList<>();
}
