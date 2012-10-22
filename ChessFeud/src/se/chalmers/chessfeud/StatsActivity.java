package se.chalmers.chessfeud;

import android.app.Activity;
import android.os.Bundle;
/**
 * Is the class for showing all the stats.
 * 
 * @author Sean Pavlov
 * 
 *         Copyright (c) Sean Pavlov 2012
 * 
 */
public class StatsActivity extends Activity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_stats);
	}
}