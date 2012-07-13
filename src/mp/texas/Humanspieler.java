package mp.texas;

public class Humanspieler extends Spieler {
	public Humanspieler(int startkapital)
	{
		profil=new Profil(App.selbst.getProfil().getName(),App.selbst.getProfil().getAvatar());
		chips=startkapital;
	}

}
