/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.util.Date;

/**
 *
 * @author stephan
 */
public class Match implements Comparable<Match> {
    
    private String matchID;
    private String team1;
    private String team2;
    private String winner;
    private String type;
    private String timeStamp;
    private String official;
    private String completed;
    private String tieBreaker;
    private int killsTeam1;
    private int killsTeam2;
    private int goldTeam1;
    private int goldTeam2;
    private int towersTeam1;
    private int towersTeam2;
    
    //constructor

    public Match (String team1, String team2, String type, String official)
    {
        this.matchID = type + "_" + team1 + "_" + team2;
        this.team1 = team1;
        this.team2 = team2;
        this.winner = null;
        this.timeStamp = "null";
        this.official = official;
        this.type = type;
        this.completed = "no";
        this.killsTeam1 = 0;
        this.killsTeam2 = 0;
        this.goldTeam1 = 0;
        this.goldTeam2 = 0;
        this.towersTeam1 = 0;
        this.towersTeam2 = 0;
    }
    
    public Match(String matchID, String team1, String team2, String winner, String timeStamp, String type, String official, String completed, String tieBreaker) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.winner = winner;
        this.timeStamp = timeStamp;
        this.official = official;
        this.type = type;
        this.completed = completed;
        this.tieBreaker = tieBreaker;
    }

    public Match(String matchID, String team1, String team2, String winner, String type, String timeStamp, String official, String completed, String tieBreaker, int killsTeam1, int killsTeam2, int goldTeam1, int goldTeam2, int towersTeam1, int towersTeam2) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.winner = winner;
        this.type = type;
        this.timeStamp = timeStamp;
        this.official = official;
        this.completed = completed;
        this.tieBreaker = tieBreaker;
        this.killsTeam1 = killsTeam1;
        this.killsTeam2 = killsTeam2;
        this.goldTeam1 = goldTeam1;
        this.goldTeam2 = goldTeam2;
        this.towersTeam1 = towersTeam1;
        this.towersTeam2 = towersTeam2;
    }

    public int getKillsTeam1() {
        return killsTeam1;
    }

    public int getKillsTeam2() {
        return killsTeam2;
    }

    public int getGoldTeam1() {
        return goldTeam1;
    }

    public int getGoldTeam2() {
        return goldTeam2;
    }

    public int getTowersTeam1() {
        return towersTeam1;
    }

    public int getTowersTeam2() {
        return towersTeam2;
    }
    
    
    

    public String getWinner() {
        return winner;
    }
    
    public String getTieBreaker() {
        return tieBreaker;
    }
    public String getMatchID() {
        return matchID;
    }

    public String getTeam1() {
        return team1;
    }

    public String getTeam2() {
        return team2;
    }

    public String getTimeStamp() {
        return timeStamp;
    }
    
    public String getOfficial () {
        return official;
    }

    public String getCompleted() {
        return completed;
    }
    
    
    
    public void setTieBreaker(String tieBreaker) {
        this.tieBreaker = tieBreaker;
    }

    public void setKillsTeam1(int killsTeam1) {
        this.killsTeam1 = killsTeam1;
    }

    public void setKillsTeam2(int killsTeam2) {
        this.killsTeam2 = killsTeam2;
    }

    public void setGoldTeam1(int goldTeam1) {
        this.goldTeam1 = goldTeam1;
    }

    public void setGoldTeam2(int goldTeam2) {
        this.goldTeam2 = goldTeam2;
    }

    public void setTowersTeam1(int towersTeam1) {
        this.towersTeam1 = towersTeam1;
    }

    //setter
    public void setTowersTeam2(int towersTeam2) {
        this.towersTeam2 = towersTeam2;
    }

    public void setWinner(String winner) {
        this.winner = winner;
    }

    public void setMatchID(String matchID) {
        this.matchID = matchID;
    }

    public void setTeam1(String team1) {
        this.team1 = team1;
    }

    public void setTeam2(String team2) {
        this.team2 = team2;
    }

    public void setTimeStamp(String timeStamp) {
        this.timeStamp = timeStamp;
    }
    
    public void setOfficial (String official) {
        this.official = official;
    }

    void setTimeStamp(Date date) {
        this.timeStamp = date.toString();
    }

    public String getType() {
        return this.type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCompleted(String completed) {
        this.completed = completed;
    }
    
    @Override
    public String toString() {
        return matchID + " " + team1 + team2;
    }
    
    @Override
    public boolean equals(Object o) {
        Match match = (Match) o;
        if (match == null) {
            return false;
        } else if (match.getMatchID().equals(this.matchID)) {
            return true;
        } else {
            return false;
        }
    }
    
    @Override
    public int compareTo(Match o) {
//        if (o.getTimeStamp() == null || "null".equals(o.getTimeStamp()) || this.getTimeStamp() == null || "null".equals(this.getTimeStamp())) {
//            return -1;
//        } else {
//            try {
//                //System.out.println(o.getTimeStamp());
//                DateFormat sdf = new SimpleDateFormat("yyyy MM dd");
//                Calendar date1 = Calendar.getInstance();
//                date1.setTime(sdf.parse(o.getTimeStamp()));
//                Calendar date2 = Calendar.getInstance();
//                date2.setTime(sdf.parse(this.getTimeStamp()));
//                //System.out.println(date1.get(Calendar.MONTH) + " - " + date2.get(Calendar.MONTH) + " - " + date1.compareTo(date2));
//                return date1.compareTo(date2);
////                if(.before()) {
////                    return  1;
////                } else {
////                    return -1;
////                }
//                
//            } catch (ParseException ex) {
//                System.out.println("Wrong date format: " + ex);
//                return -1;
//            }
//        }
    
        return this.getMatchID().compareTo(o.getMatchID());
        
    }
}
