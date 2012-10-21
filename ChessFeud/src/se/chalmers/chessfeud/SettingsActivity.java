package se.chalmers.chessfeud;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

import se.chalmers.chessfeud.constants.C;
import android.app.Activity;
import android.content.Context;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;

public class SettingsActivity extends Activity{
    private CheckBox helptipSwitch;
    private CheckBox soundSwitch;
    private final AudioManager audioManager = (AudioManager) getApplicationContext()
                    .getSystemService(Context.AUDIO_SERVICE);

    private boolean helptipStatus = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		setLayout();
		if (new File(getExternalFilesDir(null), "chessfeud_settings.txt").equals(null)) {
            String fileContent = "Helptip:\t1\nSound:\t1";
            FileOutputStream fos;
            try {
                    fos = openFileOutput(C.FILENAME_SETTINGS, Context.MODE_PRIVATE);
                    fos.write(fileContent.getBytes());
                    fos.close();
            } catch (FileNotFoundException e) {
                    Log.e(C.EXCEPTION_LOCATION_SETTINGS,
                                    "Could not find the file when trying to save new text file(?)");
            } catch (IOException e) {
                    Log.e(C.EXCEPTION_LOCATION_SETTINGS,
                                    "Error when using IO when trying to save new text file.");
            }

		}
		try {
            BufferedReader inputReader = new BufferedReader(
                            new InputStreamReader(openFileInput("chessfeud_settings")));
            Scanner sc = new Scanner(inputReader);

            // Set Helptip
            setHelptip(sc.nextInt() != 0);

            // Set Sound
            setSound(sc.nextInt() != 0);
		} catch (IOException e) {
            Log.e(C.EXCEPTION_LOCATION_SETTINGS, "Error when trying to read from text file.");
		}
	}
	
	 public void onClick(View v) {
         int id = v.getId();

         switch (id) {
         case R.id.settingsHelptipCheckBox:
                 if (helptipSwitch.isChecked()) {
                         setHelptip(false);
                 } else {
                         setHelptip(true);
                 }
                 break;
         case R.id.settingsSoundCheckBox:
                 if (soundSwitch.isChecked()) {
                         setSound(false);
                 } else {
                         setSound(true);
                 }
                 break;
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
	 private void setHelptip(boolean check) {
	         setHelptipStatus(check);
	         helptipSwitch.setChecked(check);
	
	         if (check) {
	                 changeString(C.SETTINGS_HELPTIP, 1);
	         } else {
	                 changeString(C.SETTINGS_HELPTIP, 0);
	         }
	 }
	
	 /**
	  * Method to set the state of sound. It will mute/unmute the sound depending
	  * on the boolean expression, change the state of the CheckBox and change
	  * the text file.
	  * 
	  * @param check
	  *            is the boolean expression you want to set Helptip to be.
	  */
	 private void setSound(boolean check) {
	         // Mute if checked/UnMute if unchecked
	         audioManager.setStreamMute(AudioManager.STREAM_MUSIC, !check);
	         soundSwitch.setChecked(check);
	
	         if (check) {
	                 changeString(C.SETTINGS_SOUND, 1);
	         } else {
	                 changeString(C.SETTINGS_SOUND, 0);
	         }
	 }
	
	 /**
	  * Public method to get the state of helptip. This method is used by the
	  * class that will paint the helptip so it knows if it should paint helptip
	  * or not.
	  * 
	  * @return the state of helptipstatus as a boolean.
	  */
	 public boolean getHelptipStatus() {
	         return helptipStatus;
	 }
	
	 /**
	  * Private method used to set the state of helptipstatus.
	  * 
	  * @param helptipStatus
	  *            is the state of helptipstatus as a boolean.
	  */
	 private void setHelptipStatus(boolean helptipStatus) {
	         this.helptipStatus = helptipStatus;
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
	 private String changeString(int settingsId, int newValue) {
	         String newString = "";
	         for (int rowNbr = 0; rowNbr < C.SETTINGS_NAME_LIST.length; rowNbr++) {
	                 newString += C.SETTINGS_NAME_LIST[rowNbr] + ":\t";
	                 if (rowNbr == settingsId) {
	                         newString += newValue + "\n";
	                 } else {
	                         try {
	                                 BufferedReader inputReader = new BufferedReader(
	                                                 new InputStreamReader(
	                                                                 openFileInput("chessfeud_settings")));
	                                 Scanner sc = new Scanner(inputReader);
	
	                                 for (int j = 0; j < rowNbr; j++) {
	                                         sc.nextInt();
	                                 }
	                                 newString += sc.nextInt() + "\n";
	
	                         } catch (IOException e) {
	                                 Log.e(C.EXCEPTION_LOCATION_SETTINGS,
	                                                 "Error when trying to read from text file.");
	                         }
	
	                 }
	         }
	         return newString;
	 }
	 
	 private void setLayout(){
		 helptipSwitch = (CheckBox) findViewById(R.id.settingsHelptipCheckBox);
		 soundSwitch = (CheckBox) findViewById(R.id.settingsSoundCheckBox);
	 }
}
