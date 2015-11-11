/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    public static void main(String[] args) throws IOException {
        
        ArrayList<String> team1 = new ArrayList<String>();
        team1.add("Team1");
        team1.add("speler1_1");
        team1.add("speler1_2");
        team1.add("speler1_3");
        team1.add("speler1_4");
        team1.add("speler1_5");
        
        ArrayList<String> team2 = new ArrayList<String>();
        team1.add("Team2");
        team1.add("speler2_1");
        team1.add("speler2_2");
        team1.add("speler2_3");
        team1.add("speler2_4");
        team1.add("speler2_5");
        
        ArrayList<ArrayList<String>> teamlist = new ArrayList<ArrayList<String>>();
        teamlist.add(team1);
        teamlist.add(team2);
        
        ApiHandler api = new ApiHandler();
        DatabaseHandler db = new DatabaseHandler();
        
        Poule poule1 = new Poule();
        Poule poule2 = new Poule();
    }
    
    public ArrayList<Poule> generatePoules(ArrayList<ArrayList<String>> teamlist) {
        
    }
}
