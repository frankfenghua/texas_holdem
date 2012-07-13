package mp.texas;

import java.util.ArrayList;

import android.util.Log;

public class Pokerspiel 
{
	private String name;
	private int startkapital;  // wird vorlŠufig zur Ÿbergabe aus SPielEInstellungenAct. gebraucht
	private int pot;
	private final int Runden=1; //??
	private final int Zeit=2; //??
	private int blindZeitRundenWert;
	private String blindModus;
	private int blindBetrag;
	private Spieler aktiverSpieler;
	private ArrayList<Spieler> alleSpieler = new ArrayList<Spieler>();
	private Spieler lastRaise;
	private int einsatz=0;
	private Spieler smallBlindSpieler;
	private Gemeinschaftskarten gemeinschaftskarten;
	private int wettrunde;
	
	public Gemeinschaftskarten getGemeinschaftskarten() {
		return gemeinschaftskarten;
	}

	public void setGemeinschaftskarten(Gemeinschaftskarten gemeinschaftskarten) {
		this.gemeinschaftskarten = gemeinschaftskarten;
	}

	private int ComputergegnerLevel;

	
	public Pokerspiel(){
		//leere Konstruktor zur Spieleršffnung
		//austeilen();
		Log.d("Leerer Pokerkonstruktor","aufgerufen");
	}
	
	public Pokerspiel(boolean online, int anzahlmitspieler, int startkapital, String blindart, int Blindwert, int bigblind, int computerlevel)
	{
		blindBetrag=bigblind;
		if(online==false)
		{Log.d("hier","hier");
			alleSpieler=new ArrayList<Spieler>();
			alleSpieler.add(new Humanspieler(startkapital));
			for(int i=1; i<anzahlmitspieler;i++)
			{
				alleSpieler.add(new ComputerSpieler(computerlevel, startkapital,i));
			}
			Log.d("Spieler im Konstruktor",String.valueOf(alleSpieler.size()));
		}
		setEinsatz(0);
		alleSpieler=spielerMischen(getAlleSpieler());
		smallBlindSpieler=getAlleSpieler().get(0);
		austeilen();
	}
	
	public Pokerspiel(int startkapitalarg, int blindZeitRundenWertarg, String blindModusarg, int blindBetragarg){
		this.startkapital=startkapitalarg;
		this.blindBetrag=blindBetragarg;
		this.blindZeitRundenWert=blindZeitRundenWertarg;
		this.blindModus=blindModusarg;
		Log.d("3 Konstruktor","aufgerufen");
	}

	
	public Pokerspiel(ArrayList<Spieler> alleSpieler, int Startkapital, String blindModus, int blindZeitRundenWert, int blindBetrag)
	{
		this.setAlleSpieler(alleSpieler);
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
		Log.d("4ter Pokerkonstruktor","aufgerufen");
	}
	
	public void austeilen()
	{
		pot=0;
		setWettrunde(1);
		blindWeitergeben();
		Blatt blatt = new Blatt();
		blatt.blattMischen(blatt.getKarten());
		gemeinschaftskarten=blatt.gemeinschaftskartenGeben();
		
		for(Spieler n:getAlleSpieler())
		{
			n.setHand(blatt.handGeben());
		}
		Log.d("Austeilen SPieleranzahl", String.valueOf(getAlleSpieler().size()));
		blindsEinzahlen();
		aktiverSpieler.auffordern(getEinsatz());
	}
	
	
	////////////////////////////////////////////////////////////////////////////
	///////////////////////////BLINDS VERWALTEN/////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	public void blindsEinzahlen()
	{
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		pot+=smallBlindSpieler.setzen(blindBetrag/2);
		pot+=getAlleSpieler().get(verschieben(temp+1)).setzen(blindBetrag);
		setEinsatz(blindBetrag);
	}
	
	public void blindWeitergeben()
	{
	 	int temp=getAlleSpieler().indexOf(smallBlindSpieler);
	 	getAlleSpieler().get(verschieben(temp-1)).setZustand(" ");

	 	if(temp==0){
	 		getAlleSpieler().get(getAlleSpieler().size()-1).setZustand(" ");
	 	}
	 	else
	 	{getAlleSpieler().get(temp-1).setZustand(" ");}
		getAlleSpieler().get(temp).setZustand("Dealer");
		if(temp+1==getAlleSpieler().size())
		{getAlleSpieler().get(0).setZustand("Small Blind");
		smallBlindSpieler=getAlleSpieler().get(0);}
		else{getAlleSpieler().get(temp+1).setZustand("Small Blind");
		smallBlindSpieler=getAlleSpieler().get(temp+1);}
		
		if(temp+2==getAlleSpieler().size())
		{getAlleSpieler().get(0).setZustand("Big Blind");}	
		if(temp+2==getAlleSpieler().size()+1)
		{getAlleSpieler().get(1).setZustand("Big Blind");}
		if((temp+2!=getAlleSpieler().size())&&(temp+2!=getAlleSpieler().size()+1))
		{getAlleSpieler().get(temp+2).setZustand("Big Blind");}
	 	
	 	aktiverSpieler=getAlleSpieler().get(verschieben(temp+3));
	}
	
	///////////////////////////////////////////////////////////////////////////
	/////////////////////////Bietrunden////////////////////////////////////////
	private void Preflop()
	{	setWettrunde(1);
		
		//Bietrunde vor den ersten Gemeinschaftskarten
	}
	
	private void Flop()
	{
		setWettrunde(2);
		//Bietrunde nach den ersten 3 Karten
	}
	
	private void TurnCard()
	{
		setWettrunde(3);
		//Bietrunde nach der TurnCard
	}
	
	private void RiverCard()
	{
		setWettrunde(4);
		//Bietrunde nach der RiverCard
	}
	
	

	
	
	public ArrayList<ArrayList<Spieler>> ShowDown()
	{
		 // gibt die Siegerreihenfolge zurück
		ArrayList<Spieler> aktive=new ArrayList<Spieler>(); // wählt alle Spieler aus die noch im Spiel sind
		int[][] ergebnismatrix= new int[aktive.size()][6];
		
		for(Spieler n:getAlleSpieler())
		{
			if(n.isNochDrin())
			{
				aktive.add(n);
			}
			
		}
		
		ArrayList<ArrayList<Spieler>> sieger=new ArrayList<ArrayList<Spieler>>();
		
		for(int z=0; z<aktive.size();z++)
		{	Spieler n=aktive.get(z);
			ergebnismatrix[z][0]=0;
			ergebnismatrix[z][1]=0;
			ergebnismatrix[z][2]=0;
			ergebnismatrix[z][3]=0;
			ergebnismatrix[z][4]=0;
			ergebnismatrix[z][5]=0;
			int[] Wertesammeln={0,0,0,0,0,0,0,0,0,0,0,0,0};
			int[] Farbensammeln={0,0,0,0};
			for(Karte m:gemeinschaftskarten.getGemeinschaftskarten())
			{
				Farbensammeln[m.getFarbe()]++;
				Wertesammeln[m.getWert()-2]++;
			}
			Farbensammeln[n.getHand().getKarte1().getFarbe()]++;
			Farbensammeln[n.getHand().getKarte2().getFarbe()]++;
			Wertesammeln[n.getHand().getKarte1().getWert()-2]++;
			Wertesammeln[n.getHand().getKarte2().getWert()-2]++;
			
			//9 StraightFlush
			for(int m=0;m<4;m++)
			{
				if(Farbensammeln[m]==5)
				{
					int[] nurFarbe={0,0,0,0,0,0,0,0,0,0,0,0};
					for(Karte o:gemeinschaftskarten.getGemeinschaftskarten())
					{
						if(o.getFarbe()==m)
						{nurFarbe[o.getWert()]=1;}
					}
					int[] temp =bestCards(nurFarbe, 5);
	
					if(
						(temp[0]-1==temp[1])&&
						(temp[1]-1==temp[2])&&
						(temp[2]-1==temp[3])&&
						(temp[3]-1==temp[4])
					  )
					{
						ergebnismatrix[z][0]=9;
						ergebnismatrix[z][1]=temp[0];
					}
				}
			}
			//8 Vierling
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==4)
				{
					ergebnismatrix[z][0]=8;
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 1);
					ergebnismatrix[z][3]=temp[0];
				}
			}
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//7 FullHouse
			int drilling=0;
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==3)
				{ 
					drilling=m;
					for(int o=12;o>=0;o--)
					{
						if(Wertesammeln[o]==2)
						{ 
							ergebnismatrix[z][0]=7;
							ergebnismatrix[z][1]=drilling;
							ergebnismatrix[z][2]=o;
						}
						
					}
				}
				
			}
			
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			
			//6 Flush
			for(int m=0;m<4;m++)
			{
				if(Farbensammeln[m]==5)
				{
					ergebnismatrix[z][0]=6;
					int[] temp =bestCards(Wertesammeln, 5);
					ergebnismatrix[z][1]=temp[0];
					ergebnismatrix[z][2]=temp[1];
					ergebnismatrix[z][3]=temp[2];
					ergebnismatrix[z][4]=temp[3];
					ergebnismatrix[z][5]=temp[4];
				}
			}
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//5 Straight
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]>=1)
				{
					if(Wertesammeln[m-1]>=1)
					{
						if(Wertesammeln[m-2]>=1)
						{
							if(Wertesammeln[m-3]>=1)
							{
								if(Wertesammeln[m-4]>=1)
								{
									ergebnismatrix[z][0]=5;
									ergebnismatrix[z][1]=m;
								}
								else{m=m-5;}
							}
							else{m=m-4;}
						}
						else{m=m-3;}
					}
					else{m=m-2;}
				}
				else{m=m-1;}
				
			}
			
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//4 Drilling
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==3)
				{
					ergebnismatrix[z][0]=4;
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 2);
					ergebnismatrix[z][3]=temp[0];
					ergebnismatrix[z][4]=temp[1];
				}
			}
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//3 Zwei Paar
			int paaregefunden=0;
			int paarewert=0;
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==2)
				{ 
					paaregefunden++;
					paarewert=m;
					if(paaregefunden==2)
					{
						ergebnismatrix[z][0]=3;
						ergebnismatrix[z][1]=paarewert;	//HöheresPaar
						ergebnismatrix[z][2]=m;			//Niedrigeres Paar
						Wertesammeln[paarewert]=0;	//Entfernen aus den Wertsammlungen
						Wertesammeln[m]=0;
						int[] temp =bestCards(Wertesammeln, 1);
						ergebnismatrix[z][3]=temp[0];
						break;
					}
				}

			}
				
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//2 Ein Paar
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==2)
				{
					ergebnismatrix[z][0]=2; //Für EinPaar
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 3);
					ergebnismatrix[z][3]=temp[0];
					ergebnismatrix[z][4]=temp[1];
					ergebnismatrix[z][5]=temp[2];
				}
				
			}
			if(ergebnismatrix[z][0]!=0)
			{
				break;
			}
			//1 High Card
			int[] temp =bestCards(Wertesammeln, 5);
			ergebnismatrix[z][0]=1;
			ergebnismatrix[z][1]=temp[0];
			ergebnismatrix[z][2]=temp[1];
			ergebnismatrix[z][3]=temp[2];
			ergebnismatrix[z][4]=temp[3];
			ergebnismatrix[z][5]=temp[4];
			
		}//ENDE SPIELER AUSWERTUNG
		
		int[] permutation=new int[aktive.size()];
		for(int n=0;n<aktive.size();n++)
			{permutation[n]=n;}
		for(int n=0; n<aktive.size();n++)
		{
			for(int m=n+1; m<aktive.size();m++)
				{
				if(compare(n,m,0,ergebnismatrix)==1);
					{
						int temp=permutation[m];
						permutation[m]=permutation[n];
						permutation[n]=temp;
					}
				}
		}
		// hier kommt eine "sortierte" permutation heraus
		/******
		int[] ergebnis=new int[aktive.size()];
		for(int n=0;n<aktive.size();n++)
		{
			for(int z=0;z<6;z++)
			{
				ergebnis[n]+=Math.pow(2,5-z)*ergebnismatrix[n][z];
			}
		}
		
		sieger.add(new ArrayList<Spieler>());
		sieger.get(0).set(0, aktive.get(1));
	for(int n=0;n<aktive.size();n++)
	{compare(aktive,n+1,0,ergebnismatrix);}
		*******/////
		
		
		return sieger;
	}
	
	
	public int compare(int erster, int zweiter,int koeffizient, int[][] matrix)
	{
		if(koeffizient>=6)		//wenn beide komplett gleich
		{return 0;}	
		if(matrix[erster][koeffizient]<matrix[zweiter][koeffizient])
		{return 1;}		// wenn zweiter größer
		if(matrix[erster][koeffizient]>matrix[zweiter][koeffizient])
		{return -1;}	// wenn erster größer
		if(matrix[erster][koeffizient]==matrix[zweiter][koeffizient])
		{return compare(erster,zweiter,koeffizient+1,matrix);}		//wenn bis daher gleich
		return -5; //sollte nicht eintreten können
	}
	
	/*
	void sort(int a[][], int lo0, int hi0) throws Exception {
	    int lo = lo0;
	    int hi = hi0;
	    if (lo >= hi) {
	      return;
	    }
	    int mid = a[(lo + hi) / 2][];
	    while (lo < hi) 
	    {
	      while (lo<hi && a[lo][] < mid) 
	      {
	    	  lo++;
	      }
	      while (lo<hi && a[hi][] > mid) 
	      {
	    	  hi--;
	      }
	      if (lo < hi) {
		int temp = a[lo][];
		a[lo][] = a[hi][];
		a[hi][] = temp;
	      }
	      //compex(lo, hi);
	    }
	    if (hi < lo) {
	      int T = hi;
	      hi = lo;
	      lo = T;
	    }
	    sort(a, lo0, lo);
	    sort(a, lo == lo0 ? lo+1 : lo, hi0);
	  }

	  /**
	   * Sorts the given array using the quicksort algorithm.
	   
	  void sort(int a[][]) throws Exception {
	    sort(a, 0, a.length-1);
	  }
	}
*/
	
	
	public ArrayList<Spieler> spielerMischen(ArrayList<Spieler> liste)
	{
		if(liste.size() == 0)
			return liste;
		else
		{
			for(int i=0;i<1000;i++)
			{
				int rand1=(int)(Math.random()*liste.size());
				int rand2=(int)(Math.random()*liste.size());
				Spieler temp=liste.get(rand1);
				liste.remove(rand1);
				liste.add(rand2, temp);
			}
		}
		return liste;
	}


	public int getStartkapital() {
		return startkapital;
	}

	public void setStartkapital(int startkapital) {
		this.startkapital = startkapital;
	}

	public int getBlindZeitRundenWert() {
		return blindZeitRundenWert;
	}

	public void setBlindZeitRundenWert(int blindZeitRundenWert) {
		this.blindZeitRundenWert = blindZeitRundenWert;
	}

	public String getBlindModus() {
		return blindModus;
	}

	public void setBlindModus(String blindModus) {
		this.blindModus = blindModus;
	}

	public int getBlindBetrag() {
		return blindBetrag;
	}

	public void setBlindBetrag(int blindBetrag) {
		this.blindBetrag = blindBetrag;
	}

	public ArrayList<Spieler> getAlleSpieler() {
		return alleSpieler;
	}

	public void setAlleSpieler(ArrayList<Spieler> alleSpieler) {
		this.alleSpieler = alleSpieler;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}	
	
	public int[] bestCards(int[] werte,int anzahl)
	{
		int[] ausgabe=new int[anzahl];
		int schongefunden=0;
		for(int o=12;((anzahl>schongefunden)&&(o>=0));o--)
		{
			while(werte[o]>0)
			{
				ausgabe[schongefunden]=o;
				schongefunden++;
				werte[o]--;
			}
				
		}
				
		return ausgabe;
	}

	public int getComputergegnerLevel() {
		return ComputergegnerLevel;
	}

	public void setComputergegnerLevel(int computergegnerLevel) {
		ComputergegnerLevel = computergegnerLevel;
	}

	public int getWettrunde() {
		return wettrunde;
	}

	public void setWettrunde(int wettrunde) {
		this.wettrunde = wettrunde;
	}

	public Spieler getAktiverSpieler() {
		return aktiverSpieler;
	}

	public void setAktiverSpieler(Spieler aktiverSpieler) {
		this.aktiverSpieler = aktiverSpieler;
	}
	
	public int verschieben(int zahl)
	{while((zahl<0)||(zahl>=getAlleSpieler().size()))
		{
		if(zahl<0){zahl+=getAlleSpieler().size();}
		if(zahl>=getAlleSpieler().size()){zahl-=getAlleSpieler().size();}
		}
	return zahl;
	}

	public int getPot() {
		return pot;
	}

	public void setPot(int pot) {
		this.pot = pot;
	}

	public int getEinsatz() {
		return einsatz;
	}

	public void setEinsatz(int einsatz) {
		this.einsatz = einsatz;
	}

	public void einzahlen(int setzen) {
		
		pot+=setzen;

	}
	
	public void nachsterSpieler()
	{
		int temp= getAlleSpieler().indexOf(aktiverSpieler);
		aktiverSpieler=getAlleSpieler().get(verschieben(temp++));
		while(aktiverSpieler.isNochDrin()==false)
		{aktiverSpieler=getAlleSpieler().get(verschieben(temp++));}	
		
	}

	public void weiter() {
		nachsterSpieler();
		aktiverSpieler.auffordern(getEinsatz());
		//HIER EINEN HANDLER DER DIE DRAW FUNKTION DER ACTIVITY AKTIVIERT
		
	}
}
