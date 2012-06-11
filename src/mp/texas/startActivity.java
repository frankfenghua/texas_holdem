package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;


public class startActivity extends Activity 
{

	Button neuesSpiel;
	Button profil;
	Button statistiken;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
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
