/*
 * Die App schaut nach ob man selbst der aktive Spieler ist, wenn ja wird die Funktion spielablauf() aufgerufen
 */

package mp.texas;

import java.util.ArrayList;
import java.util.Collections;

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
	private long startzeit;
	
	//Variablen um zu initialisieren, wichtig für Vorschau offen Spiele
	private int ComputergegnerLevel = 0;
	private int startkapital;

	
	
	
	//Variablen für die Blind Erhöhungen
	private int blindZeitRundenWert;
	private int blindModus;
	

	
	public Pokerspiel(){
		blindModus=1;
		//leere Konstruktor zur Spieleršffnung
		//austeilen();
		Log.d("Leerer Pokerkonstruktor","aufgerufen");
	}
	
	//DER KONSTRUKTOR DER VON MICHAEL VERWENDET WIRD
	public Pokerspiel(boolean online, int anzahlmitspieler, int startkapital, int blindart, int Blindwert, int bigblind, int computerlevel)
	{
		blindModus=blindart;
		startzeit=System.currentTimeMillis();
		if(online)
		{singlePlayer=false;}
		else{singlePlayer=true;}
		blindBetrag=bigblind;
		if(online==false)
		{
			alleSpieler=new ArrayList<Spieler>();
			alleSpieler.add(new Humanspieler(startkapital));
			for(int i=1; i<anzahlmitspieler;i++)
			{
				alleSpieler.add(new ComputerSpieler(computerlevel, startkapital,i));
			}
			setEinsatz(0);
			alleSpieler=spielerMischen(getAlleSpieler());
			smallBlindSpieler=getAlleSpieler().get(0);
			setWettrunde(0);
			setPot(0);
			receive();	//Weiterleitung
		}
		 
	}
	
	
	public Pokerspiel(int startkapitalarg, int blindZeitRundenWertarg, int blindModusarg, int blindBetragarg){
		startzeit=System.currentTimeMillis();
		this.startkapital=startkapitalarg;
		this.blindBetrag=blindBetragarg;
		this.blindZeitRundenWert=blindZeitRundenWertarg;
		this.blindModus=blindModusarg;
		
	}

	
	public Pokerspiel(ArrayList<Spieler> alleSpieler, int Startkapital, int blindModus, int blindZeitRundenWert, int blindBetrag)
	{
		startzeit=System.currentTimeMillis();
		this.startkapital=Startkapital;
		this.setAlleSpieler(alleSpieler);
		this.blindModus=blindModus;
		this.blindBetrag=blindBetrag;
		this.blindZeitRundenWert=blindZeitRundenWert;
		
		for (Spieler n: alleSpieler)
		{
			n.setChips(Startkapital);
		}
		
	}
	
	
	
	
	public void austeilen()
	{
		//Spiel reset
		pot=0;
		einsatz=blindBestimmer();
		
		//Spieler reset
		for(Spieler n:getAlleSpieler())
		{
			n.setNochDrin(true);
			n.setChipsImPot(0);
			n.setZustand(" ");
			Log.d(n.getProfil().getName(),String.valueOf(n.getChips()));
		}
		
		
		Log.d("Austeilen zur Runde",String.valueOf(Rundenzahler));

		setWettrunde(1);
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
		Rundenzahler++;
		update();

	}
	
	
	public void spielablauf() 
	{
		//nachsterSpieler();
	//Aktueller Spieler wird aufgerufen und setzt temp
		
		if(getWettrunde()==0)
		{austeilen();
		Log.d("Blind", String.valueOf(blindBestimmer()));}
		else{
		int spielerinderRunde=0;
		for(Spieler n:getAlleSpieler())
		{
			if(n.isNochDrin()==true)
			{
				spielerinderRunde++;
			}
		}
		
		if(spielerinderRunde==1)	//NUR NOCH EIN SPIELER IN DER RUNDE
		{
			ausbezahlenAllOut();
		}
		
		
		else
		{
			if((aktiverSpieler.equals(lastRaise))&&(aktiverSpieler.schongesetzt==true))
		{
				
				for(Spieler n:getAlleSpieler())
				{
					n.schongesetzt=false;
				}
					if(getWettrunde()==4)
					{ShowDown();}
					if(getWettrunde()==3)
					{RiverCard();}
					if(getWettrunde()==2)
					{TurnCard();}
					if(getWettrunde()==1)
					{Flop();}
				

		}
			
		else{
		int temp= aktiverSpieler.setzen(this);  //HIER WIRD DER SPIELER ZUM SETZEN AUFGERUFEN
		temp-=aktiverSpieler.getChipsImPot();	//Wieviel muss der Spieler noch einbezahlen
		if(aktiverSpieler.getSidepot()==0)	//Er ist nicht ALL in
		{
			if(temp>=aktiverSpieler.getChips()) //ALL IN
			{ 	Log.d(aktiverSpieler.getProfil().getName(),"ALL IN");
				aktiverSpieler.setChipsImPot(aktiverSpieler.getChipsImPot()+aktiverSpieler.getChips()); // Chips im Pot beim Spieler aktualisieren
				einzahlen(aktiverSpieler.getChips());	//Die restlichen Chips noch einzahlen
				aktiverSpieler.setSidepot(getPot());	//Den Sidepot füllen
				aktiverSpieler.setZustand("ALL IN");
				if(aktiverSpieler.getChips()>getEinsatz())
				{
					setEinsatz(aktiverSpieler.getChips());
					setLastRaise(aktiverSpieler);
				}
				aktiverSpieler.setChips(0);				//Die Chips des Spielers auf 0 setzen

			}
			else
			{
				if(temp+aktiverSpieler.getChipsImPot()>=getEinsatz())
				//RAISE ODER CALL
				{
					Log.d(aktiverSpieler.getProfil().getName(),"CALL/RAISE");
					einzahlen(temp);
					aktiverSpieler.setChips(aktiverSpieler.getChips()-temp);
					if(temp+aktiverSpieler.getChipsImPot()>getEinsatz())
					{
					setEinsatz(temp+aktiverSpieler.getChipsImPot());
					setLastRaise(getAktiverSpieler());
					}
					aktiverSpieler.setChipsImPot(aktiverSpieler.getChipsImPot()+temp);

				}
		
		
				if(temp+aktiverSpieler.getChipsImPot()<getEinsatz())
				//Fold
				{
		
					Log.d(aktiverSpieler.getProfil().getName(),"FOLD");
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
		}		//Ende der else für neue Runde
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
		setLastRaise(getAlleSpieler().get(verschieben(--temp)));
		while(lastRaise.isNochDrin()==false)
		{setLastRaise(getAlleSpieler().get(verschieben(--temp)));}	
	}
	
	
	
	////////////////////////////////////////////////////////////////////////////
	///////////////////////////BLINDS VERWALTEN/////////////////////////////////
	////////////////////////////////////////////////////////////////////////////
	
	public int blindBestimmer()
	{
		if(blindModus==1)
		{
			int temp=(int)(Rundenzahler/blindZeitRundenWert)+1;
			return getBlindBetrag()*temp;
		}
		else
		{
			int temp=(int)((System.currentTimeMillis()-startzeit)/1000/60)+1;
			return getBlindBetrag()*temp;
		}
	}
	
	public void blindsEinzahlen()
	{
		int temp=getAlleSpieler().indexOf(smallBlindSpieler);
		pot+=smallBlindSpieler.zwangssetzen(this, blindBestimmer()/2);
		pot+=getAlleSpieler().get(verschieben(temp+1)).zwangssetzen(this, blindBestimmer());
		setEinsatz(blindBestimmer());
	}
	
	public void blindWeitergeben()
	{
	 	int temp=getAlleSpieler().indexOf(smallBlindSpieler);
	 	
		smallBlindSpieler.setZustand("Dealer");
		
		getAlleSpieler().get(verschieben(temp+1)).setZustand("Small Blind");
		smallBlindSpieler=getAlleSpieler().get(verschieben(temp+1));
		
		getAlleSpieler().get(verschieben(temp+2)).setZustand("Big Blind");
		
		setLastRaise(getAlleSpieler().get(verschieben(temp+2)));
	 	aktiverSpieler=getAlleSpieler().get(verschieben(temp+3));
	}
	
	
	
	public void einzahlen(int setzen) {
		pot+=setzen;
	}

	
	public void ausbezahlenAllOut()
	{
		aktiverSpieler.setChips(aktiverSpieler.getChips()+getPot());
		Log.d("Ausbezahlen","läuft");
		setWettrunde(0);
		
	}


	///////////////////////////////////////////////////////////////////////////
	/////////////////////////Bietrunden////////////////////////////////////////
	
	private void Flop()
	{
		setWettrunde(2);
		for(Spieler n:getAlleSpieler())
		{
			n.setChipsImPot(0);
		}
		setEinsatz(0);
		Log.d("Wettrunde","Flop");
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
		//Bietrunde nach den ersten 3 Karten
	}
	
	private void TurnCard()
	{
		Log.d("Wettrunde","TurnCard");
		setWettrunde(3);
		for(Spieler n:getAlleSpieler())
		{
			n.setChipsImPot(0);
		}
		setEinsatz(0);
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
		for(Spieler n:getAlleSpieler())
		{
			n.setChipsImPot(0);
		}
		setEinsatz(0);
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
	
	

	
	
	public void ShowDown()
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
					
					for(int n1=12;n1>=3;n1--)
					{
						if(nurFarbe[n1]>=1)
						{
							if(nurFarbe[n1-1]>=1)
							{
								if(nurFarbe[n1-2]>=1)
								{
									if(nurFarbe[n1-3]>=1)
									{
										if(n1==3)
										{
											if(nurFarbe[12]>=1)
											{
												ergebnismatrix[z][0]=9;
												ergebnismatrix[z][1]=n1;
											}
										}
										if(n1!=3)
										{
											if(nurFarbe[n1-4]>=1)
											{
												ergebnismatrix[z][0]=9;
												ergebnismatrix[z][1]=n1;
											}
										}
										else{n1=n1-5;}
									}
									else{n1=n1-4;}
								}
								else{n1=n1-3;}
							}
							else{n1=n1-2;}
						}
						else{n1=n1-1;}
						
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
			for(int m=12;m>=3;m--)
			{
				if(Wertesammeln[m]>=1)
				{
					if(Wertesammeln[m-1]>=1)
					{
						if(Wertesammeln[m-2]>=1)
						{
							if(Wertesammeln[m-3]>=1)
							{
								if(m==3)
								{
									if(Wertesammeln[12]>=1)
									{
										ergebnismatrix[z][0]=5;
										ergebnismatrix[z][1]=m;
									}
								}
								if(m!=3)
								{
									if(Wertesammeln[m-4]>=1)
									{
										ergebnismatrix[z][0]=5;
										ergebnismatrix[z][1]=m;
									}
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
			for(int y=0;y<6;y++)
			{
				n.ergebnis[y]=ergebnismatrix[z][y];
			}
		}//ENDE SPIELER AUSWERTUNG
		
		//TASK WIEDER ANSCHALTEN
		setWettrunde(0);
		
		for(int n=0;n<aktive.size()-1;n++)
		{
			for( int m=1;m<aktive.size()-n;m++)
			{
				if(compare(aktive.get(n),aktive.get(n+m))==2)
				{
					Collections.swap(aktive,n,n+m);
				}
			}
		}
		//die aktiven Spieler sollten jetzt sortiert sein
		
		int platztemp=0;
		int platzierungen[]={0,0,0,0,0,0,0,0};
		for(int n=0;n<aktive.size();n++)
		{
			if(platztemp==0)
			{
				platztemp=1;
				aktive.get(n).setPlatz(platztemp);
				platzierungen[platztemp-1]++;
			}
			else{
			if(compare(aktive.get(n),aktive.get(n-1))==0)
			{
				aktive.get(n).setPlatz(platztemp);
				platzierungen[platztemp-1]++;
			}
			else
			{
				platztemp++;
				aktive.get(n).setPlatz(platztemp);
				platzierungen[platztemp-1]++;
			}
			}
		}
		
		Log.d("Platzierungen",String.valueOf(platzierungen[0])+String.valueOf(platzierungen[1])+String.valueOf(platzierungen[2])+String.valueOf(platzierungen[3])+String.valueOf(platzierungen[4]));
		int spielertemp=0;
		for(int i=0;i<8;i++)	//Die verschiedenen Platzierungen durchgehen
		{	
			if(platzierungen[i]!=0)
			{	int davonallin=0;
				int furjeden=getPot()/platzierungen[i];
				for(int n=0;n<platzierungen[i];n++)	//Die Spieler einer Platzierung durchgehen
				{	//Erst die ALL IN Spieler
					if(aktive.get(spielertemp+n).getSidepot()!=0)
					{
						aktive.get(spielertemp+n).einzahlen(Math.min(furjeden,aktive.get(spielertemp+n).getSidepot()));
						setPot(getPot()-Math.min(furjeden,aktive.get(spielertemp+n).getSidepot()));
						davonallin++;
					}
				}
				if(platzierungen[i]-davonallin!=0)
				{
					furjeden=getPot()/(platzierungen[i]-davonallin);
				}
				for(int n=0;n<platzierungen[i];n++)	//Die Spieler einer Platzierung durchgehen
				{	//Dann die Nicht-ALL-IN-Spieler
					if(aktive.get(spielertemp+n).getSidepot()==0)
					{
						aktive.get(spielertemp+n).einzahlen(furjeden);
						setPot(getPot()-furjeden);
					}
				}
				
				if(getPot()<platzierungen[i])	//TASK TJA WAS PASSIERT MIT DEM REST
				{
					setPot(0);
				}
			}
			spielertemp+=platzierungen[i];
		}
		
		
		//TASK IST JEMAND PLEITE GEGANGEN
		boolean[] loschliste={false,false,false,false,false,false, false, false};
		for(int n=0; n<getAlleSpieler().size();n++)
		{
			Log.d("Löschen"+getAlleSpieler().get(n).getProfil().getName(),String.valueOf(getAlleSpieler().get(n).getChips()));
			if(getAlleSpieler().get(n).getChips()==0)
			{
				loschliste[n]=true;
			}
		}
	
	
		//DEN SMALL BLIND SPIELER NICHT VERLIEREN
		int temp= getAlleSpieler().indexOf(smallBlindSpieler);
		smallBlindSpieler=getAlleSpieler().get(verschieben(--temp));
		while(smallBlindSpieler.getChips()==0)
		{smallBlindSpieler=getAlleSpieler().get(verschieben(--temp));}	
		

		for(int n=7; n>=0;n--)
		{
			if(loschliste[n]==true)
			{getAlleSpieler().get(n).gameover();
			getAlleSpieler().remove(n);
			}
		}
		
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
	
	
	public int compare(Spieler spieler1, Spieler spieler2)
	{
		for(int n=0;n<6;n++)
		{
			if(spieler1.ergebnis[n]>spieler2.ergebnis[n])
			{
				return 1;
			}
			if(spieler1.ergebnis[n]<spieler2.ergebnis[n])
			{
				return 2;
			}
		}
		return 0;
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
		//Kurzgeschlossen für Singleplayer
		//if(singlePlayer==true)
		//{
		//	receive();
		//}
	}
	
	
	
	public void receive()
	{
		if(singlePlayer==true)
		{while(getAlleSpieler().size()>1)
			{spielablauf();}
		}
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

	public int getBlindModus() {
		return blindModus;
	}

	public void setBlindModus(int blindModus) {
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
