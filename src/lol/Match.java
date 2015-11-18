/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.Date;

/**
 *
 * @author stephan
 */
public class Match implements Comparable<Match> {
    
    private String matchID;
    private String team1;
    private String team2;
    private String type;
    private String timeStamp;
    private String official;
    
    //constructor

    public Match (String team1, String team2, String type, String official)
    {
        this.matchID = team1 + "_" + team2 + "_" + type;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = null;
        this.official = official;
        this.type = null;
    }
    
    public Match(String matchID, String team1, String team2, String timeStamp, String type, String official) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = timeStamp;
        this.official = official;
        this.type = type;
    }
    
    //getter

    public String getMatchID() {
        return matchID;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    
    public String getOfficial () {
        return official;
    }
    
    //setter

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public void setOfficial (String official) {
        this.official = official;
    }

    void setTimeStamp(Date date) {
        this.timeStamp = date.toString();
    }

    public String getType() {
        return this.type;
    }
    
    @Override
    public String toString() {
        return matchID;
    }

    @Override
    public int compareTo(Match o) {
        return this.getTimeStamp().compareTo(o.getTimeStamp())*-1; //we want the order to be inverted
    }
}
