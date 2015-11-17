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
    DatabaseHandler db = new DatabaseHandler();
    
    public Tournament() {
    }
    
    public void addTeam(String name, ArrayList<String> members, String region, String coach) {
        Team team = new Team(name, region, coach, members);
        teamlist.add(team);
        db.storeTeam(name, members, coach, region);
    }
    
    public void addMatch(Team team1, Team team2, String type, String official) {
        
    }

    public void generatePoules(ArrayList<Team> teamlist, int amountOfPoules) {
        ArrayList<Poule> poules = new ArrayList<Poule>();
        
        Collections.shuffle(teamlist);
        
        for (int i = 0; i < amountOfPoules; i++) {
            Poule poule = new Poule("Poule " + i);
            poulelist.add(poule);
        }
        int index = 0;
        for (Team team : teamlist) {
            team.setPoule(poulelist.get(index));
            if (index == amountOfPoules) {
                index = 0;
            } else {
                index++;
            }
        }
        System.out.println(teamlist);
    }
    
    public void generatePouleMatches(Poule poule) {
        for (Team team1 : poule.getTeams()) {
            for (Team team2 : poule.getTeams()) {
                if (team1 != team2) {
                    System.out.println(team1.toString() + team2.toString());
                    Match match = new Match(team1, team2, poule.getName(), null);
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
