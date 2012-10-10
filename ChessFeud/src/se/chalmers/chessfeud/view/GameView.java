package se.chalmers.chessfeud.view;

import java.util.List;

import se.chalmers.chessfeud.constants.C;
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
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

/**
 * Paints out the board with squares and pieces.
 * 
 * @author pavlov
 * 
 */
public class GameView extends View implements OnTouchListener{
	private Context context;
	private int chessSquareHeight;
	private int chessSquareWidth;
	ChessModel gm = new ChessModel(0);
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		this.setOnTouchListener(this);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		chessSquareHeight = this.getHeight() / 8;
		chessSquareWidth = this.getWidth() / 8;
		// White square main
				Paint wMain = new Paint();
				wMain.setColor(C.SQUARE_WHITE);
				// White square selected
				Paint wSelected = new Paint();
				wSelected.setColor(C.SQUARE_WHITE_SELECTED);
				// White square available
				Paint wAvailable = new Paint();
				wAvailable.setColor(C.SQUARE_WHITE_POSSIBLE_MOVES);

				// Black square main
				Paint bMain = new Paint();
				bMain.setColor(C.SQUARE_BLACK);
				// Black square selected
				Paint bSelected = new Paint();
				bSelected.setColor(C.SQUARE_BLACK_SELECTED);
				// Black square available
				Paint bAvailable = new Paint();
				bAvailable.setColor(C.SQUARE_BLACK_POSSIBLE_MOVES);

				List<Position> possibleSquares = gm.getPossibleMoves();
				// Draw every square
				boolean paintWhite = true;
				for (int y = 0; y < 8; y++) {
					for (int x = 0; x < 8; x++) {
						Rect r = new Rect(x * chessSquareWidth, y * chessSquareHeight,
								(x + 1) * chessSquareWidth, (y + 1) * chessSquareWidth);
						
						if (paintWhite) {
							if(possibleSquares.contains(new Position(x, y))){
								canvas.drawRect(r, wAvailable);
							}else if(new Position(x, y).equals(gm.getSelectedPosition())){
								canvas.drawRect(r, wSelected);
							}else{
								canvas.drawRect(r, wMain);
							}
							
						} else {
							if(possibleSquares.contains(new Position(x, y))){
								canvas.drawRect(r, bAvailable);
							}else if(new Position(x, y).equals(gm.getSelectedPosition())){
								canvas.drawRect(r, bSelected);
							}else{
								canvas.drawRect(r, bMain);
							}
						}

						Piece p = gm.getPieceAt(new Position(x, y));
						p.toString();
						// Paint piece at position is there is any
						if (!(p instanceof NoPiece)) {
							String uri = getPieceFileName(p.getTeam(), p.getId());
							uri = "drawable/pieces_" + uri;
							int imageResource = getResources().getIdentifier(uri, null,
									context.getPackageName());
							Bitmap bm = BitmapFactory.decodeResource(getResources(),
									imageResource);
							bm = Bitmap.createScaledBitmap(bm, chessSquareWidth,
									chessSquareHeight, false);
							canvas.drawBitmap(bm, x * chessSquareWidth, y
									* chessSquareHeight, null);
						}
						// Change the color that is going to be painted for each square
						paintWhite = !paintWhite;
					}
					// Change the color after each row so the board i checked.
					paintWhite = !paintWhite;
				}
			}

	/**
	 * Returning the filename of a certain piece without directory and file
	 * extension.
	 * 
	 * @param team
	 *            is the id of the team.
	 * @param id
	 *            is the piece id.
	 * @return A String with the filename.
	 */
	private String getPieceFileName(int team, int id) {
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
		return s;
	}

	public boolean onTouch(View v, MotionEvent event) {
		int x = (int)event.getX()/chessSquareWidth;
		int y = (int)event.getY()/chessSquareHeight;
		gm.click(new Position(x,y));
		if(gm.getTakenPieces() != null)
			Log.d("Amount:", ""+gm.getTakenPieces().size());
		this.invalidate();
		return false;
	}

}
