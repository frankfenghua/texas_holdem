/*
 * Enthält alle wichtigen Funktionen für COMPUTERGEGNER, insbesondere
 * die künstliche Intelligenz zum setzen
 */
package mp.texas;

import java.security.PublicKey;

import android.os.Message;
import android.util.Log;




public class ComputerSpieler extends Spieler 
{
	private int intelligenz;

	
	//Konstruktor wenn Profil des Gegners bekannt ist
	public ComputerSpieler(Profil profilarg, int chipsarg, int intelligenz) 
	{
		super(profilarg, chipsarg);
		this.intelligenz=intelligenz;
	}

	//Konstruktor der das Profil noch erstellt
	public ComputerSpieler(int computerlevel, int chipsarg, int id) {
		// TODO Auto-generated constructor stub
		super(new Profil(id), chipsarg);
		intelligenz=computerlevel;
		getProfil().setId("COMPUTERGEGNER");
	}

	
	
	public int setzen(Pokerspiel pokerspiel)
	{
<<<<<<< HEAD
		int rand=(int) (Math.random()*300);

		schongesetzt=true;
		
	
		return 100;

=======
		int rand=(int) (Math.random()*4);
		if(rand==0)
		{
			return App.pokerspiel.getEinsatz();
		}
		
		if(rand==1)
		{
			return App.pokerspiel.getEinsatz();
		}
		if(rand==2)
		{
			return App.pokerspiel.getEinsatz()+App.pokerspiel.blindBestimmer();
		}
	
		return 0;
>>>>>>> origin/michael5
	}

	
	
	
}
