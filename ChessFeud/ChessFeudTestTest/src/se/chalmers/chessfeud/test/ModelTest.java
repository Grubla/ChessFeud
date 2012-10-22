package se.chalmers.chessfeud.test;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class ModelTest extends AndroidTestCase {

	public void testGame() {
		/*
		 * This is an actual chess game i set up to test different parts of the
		 * game.
		 */
		Position[] pos = { new Position(4, 6), new Position(4, 4),
				new Position(4, 1), new Position(4, 3), new Position(6, 7),
				new Position(5, 5), new Position(5, 0), new Position(4, 1),
				new Position(5, 5), new Position(4, 3), new Position(3, 1),
				new Position(3, 3), new Position(4, 4), new Position(3, 3),
				new Position(1, 0), new Position(0, 2), new Position(4, 3),
				new Position(2, 4), new Position(0, 2), new Position(1, 4),
				new Position(3, 7), new Position(4, 6), new Position(1, 4),
				new Position(2, 6), new Position(4, 7), new Position(3, 7),
				new Position(2, 0), new Position(3, 1), new Position(4, 6),
				new Position(7, 3), new Position(2, 6), new Position(4, 5),
				new Position(5, 6), new Position(4, 5), new Position(0, 1),
				new Position(0, 3), new Position(2, 4), new Position(4, 3),
				new Position(1, 1), new Position(1, 2), new Position(7, 3),
				new Position(5, 1) };
		ChessModel cm = new ChessModel(0);
		for (int i = 0; i < pos.length; i++) {
			cm.click(pos[i]);
			if (i == 11) {
				assertTrue(cm.getTakenPieces().size() == 1);
			}
			if (i == 13) {
				assertTrue(cm.getTakenPieces().size() == 2);
			}
			if (i == 21) {
				cm.click(new Position(4, 1));
				assertTrue(cm.getPossibleMoves().size() == 0);
				cm.click(new Position(4, 1));
			}
			if (i == 23) {
				assertTrue(cm.getTakenPieces().size() == 3);
				assertTrue(cm.getState() == C.STATE_CHECK);
				// Only king can move
			}
			if (i == 42) {
				assertTrue(cm.getState() == C.STATE_VICTORY_WHITE);
			}

		}
	}

	public void testGame2() {
		Position[] pos = {
				// Click White Move Click Black move
				new Position(6, 7), new Position(5, 5), new Position(7, 1),
				new Position(7, 3), new Position(5, 5), new Position(6, 3),
				new Position(6, 1), new Position(6, 2), new Position(4, 6),
				new Position(4, 5), new Position(5, 1), new Position(5, 3),
				new Position(5, 7), new Position(2, 4), new Position(4, 1),
				new Position(4, 2), new Position(5, 6), new Position(5, 4),
				new Position(3, 1), new Position(3, 3), new Position(2, 4),
				new Position(1, 3)/* Check */, new Position(2, 1),
				new Position(2, 2), new Position(1, 3), new Position(5, 7),
				new Position(1, 1), new Position(1, 3), new Position(3, 6),
				new Position(3, 4), new Position(0, 1), new Position(0, 2),
				new Position(6, 3), new Position(5, 5), new Position(0, 5),
				new Position(1, 4),/* Check */
				new Position(2, 7), new Position(3, 6), new Position(2, 2),
				new Position(2, 3), new Position(5, 5), new Position(4, 3),
				new Position(6, 0), new Position(4, 1), new Position(3, 4),
				new Position(2, 3), new Position(1, 4), new Position(2, 3),
				new Position(1, 6), new Position(1, 4), new Position(2, 3),
				new Position(1, 2), new Position(3, 6), new Position(2, 5),
				new Position(1, 2), new Position(4, 5), new Position(3, 7),
				new Position(5, 5), new Position(4, 5), new Position(2, 7),
				new Position(4, 3), new Position(3, 5), new Position(3, 3),
				new Position(3, 4), new Position(3, 5), new Position(2, 7),
				new Position(3, 4), new Position(2, 5), new Position(5, 5),
				new Position(0, 0), new Position(4, 1), new Position(2, 2),
				new Position(1, 7), new Position(2, 5), new Position(3, 0),
				new Position(3, 4), new Position(2, 7), new Position(4, 6),
				new Position(3, 4), new Position(1, 4), new Position(0, 7),
				new Position(1, 7), new Position(1, 4), new Position(0, 5),
				new Position(6, 6), new Position(6, 5), new Position(7, 0),
				new Position(7, 1), new Position(5, 7), new Position(6, 6),
				new Position(2, 0), new Position(1, 1), new Position(6, 1),
				new Position(2, 2), new Position(1, 1), new Position(2, 2),
				new Position(0, 0), new Position(1, 0), new Position(4, 0),
				new Position(5, 1), new Position(1, 0), new Position(2, 1),
				new Position(5, 1), new Position(6, 0), new Position(2, 1),
				new Position(2, 2), new Position(0, 5), new Position(4, 1),
				new Position(2, 2), new Position(0, 2), new Position(1, 3),
				new Position(1, 4), new Position(0, 2), new Position(2, 0),
				new Position(4, 1), new Position(5, 0), new Position(2, 0),
				new Position(4, 2), new Position(7, 1), new Position(5, 1),
				new Position(4, 2), new Position(6, 2), new Position(5, 1),
				new Position(6, 1), new Position(6, 2), new Position(4, 2),
				new Position(6, 1), new Position(5, 1), new Position(1, 7),
				new Position(1, 4), new Position(5, 0), new Position(1, 4),
				new Position(4, 2), new Position(6, 2), new Position(5, 1),
				new Position(6, 1), new Position(6, 2), new Position(5, 3),
				new Position(1, 4), new Position(2, 5), new Position(4, 6),
				new Position(2, 5), new Position(6, 1), new Position(4, 1),
				new Position(2, 5), new Position(4, 6), new Position(4, 1),
				new Position(4, 6), new Position(4, 7), new Position(4, 6),
				new Position(6, 0), new Position(6, 1), new Position(7, 7),
				new Position(3, 7), new Position(6, 1), new Position(7, 3),
				new Position(3, 7), new Position(3, 2), new Position(7, 2),
				new Position(6, 1), new Position(5, 3), new Position(7, 3),
				new Position(6, 1), new Position(5, 0), new Position(3, 2),
				new Position(3, 1), new Position(5, 0), new Position(6, 0),
				new Position(7, 3), new Position(4, 0) /* CHECKMATE */};
	}

}
