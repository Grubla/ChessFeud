package se.chalmers.chessfeud;

import se.chalmers.chessfeud.constants.PlayerInfo;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

/**
 * This is a class for managing the loginscreen and to check whether or not
 * somebody is logged in.
 * 
 * @author Henrik Alburg
 * 
 */
public class LoginActivity extends Activity implements OnClickListener {
	private PlayerInfo player;
	private EditText eUsername;
	private EditText ePassword;
	private Button bLogin;
	private Button bRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = PlayerInfo.getInstance();
		if (player.isLoggedIn()) {
			startActivity(new Intent(this, MainActivity.class));
		} else {
			setLoginLayout();
		}

	}

	/* Sets the login layout and binds all its elements. */
	private void setLoginLayout() {
		setContentView(R.layout.activity_login);
		eUsername = (EditText) findViewById(R.id.inputUsername);
		ePassword = (EditText) findViewById(R.id.inputPassword);
		bLogin = (Button) findViewById(R.id.login);
		bRegister = (Button) findViewById(R.id.register);

		bLogin.setOnClickListener(this);
		bRegister.setOnClickListener(this);
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
					if (player.login(eUsername.getText().toString(), ePassword
							.getText().toString(), getApplicationContext())) {
						startActivity(new Intent(LoginActivity.this,
								MainActivity.class));
					} else {
						Log.d("Login: ", "Wrong password");
					}
				}
			}.start();
		} else {
			startActivity(new Intent(this, RegisterActivity.class));
		}
	}
}
