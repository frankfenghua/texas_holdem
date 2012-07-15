package mp.texas;

import java.io.IOException;

import mp.texas.push.ConnectionLog;
import mp.texas.push.PushService;
import android.app.Activity;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.provider.Settings.Secure;
import android.util.Log;
import android.widget.TextView;

public class ClientPokerspielService extends Service {
	// Die Funktionalität des Service im Hintergund muss man sich gut überlegen
	// Da dieser auch geschlossen werden kann (vom Android)
	// 
	
	/*
	 * Klasse, die den gesamten Spielablauf und den entgegennahme der
	 * Netzwerkspielerdaten aus den push notifications verarbeitet
	 */
	public static final String TAG = "ClientPokerspiel";
	private ConnectionLog cLog;
	
	private static String deviceID;
	private static String aktuellesSpielID;
	
	public static boolean SERVICE_AKTIV = false;
	public static boolean SPIEL_AKTIV = false;
	
	public static String ACTION_ID = "pokerclient";
	
	// Preferences
	private SharedPreferences cPrefs;
	
	// Service Actions
	private static final String ACTION_START = ACTION_ID + ".START";
	private static final String ACTION_STOP = ACTION_ID + ".STOP";
	private static final String ACTION_EROEFFNEN = ACTION_ID + ".EROEFFNEN";
	private static final String ACTION_ERSTELLEN = ACTION_ID + ".ERSTELLEN";
	private static final String ACTION_BEITRETEN = ACTION_ID + ".BEITRETEN";
	private static final String ACTION_LADEN = ACTION_ID + ".LADEN";
	private static final String ACTION_UPDATEN = ACTION_ID + ".UPDATEN";
	private static final String ACTION_UNSUBSCRIBE = ACTION_ID + ".UNSUBSCRIBE";
	private static final String ACTION_RECEIVE = ACTION_ID + ".RECEIVE";


	public void getDeviceID(Activity aufrufendeActivity) // soll von der aufrufendenActivity die ID als Spiel-kennung lesen
	{
		deviceID = Secure.getString(aufrufendeActivity.getContentResolver(),Secure.ANDROID_ID);
	}
	
	public static String retrunDeviceID(Activity aufrufendeActivity) // soll von der aufrufendenActivity die ID als Spiel-kennung lesen
	{
		deviceID = Secure.getString(aufrufendeActivity.getContentResolver(),Secure.ANDROID_ID);
		return deviceID;
	}
	
	// Methode um den Service zu starten
	public static void actionServiceStarten(Context ctx) {
		Intent i = new Intent(ctx, ClientPokerspielService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}
	
	// Methode um den Service zu stoppen
		public static void actionServiceStoppen(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_STOP);
			ctx.startService(i);
	}
	
		// Methode um ein Spiel zu erstellen
		public static void actionSpielEroeffnen(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_EROEFFNEN);
			ctx.startService(i);
	}
		
		// Methode um ein Spiel zu erstellen
		public static void actionSpielErstellen(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_ERSTELLEN);
			ctx.startService(i);
	}
		
		// Methode um einem Spiel beizutreten
		public static void actionSpielBeitreten(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_BEITRETEN);
			ctx.startService(i);
	}
		
		// Methode um ein Spiel up zu daten (schreibt man das man neuer rechtschreibung zusammen?)
		public static void actionUpdate(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_UPDATEN);
			ctx.startService(i);
	}
			
		// Methode um offene Spiele zu laden
		public static void actionLaden(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_LADEN);
			ctx.startService(i);
	}
		
		// Methode um offene Spiele zu laden
		public static void actionUnsubscribe(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_UNSUBSCRIBE);
			ctx.startService(i);
	}
		
		// Methode um receive aufzurufen
		public static void actionReceive(Context ctx) {
			Intent i = new Intent(ctx, ClientPokerspielService.class);
			i.setAction(ACTION_RECEIVE);
			ctx.startService(i);
	}	
		
					
	// Log-methoden des Service
	private void log(String message) {
		log(message, null);
	}

	private void log(String message, Throwable e) {
		if (e != null) {
			Log.e(TAG, message, e);

		} else {
			Log.i(TAG, message);
		}

		if (cLog != null) {
			try {
				cLog.println(message);
			} catch (IOException ex) {
			}
		}
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		log("Creating ClientPokerspielService");
//		mStartTime = System.currentTimeMillis(); // muss man schauen, ob man so was braucht
		try {
			cLog = new ConnectionLog();
			Log.i(TAG, "Opened log at " + cLog.getPath());
		} catch (IOException e) {
			Log.e(TAG, "Failed to open log", e);
		}

		// Instanz von preferences
		cPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}
	
	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);

		if (intent.getAction().equals(ACTION_START) == true) {
			SERVICE_AKTIV = true; 
			log("ClientPokerService ist gestartet");
		} else if (intent.getAction().equals(ACTION_STOP) == true) {
			log("ClientPokerService stopped");
			stopSelf();
		} else if (intent.getAction().equals(ACTION_EROEFFNEN) == true) {
			eroeffnen();
		} else if (intent.getAction().equals(ACTION_ERSTELLEN) == true) {
			erstellen();			
		} else if (intent.getAction().equals(ACTION_BEITRETEN) == true) {
				beitreten();
		} else if (intent.getAction().equals(ACTION_UPDATEN) == true) {		
				updaten();
		} else if (intent.getAction().equals(ACTION_LADEN) == true) {		
			laden();
		} else if (intent.getAction().equals(ACTION_RECEIVE) == true) {		
			receive();
		} else if (intent.getAction().equals(ACTION_UNSUBSCRIBE) == true) {		
			unsubscribe();
		};
	}

	private void eroeffnen()
		{
		App.spielErstellt = true; 
		PushService.actionEroeffnen(getApplicationContext());
		App.aktuellesSpielID = Secure.getString(this.getContentResolver(),Secure.ANDROID_ID);
		}
	
	private void laden()
		{
		PushService.actionLaden(getApplicationContext());
		}
	
	private void unsubscribe()
		{
		PushService.actionUnsubscribe(getApplicationContext());
		}
	
	private void beitreten()
		{
		
		PushService.actionSubscribe(getApplicationContext());
		PushService.actionPublishJoin(getApplicationContext()); 
		// wo (welche Activity) kommen die Leute hier hin?
	
		}
	
	private void erstellen()
		{
		App.spielErstellt = true; 
		// alle angemeldeten Spieler werden initial gesmischt und in App.pokerspiel geschrieben
		App.pokerspiel.setAlleSpieler(App.pokerspiel.spielerMischen(App.Mitspieler)); 
		
		App.pokerspiel.setEinsatz(0);
		
		App.pokerspiel.setSmallBlindSpieler(App.pokerspiel.getAlleSpieler().get(0));
		App.pokerspiel.austeilen();
		
		
		PushService.actionPublishStart(getApplicationContext());
		
		log("NEUES SPIEL ERSTELLEN (FERTIG)");
		}
	
	private void updaten()
		{
		PushService.actionUpdate(getApplicationContext());
		}
	
	private void receive()
		{	
//		SpielActivity.draw();
		}
	
}
