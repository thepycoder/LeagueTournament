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
public class Poule {
    private String name;
    private ArrayList <Team> teams;
    private ArrayList <Match> matches;
    private boolean completed;
    
    //constructor

    public Poule() 
    {
    }
    
    public Poule(String name, ArrayList<Team> teams, ArrayList<Match> matches, boolean completed) {
        this.name = name;
        this.teams = teams;
        this.matches = matches;
        this.completed = completed;
    }
    //getters 

    public String getName() {
        return name;
    }

    public ArrayList<Team> getTeams() {
        return teams;
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

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }

    public void setMatches(ArrayList<Match> matches) {
        this.matches = matches;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
