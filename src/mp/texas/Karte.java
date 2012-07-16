/*
 * Stellt eine Karte dar, besitzt Wert und Farbe
 * Beinhaltet die Funktionen
 * Bitmap f¸r Karte erstellen
 */
package mp.texas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

public class Karte 
{
	public static final int Kreuz=0;
	public static final int Karo=1;
	public static final int Pik=3;
	public static final int Herz=2;
	public static final int Zwei=2;
	public static final int Drei=3;
	public static final int Vier=4;
	public static final int Funf=5;
	public static final int Sechs=6;
	public static final int Sieben=7;
	public static final int Acht=8;
	public static final int Neun=9;
	public static final int Zehn=10;
	public static final int Bube=11;
	public static final int Dame=12;
	public static final int Konig=13;
	public static final int Ass=14;
	static Bitmap blatt=null; 
	
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
	
	
	
	//Erstellung einer Bitmap mit Farbe und Wert
	public static Bitmap getKartenBild(int Wert, int Farbe, int reqWidth, int reqHeight , Context context)
	{
		
		if(reqWidth==0)
		{
			reqWidth=60;
		}
		if(reqHeight==0)
		{
		reqHeight=100;
		}
	
		if(blatt==null)
		{
			blatt=decodeSampledBitmapFromResource(context.getResources(),R.drawable.playingcards, reqWidth*13, reqHeight*5);
		}

		if(Wert!=0)	
		{		
		
			if(Wert==Karte.Ass)
			{
				Wert=0;
			}
		
			else
			{
				Wert-=1;
			}
		}
		else
		{	
			Wert=2;
			Farbe=4;
		}
		
		return Bitmap.createBitmap(blatt, (int)((blatt.getWidth()-2)/13.*Wert), (int)(blatt.getHeight()/5.*Farbe), (int)(blatt.getWidth()/13.),(int)(blatt.getHeight()/5.));
	}
	
	//Erstellung der Bitmap mit einer Karte
	public static Bitmap getKartenBild(Karte karte, int reqWidth, int reqHeight , Context context)
	{
		int wert=karte.getWert();
		int farbe=karte.getFarbe();
		
		if(reqWidth==0)
		{
			reqWidth=60;
		}
		if(reqHeight==0)
		{
		reqHeight=100;
		}
	
		if(blatt==null)
		{
			blatt=decodeSampledBitmapFromResource(context.getResources(),R.drawable.playingcards, reqWidth*13, reqHeight*5);
		}

		if(wert!=0)	
		{		
		
			if(wert==Karte.Ass)
			{
				wert=0;
			}
		
			else
			{
				wert-=1;
			}
		}
		else
		{	
			wert=2;
			farbe=4;
		}
	
		return Bitmap.createBitmap(blatt, (int)((blatt.getWidth()-2)/13.*wert), (int)(blatt.getHeight()/5.*farbe), (int)(blatt.getWidth()/13.),(int)(blatt.getHeight()/5.));
	}
	
	
	//Internes um Bildgrˆﬂe abzusch‰tzen
	private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
	{    // Raw height and width of image    
		final int height = options.outHeight;    
		final int width = options.outWidth;    
		int inSampleSize = 1;    
		if (height > reqHeight || width > reqWidth) 
		{        
			if (width > height) 
			{          
				inSampleSize = Math.round((float)height / (float)reqHeight);        
			} 
			else 
			{
				inSampleSize = Math.round((float)width / (float)reqWidth);        
			}    
		}    
		return inSampleSize;
	}
	
	
	//Internes um Bild nicht zu groﬂ zu laden
	private static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) 
	{    // First decode with inJustDecodeBounds=true to check dimensions    
		final BitmapFactory.Options options = new BitmapFactory.Options();    
		options.inJustDecodeBounds = true;    
		BitmapFactory.decodeResource(res, resId, options);    // Calculate inSampleSize    
		options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);    // Decode bitmap with inSampleSize set    
		Log.d("Faktor",String.valueOf(calculateInSampleSize(options, reqWidth, reqHeight)));
		options.inJustDecodeBounds = false;    
		return BitmapFactory.decodeResource(res, resId, options);
	}
	
}
