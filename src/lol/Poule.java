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
    private ArrayList<Team> teams;
    private String completed;
    
    //constructor
    
    public Poule(String name) {
        this.name = name;
        this.teams = new ArrayList<>();
        this.completed = "false";
    }

    public Poule(String name, ArrayList<Team> teams) 
    {
        this.name = name;
        this.teams = teams;
    }
    
    public Poule(String name, ArrayList<Team> teams, String completed) {
        this.name = name;
        this.teams = teams;
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

    public String isCompleted() {
        return completed;
    }
    
    //setters

    public void setName(String name) {
        this.name = name;
    }

    public void setTeams(ArrayList<Team> teams) {
        this.teams = teams;
    }
    public void setCompleted(String completed) {
        this.completed = completed;
    }

    @Override
    public String toString() {
        return "name=" + name + ", teams=" + teams;
    }
    
    
    
}
