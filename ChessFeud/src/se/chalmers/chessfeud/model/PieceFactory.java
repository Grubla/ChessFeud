package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.*;

/**
 * A class to create pieces. The factory pattern is adapted to make an effetive
 * class to easy create new pieces.
 * 
 * @author Arvid Karlsson
 * 
 *         Copyright (c) 2012 Arvid Karlsson
 */

public class PieceFactory {

	private int team;

	public PieceFactory(int team) {
		this.team = team;
	}

	/**
	 * Creates new pieces and returns them.
	 * 
	 * @param id
	 *            the id for which piece that shall be created
	 * @return A new piece with the given team and id.
	 */
	public Piece createPiece(int id) {
		return createPiece(this.team, id);
	}

	/**
	 * Creates a copy of the piece that's sent in.
	 * 
	 * @param p
	 *            the piece that shall be cloned
	 * @return a new piece that's a copy of the other piece.
	 */
	public static Piece createNewPiece(Piece p) {
		return createPiece(p.getTeam(), p.getId());
	}
	
	/*
	 * The factory that creates and returns new pieces depending on what comes in
	 */
	private static Piece createPiece(int team, int id) {
		Piece p;
		switch (id) {
		case C.PIECE_BISHOP:
			p = new Bishop(team);
			break;
		case C.PIECE_KING:
			p = new King(team);
			break;
		case C.PIECE_KNIGHT:
			p = new Knight(team);
			break;
		case C.PIECE_PAWN:
			p = new Pawn(team);
			break;
		case C.PIECE_QUEEN:
			p = new Queen(team);
		case C.PIECE_ROOK:
			p = new Rook(team);
			break;
		default:
			p = new NoPiece();
		}
		return p;
	}
}
