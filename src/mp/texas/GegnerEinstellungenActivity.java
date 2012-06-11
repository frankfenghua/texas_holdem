package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.SeekBar;


public class GegnerEinstellungenActivity extends Activity 
{

	Button spielStarten;
	ExpandableListView listView;
	SeekBar gegnerLevel;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gegnereinstellungen);
		spielStarten=(Button) findViewById(R.id.buttonGegnerEinstellungenSpielStarten);
		spielStarten.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Neues Spiel starten"); // Perform action on click 
						startActivity(new Intent(getApplicationContext(),SpielActivity.class));
					}         
				});
				

		gegnerLevel=(SeekBar)findViewById(R.id.seekBarGegnerEinstellungenLevel);
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
