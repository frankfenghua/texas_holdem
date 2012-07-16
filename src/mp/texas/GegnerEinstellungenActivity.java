package mp.texas;

import java.util.ArrayList;

import mp.texas.push.PushService;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Layout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ListView;
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
	ListView listView;
	public static ArrayAdapter<String> listAdapter;
	static ArrayList<String> listItems = new ArrayList<String>();
	static boolean joinedPlayer = false;

	
	static ArrayList<Profil> mitspieler=new ArrayList<Profil>();
	App app;
	
	public static Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
        	super.handleMessage(msg);
        	String text = (String)msg.obj;
        	listItems.add(text);
        	listAdapter.notifyDataSetChanged();
           }
       
	};
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.gegnereinstellungen);
		app=(App)getApplication();

		listView=(ListView)findViewById(R.id.listViewGegnerEinstellungen);
		menschlicheGegner=(TextView)findViewById(R.id.textViewGegnerMenschlich);
		layoutMain=(LinearLayout)findViewById(R.id.GegnerMainLayout);
		
		listAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, listItems); 
		listView.setAdapter(listAdapter); 
		App.spielErstellt = true;
			
		
				if(App.singlegame==false)
				{
					//listAdapter.addView(produceLayoutGegner(),0);
				}
				else
				{
					menschlicheGegner.setVisibility(View.INVISIBLE);
				}

				if(App.singlegame==false)	
				{
					App.pokerspiel = new Pokerspiel(App.Mitspieler, App.Startkapital, App.BlindsArt, App.BigBlind, App.BlindsWert);

					if(joinedPlayer == false){
					ClientPokerspielService.actionSpielBeitreten(getApplicationContext());
					joinedPlayer = true;
					}
				}
				Log.d("beigetreten", App.selbst.getProfil().getName() + " has joined the game");


				//App.selbst = new Humanspieler();


		
		spielStarten=(Button) findViewById(R.id.buttonGegnerEinstellungenSpielStarten);
		spielStarten.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{   Log.d("Button", "Neues Spiel starten"); // Perform action on click 
					

						App.GegnerLevel=gegnerLevel.getProgress();
						Log.d("Level",String.valueOf(App.GegnerLevel));

//						ClientPokerspielService.actionSpielErstellen(getApplicationContext());

//						app.GegnerLevel=gegnerLevel.getProgress();
//						Log.d("Level",String.valueOf(app.GegnerLevel));
						//app.Mitspieler mit SPielern füllen
						//app.pokerspiel= new Pokerspiel(app.Mitspieler, app.Startkapital, app.BlindsArt, app.BlindsWert, app.BigBlind);
						if(App.singlegame==false)
						{
							ClientPokerspielService.actionSpielErstellen(getApplicationContext());
							Log.d("KUCKE","bis hier alles gut");
							ClientPokerspielService.actionUpdate(getApplicationContext());
							Log.d("KUCKE1","bis hier alles gut");

						//	App.pokerspiel.spielablauf();
							Log.d("KUCKE2","bis hier alles gut");

						//	ClientPokerspielService.actionUpdate(getApplicationContext());
							Log.d("KUCKE3","bis hier alles gut");

							startActivity(new Intent(getApplicationContext(),SpielActivity.class));

						}
						else
						{

						App.pokerspiel=new Pokerspiel(false,App.AnzahlSpieler,App.Startkapital,App.BlindsArt,App.BlindsWert,App.BigBlind,App.GegnerLevel,App.BlindsWert);
						App.pokerspiel.spielablauf();
						Log.d("SPieler im App.pokerspiel",String.valueOf(App.pokerspiel.getAlleSpieler().size()));
						startActivity(new Intent(getApplicationContext(),SpielActivity.class));
						}

					
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
