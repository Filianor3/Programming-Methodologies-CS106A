import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

/*
 * File: NameSurferDataBase.java
 * -----------------------------
 * This class keeps track of the complete database of names.
 * The constructor reads in the database from a file, and
 * the only public method makes it possible to look up a
 * name and get back the corresponding NameSurferEntry.
 * Names are matched independent of case, so that "Eric"
 * and "ERIC" are the same names.
 */

public class NameSurferDataBase implements NameSurferConstants {

	/* Constructor: NameSurferDataBase(filename) */
	/**
	 * Creates a new NameSurferDataBase and initializes it using the data in the
	 * specified file. The constructor throws an error exception if the requested
	 * file does not exist or if an error occurs as the file is being read.
	 */
	public NameSurferDataBase(String filename) {
		try {
			BufferedReader br = new BufferedReader(new FileReader(NAMES_DATA_FILE));
			while (true) {
				String currentLine = br.readLine();
				if (currentLine == null)
					break;
				NameSurferEntry currentEntry = new NameSurferEntry(currentLine);
				dataBase.put(currentEntry.getName(), currentEntry);
			}
			br.close();
		} catch (Exception e) {

		}
	}

	/* Method: findEntry(name) */
	/**
	 * Returns the NameSurferEntry associated with this name, if one exists. If the
	 * name does not appear in the database, this method returns null.
	 */
	public NameSurferEntry findEntry(String name) {
		if (name.length() != 1)
			name = Character.toUpperCase(name.charAt(0)) + name.substring(1).toLowerCase();
		else
			name = Character.toUpperCase(name.charAt(0)) + "";

		if (dataBase.containsKey(name))
			return dataBase.get(name);
		return null;
	}

	private Map<String, NameSurferEntry> dataBase = new HashMap<String, NameSurferEntry>();
}
