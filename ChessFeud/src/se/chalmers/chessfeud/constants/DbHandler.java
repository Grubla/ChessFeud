package se.chalmers.chessfeud.constants;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
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
	
	List pairs;
	

	public DbHandler() {
		client = new DefaultHttpClient();
		httpPost = new HttpPost("46.239.99.71:8080/ChessFeudServer/DbHandler/*");
		is = null;
		pairs = new ArrayList();
		String username = "twister";
		String pass = "awesomeness";
		String email = "1336@grubla.n00b";
		pairs.add(new BasicNameValuePair("tag", "addUser"));
		pairs.add(new BasicNameValuePair("username", username));
		pairs.add(new BasicNameValuePair("password", pass));
		pairs.add(new BasicNameValuePair("email", email));
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			HttpResponse response = client.execute(httpPost);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		
		}
		
	}
	/**
	 * Contacts the server and tells it to save a new user, returns false if the player couldnt be added.
	 * @param email
	 * @param userName
	 * @param password
	 * @return
	 */
	public boolean addUser(String email, String userName, String password ) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "addUser"));
		pairs.add(new BasicNameValuePair("email", email));
		pairs.add(new BasicNameValuePair("username", userName));
		pairs.add(new BasicNameValuePair("password", password));

		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			return(sc.nextBoolean());
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
	 * Gives the server a username and returns the current statistics from that user in a String with / between all the different stats, which is w/l/d/numberofmoves.
	 * @param userName
	 * @return
	 */
	public String getStats(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "getStats"));
		pairs.add(new BasicNameValuePair("username", userName));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				sb.append(sc.next());
			}
			if(sb.toString() == null) {
				return "";
			}
			return sb.toString();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	/**
	 * Contacts the database and request a list of all ongoing games, returns a list of all the games, an empty list if there are no games and null if something went wrong.
	 * @param userName
	 * @return
	 */
	public List<String> getGames(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "getGames"));
		pairs.add(new BasicNameValuePair("username", userName));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			StringBuilder sb = new StringBuilder();
			while(sc.hasNext()){
				sb.append(sc.next());
			}
			if(sb.toString() == null) {
				return new ArrayList();
			}
			List<String> games = new ArrayList();
			String[] dbGames = sb.toString().split(":");
			for(String s : dbGames) {
				games.add(s);
			}
			return games;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
			return null;
		} catch (ClientProtocolException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		
	}
	/**
	 * Increments the wins for a user in the database, returns false if something went wrong.
	 * @param userName
	 * @return
	 */
	public boolean incWins(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incWins"));
		pairs.add(new BasicNameValuePair("username", userName));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			return sc.nextBoolean();
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
	 * Increments the draws for a user in the databse, returns false if something went wrong.
	 * @param userName
	 * @return
	 */
	public boolean incDraws(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incDraws"));
		pairs.add(new BasicNameValuePair("username", userName));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			return sc.nextBoolean();
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
	 * Increments the losses for a user in the database, returns false if something went wrong.
	 * @param userName
	 * @return
	 */
	public boolean incLosses(String userName) {
		pairs.clear();
		pairs.add(new BasicNameValuePair("tag", "incLosses"));
		pairs.add(new BasicNameValuePair("username", userName));
		
		try {
			httpPost.setEntity(new UrlEncodedFormEntity(pairs));
			HttpResponse response = client.execute(httpPost);
			HttpEntity entity = response.getEntity();
			is = entity.getContent();
			Scanner sc = new Scanner(is);
			return sc.nextBoolean();
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
	
	
}
