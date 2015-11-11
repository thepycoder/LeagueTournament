/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;

/**
 *
 * @author Temp
 */
public class Main {
    public static void main(String[] args) {
        
        Tournament t = new Tournament();
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
        
        System.out.println(t.generatePoules(teamlist));
    }
}
