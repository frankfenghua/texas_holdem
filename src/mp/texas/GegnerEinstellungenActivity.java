package mp.texas;

import java.util.ArrayList;

import mp.texas.push.PushService;

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
import android.widget.ScrollView;
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
	ScrollView scroll;
	ArrayList<Profil> mitspieler=new ArrayList<Profil>();
	App app;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gegnereinstellungen);
		app=(App)getApplication();
		
		scroll=(ScrollView)findViewById(R.id.scrollViewGegnerEinstellungen);
		mitspieler.add(new Profil());
		mitspieler.add(new Profil());
		mitspieler.add(new Profil());
	
		text=new TextView(this);
		text.setText("Hallo");
		
		menschlicheGegner=(TextView)findViewById(R.id.textViewGegnerMenschlich);
		layoutMain=(LinearLayout)findViewById(R.id.GegnerMainLayout);
		
		
				if(app.singlegame==false)
		{
		    scroll.addView(produceLayoutGegner(),0);
		    		   
		}
				else{
			menschlicheGegner.setVisibility(View.INVISIBLE);
		}
				Log.d("Button", "Hier");
				ClientPokerspielService.actionSpielBeitreten(getApplicationContext());
				Log.d("Button", "Hier2");
				App.pokerspiel = new Pokerspiel();
				Log.d("Button", "Hier2.5");

				App.selbst = new Spieler();
				Log.d("Button", "Hier2.7");

				ArrayList<Spieler> spielers = App.pokerspiel.getAlleSpieler();
				Log.d("Button", "Hier3");
// 				spielers.add(selbst);
//				HIERMIT GIBT ES PROBLEME!!!!!! KEINE AHNUNG WARUM?!				
				ClientPokerspielService.actionSpielBeitreten(getApplicationContext());
				Log.d("beigetreten", App.ProfilName + " has joined the game");
		
		spielStarten=(Button) findViewById(R.id.buttonGegnerEinstellungenSpielStarten);
		spielStarten.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{   Log.d("Button", "Neues Spiel starten"); // Perform action on click 
					
						app.GegnerLevel=gegnerLevel.getProgress();
						Log.d("Level",String.valueOf(app.GegnerLevel));
						//app.Mitspieler mit SPielern füllen
						//app.pokerspiel= new Pokerspiel(app.Mitspieler, app.Startkapital, app.BlindsArt, app.BlindsWert, app.BigBlind);
						ClientPokerspielService.actionSpielErstellen(getApplicationContext());
						startActivity(new Intent(getApplicationContext(),SpielActivity.class));
					
					}         
				});
				
		gegnerLevel=(SeekBar)findViewById(R.id.seekBarGegnerEinstellungenLevel);
		gegnerLevel.setMax(100);
	}
	
	public LinearLayout produceLayoutGegner()
	{
		//ScrollView scroll=new ScrollView(getApplicationContext());
		LinearLayout temp=new LinearLayout(this);
		temp.setOrientation(LinearLayout.VERTICAL);
		
		for(Profil n : mitspieler)
		{
			temp.addView(Gegnervorschau(n));
		}
		//scroll.addView(temp);
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
	
	private RelativeLayout Gegnervorschau(Profil gegner)
	{
		RelativeLayout gegnerLayout=new RelativeLayout(getApplicationContext());
		//HIER FOR SCHLEIFE ÜBER ALLE MITSPIELER
		ImageView bild=new ImageView(this);
		CheckBox check=new CheckBox(this);
		
		bild.setImageResource(R.drawable.as); //(gegner.getImage()
		bild.setAdjustViewBounds(true);
		bild.setScaleType(ScaleType.CENTER_INSIDE);
		bild.setMaxWidth(60);
		bild.setId(1);
		
		TextView name=new TextView(this);
		name.setText("SPIELERNAME"); //gegner.getName()
		
		
		gegnerLayout.addView(bild,0);
		
		LayoutParams params=new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT);
		params.addRule(RelativeLayout.RIGHT_OF, 1);
		params.addRule(RelativeLayout.CENTER_VERTICAL);
		
		gegnerLayout.addView(name,params);
		 
		LayoutParams params2=new LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT);
		params2.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
		params2.addRule(RelativeLayout.CENTER_VERTICAL);
		gegnerLayout.addView(check,params2);
		check.setChecked(true);
		return gegnerLayout;
		
	}

}
