/*
 * Die App schaut nach ob man selbst der aktive Spieler ist, wenn ja wird die Funktion spielablauf() aufgerufen
 */

package mp.texas;

import java.util.ArrayList;

import android.util.Log;

public class Pokerspiel 
{
	private String name;	//Name des Pokerspiels
	private int pot;		//Wieviel ist im Pot
	private int einsatz=0;  //nötiger Gesamteinsatz eines Spielers um in der Runde zu bleiben 
	private Spieler aktiverSpieler; 
	private Spieler smallBlindSpieler;
	private ArrayList<Spieler> alleSpieler = new ArrayList<Spieler>();
	private boolean singlePlayer;	//Ist dieses Spiel ein Offline oder Online-Spiel
	private int blindBetrag;		//Gibt den aktuellen BigBlind Betrag an
	private Gemeinschaftskarten gemeinschaftskarten; //Gemeinschaftskarten der Runde
	private int wettrunde;							//Aktuelle Bietrunde, Preflop, Flop, River...
	private Spieler lastRaise=null;
	private int Rundenzahler=0;
	
	//Variablen um zu initialisieren, wichtig für Vorschau offen Spiele
	private int ComputergegnerLevel = 0;
	private int startkapital;

	
	
	
	//Variablen für die Blind Erhöhungen
	private final int Runden=1; //??
	private final int Zeit=2; //??
	private int blindZeitRundenWert;
	private String blindModus;
	

	
	public Pokerspiel(){
		//leere Konstruktor zur Spieleršffnung
		//austeilen();
		Log.d("Leerer Pokerkonstruktor","aufgerufen");
	}
	
	//DER KONSTRUKTOR DER VON MICHAEL VERWENDET WIRD
	public Pokerspiel(boolean online, int anzahlmitspieler, int startkapital, String blindart, int Blindwert, int bigblind, int computerlevel)
	{
		if(online)
		{singlePlayer=false;}
		else{singlePlayer=true;}
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
		this.startkapital=Startkapital;
		this.setAlleSpieler(alleSpieler);
		this.blindModus=blindModus;
		this.blindBetrag=blindBetrag;
		this.blindZeitRundenWert=blindZeitRundenWert;
		
		for (Spieler n: alleSpieler)
		{
			n.setChips(Startkapital);
		}
		Log.d("4ter Pokerkonstruktor","aufgerufen");
	}
	
	
	
	
	public void austeilen()
	{
		//Spiel reset
		pot=0;
		einsatz=getBlindBetrag();
		setWettrunde(1);
		
		//Spieler reset
		for(Spieler n:getAlleSpieler())
		{
			n.setNochDrin(true);
			n.setChipsImPot(0);
		}
		
		Rundenzahler++;
		blindWeitergeben();
		
		//Karten verteilen
		Blatt blatt = new Blatt();
		blatt.blattMischen(blatt.getKarten());
		gemeinschaftskarten=blatt.gemeinschaftskartenGeben();
		
		for(Spieler n:getAlleSpieler())
		{
			n.setHand(blatt.handGeben());
		}
		
		blindsEinzahlen();
		update();
	}
	
	
	public void spielablauf() 
	{
		//nachsterSpieler();
	//Aktueller Spieler wird aufgerufen und setzt temp
		int spielerinderRunde=0;
		for(Spieler n:getAlleSpieler())
		{
			if(n.isNochDrin()==true)
			{
				spielerinderRunde++;
			}
		}
		Log.d("Spieler in der Runde",String.valueOf(spielerinderRunde));
		
		if(spielerinderRunde==1)	//NUR NOCH EIN SPIELER IN DER RUNDE
		{
			ausbezahlen();
		}
		
		else
		{
		
		if(aktiverSpieler.equals(lastRaise))
		{
			if(getWettrunde()==1)
			{Flop();}
			if(getWettrunde()==2)
			{TurnCard();}
			if(getWettrunde()==3)
			{RiverCard();}
			if(getWettrunde()==4)
			{ShowDown();}
		}
			
		else{
		int temp= aktiverSpieler.setzen(this);  //HIER WIRD DER SPIELER ZUM SETZEN AUFGERUFEN
		
		if(aktiverSpieler.getSidepot()==0)	//Er ist nicht ALL in
		{
			if(temp>=aktiverSpieler.getChips()) //ALL IN
			{ 	Log.d("Zug","ALL IN");
				aktiverSpieler.setChipsImPot(aktiverSpieler.getChipsImPot()+aktiverSpieler.getChips()); // Chips im Pot beim Spieler aktualisieren
				einzahlen(aktiverSpieler.getChips());	//Die restlichen Chips noch einzahlen
				aktiverSpieler.setSidepot(getPot());	//Den Sidepot füllen
				aktiverSpieler.setChips(0);				//Die Chips des Spielers auf 0 setzen
				aktiverSpieler.setZustand("ALL IN");
				if(temp>getEinsatz())
				{
					setEinsatz(temp);
				}
			}
			else
			{
				if(temp>=getEinsatz())
				//RAISE ODER CALL
				{
					Log.d("Zug","CALL/RAISE");
					einzahlen(temp);
					aktiverSpieler.setChips(aktiverSpieler.getChips()-temp);
					aktiverSpieler.setChipsImPot(aktiverSpieler.getChipsImPot()+temp);
					if(temp>getEinsatz())
					{
					einsatz=temp;
					setLastRaise(getAktiverSpieler());
					}
				}
		
		
				if(temp<getEinsatz())
				//Fold
				{
					Log.d("Zug","FOLD");
					aktiverSpieler.setChipsImPot(0);
					aktiverSpieler.setNochDrin(false);
					aktiverSpieler.setZustand("Fold");
				}
				
			}
			
		}
		else	//Er ist schon all in
		{
			
		}
		nachsterSpieler();
		update();
		}		//Ende der else für Beenden der Wettrunde
		}		//Ende der else für nur noch ein Spieler
		
		//HIER EINEN HANDLER DER DIE DRAW FUNKTION DER ACTIVITY AKTIVIERT
	}
	
	
	public void nachsterSpieler()
	{
		int temp= getAlleSpieler().indexOf(aktiverSpieler);
		aktiverSpieler=getAlleSpieler().get(verschieben(++temp));
		while(aktiverSpieler.isNochDrin()==false)
		{aktiverSpieler=getAlleSpieler().get(verschieben(++temp));}	
		
	}
	
	
	public void vorherigerSpieler()
	{
		int temp= getAlleSpieler().indexOf(smallBlindSpieler)+2;
		lastRaise=getAlleSpieler().get(verschieben(--temp));
		while(lastRaise.isNochDrin()==false)
		{lastRaise=getAlleSpieler().get(verschieben(--temp));}	
		
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////
	///////////////////////////BLINDS VERWALTEN/////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	public void blindsEinzahlen()
	{
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		pot+=smallBlindSpieler.zwangssetzen(blindBetrag/2);
		pot+=getAlleSpieler().get(verschieben(temp+1)).zwangssetzen(blindBetrag);
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
	 	
		setLastRaise(getAlleSpieler().get(verschieben(temp+2)));
	 	aktiverSpieler=getAlleSpieler().get(verschieben(temp+3));
	}
	
	
	
	public void einzahlen(int setzen) {
		pot+=setzen;
	}

	
	public void ausbezahlen()
	{
		
		Log.d("Ausbezahlen","läuft");
		austeilen();
		
	}


	///////////////////////////////////////////////////////////////////////////
	/////////////////////////Bietrunden////////////////////////////////////////
	
	private void Flop()
	{
		setWettrunde(2);
		Log.d("Wettrunde","Flop");
		vorherigerSpieler();
		Log.d("LastRaise",lastRaise.getProfil().getName());
		//Spieler in FirstPosition bestimmen
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		setAktiverSpieler(getAlleSpieler().get(verschieben(temp+2)));
		//Falls der schon raus ist den neuen
		while(getAktiverSpieler().isNochDrin()==false)
		{
			nachsterSpieler();
		}
		update();
		//Bietrunde nach den ersten 3 Karten
	}
	
	private void TurnCard()
	{
		Log.d("Wettrunde","TurnCard");
		setWettrunde(3);
		vorherigerSpieler();
		
		//Spieler in FirstPosition bestimmen
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		setAktiverSpieler(getAlleSpieler().get(verschieben(temp+2)));
		//Falls der schon raus ist den neuen
		while(getAktiverSpieler().isNochDrin()==false)
		{
			nachsterSpieler();
		}
		update();
		//Bietrunde nach der TurnCard
	}
	
	private void RiverCard()
	{
		Log.d("Wettrunde","RiverCard");
		setWettrunde(4);
		vorherigerSpieler();
		//Spieler in FirstPosition bestimmen
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		setAktiverSpieler(getAlleSpieler().get(verschieben(temp+2)));
		//Falls der schon raus ist den neuen
		while(getAktiverSpieler().isNochDrin()==false)
		{
			nachsterSpieler();
		}
		update();
		//Bietrunde nach der RiverCard
	}
	
	

	
	
	public ArrayList<ArrayList<Spieler>> ShowDown()
	{
		Log.d("SHOWDOWN","OH yeah");
		 // gibt die Siegerreihenfolge zurück
		ArrayList<Spieler> aktive=new ArrayList<Spieler>(); // wählt alle Spieler aus die noch im Spiel sind
		
		
		for(Spieler n:getAlleSpieler())
		{
			if(n.isNochDrin())
			{
				aktive.add(n);
			}
			
		}
		int[][] ergebnismatrix= new int[aktive.size()][6];
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
					int[] nurFarbe={0,0,0,0,0,0,0,0,0,0,0,0,0};
					for(Karte o:gemeinschaftskarten.getGemeinschaftskarten())
					{
						if(o.getFarbe()==m)
						{nurFarbe[o.getWert()-2]=1;}
					}
					if(n.getHand().getKarte1().getFarbe()==m)
					{
						nurFarbe[n.getHand().getKarte1().getWert()-2]=1;
					}
					
					if(n.getHand().getKarte2().getFarbe()==m)
					{
						nurFarbe[n.getHand().getKarte2().getWert()-2]=1;
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
			if(ergebnismatrix[z][0]==0)
			{
			//8 Vierling
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==4)
				{
					ergebnismatrix[z][0]=8;
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 1);
					ergebnismatrix[z][2]=temp[0];
				}
			}
			if(ergebnismatrix[z][0]==0)
			{
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
			
			if(ergebnismatrix[z][0]==0)
			{
			
			//6 Flush
			for(int m=0;m<4;m++)
			{
				if(Farbensammeln[m]==5)
				{
					int[] nurFarbe={0,0,0,0,0,0,0,0,0,0,0,0,0};
					for(Karte o:gemeinschaftskarten.getGemeinschaftskarten())
					{
						if(o.getFarbe()==m)
						{nurFarbe[o.getWert()-2]=1;}
					}
					if(n.getHand().getKarte1().getFarbe()==m)
					{
						nurFarbe[n.getHand().getKarte1().getWert()-2]=1;
					}
					
					if(n.getHand().getKarte2().getFarbe()==m)
					{
						nurFarbe[n.getHand().getKarte2().getWert()-2]=1;
					}
					
					int[] temp =bestCards(nurFarbe, 5);
					ergebnismatrix[z][0]=6;
					ergebnismatrix[z][1]=temp[0];
					ergebnismatrix[z][2]=temp[1];
					ergebnismatrix[z][3]=temp[2];
					ergebnismatrix[z][4]=temp[3];
					ergebnismatrix[z][5]=temp[4];
				}
			}
			if(ergebnismatrix[z][0]==0)
			{
			//5 Straight
			for(int m=12;m>=4;m--)
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
			
			if(ergebnismatrix[z][0]==0)
			{
			//4 Drilling
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==3)
				{
					ergebnismatrix[z][0]=4;
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 2);
					ergebnismatrix[z][2]=temp[0];
					ergebnismatrix[z][3]=temp[1];
				}
			}
			if(ergebnismatrix[z][0]==0)
			{
			//3 Zwei Paar
			int paaregefunden=0;
			int paarewert=0;
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==2)
				{ 
					paaregefunden++;
					if(paarewert==0)
					{
						paarewert=m;
					}
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
				
			if(ergebnismatrix[z][0]==0)
			{
			//2 Ein Paar
			for(int m=12;m>=0;m--)
			{
				if(Wertesammeln[m]==2)
				{
					ergebnismatrix[z][0]=2; //Für EinPaar
					ergebnismatrix[z][1]=m;
					Wertesammeln[m]=0;
					int[] temp =bestCards(Wertesammeln, 3);
					ergebnismatrix[z][2]=temp[0];
					ergebnismatrix[z][3]=temp[1];
					ergebnismatrix[z][4]=temp[2];
				}
				
			}
			if(ergebnismatrix[z][0]==0)
			{
			//1 High Card
			int[] temp =bestCards(Wertesammeln, 5);
			ergebnismatrix[z][0]=1;
			ergebnismatrix[z][1]=temp[0];
			ergebnismatrix[z][2]=temp[1];
			ergebnismatrix[z][3]=temp[2];
			ergebnismatrix[z][4]=temp[3];
			ergebnismatrix[z][5]=temp[4];
			}}}}}}}}	//Die einzelnen verpassten Gewinnchancen wieder zu machen
			Log.d(n.getProfil().getName(),String.valueOf(n.getHand().getKarte1().getWert())+" "+String.valueOf(n.getHand().getKarte1().getFarbe())+" "+String.valueOf(n.getHand().getKarte2().getWert())+" "+String.valueOf(n.getHand().getKarte2().getFarbe()));
			Log.d(n.getProfil().getName(),String.valueOf(ergebnismatrix[z][0])+" "+String.valueOf(ergebnismatrix[z][1])+" "+String.valueOf(ergebnismatrix[z][2])+" "+String.valueOf(ergebnismatrix[z][3])+" "+String.valueOf(ergebnismatrix[z][4])+" "+String.valueOf(ergebnismatrix[z][5]));

			
		}//ENDE SPIELER AUSWERTUNG
		setWettrunde(0);
		//austeilen();
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
	
	public int[] bestCards(int[] werte,int anzahl)
	{
		int[] ausgabe=new int[anzahl];
		int schongefunden=0;
		for(int o=12;((anzahl>schongefunden)&&(o>=0));o--)
		{
			while(werte[o]>0)
			{
				if(schongefunden<anzahl)
				{
				ausgabe[schongefunden]=o;
				schongefunden++;
				}
				werte[o]--;
			}
				
		}
				
		return ausgabe;
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
	
	
	//ENDE DER SHOW DOWN AUSWERTUNG

	
	
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

	//Hilfsfunktion um im Array zu bleiben
	public int verschieben(int zahl)
	{while((zahl<0)||(zahl>=getAlleSpieler().size()))
		{
		if(zahl<0){zahl+=getAlleSpieler().size();}
		if(zahl>=getAlleSpieler().size()){zahl-=getAlleSpieler().size();}
		}
	return zahl;
	}
	
	
	public void update()
	{
		;
		//Kurzgeschlossen für Singleplayer
		if(singlePlayer==true)
		{
			receive();
		}
	}
	
	public void receive()
	{
		if(singlePlayer==true)
		{spielablauf();}
	}
	
	public Gemeinschaftskarten getGemeinschaftskarten() {
		return gemeinschaftskarten;
	}

	public void setGemeinschaftskarten(Gemeinschaftskarten gemeinschaftskarten) {
		this.gemeinschaftskarten = gemeinschaftskarten;
	}



	public boolean isSinglePlayer() {
		return singlePlayer;
	}

	public void setSinglePlayer(boolean singlePlayer) {
		this.singlePlayer = singlePlayer;
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
	


	public int getComputergegnerLevel() {
		return ComputergegnerLevel;
	}

	public void setComputergegnerLevel(int computergegnerLevel) {
		ComputergegnerLevel = computergegnerLevel;
	}

	public Spieler getAktiverSpieler() {
		return aktiverSpieler;
	}

	public void setAktiverSpieler(Spieler aktiverSpieler) {
		this.aktiverSpieler = aktiverSpieler;
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

	public int getStartkapital() {
		return startkapital;
	}

	public void setStartkapital(int startkapital) {
		this.startkapital = startkapital;
	}

	
	public int getWettrunde() {
		return wettrunde;
	}

	public void setWettrunde(int wettrunde) {
		this.wettrunde = wettrunde;
	}

	/**
	 * @return the lastRaise
	 */
	public Spieler getLastRaise() {
		return lastRaise;
	}

	/**
	 * @param lastRaise the lastRaise to set
	 */
	public void setLastRaise(Spieler lastRaise) {
		this.lastRaise = lastRaise;
	}


}
