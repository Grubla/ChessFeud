package se.chalmers.chessfeud.constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import android.content.Context;
import android.os.Environment;

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
	 * If there isnt an instance, i creates an instance and initiates it, else
	 * it just returns the instance.
	 * 
	 * @return
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
	 * 
	 * @return username
	 */
	public String getUserName() {
		loadInfoFromFile();
		return userName;
	}

	/**
	 * Returns the encrypted password of the currently logged in player.
	 * 
	 * @return
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
		} catch (NullPointerException e) {
			loggedIn = false;
		}

	}

	/**
	 * Returns if the user is currently logged in.
	 * 
	 * @return
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
	 * @param password
	 * @return
	 */
	public boolean login(String userName, String password, Context c) {
		boolean suc = DbHandler.getInstance().login(userName, password);
		System.out.println("AAA"+suc);
		if (suc) {
			
			try {
				System.out.println("0");
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
