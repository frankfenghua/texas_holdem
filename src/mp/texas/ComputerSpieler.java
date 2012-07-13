package mp.texas;

import java.security.PublicKey;

import android.util.Log;

public class ComputerSpieler extends Spieler {

	public ComputerSpieler(Profil profilarg, int chipsarg, int intelligenz) 
	{
		super(profilarg, chipsarg);
		// TODO Auto-generated constructor stub
	}

	public ComputerSpieler(int computerlevel, int chipsarg, int id) {
		// TODO Auto-generated constructor stub
		super(new Profil(id), chipsarg);
		
	}

	@Override
	public void call(int need) {
		// TODO Auto-generated method stub
		
		super.call(need);
	}

	@Override
	public void auffordern(int need) {
		// TODO Auto-generated method stub
		call(need);
	}
	

	
	
	
}
