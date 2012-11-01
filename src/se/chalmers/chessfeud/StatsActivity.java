package se.chalmers.chessfeud;

import se.chalmers.chessfeud.utils.DbHandler;
import se.chalmers.chessfeud.utils.PlayerInfo;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;
/**
 * Is the class for showing all the stats.
 * 
 * @author Sean Pavlov
 * 
 *         Copyright (c) Sean Pavlov 2012
 * 
 */
public class StatsActivity extends Activity{
	TextView tPlayerName;
	TextView tGamesPlayed;
	TextView tAverageMoves;
	TextView tTotalMoves;
	TextView tWinPercentage;
	TextView tLossPercentage;
	TextView tDrawPercentage;
	PlayerInfo player;
	DbHandler dbh;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
		bindLayout();
		player = PlayerInfo.getInstance();
		dbh = DbHandler.getInstance();
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		setLayout();
	}
	
	/* Binds all the View-elements into their variables. */
	private void bindLayout(){
		tPlayerName = (TextView)findViewById(R.id.statsPlayerName);
		tGamesPlayed = (TextView)findViewById(R.id.statsGamesPlayed);
		tAverageMoves = (TextView)findViewById(R.id.statsAverageMoves);
		tTotalMoves = (TextView)findViewById(R.id.statsTotalMoves);
		tWinPercentage = (TextView)findViewById(R.id.statsPercentWins);
		tLossPercentage = (TextView)findViewById(R.id.statsPercentLoss);
		tDrawPercentage = (TextView)findViewById(R.id.statsPercentDraw);
	}
	/* Updates all the View-elements */
	private void setLayout(){
		tPlayerName.setText(player.getUserName());
		new Thread(){
			public void run() {
				final String statsString = dbh.getStats();
				StatsActivity.this.runOnUiThread(new Runnable(){
					public void run() {
						if(statsString != null){
							String[] stats = statsString.split("/");
							int wins = Integer.parseInt(stats[0]);
							int loss = Integer.parseInt(stats[1]);
							int draw = Integer.parseInt(stats[2]);
							int numberOfMoves = Integer.parseInt(stats[3]);
							int nbrOfGames = wins + loss + draw;
							
							tGamesPlayed.setText(""+nbrOfGames);
							if(nbrOfGames != 0){
								tAverageMoves.setText(""+(numberOfMoves/nbrOfGames));
								tWinPercentage.setText((100*wins/nbrOfGames)+"%");
								tLossPercentage.setText((100*loss/nbrOfGames)+"%");
								tDrawPercentage.setText((100*draw/nbrOfGames)+"%");
							}else{
								tAverageMoves.setText(""+(numberOfMoves));
								tWinPercentage.setText("0%");
								tLossPercentage.setText("0%");
								tDrawPercentage.setText("0%");
							}
							tTotalMoves.setText(""+numberOfMoves);
							
						}
					}
				});
			}
		}.start();
	}
}
