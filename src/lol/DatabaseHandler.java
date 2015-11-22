/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.sql.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
  public void storeMatch(Match match){
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "INSERT INTO matches (matchID, team1, team2, timestamp, official, type, completed) VALUES ('" + match.getMatchID() + "', '" + match.getTeam1() + "', '" + match.getTeam2() + "', '" + match.getTimeStamp() + "', '" + match.getOfficial() + "', '" + match.getType() + "', '" + match.getCompleted() + "')";
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
  
  public void updateMatch(Match match) {
      try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE matches SET timestamp='" + match.getTimeStamp()+ "', official='" + match.getOfficial() + "' WHERE matchID='" + match.getMatchID() + "'";
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
  
  public void storePoule(Poule poule){
        try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement(); 
            
            String teams = "'";
            for(Team x : poule.getTeams()){
                teams += x.getName() + ",";
            }   
            teams += "'";
            String query = "INSERT INTO poules (name, teams, completed) VALUES ('" + poule.getName() + "', " + teams + ", 'no')";
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
  
  public ArrayList<Poule> retrievePoules() {
      ArrayList<Poule> poules = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM poules";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ArrayList<Team> pouleTeams = new ArrayList<>();
                ArrayList<Team> storedTeams = this.retrieveTeams();
                for (String pouleTeam : rs.getString("teams").split(",")) {
                    for (Team storedTeam : storedTeams) {
                        if (storedTeam.getName().equals(pouleTeam)) {
                            pouleTeams.add(storedTeam);
                        }
                    }
                }
                Poule poule = new Poule(rs.getString("name"), pouleTeams, rs.getString("completed"));
                poules.add(poule);
             }
            return poules;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen poules: " + ex);
            return null;
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
  
  public ArrayList<Bracket> retrieveBrackets() {
      ArrayList<Bracket> brackets = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM brackets";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ArrayList<Match> bracketMatches = new ArrayList<>();
                ArrayList<Match> storedMatches = this.retrieveMatches();
                for (String bracketMatch : rs.getString("matches").split(",")) {
                    for (Match storedMatch : storedMatches) {
                        if (storedMatch.getMatchID().equals(bracketMatch)) {
                            bracketMatches.add(storedMatch);
                        }
                    }
                }
                
                ArrayList<Team> storedTeams = this.retrieveTeams();
                Team team1 = null;
                Team team2 = null;
                for (Team storedTeam : storedTeams) {
                    if (storedTeam.getName().equals(rs.getString("team1"))) {
                         team1 = storedTeam;
                    }
                    if (storedTeam.getName().equals(rs.getString("team2"))) {
                         team2 = storedTeam;
                    }
                }
                
                Bracket bracket = new Bracket(rs.getString("name"), team1, team2, rs.getInt("type"), bracketMatches, rs.getString("completed"));
                brackets.add(bracket);
             }
            return brackets;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen brackets: " + ex);
            return null;
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
  
   public void storeBracket(Bracket bracket){
        try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement();  
            String query = "INSERT INTO brackets (name, type) VALUES('" + bracket.getName() + "', " + bracket.getType() + ")"; 
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
   
   public ArrayList<Team> retrieveTeams() {
       
        ArrayList<Team> teams = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM teams";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                //Retrieve by column name
                ArrayList<String> members = new ArrayList<>(Arrays.asList(rs.getString("members").split(",")));
                members.remove(members.size() - 1); //due to manner of input, an empty space at the end is created, this truncates this
                Team team = new Team(rs.getString("name"), rs.getString("region"), rs.getString("coach"), members, rs.getInt("pouleWins"));
                teams.add(team);
             }
            return teams;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen teams: " + ex);
            return null;
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
   
   public void addWin(Team team) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE teams SET poulewins=" + team.getPouleWins() + " WHERE name='" + team.getName()+ "'";
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
   
   public void setCompleted(Match match) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE matches SET completed='yes' WHERE matchID='" + match.getMatchID()+ "'";
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
   
   public ArrayList<Match> retrieveMatches() {
        ArrayList<Match> matches = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM matches";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                Match match = new Match(rs.getString("matchID"), rs.getString("team1"), rs.getString("team2"), rs.getString("timestamp"), rs.getString("type"), rs.getString("official"), rs.getString("completed"));
                matches.add(match);
             }
            return matches;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen teams: " + ex);
            return null;
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

   public void resetMatches() {
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "DELETE FROM matches";
            stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen teams: " + ex);
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
   
   public void resetPoules() {
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "DELETE FROM poules";
            stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen teams: " + ex);
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
   
   public void resetBrackets() {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "DELETE FROM brackets";
            stmt.executeUpdate(query);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen teams: " + ex);
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
   
   public void resetScores() {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE teams SET poulewins=0";
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
