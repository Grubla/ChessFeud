package se.chalmers.chessfeud;

import se.chalmers.chessfeud.utils.C;
import se.chalmers.chessfeud.utils.PlayerInfo;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;

/**
 * Is the class that save all settings specific for the user.
 * 
 * @author Sean Pavlov, Henrik Alburg
 * 
 *         Copyright (c) Sean Pavlov, Henrik Alburg 2012
 * 
 */
public class SettingsActivity extends Activity implements OnClickListener{
	private CheckBox helptipSwitch;
	private CheckBox soundSwitch;
	private PlayerInfo pi;
	private Button bAbout;
	private Button bLogout;
	private AudioManager audioManager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		audioManager = (AudioManager) getApplicationContext().getSystemService(
				Context.AUDIO_SERVICE);
		pi = PlayerInfo.getInstance();
		setLayout();
		
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		helptipSwitch.setChecked(pi.getHelpTip());
		audioManager.setStreamMute(AudioManager.STREAM_MUSIC,
				!pi.getSoundEnabled());
		soundSwitch.setChecked(pi.getSoundEnabled());
	}

	/**
	 * Will perform an action when player click on a component that will make
	 * any changes, e.g. checkboxes.
	 * 
	 * @param v
	 *            is the view.
	 */
	public void onClick(View v) {
		int id = v.getId();

		switch (id) {
		case R.id.settingsAboutButton:
			startActivity(new Intent(this, AboutActivity.class));
			break;
		case R.id.settingsHelptipCheckBox:
			if (helptipSwitch.isChecked()) {
				setHelptip(1);
			} else {
				setHelptip(0);
			}
			break;
		case R.id.settingsSoundCheckBox:
			if (soundSwitch.isChecked()) {
				setSound(1);
			} else {
				setSound(0);
			}
			break;
		case R.id.settingsLogoutButton:
			pi.logout(getApplicationContext());
			startActivity(new Intent(this, LoginActivity.class));
			break;
		default:
			Log.e("Settings", "Button not binded");
		}
	}

	/**
	 * Method to set the state of helptip. It will save the state as a private
	 * boolean that can be accessed through a public method, change the state of
	 * the CheckBox and change the text file.
	 * 
	 * @param check
	 *            is the boolean expression you want to set Helptip to be.
	 */
	private void setHelptip(int state) {
			changeString(C.SETTINGS_HELPTIP, state);
	}

	/**
	 * Method to set the state of sound. It will mute/unmute the sound depending
	 * on the boolean expression, change the state of the CheckBox and change
	 * the text file.
	 * 
	 * @param check
	 *            is the boolean expression you want to set Helptip to be.
	 */
	private void setSound(int state) {
			changeString(C.SETTINGS_SOUND, state);
	}

	/**
	 * Is a method that change the string of the settings text file. It will
	 * change the specified id with the specified new value. It will then return
	 * the new string as a string.
	 * 
	 * @param settingsId
	 *            is the id of the setting you want to change. The id is stored
	 *            in constants.
	 * @param newValue
	 *            is the new value that will override the old value of the
	 *            specified setting.
	 * @return the new string of text file after changing old value to a new one
	 *         on the specified setting.
	 */
	private void changeString(int settingsId, int newValue) {
		pi.setString(settingsId, newValue, getApplicationContext());
	}

	/* Binds all the elements and sets the listeners etc */
	private void setLayout() {
		bAbout = (Button) findViewById(R.id.settingsAboutButton);
		helptipSwitch = (CheckBox) findViewById(R.id.settingsHelptipCheckBox);
		soundSwitch = (CheckBox) findViewById(R.id.settingsSoundCheckBox);
		bLogout = (Button) findViewById(R.id.settingsLogoutButton);
		bAbout.setOnClickListener(this);
		bLogout.setOnClickListener(this);
		soundSwitch.setOnClickListener(this);
		helptipSwitch.setOnClickListener(this);
	}
}
