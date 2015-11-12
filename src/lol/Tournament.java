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
    
    ArrayList<Team> teamlist = new ArrayList<Team>();
    ArrayList<Poule> poulelist = new ArrayList<Poule>();
    ArrayList<Match> matchlist = new ArrayList<Match>();
    
    
    public Tournament() {
    }
    
    public void addTeam(Team team) {
        this.teamlist.add(team);
    }

    public void generatePoules(ArrayList<Team> teamlist) {
        ArrayList<Poule> poules = new ArrayList<Poule>();
        
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
