package se.chalmers.chessfeud.utils;


/**
 * A class representing an ongoing game. Will hold all the information needed by
 * both the model and the list objects.
 * 
 * @author Simon Almgren Henrik Alburg
 * 
 *         Copyright (c) 2012 Simon Almgren Henrik Alburg
 */
public class GameInfo {

	private String whitePlayer;
	private String blackPlayer;

	private String gameBoard;

	private int turns;
	
	private static PlayerInfo player = PlayerInfo.getInstance();

	private String timestamp;

	/**
	 * Sets all the values depending on what the string says.
	 * 
	 * @param info
	 *            A sting containing all the info for the game. should be on the
	 *            form "whiteplayer/blackplayer/gameboard/turns/timestamp"
	 * @param position
	 *            The position in the gameslist.
	 */
	public GameInfo(String info) {
		String[] s = info.split("/");
		whitePlayer = s[0];
		blackPlayer = s[1];
		gameBoard = s[2];
		turns = Integer.parseInt(s[3]);
		timestamp = s[4];
	}

	/**
	 * Returns a string with the name of the player playing white.
	 * 
	 * @return the username of the white player
	 */
	public String getWhitePlayer() {
		return whitePlayer;
	}

	/**
	 * Returns a string with the name of the player playing black.
	 * 
	 * @return the username of the black player
	 */
	public String getBlackPlayer() {
		return blackPlayer;
	}

	/**
	 * Returns a string with the name of the player who's current turn it is.
	 * 
	 * @return the username of the player whoms turn it is
	 */
	public String getCurrentPlayer() {
		if (turns % 2 == 0) {
			return whitePlayer;
		} else {
			return blackPlayer;
		}
	}

	/**
	 * A getter for the user name of the current client.
	 * 
	 * @return a string containing the username for this client
	 */
	public String thisPlayer() {
		return player.getUserName();
	}

	/**
	 * Returns the color (C.TEAM_WHITE or C.TEAM_BLACK) of this client.
	 * 
	 * @return an int containing the team
	 */
	public int thisPlayersTeam() {
		if (player.getUserName().equalsIgnoreCase(whitePlayer)) {
			return C.TEAM_WHITE;
		} else {
			return C.TEAM_BLACK;
		}
	}

	/**
	 * The username of the opponent in this game.
	 * 
	 * @return a string containing the username
	 */
	public String getOpponent() {
		if (player.getUserName().equalsIgnoreCase(whitePlayer)) {
			return blackPlayer;
		}
		return whitePlayer;
	}
	
	/**
	 * Returns true if it is this clients turn to move.
	 * @return true if my turn
	 */
	public boolean isMyTurn(){
		return thisPlayersTeam() == getCurrentColor();
	}

	/**
	 * Returns the total number of turns taken this game.
	 * 
	 * @return the number of turns
	 */
	public int getTurns() {
		return turns;
	}

	/**
	 * Returns a string representing the gameboard.
	 * 
	 * @return the string representation of the gameboard.
	 */
	public String getGameBoard() {
		return gameBoard;
	}

	/**
	 * Returns a timestamp on when the latest move was made.
	 * 
	 * @return the timestamp of the latest move "2012-10-20 13:37"
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * Returns the color of the current player.
	 * 
	 * @return the int team of this turn
	 */
	public int getCurrentColor() {
		if (turns % 2 == 0) {
			return C.TEAM_WHITE;
		} else {
			return C.TEAM_BLACK;
		}
	}

	@Override
	public String toString(){
		return whitePlayer+"/"+blackPlayer+"/"+gameBoard+"/"+turns+"/"+timestamp;
	}
}
