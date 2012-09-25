package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;

public class ChessBoard {
	private Piece[][] board;
	
	public ChessBoard(){
		board = new Piece[8][8];
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
				board[x][y] = Rules.startBoard(x, y);
	}
	
	public ChessBoard(ChessBoard cb, Position oldPos, Position newPos){
		board = new Piece[8][8];
		for(int x = 0; x < board.length; x++)
			for(int y = 0; y < board[x].length; y++)
				board[x][y] = cb.getPieceAt(x, y);
		board[newPos.getX()][newPos.getY()] = board[oldPos.getX()][oldPos.getY()];
		board[oldPos.getX()][oldPos.getY()] = null;
	}
	
	public Piece getPieceAt(Position p){
		return getPieceAt(p.getX(), p.getY());
	}
	
	public Piece getPieceAt(int x, int y){
			return board[x][y];
	}
	
	public int width(){
		return board.length;
	}
	
	public int height(){
		return board[0].length;
	}
	
}
