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
    private String official;
    private ArrayList <String> members;

    public Team ()
    {
    }
    
    public Team(String name, String region, String official, ArrayList<String> members) {
        this.name = name;
        this.region = region;
        this.official = official;
        this.members = members;
    }

    //getters
    
    public String getName() {
        return name;
    }

    public String getRegion() {
        return region;
    }

    public String getOfficial() {
        return official;
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

    public void setOfficial(String official) {
        this.official = official;
    }

    public void setMembers(ArrayList<String> members) {
        this.members = members;
    }
    
    
}
