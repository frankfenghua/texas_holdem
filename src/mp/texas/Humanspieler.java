/*
 * Klasse für menschliche Spieler, im wesentlichen wichtig für die Interaktivität
 * die Funktion setzen, fordert zur Eingabe heraus
 */
package mp.texas;

import android.util.Log;

public class Humanspieler extends Spieler {
	public Humanspieler(int startkapital)
	{
		profil=new Profil(App.selbst.getProfil().getName(),App.selbst.getProfil().getAvatar());
		chips=startkapital;
	}
	
	public int setzen(Pokerspiel pokerspiel)
	{
		Log.d(profil.getName(),"dran");
		return 150;
	}

}
