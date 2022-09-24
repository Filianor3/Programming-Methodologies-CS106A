/*
 * File: NameSurferConstants.java
 * ------------------------------
 * This file declares several constants that are shared by the
 * different modules in the NameSurfer application.  Any class
 * that implements this interface can use these constants.
 */

public interface NameSurferConstants {

	/** The width of the application window */
	public static final int APPLICATION_WIDTH = 800;

	/** The height of the application window */
	public static final int APPLICATION_HEIGHT = 600;

	/** The name of the file containing the data */
	public static final String NAMES_DATA_FILE = "names-data.txt";

	/** The first decade in the database */
	public static final int START_DECADE = 1900;

	/** The number of decades */
	public static final int NDECADES = 11;

	/** The maximum rank in the database */
	public static final int MAX_RANK = 1000;

	/** The number of pixels to reserve at the top and bottom */
	public static final int GRAPH_MARGIN_SIZE = 20;

	/* The size of the name textield of the GUI */
	public static final int NAME_FIELD_SIZE = 18;
	
	/* the pixel difference between line and the decade indicator year */
	public static final int LINE_YEAR_OFFSET = 5;
	
	/* the offset from bottom to the Y where the year should be written */
	public static final int YEAR_OFFSET = 2;
}
