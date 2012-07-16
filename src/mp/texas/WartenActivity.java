package mp.texas;

import java.util.Timer;
import java.util.TimerTask;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.Toast;


public class WartenActivity extends Activity{
static boolean spielda = false;


public static boolean isSpielda() {
	return spielda;
}

public static void setSpielda(boolean spielda) {
	WartenActivity.spielda = spielda;
}


Timer myTimer=new Timer();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.warten);
		
		Handler mHandler = new Handler() {
	        @Override
	        public void handleMessage(Message msg) {
	        	super.handleMessage(msg);
	        	WartenActivity.spielda = true;
	        }
	       
		};
		
		
		
		myTimer=new Timer();
	    myTimer.schedule(new TimerTask() 
	        								{
	        									@Override
	        									public void run() 
	        									{
	        										TimerMethod();
	        									}

	        								}, 0, 1000);
	        
	        }
		
		
		 private void TimerMethod()
	        {
	        this.runOnUiThread(Timer_Tick);
	        }
	      
	        
	        
	        private Runnable Timer_Tick = new Runnable() 
	        {
	        	public void run() 
	        	{
	        		if(spielda == true)
	        		{
	        		startActivity(new Intent(getApplicationContext(),SpielActivity.class));
	        		myTimer.cancel();
	        		}
	        	}

	        };
	        
}
	        

