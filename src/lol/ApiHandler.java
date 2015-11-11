/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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

/**
 *
 * @author Temp
 */
public class ApiHandler {
    
    public ApiHandler() {
        
    }
    public JsonObject getMatch(String MatchID) throws MalformedURLException, IOException {
        String sURL = "https://euw.api.pvp.net/api/lol/euw/v2.2/match/" + MatchID + "?api_key=efe95977-e5a3-4bef-875d-d3555438d6a5"; //just a string

        // Connect to the URL using java's native library
        URL url = new URL(sURL);
        HttpURLConnection request = (HttpURLConnection) url.openConnection();
        request.connect();

        // Convert to a JSON object to print data
        JsonParser jp = new JsonParser(); //from gson
        JsonElement root = jp.parse(new InputStreamReader((InputStream) request.getContent())); //Convert the input stream to a json element
        JsonObject rootobj = root.getAsJsonObject(); //May be an array, may be an object.
        
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(rootobj);
        System.out.println(json);
        return rootobj;
    }
}
