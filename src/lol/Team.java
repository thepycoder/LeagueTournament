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
    private double barons;
    private double gold;
    private double dragons;
    private ArrayList<Player> members;
    private int pouleWins;
    private int pouleLosses;
    private int tieBreakerWins;
    private int tieBreakerLosses;

    public Team ()
    {
    }
    
    public Team (String name) 
    {
        this.name = name;
        this.region = null;
        this.coach = null;
        this.barons = 0.0;
        this.gold = 0.0;
        this.dragons = 0.0;
        this.members = null;
        this.pouleWins = 0;
        this.pouleLosses = 0;
        this.tieBreakerWins = 0;
        this.tieBreakerLosses = 0;
    }
    
    
    public Team(String name, String region, String coach, ArrayList<Player> members) {
        this.name = name;
        this.region = region;
        this.coach = coach;
        this.barons = 0.0;
        this.gold = 0.0;
        this.dragons = 0.0;
        this.members = members;
        this.pouleWins = 0;
        this.pouleLosses = 0;
        this.tieBreakerWins = 0;
        this.tieBreakerLosses = 0;
    }
    
    public Team(String name, String region, String coach, double barons, double gold, double dragons, ArrayList<Player> members, int pouleWins, int pouleLosses, int tieBreakerWins, int tieBreakerLosses) {
        this.name = name;
        this.region = region;
        this.coach = coach;
        this.barons = barons;
        this.gold = gold;
        this.dragons = dragons;
        this.members = members;
        this.pouleWins = pouleWins;
        this.pouleLosses = pouleLosses;
        this.tieBreakerWins = tieBreakerWins;
        this.tieBreakerLosses = tieBreakerLosses;
        
    }

    public double getBarons() {
        return barons;
    }

    public double getGold() {
        return gold;
    }

    public double getDragons() {
        return dragons;
    }
    
    public void addWin() {
        this.pouleWins += 1;
    }
    
    public void addLoss(){
        this.pouleLosses += 1;
    }
    
    public void addTieWin() {
        this.tieBreakerWins += 1;
    }
    
    public void addTieLoss(){
        this.tieBreakerLosses +=1;
    }
    
    public void addDragons(double amount) {
        if ((this.pouleLosses + this.pouleWins) > 0) { //aka if the team already played matches, so we don't count the 0 it is from the start
            this.setDragons((dragons + amount) / 2.0);
        } else {
            this.setDragons(amount);
        }
    }
    
    public void addBarons(double amount) {
        if ((this.pouleLosses + this.pouleWins) > 0) { //aka if the team already played matches, so we don't count the 0 it is from the start
            System.out.println("HEYOOOOOO");
            this.setBarons((barons + amount) / 2.0);
        } else {
            this.setBarons(amount);
        }
    }
    
    public void addGold(double amount) {
        if ((this.pouleLosses + this.pouleWins) > 0) { //aka if the team already played matches, so we don't count the 0 it is from the start
            this.setGold((gold + amount) / 2.0);
        } else {
            this.setGold(amount);
        }
    }

    public int getTieBreakerWins() {
        return tieBreakerWins;
    }

    public int getTieBreakerLosses() {
        return tieBreakerLosses;
    }
    
    public int getPouleWins() {
        return pouleWins;
    }

    public int getPouleLosses() {
        return pouleLosses;
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

    public void setBarons(double barons) {
        this.barons = barons;
    }

    public void setGold(double gold) {
        this.gold = gold;
    }

    public void setDragons(double dragons) {
        this.dragons = dragons;
    }
    
    public void setPouleWins(int pouleWins) {
        this.pouleWins = pouleWins;
    }

    public void setPouleLosses(int pouleLosses) {
        this.pouleLosses = pouleLosses;
    }

    public void setTieBreakerWins(int tieBreakerWins) {
        this.tieBreakerWins = tieBreakerWins;
    }

    public void setTieBreakerLosses(int tieBreakerLosses) {
        this.tieBreakerLosses = tieBreakerLosses;
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
        } else if (t.getPouleWins() == this.pouleWins) { //when ordinairy points form a tie
            if (t.getTieBreakerWins() > this.tieBreakerWins) {
                return 1;
            } else {
                return -1;
            }
        } else {
            return -1;
        }
    }
    
    
}
