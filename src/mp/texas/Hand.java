/*
 * Speicherklasse für die Hand eines Spielers
 * Enthält lediglich die set und get Funktionen für die beiden Karten
 */
package mp.texas;

public class Hand 
{
	private Karte karte1;
	private Karte karte2;
	
	
	public Hand(Karte karte1, Karte karte2) {
		this.karte1=karte1;
		this.karte2=karte2;
	}
	public Karte getKarte1() {
		return karte1;
	}
	public void setKarte1(Karte karte1) {
		this.karte1 = karte1;
	}
	public Karte getKarte2() {
		return karte2;
	}
	public void setKarte2(Karte karte2) {
		this.karte2 = karte2;
	}

}
