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
import android.widget.TextView;

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
        
        iLogo = (ImageView) findViewById(R.id.imageView1);
        iLogo.setOnClickListener(this);

        
        finishedGames = (ListView) findViewById(R.id.list_finishedGames);
        startedGames = (ListView) findViewById(R.id.list_ongoingGames);
        
        bMyProfile.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        
        finishedGames = (ListView) findViewById(R.id.list_finishedGames);
        startedGames = (ListView) findViewById(R.id.list_ongoingGames);
        
    }
    
    @Override
    protected void onResume() {
    	super.onResume();
    	
    	//finishedGames.setListAdapter(this, R.id.list_finishedGames, getList());
    	//startedGames.setListAdapter(this, R.id.list_ongoingGames, getList());

				try {
					HttpResponse response = client.execute(httpPost);
					//HttpEntity entity = response.getEntity();
					//is = entity.getContent();

				} catch (ClientProtocolException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
//				try {
//			        BufferedReader reader =
//			            new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
//			        StringBuilder sb = new StringBuilder();
//			        String line = null;
//			        while ((line = reader.readLine()) != null) {
//			            sb.append(line + "\n");
//			        }
//			        is.close();
//			        TextView t = (TextView)findViewById(R.id.textView1);
//			        t.setText(sb.toString());
//			    } catch (Exception e) {
//			    }
				
			}
		});
		
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
    	case R.id.imageView1:
    		startActivity(new Intent(this, PlayActivity.class));
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
    		View vRow = inflater.inflate(R.layout.menu_listitem, parent, false);
    		TextView tTurn = (TextView)vRow.findViewById(R.id.player_turn);
    		TextView blackName = (TextView) vRow.findViewById(R.id.player_black);
    		TextView whiteName = (TextView) vRow.findViewById(R.id.player_white);
    		TextView blackPawnAmount = (TextView) vRow.findViewById(R.id.nbr_pawn_black);
    		TextView whitePawnAmount = (TextView) vRow.findViewById(R.id.nbr_pawn_white);
    		//Do this for every element
    		//PLease R COME BACK TO ME
    		//String s = l.get(position);
    		//decode s
    		//allViews.setText("decoded s")
    			
    			return vRow;
    	}
    	
    }

}
