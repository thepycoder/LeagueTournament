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
    public Tournament t;
    
    
    public DatabaseHandler(Tournament t) {
        this.t = t;
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
    
    public void storeTeam(String name, ArrayList<Player> members, String coach, String region){
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            String mem = new String();
            
            for (Player member : members) {
                mem += member.getName() + ",";
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
  
  public void updateBracket(Bracket bracket) {
      try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String matches = new String();
            for (String match : bracket.getMatches()) {
                matches += match + ",";
            }
            
            String query = "UPDATE brackets SET team1='" + bracket.getTeam1Name() + "', team2='" + bracket.getTeam2Name() + "', matches='" + matches + "', completed='" + bracket.getCompleted() + "', type=" + bracket.getType() + " WHERE name='" + bracket.getName()+ "'";
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
                ArrayList<Team> storedTeams = t.getTeamlist();
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
                ArrayList<String> bracketMatches = new ArrayList<>();
                ArrayList<Match> storedMatches = this.retrieveMatches();
                for (String bracketMatch : rs.getString("matches").split(",")) {
                    for (Match storedMatch : storedMatches) {
                        if (storedMatch.getMatchID().equals(bracketMatch)) {
                            bracketMatches.add(storedMatch.getMatchID());
                        }
                    }
                }
                
                ArrayList<Team> storedTeams = t.getTeamlist();
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
                
                Bracket bracket = new Bracket(rs.getString("name"), team1, team2, rs.getInt("team1score"), rs.getInt("team2score"), rs.getInt("type"), bracketMatches, rs.getString("completed"));
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
            String matches = new String();
            for (String match : bracket.getMatches()) {
                matches += match + ",";
            }
            String query = "INSERT INTO brackets (name, team1, team2, matches, completed, type) VALUES('" + bracket.getName() + "', '" + bracket.getTeam1Name() + "', '" + bracket.getTeam2Name() + "', '" + matches + "', '" + bracket.getCompleted() + "', " +  bracket.getType() + ")"; 
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
   
   public ArrayList<Double> getPlayerStats(String playername){
       
       ArrayList<Double> stats = new ArrayList<>();
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM players WHERE name = '" + playername + "'";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                stats.add(rs.getDouble("KDA"));
                stats.add(rs.getDouble("KP"));
                stats.add(rs.getDouble("CS"));
            }
            
            return stats;
            
            }  
       catch (SQLException ex) {
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
   
    public ArrayList<Team> retrieveTeams() {
       
        ArrayList<Team> teams = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM teams";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()){
                System.out.println(rs.getString("name"));
                //Retrieve by column name
                ArrayList<Player> members = new ArrayList<>();
                ArrayList<Double> stats = null;
                
                String member1 = rs.getString("member1");
                stats = getPlayerStats(member1);
                Player player1 = new Player(member1, stats.get(0), stats.get(1), stats.get(2));
                members.add(player1);
                
                String member2 = rs.getString("member2");
                stats = getPlayerStats(member2);
                Player player2 = new Player(member2, stats.get(0), stats.get(1), stats.get(2));
                members.add(player2);
                
                String member3 = rs.getString("member3");
                stats = getPlayerStats(member3);
                Player player3 = new Player(member3, stats.get(0), stats.get(1), stats.get(2));
                members.add(player3);
                
                String member4 = rs.getString("member4");
                stats = getPlayerStats(member4);
                Player player4 = new Player(member4, stats.get(0), stats.get(1), stats.get(2));
                members.add(player4);
                
                String member5 = rs.getString("member5");
                stats = getPlayerStats(member5);
                Player player5 = new Player(member5, stats.get(0), stats.get(1), stats.get(2));
                members.add(player5);
                
                //members.remove(members.size() - 1); //due to manner of input, an empty space at the end is created, this truncates this
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
   
   public void addPouleWin(Team team) {
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
   
   public void addBracketWin(Bracket bracket, int teamNr) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            String query = "";
            
            if (teamNr == 1) {
                query = "UPDATE brackets SET team1score=" + bracket.getTeam1score() + " WHERE name='" + bracket.getName() + "'";
            } else if(teamNr == 2) { //team 2
                query = "UPDATE brackets SET team2score=" + bracket.getTeam2score() + " WHERE name='" + bracket.getName() + "'";
            }
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
   
   public void removeTeam(Team team){
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();        
            
            String query = "DELETE FROM teams WHERE name = '" + team.getName() + "'";
            System.out.println(query);
            stmt.executeUpdate(query);
        }
        catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
   }
       finally {
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
