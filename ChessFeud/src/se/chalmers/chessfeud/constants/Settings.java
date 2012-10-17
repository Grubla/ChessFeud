package se.chalmers.chessfeud.constants;

import se.chalmers.chessfeud.R;
import se.chalmers.chessfeud.StatsActivity;
import android.content.Intent;
import android.view.View;
import android.widget.ToggleButton;

public class Settings {
	private static Settings instance;
	ToggleButton tooltipSwitch = (ToggleButton)findViewById(R.id.settingsHelptipCheckBox);
	ToggleButton soundSwitch = (ToggleButton)findViewById(R.id.settingsSoundCheckBox);
	
	public Settings getInstance(){
		if(instance == null){
			instance = new Settings();
		}
			return instance;
	}
	
	public void onClick(View v) {
    	int id = v.getId();
    	
    	switch(id){
    	case R.id.settingsHelptipCheckBox:
    		//TODO: Add code for handling Helptip Checkbox
    		break;
    	case R.id.settingsSoundCheckBox:
    		//TODO: Add code for handling Sound Checkbox
    		break;
    	}
	}
	
	public boolean getButtonState(){
		
	}

}
