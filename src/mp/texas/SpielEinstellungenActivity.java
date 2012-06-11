package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;


public class SpielEinstellungenActivity extends Activity 
{

	Button spielStarten;
	Spinner anzahlGegner;
	Spinner blindsArt;
	EditText editTextStartkapital;
	EditText editTextBlindsWert;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spieleinstellungen);
		spielStarten=(Button) findViewById(R.id.buttonSpielEinstellungenSpielStarten);
		spielStarten.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Spiel Starten"); // Perform action on click 
					startActivity(new Intent(getApplicationContext(),GegnerEinstellungenActivity.class));
					}         
				});
				
		anzahlGegner=(Spinner) findViewById(R.id.spinnerSpielEinstellungenANzahlGegner);
		anzahlGegner.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				Log.d("Modus","Gegner anzahl gewählt");
				// TODO Auto-generated method stub
				
			}

			public void onNothingSelected(AdapterView<?> arg0) {
				Log.d("Modus", "Nichts gewählt");
				// TODO Auto-generated method stub
				
			} 
		});
			
			blindsArt=(Spinner) findViewById(R.id.spinnerSpielEinstellungenBlindsArt);
			blindsArt.setOnItemSelectedListener(new OnItemSelectedListener() 
			{

				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					Log.d("Modus","gewählt");
					// TODO Auto-generated method stub
					
				}

				public void onNothingSelected(AdapterView<?> arg0) {
					Log.d("Modus", "Nichts gewählt");
					// TODO Auto-generated method stub
					
				}  
			});
			
			editTextStartkapital=(EditText) findViewById(R.id.editTextSpielEinstellungenStartkapital);
			editTextBlindsWert=(EditText) findViewById(R.id.editTextSpielEinstellungenBlindsWert);
	
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
