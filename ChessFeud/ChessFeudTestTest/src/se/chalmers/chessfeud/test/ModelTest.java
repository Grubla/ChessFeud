package se.chalmers.chessfeud.test;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.model.ChessBoard;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.Rules;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.test.AndroidTestCase;

/**
 * This is a testclass for the chess model and its parts.
 * 
 * @author Henrik Alburg
 * 
 *         Copyright (c) Henrik Alburg 2012
 */
public class ModelTest extends AndroidTestCase {

	/**
	 * Sets up a testgame and tests it.
	 */
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
				assertTrue(cm.getTakenPieces().size() > 0);
			}
			if (i == 13) {
				assertTrue(cm.getTakenPieces().size() == 2);
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

	/**
	 * Sets up another test game.
	 */
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

	/**
	 * Creates a board and tests it.
	 */
	public void testBoard() {
		ChessBoard cb = new ChessBoard();
		/* Make sure the game doesnt't start with check or anything like that */
		assertTrue(!Rules.isCheck(cb, C.TEAM_BLACK));
		assertTrue(!Rules.isCheck(cb, C.TEAM_WHITE));
		assertTrue(!Rules.isDraw(cb, C.TEAM_BLACK));
		assertTrue(!Rules.isCheckMate(cb, C.TEAM_BLACK));

		/* Tests if the exportBoard and  */
		ChessBoard cb2 = new ChessBoard(cb.exportBoard());

		for (int x = 0; x < cb.getWidth(); x++) {
			for (int y = 0; y < cb.getHeight(); y++) {
				/*
				 * Make sure the start board is actually the startboard from
				 * Rules And also that the cb2 is a copy of cb
				 */
				assertTrue(Rules.startBoard(x, y).getId() == cb
						.getPieceAt(x, y).getId());
				assertTrue(Rules.startBoard(x, y).getId() == cb2.getPieceAt(x,
						y).getId());
				assertTrue(cb.getPieceAt(x, y).getId() == (cb2.getPieceAt(x, y)
						.getId()));
				if (y > 1 && y < 6) {
					assertTrue(cb.isEmpty(new Position(x, y)));
				} else {
					assertTrue(!cb.isEmpty(new Position(x, y)));
				}
			}
		}
		
		/* Make sure that the pieces moves. */
		Piece p01 = cb.getPieceAt(0, 1);
		Piece p03 = cb.getPieceAt(0, 3);
		cb.movePiece(new Position(0, 1), new Position(0, 3));
		assertTrue(cb.getPieceAt(0, 1).getId() != p01.getId());
		assertTrue(cb.getPieceAt(0, 3).getId() != p03.getId());
		assertTrue(cb.getPieceAt(0, 3).getId() == p01.getId());
		
		/* Should return the piece */
		Piece piece = cb.movePiece(new Position(0,6), new Position(0,3));
		assertTrue(piece.getId() == C.PIECE_PAWN);
	}

}
