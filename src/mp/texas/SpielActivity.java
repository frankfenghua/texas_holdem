package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class SpielActivity extends Activity 
{

	Button call;
	Button out;
	Button setzen;
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
				
		
			
			EditTextEinsatz=(EditText) findViewById(R.id.editTextSpielEinstellungenStartkapital);
			SeekBarEinsatz=(SeekBar) findViewById(R.id.seekBarSpielEinsatz);
			meineKarte1=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte1);
			meineKarte2=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte2);
			gemeinschaftsKarte1=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte1);
			gemeinschaftsKarte2=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte2);			
			gemeinschaftsKarte3=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte3);
			gemeinschaftsKarte4=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte4);
			gemeinschaftsKarte5=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte5);
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
