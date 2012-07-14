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
	
	//Menüvariablen um später das Spiel zu erstellen
	public static int AnzahlSpieler;
	public static int Startkapital;
	public static String BlindsArt;
	public static int BlindsWert;
	public static int BigBlind;
	public static int GegnerLevel;
	
	//Menüvariable für Multiplayer
	public static ArrayList<Spieler> Mitspieler= new ArrayList<Spieler>();
	public static ArrayList<Pokerspiel> offeneSpiele = new ArrayList<Pokerspiel>();
	
	//Daten des aktuellen Spiels
	public static String aktuellesSpielID;
	public static Pokerspiel pokerspiel = null;
	public static Spieler selbst = new Spieler();


	public static void addSpieler(String neuerSpielerarg, String idarg)
	{
		Spieler sp = new Spieler(neuerSpielerarg, idarg, App.Startkapital);
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
	
	
	//FUNKTION UM EIN NEUES MULTIPLAYER SPIEL ZU ERSTELLEN
	public static String neuesSpielErstellen()
	{
		
		App.pokerspiel.setBlindBetrag(App.BigBlind);
		App.pokerspiel.setBlindModus(App.BlindsArt);
		App.pokerspiel.setBlindZeitRundenWert(App.BlindsWert);
		App.pokerspiel.setName(App.aktuellesSpielID);
		App.pokerspiel.setSinglePlayer(false);
		App.pokerspiel.setStartkapital(App.Startkapital);
		App.pokerspiel.setWettrunde(0);
		App.pokerspiel.setComputergegnerLevel(App.GegnerLevel);
//		App.pokerspiel.austeilen();
		
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
				s = s + ", SpielerNr"+ Integer.toString(i+1) + "," +  App.pokerspiel.getAlleSpieler().get(i).getProfil().getName() + ","+//sind Element 15, 16, ....
					App.pokerspiel.getAlleSpieler().get(i).getProfil().getId() + "," + App.pokerspiel.getAlleSpieler().get(i).getProfil().getUri() + "," +
					Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte1().getFarbe()) + "," +
					Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte1().getWert())  + "," +
					Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte2().getFarbe()) + "," +
					Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte2().getWert()) 
					;
		}	
		
		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";
		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";
					
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
		int size = App.pokerspiel.getAlleSpieler().size();
		
		s = "UPDATE," + aktuellesSpielID + ","+ //2
				App.pokerspiel.getName() + "," + //3
				Integer.toString(App.pokerspiel.getPot()) + "," + //4
				App.pokerspiel.getBlindModus() + "," + //wird eine Integer
				Integer.toString(App.pokerspiel.getBlindZeitRundenWert()) + "," + //6
				Integer.toString(App.pokerspiel.getEinsatz()) + "," +
				App.pokerspiel.getAktiverSpieler().getProfil().getId() + "," + //8
				App.pokerspiel.getSmallBlindSpieler().getProfil().getId() + "," +
				Integer.toString(App.pokerspiel.getBlindBetrag()) + "," + //10
				Integer.toString(App.pokerspiel.getWettrunde()) + "," + 
				App.pokerspiel.getLastRaise().getProfil().getId() + "," +//12
				Integer.toString(App.pokerspiel.getRundenzahler()) + "," +
				Integer.toString(App.pokerspiel.getComputergegnerLevel()) + "," + //14
				Integer.toString(App.pokerspiel.getAlleSpieler().size());
				;
				
				//Spieler
				
		
				for(int i=0; i<size; i++)
				{
						s = s + ", SpielerNr,"+ Integer.toString(i) + "," +  
							App.pokerspiel.getAlleSpieler().get(i).getProfil().getName() + ","+// 16
							App.pokerspiel.getAlleSpieler().get(i).getProfil().getId() + "," + App.pokerspiel.getAlleSpieler().get(i).getProfil().getUri() + "," +
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte1().getFarbe()) + "," + //18
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte1().getWert())  + "," +
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte2().getFarbe()) + "," + //20
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getHand().getKarte2().getWert())  + "," + 
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getChips()) + "," + //22
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getChipsImPot()) + "," +	
							Boolean.toString(App.pokerspiel.getAlleSpieler().get(i).isNochDrin()) + "," +	//24
							Integer.toString(App.pokerspiel.getAlleSpieler().get(i).getSidepot()) + "," +
							Long.toString(123)	//26
							;
				}	
		
				s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";
				s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";		s = s+ ",Puffer";

				
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
