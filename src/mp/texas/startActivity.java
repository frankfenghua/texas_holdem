package mp.texas;

import com.ibm.mqtt.MqttConnect;

import mp.texas.push.*;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor; //test
import android.os.Bundle;
import android.provider.Settings.Secure; //test
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class startActivity extends Activity 
{

	Button neuesSpiel;
	Button profil;
	Button statistiken;
	Button pushButton;
	TextView target_text; //test
	String deviceID; //test
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(App.pokerspiel!=null)
		{
			startActivity(new Intent(getApplicationContext(),SpielActivity.class));
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);		//test	
 	  	((TextView) findViewById(R.id.target_text)).setText(deviceID);					//test
 	  	App.selbst.getProfil().setId(deviceID);

 	  	Editor editor = getSharedPreferences(PushService.TAG, MODE_PRIVATE).edit();		//test
		editor.putString(PushService.PREF_DEVICE_ID, deviceID);							//test
		editor.commit();																//test
		PushService.actionStart(getApplicationContext());		        				//test

		pushButton = (Button) findViewById(R.id.push_button);		        			//test
		pushButton.setOnClickListener(new OnClickListener() {		    				//test	
			public void onClick(View v) 		        								//test
			{		        															//test
				PushService.actionPublish(getApplicationContext());		        		//test
			}		        															//test
		}); 																			//test
  		
		pushButton = (Button) findViewById(R.id.pokerspiel_button);		        		//test
		pushButton.setOnClickListener(new OnClickListener() {		    				//test	
			public void onClick(View v) 		        								//test
			{		        															//test
				ClientPokerspielService.actionServiceStarten(getApplicationContext());  //test																		//test
			}		        															//test
		});  					        												//test

		neuesSpiel=(Button) findViewById(R.id.button1);
		neuesSpiel.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Neues Spiel"); // Perform action on click 
					startActivity(new Intent(getApplicationContext(),spielModusActivity.class));
					}         
				});
				
		profil=(Button) findViewById(R.id.button2);
		profil.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Profil"); // Perform action on click 
					startActivity(new Intent(getApplicationContext(),profilActivity.class));
					}         
				});
	
	}
	
								
							

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}

}
