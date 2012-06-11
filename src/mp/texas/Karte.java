package mp.texas;

public class Karte 
{
	public final int Kreuz=1;
	public final int Karo=2;
	public final int Pik=3;
	public final int Herz=4;
	public final int Zwei=2;
	public final int Drei=3;
	public final int Vier=4;
	public final int Funf=5;
	public final int Sechs=6;
	public final int Sieben=7;
	public final int Acht=8;
	public final int Neun=9;
	public final int Zehn=10;
	public final int Bube=11;
	public final int Dame=12;
	public final int Konig=13;
	public final int Ass=14;
	
	private int farbe;
	private int wert;
	
	
	public Karte(int i, int j)
	{farbe=i; wert=j;}
	
	
	public int getFarbe() {
		return farbe;
	}
	public void setFarbe(int farbe) {
		this.farbe = farbe;
	}
	public int getWert() {
		return wert;
	}
	public void setWert(int wert) {
		this.wert = wert;
	}
	

}
