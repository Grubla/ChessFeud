package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;

public class ChessModel {
	private ChessBoard chessBoard;
	private int activePlayer;
	private Position selected;
	
	public ChessModel(){
		chessBoard = new ChessBoard();
		activePlayer = 0;
		selected = null;
	}
	
	public void click(Position p){
		//Selected?
	}
	
	public int acitvePlayer(){
		return activePlayer;
	}
	
	public Piece getPieceAt(Position p){
		return null;
	}
	
	
	private void setSelected(Position p){
		selected = p;
	}
	
	private void changeTurn(){
		activePlayer = (activePlayer+1)%2;
	}

	private boolean tryMove(Position oldPos, Position newPos){
	return false;	
	}
	
}
