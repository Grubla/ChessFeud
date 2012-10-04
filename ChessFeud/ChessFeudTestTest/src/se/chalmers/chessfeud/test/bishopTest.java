package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class bishopTest extends AndroidTestCase {

	public void testBishop() {
		Piece bishop = new Bishop(0);
		Position[] pos = { new Position(7, 7), new Position(7, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = bishop.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						assertTrue(p != new Position(8, 8));
						assertTrue(p != new Position(7, 7));
						assertTrue(p != new Position(7, 8));
						assertTrue(p != new Position(6, 8));
						assertTrue(p.getX() >= 0 && p.getX() <= 7
								&& p.getY() >= 0 && p.getY() <= 7
								&& (p.getX() == p.getY()));
					}
					if (i == 1) {
						
					}
				}
			}
		}
	}
}
