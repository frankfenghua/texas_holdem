/*Klasse enthält ein Spielblatt, wesentliche Funktionen sind:
 * Erzeugung aller Karten
 * Mischeln der Karteb
 * Gemeinschaftskarten erzeugen
 * Hände austeilen
 */

package mp.texas;

import java.util.ArrayList;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.widget.ImageView;

public class Blatt 
{
	private ArrayList<Karte> karten = new ArrayList<Karte>();
	public ArrayList<Karte> getKarten() {
		return karten;
	}

	public void setKarten(ArrayList<Karte> karten) {
		this.karten = karten;
	}

	
	public Blatt()
	{
		karten=new ArrayList<Karte>();
		//Hier werden alle Karten erzeugt
		for(int i=0; i<4; i++)
		{
			for(int j=2; j<=14; j++)
				{	
				karten.add(new Karte(i,j));
				}
		}
		blattMischen(karten);
	}
	
	public ArrayList<Karte> blattMischen(ArrayList<Karte> liste)
	{ 
		/*Karten werden gemischt die Zahl in der For-Schleife gibt misch qualität an, sollte >52 sein
		 * Mischel verfahren, basiert in dem herausziehen einer Karte und dem Wiedereinfügen an zufälliger Stelle 
		 */
	
		
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
		if(karten.size()>=2)
		{
		Hand temp=new Hand(karten.get(0),karten.get(1));
		karten.remove(0);
		karten.remove(0);
		return temp;
		}
		else
		{	Log.d("Blatt.handgeben()","Nicht genügend Karten mehr vorhanden");
			return null;}
	}
	
	public Gemeinschaftskarten gemeinschaftskartenGeben()
	{
		if(karten.size()>=5)
		{
		Gemeinschaftskarten temp=new Gemeinschaftskarten(karten.get(0),karten.get(1),karten.get(2),karten.get(3),karten.get(4));
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		karten.remove(0);
		return temp;
		}
		
		else
		{	Log.d("Blatt.gemeinschaftskarten()","Nicht genügend Karten mehr vorhanden");
			return null;
		}
		
		
	}
	

	

}
