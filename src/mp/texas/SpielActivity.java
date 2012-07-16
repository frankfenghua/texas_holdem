package mp.texas;


import java.util.Timer;
import java.util.TimerTask;

import mp.texas.push.PushService;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings.Secure;
import android.text.Editable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;


public class SpielActivity extends Activity 
{

	int pot;
	boolean noupdate=true;
	static boolean redraw = true;
	TextView potText;
	//int need;
	//int blind;
	Spieler derSpieler; //DIES IST DER SPIELER AM GERÄT
	ImageView meineKarte1;
	ImageView meineKarte2;
	ImageView gemeinschaftsKarte1;
	ImageView gemeinschaftsKarte2;
	ImageView gemeinschaftsKarte3;
	ImageView gemeinschaftsKarte4;
	ImageView gemeinschaftsKarte5;
	Timer myTimer = new Timer();
	
	int mainspielernummer=-1; 
		
	LinearLayout[] gegnerLayout;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.spielneu);
		
		potText=(TextView)findViewById(R.id.textViewSpielPot);
		gegnerLayout=new LinearLayout[7];
		gegnerLayout[0]=(LinearLayout)findViewById(R.id.LinearLayoutGegner1);
		gegnerLayout[1]=(LinearLayout)findViewById(R.id.LinearLayoutGegner2);
		gegnerLayout[2]=(LinearLayout)findViewById(R.id.LinearLayoutGegner3);
		gegnerLayout[3]=(LinearLayout)findViewById(R.id.LinearLayoutGegner4);
		gegnerLayout[4]=(LinearLayout)findViewById(R.id.LinearLayoutGegner5);
		gegnerLayout[5]=(LinearLayout)findViewById(R.id.LinearLayoutGegner6);
		gegnerLayout[6]=(LinearLayout)findViewById(R.id.LinearLayoutGegner7);
		gemeinschaftsKarte1=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte1);
		gemeinschaftsKarte2=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte2);			
		gemeinschaftsKarte3=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte3);
		gemeinschaftsKarte4=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte4);
		gemeinschaftsKarte5=(ImageView) findViewById(R.id.ImageViewSpielGemeinsschaftskarte5);
		meineKarte1=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte1);
		meineKarte2=(ImageView) findViewById(R.id.ImageViewSpielMeineKarte2);
	}



	        private void TimerMethod()
	        {
	        this.runOnUiThread(Timer_Tick);
	        }
	        
	        
	        
	        private Runnable Timer_Tick = new Runnable() 
	        {
	        	public void run() 
	        	{
					App.pokerspiel.setSinglePlayer(false);
	        		if(App.pokerspiel.isSinglePlayer()==true)
	        		{
	        			if((App.pokerspiel.getAktiverSpieler().getProfil().getId()!=App.selbst.getProfil().getId())||(App.interacted==true))
	        			{
	        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
	        				draw();
	        			}
	        			else
	        			{
	        				if((App.pokerspiel.getEinsatz()==App.pokerspiel.getAktiverSpieler().getChipsImPot())&&(App.pokerspiel.getEinsatz()!=0))	        				{
	            				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
		        				draw();
	        				}
	        				else
	        				{
	        					Toast.makeText(getApplicationContext(), "Sie sind an der Reihe", Toast.LENGTH_SHORT).show();          
	        				}
	        			}
	        		}
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		if(App.pokerspiel.isSinglePlayer()==false)
	        		{
	        			
	        			
	        			//ICH BIN DRAN UND HABE NICHT GESETZT
	        			Log.d("adsadsasd",App.pokerspiel.getAktiverSpieler().getProfil().getId());
	        			Log.d("adsadsasd",App.selbst.getProfil().getId());

	        			if((App.pokerspiel.getAktiverSpieler().getProfil().getId()==App.selbst.getProfil().getId())&&(App.interacted==false))
	        			{
        					Toast.makeText(getApplicationContext(), "Sie sind an der Reihe", Toast.LENGTH_SHORT).show();          
	        			}

	        			//ICH BIN DRAN UND HABE GESETZT
	        			if((App.pokerspiel.getAktiverSpieler().getProfil().getId()==App.selbst.getProfil().getId())&&(App.interacted==true))
	        			{	
	        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show(); 
	        				ClientPokerspielService.actionUpdate(getApplicationContext());
	        			}
	        			
	        			
	        			if((App.pokerspiel.getAktiverSpieler().getProfil().getId()=="COMPUTERGEGNER")&&(App.pokerspiel.getName()==App.selbst.getProfil().getId()))
	        			{	
	        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
	        				ClientPokerspielService.actionUpdate(getApplicationContext());
	        			}
	        			
	        			
	        		if(noupdate==false){
		        		draw();
	        		}
	        		}
	        	}
	        };
		
	    

	
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		myTimer.cancel();
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
		String deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);		//test	
 	  	App.selbst.getProfil().setId(deviceID);
 	  	Log.d("ProfilID","gesetzt");
		draw();
		myTimer=new Timer();
	    myTimer.schedule(new TimerTask() 
	        								{
	        									@Override
	        									public void run() 
	        									{
	        										TimerMethod();
	        									}

	        								}, 0, 3000);
	        
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
	
	

		//SETZENÜ EINSTELLUNGEN
		
@Override
public boolean onCreateOptionsMenu(Menu menu)
		{
			MenuInflater inflater = getMenuInflater();
			inflater.inflate(R.layout.actionmenu,menu);
			return true;
		}
		
	
	

@Override
public boolean onOptionsItemSelected(MenuItem item)
{
	switch(item.getItemId())
	{
	case R.id.itemcall:
		call();
		Log.d("Item","Call");
		break;
	case R.id.itemraise:
		Log.d("Item","Raise");
		raise();
		break;
		
	case R.id.itemfold:
		fold();
		Log.d("Item","Fold");
	}
	return true;
}

	


private void fold() {
	// TODO Auto-generated method stub
	//App.setInteracted(true);
	App.setzwert=0;
	App.setInteracted(true);
}



private void raise() {
	// TASK HIER NOCH WAS UM RICHTIG ZU RAISEN
	showDialog(2);
}



private void call() {
	// TODO Auto-generated method stub
	App.setInteracted(true);
	App.setzwert=App.pokerspiel.getEinsatz();
}



//Funktion um den Aktuellen SPielstand zu zeichnen
public void draw()
{
	//HERAUSFINDEN WELCHER SPIELER DER SPIELER AM GERÄT IST PETER
			mainspielernummer=-1;
			for(int i=0; i<App.pokerspiel.getAlleSpieler().size();i++)
			{
				if(App.pokerspiel.getAlleSpieler().get(i).profil.getName().equals(App.selbst.getProfil().getName())) //HIER DER VERGLEICH MIT DER ID
				{
					mainspielernummer=i;
					derSpieler=App.pokerspiel.getAlleSpieler().get(mainspielernummer);
					App.pokerspiel.getAlleSpieler().get(i).mainspieler=true;
					App.pokerspiel.getAlleSpieler().get(i).layoutondevice=(LinearLayout) findViewById(R.id.LinearLayoutMainSpieler);
			//		Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_LONG).show();
				}
			}
			if(mainspielernummer==-1)
			{//TASK HIER DER ÜBERGANG ZUR PLAZIERUNGSANZEIGE UND DANN ZUM MENU
			//	Toast.makeText(getApplicationContext(), "Du wurdest "+String.valueOf(App.pokerspiel.getAlleSpieler().size()+1)+".", Toast.LENGTH_LONG).show();
			//	App.pokerspiel=null;
				startActivity(new Intent(getApplicationContext(),startActivity.class));
			}
			else{
			for(int i=App.pokerspiel.getAlleSpieler().size()-1;i<7;i++)
			{
				gegnerLayout[i].setVisibility(View.INVISIBLE);
			}
			
			
			//ORDNE JEDEM GEGNER EIN LAYOUT ZU
			for(int i=0; i<App.pokerspiel.getAlleSpieler().size()-1;i++)
			{
				int tempint=mainspielernummer+i+1;
				if(tempint>=App.pokerspiel.getAlleSpieler().size())
				{
					tempint-=App.pokerspiel.getAlleSpieler().size();
				}
					App.pokerspiel.getAlleSpieler().get(tempint).layoutondevice=gegnerLayout[i];
			}
			
			
	
	
	meineKarte1.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getAlleSpieler().get(mainspielernummer).getHand().getKarte1(), meineKarte1.getWidth(), meineKarte1.getHeight(),getApplicationContext()));
	meineKarte2.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getAlleSpieler().get(mainspielernummer).getHand().getKarte2(),meineKarte2.getWidth(), meineKarte2.getHeight(), getApplicationContext()));

	gemeinschaftsKarte1.setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
	gemeinschaftsKarte2.setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
	gemeinschaftsKarte3.setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
	gemeinschaftsKarte4.setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
	gemeinschaftsKarte5.setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));

	
	
	if(App.pokerspiel.getWettrunde()>1)
	{gemeinschaftsKarte1.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getGemeinschaftskarten().getGemeinschaftskarten().get(0), gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
	gemeinschaftsKarte2.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getGemeinschaftskarten().getGemeinschaftskarten().get(1), gemeinschaftsKarte2.getWidth(), gemeinschaftsKarte2.getHeight(), getApplicationContext()));
	gemeinschaftsKarte3.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getGemeinschaftskarten().getGemeinschaftskarten().get(2), gemeinschaftsKarte3.getWidth(), gemeinschaftsKarte3.getHeight(), getApplicationContext()));
	}
	if(App.pokerspiel.getWettrunde()>2)
	{
	gemeinschaftsKarte4.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getGemeinschaftskarten().getGemeinschaftskarten().get(3), gemeinschaftsKarte4.getWidth(), gemeinschaftsKarte4.getHeight(), getApplicationContext()));
	}
	if(App.pokerspiel.getWettrunde()>3)
	{gemeinschaftsKarte5.setImageBitmap(Karte.getKartenBild(App.pokerspiel.getGemeinschaftskarten().getGemeinschaftskarten().get(4), gemeinschaftsKarte5.getWidth(), gemeinschaftsKarte5.getHeight(), getApplicationContext()));
	}
	
	
	for(int i=0;i<App.pokerspiel.getAlleSpieler().size();i++)
	{
		setName(App.pokerspiel.getAlleSpieler().get(i));
		setChips(App.pokerspiel.getAlleSpieler().get(i));
		setImPot(App.pokerspiel.getAlleSpieler().get(i));
		setZustand(App.pokerspiel.getAlleSpieler().get(i));
		setBackground(App.pokerspiel.getAlleSpieler().get(i));

	}	
	
	potText.setText(String.valueOf(App.pokerspiel.getPot()));
	}
}



//FUNKTIONEN UM DIE TEXTE UPZUDATEN

public void setName(Spieler spieler)
{
	if(spieler.mainspieler==true)
	{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
		//temp.setBackgroundColor(Color.BLUE);
		((TextView)temp.getChildAt(0)).setText(spieler.getProfil().getName());
	}
	else{
		Log.d("KUCKE HIER PETER!", "geht immer noch");
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(1));
		((TextView)temp.getChildAt(0)).setText(spieler.getProfil().getName());
	}
}


public void setChips(Spieler spieler)
{
	if(spieler.mainspieler==true)
	{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
		((TextView)temp.getChildAt(1)).setText(String.valueOf(spieler.getChips()));
	}
	else{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(1));
		((TextView)temp.getChildAt(1)).setText(String.valueOf(spieler.getChips()));
	}
}

public void setImPot(Spieler spieler)
{
	if(spieler.mainspieler==true)
	{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
		((TextView)temp.getChildAt(2)).setText(String.valueOf(spieler.getChipsImPot()));
	}
	else{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(1));
		((TextView)temp.getChildAt(2)).setText(String.valueOf(spieler.getChipsImPot()));
	}
}

public void setZustand(Spieler spieler)
{
	if(spieler.mainspieler==true)
	{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
		((TextView)temp.getChildAt(3)).setText(spieler.getZustand());
	}
	else{
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(1));
		((TextView)temp.getChildAt(3)).setText(spieler.getZustand());
	}
}


public void setBackground(Spieler spieler)
{
	
		if(spieler.equals(App.pokerspiel.getAktiverSpieler()))
		{Log.d("Farbe","grau");
			spieler.layoutondevice.setBackgroundColor(Color.DKGRAY);}

	else{
		spieler.layoutondevice.setBackgroundColor(Color.TRANSPARENT);
		Log.d("Farbe","transparent");
	}
}




@Override
public void onBackPressed() {
	// TODO Auto-generated method stub
	showDialog(1);
}





protected Dialog onCreateDialog(int id) {
    
	final Dialog dialogRaise = new Dialog(this);
	dialogRaise.setContentView(R.layout.raisedialog);
	dialogRaise.setTitle("Raise");
	
	final EditText text = (EditText) dialogRaise.findViewById(R.id.editTextRaise);
	text.setText(String.valueOf(App.pokerspiel.getEinsatz()));
	Button button=(Button) dialogRaise.findViewById(R.id.buttonDialogRaise);
	button.setOnClickListener(new OnClickListener() {
		
		public void onClick(View v) {
			dialogRaise.dismiss();
			Log.d("Was kommt da raus",text.getText().toString());
			if(text.getText().toString().length()>0)
			{
				App.setzwert=Integer.parseInt(text.getText().toString());
				App.setInteracted(true);
			}
		}
	});
	

	
	AlertDialog.Builder builder = new AlertDialog.Builder(this);
	builder.setMessage("Are you sure you want to exit?")
	       .setCancelable(false)
	       .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                SpielActivity.this.finish();
	           }
	       })
	       .setNegativeButton("No", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                dialog.cancel();
	           }
	       });
	AlertDialog alert = builder.create();

if(id==1)
{
	return alert;
}

if(id==2)
{
	return dialogRaise;
}

return null;
}







	
}
