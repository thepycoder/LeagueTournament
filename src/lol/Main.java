/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;
/**
 *
 * @author Temp
 */
public class Main {
    public static void main(String[] args) {
        Tournament t = new Tournament();
        
        ApiHandler api = new ApiHandler(); //t wordt enkel voor tests meegegeven
        DatabaseHandler db = new DatabaseHandler(t);
        
        t.addTeams(db.retrieveTeams());
        System.out.println(t.getTeamlist());
        //api.getMatchSummary(api.getSummID("Krepo"));

        
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
            t.addMatches(db.retrieveMatches());
            t.setBracketlist(db.retrieveBrackets());
        }
        //t.completeMatch("Poule2_H2K_Fnatic");
        
        //t.completePoule(t.getPoulelist().get(0));
        //t.completePoule(t.getPoulelist().get(1));
        
        //System.out.println(t.getBracketlist());
        
        //t.completeMatch("Poule2_test_H2K");
        //System.out.println(api.getMatchSummary("41710596").get("ClownEffect"));
        
//       GuiSilke sg = new GuiSilke(t);
//       sg.show();
        GuiChangeMatch k = new GuiChangeMatch(t);
        k.show();
        
        
    }
}
