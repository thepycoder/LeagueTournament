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
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Temp
 */
public class ApiHandler {
    
    public int apiCount = 0;
    
    public ApiHandler() {
        
    }
    public JsonObject API(String arg, int call) { //Op basis van 1 player halen we de matchstatistieken op
        try {
            String sURL = "";
            switch(call){
                case 0: sURL = "https://euw.api.pvp.net/api/lol/euw/v1.3/game/by-summoner/" + arg + "/recent?api_key=efe95977-e5a3-4bef-875d-d3555438d6a5";
                break;
                case 1: sURL = "https://euw.api.pvp.net/api/lol/euw/v1.4/summoner/" + arg  + "/name?api_key=efe95977-e5a3-4bef-875d-d3555438d6a5";
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
    
    public ArrayList<String> getMembers(String player) {
        JsonObject rootobj = API(player, 0);
        ArrayList<String> members = new ArrayList<>();
        JsonArray fp = rootobj.getAsJsonArray("games").get(0).getAsJsonObject().get("fellowPlayers").getAsJsonArray();
        for (int i = 0; i < fp.size(); i++) {
            members.add(fp.get(i).getAsJsonObject().get("summonerId").getAsString());
        }
        //getSummNames(members);
        return members;
    }
    
    public ArrayList<String> getSummNames(ArrayList<String> ids) {
        String apiString = "";
        for (String id : ids) {
            apiString += id + ",";
        }
        
        JsonObject rootobj = API(apiString, 1);
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        System.out.println(gson.toJson(rootobj));
        
        
        return null;
    }
        
    
    public Map getStats(String summID) {
        JsonObject rootobj = API(summID, 0);
        Map stats = new HashMap();
        JsonObject fp = rootobj.getAsJsonArray("games").get(0).getAsJsonObject().get("stats").getAsJsonObject();
        for (Map.Entry<String,JsonElement> entry : fp.entrySet()) {
            stats.put(entry.getKey(), entry.getValue());
        }
        return stats;
    }
}
