package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Bishop;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class BishopTest extends AndroidTestCase {

	public void testBishop() {
		Piece bishop = new Bishop(0);
		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = bishop.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						// Checks so the Bishops position is inside the
						// gameboard
						assertTrue(!p.equals(new Position(8, 8)));
						assertTrue(!p.equals(new Position(7, 8)));
						assertTrue(!p.equals(new Position(6, 8)));
						// Checks so the Bishop can't go to its own position
						assertTrue(!p.equals(new Position(7, 7)));
						// Checks so the Bishop can't go vertically
						assertTrue(!p.equals(new Position(6, 7)));
						// Checks so the Bishop can't go horizontally
						assertTrue(!p.equals(new Position(7, 6)));
						// Checks so the Bishops position is inside the
						// gameboard
						assertTrue(p.getX() >= 0 && p.getX() <= C.BOARD_LENGTH
								&& p.getY() >= 0 && p.getY() <= C.BOARD_LENGTH
								&& (p.getX() == p.getY()));
					}
					if (i == 1) {
						assertTrue((p.getX() >= 0 && p.getX() <= C.BOARD_LENGTH
								&& p.getY() >= 0 && p.getY() <= C.BOARD_LENGTH && ((p
								.getX() == p.getY()) || (p.getX() + p.getY() == 6))));
					}
				}
			}
		}
	}
}
