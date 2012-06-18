package mp.texas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
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
	
	public static ImageView getKartenImageView(int Wert, int Farbe, Context context)
	{
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
		
		ImageView temp = new ImageView(context);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 
                R.drawable.playingcards); 
		bitmap=Bitmap.createBitmap(bitmap, bitmap.getWidth()/13*Wert, bitmap.getHeight()/5*Farbe, bitmap.getWidth()/13, bitmap.getHeight()/5);
		temp.setImageBitmap(bitmap);
		return temp;
	}
	
	
	public static Bitmap getKartenBild(int Wert, int Farbe, Context context)
	{
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
		
		//ImageView temp = new ImageView(context);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 
                R.drawable.playingcards); 
		bitmap=Bitmap.createBitmap(bitmap, bitmap.getWidth()/13*Wert, bitmap.getHeight()/5*Farbe, bitmap.getWidth()/13, bitmap.getHeight()/5);
		//temp.setImageBitmap(bitmap);
		return bitmap;
	}
	

}
