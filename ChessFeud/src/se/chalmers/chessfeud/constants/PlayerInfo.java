package se.chalmers.chessfeud.constants;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.NoSuchElementException;
import java.util.Scanner;

import android.content.Context;
import android.util.Log;

/**
 * This is a class that hold all the information about the client user. It saves
 * data to the internal memory for automatic login. It also uses the database to
 * see if the login is valid.
 * 
 * @author Simon Almgren
 * @modifiedby Henrik Alburg
 * 
 */
public class PlayerInfo {

	private static PlayerInfo instance = null;

	private String userName;
	private String password;
	private boolean loggedIn;
	private boolean soundSettings;
	private boolean helpTip;

	/**
	 * A singleton class for handling info about the player currently logged in.
	 */
	protected PlayerInfo() {
	}

	/**
	 * If there isnt an instance, it creates an instance and initiates it. else
	 * it just returns the instance.
	 * 
	 * @return the player singleton
	 */
	public static PlayerInfo getInstance() {
		if (instance == null) {
			instance = new PlayerInfo();
		}
		return instance;
	}

	/**
	 * Returns the username of the currently logged in player.
	 * 
	 * @return username the name of the user currently logged in
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * Returns the encrypted password of the currently logged in player.
	 * 
	 * @return the password for the logged in user.
	 */
	public String getPassword() {
		return password;
	}

	/*
	 * Loads the username and the password from a textfile, if there isnt any,
	 * sets loggedIn to false.
	 */
	private void loadInfoFromFile(Context c) {
		try {
			FileInputStream fis = c.openFileInput("stuff.db");
			Scanner sc = new Scanner(fis);
			this.userName = sc.nextLine();
			this.password = sc.nextLine();

		} catch (FileNotFoundException e) {
			userName = "";
			password = "";
		} catch (NoSuchElementException e) {
			Log.e("PlayerInfo", "File empty");
		}
		/* Try to read the settings, if no file: Create one. */
		BufferedReader inputReader;
		try {
			inputReader = new BufferedReader(new InputStreamReader(
					c.openFileInput(C.FILENAME_SETTINGS)));
			Scanner sc = new Scanner(inputReader);
			String[] help = sc.nextLine().split("\t");
			helpTip = !(Integer.parseInt(help[1]) == 0);
			String[] sound = sc.nextLine().split("\t");
			soundSettings = !(Integer.parseInt(sound[1]) == 0);
		} catch (FileNotFoundException e) {
			createSettingsFile(c);
		}

	}

	/* Create the file containing the settings. */
	private void createSettingsFile(Context c) {
		String fileContent = "Helptip:\t1\nSound:\t1";
		helpTip = true;
		soundSettings = true;
		saveSettingsFile(c, fileContent);
	}
	/* Saves the given string to the settingsfile */
	private void saveSettingsFile(Context c, String text) {
		FileOutputStream fos;
		try {
			fos = c.openFileOutput(C.FILENAME_SETTINGS, Context.MODE_PRIVATE);
			fos.write(text.getBytes());
			fos.close();
		} catch (FileNotFoundException e) {
			Log.e(C.EXCEPTION_LOCATION_SETTINGS,
					"Could not find the file when trying to save new text file");
		} catch (IOException e) {
			Log.e(C.EXCEPTION_LOCATION_SETTINGS,
					"Error when using IO when trying to save new text file.");
		}
	}

	/**
	 * Returns the state of sound settings
	 * 
	 * @return true if sound is enabled
	 */
	public boolean getSoundEnabled() {
		return soundSettings;
	}

	/**
	 * Returns the state of helptip
	 * 
	 * @return true if helptip is enabled
	 */
	public boolean getHelpTip() {
		return helpTip;
	}

	/**
	 * Sets the string for the settings
	 * 
	 * @param settingsId
	 *            The constant id of the setting to be changed
	 * @param newValue
	 *            The value to change it to
	 * @param c
	 *            The context allowing files to be written to
	 */
	public void setString(int settingsId, int newValue, Context c) {
		String newString = "";
		for (int rowNbr = 0; rowNbr < C.SETTINGS_NAME_LIST.length; rowNbr++) {
			newString += C.SETTINGS_NAME_LIST[rowNbr] + ":\t";
			if (rowNbr == settingsId) {
				newString += newValue + "\n";
			} else {
				try {
					BufferedReader inputReader = new BufferedReader(
							new InputStreamReader(
									c.openFileInput("chessfeud_settings")));
					Scanner sc = new Scanner(inputReader);

					for (int j = 0; j < rowNbr; j++) {
						sc.nextInt();
					}
					newString += sc.nextInt() + "\n";
					saveSettingsFile(c, newString);
				} catch (IOException e) {
					Log.e(C.EXCEPTION_LOCATION_SETTINGS,
							"Error when trying to read from text file.");
				}

			}
		}
	}

	/**
	 * Tries to load the login from a file.
	 */
	public void tryLogin(Context c) {
		loadInfoFromFile(c);
	}

	/**
	 * Returns if the user is currently logged in.
	 * 
	 * @return true if the user is loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * This method checks the username and password with the database and checks
	 * if you should be able to log in, then writes a textfile with username and
	 * password.
	 * 
	 * @param userName
	 *            , the name of the user
	 * @param password
	 *            , the wanted password by the user
	 * @return true if able to login
	 */
	public boolean login(String username, String password, Context c) {
		boolean suc = DbHandler.getInstance().login(username, password);
		if (suc) {
			this.userName = username;
			this.password = password;
			try {
				FileOutputStream out = c.openFileOutput("stuff.db",
						Context.MODE_PRIVATE);
				out.write((username + "\n").getBytes());
				out.write(password.getBytes());
				out.close();
			} catch (IOException e) {
				return true;
			}
		}
		return suc;
	}
}
