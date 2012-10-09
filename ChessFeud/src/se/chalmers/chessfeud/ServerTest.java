package se.chalmers.chessfeud;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.R;
import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;


public class ServerTest extends Activity {
    
	private HttpClient client;
	private HttpPost httpPost;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		System.out.println("jeh");
	}

	public boolean onTouch(View arg0, MotionEvent arg1) {
		System.out.println("hej");
		client = new DefaultHttpClient();
		httpPost = new HttpPost("localhost:8080/ChessFeudServer/TestServlet/*");
		List pairs = new ArrayList();
		String username = "twister";
		String pass = "awesomeness";
		pairs.add(new BasicNameValuePair("username", username));
		pairs.add(new BasicNameValuePair("password", pass));
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
		return false;
	}
}
