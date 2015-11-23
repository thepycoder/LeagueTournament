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
    private String name;
    private double KDA_ratio;
    private double kill_part;
    private double CS_ratio;
    
    //constructor
    
    public Player(String name) 
    {
        this.name = name;
        this.KDA_ratio = 0;
        this.kill_part = 0;
        this.CS_ratio = 0;
    }
    
    public Player(String name, String role, double KDA_ratio, double kill_part, double CS_ratio) {
        this.name = name;
        this.KDA_ratio = KDA_ratio;
        this.kill_part = kill_part;
        this.CS_ratio = CS_ratio;
    }
    
    //getters

    public String getName() {
        return name;
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

    public void setKDA_ratio(double KDA_ratio) {
        this.KDA_ratio = KDA_ratio;
    }

    public void setKill_part(double kill_part) {
        this.kill_part = kill_part;
    }

    public void setCS_ratio(double CS_ratio) {
        this.CS_ratio = CS_ratio;
    }
    
}
