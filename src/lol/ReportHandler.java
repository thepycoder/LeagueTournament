/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        builder.append("<head><title>Tournament Overview</title></head>");
        builder.append("<body>");
        genTeams();
        builder.append("</body>");
        builder.append("</html>");
        writeToFile(builder.toString());
    }
    
    public void genTeams() {
        builder.append("<h1>Team overview</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>Name</th><th>Coach</th><th>Region</th><th>members</th>");
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
    
    public void getMatches() {
        builder.append("<h1>Match overview</h1>");
        builder.append("<table border=\"1\" style=\"width:100%\">");
        builder.append("<tr><th>MatchID</th><th>Team1</th><th>Team2</th><th>timestamp</th><th>official</th><th>tiebreaker</th>");
        for (Match match : t.getMatchlist()) {
            builder.append("<tr>");
            builder.append("<td>" + match.getMatchID()+ "</td>");
            builder.append("<td>" + match.getTeam1()+ "</td>");
            builder.append("<td>" + match.getTeam2()+ "</td>");
            builder.append("<td>" + match.getTimeStamp() + "</td>");
            builder.append("<td>" + match.getOfficial() + "</td>");
            if (match.getType().equals("Poule")) {
                builder.append("<td>" + match.getTieBreaker() + "</td>");
            } else {
                builder.append("<td>N/A</td>");
            }
            builder.append("</tr>");
        }
        builder.append("</table>");
    }
}
