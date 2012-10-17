package se.chalmers.chessfeud;

import se.chalmers.chessfeud.constants.DbHandler;
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
 * @author Grubla
 * 
 */
public class LoginActivity extends Activity implements OnClickListener{
	private PlayerInfo player;
	private DbHandler dbh;
	private EditText eUsername;
	private EditText ePassword;
	private Button bLogin;
	private Button bRegister;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		player = PlayerInfo.getInstance();
		dbh = DbHandler.getInstance();
		
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

	public void onClick(View v) {
		int id = v.getId();
		if(id == R.id.login){
			new Thread(){
				public void run() {
					if( player.login(eUsername.getText().toString(), ePassword.getText().toString(), getApplicationContext()) ){
						startActivity(new Intent(LoginActivity.this, MainActivity.class));
					}else{
						Log.d("Login: ", "Wrong password"); //CHange to toaster
					}
				}			
			}.start();
		}else{
			startActivity(new Intent(this, RegisterActivity.class));
		}
	}
}
