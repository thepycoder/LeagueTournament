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
public class Team {
    private String name;
    private String region;
    private String coach;
    private ArrayList <String> members;
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
    
    
    public Team(String name, String region, String coach, ArrayList<String> members) {
        this.name = name;
        this.region = region;
        this.coach = coach;
        this.members = members;
        this.pouleWins = 0;
    }
    
    public Team(String name, String region, String coach, ArrayList<String> members, int pouleWins) {
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

    public ArrayList<String> getMembers() {
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

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }
    
    

    @Override
    public String toString() {
        return name;
    }
    
    
}
