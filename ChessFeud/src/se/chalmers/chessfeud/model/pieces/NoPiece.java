package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

/**
 * A class that marks that there's no piece at the current square. This "piece"
 * doesn't have any team and only functions as a representation of an empty
 * square.
 * 
 * @author Henrik Alburg Arvid Karlsson
 * 
 *         Copyright (c) 2012 Henrik Alburg, Arvid Karlsson
 */

public class NoPiece extends Piece {

	/**
	 * Creates a new no piece with the team -1. This is to fill the board with
	 * pieces, even where there is no pieces. The team -1 showt that this piece
	 * is neither black or white.
	 * 
	 */
	public NoPiece() {
		super(-1, C.PIECE_NOPIECE);
	}

	/**
	 * Returns a list that doesn't contains any position. This method only exist
	 * so the NoPiece class can be a child class to position.
	 * 
	 * @param p
	 *            the position where the NoPiece is.
	 * @return posList An emty list.
	 */
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		return new ArrayList<List<Position>>();
	}

	@Override
	public String toString() {
		return "Piece: NoPiece, Team: -1";
	}

}
