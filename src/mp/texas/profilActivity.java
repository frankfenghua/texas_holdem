package mp.texas;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class profilActivity extends Activity 
{

	Button statistiken;
	ImageButton bild;
	EditText name;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);
		statistiken=(Button) findViewById(R.id.buttonProfilStatistik);
		statistiken.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Statistiken"); // Perform action on click             
					}         
				});
				
		bild=(ImageButton) findViewById(R.id.imageButtonProfilBild);
		bild.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Bild"); // Perform action on click             
					}         
				});
		
		name=(EditText) findViewById(R.id.editTextProfilName);
	
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

