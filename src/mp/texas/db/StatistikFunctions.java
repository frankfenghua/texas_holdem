package mp.texas.db;

import java.util.ArrayList;
import java.util.List;


import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;
	 
import android.util.Log;
	 
	public class StatistikFunctions {
	 
	    private JSONParser jsonParser;
	 
	    private static String locationURL = "http://www.raser.heliohost.org/texas_holdem/statistik/";
	    
	    private static String statistikladen_tag = "statistikladen";
	    private static String statistikspeichern_tag = "statistikspeichern";

	    
	    // Konstructor
	    public StatistikFunctions(){
	        jsonParser = new JSONParser();
	    }
	 
	  
	    public JSONObject statistikAufrufen(String spieler){
	        // Building Parameters
	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        params.add(new BasicNameValuePair("tag", statistikladen_tag));
	        params.add(new BasicNameValuePair("uid", spieler));

	        JSONObject json = jsonParser.getJSONFromUrl(locationURL, params);
//	        // Rückgabe des json Objekts
	        return json;
	    }
//	    
//	    /**
//	     * Funktion für den Statistik Request an die MYSQL database
//	     * */
	    
	    public int statistikAusgabe(JSONObject json){

	    	int ausgabe;

	    	int geld = json.optInt("geld");
			
			ausgabe = geld;    	
	    	        
//	        // Rückgabe des json Objekts
	        return ausgabe;
	    }
//
	    
//	    /**
//	     * Funktion zum Laden der Statistik(uid)
//	     *  
//	     * */    
	    public int statistikLaden(String spieler){
	    
	    	JSONObject json = statistikAufrufen(spieler);
	    	int result = statistikAusgabe(json);
	    	return result;
	    	
	    }
	    

	    public JSONObject statistikSpeichern(String spieler, int geld){
	        // Building Parameters

	        List<NameValuePair> params = new ArrayList<NameValuePair>();
	        	params.add(new BasicNameValuePair("tag", statistikspeichern_tag));
	        	params.add(new BasicNameValuePair("uid", spieler));
	        	params.add(new BasicNameValuePair("geld", Integer.toString(geld)));
	        	
	          	Log.d("Statistik erfolgreich", "scheint bis hier zu gehen");

	        JSONObject json = jsonParser.getJSONFromUrl(locationURL, params);
	        // Rückgabe des json Objekts
	        return json;
	    }
	

//	    /**
//	     * Hilfsklasse zum Konvertieren von Integer nach Boolean
//	     */
//		public boolean convertIntToBoolean(int intValue)	
//		{
//		return (intValue != 0);
//		}	
		
}