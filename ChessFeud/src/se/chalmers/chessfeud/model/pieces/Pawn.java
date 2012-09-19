package se.chalmers.chessfeud.model.pieces;

import se.chalmers.chessfeud.model.utils.Position;


/**
 * The piece pawn
 * @author Arvid
 *
 */

//Även kallad Bonde
public class Pawn extends Piece{
	
	private boolean hasMoved = false;
	
	protected Pawn(int team, Position p) {
		super(team, p);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Position[] canMove() {
		if(this.getTeam() == 0)
			if(hasMoved = false)
				
				
		
		return null;
	}

}
