package mp.texas;

import java.io.File;

import java.util.ArrayList;
import java.util.Iterator;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;

import org.apache.http.client.utils.URLEncodedUtils;

public class App extends Application 
{
	
	public static boolean newOpenedGame = true;
	public static boolean singlegame;
	public static int AnzahlSpieler;
	public static int Startkapital;
	public static String BlindsArt;
	public static int BlindsWert;
	public static int BigBlind;
	public static ArrayList<Spieler> Mitspieler= new ArrayList<Spieler>();
	public static int GegnerLevel;
	
	public static ArrayList<Pokerspiel> offeneSpiele = new ArrayList<Pokerspiel>();
	public static String aktuellesSpielID;
	
	public static Pokerspiel pokerspiel=null;
	public static Spieler selbst;

	public static void addSpieler(String neuerSpielerarg)
	{
		Spieler sp = new Spieler(neuerSpielerarg, App.Startkapital);
		Mitspieler.add(sp);
		Log.d("addSpieler", neuerSpielerarg);
		Log.d("addSpieler", "Size of Mitspieler" + Integer.toString(Mitspieler.size()));

	}
	
	public static void addOpenGame(String namearg, int anzahlSpielerarg, int startkapitalarg, String blindArtarg, int blindsWertarg, int bigBlindarg){
		Pokerspiel neues = new Pokerspiel();
		neues.setName(namearg);
		neues.setBlindBetrag(bigBlindarg);
		neues.setBlindModus(blindArtarg);
		neues.setBlindZeitRundenWert(blindsWertarg);
		neues.setStartkapital(startkapitalarg);
		offeneSpiele.add(neues);
	}
	
	
	public static String neuesSpielErstellen(){
		String s = 		 "STARTEN," + "aktuellesSpielID" + ","
					+ "anzahlSpieler,"+ Integer.toString(App.AnzahlSpieler) + "," 
				   + "startkapital," + Integer.toString(App.Startkapital) + ", "
				   + "blindsArt," + App.BlindsArt + ","
				   + "blindsWert," + App.BlindsWert + ","
				   + "bigBlind," + App.BigBlind + ","
				   + "gegenerLevel," + App.GegnerLevel 
				   ;
		
		int size = Mitspieler.size();
	
		for(int i=0; i<size; i++)
		{
				s = s + "," +  Mitspieler.get(i).getProfil().getName();  //sind Element 15, 16, ....
		}
		
		return s;
	}
	
	
	public static String getNewGame(){
		String s = 		 "OPEN," + aktuellesSpielID + ","
					   + "anzahlSpieler,"+ Integer.toString(App.AnzahlSpieler) + "," 
					   + "startkapital," + Integer.toString(App.Startkapital) + ", "
					   + "blindsArt," + App.BlindsArt + ","
					   + "blindsWert," + App.BlindsWert + ","
					   + "bigBlind," + App.BigBlind;
		return s;
	}
	
	
	public static void setGamestate(String s){
		
	}
	
	public static String getGamestate(){
		String s = new String();
		
		
		
		return s;
	}
	
	public static String getAktuellesSpielID() {
		return aktuellesSpielID;
	}

	public static void setAktuellesSpielID(String aktuellesSpielID) {
		App.aktuellesSpielID = aktuellesSpielID;
	}
	
	public static String getKarten() {
		String s = new String();
		
		return s;
	}
	
	
}
