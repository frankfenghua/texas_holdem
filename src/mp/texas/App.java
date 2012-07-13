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
	
	public static boolean spielErstellt = false; 
	public static boolean newOpenedGame = true;
	public static boolean singlegame=true;
	public static int AnzahlSpieler;
	public static int Startkapital;
	public static String BlindsArt;
	public static int BlindsWert;
	public static int BigBlind;
	public static ArrayList<Spieler> Mitspieler= new ArrayList<Spieler>();
	public static int GegnerLevel;
	
	public static ArrayList<Pokerspiel> offeneSpiele = new ArrayList<Pokerspiel>();
	public static String aktuellesSpielID;
	
<<<<<<< HEAD
	public static Pokerspiel pokerspiel = null;
	public static Spieler selbst = new Spieler();
=======
	public static Pokerspiel pokerspiel;
	public static Pokerspiel pokerspieloffline;
	public static Spieler selbst;
>>>>>>> michael

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
		
		Log.d("offeneSpiele", "neues offenes Spiel geaddet");
	}
	
	
	public static String neuesSpielErstellen(){
		
		App.pokerspiel.setBlindBetrag(App.BigBlind);
		App.pokerspiel.setBlindModus(App.BlindsArt);
		App.pokerspiel.setBlindZeitRundenWert(App.BlindsWert);
		App.pokerspiel.setName(App.aktuellesSpielID);
		App.pokerspiel.setOnlineSpiel(true);
		App.pokerspiel.setStartkapital(App.Startkapital);
		App.pokerspiel.setWettrunden(0);
		App.pokerspiel.setComputergegnerLevel(App.GegnerLevel);
		
		
		
		String s = 		 "STARTEN," + aktuellesSpielID + ","
					+ "anzahlSpieler,"+ Integer.toString(App.pokerspiel.getAlleSpieler().size()) + "," 
				   + "startkapital," + Integer.toString(App.pokerspiel.getStartkapital()) + ", "
				   + "blindsArt," + App.pokerspiel.getBlindModus() + ","
				   + "blindsWert," + App.pokerspiel.getBlindZeitRundenWert() + ","
				   + "bigBlind," + App.pokerspiel.getBlindBetrag() + ","
				   + "gegenerLevel," + App.pokerspiel.getComputergegnerLevel();
				   
		
		int size = App.pokerspiel.getAlleSpieler().size();
	
		for(int i=0; i<size; i++)
		{
				s = s + ", SpielerNr"+ Integer.toString(i+1) + "," +  App.pokerspiel.getAlleSpieler().get(i).getProfil().getName() //sind Element 15, 16, ....
					+ App.pokerspiel.getAlleSpieler().get(i).getProfil().getUri(); //wird verwendet, sobald das bild an einer Stelle gespeichert wurde
		}	
		
		return s;
	}
	
	
	public static String getNewGame(){
		String s = 		 "OPEN," + aktuellesSpielID + ","
					   + "anzahlSpieler,"+ Integer.toString(App.AnzahlSpieler) + "," 
					   + "startkapital," + Integer.toString(App.Startkapital) + ", "
					   + "blindsArt," + App.BlindsArt + ","
					   + "blindsWert," + App.BlindsWert + ","
					   + "bigBlind," + App.BigBlind + ", ENDE";
		return s;
	}
	
	
	public static void setGamestate(String s){
		
	}
	
	public static String getGamestate(){
		String s = new String();
		
		s = "UPDATE," + "";
		
		
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
