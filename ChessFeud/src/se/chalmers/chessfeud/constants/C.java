package se.chalmers.chessfeud.constants;

public class C {

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
	
	public static final int BOARD_LENGTH = 7;
}
