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

public class RegisterActivity extends Activity{
	private Button bRegister;
	private EditText eUsername;
	private EditText ePassword1;
	private EditText ePassword2;
	private EditText eEmail;
	
	private DbHandler dbh;
	PlayerInfo player;
	
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
		
		bRegister.setOnClickListener(new OnClickListener(){
			public void onClick(View v) {
				System.out.println("HEJEJEJ");
				if(fieldsOk()){
					boolean userAdded = dbh.addUser(eEmail.getText().toString(), eUsername.getText().toString(), ePassword1.getText().toString());
					if(userAdded){
						player.login(eUsername.getText().toString(), ePassword1.getText().toString());
						startActivity(new Intent(RegisterActivity.this, MainActivity.class));
					}else{
						Log.d("Register", "Couldnt Register");
					}
					
				}
			}
		});
	}

	private boolean fieldsOk() {
		if(dbh.userExists(eUsername.getText().toString())){
			Log.d("UserExists", eUsername.getText().toString());
		}
		System.out.println("hej");
		Log.d(ePassword1.getText().toString(), ePassword2.getText().toString());
		if(!ePassword1.getText().toString().equals(ePassword2.getText().toString())){
			//Print the passwords doesn't match
			return false;
		}
		return true;
	}
}
