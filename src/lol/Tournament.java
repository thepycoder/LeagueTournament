/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    ArrayList<Team> teamlist = new ArrayList<>();
    ArrayList<Poule> poulelist;
    ArrayList<Match> matchlist = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler();
    
    public Tournament() {
    }
    
    public void changeTeam (String name, String region, String coach, ArrayList<String> members) {
        Team newTeam = new Team(name, region, coach, members);
        Team oldTeam = null;
        for (Team team : teamlist) {
            if (team.getName().equals(newTeam.getName())) {
                oldTeam = team;
            }
        }
        teamlist.remove(oldTeam);
        teamlist.add(newTeam);
        
    }
    
    public void removeTeam(String teamName) {
        for (Team team : teamlist) {
            if (team.getName().equals(teamName)) {
                teamlist.remove(team);
            }
        }
    }
    
    public Team searchTeam (String teamName) 
    {
        for (Team team : teamlist) 
        {
            if(team.getName().equals(teamName))
                return team;
        }
        return null;
    }
    
    
    public void addTeam(String name) {
        Team team = new Team(name);
        teamlist.add(team);
    }
    
    public void addTeam(String name, ArrayList<String> members, String region, String coach) {
        Team team = new Team(name, region, coach, members);
        teamlist.add(team);
        db.storeTeam(name, members, coach, region);
    }
    
    public void addTeams(ArrayList<Team> teams) {
        for (Team team : teams) {
            teamlist.add(team);
        }
    }
    
    public Match searchMatch(String matchToPlan) 
    {
        for (Match match : matchlist) 
        {
            if(match.getMatchID().equals(matchToPlan))
                return match;
        }
        return null;
    }
    
    public void addMatch(String matchToPlan, String timestamp, String official) 
    {
        System.out.println(timestamp);
        Match match = searchMatch(matchToPlan);
        match.setOfficial(official);
        match.setTimeStamp(timestamp);
        if ("null".equals(match.getTimeStamp())) { //if match is generated
            System.out.println("store");
            matchlist.add(match);
            db.storeMatch(match);
        } else {
            System.out.println("update");
            db.updateMatch(match);
        }
    }
    
    public void addMatches(ArrayList<Match> matches) {
        for (Match match : matches) {
            matchlist.add(match);
        }
    }
    
    public void resetMatches() {
        this.matchlist = new ArrayList<>();
        db.resetMatches();
    }

    public void generatePoules(ArrayList<Team> teamlist, int amountOfPoules) {
        this.poulelist = new ArrayList<>();
        ArrayList<Poule> poules = new ArrayList<Poule>();
        
        Collections.shuffle(teamlist);
        
        for (int i = 0; i < amountOfPoules; i++) {
            Poule poule = new Poule("Poule " + (i+1));
            poulelist.add(poule);
        }
        int index = 0;
        for (Team team : teamlist) {
            poulelist.get(index).addTeam(team);
            if (index == amountOfPoules - 1) {
                index = 0;
            } else {
                index++;
            }
        }
//        for (Poule poule : poulelist) {
//            db.storePoule(poule);
//        }
    }
    
    public void generatePouleMatches() {
        db.resetMatches();
        for (Poule poule : poulelist) {
            for (Team team1 : poule.getTeams()) {
                for (Team team2 : poule.getTeams()) {
                    if (team1 != team2) {
                        //System.out.println(team1.toString() + " vs " + team2.toString());
                        Match match = new Match(team1.getName(), team2.getName(), poule.getName(), "null");
                        matchlist.add(match);
                        db.storeMatch(match);
                    }
                }
            }
        }
        System.out.println(matchlist);
    }

    public ArrayList<Team> getTeamlist() {
        return teamlist;
    }

    public ArrayList<Poule> getPoulelist() {
        return poulelist;
    }

    public ArrayList<Match> getMatchlist() {
        return matchlist;
    }
    
    
    
    
    
    
}
