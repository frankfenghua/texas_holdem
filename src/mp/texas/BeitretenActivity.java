package mp.texas;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;

public class BeitretenActivity extends Activity {
	public Spinner spielwahl;
	
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.beitreten);
	spielwahl=(Spinner)findViewById(R.id.spinnerBeitretenSpiele);
	 
	ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, android.R.id.text1); 
	spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
	spielwahl.setAdapter(spinnerAdapter); 
	spinnerAdapter.add("Spiele werden geladen"); 
	spinnerAdapter.add("Spiel 1");
	spinnerAdapter.add("Spiel 2");
	spinnerAdapter.notifyDataSetChanged(); 
	
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
	 
	
	private Pokerspiel SpieleLaden()
	{
		return null;
	}
	 
}

