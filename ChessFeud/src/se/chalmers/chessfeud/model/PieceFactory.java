package se.chalmers.chessfeud.model;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.*;

public class PieceFactory {
	
	private int team;
	
	public PieceFactory(int team){
		this.team = team;
	}
	
	public Piece createPiece(int id){
		Piece p = null;
		switch(id){
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
				
		} 
		return p;
	}
}
