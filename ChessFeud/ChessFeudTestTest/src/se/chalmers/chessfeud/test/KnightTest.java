package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Knight;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class KnightTest extends AndroidTestCase {

	private Piece knight = new Knight(C.TEAM_WHITE);

	public void testKnight() {
		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = knight.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						// Checks so the Knight can't go outside the gameboard
						assertTrue(!p.equals(new Position(9, 8)));
						assertTrue(!p.equals(new Position(9, 6)));
						assertTrue(!p.equals(new Position(8, 9)));
						assertTrue(!p.equals(new Position(6, 9)));
						assertTrue(!p.equals(new Position(5, 8)));
						assertTrue(!p.equals(new Position(8, 5)));
					}
					if (i == 1) {
						// Checks thet all positions that the Knight is supposed
						// to be able to go to is in the lists
						assertTrue(p.equals(new Position(1, 2))
								|| p.equals(new Position(2, 1))
								|| p.equals(new Position(4, 1))
								|| p.equals(new Position(1, 4))
								|| p.equals(new Position(5, 2))
								|| p.equals(new Position(2, 5))
								|| p.equals(new Position(5, 4))
								|| p.equals(new Position(4, 5)));
					}
				}
			}
		}
	}

	public void testGetTeam() {
		assertTrue(knight.getTeam() == C.TEAM_WHITE);
	}

	public void testGetId() {
		assertTrue(knight.getId() == C.PIECE_KNIGHT);
	}

}
