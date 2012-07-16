package mp.texas;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import com.ibm.mqtt.MqttConnect;

import mp.texas.db.StatistikFunctions;
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
	App app;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		if(App.pokerspiel!=null)
		{
			startActivity(new Intent(getApplicationContext(),SpielActivity.class));
		}
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		app=(App)getApplication();
		String deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);		//test	
 	  	App.selbst.getProfil().setId(deviceID);
 	  	App.interacted=false;

		neuesSpiel=(Button) findViewById(R.id.button1);
		neuesSpiel.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Neues Spiel"); // Perform action on click 
<<<<<<< HEAD
					PushService.actionUnsubscribe(getApplicationContext());
					App.Mitspieler.clear();
					App.Mitspieler = new ArrayList<Spieler>();
=======
				
>>>>>>> origin/michael5
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
		
	///TEST	
//				
// 		StatistikFunctions statistikFunction1 = new StatistikFunctions();
//
//		
//	    String KEY_SUCCESS = "success";
//	    String KEY_ERROR = "error";
//						
//        JSONObject json = statistikFunction1.statistikSpeichern(deviceID, 576);
//
//         // checken der response
//         try {
////               streckenMsg.setText("Statistik erfolgreich gespeichert");
//                 String res = json.getString(KEY_SUCCESS);
//                 if(Integer.parseInt(res) == 1){
//                	 Log.d("Ergebnis speichern", KEY_SUCCESS);
//
//                 }else{
//                    // Error beim Speichern der Statistik
//                	 Log.d("Statistik nicht gespeichert", KEY_ERROR);
//                 }
//             
//         } catch (JSONException e) {
//             e.printStackTrace();
//         }
//	
////        int test = statistikFunction1.statistikLaden(deviceID);
////        Log.d("Statistik Testwert = ",String.valueOf(test));
//	
//	//Ende des Testst
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
