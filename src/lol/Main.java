/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Temp
 */
public class Main {
    public static void main(String[] args) {
        Tournament t = new Tournament();
        
        ApiHandler api = new ApiHandler(); //t wordt enkel voor tests meegegeven
        DatabaseHandler db = new DatabaseHandler(t);
        ReportHandler rh = new ReportHandler(t);
        
        //System.out.println(db.retrieveTeams());
        t.addTeams(db.retrieveTeams());
        t.addOfficials(db.retrieveOfficials());
        //t.completeMatch("Poule2_H2K_Koo Tigers");
//        HashMap<String, Map<String, String>> blub = api.getMatchSummary("Krepo");
//        System.out.println(blub);
//        for (Entry speler : blub.entrySet()) {
//            System.out.println(speler.getKey());
//        }
        //System.out.println(t.getMatchlist());
        //t.changeMatch(k.getTeam1(), k.getTeam2(), k.getTimeStamp(), "Victor");
        //System.out.println(t.getMatchlist());
        //System.out.println(t.getTeamlist());
        //t.removeTeam(t.getTeamlist().get(3).getName());
        //System.out.println(t.getTeamlist());
        //api.getMatchSummary(api.getSummID("Krepo"));
        //System.out.println(db.retrieveTeams());
        //db.removeTeam(db.retrieveTeams().get(0));
        // System.out.println(db.retrieveTeams());
        //db.resetMatches();
        //System.out.println(t.getMatchlist());
        if(db.retrievePoules().isEmpty()) {
            t.generatePoules(t.getTeamlist(), 2);
            t.generatePouleMatches();
        } else {
            t.setPoulelist(db.retrievePoules());
            t.addMatches(db.retrieveBracketMatches());
            t.addMatches(db.retrievePouleMatches());
            t.setBracketlist(db.retrieveBrackets());
        }
//
//
//        rh.generate();
        //System.out.println(db.getMatchDump("Poule2_H2K_Koo Tigers")); 
        //t.completeMatch("Poule2_H2K_Fnatic");
        //t.completePoule(t.getPoulelist().get(0));
        //t.completePoule(t.getPoulelist().get(1));
        //System.out.println(t.getBracketlist());
        //t.completeMatch("Poule2_test_H2K");
        //System.out.println(api.getMatchSummary("41710596").get("ClownEffect"));
        // GuiRemoveOfficial sk = new GuiRemoveOfficial(t);
        //sk.show();
        // System.out.println(t.getOfficials());
        GuiSilke sg = new GuiSilke(t);
        sg.show();
        //GuiSilke sg = new GuiSilke(t);
        //sg.show();
//        GuiChangeMatch k = new GuiChangeMatch(t);
//        k.show();
//        
        
        
        
    }
}
