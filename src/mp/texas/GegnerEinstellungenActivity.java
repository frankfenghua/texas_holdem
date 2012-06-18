package mp.texas;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SeekBar;
import android.widget.TextView;


public class GegnerEinstellungenActivity extends Activity 
{

	Button spielStarten;
	SeekBar gegnerLevel;
	TextView text;
	TextView menschlicheGegner;
	LinearLayout layoutGegner;
	LinearLayout layoutMain;
	App app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gegnereinstellungen);
		app=(App)getApplication();
		text=new TextView(this);
		text.setText("Hallo");
		
		menschlicheGegner=(TextView)findViewById(R.id.textViewGegnerMenschlich);
		layoutMain=(LinearLayout)findViewById(R.id.GegnerMainLayout);
		
		
				if(app.singlegame==false)
		{
		    layoutMain.addView(produceLayoutGegner(),1);
		}
				else{
			menschlicheGegner.setVisibility(View.INVISIBLE);
		}
		
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
		gegnerLevel.setMax(100);
	}
	
	public LinearLayout produceLayoutGegner()
	{
		LinearLayout temp=new LinearLayout(this);
		temp.setOrientation(LinearLayout.VERTICAL);
	
		temp.setBackgroundColor(R.color.Grau);
		
				
		RelativeLayout gegnerLayout=new RelativeLayout(getApplicationContext());
		//HIER FOR SCHLEIFE ÜBER ALLE MITSPIELER
		ImageView bild=new ImageView(this);
		CheckBox check=new CheckBox(this);
		
		bild.setImageResource(R.drawable.as);
		bild.setAdjustViewBounds(true);
		bild.setScaleType(ScaleType.CENTER_INSIDE);
		bild.setMaxHeight(80);
		bild.setId(1);
		
		TextView name=new TextView(this);
		name.setText("SPIELERNAME");
		//gegnerLayout.setOrientation(LinearLayout.HORIZONTAL);
		LayoutParams params=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, 1);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		gegnerLayout.addView(bild);
		gegnerLayout.addView(name,params);
		 
		LayoutParams params2=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params2.addRule(RelativeLayout.CENTER_VERTICAL);
		gegnerLayout.addView(check,params2);
		check.setChecked(true);
		gegnerLayout.setGravity(Gravity.CENTER_VERTICAL+Gravity.FILL_HORIZONTAL);

		temp.addView(gegnerLayout);
		//HIER ENDE DER WHILESCHLEIFE
		
		return temp;
		
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
