/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

/**
 *
 * @author stephan
 */
public class Match {
    private int matchID;
    private Team team1;
    private Team team2;
    private String timeStamp;
    
    //constructor

    public Match ()
    {
    }
    
    public Match(int matchID, Team team1, Team team2, String timeStamp) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = timeStamp;
    }
    
    //getter

    public int getMatchID() {
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
    
    //setter

    public void setMatchID(int matchID) {
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
    
    
}
