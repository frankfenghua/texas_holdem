package mp.texas;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
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
import android.widget.ImageView.ScaleType;
import android.widget.Toast;


public class profilActivity extends Activity 
{

	Button statistiken;
	ImageButton bild;
	EditText name;
	File storageDir;
	File file;
	String mCurrentPhotoPath;
	App app;

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		app=((App)(getApplication()));
		
			
		
		Log.d("Zustand","Create");
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
		if(app.selbst.getProfil().getAvatar()!=null)
		{
			Log.d("Bild","ungleich null");
			bild.setImageDrawable(app.selbst.getProfil().getAvatar());
		    bild.setScaleType(ScaleType.CENTER_INSIDE);
		}
		

		
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
		if(app.selbst.getProfil().getName()!=null)
		{name.setText(app.selbst.getProfil().getName());}
	}
	
								
							

	@Override
	protected Dialog onCreateDialog(int id) {    
		final CharSequence[] items = {"Bild aufnehmen", "Galerie"};
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
				
				if(item==1)
				{
					LoadPicture(2);
				}
				
				Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();    }});
		AlertDialog alert = builder.create();   
		return alert;
	}


		
	protected void StandardAvatar(int i) {
		// TODO Auto-generated method stub
		
	}





	@Override
	protected void onPause() {
		Log.d("Zustand","Pause");
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	protected void onRestart() {
		Log.d("Zustand","Restart");
		// TODO Auto-generated method stub
		super.onRestart();
	}

	@Override
	protected void onResume() {
		Log.d("Zustand","Resume");
		// TODO Auto-generated method stub
		super.onResume();
	}

	@Override
	protected void onStart() {
		Log.d("Zustand","Start");
		// TODO Auto-generated method stub
		super.onStart();
	}

	@Override
	protected void onStop() {
		Log.d("Zustand","Stop");
		// TODO Auto-generated method stub
		super.onStop();
	}
	
	
	private void LoadPicture(int actionCode)
	{
		Intent i = new Intent(                        
		Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);                                 
		startActivityForResult(i, 2);
	}
	
	private void TakePicture(int actionCode) 
	{    
		
		if(isIntentAvailable(getApplicationContext(),MediaStore.ACTION_IMAGE_CAPTURE))
		{
		storageDir = Environment.getExternalStorageDirectory() ;
		 File f=new File(storageDir, App.selbst.getProfil().getId() +".jpg");
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
			file=f;
			startActivityForResult(takePictureIntent, actionCode);
		}
		else{Toast.makeText(getApplicationContext(), "Keine Kamera-App verfügbar", Toast.LENGTH_LONG);}
	}

	
	
	public static boolean isIntentAvailable(Context context, String action) 
	{    
		final PackageManager packageManager = context.getPackageManager();    
		final Intent intent = new Intent(action);    
		List<ResolveInfo> list =            packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);    
		return list.size() > 0;
		}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data)
	{
		
        if (requestCode == 2 && resultCode == RESULT_OK && null != data) 
        {            Uri selectedImage = data.getData();            
        String[] filePathColumn = { MediaStore.Images.Media.DATA };             
        Cursor cursor = getContentResolver().query(selectedImage,filePathColumn, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
        String picturePath = cursor.getString(columnIndex);
        cursor.close();
        bild.setImageBitmap(BitmapFactory.decodeFile(picturePath));
        bild.setScaleType(ScaleType.CENTER_INSIDE);
 
        
        
        }
        if(requestCode ==1)
        {
		
		// TODO Auto-generated method stub
		//Bundle extras = data.getExtras();    
		//Bitmap mImageBitmap = (Bitmap) extras.get("data");    
		//bild.setImageBitmap(mImageBitmap);
		Log.d("Bild","sollte kommen");
	    bild.setImageURI(Uri.fromFile(file));
	    bild.setScaleType(ScaleType.CENTER_INSIDE);
	    
        }
        app.selbst.getProfil().setAvatar(bild.getDrawable());
		super.onActivityResult(requestCode, resultCode, data);
	}




	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		App.selbst.getProfil().setName(name.getText().toString());
		
		super.onBackPressed();
	}
	
	
	
	

}

