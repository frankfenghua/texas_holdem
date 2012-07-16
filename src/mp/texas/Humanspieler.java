/*
 * Klasse für menschliche Spieler, im wesentlichen wichtig für die Interaktivität
 * die Funktion setzen, fordert zur Eingabe heraus
 */
package mp.texas;

import android.util.Log;

public class Humanspieler extends Spieler 
{

	boolean call=false;
	
	public Humanspieler(Spieler selbst, int chips)
	{
		profil=selbst.getProfil();
		this.chips=chips;
	}
	
	
	public Humanspieler() 
	{
		
	}
	
	

	public int setzen(Pokerspiel pokerspiel)
	{
		Log.d("Human","setzen");
		App.setInteracted(false);
		if(App.call==true)
		{
			Log.d("Call","Call gesetzt");
			App.call=false;
			return App.pokerspiel.getEinsatz();
		}
		return App.setzwert;
		
	}

	@Override
	public void gameover() {
		Log.d("GameOver","Human Player");
		super.gameover();
	}


	
	
	

}
