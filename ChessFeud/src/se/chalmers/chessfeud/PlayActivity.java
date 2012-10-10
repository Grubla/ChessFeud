package se.chalmers.chessfeud;

import se.chalmers.chessfeud.view.GameView;
import android.app.Activity;
import android.os.Bundle;

public class PlayActivity extends Activity{
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_play);
		
		GameView gv = (GameView) findViewById(R.id.chessBoard);
		String gameInfo = (String)savedInstanceState.get("GameString");
		if(gameInfo != null)
			gv.setGameModel(gameInfo);
	}
}
