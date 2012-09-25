package se.chalmers.chessfeud.model;

import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;

public class ChessModel {
	private ChessBoard chessBoard;
	private int activePlayer;
	private Position selected;
	private List<Position> possibleMoves;
	
	public ChessModel(){
		chessBoard = new ChessBoard();
		activePlayer = 0;
		selected = null;
		possibleMoves = new LinkedList<Position>();
	}
	
	public void click(Position p){
		//Selected?
	}
	
	private void getPossibleMoves(Position pos){
		possibleMoves.clear();
		Piece piece = chessBoard.getPieceAt(pos);
		List<List<Position>> tempMoves = piece.canMove();
		for(List<Position> l : tempMoves){
			boolean canMove = true;
			for(Position p : l){
				if(canMove && notCheck(pos)){
					if(isEmpty(pos))
						possibleMoves.add(pos);
					else{
						if(sameTeam(pos, p))
							possibleMoves.add(pos);
						canMove = false;
					}
				}
			}
		}
		
	}
	
	private boolean sameTeam(Position pos, Position p) {
		return chessBoard.getPieceAt(pos).getTeam() == chessBoard.getPieceAt(p).getTeam();
	}

	private boolean isEmpty(Position pos) {
		return chessBoard.getPieceAt(pos) == null;
	}

	private boolean notCheck(Position pos){
		ChessBoard tmpBoard = new ChessBoard(chessBoard, selected, pos);
		for(int x = 0; x < tmpBoard.width(); x++)
			for(int y = 0; y < tmpBoard.height(); y++){
				Piece piece = tmpBoard.getPieceAt(x, y);
				if(piece.getTeam() == activePlayer && piece instanceof King){
					for(int dx = -1; dx <= 1; dx++)
						for(int dy = -1; dy <= 1; dy++){
							if(0 <= x && x < 8 && 0 <= y && y < 8 && isEmpty(new Position(x+dx, y+dy))){
								int dir = 2;
								while(0 <= x && x < 8 && 0 <= y && y < 8 && isEmpty(new Position(x+dx*dir, y+dy*dir)) && !pos.equals(new Position(x,y))){
									dir++;
								}
								if(0 <= x && x < 8 && 0 <= y && y < 8 && !pos.equals(new Position(x,y))){
									Piece pi = tmpBoard.getPieceAt(x+dx*dir, y+dy*dir);
									if(Math.abs(dx*dy) == 0 && (pi instanceof Queen || pi instanceof Rook))
										return false;
									if(Math.abs(dx*dy) == 1 && (pi instanceof Queen || pi instanceof Bishop))
										return false;
								}
								
							}
								
						}
				}
			}
		return true;
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
