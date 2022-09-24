
/*
 * File: NameSurferEntry.java
 * --------------------------
 * This class represents a single entry in the database.  Each
 * NameSurferEntry contains a name and a list giving the popularity
 * of that name for each decade stretching back to 1900.
 */

import java.util.*;

public class NameSurferEntryExtension implements NameSurferConstantsExtension {

	/* Constructor: NameSurferEntry(line) */
	/**
	 * Creates a new NameSurferEntry from a data line as it appears in the data
	 * file. Each line begins with the name, which is followed by integers giving
	 * the rank of that name for each decade.
	 */
	public NameSurferEntryExtension(String line) {
		currentName = line.substring(0, line.indexOf(','));
		ArrayList<Integer> currentList = new ArrayList<>();
		line = line.substring(line.indexOf(',') + 1);
		int currentInt = 0;
		for (int i = 0; i < NDECADES; i++) {
			for (int j = 0; j < line.length(); j++) {
				if (line.charAt(j) == ',') {
					currentInt = Integer.parseInt(line.substring(0, j));
					line = line.substring(line.indexOf(',') + 1);
					currentList.add(i, currentInt);
					break;
				}
			}
		}
		currentList.add(Integer.parseInt(line));
		data.put(currentName, currentList);
	}

	/* Method: getName() */
	/**
	 * Returns the name associated with this entry.
	 */
	public String getName() {
		return currentName;
	}

	/* Method: getRank(decade) */
	/**
	 * Returns the rank associated with an entry for a particular decade. The decade
	 * value is an integer indicating how many decades have passed since the first
	 * year in the database, which is given by the constant START_DECADE. If a name
	 * does not appear in a decade, the rank value is 0.
	 */
	public int getRank(int decade) {
		return data.get(getName()).get(decade);
	}

	/* Method: toString() */
	/**
	 * Returns a string that makes it easy to see the value of a NameSurferEntry.
	 */
	public String toString() {
		String toString = "";
		toString += currentName + ": [";
		for (int i = 0; i < 11; i++) {
			toString += data.get(currentName).get(i);
			toString += " ";
		}
		return toString + "]";
	}

	private Map<String, ArrayList<Integer>> data = new HashMap<String, ArrayList<Integer>>();
	String currentName = "";
}
