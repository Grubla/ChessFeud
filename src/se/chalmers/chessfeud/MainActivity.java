package se.chalmers.chessfeud;

import java.util.ArrayList;
import java.util.List;

import se.chalmers.chessfeud.model.ChessModel;
import se.chalmers.chessfeud.model.pieces.Piece;
import se.chalmers.chessfeud.utils.C;
import se.chalmers.chessfeud.utils.DbHandler;
import se.chalmers.chessfeud.utils.GameInfo;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This is the activity that handles the overview of games. It binds the
 * menu_main.xml file and sets an adapter to its list objects. From here you can
 * reach the settings and statistics as well as a certain game in the list.
 * 
 * @author Henrik Alburg
 * 
 *         Copyright (c) Henrik Alburg 2012
 * 
 */
public class MainActivity extends Activity implements OnClickListener {
	private ImageView iLogo;
	private ListView startedGames;
	private DbHandler dbh;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_main);

		Button bStats, bSettings, bNewGame;

		dbh = DbHandler.getInstance();

		bStats = (Button) findViewById(R.id.button_stats);
		bSettings = (Button) findViewById(R.id.button_settings);
		bNewGame = (Button) findViewById(R.id.button_newgame);

		iLogo = (ImageView) findViewById(R.id.imageView1);
		iLogo.setOnClickListener(this);

		startedGames = (ListView) findViewById(R.id.list_ongoingGames);

		bStats.setOnClickListener(this);
		bSettings.setOnClickListener(this);
		bNewGame.setOnClickListener(this);
	}

	@Override
	protected void onResume() {
		super.onResume();
		updateGameList();
	}

	/* Updates the game list with what is given from the server */
	private void updateGameList() {
		new Thread() {
			public void run() {
				List<String> games = dbh.getGames();
				List<String> finished = dbh.getFinishedGames();

				final List<String> gamesList = orderLists(games, finished);

				MainActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						startedGames.setAdapter(new GameListAdapter(
								MainActivity.this, R.id.list_ongoingGames,
								gamesList));
					}
				});

			}
		}.start();
	}

	/*
	 * Takes the list och ongoing and finished games and merges them to one with
	 * the right priorities
	 */
	private List<String> orderLists(List<String> ongoing, List<String> finished) {
		List<String> orderedList = new ArrayList<String>();
		List<String> tmpList = new ArrayList<String>();
		orderedList.add(C.YOUR_TURN);

		for (String s : ongoing) {
			GameInfo g = new GameInfo(s);
			if (g.isMyTurn()) {
				orderedList.add(s);
			} else {
				tmpList.add(s);
			}
		}
		orderedList.add(C.OPPONENTS_TURN);
		orderedList.addAll(tmpList);
		orderedList.add(C.FINISHED_GAMES);
		orderedList.addAll(finished);

		return orderedList;
	}

	@Override
	public void onBackPressed() {
		setResult(1);
		finish();
	}

	/* Will launch a new activity based on what is clicked. */
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.button_stats:
			startActivity(new Intent(this, StatsActivity.class));
			break;
		case R.id.button_settings:
			startActivity(new Intent(this, SettingsActivity.class));
			break;
		case R.id.imageView1:
			startActivity(new Intent(this, PlayActivity.class));
			break;
		case R.id.button_newgame:
			showPrompt();
			break;
		default:
			Log.e("Mainactivity", "Button not binded");
		}
	}

	/* This is temporary until the view is finished. */
	private void showPrompt() {
		AlertDialog.Builder prompt = new AlertDialog.Builder(this);
		prompt.setTitle("New Game");
		prompt.setMessage("Type in the username of the person you want to challange:");
		final EditText input = new EditText(this);
		prompt.setView(input);
		prompt.setPositiveButton("Done", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				new Thread() {
					public void run() {
						final boolean success = DbHandler.getInstance()
								.newGame(input.getText().toString(),
										new ChessModel(null).exportModel());
						if (!success) {
							makeToast(C.SERVER_ERROR
									+ "Or you already have a game");
						}
					}
				}.start();
			}
		});
		prompt.show();
	}

	/* Creates a toast and displays is for the user */
	private void makeToast(final String msg) {
		MainActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}

	/*
	 * An inner class that works as a adapter between a list och games to a list
	 * of list_item views.
	 */
	private class GameListAdapter extends ArrayAdapter<String> {
		private Context context;
		private List<String> stringList;
		private int offset;

		/**
		 * Creates an adapter between the Strings (GameInfos) object and list
		 * items.
		 * 
		 * @param context
		 *            the context of the application
		 * @param resId
		 *            the resource id for the list
		 * @param l
		 *            the list of string to be converted to GameInfos
		 */
		public GameListAdapter(Context context, int resId, List<String> l) {
			super(context, resId, l);
			this.context = context;
			stringList = new ArrayList<String>();
			if (l != null) {
				for (String s : l) {
					stringList.add(s);
				}
			}
			this.offset = 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

			if (stringList.get(position).equals(C.YOUR_TURN)) {
				return setTextView(inflater, parent, C.YOUR_TURN);
			} else if(stringList.get(position).equals(C.OPPONENTS_TURN)){
				return setTextView(inflater, parent, C.OPPONENTS_TURN);
			}else if (stringList.get(position).equals(C.FINISHED_GAMES)) {
				return setTextView(inflater, parent, C.FINISHED_GAMES);
			} else {
				final String gameString = stringList.get(position);
				final GameInfo game = new GameInfo(gameString);

				View vRow = inflater.inflate(R.layout.menu_listitem, parent,
						false);
				if((position + offset) % 2 == 1){					
					vRow.setBackgroundColor(C.COLOR_BACKGROUND_WITEM);
				}else{
					vRow.setBackgroundColor(C.COLOR_BACKGROUND_BITEM);
				}

				bindAndSetViews(vRow, game);

				/* Start a PlayActivity with the right GameInfo */
				vRow.setOnClickListener(new OnClickListener() {
					public void onClick(View v) {
						startPlayActivity(gameString);
					}
				});
				return vRow;
			}
		}
		
		private void startPlayActivity(String gameString){
			Intent i = new Intent(context, PlayActivity.class);
			i.putExtra("GameString", gameString);
			startActivity(i);
		}
		
		/* Binds and sets all the views of the regular game in the list. */
		private void bindAndSetViews(View vRow, GameInfo game) {
			/* Bind all the views */
			TextView tTurn = (TextView) vRow.findViewById(R.id.player_turn);
			TextView blackName = (TextView) vRow
					.findViewById(R.id.player_black);
			TextView whiteName = (TextView) vRow
					.findViewById(R.id.player_white);
			TextView tNbrOfTurns = (TextView) vRow
					.findViewById(R.id.nbr_turns);

			/* Set the binded views */
			tTurn.setText(game.getCurrentPlayer() + "'s turn");
			blackName.setText(game.getBlackPlayer());
			whiteName.setText(game.getWhitePlayer());
			tNbrOfTurns.setText("" + game.getTurns());
			
			setPiecesLeft(vRow, game);
		}

		private void setPiecesLeft(View vRow, GameInfo gameInfo) {
			TextView blackPawn = (TextView) vRow.findViewById(R.id.nbr_pawn_black);
			ImageView blackRook1 = (ImageView) vRow.findViewById(R.id.img_black_piece_1);
			ImageView blackRook2 = (ImageView) vRow.findViewById(R.id.img_black_piece_2);
			ImageView blackBishop1 = (ImageView) vRow.findViewById(R.id.img_black_piece_3);
			ImageView blackBishop2 = (ImageView) vRow.findViewById(R.id.img_black_piece_4);
			ImageView blackKnight1 = (ImageView) vRow.findViewById(R.id.img_black_piece_5);
			ImageView blackKnight2 = (ImageView) vRow.findViewById(R.id.img_black_piece_6);
			ImageView blackQueen = (ImageView) vRow.findViewById(R.id.img_black_piece_7);
			ImageView blackKing = (ImageView) vRow.findViewById(R.id.img_black_piece_8);
			
			TextView whitePawn = (TextView) vRow.findViewById(R.id.nbr_pawn_white);
			ImageView whiteRook1 = (ImageView) vRow.findViewById(R.id.img_white_piece_1);
			ImageView whiteRook2 = (ImageView) vRow.findViewById(R.id.img_white_piece_2);
			ImageView whiteBishop1 = (ImageView) vRow.findViewById(R.id.img_white_piece_3);
			ImageView whiteBishop2 = (ImageView) vRow.findViewById(R.id.img_white_piece_4);
			ImageView whiteKnight1 = (ImageView) vRow.findViewById(R.id.img_white_piece_5);
			ImageView whiteKnight2 = (ImageView) vRow.findViewById(R.id.img_white_piece_6);
			ImageView whiteQueen = (ImageView) vRow.findViewById(R.id.img_white_piece_7);
			ImageView whiteKing = (ImageView) vRow.findViewById(R.id.img_white_piece_8);
			
			blackRook1.setVisibility(View.INVISIBLE);
			blackRook2.setVisibility(View.INVISIBLE);
			blackBishop1.setVisibility(View.INVISIBLE);
			blackBishop2.setVisibility(View.INVISIBLE);
			blackKnight1.setVisibility(View.INVISIBLE);
			blackKnight2.setVisibility(View.INVISIBLE);
			blackQueen.setVisibility(View.INVISIBLE);
			blackKing.setVisibility(View.INVISIBLE);
			whiteRook1.setVisibility(View.INVISIBLE);
			whiteRook2.setVisibility(View.INVISIBLE);
			whiteBishop1.setVisibility(View.INVISIBLE);
			whiteBishop2.setVisibility(View.INVISIBLE);
			whiteKnight1.setVisibility(View.INVISIBLE);
			whiteKnight2.setVisibility(View.INVISIBLE);
			whiteQueen.setVisibility(View.INVISIBLE);
			whiteKing.setVisibility(View.INVISIBLE);
			
			ChessModel cm = new ChessModel(gameInfo, null);
			List<Piece> left = cm.getPiecesLeft(); // Calculate only once
			int whitePawns = 0;
			int blackPawns = 0;
			for (int i = 0; i < left.size(); i++) {
				switch (left.get(i).getId()) {
				case C.PIECE_PAWN:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						whitePawns++;
					} else {
						blackPawns++;
					}
					break;
				case C.PIECE_ROOK:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						if (left.indexOf(left.get(i)) == i) {
							whiteRook1.setVisibility(View.VISIBLE);
						} else {
							whiteRook2.setVisibility(View.VISIBLE);
						}
					} else {
						if (left.indexOf(left.get(i)) == i) {
							blackRook1.setVisibility(View.VISIBLE);
						} else {
							blackRook2.setVisibility(View.VISIBLE);
						}
					}
					break;
				case C.PIECE_BISHOP:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						if (left.indexOf(left.get(i)) == i) {
							whiteBishop1.setVisibility(View.VISIBLE);
						} else {
							whiteBishop2.setVisibility(View.VISIBLE);
						}
					} else {
						if (left.indexOf(left.get(i)) == i) {
							blackBishop1.setVisibility(View.VISIBLE);
						} else {
							blackBishop2.setVisibility(View.VISIBLE);
						}
					}
					break;
				case C.PIECE_KNIGHT:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						if (left.indexOf(left.get(i)) == i) {
							whiteKnight1.setVisibility(View.VISIBLE);
						} else {
							whiteKnight2.setVisibility(View.VISIBLE);
						}
					} else {
						if (left.indexOf(left.get(i)) == i) {
							blackKnight1.setVisibility(View.VISIBLE);
						} else {
							blackKnight2.setVisibility(View.VISIBLE);
						}
					}
					break;
				case C.PIECE_QUEEN:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						whiteQueen.setVisibility(View.VISIBLE);
					} else {
						blackQueen.setVisibility(View.VISIBLE);
					}
					break;
				case C.PIECE_KING:
					if (left.get(i).getTeam() == C.TEAM_WHITE) {
						whiteKing.setVisibility(View.VISIBLE);
					} else {
						blackKing.setVisibility(View.VISIBLE);
					}
					break;
				}
				whitePawn.setText("" + whitePawns);
				blackPawn.setText("" + blackPawns);
			}
		}

		/* Sets the given text to the created TextView-listobject, also returns it. */
		private TextView setTextView(LayoutInflater inflater, ViewGroup parent, String s){
			TextView vRow = (TextView) inflater.inflate(R.layout.menu_listhead, parent, false);
			vRow.setText(s);
			offset++;
			return vRow;
		}

	}

}
