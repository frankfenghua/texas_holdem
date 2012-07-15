/*package mp.texas;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class AblaufService extends Service {

	Thread thread = new Thread(new Runnable() {
		
		public void run() {
		while(App.pokerspiel.getAlleSpieler().size()>1)
		{
			App.pokerspiel.spielablauf();
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		//	getApplicationContext().sendBroadcast(new Intent("DRAW"));
		//	Log.d("Receive","gesendet");
			
			}
		
		}
	});
	
	@Override
	public void onCreate() {
		super.onCreate();
		/*App.pokerspiel.spielablauf();
		App.pokerspiel.spielablauf();
		App.pokerspiel.spielablauf();

		registerReceiver(receiver, new IntentFilter("DRAWED"));
		
		Log.d("Broadcast","gesendet");
		getApplicationContext().sendBroadcast(new Intent("DRAW"));
		//thread.run();
		
	}
	 drawedReceiver receiver = new drawedReceiver();
	
	class drawedReceiver extends BroadcastReceiver
	{

		@Override
		public void onReceive(Context context, Intent intent) {
			App.pokerspiel.spielablauf();
			getApplicationContext().sendBroadcast(new Intent("DRAW"));
	
		
		
		//thread.run();
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		return null;
	}

}
*/
