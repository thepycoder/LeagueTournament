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
        
        ArrayList<Team> teamlist = new ArrayList<Team>();
        
        ApiHandler api = new ApiHandler();
        DatabaseHandler db = new DatabaseHandler();
    
        Team FNC = new Team ("FNC");
        Team C9 = new Team ("C9");
        Team ORI = new Team ("ORI");
        Team TSM = new Team ("TSM");
        Team CLG= new Team ("CLG");
        Team SKT = new Team ("SKT");
        Team EDG = new Team ("EDG");
        Team BKT= new Team ("BKT");
        
        teamlist.add(FNC);
        teamlist.add(C9);
        teamlist.add(ORI);
        teamlist.add(TSM);
        teamlist.add(CLG);
        teamlist.add(SKT);
        teamlist.add(EDG);
        teamlist.add(BKT);
        
    }
    
    public ArrayList<Poule> generatePoules(ArrayList<Team> teamlist) {
        ArrayList<Poule> poules = new ArrayList<Poule>();
        Poule poule1 = new Poule("Poule 1", (ArrayList<Team>) teamlist.subList(0, 3));
        Poule poule2 = new Poule("Poule 2", (ArrayList<Team>) teamlist.subList(4, 7));
        poules.add(poule1);
        poules.add(poule2);
        return poules;
    }
}
