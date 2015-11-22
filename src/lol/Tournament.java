/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    ArrayList<Team> teamlist = new ArrayList<>();
    ArrayList<Poule> poulelist = new ArrayList<>();
    ArrayList<Match> matchlist = new ArrayList<>();
    ArrayList<Bracket> bracketlist = new ArrayList<>();
    DatabaseHandler db = new DatabaseHandler();
    ApiHandler api = new ApiHandler();
    
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
        db.resetPoules();
        db.resetBrackets();
        db.resetScores();
        
        this.poulelist = new ArrayList<>();
        this.bracketlist = new ArrayList<>(); //brackets are depending on poules
        
        ArrayList<Poule> poules = new ArrayList<>();
        
        Collections.shuffle(teamlist);
        
        System.out.println(teamlist);
        
        for (int i = 0; i < amountOfPoules; i++) {
            Poule poule = new Poule("Poule" + (i+1));
            poulelist.add(poule);
        }
        int index = 0;
        for (Team team : teamlist) {
            team.setPouleWins(0);
            poulelist.get(index).addTeam(team);
            if (index == amountOfPoules - 1) {
                index = 0;
            } else {
                index++;
            }
        }
        for (Poule poule : poulelist) {
            db.storePoule(poule);
        }
        
        generateBrackets(); //set up brackets already
    }
    
    public void generatePouleMatches() {
        db.resetMatches();
        for (Poule poule : poulelist) {
            for (Team team1 : poule.getTeams()) {
                for (Team team2 : poule.getTeams()) {
                    if (!team1.getName().equals(team2.getName())) {
                        //System.out.println(team1.toString() + " vs " + team2.toString());
                        Match match = new Match(team1.getName(), team2.getName(), poule.getName(), "");
                        matchlist.add(match);
                        db.storeMatch(match);
                    }
                }
            }
        }
    }
    
    public void generateBrackets() {
        for (int i = 0; i < poulelist.size(); i++) { //generate a bracket for each poule, this will be just right
            Bracket bracket = new Bracket("Bracket" + (i + 1), poulelist.size());
            bracketlist.add(bracket);
            db.storeBracket(bracket);
        }
    }
    
    public void forfaitPouleMatch(String matchID, String teamName) {
        Match matchPlayed = getMatchById(matchID);
        Poule poule = getPouleByMatch(matchPlayed);
        Team team1 = searchTeam(matchID.split("_")[1]);
        Team team2 = searchTeam(matchID.split("_")[2]);
        
        System.out.println(team1);
        System.out.println(teamName);
        
        matchPlayed.setCompleted("yes");
        db.setCompleted(matchPlayed);
        
        if (teamName.equals(team1.getName())) { // if team 1 forfaited add win by other team
            team2.addWin(); //once inside the tournament teamlist, once inside the poule teamlist. this should've been made better but hey, it works right?
            poule.addWin(team2);
            System.out.println("team " + team2.getName() + " wint");
            db.addWin(team2);
        } else { // no else if so we only need 1 known player for testing purposes
            team1.addWin();
            poule.addWin(team1);
            System.out.println("team " + team1.getName() + " wint");
            db.addWin(team1);
        }
        
        int flag = 0; // if this stays 0, all matches have been played
        if (matchPlayed.getType().startsWith("Poule")) {
            for (Match match : matchlist) {
                if (match.getType().equals(matchPlayed.getType())) { //if match is from the current poule
                    if(match.getCompleted().equals("no")) {
                        flag++;
                    }
                }
            }
            if (flag == 0) {
                poule.setCompleted("yes");
                completePoule(poule);
            }
        }
        
    }
    
    public void completeMatch(String matchID) {
        Match matchPlayed = getMatchById(matchID);
        Team team1 = searchTeam(matchPlayed.getTeam1());
        Team team2 = searchTeam(matchPlayed.getTeam2());
        String player1 = team1.getMembers().get(0);
        String player2 = team2.getMembers().get(0);
        String summID1 = api.getSummID(player1);
        System.out.println(summID1);
        Map<String,Map<String,String>> matchDump = api.getMatchSummary(summID1);
        
        System.out.println(matchDump.get(player1).get("winner"));
        
        //Step 0: set Match object completed flag
        matchPlayed.setCompleted("yes");
        db.setCompleted(matchPlayed);
        
        //Step1: determine the winner
        if (matchDump.get(player1).get("winner").equals("true")) {
            team1.addWin();
            db.addWin(team1);
        } else { // no else if so we only need 1 known player for testing purposes
            team2.addWin();
            db.addWin(team2);
        }
        
        //Step 2: Get Match data and update Statistics + store in DB
        //TODO
        
        //Step 3: Check if all matches in poule are played
        int flag = 0; // if this stays 0, all matches have been played
        if (matchPlayed.getType().startsWith("Poule")) {
            for (Match match : matchlist) {
                if (match.getType().equals(matchPlayed.getType())) { //if match is from the current poule
                    if(match.getCompleted().equals("no")) {
                        flag++;
                    }
                }
            }
            if (flag == 0) {
                Poule completedPoule = getPouleByMatch(matchPlayed);
                completedPoule.setCompleted("yes");
                completePoule(completedPoule);
            }
        }
        
    }
    
    public void completePoule(Poule poule) {
        Team team1 = poule.getSortedTeams().get(0); //select the first two of the poule, these teams made it to the knockout stage
        Team team2 = poule.getSortedTeams().get(1);
        int pouleNr = Integer.parseInt(poule.getName().substring(poule.getName().length() - 1)) - 1; // because we generated with i + 1
        bracketlist.get(pouleNr).setTeam1(team1);
        if ((pouleNr + 1) % 2 == 0) { //if poulenr is even, put team in bracket under it else bracket above. crossmatching
            bracketlist.get(pouleNr - 1).setTeam2(team2);
        } else {
            bracketlist.get(pouleNr + 1).setTeam2(team2);
        }
        
        System.out.println(bracketlist);
        
        for (Bracket bracket : bracketlist) { //check if any brackets have 2 team and zero matches ie. just filled and add first match to tournament
            if((bracket.getTeam1() != null && bracket.getTeam2() != null)) {
                if(bracket.getMatches().isEmpty()) {
                    Match match = new Match(bracket.getTeam1().getName(), bracket.getTeam2().getName(), bracket.getName(), "");
                    matchlist.add(match);
                    db.storeMatch(match);
                    bracket.addMatch(match.getMatchID());
                }
                db.updateBracket(bracket);
            }
        }
        
        
    }
    
    public Match getMatchById(String matchID) {
        for (Match match : matchlist) {
            if(match.getMatchID().equals(matchID)) {
                return match;
            }
        }
        return null;
    }
    
    public Poule getPouleByMatch(Match match) {
        for (Poule poule : poulelist) {
            if (match.getType().equals(poule.getName())) {
                return poule;
            }
        }
        return null;
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

    public ArrayList<Bracket> getBracketlist() {
        return bracketlist;
    }

    public void setPoulelist(ArrayList<Poule> poulelist) {
        this.poulelist = poulelist;
    }

    public void setBracketlist(ArrayList<Bracket> bracketlist) {
        this.bracketlist = bracketlist;
    }
    
    
    
    
    
    
    
    
}
