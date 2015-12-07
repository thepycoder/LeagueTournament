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
    
    public void generate() {
        builder.append("<!DOCTYPE html>");
        builder.append("<html lang=\"en\">");
        builder.append("<head>");
        builder.append("<title>Tournament Overview</title>");
        builder.append("<style>table {border-collapse: collapse;width: 100%;}th, td {text-align: left;padding: 8px;}tr:nth-child(even){background-color: #f2f2f2}th {background-color: #4CAF50;color: white;}</style>");
        builder.append("</head>");
        builder.append("<body>");
        genTeams();
        builder.append("<br>");
        genMatches();
        builder.append("<br>");
        genPoules();
        builder.append("<br>");
        genPlayerStats();
        builder.append("<br>");
        genBrackets();
        builder.append("</body>");
        builder.append("</html>");
        writeToFile(builder.toString());
        builder = new StringBuilder();
    }
    
    public void genTeams() {
        builder.append("<h1>Team overview</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>Name</th><th>Coach</th><th>Region</th><th>members</th><th></th><th></th><th></th><th></th>");
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
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>timestamp</th><th>official</th><th>tiebreaker</th>");
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
        }
        builder.append("</table>");
        
        
        builder.append("<h2>Planned</h2>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>timestamp</th><th>official</th><th>tiebreaker</th>");
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
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>timestamp</th><th>official</th><th>tiebreaker</th>");
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
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>timestamp</th><th>official</th><th>tiebreaker</th>");
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
        }
        builder.append("</table>");
    }
    
    public void genPoules() {
        builder.append("<h1>Poule overview</h1>");
        for (Poule poule : t.getPoulelist()) {
            builder.append("<h2>" + poule.getName() + "</h2>");
            builder.append("<table border=\"1\" style=\"width:100%\">");
            builder.append("<tr><th>Team</th><th>wins</th><th>losses</th><th>tiebreakerwins</th><th>tiebreakerlosses</th>");
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
            builder.append("<p>" + "Gold: " + team.getGold() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Dragons: " + team.getDragons() + "&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;" + "Barons: " + team.getBarons() + "</p>");            
            builder.append("<table border=\"1\" style=\"width:100%\">");
            builder.append("<tr><th>Username</th><th>Average KDA</th><th>Average Kill Participation</th><th>Average CS-score</th>");
            for (Player player : team.getMembers()) {
                builder.append("<tr>");
                builder.append("<td>" + player.getName() + "</td>");
                builder.append("<td>" + player.getKDA_ratio() + "</td>");
                builder.append("<td>" + player.getKill_part() + "</td>");
                builder.append("<td>" + player.getCS_ratio() + "</td>");
                builder.append("</tr>");
            }
            builder.append("</table>");
        }
    }
}
