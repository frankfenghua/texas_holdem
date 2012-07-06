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
	
	public static String ProfilName="Reiner Zufall";
	public static Drawable ProfilBild=null; 
	
	public static boolean newOpenedGame = true;
	public static boolean singlegame;
	public static int AnzahlSpieler;
	public static int Startkapital;
	public static String BlindsArt;
	public static int BlindsWert;
	public static int BigBlind;
	public static ArrayList<Spieler> Mitspieler= new ArrayList<Spieler>();
	public static int GegnerLevel;
	
	
	public static ArrayList<Pokerspiel> offeneSpiele;
	public static String aktuellesSpielID;
	
	public static Pokerspiel pokerspiel;
	public static Spieler selbst;

	public static void addSpieler(String neuerSpielerarg)
	{
		Log.d("Button", "hier drin 0");
		Spieler sp = new Spieler(neuerSpielerarg, App.Startkapital);
		Log.d("Button", "hier drin");
		Mitspieler.add(sp);
		Log.d("Button", "hier drin 2");
	}
	
	public static void addOpenGame(String namearg, int anzahlSpielerarg, int startkapitalarg, String blindArtarg, int blindsWertarg, int bigBlindarg){
		Pokerspiel neues = new Pokerspiel();
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
		
		int size = pokerspiel.getAlleSpieler().size();
	
		for(int i=0; i<=size; i++)
		{
				s = s + "," +  pokerspiel.getAlleSpieler().get(i);  //sind Element 15, 16, ....
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
	
}
