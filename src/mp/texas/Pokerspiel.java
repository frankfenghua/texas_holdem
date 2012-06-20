package mp.texas;

import java.util.ArrayList;

public class Pokerspiel 
{
	private int pot;
	private Blatt blatt;
	private final int Runden=1;
	private final int Zeit=2;
	private int blindZeitRundenWert;
	private String blindModus;
	private int blindBetrag;
	private Spieler aktiverSpieler;
	private ArrayList<Spieler> alleSpieler;
	private Spieler lastRaise;
	private int einsatz;
	private Spieler smallBlindSpieler;
	
	
	public Pokerspiel(ArrayList<Spieler> alleSpieler, int Startkapital, String blindModus, int blindZeitRundenWert, int blindBetrag)
	{
		this.alleSpieler=alleSpieler;
		this.blindModus=blindModus;
		this.blindBetrag=blindBetrag;
		this.blindZeitRundenWert=blindZeitRundenWert;
		
		for (Spieler n: alleSpieler)
		{
			n.setChips(Startkapital);
		}
		//alleSpieler=(ArrayList<Spieler>) spielerMischen(alleSpieler);
		//smallBlindSpieler=alleSpieler.get(0);
		//aktiverSpieler=alleSpieler.get(0);
	}
	
	public void austeilen()
	{
		blindWeitergeben();
		blatt.blattMischen(blatt.getKarten());
		for(Spieler n:alleSpieler)
		{
			n.setHand(blatt.handGeben());
		}
		blindsEinzahlen();
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	///////////////////////////BLINDS VERWALTEN/////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	public void blindsEinzahlen()
	{
		
	}
	
	public void blindWeitergeben()
	{
		
	}
	
	///////////////////////////////////////////////////////////////////////////
	/////////////////////////Bietrunden////////////////////////////////////////
	private void Preflop()
	{
		//Bietrunde vor den ersten Gemeinschaftskarten
	}
	
	private void Flop()
	{
		//Bietrunde nach den ersten 3 Karten
	}
	
	private void TurnCard()
	{
		//Bietrunde nach der TurnCard
	}
	
	private void RiverCard()
	{
		//Bietrunde nach der RiverCard
	}
	
	

	
	
	public ArrayList<Spieler> ShowDown()
	{
		
		int[] best={0,0};
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
			int[] temp=RoyalFlush(n.getHand());
			if(temp[0]!=0)
			{
				int besser = intHigher(best, temp);
				if(besser==2)
				{
					sieger.clear();
					sieger.add(n);
				}
				if(besser==3)
				{
					sieger.add(n);
				}
				
			}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(StraightFlush(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
			
		for(Spieler n:aktive)
		{
			if(Vierling(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(FullHouse(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Flush(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Straight(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(Drilling(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(ZweiPaar(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		
		for(Spieler n:aktive)
		{
			if(EinPaar(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		
		for(Spieler n:aktive)
		{
			if(HighCard(n.getHand())!=null);
			{sieger.add(n);}
		}
		if(sieger.size()>0)
		{return sieger;}
		return sieger;
	}
	
	
	//Bewertung der Hand
	
	//1
	private int[] RoyalFlush(Hand hand)
	{
		
		return null; //0 heißt kein RoyalFlush
					//1 heißt RoyalFlush
	}
	
	//2
	private int[] StraightFlush(Hand hand)
	{
		return null;	//0 heißt kein Straight Flush
					//ansonsten gibt die Zahl den Wert der höchsten Karte an
	}
	
	//3
	private int[] Vierling(Hand hand)
	{
		
		return null;	//0 heißt kein Vierling
					//ansonsten gibt die Zahl den Wert des Vierlings an
	}
	
	//4
	private int[] FullHouse(Hand hand)
	{
		return null;	//0 heißt kein Full House
					//ansonsten gibt die Zahl zuerst den Drilling und danach die des Paares
	}
	
	//5
	private int[] Flush(Hand hand)
	{
		return null; //0 ansonsten die Werte absteigend der Karten des Flush
	}
	
	//6
	private int[] Straight(Hand hand)
	{
		return null;	//0 fall nicht, ansonsten wird der Wert der höchsten Karte zurückgegeben
	}
	
	//7
	private int[] Drilling(Hand hand)
	{
		return null;	//0 falls nicht, ansonsten wird der Wert des Drilling zurückgegeben
					//sowie der Wert der 4te und 5ten Karte 
	}
	
	//8
	private int[] ZweiPaar(Hand hand)
	{
		return null; //0 falls nicht, ansonsten wird zuerst der Wert des höchsten Paares und dann der des zweiten Paares zurückgegeben
					//sowie die fünft höchste Karte
	}
	
	//9
	private int[] EinPaar(Hand hand)
	{
		return null; //0 falls nicht, ansonten der Kartenwert des Paares
					// dritte vierte und fünfte Karte
	}
	
	//10
	private int[] HighCard(Hand hand)
	{
		return null; //Wert der höchsten Karten
	}
	
	private int intHigher(int[] int1, int[] int2)
	{
		for(int i=0;i<int2.length;i++)
		{
			if(int1[i]>int2[i])
			{
				return 1;	//int1 ist größer
			}
			
			if(int1[i]<int2[i])
			{
				return 2;	//int2 ist größer
			}
		}
		return 3;			//beide gleich
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
	
}
