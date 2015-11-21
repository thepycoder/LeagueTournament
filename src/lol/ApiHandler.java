/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Temp
 */
public class ApiHandler {
    
    public int apiCount = 0;
    public String ApiKey = "efe95977-e5a3-4bef-875d-d3555438d6a5";
    public String matchID = "";
    
    public ApiHandler() {
        
    }
    public JsonObject API(String arg, int call) { //Op basis van 1 player halen we de matchstatistieken op
        try {
            String sURL = "";
            switch(call){
                case 0: sURL = "https://euw.api.pvp.net/api/lol/euw/v1.3/game/by-summoner/" + arg + "/recent?api_key=" + ApiKey;
                break;
                case 1: sURL = "https://euw.api.pvp.net/api/lol/euw/v1.4/summoner/" + arg  + "/name?api_key=" + ApiKey;
                break;
                case 2: sURL = "https://euw.api.pvp.net/api/lol/euw/v2.2/match/" + arg  + "?api_key=" + ApiKey;
                break;
            }
            apiCount++;
            System.out.println(apiCount);
            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject();
            //JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            
            //System.out.println(rootobj.getAsJsonArray("games").get(3)); //participantIdentities
            //System.out.println(rootobj.getAsJsonArray("participantIdentities").get(0).getAsJsonObject().get("participantId")); //participantID
            //System.out.println(rootobj.getAsJsonArray("participantIdentities").get(0).getAsJsonObject().get("player").getAsJsonObject().get("summonerName")); //summoner name
            //System.out.println(getMembers(rootobj));
            
//            for (String mem : getMembers(rootobj)) {
//                Map stats = getStats(rootobj, 0);
//                System.out.println(mem + "\n" + stats + "\n");
//            }
//            
//            Gson gson = new GsonBuilder().setPrettyPrinting().create();
//            System.out.println(gson.toJson(rootobj));
            
            return rootobj;
        } catch (MalformedURLException ex) {
            System.out.println("Url doesn't work: " + ex);
            return null;
        } catch (IOException ex) {
            System.out.println("Some IO error occured: " + ex);
            return null;
        }
    }
    
    public Map<String, Map<String, String>> getMatchSummary(String player) {
        Map<String, Map<String, String>> summary = new HashMap<>();
        Map<String, String> champSummID = getMembers(player);
        Map<String, String> champSummName = getSummNames(champSummID);
        
        JsonObject rootobj = API(matchID, 2); //by now the requested matchID is in the variable
        
        JsonArray participants = rootobj.getAsJsonArray("participants");
        
        for (JsonElement participant : participants) {
            JsonObject p = (JsonObject) participant;
            
            for (Map.Entry<String,String> entry : champSummName.entrySet()) {
                if(p.get("championId").getAsString().equals(entry.getKey())) {
                    
                    Map stats = new HashMap();
                    for (Map.Entry<String,JsonElement> stat : p.get("stats").getAsJsonObject().entrySet()) {
                        stats.put(stat.getKey(), stat.getValue());
                    }
                    
                    summary.put(entry.getValue(), stats);
                    
                }
            }
                    
        }
        
        return summary;
    }
    
    
    public Map<String, String> getMembers(String player) {
        JsonObject rootobj = API(player, 0);
        //ArrayList<String> result = new ArrayList<>();
        Map<String, String> champSummID = new HashMap<>();
        champSummID.put(rootobj.getAsJsonArray("games").get(0).getAsJsonObject().get("championId").getAsString(), player);
        matchID = rootobj.getAsJsonArray("games").get(0).getAsJsonObject().get("gameId").getAsString();
        //Map<String,Map<String,String>> result = new LinkedHashMap<String,Map<String,String>>();
        JsonArray fp = rootobj.getAsJsonArray("games").get(0).getAsJsonObject().get("fellowPlayers").getAsJsonArray();
        for (int i = 0; i < fp.size(); i++) {
            String summID = fp.get(i).getAsJsonObject().get("summonerId").getAsString();
            String champID = fp.get(i).getAsJsonObject().get("championId").getAsString();
            champSummID.put(champID, summID);
            //result.put(summID, getStats(summID));
        }
        //getSummNames(members);
        return champSummID;
    }
    
    public Map<String, String> getSummNames(Map<String, String> ids) {
        String apiString = "";
        Map<String, String> champSummNames = new HashMap<>();
        for(Entry<String, String> id : ids.entrySet()) {
            apiString += id.getValue() + ",";
        }
        
        JsonObject rootobj = API(apiString, 1);
        
        for(Entry<String, String> id : ids.entrySet()) {
            champSummNames.put(id.getKey(), rootobj.get(id.getValue()).getAsString());
        }
        
        
        return champSummNames;
    }
}
