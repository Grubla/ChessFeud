package se.chalmers.chessfeud;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.model.utils.Position;
import se.chalmers.chessfeud.utils.C;
import se.chalmers.chessfeud.utils.DbHandler;
import se.chalmers.chessfeud.utils.GameInfo;
import se.chalmers.chessfeud.utils.TimeStamp;
import se.chalmers.chessfeud.view.GameView;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Is a class that will update all data that is shown in Activity Play.
 * 
 * @author Sean Pavlov
 * @modifiedby Henrik Alburg Copyright (c) Sean Pavlov and Henrik Alburg 2012
 * 
 */
public class PlayActivity extends Activity implements PropertyChangeListener {
	private GameInfo g;
	private ChessModel cm;
	private TimeStamp ts;
	private TextView nbrOfTurns;
	private TextView playerNameWhite;
	private TextView playerNameBlack;
	private TextView statusTxt;
	private TextView whoseTurnNTime;
	private GameView gv;
	private ImageView blackRook1, blackRook2, blackBishop1, blackBishop2,
			blackKnight1, blackKnight2, blackQueen, blackKing;
	private ImageView whiteRook1, whiteRook2, whiteBishop1, whiteBishop2,
			whiteKnight1, whiteKnight2, whiteQueen, whiteKing;
	private TextView blackPawn, whitePawn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		setLayout();
		gv = (GameView) findViewById(R.id.chessBoard);
		String gameInfo = getIntent().getStringExtra("GameString");
		if (gameInfo != null) {
			g = new GameInfo(gameInfo);
			cm = new ChessModel(g, this);
			gv.setGameModel(cm);
			ts = new TimeStamp(g.getTimestamp());
		} else {
			// This is a dummygame
			g = new GameInfo("NA/NA/NA/0/NA/0");
			cm = new ChessModel(this);
			gv.setGameModel(cm);
			ts = new TimeStamp();
		}

		nbrOfTurns.setText("" + g.getTurns());
		playerNameWhite.setText("" + g.getWhitePlayer());
		playerNameBlack.setText("" + g.getBlackPlayer());

	}

	@Override
	protected void onResume() {
		super.onResume();
		nbrOfTurns.setText("" + g.getTurns());
		setTurnNTime();
		setState();
		setPiecesLeft();
	}

	/**
	 * Sets all the layouts that is in use in PlayActivity.
	 */
	private void setLayout() {
		nbrOfTurns = (TextView) findViewById(R.id.nbrOfTurns);
		playerNameWhite = (TextView) findViewById(R.id.playerNameWhite);
		playerNameBlack = (TextView) findViewById(R.id.playerNameBlack);
		statusTxt = (TextView) findViewById(R.id.statusTxt);
		whoseTurnNTime = (TextView) findViewById(R.id.whoseTurnAndTime);

		whitePawn = (TextView) findViewById(R.id.nbr_pawn_white);
		whiteRook1 = (ImageView) findViewById(R.id.img_white_rook_1);
		whiteRook2 = (ImageView) findViewById(R.id.img_white_rook_2);
		whiteBishop1 = (ImageView) findViewById(R.id.img_white_bishop_1);
		whiteBishop2 = (ImageView) findViewById(R.id.img_white_bishop_2);
		whiteKnight1 = (ImageView) findViewById(R.id.img_white_knight_1);
		whiteKnight2 = (ImageView) findViewById(R.id.img_white_knight_2);
		whiteQueen = (ImageView) findViewById(R.id.img_white_queen);
		whiteKing = (ImageView) findViewById(R.id.img_white_king);

		blackPawn = (TextView) findViewById(R.id.nbr_pawn_black);
		blackRook1 = (ImageView) findViewById(R.id.img_black_rook_1);
		blackRook2 = (ImageView) findViewById(R.id.img_black_rook_2);
		blackBishop1 = (ImageView) findViewById(R.id.img_black_bishop_1);
		blackBishop2 = (ImageView) findViewById(R.id.img_black_bishop_2);
		blackKnight1 = (ImageView) findViewById(R.id.img_black_knight_1);
		blackKnight2 = (ImageView) findViewById(R.id.img_black_knight_2);
		blackQueen = (ImageView) findViewById(R.id.img_black_queen);
		blackKing = (ImageView) findViewById(R.id.img_black_king);

	}

	/**
	 * Set the state of the game; Check, Win, Loss or Draw.
	 */
	private void setState() {
		switch (cm.getState()) {
		case C.STATE_NORMAL:
			statusTxt.setText("");
			break;
		case C.STATE_CHECK:
			statusTxt.setTextColor(C.STATE_TEXT_COLOR_RED);
			statusTxt.setText("Check");
			break;
		case C.STATE_DRAW:
			statusTxt.setTextColor(C.STATE_TEXT_COLOR_GREY);
			statusTxt.setText("Draw");
			Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
			break;
		case C.STATE_VICTORY_WHITE:
			if (g.thisPlayersTeam() == C.TEAM_WHITE) {
				statusTxt.setTextColor(C.STATE_TEXT_COLOR_GREEN);
				statusTxt.setText("Winner");
				Toast.makeText(this, "Congratulations! You won the game!",
						Toast.LENGTH_SHORT).show();
			} else {
				statusTxt.setTextColor(C.STATE_TEXT_COLOR_RED);
				statusTxt.setText("Loser");
				Toast.makeText(this, "Ouch! You lost the game!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		case C.STATE_VICTORY_BLACK:
			if (g.thisPlayersTeam() == C.TEAM_BLACK) {
				statusTxt.setTextColor(C.STATE_TEXT_COLOR_GREEN);
				statusTxt.setText("Winner");
				Toast.makeText(this, "Congratulations! You won the game!",
						Toast.LENGTH_SHORT).show();
			} else {
				statusTxt.setTextColor(C.STATE_TEXT_COLOR_RED);
				statusTxt.setText("Loser");
				Toast.makeText(this, "Ouch! You lost the game!",
						Toast.LENGTH_SHORT).show();
			}
			break;
		default:
			statusTxt.setText("");
			break;
		}
	}

	/**
	 * Sets the TextView that show whose turn it is and how long it has been
	 * since it was the players turn.
	 */
	private void setTurnNTime() {
		String s = "";
		if (g.getCurrentColor() == C.TEAM_WHITE) {
			s += "White";
		} else {
			s += "Black";
		}
		s += "'s Turn(";
		if (ts.getMinutesSinceStamp() < C.MINUTES_PER_HOUR) {
			s += "" + ts.getMinutesSinceStamp() + "m";
		} else {
			s += "" + (int) ts.getMinutesSinceStamp() / C.MINUTES_PER_HOUR
					+ "h";
		}
		s += ")";
		whoseTurnNTime.setText(s);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.beans.PropertyChangeListener#propertyChange(java.beans.
	 * PropertyChangeEvent)
	 */
	public void propertyChange(PropertyChangeEvent event) {
		if (event.getPropertyName().equals("Model")) {
			final GameInfo gameInfo = (GameInfo) event.getOldValue();
			final String gameBoard = (String) event.getNewValue();
			new Thread() {
				public void run() {
					DbHandler.getInstance().newMove(gameInfo.getOpponent(),
							gameBoard);
				}
			}.start();
		} else if (event.getPropertyName().equals("Pawn")) {
			Position p = (Position) event.getOldValue();
			promptNewPiece(p);

		}
		setState();
		setTurnNTime();
		if (cm.getState() != C.STATE_NORMAL) {
			final String s;
			if(cm.getState() == C.STATE_VICTORY_WHITE){
				s = "win";
			}else if(cm.getState() == C.STATE_VICTORY_BLACK){
				s = "loss";
			}else{
				s = "draw";
			}
			new Thread() {
				public void run() {
					//TODO: Send the state of the game.. 
					DbHandler.getInstance().setGameFinished(g.getWhitePlayer(),
							g.getBlackPlayer(), s);
				}
			}.start();

		}
		gv.invalidate();
	}

	/* Creates a popup list which has the different pieces as alternatives */
	private void promptNewPiece(final Position p) {
		final String[] pieces = { "Queen", "Rook", "Bishop", "Knight" };
		PlayActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				Builder builder = new Builder(PlayActivity.this);
				builder.setTitle(R.string.choose_piece);
				builder.setItems(pieces, new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int position) {
						switch (position) {
						case 0:
							setNewPiece(p, C.PIECE_QUEEN);
							break;
						case 1:
							setNewPiece(p, C.PIECE_ROOK);
							break;
						case 2:
							setNewPiece(p, C.PIECE_BISHOP);
							break;
						case 3:
							setNewPiece(p, C.PIECE_KNIGHT);
							break;
						default:
							setNewPiece(p, C.PIECE_QUEEN);
						}
					}
				});
				builder.setOnCancelListener(new OnCancelListener() {
					@Override
					public void onCancel(DialogInterface dialog) {
						setNewPiece(p, C.PIECE_QUEEN);
					}
				});
				builder.show();
			}
		});
	}

	private void setNewPiece(Position p, int id) {
		cm.changePawnTo(p, id);
		gv.invalidate();
	}

	/* Calculates and displays the pieces left from the list och pieces left */
	private void setPiecesLeft() {
		setAllVisible();

		List<Piece> left = cm.getPiecesLeft(); // Calculate only once
		int whitePawns = 8;
		int blackPawns = 8;
		for (int i = 0; i < left.size(); i++) {
			switch (left.get(i).getId()) {
			case C.PIECE_PAWN:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					whitePawns--;
				} else {
					blackPawns--;
				}
				break;
			case C.PIECE_ROOK:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					if (left.indexOf(left.get(i)) == i) {
						whiteRook1.setVisibility(View.INVISIBLE);
					} else {
						whiteRook2.setVisibility(View.INVISIBLE);
					}
				} else {
					if (left.indexOf(left.get(i)) == i) {
						blackRook1.setVisibility(View.INVISIBLE);
					} else {
						blackRook2.setVisibility(View.INVISIBLE);
					}
				}
				break;
			case C.PIECE_BISHOP:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					if (left.indexOf(left.get(i)) == i) {
						whiteBishop1.setVisibility(View.INVISIBLE);
					} else {
						whiteBishop2.setVisibility(View.INVISIBLE);
					}
				} else {
					if (left.indexOf(left.get(i)) == i) {
						blackBishop1.setVisibility(View.INVISIBLE);
					} else {
						blackBishop2.setVisibility(View.INVISIBLE);
					}
				}
				break;
			case C.PIECE_KNIGHT:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					if (left.indexOf(left.get(i)) == i) {
						whiteKnight1.setVisibility(View.INVISIBLE);
					} else {
						whiteKnight2.setVisibility(View.INVISIBLE);
					}
				} else {
					if (left.indexOf(left.get(i)) == i) {
						blackKnight1.setVisibility(View.INVISIBLE);
					} else {
						blackKnight2.setVisibility(View.INVISIBLE);
					}
				}
				break;
			case C.PIECE_QUEEN:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					whiteQueen.setVisibility(View.INVISIBLE);
				} else {
					blackQueen.setVisibility(View.INVISIBLE);
				}
				break;
			case C.PIECE_KING:
				if (left.get(i).getTeam() == C.TEAM_WHITE) {
					whiteKing.setVisibility(View.INVISIBLE);
				} else {
					blackKing.setVisibility(View.INVISIBLE);
				}
				break;
			}
			whitePawn.setText("" + whitePawns);
			blackPawn.setText("" + blackPawns);
		}

	}

	private void setAllVisible() {
		whiteRook1.setVisibility(View.VISIBLE);
		whiteRook2.setVisibility(View.VISIBLE);
		blackRook1.setVisibility(View.VISIBLE);
		blackRook2.setVisibility(View.VISIBLE);
		whiteBishop1.setVisibility(View.VISIBLE);
		whiteBishop2.setVisibility(View.VISIBLE);
		blackBishop1.setVisibility(View.VISIBLE);
		blackBishop2.setVisibility(View.VISIBLE);
		whiteKnight1.setVisibility(View.VISIBLE);
		whiteKnight2.setVisibility(View.VISIBLE);
		blackKnight1.setVisibility(View.VISIBLE);
		blackKnight2.setVisibility(View.VISIBLE);
		whiteQueen.setVisibility(View.VISIBLE);
		blackQueen.setVisibility(View.VISIBLE);
		whiteKing.setVisibility(View.VISIBLE);
		blackKing.setVisibility(View.VISIBLE);
	}
}
