/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Temp
 */
public class DatabaseHandler {
    

   

    public String user = "BINFG16";
    public String pass = "f9xff87y";
    public String url = "jdbc:mysql://mysqlha2.ugent.be/BINFG16";

 //   public String user = "root";
  //  public String pass = "";
  //  public String url = "jdbc:mysql://localhost/BINFG16";

    Connection conn = null;
    public Tournament t;
    
    
    public DatabaseHandler(Tournament t) {
        this.t = t;
    }
    
    public DatabaseHandler() {
        this.t = null;
    }
    
    public Connection createConnection(String url) {
        try {
            
            Class.forName("com.mysql.jdbc.Driver");
            
            Connection connection = DriverManager.getConnection(url,user,pass);
            
            return connection;
            
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
            
            String query = "INSERT INTO teams (name, region, member1, member2, member3, member4, member5, coach) VALUES ('" + name + "', '" + region + "', '" + members.get(0) + "', '" + members.get(1) + "', '" + members.get(2) + "', '" + members.get(3) + "', '" + members.get(4) + "', '" + coach + "')";
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
            
            String query = "";
            
            if (match.getType().startsWith("Bracket")) {
                query = "INSERT INTO bracketmatches (matchID, team1, team2, timestamp, official, bracketname, matchnr, completed, datadump) VALUES ('" + match.getMatchID() + "', '" + match.getTeam1() + "', '" + match.getTeam2() + "', '" + match.getTimeStamp() + "', null, '" + match.getType().split("_")[0] + "', " + Integer.parseInt(match.getType().split("_")[1]) + ", '" + match.getCompleted() + "', 'to be played')";
            } else if(match.getType().startsWith("Poule")) {
                if (match.getType().contains("TB")) { //tiebreaker
                    query = "INSERT INTO poulematches (matchID, team1, team2, timestamp, official, poulename, completed, tiebreaker, datadump) VALUES ('" + match.getMatchID() + "', '" + match.getTeam1() + "', '" + match.getTeam2() + "', '" + match.getTimeStamp() + "',  null, '" + match.getType().split("_")[0] + "', '" + match.getCompleted() + "', 'yes', 'to be played')";
                } else {
                    query = "INSERT INTO poulematches (matchID, team1, team2, timestamp, official, poulename, completed, tiebreaker, datadump) VALUES ('" + match.getMatchID() + "', '" + match.getTeam1() + "', '" + match.getTeam2() + "', '" + match.getTimeStamp() + "', null, '" + match.getType() + "', '" + match.getCompleted() + "', 'no', 'to be played')";
                }
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
  
  
public void updateMatch(Match match) {
      try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            String query = "";
            String official = match.getOfficial();
            if (!"".equals(official) && official != null) {
                official = "'" + official + "'";
            } else {
                official = "null";
            }
            if (match.getType().startsWith("Bracket")) {
                query = "UPDATE bracketmatches SET timestamp='" + match.getTimeStamp()+ "', official=" + official + ", winner='" + match.getWinner() + "' WHERE matchID='" + match.getMatchID() + "'";
            } else {
                query = "UPDATE poulematches SET timestamp='" + match.getTimeStamp()+ "', official=" + official + ", winner='" + match.getWinner() + "' WHERE matchID='" + match.getMatchID() + "'";
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
  
  public void updateBracket(Bracket bracket) {
      try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String team1 = bracket.getTeam1Name();
            String team2 = bracket.getTeam2Name();
            if(team1.equals("TBA")) {
                team1 = null;
            } else {
                team1 = "'" + team1 + "'";
            }
            if(team2.equals("TBA")) {
                team2 = null;
            } else {
                team2 = "'" + team2 + "'";
            }
            
            String query = "UPDATE brackets SET completed='" + bracket.getCompleted() + "', type='" + bracket.getType() + "' WHERE name='" + bracket.getName() + "'";
            String query2 = "UPDATE bracketscores SET team1=" + team1 + ", team2=" + team2 + ", team1score=" + bracket.getTeam1score() + ", team2score=" + bracket.getTeam2score() + " WHERE bracket='" + bracket.getName()+ "'";
            System.out.println(query);
            System.out.println(query2);
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
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
            String query = "INSERT INTO poules (name, completed) VALUES ('" + poule.getName() + "', 'no')";
            stmt.executeUpdate(query);
            for(Team team : poule.getTeams()){
                stmt.executeUpdate("INSERT INTO poulescores (team, poule, wins, losses, tiebreakerwins) VALUES ('" + team.getName() + "', '" + poule.getName() + "', 0, 0, 0)");
            }
            System.out.println(query);
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
                String query2 = "SELECT team FROM poulescores WHERE poule='" + rs.getString("name") + "'";
                Statement stmt2 = conn.createStatement();
                ResultSet rs2 = stmt2.executeQuery(query2);
                while (rs2.next()) {
                    String pouleTeam = rs2.getString("team");
                    for (Team storedTeam : storedTeams) {
                        if (storedTeam.getName().equals(pouleTeam)) { //team have already been added. We get the names of the teams in a certain poule, select those out of the complete list of teams and then divide thos into the right pouleobjects
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
            
            String query = "SELECT * FROM brackets LEFT JOIN bracketscores ON brackets.name=bracketscores.bracket";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                ArrayList<String> bracketMatches = new ArrayList<>();
                ArrayList<Match> storedMatches = this.retrieveBracketMatches();
                for (Match storedMatch : storedMatches) { //fore bracketmatch, store it in the arraylist
                    bracketMatches.add(storedMatch.getMatchID());
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
            //String query = "INSERT INTO brackets (name, team1, team2, matches, completed, type) VALUES('" + bracket.getName() + "', '" + bracket.getTeam1Name() + "', '" + bracket.getTeam2Name() + "', '" + matches + "', '" + bracket.getCompleted() + "', " +  bracket.getType() + ")";
            String query = "INSERT INTO brackets (name, completed, type) VALUES('" + bracket.getName() + "', '" + bracket.getCompleted() + "', " +  bracket.getType() + ")";
            String query2 = "INSERT INTO bracketscores (bracket) VALUES ('" + bracket.getName() + "')";
            System.out.println(query);
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
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
     public ArrayList<String> retrieveOfficials() {
       
        ArrayList<String> officials = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM officials";
            ResultSet rs = stmt.executeQuery(query);         
            while(rs.next()) {
                officials.add(rs.getString("name"));                
            }
            return officials;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen officials: " + ex);
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
            
            String query = "SELECT * FROM teams LEFT JOIN poulescores ON teams.name=poulescores.team";
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
                Team team = new Team(rs.getString("name"), rs.getString("region"), rs.getString("coach"), rs.getDouble("barons"), rs.getDouble("gold"), rs.getDouble("dragons"), members, rs.getInt("wins"), rs.getInt("losses"), rs.getInt("tiebreakerwins"), rs.getInt("tiebreakerlosses"));
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
    
    public void updateTeam(Team team) {
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE teams SET region='" + team.getRegion()+ "', coach='" + team.getCoach()+ "', poulewins=" + team.getPouleWins()+ ", tiebreakerwins=" + team.getTieBreakerWins() + ", member1='" + team.getMembers().get(0) + "', member2='"  + team.getMembers().get(1)+ "', member3='"  + team.getMembers().get(2) + "', member4='"  + team.getMembers().get(3) + "', member5='"  + team.getMembers().get(4)+ "' WHERE name='" + team.getName() + "'";
            String query2 = "UPDATE poulescores SET wins=" + team.getPouleWins() + ", losses=" + team.getPouleLosses() + ", tiebreakerwins=" + team.getTieBreakerWins() + " WHERE team='" + team.getName() + "'";
            System.out.println(query);
            stmt.executeUpdate(query);
            System.out.println(query2);
            stmt.executeUpdate(query2);
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
    
   public void storePlayer(Player player) {
        try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement();

            String query = "INSERT INTO players (name) VALUES('" + player.getName() + "')"; 
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
    
    public void updatePlayerStats(Player player) {
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE players SET KDA=" + player.getKDA_ratio() + ", KP=" + player.getKill_part() + ", CS = " + player.getCS_ratio() + " WHERE name='" + player.getName()+ "'";
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
   
    public void addPouleWin(Team team) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE poulescores SET wins=" + team.getPouleWins() + " WHERE team='" + team.getName()+ "'";
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
  public void storeOfficial(String naam){
      try {            
            conn = createConnection(url);
            Statement stmt = conn.createStatement();

            String query = "INSERT INTO officials (name) VALUES('" + naam + "')"; 
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
  
  public void removeOfficial(String naam){
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();        
            
            String query = "DELETE FROM officials WHERE name = '" + naam + "'";
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
    
    public void addPouleLoss(Team team) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE poulescores SET losses=" + team.getPouleLosses()+ " WHERE team='" + team.getName()+ "'";
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
    
    public void addTieBreakerWin(Team team) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE poulescores SET tiebreakerwins=" + team.getTieBreakerWins() + " WHERE team='" + team.getName()+ "'";
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
    
    public void addTieBreakerLoss(Team team) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();           
            
            String query = "UPDATE poulescores SET tiebreakerlosses=" + team.getTieBreakerWins() + " WHERE team='" + team.getName()+ "'";
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
                query = "UPDATE bracketscores SET team1score=" + bracket.getTeam1score() + " WHERE name='" + bracket.getName() + "'";
            } else if(teamNr == 2) { //team 2
                query = "UPDATE bracketscores SET team2score=" + bracket.getTeam2score() + " WHERE name='" + bracket.getName() + "'";
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
   
    public void setCompleted(Match match, String matchDump, String timestamp) {
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            String query = "";
            if (match.getType().startsWith("Bracket")) {
                query = "UPDATE bracketmatches SET completed='yes', timestamp='" + timestamp + "', datadump='" + matchDump + "' WHERE matchID='" + match.getMatchID()+ "'";
            } else {
                query = "UPDATE poulematches SET completed='yes', timestamp='" + timestamp + "', datadump='" + matchDump + "' WHERE matchID='" + match.getMatchID()+ "'";
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
   
    public ArrayList<Match> retrieveBracketMatches() {
        ArrayList<Match> matches = new ArrayList<>();
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM bracketmatches";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                Match match = new Match(rs.getString("matchID"), rs.getString("team1"), rs.getString("team2"), rs.getString("winner"), rs.getString("timestamp"), rs.getString("bracketname") + "_" + rs.getString("matchnr"), rs.getString("official"), rs.getString("completed"), "no");
                matches.add(match);
            }
            return matches;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen matches: " + ex);
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
    
    public ArrayList<Match> retrievePouleMatches() {
        ArrayList<Match> matches = new ArrayList<>();
       
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT * FROM poulematches";
            ResultSet rs = stmt.executeQuery(query);
            
            while(rs.next()){
                String type = rs.getString("poulename");
                if (rs.getString("tiebreaker").equals("yes")) {
                    type += "_TB";
                }
                Match match = new Match(rs.getString("matchID"), rs.getString("team1"), rs.getString("team2"), rs.getString("winner"), rs.getString("timestamp"), type, rs.getString("official"), rs.getString("completed"), rs.getString("tiebreaker"));
                matches.add(match);
            }
            return matches;
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij ophalen matches: " + ex);
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
            
            String query2 = "DELETE FROM poulematches";
            String query = "DELETE FROM bracketmatches";
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij resetten matches: " + ex);
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
            
            String query2 = "DELETE FROM poules";
            String query = "DELETE FROM poulescores";
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij resetten poules: " + ex);
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
            
            String query2 = "DELETE FROM brackets";
            String query = "DELETE FROM bracketscores";
            System.out.println(query);
            stmt.executeUpdate(query);
            stmt.executeUpdate(query2);
            
        } catch (SQLException ex) {
            System.out.println("Probleem bij resetten brackets: " + ex);
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
            String query = "UPDATE players SET KDA=0, KP=0, CS=0";
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
            String query = "DELETE FROM poulescores WHERE team = '" + team.getName() + "'";
           // String query2 = "DELETE FROM bracketscores WHERE team = '" + team.getName() + "'";
            String query1 = "DELETE FROM teams WHERE name = '" + team.getName() + "'";            
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
   
    public HashMap<String, HashMap<String, String>> getMatchDump(String matchID) {
        try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            String query = "SELECT datadump FROM matches WHERE matchID = '" + matchID + "'";
            ResultSet rs = stmt.executeQuery(query);
            while(rs.next()) {
                if (!"to be played".equals(rs.getString("datadump"))) {
                    HashMap<String, HashMap<String, String>> matchSumm = new HashMap<>();
                    
                    String dump = rs.getString("datadump");
                    
                    dump = dump.substring(1, dump.length() - 1); //substring is to get rid of the brackets
                    
                    for (String player : dump.split(", ")) {
                        HashMap<String, String> stats = new HashMap<>();
                        String[] nameStats = player.split("=");
                        System.out.println(nameStats[0]);
                        //matchSumm.put(player, stats);
                    }
                    
                    return matchSumm;
                    
                }
            }
            
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
       
        return null;
   }
    
    
    public ResultSet CustomSQL(String query){
       try {
            conn = createConnection(url);
            Statement stmt = conn.createStatement();
            
            System.out.println(query);
            ResultSet rs = stmt.executeQuery(query);
            
            return rs;
        }
        catch (SQLException ex) {
            System.out.println("Something went wrong with the database query: " + ex);
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
}
