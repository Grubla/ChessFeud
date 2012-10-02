package se.chalmers.chessfeud;

import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;

public class MainActivity extends Activity implements OnClickListener{
	private Button bPlay, bMyProfile, bSettings, bAbout;
	private ImageView iLogo;
	private ListView finishedGames, startedGames;
	

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_main);
        
        bMyProfile = (Button) findViewById(R.id.button_myprofile);
        bSettings = (Button) findViewById(R.id.button_settings);
        bMyProfile.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        
        iLogo = (ImageView) findViewById(R.id.imageView1);
        iLogo.setOnClickListener(this);
        
        finishedGames = (ListView) findViewById(R.id.list_finishedGames);
        startedGames = (ListView) findViewById(R.id.list_ongoingGames);
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	//finishedGames.setListAdapter(this, R.id.list_finishedGames, getList());
    	//startedGames.setListAdapter(this, R.id.list_ongoingGames, getList());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.activity_main, menu);
        return true;
    }
    
    public void onClick(View v) {
    	int id = v.getId();
    	
    	switch(id){
    	case R.id.button_myprofile:
    		startActivity(new Intent(this, ProfileActivity.class));
    		break;
    	case R.id.button_settings:
    		startActivity(new Intent(this, SettingsActivity.class));
    		break;
    	default:
    		Log.d("Default", "Should not get here!");
    	}
    }
    
    private class GameListAdapter extends ArrayAdapter<String> {
    	private Context context;
    	private int resourceId;
    	
    	public GameListAdapter(Context context, int resId, List<String> l){
    		super(context, resId, new String[] {"hej", "lol"});
    		this.context = context;
    		this.resourceId = resId;
    		//To be fixed here when the view is finsihed
    	}
    	
    	@Override
    	public View getView(int position, View convertView, ViewGroup parent) {

    		LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    		View view = inflater.inflate(resourceId, parent, false);
    		//TextView opponentName = (TextView) view.findViewById(123534)
    		//Do this for every element
    		//String s = l.get(position);
    		//decode s
    		//opponentView.setText("decoded s")
    			
    			return view;
    	}
    	
    }
}
