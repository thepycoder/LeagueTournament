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
public class Bracket implements Comparable<Bracket> {
    private String name;
    private Team team1;
    private Team team2;
    private int team1score;
    private int team2score;
    private int type; //8 for eighth final, 4 for quarter final, 2 for semi final, 1 for final
    private ArrayList<String> matches;
    private String completed;
    
    //constructor

    public Bracket (String name, int type) 
    {
        this.name = name;
        this.team1 = null;
        this.team2 = null;
        this.team1score = 0;
        this.team2score = 0;
        this.type = type;
        this.matches = new ArrayList<>();
        this.completed = "no";
    }

    public Bracket(String name, Team team1, Team team2, int team1score, int team2score, int type, ArrayList<String> matches, String completed) {
        this.name = name;
        this.team1 = team1;
        this.team2 = team2;
        this.team1score = team1score;
        this.team2score = team2score;
        this.type = type;
        this.matches = matches;
        this.completed = completed;
    }
    
    public void addMatch(String matchID) {
        this.matches.add(matchID);
    }
    
    public void addWinTeam1(){
        setTeam1score(getTeam1score() + 1); 
    }
    
    public void addWinTeam2(){
        setTeam2score(getTeam2score() + 1); 
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
    public String getTeam1Name() {
        if (team1 == null){
            return "TBA";
        } else {
            return team1.getName();
        }
    }
    public String getTeam2Name() {
        if (team2 == null){
            return "TBA";
        } else {
            return team2.getName();
        }
    }
    public ArrayList<String> getMatches() {
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

    public int getTeam1score() {
        return team1score;
    }

    public int getTeam2score() {
        return team2score;
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

    public void setMatches(ArrayList<String> matches) {
        this.matches = matches;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }

    public void setTeam1score(int team1score) {
        this.team1score = team1score;
    }

    public void setTeam2score(int team2score) {
        this.team2score = team2score;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
    @Override
    public String toString(){
        return name + ": " + team1 + ": " + team1score + " vs " + team2 + ": " + team2score + " type: " + type;
    }

    @Override
    public int compareTo(Bracket o) {
        return this.name.compareTo(o.getName());
    }
}
