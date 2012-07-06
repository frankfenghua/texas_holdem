package mp.texas;
import android.widget.Toast;


public class Spieler 
{
	private Profil profil;
	private Hand hand;
	private int chips;
	private boolean nochDrin=true;
	private double anzahlZigaretten=19;
	private int chipsImPot=0;
	
	public Spieler(Profil profilarg, int chipsarg)
	{
		profil=profilarg;
		chips=chipsarg;
		nochDrin=true;
	}
	
	public Spieler(String namearg, int chipsarg)
	{
		profil.setName(namearg);
		this.setChips(chipsarg);
		nochDrin=true;
	}
	
	public Spieler() {
		// TODO Auto-generated constructor stub
	}

	public int setzen(int chipsarg)
	{
		if(chips>chipsarg)
		{
			chips-=chipsarg;
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
}
