package mp.texas;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Spieler 
{
	//Dies sind die Daten die online gespeichert werden müssen
	protected Profil profil = new Profil();
	private Hand hand;
	protected int chips;
	private boolean nochDrin=true;
	private String zustand=" ";
	private double anzahlZigaretten=19;
	protected int chipsImPot=0;
	private int sidepot;
	public int[] ergebnis={0,0,0,0,0,0};
	//Hier enden die online gespeicherten Daten
	
	
	//ANZEIGEPARAMETER
	public int numberondevice;
	public LinearLayout layoutondevice;
	public boolean mainspieler;
	//IST DER SPIELER AUF DEM GERÄT;
	
	
	
	
	public Spieler(Profil profilarg, int chipsarg)
	{
		profil=profilarg;
		chips=chipsarg;
		nochDrin=true;
		chipsImPot=0;
	}
	
	public Spieler(String namearg, int chipsarg)
	{
		profil.setName(namearg);
		this.setChips(chipsarg);
		nochDrin=true;
		chipsImPot=0;
	}
	
	//DAS IST DIE FUNKTION DIE AUFGERUFEN WIRD UM DEN SPIELER AM DEVICE ZU KREIEREN
	public Spieler() 
	{
		profil=new Profil();
	}
	

	
	public int zwangssetzen(int blind)
	{
		setChips(getChips()-blind);
		setChipsImPot(blind);
		return blind;
	}
	
	//Funktion die angibt wieviel gesetzt wird
	public int setzen(Pokerspiel pokerspiel)
	{
		/*if(chips>chipsarg)
		{
			chips-=chipsarg;
			chipsImPot+=chipsarg;
			return chipsarg;
		}
		
		else
		{
			return -1;
		}*/
		pokerspiel.einzahlen(300);
		return 0;
	}

	public Profil getProfil() {
		return profil;
	}

	public Hand getHand() {
		return hand;
	}

	public void setHand(Hand hand) {
		this.hand = hand;
	}

	public int getChips() {
		return chips;
	}

	public void setChips(int chips) {
		this.chips = chips;
	}

	public boolean isNochDrin() {
		return nochDrin;
	}

	public void setNochDrin(boolean nochDrin) {
		this.nochDrin = nochDrin;
	}

	public int getChipsImPot() {
		return chipsImPot;
	}

	public void setChipsImPot(int chipsImPot) {
		this.chipsImPot = chipsImPot;
	}

	public String getZustand() {
		return zustand;
	}

	public void setZustand(String zustand) {
		this.zustand = zustand;
	}

	/**
	 * @return the sidepot
	 */
	public int getSidepot() {
		return sidepot;
	}

	/**
	 * @param sidepot the sidepot to set
	 */
	public void setSidepot(int sidepot) {
		this.sidepot = sidepot;
	}
	
	/*
	public void call(int need)
	{
		Log.d("App.pokerspieltest",String.valueOf(App.pokerspiel));
		//App.pokerspiel.einzahlen(setzen(need-getChipsImPot()));
		App.pokerspiel.weiter();
	}
	 
	public void raise(int wert)
	{
		App.pokerspiel.einzahlen(setzen(wert));
		App.pokerspiel.setEinsatz(wert+chipsImPot);
		App.pokerspiel.weiter();
	}
	
	public void fold()
	{
		
	}
	
	public void auffordern(int need)
	{

	}
	*/
}
