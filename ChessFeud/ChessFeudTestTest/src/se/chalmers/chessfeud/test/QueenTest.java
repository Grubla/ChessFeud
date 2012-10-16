package se.chalmers.chessfeud.test;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.pieces.Queen;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class QueenTest extends AndroidTestCase {

	public void testQueen() {
		Piece queen = new Queen(0);
		Position[] pos = { new Position(7, 7), new Position(3, 3) };
		for (int i = 0; i < pos.length; i++) {
			List<List<Position>> testList = queen.theoreticalMoves(pos[i]);
			for (List<Position> l : testList) {
				for (Position p : l) {
					if (i == 0) {
						// Checks so the Queen can't go outside the gameboard
						assertTrue(!p.equals(new Position(8, 8)));
						assertTrue(!p.equals(new Position(7, 8)));
						assertTrue(!p.equals(new Position(6, 8)));
						// Checks so the Queen can't go to its own square
						assertTrue(!p.equals(new Position(7, 7)));
						// Checks so the positions will be inside the gameboard
						assertTrue((p.getX() >= 0 && p.getX() < C.BOARD_LENGTH && p
								.getY() == 7) // Checks horizontally
								|| (p.getY() >= 0 && p.getY() < C.BOARD_LENGTH && p
										.getX() == 7) // Checks vertically
								|| (p.getX() >= 0 && p.getX() < C.BOARD_LENGTH
										&& p.getY() >= 0
										&& p.getY() < C.BOARD_LENGTH && (p
										.getX() == p.getY()))); // Checks diagonally
					}
					if (i == 1) {
						// Checks so the Queen doesn't have any possible
						// positions outside the gameboard
						assertTrue((p.getX() >= 0 && p.getX() < C.BOARD_LENGTH && p
								.getY() == 3)
								|| (p.getY() >= 0 && p.getY() <= 7
										&& p.getX() == 3 && !(p.getX() == 3 && p
										.getY() == 3))
								|| (p.getX() >= 0 && p.getX() < C.BOARD_LENGTH
										&& p.getY() >= 0
										&& p.getY() < C.BOARD_LENGTH && ((p
										.getX() == p.getY()) || (p.getX()
										+ p.getY() == 6))));
					}
				}
			}
		}
	}
}
