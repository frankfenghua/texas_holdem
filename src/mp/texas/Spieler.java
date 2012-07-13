package mp.texas;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;


public class Spieler 
{
	protected Profil profil = new Profil(1);
	private Hand hand;
	//ANZEIGEPARAMETER
	public int numberondevice;
	public LinearLayout layoutondevice;
	public boolean mainspieler;
	//IST DER SPIELER AUF DEM GERÄT;
	protected int chips;
	private boolean nochDrin=true;
	private String zustand=" ";
	private double anzahlZigaretten=19;
	protected int chipsImPot=0;
	public int[] ergebnis={0,0,0,0,0,0};
	
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
	
	public Spieler() {
		// TODO Auto-generated constructor stub
	}

	public int setzen(int chipsarg)
	{
		if(chips>chipsarg)
		{
			chips-=chipsarg;
			chipsImPot+=chipsarg;
			Log.d(profil.getName(),String.valueOf(chipsarg));
			return chipsarg;
		}
		
		else
		{
			return -1;
		}
			
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
}
