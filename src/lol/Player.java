/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

/**
 *
 * @author stephan
 */
public class Player {
    private String player;
    private String role;
    private double KDA_ratio;
    private double kill_part;
    private double CS_ratio;
    private Team name;
    
    //constructor
    
    public Player() 
    {
    }
    
    public Player(String player, String role, double KDA_ratio, double kill_part, double CS_ratio, Team name) {
        this.player = player;
        this.role = role;
        this.KDA_ratio = KDA_ratio;
        this.kill_part = kill_part;
        this.CS_ratio = CS_ratio;
        this.name = name;
    }
    
    //getters
    
    public String getPlayer() {
        return player;
    }

    public String getRole() {
        return role;
    }

    public double getKDA_ratio() {
        return KDA_ratio;
    }

    public double getKill_part() {
        return kill_part;
    }

    public double getCS_ratio() {
        return CS_ratio;
    }

    public Team getName() {
        return name;
    }

    public void setPlayer(String player) {
        this.player = player;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public void setKDA_ratio(double KDA_ratio) {
        this.KDA_ratio = KDA_ratio;
    }

    public void setKill_part(double kill_part) {
        this.kill_part = kill_part;
    }

    public void setCS_ratio(double CS_ratio) {
        this.CS_ratio = CS_ratio;
    }

    public void setName(Team name) {
        this.name = name;
    }
    
}
