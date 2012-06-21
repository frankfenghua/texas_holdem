package mp.texas;

import java.io.File;
import java.util.ArrayList;

import android.app.Application;
import android.graphics.drawable.Drawable;
import android.net.Uri;

public class App extends Application 
{
	
	public String ProfilName="Reiner Zufall";
	public Drawable ProfilBild=null; 
	
	public boolean singlegame;
	public int AnzahlSpieler;
	public int Startkapital;
	public String BlindsArt;
	public int BlindsWert;
	public int BigBlind;
	public ArrayList<Spieler> Mitspieler= new ArrayList<Spieler>();
	public int GegnerLevel;
	public Pokerspiel pokerspiel;
}
