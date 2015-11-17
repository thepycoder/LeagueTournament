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
        
        //api.getMatch("2370414822");
        
        t.addTeam("team1", null, null, null);
        t.addTeam("team2", null, null, null);
        t.addTeam("team3", null, null, null);
        t.addTeam("team4", null, null, null);
        t.addTeam("team5", null, null, null);
        
        t.generatePoules(t.getTeamlist(), 2);
        for (Poule poule : t.getPoulelist()) {
            t.generatePouleMatches(poule);
        }
    }
}
