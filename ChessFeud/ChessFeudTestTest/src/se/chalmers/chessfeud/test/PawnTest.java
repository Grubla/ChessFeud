package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.model.pieces.Pawn;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class PawnTest extends AndroidTestCase {

	public void testPawn() {
		Piece whitePawn = new Pawn(0);
		Piece blackPawn = new Pawn(1);
		Position[] pos = { new Position(6, 6), new Position(1, 1) };
		for (int i = 0; i < pos.length; i++) {
			if (i == 0) { // Test the white pawn
				List<List<Position>> testList = whitePawn
						.theoreticalMoves(pos[i]);
				for (List<Position> l : testList) {
					for (Position p : l) {
						assertTrue(p.equals(new Position(5, 5))
								|| p.equals(new Position(6, 5))
								|| p.equals(new Position(7, 5))
								|| p.equals(new Position(6, 4)));
					}
				}
			}
			if (i == 1) { // Test the black pawn
				List<List<Position>> testList = blackPawn
						.theoreticalMoves(pos[i]);
				for (List<Position> l : testList) {
					for (Position p : l) {
						assertTrue(p.equals(new Position(0, 2))
								|| p.equals(new Position(1, 2))
								|| p.equals(new Position(2, 2))
								|| p.equals(new Position(1, 3)));
					}
				}
			}
		}
	}
}
