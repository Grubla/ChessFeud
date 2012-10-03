package se.chalmers.chessfeud.model.pieces;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.utils.Position;

public class NoPiece extends Piece{
	
	public NoPiece() {
		super(-1, C.PIECE_NOPIECE);
	}
	
	@Override
	public List<List<Position>> theoreticalMoves(Position p) {
		return new ArrayList<List<Position>>();
	}
	
	@Override
	public String toString(){
		return "Piece: NoPiece, Team: -1";
	}

}
