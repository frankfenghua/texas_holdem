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
	private int PlatzShowdown=0;
	//Hier enden die online gespeicherten Daten
	
	
	//ANZEIGEPARAMETER
	public int numberondevice;
	public LinearLayout layoutondevice;
	public boolean mainspieler;

	//IST DER SPIELER AUF DEM GERÄT;
	public boolean schongesetzt=false;
	
	
	
	
	public Spieler(Profil profilarg, int chipsarg)
	{
		profil=profilarg;
		chips=chipsarg;
		nochDrin=true;
		chipsImPot=0;
	}

	
	public Spieler(String namearg, String idarg, int chipsarg, int karte1farbe, int karte1wert, int karte2farbe, int karte2wert)
	{
		profil.setId(idarg);
		Karte karte1 = new Karte(karte1farbe, karte1wert);
		getHand().setKarte1(karte1);
		Karte karte2 = new Karte(karte2farbe, karte2wert);
		getHand().setKarte1(karte2);
			
		profil.setName(namearg);
		this.setChips(chipsarg);
		nochDrin=true;
		chipsImPot=0;
	}
	

	
	public Spieler(String namearg, String idarg, int chipsarg) {
		profil.setId(idarg);
		profil.setName(namearg);
		this.setChips(chipsarg);
		nochDrin=true;
		chipsImPot=0;	}

	
	//DAS IST DIE FUNKTION DIE AUFGERUFEN WIRD UM DEN SPIELER AM DEVICE ZU KREIEREN
	public Spieler() 
	{
		profil=new Profil();
	}
	
	
	public int zwangssetzen(Pokerspiel pokerspiel, int blind)

	{
		if(blind<getChips())
		{
		setChips(getChips()-blind);
		setChipsImPot(blind);
		return blind;
		}
		else
		{
			//schongesetzt=true;
			Log.d("Zwangssetzen",String.valueOf(getChips()));
			setChipsImPot(getChips());
			setChips(0);
			setZustand("All In");
			setSidepot(pokerspiel.getPot()+getChipsImPot());//da pot erst anschließend aufgefüllt
			return getChipsImPot();
		}
	}
	
	//Funktion die angibt wieviel gesetzt wird
	public int setzen(Pokerspiel pokerspiel)
	{
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

	/**
	 * @return the platz
	 */
	public int getPlatz() {
		return PlatzShowdown;
	}

	/**
	 * @param platz the platz to set
	 */
	public void setPlatz(int platz) {
		this.PlatzShowdown = platz;
	}
	
	public void einzahlen(int betrag)
	{
		Log.d(getProfil().getName(),"gewinnt:" + String.valueOf(betrag));
		setChips(getChips()+betrag);
	}

	public void gameover() 
	{
		// TODO Auto-generated method stub
		
	}
}
