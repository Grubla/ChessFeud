package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Rook;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class RookTest extends AndroidTestCase {

	public void testRook() {
		Piece rook = new Rook(0);
		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = rook.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						// Checks so the Rook can't go outside the gameboard
						assertTrue(!p.equals(new Position(8, 8)));
						assertTrue(!p.equals(new Position(7, 8)));
						assertTrue(!p.equals(new Position(6, 8)));
						// Checks so the Rook can't go to its own square
						assertTrue(!p.equals(new Position(7, 7)));
						// Checks so the Rook can't go diagonally
						assertTrue(!p.equals(new Position(6, 6)));
						// Checks so the Rooks position is inside the gameboard
						// and also only verically or horizontally
						assertTrue((p.getX() >= 0 && p.getX() <= C.BOARD_LENGTH && p
								.getY() == C.BOARD_LENGTH) // Checks vertically
								|| (p.getY() >= 0 && p.getY() <= C.BOARD_LENGTH && p
										.getX() == C.BOARD_LENGTH)); //Checks horizontally
					}
					if (i == 1) {
						// Checks so the Rooks position is inside the gameboard
						assertTrue((p.getX() >= 0 && p.getX() <= C.BOARD_LENGTH && p
								.getY() == 3)
								|| (p.getY() >= 0 && p.getY() <= C.BOARD_LENGTH
										&& p.getX() == 3 && !(p.getX() == 3 && p
										.getY() == 3)));
						// Checks so the Rook doesn't have any positions
						// diagonally
						assertTrue(!p.equals(new Position(2, 2)));
						assertTrue(!p.equals(new Position(4, 4)));
						assertTrue(!p.equals(new Position(2, 4)));
						assertTrue(!p.equals(new Position(4, 2)));
					}
				}
			}
		}
	}
}
