package se.chalmers.chessfeud.model;

import java.util.ArrayList;
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
	private List<Piece> takenPieces;
	
	/**
	 * Creates an instance of chess with a new starting board. 
	 */
	public ChessModel(){
		chessBoard = new ChessBoard();
		activePlayer = 0;
		selected = null;
		possibleMoves = new LinkedList<Position>();
		takenPieces = new ArrayList<Piece>();
	}
	
	/**
	 * Represents a click on the board, the first click being choosing a piece and the second where it should go
	 * @param p, the position clicked
	 */
	public void click(Position p){
		if(selected != null){ //Some piece is selected
			if(possibleMoves.contains(p)){ //A valid move has been clicked
				if(true){ //Change to ID > 0 <------------------------------------------------------------------------------- !!!!
					takenPieces.add(chessBoard.getPieceAt(p));
				}
				
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
				ChessBoard tmpBoard = new ChessBoard(chessBoard, selected, p);
				if(canMove && Rules.isCheck(tmpBoard, selected)){
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
	/**
	 * Returns a list with all the taken pieces
	 * @return
	 */
	public List<Piece> getTakenPieces(){
		return takenPieces;
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
