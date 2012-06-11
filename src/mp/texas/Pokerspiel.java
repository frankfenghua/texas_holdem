package mp.texas;

import java.util.ArrayList;

public class Pokerspiel 
{
	private int pot;
	private Blatt blatt;
	private final int Runden=1;
	private final int Zeit=2;
	private int blindZeitRundenWert;
	private int blindModus;
	private int blindBetrag;
	private Spieler aktiverSpieler;
	private ArrayList<Spieler> alleSpieler;
	private Spieler lastRaise;
	private int einsatz;
	private Spieler bigBlindSpieler;
	
	
	public Pokerspiel(ArrayList<Spieler> alleSpieler, int Startkapital, int blindModus, int blindZeitRundenWert, int blindBetrag)
	{
		this.alleSpieler=alleSpieler;
		this.blindModus=blindModus;
		this.blindBetrag=blindBetrag;
		this.blindZeitRundenWert=blindZeitRundenWert;
		
		for (Spieler n: alleSpieler)
		{
			n.setChips(Startkapital);
		}
		alleSpieler=(ArrayList<Spieler>) spielerMischen(alleSpieler);
		bigBlindSpieler=alleSpieler.get(0);
		aktiverSpieler=alleSpieler.get(0);
	}
	
	public void austeilen()
	{
		blatt.blattMischen(blatt.getKarten());
		for(Spieler n:alleSpieler)
		{
			n.setHand(blatt.handGeben());
		}
	}
	
	public ArrayList<Spieler> spielerMischen(ArrayList<Spieler> liste)
	{
		return liste;
	}
	
}
