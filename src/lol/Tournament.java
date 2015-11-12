/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import com.google.gson.JsonObject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    ArrayList<Team> teamlist = new ArrayList<Team>();
    
    
    public Tournament() {
    }
    
    public void addTeam(Team team) {
        this.teamlist.add(team);
    }

    public ArrayList<Poule> generatePoules(ArrayList<Team> teamlist) {
        ArrayList<Poule> poules = new ArrayList<Poule>();
        
        Collections.shuffle(teamlist);
        
        Poule poule1 = new Poule("Poule 1", teamlist.subList(0, 4));
        Poule poule2 = new Poule("Poule 2", teamlist.subList(4, 8));
        poules.add(poule1);
        poules.add(poule2);
        return poules;
    }

    public ArrayList<Team> getTeamlist() {
        return teamlist;
    }
    
    
}
