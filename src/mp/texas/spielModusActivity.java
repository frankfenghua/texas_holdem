package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.database.DataSetObserver;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;


public class spielModusActivity extends Activity 
{

	Button einzelspieler;
	Button mehrspielerNeu;
	Button mehrspielerBeitreten;
	App app;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spielmodus);
		app=(App)getApplication();
		einzelspieler=(Button) findViewById(R.id.buttonSpielModusEinzelspieler);
		einzelspieler.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Einzelspielerspiel"); 
					App.singlegame=true;
					startActivity(new Intent(getApplicationContext(),SpielEinstellungenActivity.class));
					
					}         
				});
				
		mehrspielerNeu=(Button) findViewById(R.id.buttonSpielModusMehrspielerNeu);
		mehrspielerNeu.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Mehrspieler Neu"); // Perform action on click
					app.singlegame=false;
					startActivity(new Intent(getApplicationContext(),SpielEinstellungenActivity.class));
		
					}         
				});
		
		mehrspielerBeitreten=(Button) findViewById(R.id.buttonModusSpielBeitreten);
		mehrspielerBeitreten.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{               
						Log.d("Button", "Mehrspieler Beitreten"); // Perform action on click
						app.singlegame=false;
						startActivity(new Intent(getApplicationContext(),BeitretenActivity.class));

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

