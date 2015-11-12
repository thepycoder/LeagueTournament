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
        
        t.addTeam(FNC);
        t.addTeam(C9);
        t.addTeam(ORI);
        t.addTeam(TSM);
        t.addTeam(CLG);
        t.addTeam(SKT);
        t.addTeam(EDG);
        t.addTeam(BKT);
        
        System.out.println(t.generatePoules(t.getTeamlist()));
    }
}
