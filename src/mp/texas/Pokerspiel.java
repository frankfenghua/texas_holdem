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
			for(int i=0;i<1000;i++)
			{
				int rand1=(int)(Math.random()*liste.size());
				int rand2=(int)(Math.random()*liste.size());
				Spieler temp=liste.get(rand1);
				liste.remove(rand1);
				liste.add(rand2, temp);
			}
		return liste;
	}
	
	
	public ArrayList<Spieler> bewerten()
	{
		ArrayList<Spieler> aktive=new ArrayList<Spieler>();
		for(Spieler n:alleSpieler)
		{
			if(n.isNochDrin())
			{
				aktive.add(n);
			}
			
		}
		
		ArrayList<Spieler> sieger=new ArrayList<Spieler>();
	
		for(Spieler n:aktive)
		{
			if(RoyalFlush(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(StraightFlush(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
			
		for(Spieler n:aktive)
		{
			if(Vierling(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(FullHouse(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Flush(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Straight(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Drilling(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(ZweiPaar(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		
		for(Spieler n:aktive)
		{
			if(EinPaar(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(HighCard(n.getHand()));
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		return sieger;
	}
	
	private int RoyalFlush(Hand hand)
	{
		
		return false;
	}
	
	private int StraightFlush(Hand hand)
	{
		return false;
	}
	
	private int Vierling(Hand hand)
	{
		return false;
	}
	
	private int FullHouse(Hand hand)
	{
		return false;
	}
	
	private int Flush(Hand hand)
	{
		return false;
	}
	
	private int Straight(Hand hand)
	{
		return false;
	}
	
	private int Drilling(Hand hand)
	{
		return false;
	}
	
	private int ZweiPaar(Hand hand)
	{
		return false;
	}
	
	private int EinPaar(Hand hand)
	{
		return false;
	}
	
	private int HighCard(Hand hand)
	{
		return false;
	}
	
}
