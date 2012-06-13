package mp.texas;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


public class profilActivity extends Activity 
{

	Button statistiken;
	ImageButton bild;
	EditText name;
	File storageDir;
	String mCurrentPhotoPath;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub

		super.onCreate(savedInstanceState);
		setContentView(R.layout.profil);
		statistiken=(Button) findViewById(R.id.buttonProfilStatistik);
		statistiken.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{                Log.d("Button", "Statistiken"); // Perform action on click             
					}         
				});
				
		bild=(ImageButton) findViewById(R.id.imageButtonProfilBild);
		bild.setOnClickListener(
				new View.OnClickListener() 
				{             
					public void onClick(View v) 
					{     
						//TakePicture(1);
						showDialog(1);
						Log.d("Button", "Bild"); 
									// Perform action on click             
					}         
				});
		
		name=(EditText) findViewById(R.id.editTextProfilName);
	
	}
	
								
							

	@Override
	protected Dialog onCreateDialog(int id) {    
		final CharSequence[] items = {"Bild aufnehmen", "Galerie", "Standard-Avatare"};
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Quelle");
		builder.setItems(items, new DialogInterface.OnClickListener() 
		{    
			public void onClick(DialogInterface dialog, int item) 
			{     
				if(item==0)
				{
					TakePicture(1);
				}
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();    }});
		AlertDialog alert = builder.create();   
		return alert;
	}




	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	private void TakePicture(int actionCode) 
	{    
		
		if(isIntentAvailable(getApplicationContext(),MediaStore.ACTION_IMAGE_CAPTURE))
		{
		storageDir = Environment.getExternalStorageDirectory() ;
		 File f=new File(storageDir,"Bild.jpg");
		 mCurrentPhotoPath = f.getAbsolutePath(); 
			if(f.exists()==false)
			{
		 try {
			f.createNewFile();
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			}
			Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(f));
			startActivityForResult(takePictureIntent, actionCode);

		

			//Toast.makeText(getApplicationContext(), "Leider nicht möglich", Toast.LENGTH_LONG);
		   
		
		}
		else{Toast.makeText(getApplicationContext(), "Keine Kamera-App verfügbar", Toast.LENGTH_LONG);}
	}


	private File createImageFile() throws IOException 
	{    // Create an image file name    
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());    
		String imageFileName = "Avatar" + timeStamp + "_";    
		File image = File.createTempFile(imageFileName,".jpg",storageDir);    
		mCurrentPhotoPath = image.getAbsolutePath();   
		Log.d("File","mCurrentPhotoPath");
		return image;
	}
	
	
	public static boolean isIntentAvailable(Context context, String action) 
	{    
		final PackageManager packageManager = context.getPackageManager();    
		final Intent intent = new Intent(action);    
		List<ResolveInfo> list =            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);    
		return list.size() > 0;
		}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		// TODO Auto-generated method stub
		//Bundle extras = data.getExtras();    
		//Bitmap mImageBitmap = (Bitmap) extras.get("data");    
		//bild.setImageBitmap(mImageBitmap);
		Log.d("Bild","sollte kommen");
	    int targetW = bild.getWidth();    int targetH = bild.getHeight();      
	    BitmapFactory.Options bmOptions = new BitmapFactory.Options();    
	    bmOptions.inJustDecodeBounds = true;    
	    BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);    
	    int photoW = bmOptions.outWidth;    int photoH = bmOptions.outHeight;   
	    int scaleFactor = Math.min(photoW/targetW, photoH/targetH);  
	    bmOptions.inJustDecodeBounds = false;    
	    bmOptions.inSampleSize = scaleFactor;    
	    bmOptions.inPurgeable = true;      
	    Bitmap bitmap = BitmapFactory.decodeFile(mCurrentPhotoPath, bmOptions);    
	    bild.setImageBitmap(bitmap);
	 
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	

}

