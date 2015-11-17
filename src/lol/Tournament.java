/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    ArrayList<Team> teamlist = new ArrayList<>();
    ArrayList<Poule> poulelist = new ArrayList<>();
    ArrayList<Match> matchlist = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler();
    
    public Tournament() {
    }
    
    public void addTeam(String name, ArrayList<String> members, String region, String coach) {
        Team team = new Team(name, region, coach, members);
        teamlist.add(team);
        db.storeTeam(name, members, coach, region);
    }

    public void generatePoules(ArrayList<Team> teamlist) {
        ArrayList<Poule> poules = new ArrayList<>();
        
        Collections.shuffle(teamlist);
        
        Poule poule1 = new Poule("Poule 1", teamlist.subList(0, 4));
        Poule poule2 = new Poule("Poule 2", teamlist.subList(4, 8));
        
        poulelist.add(poule1);
        poulelist.add(poule2);
    }
    
    public void generatePouleMatches(Poule poule) {
        for (Team team1 : poule.getTeams()) {
            for (Team team2 : poule.getTeams()) {
                if (team1 != team2) {
                    System.out.println(team1.toString() + team2.toString());
                    Match match = new Match(team1, team2, poule.getName());
                    matchlist.add(match);
                }
            }
        }
    }

    public ArrayList<Team> getTeamlist() {
        return teamlist;
    }

    public ArrayList<Poule> getPoulelist() {
        return poulelist;
    }
    
    
    
    
}
