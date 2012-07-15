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
	 
	  
//	    public JSONObject statistikAufrufen(int latitude, int longitude, int umkreis){
//	    	// lädt die strecken im umkreis um den Punkt latitude/longitude und gibt diese als JSON zurück oder lädt sie zu SQLite
//
//	        // Building Parameters
//	        List<NameValuePair> params = new ArrayList<NameValuePair>();
//	        params.add(new BasicNameValuePair("tag", magnetkarteladen_tag));
//	        params.add(new BasicNameValuePair("latitude", Integer.toString(latitude)));
//	        params.add(new BasicNameValuePair("longitude", Integer.toString(longitude)));
//	        params.add(new BasicNameValuePair("umkreis", Integer.toString(umkreis)));
//
//	        JSONObject json = jsonParser.getJSONFromUrl(locationURL, params);
//	        // Rückgabe des json Objekts
//	        return json;
//	    }
//	    
//	    /**
//	     * Funktion für den Streckenausgabe Request an die MYSQL database
//	     * */
//	    public Magnetkarte magnetkarteAusgabe(JSONObject json){
//	    	// gibt eine Magnetkarte zurück, welche alle geladenen Kartenpunkte enthält
//
//	    	Magnetkarte ausgabe = new Magnetkarte(); //List der ganzen aufgerufenen Strecken
//
//	    	try {
//	 		
//	    		int anzahlReihen = json.optInt("anzahlReihen");
//	    		
////			    Map<Integer, List<Kartenpunkt>> kpMap = new HashMap<Integer, List<Kartenpunkt>>();					
//	    		
//				for(int i=1; i<=anzahlReihen; i++)	//die einzelnen KP werden aus dem jsonObjekt ausgelesen
//	        	{
//					JSONObject json_kpTemp = json.getJSONObject("laden"+Integer.toString(i)); //ließt die einzelnen Zeilen aus
//				
//					Kartenpunkt kpTemp = new Kartenpunkt(json_kpTemp.optInt("latitude"), json_kpTemp.optInt("longitude"), json_kpTemp.optInt("magneticvertical"), json_kpTemp.optInt("magnetichorizontal"), json_kpTemp.getInt("anzahl")); //erstellt einen neuen KP
//					
//					ausgabe.add(kpTemp);
//				}
////				Log.d("wkMap Größe", Integer.toString(wpMap.size()));
////				Log.d("wkMap nach for-Schleife", wpMap.toString());	
//								
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}    	
//	    	        
//	        // Rückgabe des json Objekts
//	        return ausgabe;
//	    }
//
//	    
//	    /**
//	     * Funktion zum Laden der Magnetkarte zu den gegebenen Koordinaten+Umkreis
//	     *  
//	     * */    
//	    public Magnetkarte magnetkarteLaden(int latitude, int longitude, int umkreis){
//	    
//	    	JSONObject json = magnetkarteAufrufen(latitude, longitude, umkreis);
//	    	Magnetkarte result = magnetkarteAusgabe(json);
//	    	return result;
//	    	
//	    }
//	    

//	    public JSONObject statistikSpeichern(String spieler, int geld){
//	        // Building Parameters
//
//	        List<NameValuePair> params = new ArrayList<NameValuePair>();
//	        	params.add(new BasicNameValuePair("tag", statistikspeichern_tag));
//	        	params.add(new BasicNameValuePair("uid", spieler));
//	        	params.add(new BasicNameValuePair("geld", Integer.toString(geld)));
//	        	
//	          	Log.d("Statistik erfolgreich", "scheint bis hier zu gehen");
//
//	        JSONObject json = jsonParser.getJSONFromUrl(locationURL, params);
//	        // Rückgabe des json Objekts
//	        return json;
//	    }
	}
//
//	    /**
//	     * Hilfsklasse zum Konvertieren von Integer nach Boolean
//	     */
//		public boolean convertIntToBoolean(int intValue)	
//		{
//		return (intValue != 0);
//		}	
		
//	}