package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.Spinner;


public class spielModusActivity extends Activity 
{

	Button einzelspieler;
	Button mehrspielerNeu;
	Spinner spielwahl;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spielmodus);
		einzelspieler=(Button) findViewById(R.id.buttonSpielModusEinzelspieler);
		einzelspieler.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Einzelspielerspiel"); 
					startActivity(new Intent(getApplicationContext(),SpielEinstellungenActivity.class));
            
					}         
				});
				
		mehrspielerNeu=(Button) findViewById(R.id.buttonSpielModusMehrspielerNeu);
		mehrspielerNeu.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Mehrspieler Neu"); // Perform action on click
					startActivity(new Intent(getApplicationContext(),SpielEinstellungenActivity.class));

					
					}         
				});
		
		spielwahl=(Spinner) findViewById(R.id.spinnerSpielModusWahl);
		spielwahl.setOnItemSelectedListener(new OnItemSelectedListener() 
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

