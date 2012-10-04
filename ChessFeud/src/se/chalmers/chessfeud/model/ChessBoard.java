package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.NoPiece;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.util.Log;

/**
 * A class for representing a chessboard.
 * @author grubla
 *
 * Copyright � 2012 Henrik Alburg  
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
		board[oldPos.getX()][oldPos.getY()] = new NoPiece();
	}
	/**
	 * Creates a chess board from the given string.
	 * The string should be on the following format:
	 * Every letter symbolises a position and the order of chars is the pieces
	 * starting position. The char's int is the position where the top left tower stands on 0
	 * and the black pawns from 8-15 and the white pawns on 48-55.
	 * This is the string for the starting board: <------------------------------------>
	 * @param s
	 */
	public ChessBoard(String s){
		
		PieceFactory[] pf = {new PieceFactory(C.TEAM_WHITE), new PieceFactory(C.TEAM_BLACK)};
		board = new Piece[8][8];
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++){
				int team = ((int)s.charAt(8*x+y)) % 2;
				int id = ((int)s.charAt(8*x+y)) - team;
				board[x][y] = pf[team].createPiece(id);
			}
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
				if(board[x][y] == null)
					board[x][y] = new NoPiece();
	}
	/**
	 * Returns a string representing a board.
	 * The String holds the position of every Piece where a pos is between 0-63 and 65 is removed.
	 * 
	 * @return
	 */
	public String exportBoard(){
		char c = (char)65;
		StringBuilder export = new StringBuilder();
		for(int i = 0; i < 64; i++)
			export.append(c);
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++){
				export.append((char)getPieceAt(x, y).getId()+getPieceAt(x, y).getTeam());
			}
		return export.toString();	
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
		board[oldPos.getX()][oldPos.getY()] = new NoPiece();
		return piece;
	}
	
	/**
	 * Check whether a position is empty or not
	 * @param pos, the pos to be checked
	 * @return true if their is no piece at the given pos
	 */
	public boolean isEmpty(Position pos) {
		return getPieceAt(pos) instanceof NoPiece;
	}
	
}
