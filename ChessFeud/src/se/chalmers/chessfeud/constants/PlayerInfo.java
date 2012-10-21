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

	public boolean getHelptipStatus(Context c) {
		BufferedReader inputReader;
		try {
			inputReader = new BufferedReader(new InputStreamReader(
					c.openFileInput(C.FILENAME_SETTINGS)));
			Scanner sc = new Scanner(inputReader);
			return (sc.nextInt() != 0);
		} catch (FileNotFoundException e) {
			Log.e(C.EXCEPTION_LOCATION_SETTINGS,
					"Could not find the file when trying to save new text file(?)");
		}
		return true;
	}

	/**
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
			Log.e("PlayerInfo", "Couldnt find loginfile");
		} catch (NoSuchElementException e) {
			Log.e("PlayerInfo", "File empty");
		}
		if (userName == null || password == null) {
			userName = "";
			password = "";
		}
	}

	/**
	 * 
	 */
	public void tryLogin(Context c) {
		loadInfoFromFile(c);
		loggedIn = DbHandler.getInstance().login(userName, password);
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
