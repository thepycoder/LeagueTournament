/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author Temp
 */
public class Main {
    public static void main(String[] args) {
        Tournament t = new Tournament();
        
        ApiHandler api = new ApiHandler();
        DatabaseHandler db = new DatabaseHandler();
        
//        ArrayList<String> mem = new ArrayList<String>();
//        mem.add("Huni");
//        mem.add("Rekkles");
//        mem.add("Lustboy");
//        mem.add("Reignover");
//        mem.add("Probelter");
        JsonObject json = api.getMatch("2370414822");
        JsonArray jarr = json.getAsJsonArray("participants");
        for (JsonElement elem : jarr) {
            System.out.println(elem);
        }
        
//        t.generatePoules(t.getTeamlist());
//        t.generatePouleMatches(t.getPoulelist().get(0));
    }
}
