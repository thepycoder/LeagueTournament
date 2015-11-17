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
        
        t.addTeam("team1");
        t.addTeam("team2");
        t.addTeam("team3");
        t.addTeam("team4");
        t.addTeam("team5");
        t.addTeam("team5");
        t.addTeam("team6");
        t.addTeam("team7");
        t.addTeam("team8");
        t.addTeam("team9");
        t.addTeam("team10");
        t.addTeam("team11");
        t.addTeam("team12");
        
        t.generatePoules(t.getTeamlist(), 2);
        
        for (Poule poule : t.getPoulelist()) {
            t.generatePouleMatches(poule);
        }
        
        StartGui sg = new StartGui(t);
        sg.show();
    }
}
