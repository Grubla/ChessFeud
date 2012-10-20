package se.chalmers.chessfeud.constants;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;
/**
 * This is a class that hold all the information about the client user.
 * It saves data to the internal memory for automatic login.
 * It also uses the database to see if the login is valid.
 * @author Simon Almgren
 * @modifiedby Henrik Alburg
 *
 */
public class PlayerInfo {

	private static PlayerInfo instance = null;

	private String userName;
	private String password;

	private static File sdcard;
	private static File credentials;

	private boolean loggedIn = false;

	/**
	 * A singleton class for handling info about the player currently logged in.
	 */
	protected PlayerInfo() {
		loadInfoFromFile();
	}

	/**
	 * If there isnt an instance, it creates an instance and initiates it.
	 * else it just returns the instance.
	 * @return the player singleton
	 */
	public static PlayerInfo getInstance() {
		if (instance == null) {
			instance = new PlayerInfo();
			sdcard = Environment.getExternalStorageDirectory();
			credentials = new File(sdcard, "usercred.txt");
		}
		return instance;
	}

	/**
	 * Returns the username of the currently logged in player.
	 * @return username the name of the user currently logged in
	 */
	public String getUserName() {
		loadInfoFromFile();
		return userName;
	}

	/**
	 * Returns the encrypted password of the currently logged in player.
	 * @return the password for the logged in user.
	 */
	public String getPassword() {
		loadInfoFromFile();
		return password;
	}

	/**
	 * Loads the username and the password from a textfile, if there isnt any,
	 * sets loggedIn to false.
	 */
	private void loadInfoFromFile() {

		try {
			BufferedReader br = new BufferedReader(new FileReader(credentials));
			userName = br.readLine();
			password = br.readLine();
			loggedIn = true;
			br.close();
		} catch (IOException e) {
			loggedIn = false;
		} 
	}

	/**
	 * Returns if the user is currently logged in.
	 * @return true if the user is loggedIn
	 */
	public boolean isLoggedIn() {
		return loggedIn;
	}

	/**
	 * This method checks the username and password with the database and checks
	 * if you should be able to log in, then writes a textfile with username and
	 * password.
	 * @param userName, the name of the user
	 * @param password, the wanted password by the user
	 * @return true if able to login
	 */
	public boolean login(String userName, String password, Context c) {
		boolean suc = DbHandler.getInstance().login(userName, password);
		if (suc) {
			
			try {
				FileOutputStream out = c.openFileOutput("hej.txt", Context.MODE_PRIVATE);
				out.write((userName+"\n").getBytes());
				out.write(password.getBytes());
				out.close();
			} catch (IOException e) {
				return false;
			}
		}
		return suc;
	}
}
