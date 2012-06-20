package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;


public class SpielActivity extends Activity 
{

	Button call;
	Button out;
	Button setzen;
	int pot;
	int need;
	int blind;
	TextView textPot;
	TextView textNeed;
	TextView textBlind;
	ListView gegner;
	SeekBar SeekBarEinsatz;
	EditText EditTextEinsatz;
	ImageView meineKarte1;
	ImageView meineKarte2;
	ImageView gemeinschaftsKarte1;
	ImageView gemeinschaftsKarte2;
	ImageView gemeinschaftsKarte3;
	ImageView gemeinschaftsKarte4;
	ImageView gemeinschaftsKarte5;
	Editable editable;
	

	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spiel);
		call=(Button) findViewById(R.id.buttonSpielCall);
		call.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Call"); // Perform action on click 
					}         
				});
		
		setzen=(Button) findViewById(R.id.buttonSpielSetzen);
		setzen.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Setzen"); // Perform action on click 
					}         
				});
		
		out=(Button) findViewById(R.id.buttonSpielOut);
		out.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Out"); // Perform action on click 
					}         
				});
				
		
			textPot=(TextView) findViewById(R.id.SpielPotZahl);
			textPot.setText("12345");
			textNeed=(TextView) findViewById(R.id.SpielNeedZahl);
			textNeed.setText("2345");
			textBlind=(TextView) findViewById(R.id.SpielBlindZahl);
			textBlind.setText("345");
			
			EditTextEinsatz=(EditText) findViewById(R.id.editTextSpielEinsatz);
			
			
			EditTextEinsatz.setOnEditorActionListener(new TextView.OnEditorActionListener() {
				
				public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
					// TODO Auto-generated method stub
					if(v.getText().length()!=0)
					{
					SeekBarEinsatz.setProgress(Integer.valueOf(v.getText().toString()));
					}
					return false;
				
			}});
			
			SeekBarEinsatz=(SeekBar) findViewById(R.id.seekBarSpielEinsatz);
			SeekBarEinsatz.setMax(100);
			SeekBarEinsatz.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
				
				public void onStopTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
	    	           //EditTextEinsatz.setText("55");

				}
				
				public void onStartTrackingTouch(SeekBar seekBar) {
					// TODO Auto-generated method stub
					
				}
				
				public void onProgressChanged(SeekBar seekBar, int progress,
						boolean fromUser) {
					EditTextEinsatz.setText(String.valueOf(progress));
					Log.d("SeekBar",String.valueOf(progress));
					// TODO Auto-generated method stub
					
				}
			});
			
			meineKarte1=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte1);
			meineKarte2=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte2);
			meineKarte1.setImageBitmap(Karte.getKartenBild(0, Karte.Herz, meineKarte1.getWidth(), meineKarte1.getHeight(),getApplicationContext()));
			
			meineKarte2.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz,meineKarte2.getWidth(), meineKarte2.getHeight(), getApplicationContext()));
			Log.d("Karte",String.valueOf(meineKarte1.getHeight()));
			gemeinschaftsKarte1=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte1);
			gemeinschaftsKarte2=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte2);			
			gemeinschaftsKarte3=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte3);
			gemeinschaftsKarte4=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte4);
			gemeinschaftsKarte5=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte5);
			gemeinschaftsKarte1.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
			gemeinschaftsKarte2.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz, gemeinschaftsKarte2.getWidth(), gemeinschaftsKarte2.getHeight(), getApplicationContext()));
			gemeinschaftsKarte3.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz, gemeinschaftsKarte3.getWidth(), gemeinschaftsKarte3.getHeight(), getApplicationContext()));
			gemeinschaftsKarte4.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz, gemeinschaftsKarte4.getWidth(), gemeinschaftsKarte4.getHeight(), getApplicationContext()));
			gemeinschaftsKarte5.setImageBitmap(Karte.getKartenBild(0, Karte.Kreuz, gemeinschaftsKarte5.getWidth(), gemeinschaftsKarte5.getHeight(), getApplicationContext()));
 
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



	
	
	/*
	String ns = Context.NOTIFICATION_SERVICE;
	NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
	
	int icon = R.drawable.as;
	CharSequence tickerText = "Hello";
	long when = System.currentTimeMillis();
	Notification notification = new Notification(icon, tickerText, when);
	
	Context context = getApplicationContext();
	CharSequence contentTitle = "My notification";
	CharSequence contentText = "Hello World!";
	Intent notificationIntent = new Intent(this, profilActivity.class);
	PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);
	notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);
	
	final int HELLO_ID = 1;
	mNotificationManager.notify(HELLO_ID, notification);
*/
	
}
