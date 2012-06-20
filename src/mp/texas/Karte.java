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
	/*
	public static ImageView getKartenImageView(int Wert, int Farbe, Context context)
	{
		if(blatt==null)
		{
			
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
		
		ImageView temp = new ImageView(context);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 
                R.drawable.playingcards); 
		bitmap=Bitmap.createBitmap(bitmap, bitmap.getWidth()/13*Wert, bitmap.getHeight()/5*Farbe, bitmap.getWidth()/13, bitmap.getHeight()/5);
		temp.setImageBitmap(bitmap);
		return temp;
	}
	*/
	
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
		
		/*//--
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(context.getResources(), R.drawable.playingcards, options);
		int imageHeight = options.outHeight;
		int imageWidth = options.outWidth;
		String imageType = options.outMimeType;
		
		
		//--
		//ImageView temp = new ImageView(context);
		Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), 
                R.drawable.playingcards); 
		bitmap=Bitmap.createBitmap(bitmap, bitmap.getWidth()/13*Wert, bitmap.getHeight()/5*Farbe, bitmap.getWidth()/13, bitmap.getHeight()/5);
		//temp.setImageBitmap(bitmap);
		*/
		return Bitmap.createBitmap(blatt, blatt.getWidth()/13*Wert, blatt.getHeight()/5*Farbe, blatt.getWidth()/13, blatt.getHeight()/5);
	}
	

	public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) 
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
	
	public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,int reqWidth, int reqHeight) 
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
