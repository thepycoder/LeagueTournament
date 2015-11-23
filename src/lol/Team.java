/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.ArrayList;
import java.util.Objects;

/**
 *
 * @author stephan
 */
public class Team implements Comparable<Team> {
    private String name;
    private String region;
    private String coach;
    private ArrayList<Player> members;
    private int pouleWins;

    public Team ()
    {
    }
    
    public Team (String name) 
    {
        this.name = name;
        this.region = null;
        this.coach = null;
        this.members = null;
        this.pouleWins = 0;
    }
    
    
    public Team(String name, String region, String coach, ArrayList<Player> members) {
        this.name = name;
        this.region = region;
        this.coach = coach;
        this.members = members;
        this.pouleWins = 0;
    }
    
    public Team(String name, String region, String coach, ArrayList<Player> members, int pouleWins) {
        this.name = name;
        this.region = region;
        this.coach = coach;
        this.members = members;
        this.pouleWins = pouleWins;
    }
    
    public void addWin() {
        this.pouleWins += 1;
    }

    //getters

    public int getPouleWins() {
        return pouleWins;
    }
    
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getCoach() {
        return coach;
    }

    public ArrayList<Player> getMembers() {
        return members;
    }

    //setters
    public void setName(String name) {
        this.name = name;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setOfficial(String coach) {
        this.coach = coach;
    }

    public void setMembers(ArrayList<Player> members) {
        this.members = members;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public void setPouleWins(int pouleWins) {
        this.pouleWins = pouleWins;
    }
    
    
    

    @Override
    public String toString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        Team t = (Team) o;
        if (t.getName().equals(this.getName())) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.name);
        return hash;
    }
    
    @Override
    public int compareTo(Team t) {
        if (t == null){
            return -1;
        } else if (t.getPouleWins() > this.pouleWins) {
            return 1;
        } else {
            return -1;
        }
    }
    
    
}
