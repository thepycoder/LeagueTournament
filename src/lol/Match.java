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
public class Match {
    
    private String matchID;
    private Team team1;
    private Team team2;
    private String type;
    private String timeStamp;
    private String official;
    
    //constructor

    public Match (Team team1, Team team2, String type, String official)
    {
        this.matchID = team1.getName() + "_" + team2.getName() + "_" + type;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = null;
        this.official = official;
    }
    
    public Match(String matchID, Team team1, Team team2, String timeStamp, String official) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = timeStamp;
        this.official = official;
    }
    
    //getter

    public String getMatchID() {
        return matchID;
    }

    public Team getTeam1() {
        return team1;
    }

    public Team getTeam2() {
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

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public void setTeam2(Team team2) {
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
}
