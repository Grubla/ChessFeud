package se.chalmers.chessfeud.constants;

/**
 * A class that contains all the constants. Makes it easier if we want to change
 * a number. Then we only need to change in one place.
 * 
 * 
 * @author Arvid Karlsson, Henrik Alburg, Sean Pavlov, Simon Almgren
 * 
 *         copyright (c) 2012 Arvid Karlsson, Henrik Alburg, Sean Pavlov, Simon
 *         Almgren
 * 
 */
public final class C {

	public static final int PIECE_PAWN = 8;
	public static final int PIECE_ROOK = 10;
	public static final int PIECE_BISHOP = 12;
	public static final int PIECE_KNIGHT = 14;
	public static final int PIECE_QUEEN = 16;
	public static final int PIECE_KING = 18;
	public static final int PIECE_NOPIECE = 25;

	public static final int SQUARE_WHITE = 0xFFFFCE9E;
	public static final int SQUARE_WHITE_SELECTED = 0xFFFFDE5F;
	public static final int SQUARE_WHITE_POSSIBLE_MOVES = 0xFFAAE25F;
	public static final int SQUARE_BLACK = 0xFFD18B47;
	public static final int SQUARE_BLACK_SELECTED = 0xFFE3B62B;
	public static final int SQUARE_BLACK_POSSIBLE_MOVES = 0xFF8EB92B;
	public static final int TEAM_WHITE = 0;
	public static final int TEAM_BLACK = 1;

	public static final int STATE_NORMAL = 20;
	public static final int STATE_CHECK = 21;
	public static final int STATE_VICTORY_WHITE = 22;
	public static final int STATE_VICTORY_BLACK = 23;
	public static final int STATE_DRAW = 24;
	
	public static final int STATE_TEXT_COLOR_GREEN = 0xFF00DD00;
	public static final int STATE_TEXT_COLOR_RED = 0xFFDD0000;
	public static final int STATE_TEXT_COLOR_GREY = 0xFF666666;

	public static final int BOARD_LENGTH = 8;
	public static final int STARTING_POSITION_BLACK_PAWN = 6;

	public static final int[] KNIGHT_X = { -2, -1, 1, 2, 2, 1, -1, -2 };
	public static final int[] KNIGHT_Y = { 1, 2, 2, 1, -1, -2, -2, -1 };

	public static final int PRIME = 31;

	public static final int PW_MIN_LENGHT = 4;

	public static final String FILENAME_SETTINGS = "chessfeud_settings";
	public static final String EXCEPTION_LOCATION_SETTINGS = "Settings";

	public static final String[] SETTINGS_NAME_LIST = { "Helptip", "Sound" };
	public static final int SETTINGS_HELPTIP = 0;
	public static final int SETTINGS_SOUND = 1;

	public static final int[] DAYS_PER_MONTH = { 31, 28, 31, 30, 31, 30, 31,
			31, 30, 31, 30, 31 };
	public static final int SECONDS_PER_MINUTE = 60;
	public static final int MINUTES_PER_HOUR = 60;
	public static final int HOURS_PER_DAY = 24;
	public static final int DAYS_PER_YEAR = 365;
	public static final int DAYS_PER_LEAPYEAR = 366;
	public static final int STARTING_YEAR = 1970;
	public static final int YEARS_BETWEEN_LEAPYEAR = 4;

	// An empty private constructor to hide utility class constructor
	private C() {

	}

}
