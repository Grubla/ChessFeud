package se.chalmers.chessfeud.model;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.constants.Game;
import se.chalmers.chessfeud.model.pieces.NoPiece;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
/**
 * A class that implements the model of a chessgame
 * @author grubla
 *
 * Copyright � 2012 Henrik Alburg, Arvid Karlsson
 *
 */
public class ChessModel {
	private ChessBoard chessBoard;
	private int numberOfMoves;
	private Position selected;
	private List<Position> possibleMoves;
	private List<Piece> takenPieces;
	private int state;
	private final int thisPlayer;
	private Game gameInfo;
	private PropertyChangeListener listener;
	
	/**
	 * Creates an instance of chess with a new starting board. 
	 */
	public ChessModel(int thisPlayer){
		gameInfo = null;
		chessBoard = new ChessBoard();
		numberOfMoves = 0;
		selected = null;
		this.thisPlayer = thisPlayer;
		possibleMoves = new LinkedList<Position>();
		takenPieces = new ArrayList<Piece>();
		state = C.STATE_NORMAL;
	}
	
	/**
	 * Creates the model from the same way the exportModel() exports.
	 * It is saved like this: "gameboard/turns" where gameboard is represented
	 * like in the GameBoard(String s) constructor.
	 * @param s
	 */
	public ChessModel(Game gameInfo, PropertyChangeListener pcl) {
		this.gameInfo = gameInfo;
		chessBoard = new ChessBoard(gameInfo.getGameBoard());
		this.numberOfMoves = gameInfo.getTurns();
		this.thisPlayer = 0; ////////////////////////////////////FIX
		selected = null;
		possibleMoves = new LinkedList<Position>();
		takenPieces = new ArrayList<Piece>();
		this.listener = pcl;
		checkState();
	}

	public String exportModel(){
		StringBuilder sb = new StringBuilder();
		sb.append(chessBoard.exportBoard());
		sb.append("/");
		sb.append(""+numberOfMoves);
		return sb.toString();
	}
	
	/**
	 * Represents a click on the board, the first click being choosing a piece and the second where it should go
	 * @param p, the position clicked
	 */
	public void click(Position p){
		if(selected != null){ //Some piece is selected
			if(possibleMoves.contains(p)){ //A valid move has been clicked
				Piece pi = chessBoard.movePiece(selected, p);
				deselectPiece();
				if(!(pi instanceof NoPiece)){
					takenPieces.add(pi);
				}
				changeTurn();
				if(Rules.isCheck(chessBoard, activePlayer()))
					if(Rules.isCheckMate(chessBoard, activePlayer())){
						setState(C.STATE_VICTORY_WHITE + activePlayer());
					}else{
						setState(C.STATE_CHECK);
					}
				else if(Rules.isDraw(chessBoard, activePlayer())){
						setState(C.STATE_DRAW);	
				}else{ //Next Turn
					sendModel();
				}
						
					
				//Check for check draw etc
				//Next turn or gameOver
			}else{ //Clicked on a place the piece cannot go
				if(chessBoard.getPieceAt(p).getTeam() == activePlayer()){ 
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
			if(chessBoard.getPieceAt(p) != null && chessBoard.getPieceAt(p).getTeam() == activePlayer()){ //Clicked on a new valid piece
				setSelected(p);
			}
		}
	}
	/*
	 * Returns the possible moves (according to ALL rules) for the chosen piece.
	 * @param pos, the position of the chosen piece
	 */
	private void setPossibleMoves(){
		possibleMoves.clear();
		possibleMoves = Rules.getPossibleMoves(chessBoard, selected);
	}
	/**
	 * Returns the possible moves in a list och positions.
	 */
	public List<Position> getPossibleMoves(){
		return possibleMoves;
	}
	
	/**
	 * Returns the piece at the given position
	 * @param p, a position on the board 0 <= x,y < 8 
	 * @return a Piece object
	 */
	public Piece getPieceAt(Position p){
		return chessBoard.getPieceAt(p.getX(), p.getY());
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
		setPossibleMoves();
	}
	
	/* Deselcts the current selected piece */
	private void deselectPiece(){
		selected = null;
		possibleMoves.clear();
	}
	
	/**
	 * Returns the status of the game, one of the following;
	 * C.STATE_NORMAL, C.STATE_CHECK, C.STATE_CHECKMATE, C.STATE_DRAW
	 * @return a status from the class C.
	 */
	public int getState(){
		return this.state;
	}
	/* Manually checks and updates the state.
	 * Also all the elses are needed to make sure it will only get into on if/else.*/
	private void checkState(){
		if(Rules.isCheck(chessBoard, activePlayer())){
			if(Rules.isCheckMate(chessBoard, nextTurn())){
				if(activePlayer() == C.TEAM_WHITE){
					setState(C.STATE_VICTORY_WHITE);
				}else{
					setState(C.STATE_VICTORY_BLACK);
				}
			}else{
				setState(C.STATE_CHECK);
			}
		}else{
				if(Rules.isDraw(chessBoard, nextTurn())){
					setState(C.STATE_DRAW);
				}else{
					setState(C.STATE_NORMAL);
				}
		}
	}
	
	/* Sets the state of the game */
	private void setState(int newState){
		this.state = newState;
	}
	
	/**
	 * Return the selected position or null if not any selected
	 * @return
	 */
	public Position getSelectedPosition(){
		return selected;
	}
	
	/* Changes the turn (the actve player) */
	private void changeTurn(){
		numberOfMoves++;
	}
	
	/**
	 * Returns the active player where 0 is "white" and 1 is "black"
	 * @return the player who's turn it is to move
	 */
	public int activePlayer(){
		int activePlayer = numberOfMoves%2;
		if(activePlayer == this.thisPlayer)
			return activePlayer;
		return -1;
	}
	
	private int nextTurn(){
		int next = numberOfMoves%2 == 0 ? 1 : 0;
		return next;
	}
	
	private void sendModel(){
		PropertyChangeEvent event = new PropertyChangeEvent(this, "Model", gameInfo, this.exportModel());
		this.listener.propertyChange(event);
	}
	
}
