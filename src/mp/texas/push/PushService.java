package mp.texas.push;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;
import java.util.Map;


import mp.texas.App;
import mp.texas.BeitretenActivity;
import mp.texas.Pokerspiel;

import com.ibm.mqtt.IMqttClient;
import com.ibm.mqtt.MqttClient;
import com.ibm.mqtt.MqttException;
import com.ibm.mqtt.MqttPersistence;
import com.ibm.mqtt.MqttPersistenceException;
import com.ibm.mqtt.MqttSimpleCallback;

import android.app.AlarmManager;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.SpinnerAdapter;

import org.apache.http.NameValuePair;
import org.apache.http.client.utils.URLEncodedUtils;
import java.nio.charset.Charset;

/* 
 * PushService um die Kommunikation mit dem Server zu gewährleisten
 * Quellen:
 * 	http://code.google.com/p/android-random/source/browse/trunk/TestKeepAlive/src/org/devtcg/demo/keepalive/KeepAliveService.java?r=219
 *	https://github.com/tokudu/AndroidPushNotificationsDemo
 */
public class PushService extends Service {
	// log tag
	public static final String TAG = "PokerPushService";

	// die IP Adresse des MQTT broker
	private static final String MQTT_HOST = "147.142.42.247"; // muss man
																// schauen
	// der Port des brokers (default ist 1883)
	private static int MQTT_BROKER_PORT_NUM = 1883;
	// MQTT persistence:
	// http://publib.boulder.ibm.com/infocenter/wmqv7/v7r0/index.jsp?topic=%2Fcom.ibm.mq.amqtat.doc%2Ftt60320_.htm
	// Weiß noch nicht, wie man die einstellen muss, damit jede Nachricht auf
	// jedenfall an jeden gesendet wird
	private static MqttPersistence MQTT_PERSISTENCE = null;
	private static boolean MQTT_CLEAN_START = true;
	// Zeit wie lange die Verbindung aufrecht erhalten wird (in sekunden)
	private static short MQTT_KEEP_ALIVE = 60 * 15;
	// QoS=2 bedeutet, dass jeden Nachricht genau einmal geliefert wird.
	private static int[] MQTT_QUALITIES_OF_SERVICE = { 2 };
	private static int MQTT_QUALITY_OF_SERVICE = 0;
	// Bedeutet, dass immer nur der aktuellste Spielstand gespeichert wird (no
	// retain)
	private static boolean MQTT_RETAINED_PUBLISH = false;
	
	private static boolean MQTT_ALREADY_PUBLISHED = true;

	// MQTT client ID
	public static String MQTT_CLIENT_ID = "poker";

	// Service Actions
	private static final String ACTION_START = MQTT_CLIENT_ID + ".START";
	private static final String ACTION_PUBLISH = MQTT_CLIENT_ID + ".PUBLISH";
	private static final String ACTION_GETDEVICEID = MQTT_CLIENT_ID + ".GETDEVICEID";
	private static final String ACTION_PUBLISHJOIN = MQTT_CLIENT_ID + ".PUBLISHJOIN";
	private static final String ACTION_STOP = MQTT_CLIENT_ID + ".STOP";
	private static final String ACTION_KEEPALIVE = MQTT_CLIENT_ID + ".KEEP_ALIVE";
	private static final String ACTION_RECONNECT = MQTT_CLIENT_ID + ".RECONNECT";
	private static final String ACTION_EROEFFNEN = MQTT_CLIENT_ID + ".EROEFFNEN";
	private static final String ACTION_PUBLISHSTART = MQTT_CLIENT_ID + ".PUBLISHSTART";
	private static final String ACTION_LADEN = MQTT_CLIENT_ID + ".LADEN";
	private static final String ACTION_UNSUBSCRIBE = MQTT_CLIENT_ID + ".UNSUSCRIBE";
	private static final String ACTION_SUBSCRIBE = MQTT_CLIENT_ID + ".SUSCRIBE";

	
	// Connection log
	private ConnectionLog mLog;

	// Connectivity manager um die Verbindung mit dem broker zu verwalten
	private ConnectivityManager mConnMan;
	// Notification manager um die erhaltenen Notifications zu zeigen
	private NotificationManager mNotifMan;

	// ist true, wenn der Service aktiv ist
	private boolean mStarted;

	// In diesem Intervall wird die verbindung aktiv gehalten, auch wenn das
	// Gerät die Verbindung aktiv unterbricht
	private static final long KEEP_ALIVE_INTERVAL = 1000 * 60 * 28;

	// Wiederverbindungsintervalle, wenn keine Verbindung besteht (muss man
	// schauen, was hier Sinn macht...)
	private static final long INITIAL_RETRY_INTERVAL = 1000 * 10;
	private static final long MAXIMUM_RETRY_INTERVAL = 1000 * 60 * 30;

	// Preferences
	private SharedPreferences mPrefs;
	// gespeichert, ob der Service gestartet wird
	public static final String PREF_STARTED = "isStarted";
	// deviceID wird gespeichert
	public static final String PREF_DEVICE_ID = "deviceID";
	// letztes Verbindungsintervall wird gespeichert.
	public static final String PREF_RETRY = "retryInterval";

	// Name der Notification
	public static String NOTIF_TITLE = "Texas Holdem";
	// Notification ID
	private static final int NOTIF_CONNECTED = 0;

	// Instanz der MQTT connection
	private MQTTConnection mConnection;
	private long mStartTime;

	// static Methode um den Service zu starten
	public static void actionStart(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_START);
		ctx.startService(i);
	}

	// static Methode um über den Service zu publishen
	public static void actionPublish(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_PUBLISH);
		ctx.startService(i);
	}	
	
	// static Methode um über den Service zu publishen
	public static void actionPublishJoin(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_PUBLISHJOIN);
		ctx.startService(i);
	}	
	
	// static Methode um über den Service eine Spiel zu eröffnen
	public static void actionEroeffnen(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_EROEFFNEN);
		ctx.startService(i);
	}	
	
	// static Methode um den Service zu stopen
	public static void actionStop(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_STOP);
		ctx.startService(i);
	}

	// static Methode um dem Service "keep alive" zu schicken
	public static void actionPing(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		ctx.startService(i);
	}
	
	// static Methode um den Service die offenen Spiele laden zu lassen
	public static void actionLaden(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_LADEN);
		ctx.startService(i);
	}
	
	// static Methode um den von einem Topic abzumelden
	public static void actionUnsubscribe(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_UNSUBSCRIBE);
		ctx.startService(i);
	}

	// static Methode um den von einem Topic abzumelden
	public static void actionSubscribe(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_SUBSCRIBE);
		ctx.startService(i);
	}
	
	// static Methode um den von einem Topic abzumelden
	public static void actionPublishStart(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_PUBLISHSTART);
		ctx.startService(i);
	}
	
	// static Methode um den von einem Topic abzumelden
	public static void actionGetDeviceID(Context ctx) {
		Intent i = new Intent(ctx, PushService.class);
		i.setAction(ACTION_GETDEVICEID);
		ctx.startService(i);
	}
	
	@Override
	public void onCreate() {
		super.onCreate();

		log("Creating service");
		mStartTime = System.currentTimeMillis();

		try {
			mLog = new ConnectionLog();
			Log.i(TAG, "Opened log at " + mLog.getPath());
		} catch (IOException e) {
			Log.e(TAG, "Failed to open log", e);
		}

		// Instanzen von preferences, connectivity manager und notification manager
		mPrefs = getSharedPreferences(TAG, MODE_PRIVATE);
		mConnMan = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		mNotifMan = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

		/*
		 * If our process was reaped by the system for any reason we need to
		 * restore our state with merely a call to onCreate. We record the last
		 * "started" value and restore it here if necessary.
		 */
		handleCrashedService();
	}

	// Methode ordnet einen Server-crash auf dem System
	
	private void handleCrashedService() {
		if (wasStarted() == true) {
			log("Handling crashed service...");
			stopKeepAlives();

			// service wird wieder gestartet
			start();
		}
	}

	@Override
	public void onDestroy() {
		log("Service destroyed (started=" + mStarted + ")");

		// service wird gestoppt, fals er gastartet war
		if (mStarted == true) {
			stop();
		}

		try {
			if (mLog != null)
				mLog.close();
		} catch (IOException e) {
		}
	}

	@Override
	public void onStart(Intent intent, int startId) {
		super.onStart(intent, startId);
		log("Service started with intent=" + intent);

		if (intent.getAction().equals(ACTION_STOP) == true) {
			stop();
			stopSelf();
		} else if (intent.getAction().equals(ACTION_START) == true) {
			start();
		} else if (intent.getAction().equals(ACTION_PUBLISH) == true) {	
			publish();
		} else if (intent.getAction().equals(ACTION_PUBLISHJOIN) == true) {	
			publishJoin();
		} else if (intent.getAction().equals(ACTION_KEEPALIVE) == true) {
			keepAlive();
		} else if (intent.getAction().equals(ACTION_EROEFFNEN) == true) {			
			eroeffnen();
		} else if (intent.getAction().equals(ACTION_LADEN) == true) {
			laden();			
		} else if (intent.getAction().equals(ACTION_UNSUBSCRIBE) == true) {
			unsubscribe();		
		} else if (intent.getAction().equals(ACTION_SUBSCRIBE) == true) {
			subscribe();		
		} else if (intent.getAction().equals(ACTION_PUBLISHSTART) == true) {
			publishStart();		
//		} else if (intent.getAction().equals(ACTION_PUBLISHSTART) == true) {
//			getDeviceID();			
			//		} else if (intent.getAction().equals(ACTION_UPDATEN) == true) {			//wird ja nicht von hier aufgerufen
//			updaten();	
		} else if (intent.getAction().equals(ACTION_RECONNECT) == true) {
			if (isNetworkAvailable()) {
				reconnectIfNecessary();
			}
		}
	}

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	// Funktion des log 
	private void log(String message) {
		log(message, null);
	}

	private void log(String message, Throwable e) {
		if (e != null) {
			Log.e(TAG, message, e);

		} else {
			Log.i(TAG, message);
		}

		if (mLog != null) {
			try {
				mLog.println(message);
			} catch (IOException ex) {
			}
		}
	}

	private boolean wasStarted() {
		return mPrefs.getBoolean(PREF_STARTED, false);
	}

	private void setStarted(boolean started) {
		mPrefs.edit().putBoolean(PREF_STARTED, started).commit();
		mStarted = started;
	}

	private synchronized void start() {
		log("Starting service...");

		if (mStarted == true) {
			Log.w(TAG, "Attempt to start connection that is already active");
			return;
		}

		connect();

		registerReceiver(mConnectivityChanged, new IntentFilter(
				ConnectivityManager.CONNECTIVITY_ACTION));
	}

	private synchronized void publish() {
		log("Publishing via service...");

		if (mStarted == true) {
			Log.w(TAG, "Attempt to publish via a active connection");
		MQTT_ALREADY_PUBLISHED = false;
		publishing();
	
		}
	}
	
	private synchronized void publishJoin() {
		log("PublishingJoin via service...");

		if (mStarted == true) {
			Log.w(TAG, "Attempt to publishJoin via a active connection");
		MQTT_ALREADY_PUBLISHED = false;
		publishingJoin();
	
		}
	}

	private synchronized void publishStart() {
		log("PublishingStart via service...");

		if (mStarted == true) {
			Log.w(TAG, "Attempt to publishJoin via a active connection");
		MQTT_ALREADY_PUBLISHED = false;
		publishingStart();
	
		}
	}	
	
	private synchronized void eroeffnen() {
		log("Spiel eroeffnen via service...");

		if (mStarted == true) {
			Log.w(TAG, "Attempt to open new game via a active connection");
		MQTT_ALREADY_PUBLISHED = false;
		newPublishing();
	
		}
	}	

	private synchronized void subscribe() {
		log("subscribe to neuesSpiel...");

		if (mStarted == true && mConnection != null ) {
			Log.w(TAG, "Subscribing to new game");

			String subscribeTopic = MQTT_CLIENT_ID + "/game/" + App.aktuellesSpielID; //"/#";
			try {
				mConnection. subscribeToTopic(subscribeTopic);

			} catch (MqttException e) {
				log("subsribe geht nicht");
			}
		} else {
			log("subscribe geht nicht :((((");
		}
	}	
	
	private synchronized void laden() {
		log("Offene Spiele laden via service...");

		if (mStarted == true && mConnection != null ) {
			Log.w(TAG, "Loading new open games");

			String subscribeTopic = MQTT_CLIENT_ID + "/" + "new/#"; //"/#";
			try {
				mConnection. subscribeToTopic(subscribeTopic);

			} catch (MqttException e) {
				log("laden offener Spiele klappt nicht");
			}
		} else {
			log("laden offener Spiele klappt nicht :((((");
		}
	}
	
	private synchronized void unsubscribe() {
		log("Topic abmelden...");

		if (mStarted == true && mConnection != null ) {
			Log.w(TAG, "Unsubscribing form topic");

			try {
//				mConnection.unsubscribeFromTopic("poker/new/2001423fa17d7f77");
				int i = mConnection.unsubscribeFromTopic("#");
				log("return of unsubscribe" + Integer.toString(i));
			} catch (MqttException e) {
				log("unsubscribe klappt nicht");
			}
		} else {
			log("unsubscribe klappt nicht :((((");
		}
	}
		
	
	private synchronized void stop() {

		if (mStarted == false) {
			Log.w(TAG, "Attempt to stop connection not active.");
			return;
		}

		setStarted(false);

		unregisterReceiver(mConnectivityChanged);

		cancelReconnect();

		if (mConnection != null) {
			mConnection.disconnect();
			mConnection = null;
		}
	}

	//
	private synchronized void connect() {
		log("Connecting...");
		// device ID wird gelesen
		String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);

		if (deviceID == null) {
			log("Device ID not found.");
		} else {
			try {
				mConnection = new MQTTConnection(MQTT_HOST, deviceID);
			} catch (MqttException e) {
				// reconnect, falls es nicht geklappt hat
				log("MqttException: "
						+ (e.getMessage() != null ? e.getMessage() : "NULL"));
				if (isNetworkAvailable()) {
					scheduleReconnect(mStartTime);
				}
			}
			setStarted(true);
		}
	}
	
	private synchronized void publishing() {
		log("Publishing...");

		String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);
		
		if (mStarted == true && mConnection != null && MQTT_ALREADY_PUBLISHED == false) { //MQTT_ALRE... checkt ob es schon publ. wurde
			String publishTopic = MQTT_CLIENT_ID + "/" + deviceID; //"/#";
			try {
				mConnection.publishToTopic(publishTopic, "Hallo Peter, das klappt");

			} catch (MqttException e) {
				log("publishToTopic throws exception... der Idiot");
			}
		} else {
			log("No connection to publish to :(");
		}
	}	
	
	private synchronized void publishingJoin() {
		log("PublishingJoin...");
		
		if (mStarted == true && mConnection != null && MQTT_ALREADY_PUBLISHED == false) { //MQTT_ALRE... checkt ob es schon publ. wurde
			String publishTopic = MQTT_CLIENT_ID + "/game/" + "2001423fa17d7f77";//App.aktuellesSpielID; //"/#";
			String publishJoinString = "JOIN,"+ App.ProfilName; //Keine Ahnung wie das Bild verschickt werden soll
			log("publishing join mit topic" + publishTopic + publishJoinString);
			try {
				mConnection.publishToTopic(publishTopic, publishJoinString);

			} catch (MqttException e) {
				log("publishToTopic throws exception... der Idiot");
			}
		} else {
			log("No connection to publish to :(");
		}
	}	
	
	
	private synchronized void publishingStart() {
		log("PublishingStart...");
		
		if (mStarted == true && mConnection != null && MQTT_ALREADY_PUBLISHED == false) { //MQTT_ALRE... checkt ob es schon publ. wurde
			String publishTopic = MQTT_CLIENT_ID + "/game/" + App.aktuellesSpielID; //"/#";
			String publishJoinString = App.neuesSpielErstellen(); //Keine Ahnung wie das Bild verschickt werden soll
			try {
				mConnection.publishToTopic(publishTopic, publishJoinString);

			} catch (MqttException e) {
				log("publishToTopic throws exception... der Idiot");
			}
		} else {
			log("No connection to publish to :(");
		}
	}	
	
	private synchronized void newPublishing() {
		log("NewPublishing...");

		String deviceID = mPrefs.getString(PREF_DEVICE_ID, null);
		
		if (mStarted == true && mConnection != null && MQTT_ALREADY_PUBLISHED == false) { //MQTT_ALRE... checkt ob es schon publ. wurde
			String publishTopicNew = MQTT_CLIENT_ID + "/" + "new" + "/" + deviceID; //"/#";
			String publishTopicGame = MQTT_CLIENT_ID + "/" + "game" + "/" + deviceID;
			try {
				// Hier müssen alle Daten von app abgefragt und als String gepublished werden
				String newGameString = App.getNewGame();
				log(newGameString);

			
				mConnection.publishToTopic(publishTopicNew, newGameString);
				mConnection.publishToTopic(publishTopicGame, "Starten, Hallo Welt");
			} catch (MqttException e) {
				log("publishToTopic throws exception beim newPublishing... der Idiot");
			}
		} else {
			log("No connection to publish to :(");
		}
	}		

	private synchronized void keepAlive() {
		try {

			if (mStarted == true && mConnection != null) {
				mConnection.sendKeepAlive();
			}
		} catch (MqttException e) {
			log("MqttException: "
					+ (e.getMessage() != null ? e.getMessage() : "NULL"), e);

			mConnection.disconnect();
			mConnection = null;
			cancelReconnect();
		}
	}

	// keep-alives werden über den AlarmManager gesendet
	private void startKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.setRepeating(AlarmManager.RTC_WAKEUP,
				System.currentTimeMillis() + KEEP_ALIVE_INTERVAL,
				KEEP_ALIVE_INTERVAL, pi);
	}

	private void stopKeepAlives() {
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_KEEPALIVE);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	public void scheduleReconnect(long startTime) {
		long interval = mPrefs.getLong(PREF_RETRY, INITIAL_RETRY_INTERVAL);

		long now = System.currentTimeMillis();
		long elapsed = now - startTime;

		if (elapsed < interval) {
			interval = Math.min(interval * 4, MAXIMUM_RETRY_INTERVAL);
		} else {
			interval = INITIAL_RETRY_INTERVAL;
		}

		log("Rescheduling connection in " + interval + "ms.");

		mPrefs.edit().putLong(PREF_RETRY, interval).commit();

		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.set(AlarmManager.RTC_WAKEUP, now + interval, pi);
	}

	public void cancelReconnect() {
		Intent i = new Intent();
		i.setClass(this, PushService.class);
		i.setAction(ACTION_RECONNECT);
		PendingIntent pi = PendingIntent.getService(this, 0, i, 0);
		AlarmManager alarmMgr = (AlarmManager) getSystemService(ALARM_SERVICE);
		alarmMgr.cancel(pi);
	}

	private synchronized void reconnectIfNecessary() {
		if (mStarted == true && mConnection == null) {
			log("Reconnecting...");
			connect();
		}
	}

	// receiver schaut nach Änderungen/Updates des MQTT brokers
	private BroadcastReceiver mConnectivityChanged = new BroadcastReceiver() {
		@Override
		public void onReceive(Context context, Intent intent) {
			// Netzwerinfo wird gelesen
			NetworkInfo info = (NetworkInfo) intent
					.getParcelableExtra(ConnectivityManager.EXTRA_NETWORK_INFO);

			boolean hasConnectivity = (info != null && info.isConnected()) ? true
					: false;

			log("Connectivity changed: connected=" + hasConnectivity);

			if (hasConnectivity) {
				reconnectIfNecessary();
			} else if (mConnection != null) {
			
				mConnection.disconnect();
				cancelReconnect();
				mConnection = null;
			}
		}
	};

	// Notifivation wird angezeigt
	private void showNotification(String text) {
		Notification n = new Notification();

		n.flags |= Notification.FLAG_SHOW_LIGHTS;
		n.flags |= Notification.FLAG_AUTO_CANCEL;

		n.defaults = Notification.DEFAULT_ALL;

		n.icon = mp.texas.R.drawable.texas_holdem_icon;
		n.when = System.currentTimeMillis();

		// Simply open the parent activity
		PendingIntent pi = PendingIntent.getActivity(this, 0, new Intent(this,
				mp.texas.startActivity.class), 0);

		// Change the name of the notification here
		n.setLatestEventInfo(this, NOTIF_TITLE, text, pi);

		mNotifMan.notify(NOTIF_CONNECTED, n);
	}
	
	// Gamestate wird geupdatet wird angezeigt
	private void updateGamestate(String text) {
		
		//Hier kann noch ein Test eingebaut werden, ob die Poker App gerade offen ist, und falls nicht eine notification über den
		// ZUg (wie oben) mitgesendet werden
		
		App.setGamestate(text);
		log("Gamestate uebermittelt.");
		
	}	
	

	// Checkt ob es ein netzwek gibt
	private boolean isNetworkAvailable() {
		NetworkInfo info = mConnMan.getActiveNetworkInfo();
		if (info == null) {
			return false;
		}
		return info.isConnected();
	}

	private class MQTTConnection implements MqttSimpleCallback {
		IMqttClient mqttClient = null;

		// schafft eine Verbindung mit broker addresse und dem start/topic
		public MQTTConnection(String brokerHostName, String initTopic)
				throws MqttException {

			String mqttConnSpec = "tcp://" + brokerHostName + "@"
					+ MQTT_BROKER_PORT_NUM;

			mqttClient = MqttClient.createMqttClient(mqttConnSpec,
					MQTT_PERSISTENCE);
			String clientID = MQTT_CLIENT_ID + "/"
					+ mPrefs.getString(PREF_DEVICE_ID, "");
			mqttClient.connect(clientID, MQTT_CLEAN_START, MQTT_KEEP_ALIVE);

			mqttClient.registerSimpleHandler(this);

			// Aboniiert das Anfangstopic, was aus ClientID und deviceID ist
			initTopic = MQTT_CLIENT_ID + "/" + initTopic; //"/#";
			subscribeToTopic(initTopic);

			log("Connection established to " + brokerHostName + " on topic "
					+ initTopic);

			mStartTime = System.currentTimeMillis();
			startKeepAlives();
		}

		public void disconnect() {
			try {
				stopKeepAlives();
				mqttClient.disconnect();
			} catch (MqttPersistenceException e) {
				log("MqttException"
						+ (e.getMessage() != null ? e.getMessage() : " NULL"),
						e);
			}
		}

		/*
		 * schickt dem broker eine Nachricht Änderungen an dem abonnierten Topic zu übernehemen
		 * Wildcards können verwednet werden (# für alle unter topics, * für alle eine ebene drunter)
		 */
		private void subscribeToTopic(String topicName) throws MqttException {

			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
				// checkt ob eine verbindung da ist
				log("Connection error" + "No connection");
			} else {
				String[] topics = { topicName };
				mqttClient.subscribe(topics, MQTT_QUALITIES_OF_SERVICE);
			}
		}
		
		private int unsubscribeFromTopic(String topicName) throws MqttException {
			int i = 0;
			if ((mqttClient == null) || (mqttClient.isConnected() == false)) {
				// checkt ob eine verbindung da ist
				log("Connection error" + "No connection");
			} else {
				String[] topics = { topicName };
				 i = mqttClient.unsubscribe(topics);
	
			}
			return i;
		}

		/*
		 * Schickt dem Broker eine Publish Massage
		 */
		private void publishToTopic(String topicName, String message)
				throws MqttException {
			if ((mqttClient == null ) || (mqttClient.isConnected() == false)) {
				// checkt ob eine verbindung da ist
				log("No connection to public to");
			} else {
				mqttClient.publish(topicName, message.getBytes(),
						MQTT_QUALITY_OF_SERVICE, MQTT_RETAINED_PUBLISH);
				MQTT_ALREADY_PUBLISHED = false; // wird gesetzt, das die nachricht versendet wurde
			}
		}

		/*
		 * Wir aufgerufen wenn das Gerät die Verbindung zum broker verliert
		 */
		public void connectionLost() throws Exception {
			log("Loss of connection" + "connection downed");
			stopKeepAlives();
			// null itself
			mConnection = null;
			if (isNetworkAvailable() == true) {
				reconnectIfNecessary();
			}
		}

		/*
		 * Wird aufgerufen, wenn eine Nachricht auf dem Gerät angekommen ist.
		 */
		public void publishArrived(String topicName, byte[] payload, int qos,
				boolean retained) {
			
			String s = new String(payload);

//			showNotification(s);
//			hier muss bei dem angekommenen Stirng getestet werden, um welche Art nachricht es sich handelt 
//			und entsprechend auseinander genommen werden
			
			List<String> items = Arrays.asList(s.split("\\s*,\\s*"));
			log("payload =" + items.toString());

			if(items.get(0).equalsIgnoreCase("OPEN"))
			{	
	
			String message_s = items.get(1);
			App.addOpenGame(items.get(1),Integer.parseInt(items.get(3)), Integer.parseInt(items.get(5)), items.get(7), Integer.parseInt(items.get(9)), Integer.parseInt(items.get(11)));

			final Message msg = Message.obtain();
			msg.obj = message_s;
					
			BeitretenActivity.mHandler.post(new Runnable(){
		        public void run() {
					BeitretenActivity.mHandler.handleMessage(msg);
		        }
		    });
			
			msg.recycle();
			
			//App.offeneSpiele.add();
			log(items.get(1) + "," +items.get(2) + "," + items.get(3) + "," +items.get(4) + "," +items.get(5) + "," +items.get(6)) ;

			
			}
			else if (items.get(0).equalsIgnoreCase("JOIN"))
			{
				showNotification(s);
			App.addSpieler(items.get(1));
			log(items.get(1) + " has joined the game");
		
			}
			
			else if (items.get(0).equalsIgnoreCase("STARTEN"))
			{
//			log(items.get(1) + "," +items.get(2) + "," +items.get(3) + "," +items.get(4) + "," +items.get(5) + "," +items.get(6) 
//					+ "," +items.get(7) + "," +items.get(8) + "," +items.get(9) + "," +items.get(10) + "," +items.get(11) + "," +items.get(12) 
//					+ "," +items.get(13) + "," +items.get(14) );
				
				
			}
			else if (items.get(0).equalsIgnoreCase("UPDATEN"))
			{
				log("test" + items.toString());	
				updateGamestate(s);
			}
			

			
			
			log("Got message: " + s);
		}
		

		public void sendKeepAlive() throws MqttException {
			log("Sending keep alive");
			publishToTopic(MQTT_CLIENT_ID + "/keepalive",
					mPrefs.getString(PREF_DEVICE_ID, ""));
		}
	}
}