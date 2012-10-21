package se.chalmers.chessfeud;

import se.chalmers.chessfeud.constants.DbHandler;
import se.chalmers.chessfeud.constants.PlayerInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/**
 * This is the controller activity for a view to register. It uses the
 * PlayerInfo class and DbHandler to create users.
 * 
 * @author Henrik Alburg
 * 
 */
public class RegisterActivity extends Activity {
	private Button bRegister;
	private EditText eUsername;
	private EditText ePassword1;
	private EditText ePassword2;
	private EditText eEmail;

	private DbHandler dbh;
	private PlayerInfo player;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		dbh = DbHandler.getInstance();
		player = PlayerInfo.getInstance();

		setContentView(R.layout.activity_register);
		bRegister = (Button) findViewById(R.id.regButtonRegister);
		eUsername = (EditText) findViewById(R.id.regInputUsername);
		ePassword1 = (EditText) findViewById(R.id.regInputPassword);
		ePassword2 = (EditText) findViewById(R.id.regReInputPassword);
		eEmail = (EditText) findViewById(R.id.regInputEmail);

		bRegister.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				new Thread() {
					public void run() {
						if (fieldsOk()) {
							if (addUser()) {
								if (login()) {
									startActivity(new Intent(
											RegisterActivity.this,
											MainActivity.class));
								}
							} else {
								String msg = "Failed to register";
								Toast.makeText(getApplicationContext(), msg,
										Toast.LENGTH_SHORT).show();
							}
						} else {
							String msg = "Failed to log in after registration";
							Toast.makeText(getApplicationContext(), msg,
									Toast.LENGTH_SHORT).show();
						}
					}
				}.start();

			}
		});
	}

	/* Returns true if the user was successfully added */
	private boolean addUser() {
		return dbh.addUser(eEmail.getText().toString(), eUsername.getText()
				.toString(), ePassword1.getText().toString());
	}

	/* Returns true if able to log in */
	private boolean login() {
		return player.login(eUsername.getText().toString(), ePassword1
				.getText().toString(), getApplicationContext());
	}

	/* Returns true if the username and passwords are ok */
	private boolean fieldsOk() {
		if (dbh.userExists(eUsername.getText().toString())) {
			String msg = "User already exists";
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
		}
		if (!ePassword1.getText().toString()
				.equals(ePassword2.getText().toString())) {
			String msg = "Passwords doesn't match";
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
			return false;
		}
		if (ePassword1.getText().toString().length() < 4) {
			String msg = "Password has to be at least 4 characters";
			Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT)
					.show();
		}
		return true;
	}
}
