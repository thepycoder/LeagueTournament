/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;

/**
 *
 * @author stephan
 */
public class Bracket {
    private String name;
    private Team team1;
    private Team team2;
    private String timeStamp;
    private ArrayList <Match> matches;
    private boolean completed;
    
    //constructor

    public Bracket () 
    {
    }
    
    public Bracket(String name, Team team1, Team team2, String timeStamp, ArrayList<Match> matches, boolean completed) {
        this.name = name;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = timeStamp;
        this.matches = matches;
        this.completed = completed;
    }
    
    //getters

    public String getName() {
        return name;
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

    public ArrayList<Match> getMatches() {
        return matches;
    }

    public boolean isCompleted() {
        return completed;
    }
    
    //setters

    public void setName(String name) {
        this.name = name;
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

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
