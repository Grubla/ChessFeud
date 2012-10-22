package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.King;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class KingTest extends AndroidTestCase {
	private Piece king = new King(C.TEAM_WHITE);

	public void testKing() {

		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = king.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						// Checks so the King position is inside the
						// gameboard
						assertTrue(!p.equals(new Position(8, 8)));
						assertTrue(!p.equals(new Position(7, 8)));
						assertTrue(!p.equals(new Position(6, 8)));
						// Checks so the King can't go to its own position
						assertTrue(!p.equals(new Position(7, 7)));
						// Checks so the King can't take more than one step
						assertTrue(!p.equals(new Position(5, 7)));
						assertTrue(!p.equals(new Position(7, 5)));
						assertTrue(!p.equals(new Position(5, 5)));
					}
					if (i == 1) {
						// Checks so the King can't pick its own position
						assertTrue(!p.equals(new Position(3, 3)));
						// Checks so the King can go to every position its
						// supposed to go to
						assertTrue(p.equals(new Position(2, 2))
								|| p.equals(new Position(2, 3))
								|| p.equals(new Position(2, 4))
								|| p.equals(new Position(3, 2))
								|| p.equals(new Position(3, 4))
								|| p.equals(new Position(4, 2))
								|| p.equals(new Position(4, 3))
								|| p.equals(new Position(4, 4)));
					}
				}
			}
		}
	}

	public void testGetTeam() {
		assertTrue(king.getTeam() == C.TEAM_WHITE);
	}

	public void testGetId() {
		assertTrue(king.getId() == C.PIECE_KING);
	}
}
