/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
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
    ApiHandler api = new ApiHandler(this);
    
    public Tournament() {
    }
    
    public void changeTeam (String name, String region, String coach, ArrayList<Player> members) {
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
    
    public void changeMatch(String team1, String team2,String timeStamp, String official){
        Match oldMatch = null;
        for(Match k: matchlist){
            if(k.getTeam1().equals(team1) && k.getTeam2().equals(team2)){
            oldMatch = k;
                    }
        }
        Match newMatch = new Match(oldMatch.getMatchID(),team1, team2, timeStamp, oldMatch.getType(), official, oldMatch.getCompleted());
        newMatch.setType(oldMatch.getType());
        matchlist.remove(oldMatch);
        matchlist.add(newMatch);
        
    }
    
    public void removeTeam(String teamName) {
        Team wrongTeam = null;
        for (Team team : teamlist) {
            if (team.getName().equals(teamName)) {
                wrongTeam = team;
            }
        }
        teamlist.remove(wrongTeam);
        for(Poule p: poulelist){
            if(p.getTeams().contains(wrongTeam)){
                p.getTeams().remove(wrongTeam);
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
    
    public void addTeam(String name, ArrayList<Player> members, String region, String coach) {
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
        Bracket semi1 = new Bracket("Bracket" + (bracketlist.size() + 1), 2);
        bracketlist.add(semi1);
        db.storeBracket(semi1);
        Bracket semi2 = new Bracket("Bracket" + (bracketlist.size() + 1), 2);
        bracketlist.add(semi2);
        db.storeBracket(semi2);
        
        Bracket fin = new Bracket("Bracket" + (bracketlist.size() + 1), 1);
        bracketlist.add(fin);
        db.storeBracket(fin);
        
        System.out.println("Bracketlijst: " + bracketlist);
    }
    
    public void forfeitMatch(String matchID, String teamName) {
        Match matchPlayed = getMatchById(matchID);
        String[] ID = matchID.split("_");
        Team team1 = searchTeam(ID[ID.length - 1]);
        Team team2 = searchTeam(ID[ID.length - 2]);
        
        System.out.println(team1);
        System.out.println(team2);
        
        matchPlayed.setCompleted("yes");
        db.setCompleted(matchPlayed);
        
        //generate statistics piece
        
        if (matchPlayed.getType().startsWith("Poule")) {
            
            Poule poule = getPouleByMatch(matchPlayed);
            
            if (teamName.equals(team1.getName())) { // if team 1 forfaited add win by other team
                //team2.addWin(); //once inside the tournament teamlist, once inside the poule teamlist. this should've been made better but hey, it works right?
                poule.addWin(team2);
                System.out.println("team " + team2.getName() + " wint");
                db.addPouleWin(team2);
            } else {
                //team1.addWin();
                poule.addWin(team1);
                System.out.println("team " + team1.getName() + " wint");
                db.addPouleWin(team1);
            }
            
            int flag = 0; // if this stays 0, all matches have been played
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
        } else if(matchPlayed.getType().startsWith("Bracket")) {
            
            Team winner = null;
            Bracket bracket = getBracketByMatch(matchPlayed);
            bracket.addMatch(matchPlayed.getMatchID());
            db.updateBracket(bracket);
            
            //step 1: add win to right team
            if (teamName.equals(team1.getName())) { //team 1 forfeited
                winner = team2;
                bracket.addWinTeam1(); //sucks but apparently team1 of the GUI and team1 of the bracket aren't the same
                db.addBracketWin(bracket, 2);
            } else {
                winner = team1;
                bracket.addWinTeam2();
                db.addBracketWin(bracket, 1);
            }
            
            //step 2: check if last match from bracket
            
            if (bracket.getTeam1score() == 3 || bracket.getTeam2score() == 3) { //dan is de bracket gedaan
                bracket.setCompleted("yes");
                String bracketNr = bracket.getName().substring(bracket.getName().length() - 1);
                
                Bracket sem1 = bracketlist.get(4);
                Bracket sem2 = bracketlist.get(5);
                
                if (bracket.getType() == 4) { //quarterfinal
                    switch (bracketNr) {
                        case "1":
                            sem1.setTeam1(winner);
                            break;
                        case "2":
                            sem1.setTeam2(winner);
                            break;
                        case "3":
                            sem2.setTeam1(winner);
                            break;
                        case "4":
                            sem2.setTeam2(winner);
                            break;
                    }
                    
                    if ((sem1.getTeam1() != null && sem1.getTeam2() != null) && sem1.getMatches().isEmpty()) {
                        addBracketMatch(sem1);
                    }
                    if ((sem2.getTeam1() != null && sem2.getTeam2() != null) && sem2.getMatches().isEmpty()) {
                        addBracketMatch(sem2);
                    }
                    
                } else if (bracket.getType() == 2) { //semifinal
                    //check if final bracket already exists
                    Bracket fin = getFinal();
                    switch (bracketNr) {
                        case "5":
                            // if it is the upper bracket a.k.a 5th bracket
                            fin.setTeam1(winner);
                            break;
                        case "6":
                            fin.setTeam2(winner);
                            break;
                    }
                    if (fin.getTeam1() != null && fin.getTeam2() != null){
                        addBracketMatch(fin); //ony add match when both teams are set
                    }
                    
                } else if (bracket.getType() == 1) { //final
                    System.out.println("feest tis gedaan");
                }
                
            } else {
                addBracketMatch(bracket);
            }
            
        }
        
    }
    
    public void addBracketMatch(Bracket bracket) {
        String MatchNr = "-1";
        if (bracket.getMatches().size() > 0) {
            MatchNr = bracket.getMatches().get(bracket.getMatches().size() - 1).split("_")[1]; //get the number of the latest played match
        }
        Match match = new Match(bracket.getTeam1().getName(), bracket.getTeam2().getName(), bracket.getName().concat("_" + (Integer.parseInt(MatchNr) + 1)), "");
        matchlist.add(match);
        db.storeMatch(match);
        bracket.addMatch(match.getMatchID()); 
    }
    
    public void completeMatch(String matchID) { //I know this could've been more elegant than just copy the forfeit method and change some vars, but I like sleep
        Match matchPlayed = getMatchById(matchID);
        String[] ID = matchID.split("_");
        Team team1 = searchTeam(ID[ID.length - 1]);
        Team team2 = searchTeam(ID[ID.length - 2]);
        String team1mem = team1.getMembers().get(0).getName();
        HashMap<String,Map<String,String>> matchDump = api.getMatchSummary(team1mem);
        
        matchPlayed.setCompleted("yes");
        db.setCompleted(matchPlayed);
        
        //generate statistics piece
        for (Player player : team1.getMembers()) {
            
        }
        
        
        if (matchPlayed.getType().startsWith("Poule")) {
            
            Poule poule = getPouleByMatch(matchPlayed);
            
            if (!matchDump.get(team1mem).get("winner").equals("true")) { // if team 1 lost add win by other team
                //team2.addWin(); //once inside the tournament teamlist, once inside the poule teamlist. this should've been made better but hey, it works right?
                poule.addWin(team2);
                System.out.println("team " + team2.getName() + " wint");
                db.addPouleWin(team2);
            } else {
                //team1.addWin();
                poule.addWin(team1);
                System.out.println("team " + team1.getName() + " wint");
                db.addPouleWin(team1);
            }
            
            int flag = 0; // if this stays 0, all matches have been played
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
        } else if(matchPlayed.getType().startsWith("Bracket")) {
            
            Team winner = null;
            Bracket bracket = getBracketByMatch(matchPlayed);
            bracket.addMatch(matchPlayed.getMatchID());
            db.updateBracket(bracket);
            
            //step 1: add win to right team
            if (!matchDump.get(team1mem).get("winner").equals("true")) { //team 1 lost
                winner = team2;
                bracket.addWinTeam1(); //sucks but apparently team1 of the GUI and team1 of the bracket aren't the same
                db.addBracketWin(bracket, 2);
            } else {
                winner = team1;
                bracket.addWinTeam2();
                db.addBracketWin(bracket, 1);
            }
            
            //step 2: check if last match from bracket
            
            if (bracket.getTeam1score() == 3 || bracket.getTeam2score() == 3) { //dan is de bracket gedaan
                bracket.setCompleted("yes");
                String bracketNr = bracket.getName().substring(bracket.getName().length() - 1);
                
                Bracket sem1 = bracketlist.get(4);
                Bracket sem2 = bracketlist.get(5);
                
                if (bracket.getType() == 4) { //quarterfinal
                    switch (bracketNr) {
                        case "1":
                            sem1.setTeam1(winner);
                            break;
                        case "2":
                            sem1.setTeam2(winner);
                            break;
                        case "3":
                            sem2.setTeam1(winner);
                            break;
                        case "4":
                            sem2.setTeam2(winner);
                            break;
                    }
                    
                    if ((sem1.getTeam1() != null && sem1.getTeam2() != null) && sem1.getMatches().isEmpty()) {
                        addBracketMatch(sem1);
                    }
                    if ((sem2.getTeam1() != null && sem2.getTeam2() != null) && sem2.getMatches().isEmpty()) {
                        addBracketMatch(sem2);
                    }
                    
                } else if (bracket.getType() == 2) { //semifinal
                    //check if final bracket already exists
                    Bracket fin = getFinal();
                    switch (bracketNr) {
                        case "5":
                            // if it is the upper bracket a.k.a 5th bracket
                            fin.setTeam1(winner);
                            break;
                        case "6":
                            fin.setTeam2(winner);
                            break;
                    }
                    if (fin.getTeam1() != null && fin.getTeam2() != null){
                        addBracketMatch(fin); //ony add match when both teams are set
                    }
                    
                } else if (bracket.getType() == 1) { //final
                    System.out.println("feest tis gedaan");
                }
                
            } else {
                addBracketMatch(bracket);
            }
            
        }
        
    }
    
    public void completeBracket(Bracket bracket) {
        Team winningTeam = null;
        
        if(bracket.getTeam1score() > bracket.getTeam2score()) {
            winningTeam = bracket.getTeam1();
        } else {
            winningTeam = bracket.getTeam2();
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
        
        //System.out.println(bracketlist);
        
        for (Bracket bracket : bracketlist) { //check if any brackets have 2 team and zero matches ie. just filled and add first match to tournament
            if((bracket.getTeam1() != null && bracket.getTeam2() != null)) {
                if(bracket.getMatches().isEmpty()) {
                    Match match = new Match(bracket.getTeam1().getName(), bracket.getTeam2().getName(), bracket.getName().concat("_" + bracket.getMatches().size()), "");
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
    
    public Bracket getBracketByMatch(Match match) {
        for (Bracket bracket : bracketlist) {
            if (match.getType().split("_")[0].equals(bracket.getName())) {
                return bracket;
            }
        }
        return null;
    }
    
    public ArrayList<Bracket> getSemi() {
        ArrayList<Bracket> s = new ArrayList<>();
        for (Bracket bracket : bracketlist) {
            if (bracket.getType() == 2) {
                s.add(bracket);
            }
        }
        Collections.sort(s);
        return s;
    }
    
    public Bracket getFinal() {
        Bracket fin = null;
        for (Bracket bracket : bracketlist) {
            if (bracket.getType() == 1) {
                fin = bracket;
            }
        }
        System.out.println("finale: " + fin);
        return fin;
    }
    
    public Player getPlayer(String name) {
        for (Team team : teamlist) {
            for (Player player : team.getMembers()) {
                if (player.getName().equals(name)) {
                    return player;
                }
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
        Collections.sort(teamlist);
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
