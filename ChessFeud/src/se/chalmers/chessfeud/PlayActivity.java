package se.chalmers.chessfeud;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.constants.DbHandler;
import se.chalmers.chessfeud.constants.Game;
import se.chalmers.chessfeud.constants.TimeStamp;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.view.GameView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Is a class that will update all data that is shown in Activity Play.
 * 
 * @author Sean Pavlov
 * 
 *         Copyright (c) Sean Pavlov 2012
 * 
 */
public class PlayActivity extends Activity implements PropertyChangeListener {
	private Game g;
	private ChessModel cm;
	private TimeStamp ts;
	private TextView nbrOfTurns;
	private TextView playerNameWhite;
	private TextView playerNameBlack;
	private TextView statusTxt;
	private TextView whoseTurnNTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		setLayout();
		GameView gv = (GameView) findViewById(R.id.chessBoard);
		String gameInfo = getIntent().getStringExtra("GameString");
		int position = getIntent().getIntExtra("Position", -1);
		if (gameInfo != null) {
			g = new Game(gameInfo, position);
			cm = new ChessModel(g, this);
			gv.setGameModel(cm);
			ts = new TimeStamp(g.getTimestamp());
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
			statusTxt.setTextColor(0xFFDD0000);
			statusTxt.setText("Check");
			break;
		case C.STATE_DRAW:
			statusTxt.setTextColor(0xFF666666);
			statusTxt.setText("Draw");
			Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
			break;
		case C.STATE_VICTORY_WHITE:
			if (g.thisPlayersTeam() == C.TEAM_WHITE) {
				statusTxt.setTextColor(0xFF00DD00);
				statusTxt.setText("Winner");
			} else {
				statusTxt.setTextColor(0xFFDD0000);
				statusTxt.setText("Loser");
			}
			break;
		case C.STATE_VICTORY_BLACK:
			if (g.thisPlayersTeam() == C.TEAM_BLACK) {
				statusTxt.setTextColor(0xFF00DD00);
				statusTxt.setText("Winner");
				Toast.makeText(this, "Congratulations! You won the game!",
						Toast.LENGTH_SHORT).show();
			} else {
				statusTxt.setTextColor(0xFFDD0000);
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
		if (ts.getMinutesSinceStamp() < 60) {
			s += "" + ts.getMinutesSinceStamp() + "m";
		} else {
			s += "" + (int) ts.getMinutesSinceStamp() / 60 + "h";
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
			final Game gameInfo = (Game) event.getOldValue();
			final String gameBoard = (String) event.getNewValue();
			new Thread() {
				public void run() {
					DbHandler.getInstance().newMove(gameInfo.getOpponent(),
							gameBoard);
				}
			}.start();
		}
	}
}
