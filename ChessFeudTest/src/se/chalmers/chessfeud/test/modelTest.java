package se.chalmers.chessfeud.test;

import org.junit.After;
import org.junit.Before;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

public class modelTest extends AndroidTestCase {

	@Before
	public void setUp() throws Exception {

	}

	@After
	public void tearDown() throws Exception {

	}

	public void testGame() {
		/*
		 * This is an actual chess game i set up to test different parts of the
		 * game.
		 */
		ChessModel chessmodel = new ChessModel();
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
		ChessModel cm = new ChessModel();
		for (int i = 0; i < pos.length; i++) {
			cm.click(pos[i]);
			if (i == 10)
				assertTrue(cm.getTakenPieces().size() == 1);
			if (i == 14)
				assertTrue(cm.getTakenPieces().size() == 2);
			if (i == 22) {
				cm.click(new Position(4, 1));
				assertTrue(cm.getPossibleMoves().size() == 0);
				cm.click(new Position(0, 4));
				if (i == 24)
					assertTrue(cm.getTakenPieces().size() == 3);
				assertTrue(cm.getState() == C.STATE_CHECK);
				// Only king can move
			}
			if (i == 32) {
				// Only 36, 37, 56 can move
			}
			if (i == 42) {
				assertTrue(cm.getState() == C.STATE_VICTORY_WHITE);
			}

		}
	}

}
