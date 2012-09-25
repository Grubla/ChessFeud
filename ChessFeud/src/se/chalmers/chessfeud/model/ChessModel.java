package se.chalmers.chessfeud.model;

import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;
/**
 * A class that implements the model of a chessgame
 * @author grubla
 *
 */
public class ChessModel {
	private ChessBoard chessBoard;
	private int activePlayer;
	private Position selected;
	private List<Position> possibleMoves;
	/**
	 * Creates an instance of chess with a new starting board. 
	 */
	public ChessModel(){
		chessBoard = new ChessBoard();
		activePlayer = 0;
		selected = null;
		possibleMoves = new LinkedList<Position>();
	}
	
	/**
	 * Represents a click on the board, the first click being choosing a piece and the second where it should go
	 * @param p, the position clicked
	 */
	public void click(Position p){
		if(selected != null){ //Some piece is selected
			if(possibleMoves.contains(p)){ //A valid move has been clicked
				//Save the newPos piece
				//Move the piece
				//Check for check draw etc
				//Next turn or gameOver
			}else{ //Clicked on a place the piece cannot go
				if(chessBoard.getPieceAt(p).getTeam() == activePlayer){ 
					if(p.equals(selected)){ //Clicked on the same piece twice
						deselectPiece();
					}else{ //Clicked on a new piece in its team
						setSelected(p);
					}
				}else{ //Clicked on an enemy piece where it cannot go
					deselectPiece();
				}
			}
		}else{ 
			if(chessBoard.getPieceAt(p).getTeam() == activePlayer){ //Clicked on a new valid piece
				setSelected(p);
			}
		}
	}
	/*
	 * Returns the possible moves (according to ALL rules) for the chosen piece.
	 * @param pos, the position of the chosen piece
	 */
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
	/* Returns true if the pieces at the given positions are on the same team */
	private boolean sameTeam(Position pos, Position p) {
		return chessBoard.getPieceAt(pos).getTeam() == chessBoard.getPieceAt(p).getTeam();
	}

	/* Returns true if there is no piece at the given position */
	private boolean isEmpty(Position pos) {
		return chessBoard.getPieceAt(pos) == null;
	}

	/* Returns true if the selected position -> pos results in a state that is not check */
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
	
	/**
	 * Returns the active player where 0 is "white" and 1 is "black"
	 * @return the player who's turn it is to move
	 */
	public int acitvePlayer(){
		return activePlayer;
	}
	
	/**
	 * Returns the piece at the given position
	 * @param p, a position on the board 0 <= x,y < 8 
	 * @return a Piece object
	 */
	public Piece getPieceAt(Position p){
		return null;
	}
	
	/* Sets the current position as the selected one */
	private void setSelected(Position p){
		selected = p;
		getPossibleMoves(selected);
	}
	
	/* Deselcts the current selected piece */
	private void deselectPiece(){
		selected = null;
	}
	
	/* Changes the turn (the actve player) */
	private void changeTurn(){
		activePlayer = (activePlayer+1)%2;
	}
	
}
