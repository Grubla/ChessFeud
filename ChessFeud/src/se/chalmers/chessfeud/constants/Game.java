package se.chalmers.chessfeud.constants;
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
	
	private String timestamp;
	/**
	 * Sets all the values depending on what the string says.
	 * @param info
	 */
	public Game(String info) {
		String[] s = info.split("/");
		whitePlayer = s[0];
		blackPlayer = s[1];
		gameBoard = s[2];
		turns = Integer.parseInt(s[3]);
		timestamp = s[4];
		
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
	
}
