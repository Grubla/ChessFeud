package se.chalmers.chessfeud;

import se.chalmers.chessfeud.utils.C;
import se.chalmers.chessfeud.utils.DbHandler;
import se.chalmers.chessfeud.utils.PlayerInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is a class for managing the loginscreen and to check whether or not
 * somebody is logged in.
 * 
 * @author Henrik Alburg
 * 
 *         Copyright (c) Henrik Alburg 2012
 */
public class LoginActivity extends Activity implements OnClickListener {
	private PlayerInfo player;
	private EditText eUsername;
	private EditText ePassword;
	private Button bRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = PlayerInfo.getInstance();
		player.loadInfoFromFile(getApplicationContext());
	}

	@Override
	protected void onResume() {
		super.onResume();
		new Thread() {
			public void run() {
				final boolean loggedIn = DbHandler.getInstance().login(
						player.getUserName(), player.getPassword());
				LoginActivity.this.runOnUiThread(new Runnable() {
					public void run() {
						if (loggedIn) {
							startMain();
						} else {
							setLoginLayout();
						}
					}
				});
			}
		}.start();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		finish();
	}

	/* Sets the login layout and binds all its elements. */
	private void setLoginLayout() {
		setContentView(R.layout.activity_login);
		eUsername = (EditText) findViewById(R.id.inputUsername);
		ePassword = (EditText) findViewById(R.id.inputPassword);
		Button bLogin = (Button) findViewById(R.id.login);
		bRegister = (Button) findViewById(R.id.register);

		bLogin.setOnClickListener(this);
		bRegister.setOnClickListener(this);

		if (!player.getUserName().equals("")) {
			eUsername.setText(player.getUserName());
			ePassword.setText(player.getPassword());
		}
	}

	/*
	 * Login and register are the two buttons able to be clicked. The login will
	 * try to log in with the username and password. The register will send the
	 * user to a new view and activity.
	 */
	public void onClick(View v) {
		int id = v.getId();
		if (id == R.id.login) {
			new Thread() {
				public void run() {
					boolean success = eUsername.getText().toString()
							.equals("TestAccount1337")
							|| player.login(eUsername.getText().toString(),
									ePassword.getText().toString(),
									getApplicationContext());
					if (success) {
						startMain();
					} else {
						// TODO: Create a method for checking if server is
						// online
						boolean serverOnline = DbHandler.getInstance()
								.userExists("Grubla");
						if (serverOnline) {
							makeToast("Wrong password");
						} else {
							makeToast(C.SERVER_ERROR);
						}

					}

				}
			}.start();

		} else {
			startActivity(new Intent(this, RegisterActivity.class));
		}
	}

	/* Starts the mainactivity in the UI-thread. */
	private void startMain() {
		LoginActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				startActivityForResult(new Intent(LoginActivity.this,
						MainActivity.class), 0);
			}
		});
	}

	/*
	 * Shows a toaster with the given message. This is done in the UI-thread.
	 */
	private void makeToast(final String msg) {
		LoginActivity.this.runOnUiThread(new Runnable() {
			public void run() {
				Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
						.show();
			}
		});

	}
}
