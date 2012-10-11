package se.chalmers.chessfeud.constants;

import java.beans.PropertyChangeListener;

/**
 * A class representing an ongoing game.
 * @author twister
 *
 */
public class Game {
	
	private String whitePlayer;
	private String blackPlayer;
	
	private String gameBoard;
	
	private int turns;
	private int position;
	
	PropertyChangeListener listener;
	
	private String timestamp;
	/**
	 * Sets all the values depending on what the string says.
	 * @param info
	 */
	public Game(String info, int position) {
		String[] s = info.split("/");
		whitePlayer = s[0];
		blackPlayer = s[1];
		gameBoard = s[2];
		turns = Integer.parseInt(s[3]);
		timestamp = s[4];
		this.position = position;
		
	}
	/**
	 * Returns a string with the name of the player playing white.
	 * @return
	 */
	public String getWhitePlayer() {
		return whitePlayer;
	}
	/**
	 * Returns a string with the name of the player playing black.
	 * @return
	 */
	public String getBlackPlayer() {
		return blackPlayer;
	}
	/**
	 * Returns a string with the name of the player who's current turn it is.
	 * @return
	 */
	public String getCurrentPlayer() {
		if(turns%2 == 0) {
			return whitePlayer;
		} else {
			return blackPlayer;
		}
	}
	/**
	 * Returns the total number of turns taken this game.
	 * @return
	 */
	public int getTurns() {
		return turns;
	}
	/**
	 * Returns a string representing the gameboard.
	 * @return
	 */
	public String getGameBoard() {
		return gameBoard;
	}
	/**
	 * Returns a timestamp on when the newest move was made.
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}
	/**
	 * Returns the color of the current player.
	 * @return
	 */
	public int getCurrentColor() {
		if(turns%2 == 0) {
			return C.TEAM_WHITE;
		} else {
			return C.TEAM_BLACK;
		}
	}
	/**
	 * Returns the position (or id) for this game in the list.
	 */
	public int getPosition(){
		return this.position;
	}
	
}
