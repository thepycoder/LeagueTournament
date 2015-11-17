/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author stephan
 */
public class Poule {
    
    private String name;
    private List<Team> teams;
    private ArrayList <Match> matches;
    private boolean completed;
    
    //constructor
    
    public Poule(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.matches = new ArrayList<>();
        this.completed = false;
    }

    public Poule(String name, List<Team> teams) 
    {
        this.name = name;
        this.teams = teams;
    }
    
    public Poule(String name, ArrayList<Team> teams, ArrayList<Match> matches, boolean completed) {
        this.name = name;
        this.teams = teams;
        this.matches = matches;
        this.completed = completed;
    }
    
    public void addTeam(Team team) {
        teams.add(team);
    }
    
    //getters 

    public String getName() {
        return name;
    }

    public List<Team> getTeams() {
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

    @Override
    public String toString() {
        return "name=" + name + ", teams=" + teams;
    }
    
    
    
}
