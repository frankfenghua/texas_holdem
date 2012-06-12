package mp.texas;

import java.util.ArrayList;

public class Blatt 
{
	private ArrayList<Karte> karten;
	public ArrayList<Karte> getKarten() {
		return karten;
	}

	public void setKarten(ArrayList<Karte> karten) {
		this.karten = karten;
	}

	public ArrayList<Karte> getKartenOffen() {
		return kartenOffen;
	}

	public void setKartenOffen(ArrayList<Karte> kartenOffen) {
		this.kartenOffen = kartenOffen;
	}

	private ArrayList<Karte> kartenOffen;
	
	public Blatt()
	{
		for(int i=1; i<=4; i++)
		{
			for(int j=2; j<=14; j++)
				karten.add(new Karte(i,j));
		}
		blattMischen(karten);
	}
	
	public ArrayList<Karte> blattMischen(ArrayList<Karte> liste)
	{
		for(int i=0;i<1000;i++)
		{
			int rand1=(int)(Math.random()*52);
			int rand2=(int)(Math.random()*52);
			Karte temp=liste.get(rand1);
			liste.remove(rand1);
			liste.add(rand2, temp);
		}
		return liste;
	}
	
	
	public Hand handGeben()
	{
		Hand temp=new Hand(karten.get(0),karten.get(1));
		karten.remove(0);
		karten.remove(0);
		return temp;
	}
	
	public Gemeinschaftskarten gemeinschaftskartenGeben()
	{
		Gemeinschaftskarten temp=new Gemeinschaftskarten(karten.get(0),karten.get(1),karten.get(2),karten.get(3),karten.get(4));
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		
		return temp;
	}
	

}
