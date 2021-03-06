/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    //private static String matchID = "2370414822"; //als voorbeeld
    
    public ArrayList<Team> teamlist = new ArrayList<>();
    public ArrayList<Poule> poulelist = new ArrayList<>();
    public ArrayList<Match> matchlist = new ArrayList<>();
    public ArrayList<Bracket> bracketlist = new ArrayList<>();
    public ArrayList<String> officials = new ArrayList<>();
    public DatabaseHandler db = new DatabaseHandler(this);
    public ApiHandler api = new ApiHandler();
    
    
    
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
        db.updateTeam(newTeam);
    }
    
    public void changeMatch(String team1, String team2,String timeStamp, String official){
        Match oldMatch = null;
        for(Match k: matchlist){
            if(k.getTeam1().equals(team1) && k.getTeam2().equals(team2)){
            oldMatch = k;
                    }
        }
        Match newMatch = new Match(oldMatch.getMatchID(),team1, team2, oldMatch.getWinner(), timeStamp, oldMatch.getType(), official, oldMatch.getCompleted(), oldMatch.getTieBreaker());
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
        for(Poule p: poulelist){
            if(p.getTeams().contains(wrongTeam)){
                p.getTeams().remove(wrongTeam);
            }
        }
        ArrayList<Player> players = wrongTeam.getMembers();        
        teamlist.remove(wrongTeam);        
        this.resetMatches();
        db.removeTeam(wrongTeam,players);
        this.generatePoules(teamlist, poulelist.size());
        this.generatePouleMatches();
        
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
        db.storeTeam(name, null, name, name);
       
    }
    public void addOfficial(String name) {
        officials.add(name);
        db.storeOfficial(name);
    }
    
    public void removeOfficial(String name){
       officials.remove(name);
       db.removeOfficial(name);
        
    }
    public void addOfficials(ArrayList<String> officialz){
        for(String k : officialz){
            officials.add(k);
        }
    }
    public void addTeam(String name, ArrayList<Player> members, String region, String coach) {
        Team team = new Team(name, region, coach, members);
        teamlist.add(team);       
        
        for (Player member : members) {
            db.storePlayer(member);
        }       
        db.storeTeam(name, members, coach, region);
        this.resetMatches();
        this.generatePoules(teamlist,poulelist.size());
        this.generatePouleMatches();
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
        db.resetMatches();
        db.resetPoules();
        db.resetBrackets();
        db.resetScores();
       
        
        for (Team team : teamlist)
        { 
            team.setBarons(0);
            team.setDragons(0);
            team.setGold(0);            
            for (Player player : team.getMembers()) {
                
                player.setCS_ratio(0);
                player.setKDA_ratio(0);
                player.setKill_part(0);
            }
        }
        
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
        for (int i = 0; i < 4; i++) { //generate a bracket for each poule
            Bracket bracket = new Bracket("Bracket" + (i + 1), 4);
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
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date date = new Date();
        matchPlayed.setTimeStamp(dateFormat.format(date));
        db.setCompleted(matchPlayed, dateFormat.format(date));

        
        if (matchPlayed.getType().startsWith("Poule")) {
            
            if (teamName.equals(team1.getName())) { // if team 1 forfaited add win by other team
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team2.addTieWin();
                    team1.addTieLoss();
                    matchPlayed.setWinner(team2.getName());
                    db.addTieBreakerWin(team2);
                    db.addTieBreakerLoss(team1);
                    
                } else {
                    team2.addWin();
                    team1.addLoss();
                    matchPlayed.setWinner(team2.getName());
                    //poule.addWin(team2);
                    //poule.addLoss(team1);
                    System.out.println("team " + team2.getName() + " wint");
                    db.addPouleWin(team2);
                    db.addPouleLoss(team1);
                }
            } else {
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team1.addTieWin();
                    team2.addTieLoss();
                    matchPlayed.setWinner(team1.getName());
                    db.addTieBreakerWin(team1);
                    db.addTieBreakerLoss(team2);
                } else {
                    team1.addWin();
                    team2.addLoss();
                    matchPlayed.setWinner(team1.getName());
                    //poule.addWin(team1);
                    //poule.addLoss(team2);
                    System.out.println("team " + team1.getName() + " wint");
                    db.addPouleWin(team1);
                    db.addPouleLoss(team2);
                }
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
                Poule poule = getPouleByMatch(matchPlayed);
                if (checkTies(poule)) { //if false, new macthes will have been added to resolve the tie
                    poule.setCompleted("yes");
                    completePoule(poule);
                }
            }
        } else if(matchPlayed.getType().startsWith("Bracket")) {
            
            Team winner = null;
            Bracket bracket = getBracketByMatch(matchPlayed);
            bracket.addMatch(matchPlayed.getMatchID());
            
            //step 1: add win to right team
            if (teamName.equals(team1.getName())) { //team 1 forfeited
                winner = team2;
                matchPlayed.setWinner(winner.getName());
                bracket.addWinTeam1(); 
                //bracket.addLossTeam2();
                //db.addBracketWin(bracket, 2);
                //db.addBracketLoss(bracket, 1);
            } else {
                winner = team1;
                matchPlayed.setWinner(winner.getName());
                bracket.addWinTeam2();
                //bracket.addLossTeam1();
                //db.addBracketWin(bracket, 1);
                //db.addBracketLoss(bracket, 2);
            }
            
            db.updateBracket(bracket);
            
            //step 2: check if last match from bracket
            
            if (bracket.getTeam1score() == 3 || bracket.getTeam2score() == 3) { //dan is de bracket gedaan
                bracket.setCompleted("yes");
                String bracketNr = bracket.getName().substring(bracket.getName().length() - 1); //in de database staat deze appart
                
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
                    db.updateBracket(sem1);
                    db.updateBracket(sem2);
                    
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
                    db.updateBracket(fin);
                    
                } else if (bracket.getType() == 1) { //final
                    System.out.println("feest tis gedaan");
                }
                
            } else {
                addBracketMatch(bracket);
            }
            
        }
        
        db.updateMatch(matchPlayed); // add winners to the database
        
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
    
    public void manualCompleteMatch(String matchID, String winnerr) { 
        Match matchPlayed = getMatchById(matchID);
        String[] ID = matchID.split("_");
        Team team1 = searchTeam(ID[ID.length - 2]);
        Team team2 = searchTeam(ID[ID.length - 1]);
        String team1mem = team1.getMembers().get(0).getName();
        HashMap<String,Map<String,String>> matchDump = api.getMatchSummary(team1mem);
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date date = new Date();
        
        //this part is for testing puposes. It sets the names of the members to the ones in the database.
        
        HashMap<String,Map<String,String>> newMatchDump = new HashMap<>();
        
        int index1 = 0;
        int index2 = 0;
        Team blue;
        Team purple;
        
        
        System.out.println(team1mem + ": " + matchDump);
        if (matchDump.get(team1mem).get("team").equals("1")) {
            blue = team1;
            purple = team2;
        } else {
            blue = team2;
            purple = team1;
        }
        
        for (Entry<String, Map<String, String>> entry : matchDump.entrySet()) {
            if (entry.getValue().get("team").equals("1")) {
                newMatchDump.put(blue.getMembers().get(index1).getName(), entry.getValue());
                index1++;
            } else if (entry.getValue().get("team").equals("2")) {
                newMatchDump.put(purple.getMembers().get(index2).getName(), entry.getValue());
                index2++;
            }
        }
        matchDump = newMatchDump;
        //end testing part
        
        matchPlayed.setCompleted("yes");
        matchPlayed.setTimeStamp(dateFormat.format(date));
        db.setCompleted(matchPlayed, dateFormat.format(date));
        
        //Statistics part
        updateStats(matchDump, team1, team2, matchPlayed);
        
        if (matchPlayed.getType().startsWith("Poule")) {
            
            if (matchPlayed.getTeam2().equals(winnerr)) { // if team 1 lost add win by other team
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team2.addTieWin();
                    team1.addTieLoss();
                    matchPlayed.setWinner(team2.getName());
                    db.addTieBreakerWin(team2);
                    db.addTieBreakerLoss(team1);
                    
                } else {
                    team2.addWin();
                    team1.addLoss();
                    matchPlayed.setWinner(team2.getName());
                    //poule.addWin(team2);
                    //poule.addLoss(team1);
                    System.out.println("team " + team2.getName() + " wint");
                    db.addPouleWin(team2);
                    db.addPouleLoss(team1);
                }
            } else {
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team1.addTieWin();
                    team2.addTieLoss();
                    matchPlayed.setWinner(team1.getName());
                    db.addTieBreakerWin(team1);
                    db.addTieBreakerLoss(team2);
                } else {
                    team1.addWin();
                    team2.addLoss();
                    matchPlayed.setWinner(team1.getName());
                    //poule.addWin(team1);
                    //poule.addLoss(team2);
                    System.out.println("team " + team1.getName() + " wint");
                    db.addPouleWin(team1);
                    db.addPouleLoss(team2);
                }
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
                Poule poule = getPouleByMatch(matchPlayed);
                if (checkTies(poule)) { //if false, new macthes will have been added to resolve the tie
                    poule.setCompleted("yes");
                    completePoule(poule);
                }
            }
        } else if(matchPlayed.getType().startsWith("Bracket")) {
            
            Team winner = null;
            Bracket bracket = getBracketByMatch(matchPlayed);
            bracket.addMatch(matchPlayed.getMatchID());
            db.updateBracket(bracket);
            
            //step 1: add win to right team
            if (matchPlayed.getTeam2().equals(winnerr)) { //team 1 lost
                winner = team2;
                matchPlayed.setWinner(winner.getName());
                bracket.addWinTeam1(); 
                bracket.addLossTeam2();
                //db.addBracketWin(bracket, 2);
                //db.addBracketLoss(bracket, 1);
            } else {
                winner = team1;
                matchPlayed.setWinner(winner.getName());
                bracket.addWinTeam2();
                bracket.addLossTeam1();
                //db.addBracketWin(bracket, 1);
                //db.addBracketLoss(bracket, 2);
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
        db.updateMatch(matchPlayed); // add winners to the database
    }
    
    
    public void completeMatch(String matchID) { 
        Match matchPlayed = getMatchById(matchID);
        String[] ID = matchID.split("_");
        Team team1 = searchTeam(ID[ID.length - 2]);
        Team team2 = searchTeam(ID[ID.length - 1]);
        String team1mem = team1.getMembers().get(0).getName();
        HashMap<String,Map<String,String>> matchDump = api.getMatchSummary(team1mem);
        DateFormat dateFormat = new SimpleDateFormat("yyyy MM dd");
        Date date = new Date();
        
        //this part is for testing puposes. It sets the names of the members to the ones in the database.
        
        HashMap<String,Map<String,String>> newMatchDump = new HashMap<>();
        
        int index1 = 0;
        int index2 = 0;
        ArrayList<Player> blue;
        ArrayList<Player> purple;
        
        System.out.println(team1mem + ": " + matchDump);
        if (matchDump.get(team1mem).get("team").equals("1")) {
            blue = team1.getMembers();
            purple = team2.getMembers();
        } else {
            blue = team2.getMembers();
            purple = team1.getMembers();
        }
        
        for (Entry<String, Map<String, String>> entry : matchDump.entrySet()) {
            if (entry.getValue().get("team").equals("1")) {
                newMatchDump.put(blue.get(index1).getName(), entry.getValue());
                index1++;
            } else if (entry.getValue().get("team").equals("2")) {
                newMatchDump.put(purple.get(index2).getName(), entry.getValue());
                index2++;
            }
        }
        matchDump = newMatchDump;
        //end testing part
        
        //Statistics part
        updateStats(matchDump, team1, team2, matchPlayed);
        
        if (matchPlayed.getType().startsWith("Poule")) {
            
            if (!matchDump.get(team1mem).get("winner").equals("true")) { // if team 1 lost add win by other team
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team2.addTieWin();
                    team1.addTieLoss();
                    matchPlayed.setWinner(team2.getName());
                    db.addTieBreakerWin(team2);
                    db.addTieBreakerLoss(team1);
                } else {
                    team2.addWin(); 
                    team1.addLoss();
                    matchPlayed.setWinner(team2.getName());
                    //poule.addWin(team2);
                    //poule.addLosst(team1);
                    //System.out.println("team " + team2.getName() + " wint");
                    //System.out.println("team " + team1.getName() + " verliest");
                    db.addPouleWin(team2);
                    db.addPouleLoss(team1);
                }
            } else {
                if (matchID.split("_")[1].equals("TB")) { // check if match is tiebreaker, using different scoring systems then
                    team1.addTieWin();
                    team2.addTieLoss();
                    matchPlayed.setWinner(team1.getName());
                    db.addTieBreakerWin(team1);
                    db.addTieBreakerLoss(team2);
                } else {
                    team1.addWin();
                    team2.addLoss();
                    matchPlayed.setWinner(team1.getName());
                    //poule.addWin(team1);
                    //poule.addLoss(team2);
                    //System.out.println("team " + team1.getName() + " wint");
                    db.addPouleWin(team1);
                    db.addPouleLoss(team2);
                }
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
                Poule poule = getPouleByMatch(matchPlayed);
                if (checkTies(poule)) { //if false, new macthes will have been added to resolve the tie
                    poule.setCompleted("yes");
                    completePoule(poule);
                }
            }
        } else if(matchPlayed.getType().startsWith("Bracket")) {
            
            Team winner = null;
            Bracket bracket = getBracketByMatch(matchPlayed);
            bracket.addMatch(matchPlayed.getMatchID());
            db.updateBracket(bracket);
            
            //step 1: add win to right team
            if (!matchDump.get(team1mem).get("winner").equals("true")) { //team 1 lost
                winner = team2;
                bracket.addWinTeam1();
                bracket.addLossTeam2();
                matchPlayed.setWinner(winner.getName());
                db.addBracketWin(bracket, 2);
                //db.addBracketLoss(bracket, 1);
            } else {
                winner = team1;
                bracket.addWinTeam2();
                bracket.addLossTeam1();
                matchPlayed.setWinner(winner.getName());
                db.addBracketWin(bracket, 1);
                //db.addBracketLoss(bracket, 2);
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
        db.updateMatch(matchPlayed);
    }
    
    public void updateStats(HashMap<String,Map<String,String>> matchDump, Team team1, Team team2, Match match) {
        String teststat = "";
        int kills1 = 0;
        int kills2 = 0;
        int totalGoldTeam1 = 0;
        int totalGoldTeam2 = 0;
        
        for (Player player : team1.getMembers()) { // first calc the total amount of kills for each team
            kills1 += Integer.parseInt(matchDump.get(player.getName()).get("kills"));
            kills2 += Integer.parseInt(matchDump.get(player.getName()).get("deaths"));
        }
        
        for (Player player : team1.getMembers()) {
            
            System.out.println(player.getName() + ": " + matchDump.get(player.getName()));
            
            teststat += player + ": ";
            double KDA = 0;
            int CS = 0;
            double KP = 0;
            
            totalGoldTeam1 += Integer.parseInt(matchDump.get(player.getName()).get("goldEarned"));
            
            if (Double.parseDouble(matchDump.get(player.getName()).get("deaths")) != 0) {
                KDA = ((Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists"))) / Double.parseDouble(matchDump.get(player.getName()).get("deaths")));
                KDA = Math.round(KDA * 100);
                KDA = KDA / 100;
            } else {
                KDA = (Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists")));
            }
            teststat += "KDA: " + KDA + " ";
            
            CS = Integer.parseInt(matchDump.get(player.getName()).get("minionsKilled")) + Integer.parseInt(matchDump.get(player.getName()).get("neutralMinionsKilled"));
            teststat += "CS: " + CS + " ";
            
            //System.out.println(player.getName() + " " + matchDump.get(player.getName()).get("kills") + " " + matchDump.get(player.getName()).get("assists") + " " + kills1 + ((11 + 7)/27));
            KP = (Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists"))) / (double) (kills1);
            teststat += "KP: " + KP + "\n";
            
            KDA = Math.round(KDA * 100);
            KDA = KDA / 100;
            KP = Math.round(KP * 100);
            KP = KP / 100;
            
            
            if (player.getKDA_ratio() == 0) { //for the first time, don't use previous (0) value
                player.setKDA_ratio(KDA);
                player.setCS_ratio(CS);
                player.setKill_part(KP);
            } else {
                player.setKDA_ratio((player.getKDA_ratio() + KDA) / 2);
                player.setCS_ratio((player.getCS_ratio()+ CS) / 2);
                player.setKill_part((player.getKill_part() + KP) / 2);
            }
            db.updatePlayerStats(player);
        }
        
        for (Player player : team2.getMembers()) {
            teststat += player + ": ";
            double KDA = 0;
            int CS = 0;
            double KP = 0;
            
            System.out.println(player.getName() + ": " + matchDump.get(player.getName()));
            
            totalGoldTeam2 += Integer.parseInt(matchDump.get(player.getName()).get("goldEarned"));
            
            if (Double.parseDouble(matchDump.get(player.getName()).get("deaths")) != 0) {
                KDA = ((Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists"))) / Double.parseDouble(matchDump.get(player.getName()).get("deaths")));
            } else {
                KDA = (Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists")));
            }
            teststat += "KDA: " + KDA + " ";
            
            CS = Integer.parseInt(matchDump.get(player.getName()).get("minionsKilled")) + Integer.parseInt(matchDump.get(player.getName()).get("neutralMinionsKilled"));
            teststat += "CS: " + CS + " ";
            
            KP = (Double.parseDouble(matchDump.get(player.getName()).get("kills")) + Double.parseDouble(matchDump.get(player.getName()).get("assists"))) / (double) kills2;
            teststat += "KP: " + KP + "\n";
            
            
            KDA = Math.round(KDA * 100);
            KDA = KDA / 100;
            KP = Math.round(KP * 100);
            KP = KP / 100;
            
            
            if (player.getKDA_ratio() == 0) { //for the first time, don't use previous (0) value
                player.setKDA_ratio(KDA);
                player.setCS_ratio(CS);
                player.setKill_part(KP);
            } else {
                player.setKDA_ratio((player.getKDA_ratio() + KDA) / 2);
                player.setCS_ratio((player.getCS_ratio()+ CS) / 2);
                player.setKill_part((player.getKill_part() + KP) / 2);
            }
            db.updatePlayerStats(player);
        }
        
        System.out.println(matchDump);
        System.out.println(team1.getMembers().get(0).getName());
        team1.addDragons(Double.parseDouble(matchDump.get(team1.getMembers().get(0).getName()).get("dragons")));
        team2.addDragons(Double.parseDouble(matchDump.get(team2.getMembers().get(0).getName()).get("dragons")));
        team1.addBarons(Double.parseDouble(matchDump.get(team1.getMembers().get(0).getName()).get("barons")));
        team2.addBarons(Double.parseDouble(matchDump.get(team2.getMembers().get(0).getName()).get("barons")));
        team1.addGold(totalGoldTeam1);
        team2.addGold(totalGoldTeam2);
        
        match.setKillsTeam1(kills1);
        match.setKillsTeam2(kills2);
        match.setGoldTeam1(totalGoldTeam1);
        match.setGoldTeam2(totalGoldTeam2);
        match.setTowersTeam1(Integer.parseInt(matchDump.get(team1.getMembers().get(0).getName()).get("towers")));
        match.setTowersTeam2(Integer.parseInt(matchDump.get(team2.getMembers().get(0).getName()).get("towers")));
        
        db.updateTeamStats(team1);
        db.updateTeamStats(team2);
        
        db.updateMatchStats(match);
        
        System.out.println(teststat);
        System.out.println(kills1);
        System.out.println(kills2);
    }
    
    public boolean checkTies(Poule poule) {
        boolean flag = true;
        List<Team> standing = poule.getSortedTeams();
        System.out.println(standing);
        
        int tieBreakerRound = 1;
        
        for (Match match : matchlist) {
            if (match.getType().startsWith(poule.getName() + "_TB_")) { //if the match is a tiebreaker
                tieBreakerRound = Integer.parseInt(match.getType().split("_")[2]) + 1; //this is a numbering system to prevent the matches from having the same id when a tiebreaker round ends up with yet another tie
            }
        }
        
        HashMap<Integer, ArrayList<Team>> scoreDist = new HashMap<>();
        
        for (Team team : standing) {
            if(!scoreDist.containsKey(team.getPouleWins() + team.getTieBreakerWins())) {
                ArrayList<Team> teamsWithTheSameScore = new ArrayList<>();
                teamsWithTheSameScore.add(team);
                scoreDist.put(team.getPouleWins() + team.getTieBreakerWins(), teamsWithTheSameScore);
            } else {
                scoreDist.get(team.getPouleWins() + team.getTieBreakerWins()).add(team);
            }
        }
        
        for (Entry<Integer, ArrayList<Team>> entry : scoreDist.entrySet()) {
            System.out.println("value: " + entry.getValue());
            if (entry.getValue().size() > 1) { //now we have a tie -> generate the required matches IF the noraml AND tie scores are the same
                for(int i = 0 ; i < entry.getValue().size(); i ++){
                    for(int j = i+1 ; j < entry.getValue().size(); j ++){
                        if (entry.getValue().get(i).getPouleWins() == entry.getValue().get(j).getPouleWins() && entry.getValue().get(i).getTieBreakerWins() == entry.getValue().get(j).getTieBreakerWins()) {
                            System.out.println(entry.getValue().get(i) + "," + entry.getValue().get(j));
                            flag = false;
                            Match tiebreaker = new Match(entry.getValue().get(i).getName(), entry.getValue().get(j).getName(), poule.getName() + "_TB_" + tieBreakerRound, ""); //TB for tiebreaker
                            if (matchlist.contains(tiebreaker)) {
                                flag = true; //the tertiairy way of sorting teams in poulelist is supposed to be average time to victory. If the first round of tiebreaker macthes doesn't solve the tie, team are ordened and selected this way
                            } else {
                                matchlist.add(tiebreaker);
                                db.storeMatch(tiebreaker);
                            }
                        }
                    }
                }
            }
        }
        
        return flag; //if flag is false, the poule contains ties
    }
    
    public void completePoule(Poule poule) {

            Team team1 = poule.getSortedTeams().get(0); //select the first two of the poule, these teams made it to the knockout stage
            Team team2 = poule.getSortedTeams().get(1);
            int pouleNr = Integer.parseInt(poule.getName().substring(poule.getName().length() - 1)); // because we generated with i + 1
            
            if (this.poulelist.size() == 4) {
                switch (pouleNr) {
                    case 1:
                        bracketlist.get(0).setTeam1(team1);
                        bracketlist.get(1).setTeam2(team2);
                        break;
                    case 2:
                        bracketlist.get(1).setTeam1(team1);
                        bracketlist.get(0).setTeam2(team2);
                        break;
                    case 3:
                        bracketlist.get(2).setTeam1(team1);
                        bracketlist.get(3).setTeam2(team2);
                        break;
                    case 4:
                        bracketlist.get(3).setTeam1(team1);
                        bracketlist.get(2).setTeam2(team2);
                        break;
                }
            } else if (this.poulelist.size() == 2) {
                switch (pouleNr) {
                    case 1:
                        bracketlist.get(4).setTeam1(team1);
                        bracketlist.get(5).setTeam2(team2);
                        break;
                    case 2:
                        bracketlist.get(5).setTeam1(team1);
                        bracketlist.get(4).setTeam2(team2);
                        break;
                }
            }
//            bracketlist.get(pouleNr).setTeam1(team1);
//            if ((pouleNr + 1) % 2 == 0) { //if poulenr is even, put team in bracket under it else bracket above. crossmatching
//                bracketlist.get(pouleNr - 1).setTeam2(team2);
//            } else {
//                bracketlist.get(pouleNr + 1).setTeam2(team2);
//            }

            

            //System.out.println(bracketlist);

            for (Bracket bracket : bracketlist) { //check if any brackets have 2 team and zero matches ie. just filled and add first match to tournament
                if((bracket.getTeam1() != null && bracket.getTeam2() != null)) {
                    if(bracket.getMatches().isEmpty()) {
                        Match match = new Match(bracket.getTeam1().getName(), bracket.getTeam2().getName(), bracket.getName().concat("_" + bracket.getMatches().size()), "");
                        matchlist.add(match);
                        db.storeMatch(match);
                        bracket.addMatch(match.getMatchID());
                    }
                }
                db.updateBracket(bracket);
            }
        
    }
    public void updateMatch(String matchID, String date, String official){
        String[] ID = matchID.split("_");
        String team1 = ID[ID.length - 2];
        String team2 = ID[ID.length - 1];
        for(Match k: matchlist) {
            if(k.getMatchID().equals(matchID)){
                k.setOfficial(official);
                k.setTimeStamp(date);
                db.updateMatch(k);
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
            if (match.getType().startsWith(poule.getName())) { //startswith because tiebreakers add the TB mark at the end
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
        //player doesn't exist yet
        Player newPlayer = new Player(name);
        db.storePlayer(newPlayer);
        return newPlayer;
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

    public ArrayList<String> getOfficials() {
        return officials;
    }

    public void setOfficials(ArrayList<String> officials) {
        this.officials = officials;
    }

    public void setPoulelist(ArrayList<Poule> poulelist) {
        this.poulelist = poulelist;
    }

    public void setBracketlist(ArrayList<Bracket> bracketlist) {
        this.bracketlist = bracketlist;
    }

    public String convertHashMapToString(HashMap<String, Map<String, String>> matchDump) {
        String dbMatchDump = new String();
        for (Entry<String, Map<String, String>> player : matchDump.entrySet()) {
            dbMatchDump += player.getKey() + "=";
            for (Entry<String, String> stat : player.getValue().entrySet()) {
                dbMatchDump += stat.getKey() + ":" + stat.getValue() + ";";
            }
            dbMatchDump += "||";
        }
        return dbMatchDump;
    }
    
    
    
    
    
    
    
    
}
