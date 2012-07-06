package mp.texas;

import mp.texas.push.PushService;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BeitretenActivity extends Activity {
	Button beitretenbutton;
	public static Spinner spielwahl;
	public static ArrayAdapter<String> spinnerAdapter;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beitreten);

	PushService.actionLaden(getApplicationContext());
		
	spielwahl=(Spinner)findViewById(R.id.spinnerBeitretenSpiele);
	 
	spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1); 
	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	spielwahl.setAdapter(spinnerAdapter); 
	spinnerAdapter.add("Spiele werden geladen"); 
//	spinnerAdapter.add("Spiel 1");
//	spinnerAdapter.add("Spiel 2");
//	spinnerAdapter.notifyDataSetChanged(); 
	
	spielwahl.setOnItemSelectedListener(new OnItemSelectedListener() 
	{

		public void onItemSelected(AdapterView<?> arg0, View arg1,
				int arg2, long arg3) {
			Log.d("Modus","gewählt");

			if(arg2 == 0){
				beitretenbutton.setEnabled(false);	
			}
			else 
			{
				beitretenbutton.setEnabled(true);
				App.aktuellesSpielID = App.offeneSpiele.get(arg2-1).getName();
				
			}
			
		}

		public void onNothingSelected(AdapterView<?> arg0) {
			Log.d("Modus", "Nichts gewählt");
			// TODO Auto-generated method stub
			
		}             
 
	});
	
	 beitretenbutton = (Button) findViewById(R.id.button1);		        			
		beitretenbutton.setOnClickListener(new OnClickListener() {		    				
			public void onClick(View v) 		        								
			{		        															
				startActivity(new Intent(getApplicationContext(),SpielActivity.class));
			
				// Die AUswahl im Spinner muss hier noch mitimplimentiert werden
				// Danach muss 			
				// ClientPokerspielService.actionBeitreten(getApplicationContext())
				// an das dazugehšrige SPiel (ID) geschickt werden;  //test		
				
				PushService.actionUnsubscribe(getApplicationContext()); //um sich wieder von den Themen abzumelden
				ClientPokerspielService.actionSpielBeitreten(getApplicationContext());
			}		        															
		}); 		
	 }
	 
	
	private Pokerspiel SpieleLaden()
	{
		return null;
	}
	 
}

