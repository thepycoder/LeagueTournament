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
    
    public ApiHandler() {
        
    }
    public JsonObject getMatch(String MatchID) {
        try {
            String sURL = "https://euw.api.pvp.net/api/lol/euw/v2.2/match/" + MatchID + "?api_key=efe95977-e5a3-4bef-875d-d3555438d6a5"; //the api url + received key from riot games
            
            // Connect to the URL using java's native library
            URL url = new URL(sURL);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.connect();
            
            // Convert to a JSON object to print data
            JsonParser jp = new JsonParser(); //from gson
            JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //convert the input stream to a json element
            JsonObject rootobj = root.getAsJsonObject();
            //JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
            
            //System.out.println(rootobj.getAsJsonArray("participantIdentities").get(0)); //participantIdentities
            //System.out.println(rootobj.getAsJsonArray("participantIdentities").get(0).getAsJsonObject().get("participantId")); //participantID
            //System.out.println(rootobj.getAsJsonArray("participantIdentities").get(0).getAsJsonObject().get("player").getAsJsonObject().get("summonerName")); //summoner name
            
            for (String mem : getMembers(rootobj)) {
                Map stats = getStats(rootobj, 0);
                System.out.println(mem + "\n" + stats + "\n");
            }
            
            //Gson gson = new GsonBuilder().setPrettyPrinting().create();
            //String json = gson.toJson(rootobj);
            //System.out.println(json);
            
            return rootobj;
        } catch (MalformedURLException ex) {
            System.out.println("Url doesn't work: " + ex);
            return null;
        } catch (IOException ex) {
            System.out.println("Some IO error occured: " + ex);
            return null;
        }
    }
    
    public ArrayList<String> getMembers(JsonObject rootobj) {
        ArrayList<String> members = new ArrayList<>();
        JsonArray pi = rootobj.getAsJsonArray("participantIdentities");
        for (int i = 0; i < pi.size(); i++) {
            members.add(pi.get(i).getAsJsonObject().get("player").getAsJsonObject().get("summonerName").getAsString());
        }
        //System.out.println(members);
        return members;
    }
    
    public Map getStats(JsonObject rootobj, int pn) {
        Map stats = new HashMap();
        JsonArray pi = rootobj.getAsJsonArray("participants");
        JsonObject po = (JsonObject) pi.get(pn).getAsJsonObject().get("stats");
        for (Map.Entry<String,JsonElement> entry : po.entrySet()) {
            stats.put(entry.getKey(), entry.getValue());
        }
        return stats;
    }
}
