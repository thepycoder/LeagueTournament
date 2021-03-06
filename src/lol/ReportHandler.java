/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author victor
 */

public class ReportHandler {
    
    public String html = "";
    public Tournament t;
    public DatabaseHandler db;
    public StringBuilder builder;
    
    
    
    public ReportHandler(Tournament t) {
        this.t = t;
        this.db = new DatabaseHandler(this.t);
        this.builder = new StringBuilder();
    }
    
    public void writeToFile(String html) {
        PrintWriter writer = null;
        try {
            writer = new PrintWriter("report.html", "UTF-8");
            writer.println(html);
            writer.close();
        } catch (FileNotFoundException ex) {
            System.out.println(ex);
        } catch (UnsupportedEncodingException ex) {
            System.out.println(ex);
        } finally {
            writer.close();
        }
    }
    
    public void generate(Boolean a, Boolean b, Boolean c, Boolean d, Boolean e) {       
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head>");
        builder.append("<title>Tournament Overview</title>");
        builder.append("<style>body {font: 200 20px/1.5 Helvetica, Verdana, sans-serif;} table {border-collapse: collapse;width: 100%;}th, td {text-align: left;padding: 8px;}tr:nth-child(even){background-color: #f2f2f2}th {background-color: #4CAF50;color: white;}ul {list-style-type: none;margin: 0;padding: 0;}li{font: 200 20px/1.5 Helvetica, Verdana, sans-serif;border-bottom: 1px solid #ccc;}li:last-child {border: none;}li a {text-decoration: none;color: #000;display: block;width: 200px;-webkit-transition: font-size 0.3s ease, background-color 0.3s ease;-moz-transition: font-size 0.3s ease, background-color 0.3s ease;-o-transition: font-size 0.3s ease, background-color 0.3s ease;-ms-transition: font-size 0.3s ease, background-color 0.3s ease;transition: font-size 0.3s ease, background-color 0.3s ease;}li a:hover {font-size: 30px;background: #f6f6f6;} div{width: 200px;}</style>");
        builder.append("</head>");
        builder.append("<body>");
        builder.append("<h1>Esports: Tournament League of Legends</h1>");
        genTournament();
        if(a == true){
            genMatches();        
            builder.append("<br>");
        }
        if(e == true){
            genTeams();
            builder.append("<br>");
        }
        if(b == true){
            genPoules();
            builder.append("<br>");
        }
        if (c == true){
            genPlayerStats();
            builder.append("<br>");
        }
        if(d == true){
            genBrackets();
            
        }
        builder.append("</body>");
        builder.append("</html>");
        writeToFile(builder.toString());
        builder = new StringBuilder();
    }
    
    public void genTournament() {
        builder.append("<h1>Tournament overview</h1>");
        builder.append("<table>");
        builder.append("<tr>");
        builder.append("<td>");
        builder.append("<div>");
        builder.append("<p>Officials</p>");
        builder.append("<ul>");
        for (String official : t.getOfficials()) {
            builder.append("<li><a href=#>" + official + "</a></li>");
        }
        builder.append("</ul>");
        builder.append("</div>");
        builder.append("</td>");
        
        builder.append("<td>");
        builder.append("<div>");
        builder.append("<p>Coaches</p>");
        builder.append("<ul>");
        for (Team team : t.getTeamlist()) {
            if (!team.getCoach().equals("")) {
                builder.append("<li><a href=#>" + team.getCoach() + "</a></li>");
            }
        }
        builder.append("</ul>");
        builder.append("</div>");
        builder.append("</td>");
        
        builder.append("<td>");
        builder.append("<div>");
        builder.append("</div>");
        builder.append("</td>");
        
        builder.append("</tr>");
        builder.append("</table>");
    }
    
    public void genTeams() {
        builder.append("<h1>Team overview</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>Name</th><th>Coach</th><th>Region</th><th>Members</th><th></th><th></th><th></th><th></th>");
        for (Team team : t.getTeamlist()) {
            builder.append("<tr>");
            builder.append("<td>" + team.getName() + "</td>");
            builder.append("<td>" + team.getCoach()+ "</td>");
            builder.append("<td>" + team.getRegion() + "</td>");
            builder.append("<td>" + team.getMembers().get(0) + "</td>");
            builder.append("<td>" + team.getMembers().get(1) + "</td>");
            builder.append("<td>" + team.getMembers().get(2) + "</td>");
            builder.append("<td>" + team.getMembers().get(3) + "</td>");
            builder.append("<td>" + team.getMembers().get(4) + "</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
    }
    
    public void genMatches() {
        
        ArrayList<Match> today = new ArrayList<>();
        ArrayList<Match> history = new ArrayList<>();
        ArrayList<Match> planned = new ArrayList<>();
        ArrayList<Match> toplay = new ArrayList<>();
        SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy MM dd");
        Date currentDate = new Date();
        String now = sdfDate.format(currentDate);
        
        for (Match match : t.getMatchlist()) {
            if (match.getCompleted().equals("no") && match.getTimeStamp().equals("null")) { //match isn't played nor planned
                toplay.add(match);
            }
            if (match.getCompleted().equals("no") && !(match.getTimeStamp().equals("null"))) { // match is planned and not yet played
                planned.add(match);
            }
            if (match.getTimeStamp().equals(now) && match.getCompleted().equals("yes")) { // if a match was played today
                System.out.println(match.getTimeStamp() + " " + now);
                today.add(match);
            }
            if (!match.getTimeStamp().equals(now) && match.getCompleted().equals("yes")) { //match is played but not today
                history.add(match);
            }
        }
        
        builder.append("<h1>Match overview</h1>");
        
        builder.append("<h2>Played Today</h2>");
        builder.append("<table border=\"1\" style=\"width:100%\" id='report'>");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>Timestamp</th><th>Official</th><th>Tiebreaker</th>");
        for (Match match : today) {
            builder.append("<tr>");
            builder.append("<td>" + match.getMatchID()+ "</td>");
            if (match.getWinner() != null && match.getWinner().equals(match.getTeam1())) {
                builder.append("<td bgcolor='#E8AF0C'>" + match.getTeam1()+ "</td>");
                builder.append("<td>" + match.getTeam2()+ "</td>");
            } else {
                builder.append("<td>" + match.getTeam1()+ "</td>");
                builder.append("<td bgcolor='#E8AF0C'>" + match.getTeam2()+ "</td>");
            }
            builder.append("<td>" + match.getTimeStamp() + "</td>");
            builder.append("<td>" + match.getOfficial() + "</td>");
            if (match.getType().startsWith("Poule")) {
                builder.append("<td>" + match.getTieBreaker() + "</td>");
            } else {
                builder.append("<td>N/A</td>");
            }
            
            builder.append("</tr>");
            
            builder.append("<tr>");
            builder.append("<td colspan='6'>");
            builder.append("<p>");
            builder.append("Total Kills: " + match.getKillsTeam1() + " - " + match.getKillsTeam2() + "   |   ");
            builder.append("Total Gold: " + match.getGoldTeam1() + " - " + match.getGoldTeam2() + "   |   ");
            builder.append("Total Towers: " + match.getTowersTeam1() + " - " + match.getTowersTeam2());
            builder.append("</td>");
            builder.append("</tr>");

            
        }
        builder.append("</table>");
        
        
        builder.append("<h2>Planned</h2>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>Timestamp</th><th>Official</th><th>Tiebreaker</th>");
        for (Match match : planned) {
            builder.append("<tr>");
            builder.append("<td>" + match.getMatchID()+ "</td>");
            builder.append("<td>" + match.getTeam1()+ "</td>");
            builder.append("<td>" + match.getTeam2()+ "</td>");
            builder.append("<td>" + match.getTimeStamp() + "</td>");
            builder.append("<td>" + match.getOfficial() + "</td>");
            if (match.getType().startsWith("Poule")) {
                builder.append("<td>" + match.getTieBreaker() + "</td>");
            } else {
                builder.append("<td>N/A</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</table>");
        
        
        
        builder.append("<h2>To Be Played</h2>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>Timestamp</th><th>Official</th><th>Tiebreaker</th>");
        for (Match match : toplay) {
            builder.append("<tr>");
            builder.append("<td>" + match.getMatchID()+ "</td>");
            builder.append("<td>" + match.getTeam1()+ "</td>");
            builder.append("<td>" + match.getTeam2()+ "</td>");
            builder.append("<td>" + match.getTimeStamp() + "</td>");
            builder.append("<td>" + match.getOfficial() + "</td>");
            if (match.getType().startsWith("Poule")) {
                builder.append("<td>" + match.getTieBreaker() + "</td>");
            } else {
                builder.append("<td>N/A</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</table>");
        
        
        builder.append("<h2>History</h2>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>Timestamp</th><th>Official</th><th>Tiebreaker</th>");
        for (Match match : history) {
            builder.append("<tr>");
            builder.append("<td>" + match.getMatchID()+ "</td>");
            if (match.getWinner() != null && match.getWinner().equals(match.getTeam1())) {
                builder.append("<td bgcolor='#E8AF0C'>" + match.getTeam1()+ "</td>");
                builder.append("<td>" + match.getTeam2()+ "</td>");
            } else {
                builder.append("<td>" + match.getTeam1()+ "</td>");
                builder.append("<td bgcolor='#E8AF0C'>" + match.getTeam2()+ "</td>");
            }
            builder.append("<td>" + match.getTimeStamp() + "</td>");
            builder.append("<td>" + match.getOfficial() + "</td>");
            if (match.getType().startsWith("Poule")) {
                builder.append("<td>" + match.getTieBreaker() + "</td>");
            } else {
                builder.append("<td>N/A</td>");
            }
            builder.append("</tr>");
            
            builder.append("<tr>");
            builder.append("<td colspan='6'>");
            builder.append("<p>");
            builder.append("Total Kills: " + match.getKillsTeam1() + " - " + match.getKillsTeam2() + "   |   ");
            builder.append("Total Gold: " + match.getGoldTeam1() + " - " + match.getGoldTeam2() + "   |   ");
            builder.append("Total Towers: " + match.getTowersTeam1() + " - " + match.getTowersTeam2());
            builder.append("</td>");
            builder.append("</tr>");
            
        }
        builder.append("</table>");
    }
    
    public void genPoules() {
        builder.append("<h1>Poule overview</h1>");
        for (Poule poule : t.getPoulelist()) {
            builder.append("<h2>" + poule.getName() + "</h2>");
            builder.append("<table border=\"1\" style=\"width:100%\">");
            builder.append("<tr><th>Team</th><th>Wins</th><th>Losses</th><th>Tiebreakerwins</th><th>Tiebreakerlosses</th>");
            for (Team team : poule.getSortedTeams()) {
                builder.append("<tr>");
                builder.append("<td>" + team.getName()+ "</td>");
                builder.append("<td>" + team.getPouleWins()+ "</td>");
                builder.append("<td>" + team.getPouleLosses()+ "</td>");
                builder.append("<td>" + team.getTieBreakerWins() + "</td>");
                builder.append("<td>" + team.getTieBreakerLosses() + "</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");
        }
    }
    
    public void genBrackets() {
        System.out.println(t.bracketlist);
        ArrayList<Bracket> quarterFinals = new ArrayList<>();
        ArrayList<Bracket> semiFinals = new ArrayList<>();
        ArrayList<Bracket> finals = new ArrayList<>();
        for (Bracket bracket : t.getBracketlist()) {
            switch (bracket.getType()) {
                case 4:
                    quarterFinals.add(bracket);
                    break;
                case 2:
                    semiFinals.add(bracket);
                    break;
                case 1:
                    finals.add(bracket);
            }
        }
        if (t.getPoulelist().size() == 4) { //quarter finals
            builder.append("<h1>Quarter Finals</h1>");
            builder.append("<table border=\"1\" style=\"width:100%\">");
            builder.append("<tr><th>Team 1</th><th>score</th><th>Team 2</th><th>score</th>");
            for (Bracket b : quarterFinals) {
                builder.append("<tr>");
                builder.append("<td>" + b.getTeam1Name()+ "</td>");
                builder.append("<td>" + b.getTeam1score()+ "</td>");
                builder.append("<td>" + b.getTeam2Name() + "</td>");
                builder.append("<td>" + b.getTeam2score() + "</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");
        } else {
            builder.append("<h1>Quarter Finals</h1>");
            builder.append("N/A");
        }
        builder.append("<br>");

        
        builder.append("<h1>Semi Finals</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>Team 1</th><th>score</th><th>Team 2</th><th>score</th>");
        for (Bracket b : semiFinals) {
            builder.append("<tr>");
            builder.append("<td>" + b.getTeam1Name()+ "</td>");
            builder.append("<td>" + b.getTeam1score()+ "</td>");
            builder.append("<td>" + b.getTeam2Name() + "</td>");
            builder.append("<td>" + b.getTeam2score() + "</td>");
            builder.append("</tr>");
        }
        builder.append("</table>");
     
        builder.append("<br>");
        //finals always
        builder.append("<h1>Final</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>Team 1</th><th>score</th><th>Team 2</th><th>score</th>");
        builder.append("<tr>");
        builder.append("<td>" + finals.get(0).getTeam1Name()+ "</td>");
        builder.append("<td>" + finals.get(0).getTeam1score()+ "</td>");
        builder.append("<td>" + finals.get(0).getTeam2Name() + "</td>");
        builder.append("<td>" + finals.get(0).getTeam2score() + "</td>");
        builder.append("</tr>");
        builder.append("</table>");
        
        
    }
    
    public void genPlayerStats() {
        
        
        builder.append("<h1>Statistics overview</h1>");
        for (Team team : t.getTeamlist()) {
            builder.append("<h2>" + team.getName() + "</h2>");
            builder.append("<p>" + "Avg. Gold: " + team.getGold() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Avg. Dragons: " + team.getDragons() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Avg. Barons: " + team.getBarons() + "</p>");            
            builder.append("<table border=\"1\" style=\"width:100%\">");
            builder.append("<tr><th>Username</th><th>Average KDA</th><th>Average Kill Participation</th><th>Average CS-score</th>");
            for (Player player : team.getMembers()) {
                
                double KDA = player.getKDA_ratio();
                KDA = Math.round(KDA * 100);
                KDA = KDA / 100;
                
                double KP = player.getKill_part();
                KP = Math.round(KP * 100);
                KP = KP / 100;
                
                double CS = player.getCS_ratio();
                CS = Math.round(CS * 100);
                CS = CS / 100;
                
                builder.append("<tr>");
                builder.append("<td>" + player.getName() + "</td>");
                builder.append("<td>" + KDA + "</td>");
                builder.append("<td>" + KP + "</td>");
                builder.append("<td>" + CS + "</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");
        }
    }
}
