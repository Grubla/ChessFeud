package se.chalmers.chessfeud;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import se.chalmers.chessfeud.constants.C;
import se.chalmers.chessfeud.constants.DbHandler;
import se.chalmers.chessfeud.constants.Game;
import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.view.GameView;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class PlayActivity extends Activity implements PropertyChangeListener {
	private Game g;
	private ChessModel cm;
	private TextView nbrOfTurns = (TextView) findViewById(R.id.nbrOfTurns);
	private TextView playerNameWhite = (TextView) findViewById(R.id.playerNameWhite);
	private TextView playerNameBlack = (TextView) findViewById(R.id.playerNameBlack);
	private TextView statusTxt = (TextView) findViewById(R.id.statusTxt);
	private TextView whoseTurnNTime = (TextView) findViewById(R.id.whoseTurnAndTime);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		GameView gv = (GameView) findViewById(R.id.chessBoard);
		String gameInfo = getIntent().getStringExtra("GameString");
		int position = getIntent().getIntExtra("Position", -1);
		if (gameInfo != null) {
			gv.setGameModel(gameInfo, position, this);
			g = new Game(gameInfo, 0);
		}

		nbrOfTurns.setText(g.getTurns());
		playerNameWhite.setText(g.getWhitePlayer());
		playerNameBlack.setText(g.getBlackPlayer());
		setState();
		setTurnNTime();
	}

	protected void onResume() {
		nbrOfTurns.setText(g.getTurns());
		setState();
		setTurnNTime();
	}

	/**
	 * A method to set the state of the game; Check, Win, Loss or Draw.
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
			// TODO: Add pop-up
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
	
	private void setTurnNTime() {
		String s = "";
		if(g.getCurrentColor() == C.TEAM_WHITE){
			s += "White";
		}else{
			s += "Black";
		}
		//TODO: Fix turns when Grubla has fixed a Timestamp-class
		s += "'s Turn()";
	}

	public void propertyChange(PropertyChangeEvent event) {
		if(event.getPropertyName().equals("Model")){
			final Game gameInfo = (Game)event.getOldValue();
			final String gameBoard = (String)event.getNewValue();
			new Thread(){
				public void run() {
					DbHandler.getInstance().newMove(gameInfo.getOpponent(), gameBoard);
				}
			}.start();
		}
	}
}
