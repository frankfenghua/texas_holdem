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
import android.widget.ImageView.ScaleType;
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
	Spieler derSpieler; //DIES IST DER SPIELER AM GER�T
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
		for(int n=0; n<7;n++)
		{
		((ImageView)gegnerLayout[n].getChildAt(0)).setImageBitmap(Karte.getKartenBild(0,0, gemeinschaftsKarte1.getWidth(), gemeinschaftsKarte1.getHeight(), getApplicationContext()));
		}
	}



	        private void TimerMethod()
	        {
	        this.runOnUiThread(Timer_Tick);
	        }
	        
	        
	        
	        private Runnable Timer_Tick = new Runnable() 
	        {
	        	public void run() 
	        	{
<<<<<<< HEAD
//					App.pokerspiel.setSinglePlayer(false);
=======
>>>>>>> origin/michael5
	        		if(App.pokerspiel.isSinglePlayer()==true)
	        		{

	        			if(App.pokerspiel.getAktiverSpieler().getProfil().getId()!=App.selbst.getProfil().getId())
	        			{	//ICH BIN NICHT DER AKTIVE SPIELER
	        				Log.d("Fall","NICHT ICH");
	        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
	        				draw();
	        			}
	        			
	        			else
	        			{
	        				//ICH BIN DER AKTIVE SPIELER
	        				if(App.interacted==true)
	        				{
		        				Log.d("Fall","1");
		        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
		        				draw();
	        				}			
	        				
	        				
	        				
	        				else
	        				{
	        					int anzahlaktive=0;
	        					for(Spieler n:App.pokerspiel.getAlleSpieler())
	        					{
	        						if(n.isNochDrin()==true)
	        						{anzahlaktive++;}
	        					}
	        					
	        					if((App.pokerspiel.getAktiverSpieler().getSidepot()!=0)||(App.pokerspiel.getWettrunde()==0)||(anzahlaktive==1)||((App.pokerspiel.getAktiverSpieler().schongesetzt==true)&&(App.pokerspiel.getAktiverSpieler().getChipsImPot()==App.pokerspiel.getEinsatz())))

	        					{ //MAN IST EINZIGSTER SPIELER NOCH IN DER WETTRUNDE, ODER MAN IST WIEDER DRAN HAT ABER SCHON GESETZT UND IST AUF DEM EINSATZ
		        					          
		        					Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show();          
			        				draw();          

	        					}
	        					else
	        					{
		        					Toast.makeText(getApplicationContext(), "Sie sind an der Reihe", Toast.LENGTH_SHORT).show();  

	        					}
	    
	        					
	        					
	        				}
	        				
	        		
	        			}
	        		}
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		
	        		if(App.pokerspiel.isSinglePlayer()==false)
	        		{
	        			
	        			
	        			//ICH BIN DRAN 
	        			if(App.pokerspiel.getAktiverSpieler().getProfil().getId()==App.selbst.getProfil().getId())
	        			{
	        				if(App.interacted==false)
	        				{
	        					int anzahlaktive=0;
	        					for(Spieler n:App.pokerspiel.getAlleSpieler())
	        					{
	        						if(n.isNochDrin()==true)
	        						{anzahlaktive++;}
	        					}
	        					if((App.pokerspiel.getAktiverSpieler().getSidepot()!=0)||(App.pokerspiel.getWettrunde()==0)||(anzahlaktive==1)||((App.pokerspiel.getAktiverSpieler().schongesetzt==true)&&(App.pokerspiel.getAktiverSpieler().getChipsImPot()==App.pokerspiel.getEinsatz())))
	        					{
			        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show(); 
			        				ClientPokerspielService.actionUpdate(getApplicationContext());
	        					}
	        					else
	        					{
		        					Toast.makeText(getApplicationContext(), "Sie sind an der Reihe", Toast.LENGTH_SHORT).show();  
	        					}
	        				}
	        				else
	        				{
		        				Toast.makeText(getApplicationContext(), App.pokerspiel.spielablauf(),Toast.LENGTH_SHORT).show(); 
		        				ClientPokerspielService.actionUpdate(getApplicationContext());
	        				}
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
		//String deviceID = Secure.getString(this.getContentResolver(), Secure.ANDROID_ID);		//test	
 	  	//App.selbst.getProfil().setId(deviceID);
 	  	//Log.d("ProfilID","gesetzt");
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
		draw();
		myTimer=new Timer();
	    myTimer.schedule(new TimerTask() 
	        								{
	        									@Override
	        									public void run() 
	        									{
	        										TimerMethod();
	        									}

	        								}, 1000, 3600);
	        
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
	
	

		//SETZEN� EINSTELLUNGEN
		
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
		App.call=true;
		Log.d("Item","Call");
		break;
	case R.id.itemraise:
		Log.d("Item","Raise");
		raise();
		App.call=false;
		break;
		
	case R.id.itemfold:
		fold();
		App.call=false;
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

			
			//HERAUSFINDEN WELCHER SPIELER DER SPIELER AM GER�T IST PETER
			mainspielernummer=-1;
			for(int i=0; i<App.pokerspiel.getAlleSpieler().size();i++)
			{
				if(App.pokerspiel.getAlleSpieler().get(i).profil.getName()==App.selbst.getProfil().getName()) //HIER DER VERGLEICH MIT DER ID
				{
					mainspielernummer=i;
					derSpieler=App.pokerspiel.getAlleSpieler().get(mainspielernummer);
					App.pokerspiel.getAlleSpieler().get(i).mainspieler=true;
					App.pokerspiel.getAlleSpieler().get(i).layoutondevice=(LinearLayout) findViewById(R.id.LinearLayoutMainSpieler);
			//		Toast.makeText(getApplicationContext(), String.valueOf(i), Toast.LENGTH_LONG).show();
				}
			}
			if(mainspielernummer==-1)
			{//TASK HIER DER �BERGANG ZUR PLAZIERUNGSANZEIGE UND DANN ZUM MENU
			//	Toast.makeText(getApplicationContext(), "Du wurdest "+String.valueOf(App.pokerspiel.getAlleSpieler().size()+1)+".", Toast.LENGTH_LONG).show();
			//	App.pokerspiel=null;
			//	startActivity(new Intent(getApplicationContext(),startActivity.class));
			}
			else{
			for(int i=App.pokerspiel.getAlleSpieler().size()-1;i<7;i++)
			{
				gegnerLayout[i].setVisibility(View.INVISIBLE);
			}
			
			
			//ORDNE JEDEM GEGNER EIN LAYOUT ZU
			for(int i=0; i<Math.min(App.pokerspiel.getAlleSpieler().size()-1,4);i++)
			{
				int tempint=mainspielernummer+i+1;
				if(tempint>=App.pokerspiel.getAlleSpieler().size())
				{
					tempint-=App.pokerspiel.getAlleSpieler().size();
				}
					App.pokerspiel.getAlleSpieler().get(tempint).layoutondevice=gegnerLayout[i];
			}
			if(App.pokerspiel.getAlleSpieler().size()==6)
			{
				for(int i=0; i<Math.min(App.pokerspiel.getAlleSpieler().size()-1,4);i++)
				{
					int tempint=mainspielernummer+i+2;
					if(tempint>=App.pokerspiel.getAlleSpieler().size())
					{
						tempint-=App.pokerspiel.getAlleSpieler().size();
					}
						App.pokerspiel.getAlleSpieler().get(tempint).layoutondevice=gegnerLayout[i];
				}
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+1)).layoutondevice=gegnerLayout[4];

				
			}
			if(App.pokerspiel.getAlleSpieler().size()==7)
			{
				for(int i=0; i<Math.min(App.pokerspiel.getAlleSpieler().size()-1,4);i++)
				{
					int tempint=mainspielernummer+i+3;
					if(tempint>=App.pokerspiel.getAlleSpieler().size())
					{
						tempint-=App.pokerspiel.getAlleSpieler().size();
					}
						App.pokerspiel.getAlleSpieler().get(tempint).layoutondevice=gegnerLayout[i];
				}
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+1)).layoutondevice=gegnerLayout[4];
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+2)).layoutondevice=gegnerLayout[5];
				
			}
			if(App.pokerspiel.getAlleSpieler().size()==8)
			{
				for(int i=0; i<Math.min(App.pokerspiel.getAlleSpieler().size()-1,4);i++)
				{
					int tempint=mainspielernummer+i+4;
					if(tempint>=App.pokerspiel.getAlleSpieler().size())
					{
						tempint-=App.pokerspiel.getAlleSpieler().size();
					}
						App.pokerspiel.getAlleSpieler().get(tempint).layoutondevice=gegnerLayout[i];
				}
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+1)).layoutondevice=gegnerLayout[4];
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+2)).layoutondevice=gegnerLayout[5];
				App.pokerspiel.getAlleSpieler().get(verschieben(mainspielernummer+3)).layoutondevice=gegnerLayout[6];

				
			}

				int anzahlaktive=0;
				for(Spieler n:App.pokerspiel.getAlleSpieler())
				{
					if(n.isNochDrin()==true)
					{anzahlaktive++;}
				}
			
			
			if((App.pokerspiel.getWettrunde()==4)&&(((App.pokerspiel.getAktiverSpieler().schongesetzt==true)&&(App.pokerspiel.getAktiverSpieler().getChipsImPot()==App.pokerspiel.getEinsatz())))&&(anzahlaktive!=1))
			{
				for(Spieler n:App.pokerspiel.getAlleSpieler())
				{
					if(n.isNochDrin()==true)
					{
						drawCardShowDown(n);
					}
				}
			}
	else{
		for(Spieler n:App.pokerspiel.getAlleSpieler())
		{
				removeCardShowDown(n);	
		}
		for(int i=0;i<App.pokerspiel.getAlleSpieler().size();i++)
		{
			setName(App.pokerspiel.getAlleSpieler().get(i));
			setChips(App.pokerspiel.getAlleSpieler().get(i));
			setImPot(App.pokerspiel.getAlleSpieler().get(i));
			setZustand(App.pokerspiel.getAlleSpieler().get(i));
			setBackground(App.pokerspiel.getAlleSpieler().get(i));

		}	
		
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
	
	

	
	potText.setText(String.valueOf(App.pokerspiel.getPot()));
	}
}


public int verschieben(int zahl)
{
if(zahl>=App.pokerspiel.getAlleSpieler().size())
{
	zahl-=App.pokerspiel.getAlleSpieler().size();
}
return zahl;
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
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
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
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
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
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
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
		LinearLayout temp=((LinearLayout)spieler.layoutondevice.getChildAt(2));
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


public void drawCardShowDown(Spieler spieler)
{
	if(spieler.mainspieler==false)
	{
<<<<<<< HEAD
	((ImageView)(spieler.layoutondevice.getChildAt(0))).setImageBitmap(Karte.getKartenBild(spieler.getHand().getKarte1(), meineKarte1.getWidth(), meineKarte1.getHeight(),getApplicationContext()));
	spieler.layoutondevice.addView(new ImageView(getApplicationContext()), 1);
	((ImageView)(spieler.layoutondevice.getChildAt(1))).setImageBitmap(Karte.getKartenBild(spieler.getHand().getKarte2(), meineKarte1.getWidth(), meineKarte2.getHeight(),getApplicationContext()));
	spieler.layoutondevice.getChildAt(2).setVisibility(View.INVISIBLE);
	((ImageView)(spieler.layoutondevice.getChildAt(1))).setMaxWidth(spieler.layoutondevice.getChildAt(0).getWidth());
=======
	((ImageView)(spieler.layoutondevice.getChildAt(0))).setImageBitmap(Karte.getKartenBild(spieler.getHand().getKarte1(), ((ImageView)(spieler.layoutondevice.getChildAt(0))).getWidth(), ((ImageView)(spieler.layoutondevice.getChildAt(0))).getHeight(),getApplicationContext()));
	((ImageView)(spieler.layoutondevice.getChildAt(1))).setImageBitmap(Karte.getKartenBild(spieler.getHand().getKarte2(), ((ImageView)(spieler.layoutondevice.getChildAt(0))).getWidth(), ((ImageView)(spieler.layoutondevice.getChildAt(0))).getHeight(),getApplicationContext()));
	
	((ImageView)(spieler.layoutondevice.getChildAt(1))).setVisibility(View.VISIBLE);
	spieler.layoutondevice.getChildAt(2).setVisibility(View.GONE);
>>>>>>> origin/michael5
	}
}

public void removeCardShowDown(Spieler spieler)
{
	if((spieler.mainspieler==false))
	{
		if(spieler.layoutondevice.getChildAt(1).getVisibility()==View.VISIBLE)
		{
				((ImageView)(spieler.layoutondevice.getChildAt(0))).setImageBitmap(Karte.getKartenBild(0,0, meineKarte1.getWidth(), meineKarte1.getHeight(),getApplicationContext()));
				spieler.layoutondevice.getChildAt(1).setVisibility(View.GONE);
				spieler.layoutondevice.getChildAt(2).setVisibility(View.VISIBLE);
		}
		Log.d("Anzahl Childs",String.valueOf(spieler.layoutondevice.getChildCount()));

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
	text.setText(String.valueOf(App.pokerspiel.getEinsatz()+App.pokerspiel.blindBestimmer()));
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
	builder.setMessage("Spiel beenden?")
	       .setCancelable(false)
	       .setPositiveButton("Ja", new DialogInterface.OnClickListener() {
	           public void onClick(DialogInterface dialog, int id) {
	                SpielActivity.this.finish();
	           }
	       })
	       .setNegativeButton("Nein", new DialogInterface.OnClickListener() {
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
