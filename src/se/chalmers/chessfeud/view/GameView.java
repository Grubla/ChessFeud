package se.chalmers.chessfeud.view;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.constants.PlayerInfo;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.pieces.NoPiece;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Paints out the board with squares and pieces.
 * 
 * @author Sean Pavlov
 * 
 *         Copyright (c) Sean Pavlov 2012
 */
public class GameView extends View implements OnTouchListener {
	private Context context;
	private PlayerInfo pi = PlayerInfo.getInstance();
	private int chessSquareHeight;
	private int chessSquareWidth;
	private ChessModel gm = new ChessModel(0);
	private Paint wMain = new Paint();
	private Paint wSelected = new Paint();
	private Paint wAvailable = new Paint();
	private Paint bMain = new Paint();
	private Paint bSelected = new Paint();
	private Paint bAvailable = new Paint();
	private Rect r = new Rect();

	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setOnTouchListener(this);
	}

	public void setGameModel(ChessModel cm) {
		gm = cm;
		this.invalidate();
	}

	protected void onCreate(Canvas canvas) {

	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		chessSquareHeight = this.getHeight() / C.BOARD_LENGTH;
		chessSquareWidth = this.getWidth() / C.BOARD_LENGTH;

		wMain.setColor(C.SQUARE_WHITE);
		wSelected.setColor(C.SQUARE_WHITE_SELECTED);
		wAvailable.setColor(C.SQUARE_WHITE_POSSIBLE_MOVES);

		bMain.setColor(C.SQUARE_BLACK);
		bSelected.setColor(C.SQUARE_BLACK_SELECTED);
		bAvailable.setColor(C.SQUARE_BLACK_POSSIBLE_MOVES);

		List<Position> possibleSquares = gm.getPossibleMoves();

		// Draw every square
		boolean paintWhite = true;
		for (int y = 0; y < C.BOARD_LENGTH; y++) {
			for (int x = 0; x < C.BOARD_LENGTH; x++) {
				r.set(x * chessSquareWidth, y * chessSquareHeight, (x + 1)
						* chessSquareWidth, (y + 1) * chessSquareHeight);

				if (paintWhite) {
					paintWhiteSquare(possibleSquares, canvas, r, x, y);

				} else {
					paintBlackSquare(possibleSquares, canvas, r, x, y);
				}
				Piece p = gm.getPieceAt(new Position(x, y));
				p.toString();
				// Paint piece at position is there is any
				if (!(p instanceof NoPiece)) {
					canvas.drawBitmap(getPieceBitmap(p.getTeam(), p.getId()), x
							* chessSquareWidth, y * chessSquareHeight, null);
				}
				// Change the color that is going to be painted for each square
				paintWhite = !paintWhite;
			}
			// Change the color after each row so the board i checked.
			paintWhite = !paintWhite;
		}
	}

	/**
	 * A method to paint out the brighter square.
	 * 
	 * @param possibleSquares
	 *            List of squares where the selected piece can go to.
	 * @param canvas
	 *            is the canvas
	 * @param r
	 *            is the rectangle to be painted
	 * @param x
	 *            is the x-pos
	 * @param y
	 *            is the y-pos
	 */
	private void paintWhiteSquare(List<Position> possibleSquares,
			Canvas canvas, Rect r, int x, int y) {
		if (possibleSquares.contains(new Position(x, y))) {
			canvas.drawRect(r, wAvailable);
		} else if (new Position(x, y).equals(gm.getSelectedPosition())
				&& pi.getHelpTip()) {
			canvas.drawRect(r, wSelected);
		} else {
			canvas.drawRect(r, wMain);
		}
	}

	/**
	 * A method to paint out the darker square.
	 * 
	 * @param possibleSquares
	 *            List of squares where the selected piece can go to.
	 * @param canvas
	 *            is the canvas
	 * @param r
	 *            is the rectangle to be painted
	 * @param x
	 *            is the x-pos
	 * @param y
	 *            is the y-pos
	 */
	private void paintBlackSquare(List<Position> possibleSquares,
			Canvas canvas, Rect r, int x, int y) {
		if (possibleSquares.contains(new Position(x, y))) {
			canvas.drawRect(r, bAvailable);
		} else if (new Position(x, y).equals(gm.getSelectedPosition())
				&& pi.getHelpTip()) {
			canvas.drawRect(r, bSelected);
		} else {
			canvas.drawRect(r, bMain);
		}
	}

	/**
	 * Returning the bitmap of a certain piece.
	 * 
	 * @param team
	 *            is the id of the team.
	 * @param id
	 *            is the piece id.
	 * @return A bitmap of the piece
	 */
	private Bitmap getPieceBitmap(int team, int id) {
		String s = "";
		if (team == 0) {
			s = "" + "white";
		} else {
			s = "" + "black";
		}
		s += "_";

		switch (id) {
		case C.PIECE_PAWN:
			s += "pawn";
			break;

		case C.PIECE_ROOK:
			s += "rook";
			break;

		case C.PIECE_BISHOP:
			s += "bishop";
			break;

		case C.PIECE_KNIGHT:
			s += "knight";
			break;

		case C.PIECE_QUEEN:
			s += "queen";
			break;

		case C.PIECE_KING:
			s += "king";
			break;

		default:
			s = "notfound";
			break;
		}
		s = "drawable/pieces_" + s;
		int imageResource = getResources().getIdentifier(s, null,
				context.getPackageName());
		Bitmap bm = BitmapFactory.decodeResource(getResources(), imageResource);
		bm = Bitmap.createScaledBitmap(bm, chessSquareWidth, chessSquareHeight,
				false);
		return bm;
	}

	public boolean onTouch(View v, MotionEvent event) {
		int x = (int) event.getX() / chessSquareWidth;
		int y = (int) event.getY() / chessSquareHeight;
		gm.click(new Position(x, y));
		this.invalidate();
		return false;
	}

}
