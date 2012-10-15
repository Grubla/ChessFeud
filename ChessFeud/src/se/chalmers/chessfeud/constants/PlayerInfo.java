package se.chalmers.chessfeud.constants;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;

import android.os.Environment;

public class PlayerInfo {
	
	private static PlayerInfo instance = null;
	
	private String userName;
	private String password;
	
	private static File sdcard;
	private static File credentials;
	
	private boolean loggedIn = false;

	
	protected PlayerInfo() {
		loadInfoFromFile();
	}
	
	public static PlayerInfo getInstance() {
		if(instance == null) {
			instance = new PlayerInfo();
			sdcard = Environment.getExternalStorageDirectory();
			credentials = new File(sdcard, "usercred.txt");
		}
		return instance;
	}
	
	private String getUserName() {
		loadInfoFromFile();
		return userName;
	}
	
	private String getPassword() {
		loadInfoFromFile();
		return password;
	}
	private void loadInfoFromFile() {

		try {
		    BufferedReader br = new BufferedReader(new FileReader(credentials));
		    userName = br.readLine();
		    password = br.readLine();
		    loggedIn = true;
		    br.close();
		}	
		catch (IOException e) {
			loggedIn = false;
		}

		
	}
	public boolean isLoggedIn() {
		return loggedIn;
	}
	
	private boolean login(String userName, String password) {
		boolean suc = false;
		try {
			suc = DbHandler.getInstance().login(userName, password);
		} catch (NoSuchAlgorithmException e) {
			// TODO Add what to do if there is no such algoritm.
			e.printStackTrace();
		}
		if(suc) {
			BufferedWriter out;
			try {
				out = new BufferedWriter(new FileWriter(credentials));
				out.write(userName);
				out.newLine();
				out.write(password);
			} catch (IOException e) {
				// TODO Add IOexception path
				e.printStackTrace();
			}
		}
		return suc;
	}
}
