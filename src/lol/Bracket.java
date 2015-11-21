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
    private int type; //8 for eighth final, 4 for quarter final, 2 for semi final, 1 for final
    private ArrayList <Match> matches;
    private String completed;
    
    //constructor

    public Bracket (String name, int type) 
    {
        this.name = name;
        this.team1 = null;
        this.team2 = null;
        this.type = type;
        this.matches = new ArrayList<>();
        this.completed = "no";
    }

    public Bracket(String name, Team team1, Team team2, int type, ArrayList<Match> matches, String completed) {
        this.name = name;
        this.team1 = team1;
        this.team2 = team2;
        this.type = type;
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
    public ArrayList<Match> getMatches() {
        return matches;
    }

    public String isCompleted() {
        return completed;
    }

    public int getType() {
        return type;
    }

    public String getCompleted() {
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

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
    
    @Override
    public String toString(){
        return name + ": " + team1 + " vs " + team2;
    }
}
