package se.chalmers.chessfeud.test;


import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;


public class modelTest extends AndroidTestCase {



	
	public void testGame(){
		/* This is an actual chess game i set up to test different parts of the game. */
		Position[] pos = {new Position(4, 6), new Position(4, 4), new Position(4,1), new Position(4,3), 
				new Position(6,7), new Position(5,5), new Position(5,0), new Position(4,1), new Position(5,5), 
				new Position(4,3), new Position(3,1), new Position(3,3), new Position(4,4), new Position(3,3), 
				new Position(1,0), new Position(0,2), new Position(4,3), new Position(2,4), new Position(0,2), 
				new Position(1,4), new Position(3,7), new Position(4,6), new Position(1,4), new Position(2,6),
				new Position(4,7), new Position(3,7), new Position(2,0), new Position(3,1), new Position(4,6), 
				new Position(7,3), new Position(2,6), new Position(4,5), new Position(5,6), new Position(4,5),
				new Position(0,1), new Position(0,3), new Position(2,4), new Position(4,3), new Position(1,1), 
				new Position(1,2), new Position(7,3), new Position(5,1) };
		ChessModel cm = new ChessModel();
		for(int i = 0; i < pos.length; i++){
			cm.click(pos[i]);
			if(i == 11)
				assertTrue(cm.getTakenPieces().size() == 1);
			if(i == 13)
				assertTrue(cm.getTakenPieces().size() == 2);
			if(i == 21){
				cm.click(new Position(4,1));
				assertTrue(cm.getPossibleMoves().size() == 0);
				cm.click(new Position(4,1));
			}
			if(i == 23){
				assertTrue(cm.getTakenPieces().size() == 3);
				assertTrue(cm.getState() == C.STATE_CHECK);
				//Only king can move
			}
			if(i == 31){
				//Only 36, 37, 56 can move
			}
			if(i == 42){
				assertTrue(cm.getState() == C.STATE_VICTORY_WHITE);
			}
				
		}
	}
	
}
