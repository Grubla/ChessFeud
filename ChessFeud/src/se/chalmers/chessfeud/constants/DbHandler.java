package se.chalmers.chessfeud.constants;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

public class DbHandler {

	private HttpClient client;
	private HttpPost httpPost;
	private InputStream is;
	private List pairs;
	
	private static DbHandler instance;
	//TODO add a PlayerInfo object here

	protected DbHandler() {
	}
	public static DbHandler getInstance() {
		if(instance == null) {
			instance = new DbHandler();
			instance.init();
		}
		return instance;
	}
	/**
	 * Initiates the DbHandler and connects it to the database.
	 */
	private void init() {
		client = new DefaultHttpClient();
		httpPost = new HttpPost("http://46.239.99.71:8080/ChessFeudServer/DbHandler/*");
		is = null;
		pairs = new ArrayList();	
	}

	/**
	 * Tries to authenticate the specified username and password with an
	 * existing user on the server, returns false if it wasn't there or if
	 * something went wrong otherwise true.
	 * 
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean login(String userName, String password) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "login"));
		pairs.add(new BasicNameValuePair("username", userName));
		pairs.add(new BasicNameValuePair("password", password));
		return updateDatabase();
	}

	/**
	 * Contacts the server and tells it to save a new user, returns false if the
	 * player couldnt be added.
	 * 
	 * @param email
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean addUser(String email, String userName, String password) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "addUser"));
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("username", userName));
		pairs.add(new BasicNameValuePair("password", password));
		return (updateDatabase());
	}

	/**
	 * Contacts the database and tells it to create a new game, returns false if
	 * it failed.
	 * 
	 * @param user1
	 * @param user2
	 * @param board
	 * @return
	 */
	public boolean newGame(String user1, String user2, String board) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "addUser"));
		pairs.add(new BasicNameValuePair("user1", user1));
		pairs.add(new BasicNameValuePair("user2", user2));
		pairs.add(new BasicNameValuePair("board", board));
		return updateDatabase();
	}

	/**
	 * Contacts the database with a new gameboard for the existing game between
	 * two players.
	 * 
	 * @param user1
	 * @param user2
	 * @param newBoard
	 * @return
	 */
	public boolean newMove(String user1, String user2, String newModel) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "newMove"));
		pairs.add(new BasicNameValuePair("user1", user1));
		pairs.add(new BasicNameValuePair("user2", user2));
		pairs.add(new BasicNameValuePair("board", newModel));
		return updateDatabase();
	}

	/**
	 * Contacts the server and tells it to save a new inquirie, returns false if
	 * it couldnt be saved or if something went wrong.
	 * 
	 * @param user
	 * @param target
	 * @return
	 */
	public boolean addInquirie(String user, String target) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "addInquirie"));
		pairs.add(new BasicNameValuePair("user", user));
		pairs.add(new BasicNameValuePair("target", target));
		return updateDatabase();
	}

	/**
	 * Gives the server a username and returns the current statistics from that
	 * user in a String with / between all the different stats, which is
	 * w/l/d/numberofmoves. Returns null of something went wrong when contacting
	 * the database.
	 * 
	 * @param userName
	 * @return
	 */
	public String getStats(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "getStats"));
		pairs.add(new BasicNameValuePair("username", userName));
		String s = getFromDatabase();
		return s;
	}

	/**
	 * Contacts the database and request a list of all ongoing games, returns a
	 * list of all the games, an empty list if there are no games and null if
	 * something went wrong.
	 * 
	 * @param userName
	 * @return
	 */
	public List<String> getGames(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "getGames"));
		pairs.add(new BasicNameValuePair("username", userName));
		String s = getFromDatabase();
		if (s == null) {
			return null;
		} else if (s.equals("")) { // If something went wrong when contacting
									// the database.
			return new ArrayList<String>();
		}
		List<String> games = new ArrayList<String>();
		String[] dbGames = s.toString().split(";");
		for (String i : dbGames) {
			games.add(i);
		}
		return games;
	}

	/**
	 * Increments the wins for a user in the database, returns false if
	 * something went wrong.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean incWins(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incWins"));
		pairs.add(new BasicNameValuePair("username", userName));
		return updateDatabase();

	}

	/**
	 * Increments the draws for a user in the databse, returns false if
	 * something went wrong.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean incDraws(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incDraws"));
		pairs.add(new BasicNameValuePair("username", userName));
		return updateDatabase();
	}

	/**
	 * Increments the losses for a user in the database, returns false if
	 * something went wrong.
	 * 
	 * @param userName
	 * @return
	 */
	public boolean incLosses(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incLosses"));
		pairs.add(new BasicNameValuePair("username", userName));

		return updateDatabase();
	}

	/**
	 * Contacts the database with an update and returns true if it sucseeded or
	 * false if something went wrong along the way.
	 * 
	 * @return
	 */
	public boolean updateDatabase() {
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			boolean b = sc.nextBoolean();
			sc.close();
			return b;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return false;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Requests a String from the database, returns null if something went
	 * wrong.
	 * 
	 * @return
	 */
	public String getFromDatabase() {
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			StringBuilder sb = new StringBuilder();
			while (sc.hasNext()) {
				sb.append(sc.next());
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IllegalStateException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * A method for encrypting a string using the common md5-encryption pattern.
	 * @param s
	 * @return
	 * @throws NoSuchAlgorithmException
	 */
	public String encrypt(String s) throws NoSuchAlgorithmException {
		MessageDigest md5enc = MessageDigest.getInstance("MD5");
		md5enc.update(s.getBytes(),0,s.length());
		String encryption = new BigInteger(1, md5enc.digest()).toString(16);
		return encryption;
	}

}