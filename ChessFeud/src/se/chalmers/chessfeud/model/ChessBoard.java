package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * A class for representing a chessboard.
 * @author grubla
 *
 */
public class ChessBoard {
	private Piece[][] board;
	
	/**
	 * Creates a board with the starting pieces at the right places.
	 */
	public ChessBoard(){
		board = new Piece[8][8];
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
				board[x][y] = Rules.startBoard(x, y);
	}
	/**
	 * Creates a clone of board given with the piece from oldPos moved to newPos
	 * @param cb, the old board given
	 * @param oldPos, the position of the piece that should be moved
	 * @param newPos, where the piece will be moved
	 */
	public ChessBoard(ChessBoard cb, Position oldPos, Position newPos){
		board = new Piece[8][8];
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
				board[x][y] = cb.getPieceAt(x, y);
		board[newPos.getX()][newPos.getY()] = board[oldPos.getX()][oldPos.getY()];
		board[oldPos.getX()][oldPos.getY()] = null;
	}
	/**
	 * Returns the piece at the given position
	 * @param p, the given position 0 <= x, y < 8
	 * @return a piece object
	 */
	public Piece getPieceAt(Position p){
		return getPieceAt(p.getX(), p.getY());
	}
	/**
	 * Returns the piece at the given position
	 * @param x, the positions x value, 0 <= x < 8
	 * @param y, the positions y value, 0 <= y < 8
	 * @return a piece objet from the position
	 */
	public Piece getPieceAt(int x, int y){
			return board[x][y];
	}
	/**
	 * Returns the width of the chessboard
	 */
	public int getWidth(){
		return board.length;
	}
	/**
	 * Returns the height of the chessboard
	 */
	public int getHeight(){
		return board[0].length;
	}
	
	/**
	 * Moves a piece from oldPos to newPos
	 * @param oldPos, the current position of the piece
	 * @param newPos, the new position of the piece
	 * @return, the former piece at newPos
	 */
	public Piece movePiece(Position oldPos, Position newPos){
		Piece piece = getPieceAt(newPos);
		board[newPos.getX()][newPos.getY()] = getPieceAt(oldPos);
		board[oldPos.getX()][oldPos.getY()] = null;
		return piece;
	}
	
	/* Returns true if there is no piece at the given position */
	public boolean isEmpty(Position pos) {
		return getPieceAt(pos) == null;
	}
	
}
