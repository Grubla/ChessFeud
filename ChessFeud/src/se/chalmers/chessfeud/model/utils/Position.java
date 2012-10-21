package se.chalmers.chessfeud.model.utils;

import se.chalmers.chessfeud.constants.C;

/**
 * A class representing 2D positions.
 * The positions basically only have x and y values,
 * though they are adapted to the chess game by adding 
 * methods to check if the positions are inbounds.
 * 
 * @author Arvid modifiedby Henrik Alburg
 * 
 * 
 *         Copyright © 2012 Arvid Karlsson, Henrik Alburg
 */

public class Position {
	private int x, y;

	/**
	 * Creates a new position with the given x and y values.
	 * 
	 * @param x
	 *            The x value of the position
	 * @param y
	 *            The y value of the position
	 */
	public Position(int x, int y) {
		this.x = x;
		this.y = y;
	}

	/**
	 * Creates a new position with the x and y values from the given position.
	 * @param p
	 * 			The position to copy. 
	 */
	public Position(Position p) {
		this.x = p.x;
		this.y = p.y;
	}

	/**
	 * A function to get the x-value from a position.
	 * 
	 * @return the x-value in the position
	 */
	public int getX() {
		return this.x;
	}

	/**
	 * A function to get the y-value from a position.
	 * 
	 * @return the y-value in the position.
	 */
	public int getY() {
		return this.y;
	}

	/**
	 * A function to check if the position is inside of the board.
	 * 
	 * @return true if the position is inside the board, otherwise false.
	 */
	public boolean inBounds() {
		return (0 <= this.getX() && this.getX() < C.BOARD_LENGTH
				&& 0 <= this.getY() && this.getY() <= C.BOARD_LENGTH);
	}

	/**
	 * A function to check if two coordinates is inside of the board.
	 * 
	 * @param x
	 *            the x-value of the position.
	 * @param y
	 *            the y-value of the position.
	 * @return true if the position is inside the board, otherwise false.
	 */
	public static boolean inBounds(int x, int y) {
		return (0 <= x && x < C.BOARD_LENGTH && 0 <= y && y < C.BOARD_LENGTH);
	}

	@Override
	public int hashCode() {
		int result = 1;
		result = C.PRIME * result + x;
		result = C.PRIME * result + y;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		Position other = (Position) obj;
		if (x != other.x) {
			return false;
		}
		if (y != other.y) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "x: " + this.x + " y: " + this.y;
	}
}
