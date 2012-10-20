package se.chalmers.chessfeud.model;

import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Knight;
import se.chalmers.chessfeud.model.pieces.NoPiece;
import se.chalmers.chessfeud.model.pieces.Pawn;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * A class for managing the Rules in a chessgame.
 * 
 * @author Henrik Alburg
 * 
 *         Copyright � 2012 Henrik Alburg, Arvid Karlsson
 * 
 */
public class Rules {

	private static final int[] HORSE_X = { -2, -1, 1, 2, 2, 1, -1, -2 };
	private static final int[] HORSE_Y = { 1, 2, 2, 1, -1, -2, -2, -1 };

	/* The start board for a regular chess game */
	private static final Piece[][] START_BOARD = {
			{ new Rook(C.TEAM_BLACK), new Knight(C.TEAM_BLACK),
					new Bishop(C.TEAM_BLACK), new Queen(C.TEAM_BLACK),
					new King(C.TEAM_BLACK), new Bishop(C.TEAM_BLACK),
					new Knight(C.TEAM_BLACK), new Rook(C.TEAM_BLACK) },
			{ new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK),
					new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK),
					new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK),
					new Pawn(C.TEAM_BLACK), new Pawn(C.TEAM_BLACK) },
			{ new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(),
					new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece() },
			{ new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(),
					new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece() },
			{ new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(),
					new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece() },
			{ new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece(),
					new NoPiece(), new NoPiece(), new NoPiece(), new NoPiece() },
			{ new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE),
					new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE),
					new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE),
					new Pawn(C.TEAM_WHITE), new Pawn(C.TEAM_WHITE) },
			{ new Rook(C.TEAM_WHITE), new Knight(C.TEAM_WHITE),
					new Bishop(C.TEAM_WHITE), new Queen(C.TEAM_WHITE),
					new King(C.TEAM_WHITE), new Bishop(C.TEAM_WHITE),
					new Knight(C.TEAM_WHITE), new Rook(C.TEAM_WHITE) } };

	/**
	 * Returns the piece at the given position when a game of chess is
	 * initiated. The black Rook is on (0,0) The white king is on (4,7)
	 * 
	 * @param x
	 *            , the x position of the piece.
	 * @param y
	 *            , the y position of the piece
	 * @return a new Piece-object, at the start position.
	 */
	public static Piece startBoard(int x, int y) {
		return START_BOARD[y][x];
	}

	/**
	 * Returns true if the board is in an state that makes the game over.
	 * 
	 * @param cb
	 *            , the current chess board to be checked
	 * @return true if game over.
	 */
	public boolean gameOver(ChessBoard cb) {
		// To be fixed
		return false;
	}

	/**
	 * Returns true if the current board is in a check state.
	 * 
	 * @param cb
	 *            , the board to be checked
	 * @param team
	 *            , the currentPlayer (C.TEAM_WHITE)
	 * @return true if it is check
	 */
	public static boolean isCheck(ChessBoard cb, int team) {
		for (int x = 0; x < cb.getWidth(); x++) {
			for (int y = 0; y < cb.getHeight(); y++) {
				Piece kingPiece = cb.getPieceAt(x, y);
				if (kingPiece.getId() == C.PIECE_KING
						&& kingPiece.getTeam() == team) {
					if (isCheck(cb, team, x, y)) {
						return true;
					}
				}
			}
		}
		return false;

	}

	/* Checks if the given king is check */
	private static boolean isCheck(ChessBoard cb, int team, int kingX, int kingY) {
		for (int dx = -1; dx <= 1; dx++) {
			for (int dy = -1; dy <= 1; dy++) {
				if (inBounds(kingX + dx, kingY + dy) && !(dx == 0 && dy == 0)) {
					checkDirection(cb, team, kingX, kingY, dx, dy);
				}
			}
		}
		if (doHorsesCheck(cb, team, kingX, kingY)) {
			return true;
		}
		if (doPawnsCheck(cb, team, kingX, kingY)) {
			return true;
		}
		return false;
	}
	
	/* Checks if it is check in different directions from the king */
	private static boolean checkDirection(ChessBoard cb, int team, int kingX,
			int kingY, int dx, int dy) {
		int dir = 1;
		while (inBounds(kingX + dx * dir, kingY + dy * dir)
				&& cb.isEmpty(new Position(kingX + dx * dir, kingY + dy * dir))) {
			dir++;
		}
		if (inBounds(kingX + dx * dir, kingY + dy * dir)) {
			Piece pi = cb.getPieceAt(kingX + dx * dir, kingY + dy * dir);
			if (Math.abs(dx * dy) == 0
					&& (pi.getId() == C.PIECE_QUEEN || pi.getId() == C.PIECE_ROOK)
					&& pi.getTeam() != team) {
				return true;
			}
			if (Math.abs(dx * dy) == 1
					&& (pi.getId() == C.PIECE_QUEEN || pi.getId() == C.PIECE_BISHOP)
					&& pi.getTeam() != team) {
				return true;
			}
			if (dir == 1 && pi.getId() == C.PIECE_KING && pi.getTeam() != team) {
				return true;
			}
		}
		return false;
	}
	/* Checks if a horse is checking the king */
	private static boolean doHorsesCheck(ChessBoard cb, int team, int kingX,
			int kingY) {
		for (int i = 0; i < HORSE_X.length; i++) {
			int dx = HORSE_X[i];
			int dy = HORSE_Y[i];
			if (inBounds(kingX + dx, kingY + dy)
					&& cb.getPieceAt(kingX + dx, kingY + dy).getId() == C.PIECE_KNIGHT
					&& cb.getPieceAt(kingX + dx, kingY + dy).getTeam() != team) {
				return true;
			}
		}
		return false;
	}
	/* Check if any pawns checks the king */
	private static boolean doPawnsCheck(ChessBoard cb, int team, int kingX,
			int kingY) {
		int forward = team == C.TEAM_WHITE ? -1 : 1;
		if (inBounds(kingX + 1, kingY + forward)
				&& cb.getPieceAt(kingX + 1, kingY + forward).getId() == C.PIECE_PAWN
				&& cb.getPieceAt(kingX + 1, kingY + forward).getTeam() != team) {
			return true;
		}
		if (inBounds(kingX - 1, kingY + forward)
				&& cb.getPieceAt(kingX - 1, kingY + forward).getId() == C.PIECE_PAWN
				&& cb.getPieceAt(kingX - 1, kingY + forward).getTeam() != team) {
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the game is in a draw state.
	 * 
	 * @param cb, the board to be checked.
	 * @param nextTurn, who is to move next time
	 * @return true if the game is in a draw state.
	 */
	public static boolean isDraw(ChessBoard cb, int nextTurn) {
		if (!isCheck(cb, nextTurn)) {
			for (int x = 0; x < cb.getWidth(); x++) {
				for (int y = 0; y < cb.getHeight(); y++) {
					if (!cb.isEmpty(new Position(x, y))
							&& cb.getPieceAt(x, y).getTeam() == nextTurn) {
						if (isPossibleToMove(cb, x, y)) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns true if the game is over and someone has won.
	 * 
	 * @param cb, the board to be checked.
	 * @param nextTurn, the player to move next time
	 * @return true if it is check mate.
	 */
	public static boolean isCheckMate(ChessBoard cb, int nextTurn) {
		if (isCheck(cb, nextTurn)) {
			for (int x = 0; x < cb.getWidth(); x++) {
				for (int y = 0; y < cb.getHeight(); y++) {
					if (cb.getPieceAt(x, y) != null
							&& cb.getPieceAt(x, y).getTeam() == nextTurn) {
						if (isPossibleToMove(cb, x, y)) {
							return false;
						}
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Returns a list of possible moves for a certain piece. This will return a
	 * list of position of the possible moves.
	 * 
	 * @param cb, the board it is in.
	 * @param selected, the selected position of the piece
	 * @return a list of positions which it can go to.
	 */
	public static List<Position> getPossibleMoves(ChessBoard cb,
			Position selected) {
		List<Position> pm = new LinkedList<Position>();
		Piece piece = cb.getPieceAt(selected);
		List<List<Position>> tempMoves = piece.theoreticalMoves(selected);
		int team = cb.getPieceAt(selected).getTeam();
		if (piece.getId() != C.PIECE_PAWN) {
			for (List<Position> l : tempMoves) {
				boolean canMove = true;
				for (Position p : l) {
					ChessBoard tmpBoard = new ChessBoard(cb, selected, p);
					if (canMove && !Rules.isCheck(tmpBoard, team)) {
						if (cb.isEmpty(p)) {
							pm.add(p);
						} else {
							if (team != cb.getPieceAt(p).getTeam()) {
								pm.add(p);
							}
							canMove = false;
						}
					} else {
						if (!cb.isEmpty(p)) {
							canMove = false;
						}
					}
				}
			}
		} else { // ID == PAWN
			int dy = piece.getTeam() == C.TEAM_WHITE ? -1 : 1;
			int startY = piece.getTeam() == C.TEAM_WHITE ? C.STARTING_POSITION_BLACK_PAWN
					: 1;
			Position tryPos = new Position(selected.getX(), selected.getY()
					+ dy);
			ChessBoard tmpBoard = new ChessBoard(cb, selected, tryPos);
			if (inBounds(tryPos) && cb.isEmpty(tryPos)
					&& !isCheck(tmpBoard, team)) {
				pm.add(tryPos);
				tryPos = new Position(selected.getX(), selected.getY() + 2 * dy);
				tmpBoard = new ChessBoard(cb, selected, tryPos);
				if (inBounds(tryPos) && cb.isEmpty(tryPos)
						&& selected.getY() == startY
						&& !isCheck(tmpBoard, team)) {
					pm.add(tryPos);
				}
			}
			// Check if there is pieces to take diagonally forward
			tryPos = new Position(selected.getX() - 1, selected.getY() + dy);
			if (inBounds(tryPos)) {
				tmpBoard = new ChessBoard(cb, selected, tryPos);
				if (!cb.isEmpty(tryPos)
						&& cb.getPieceAt(tryPos).getTeam() != piece.getTeam()
						&& !isCheck(tmpBoard, team)) {
					pm.add(tryPos);
				}
			}
			tryPos = new Position(selected.getX() + 1, selected.getY() + dy);
			if (inBounds(tryPos)) {
				tmpBoard = new ChessBoard(cb, selected, tryPos);
				if (!cb.isEmpty(tryPos)
						&& cb.getPieceAt(tryPos).getTeam() != piece.getTeam()
						&& !isCheck(tmpBoard, team)) {
					pm.add(tryPos);
				}
			}
		}
		return pm;
	}

	/* Returns true if any piece on the board can moce for the currrent team */
	private static boolean isPossibleToMove(ChessBoard cb, int x, int y) {
		return getPossibleMoves(cb, new Position(x, y)).size() > 0;
	}

	/* Returns true if the position is inbounds */
	private static boolean inBounds(int x, int y) {
		return 0 <= x && x < C.BOARD_LENGTH && 0 <= y && y < C.BOARD_LENGTH;
	}

	/* Returns true if the position is inbounds */
	private static boolean inBounds(Position p) {
		return 0 <= p.getX() && p.getX() < C.BOARD_LENGTH && 0 <= p.getY()
				&& p.getY() < C.BOARD_LENGTH;
	}
}
