package se.chalmers.chessfeud.model;

public class ChessModel {
	private ChessBoard chessBoard;
	private int turn;
	private Position selected;
	
	public ChessModel(){
		chessBoard = new ChessBoard();
		turn = 0;
		selected = null;
	}
	
	public void click(Position p){
		//Selected?
	}
	
	public int acitvePlayer(){
		return turn;
	}
	
	public Piece getPieceAt(Position p){
		return null;
	}
	
	
	private void setSelected(Position p){
		selected = p;
	}
	
	private void changeTurn(){
		turn = (turn+1)%2;
	}

	private boolean tryMove(Position oldPos, Position newPos){
	return false;	
	}
	
}
