/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Temp
 */
public class DatabaseHandler {
    
    public String user = "BINFG16";
    public String pass = "f9xff87y";
    public String url = "jdbc:mysql://mysqlha2.ugent.be/BINFG16";
    Connection conn = null;
    
    
    public DatabaseHandler() {
    }
    
    
    public Connection createConnection(String url) {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection conn = DriverManager.getConnection(url,user,pass);
            
            return conn;
            
        } catch (ClassNotFoundException ex) {
            //Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("The jdbc driver was not found! " + ex);
            return null;
        } catch (SQLException ex) {
            //Logger.getLogger(DatabaseHandler.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("A connection to the databse could not be established! " + ex);
            return null;
        }
        
    }
    
    public void storeTeam(String name, ArrayList<String> members, String coach, String region){
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            String mem = new String();
            
            for (String member : members) {
                mem += member + ", ";
            }
            String query = "INSERT INTO teams (name, region, members, coach) VALUES ('" + name + "', '" + region + "', '" + mem + "', '" + coach + "')";
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Couldn't close the connection: " + ex);
                }
            }
        }
    }
  public void storeMatch(String matchID, Team team1, Team team2, String timeStamp, String official,String type){
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "INSERT INTO teams (name, region, members, coach) VALUES ('" + matchID + "', '" + team1 + "', '" + team2 + "', '" + timeStamp + "', '" + official + "', '" + type + "')";
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Couldn't close the connection: " + ex);
                }
            }
        }
    }
  public void storePoule(String name, Poule poule){
        try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement(); 
            
            String teams = new String();
            for(Team x : poule.getTeams()){
                teams += x.getName() + ", ";
            }     
            String query = "INSERT INTO teams (name, teams) VALUES ('" + name + "', '" + teams + "')";
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Couldn't close the connection: " + ex);
                }
            }
        }
    }
   public void storeBracket(String name, Team team1, Team team2, String timeStamp, ArrayList<Match> matches, String type){
        try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement(); 
            
            String matchez = new String();
            for(Match x : matches){
                matchez += x.getMatchID() + ", ";
            }     
            String query = "INSERT INTO teams (name, teams) VALUES('" + name + "', '" + team1 + "', '" + team2 + "', '" + matchez + "', '" + false + "', '" + type + "')"; 
            System.out.println(query);
            stmt.executeUpdate(query);
        } catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
        } finally {
            if(conn != null) {
                try {
                    conn.close();
                } catch (SQLException ex) {
                    System.out.println("Couldn't close the connection: " + ex);
                }
            }
        }
    }
  
}
