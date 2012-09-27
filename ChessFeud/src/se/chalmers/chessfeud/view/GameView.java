package se.chalmers.chessfeud.view;

import se.chalmers.chessfeud.constants.C;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
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
	GameModel gm = new GameModel();

	// For testing purposes only
	private int i = 0;
	private Position[] pA = {new Position(4, 6), new Position(4, 4), new Position(4,1), new Position(4,3), 
			new Position(6,7), new Position(5,5), new Position(5,0), new Position(4,1), new Position(5,5), 
			new Position(4,3), new Position(3,1), new Position(3,3), new Position(4,4), new Position(3,3), 
			new Position(1,0), new Position(0,2), new Position(4,3), new Position(2,4), new Position(0,2), 
			new Position(1,4), new Position(3,7), new Position(4,6), new Position(1,4), new Position(2,6),
			new Position(4,7), new Position(3,7), new Position(2,0), new Position(3,1), new Position(4,6), 
			new Position(7,3), new Position(2,6), new Position(4,5), new Position(5,6), new Position(4,5),
			new Position(0,1), new Position(0,3), new Position(2,4), new Position(4,3), new Position(1,1), 
			new Position(1,2), new Position(7,3), new Position(5,1) };
	
	public GameView(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		int chessSquareHeight = this.getHeight() / 8;
		int chessSquareWidth = this.getWidth() / 8;

		// White square property
		Paint pW = new Paint();
		pW.setColor(C.SQUARE_WHITE);

		// Black square property
		Paint pB = new Paint();
		pB.setColor(C.SQUARE_BLACK);

		// Draw every square
		boolean paintWhite = true;
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				Rect r = new Rect(j * chessSquareWidth, i * chessSquareHeight,
						(j + 1) * chessSquareWidth, (i + 1) * chessSquareWidth);
				if (paintWhite) {
					canvas.drawRect(r, pW);
				} else {
					canvas.drawRect(r, pB);
				}

				Piece p = gm.getPieceAt(new Position(j, i));
				// Paint piece at position is there is any
				if (p != null) {
					String uri = getPieceFileName(p.getTeam, p.getId);
					uri = "../../../../res/drawable-mdpi/pieces/" + uri
							+ ".png";
					int imageResource = getResources().getIdentifier(uri, null,
							context.getPackageName());
					Drawable image = getResources().getDrawable(imageResource);
					Bitmap bm = BitmapFactory.decodeResource(getResources(),
							imageResource);
					Bitmap.createScaledBitmap(bm, chessSquareWidth / 8,
							chessSquareHeight / 8, false);
					canvas.drawBitmap(bm, j * chessSquareWidth, i
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
		case 0:
			s += "pawn";
			break;

		case 1:
			s += "rook";
			break;

		case 2:
			s += "bishop";
			break;

		case 3:
			s += "knight";
			break;

		case 4:
			s += "queen";
			break;

		case 5:
			s += "king";
			break;

		default:
			s = "notfound";
			break;
		}
		return s;
	}

	public boolean onTouch(View v, MotionEvent event) {
		if(pA.length > i)
			gm.click(pA[i]);
		i++;
		return false;
	}

}
