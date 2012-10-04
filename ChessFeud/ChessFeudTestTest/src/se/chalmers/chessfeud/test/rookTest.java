package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class rookTest extends AndroidTestCase {

	public void testRook() {
		Piece rook = new Rook(0);
		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = rook.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						assertTrue(p != new Position(8, 8));
						assertTrue(p != new Position(7, 7));
						assertTrue(p != new Position(7, 8));
						assertTrue(p != new Position(6, 6));
						assertTrue(p != new Position(6, 8));
						assertTrue((p.getX() >= 0 && p.getX() <= 7 && p.getY() == 7)
								|| (p.getY() >= 0 && p.getY() <= 7 && p.getX() == 7));
					}
					if (i == 1) {
						assertTrue((p.getX() >= 0 && p.getX() <= 7 && p.getY() == 3)
								|| (p.getY() >= 0 && p.getY() <= 7
										&& p.getX() == 3 && !(p.getX() == 3 && p
										.getY() == 3)));
						assertTrue(p != new Position(2, 2));
						assertTrue(p != new Position(4, 4));
						assertTrue(p != new Position(2, 4));
						assertTrue(p != new Position(4, 2));
					}
				}
			}
		}
	}
}
