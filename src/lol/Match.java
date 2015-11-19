/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author stephan
 */
public class Match implements Comparable<Match> {
    
    private String matchID;
    private String team1;
    private String team2;
    private String type;
    private String timeStamp;
    private String official;
    
    //constructor

    public Match (String team1, String team2, String type, String official)
    {
        this.matchID = team1 + "_" + team2 + "_" + type;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = null;
        this.official = official;
        this.type = null;
    }
    
    public Match(String matchID, String team1, String team2, String timeStamp, String type, String official) {
        this.matchID = matchID;
        this.team1 = team1;
        this.team2 = team2;
        this.timeStamp = timeStamp;
        this.official = official;
        this.type = type;
    }
    
    //getter

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
    
    //setter

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
    
    @Override
    public String toString() {
        return matchID;
    }

    @Override
    public int compareTo(Match o) {
        if (o.getTimeStamp() == null || "null".equals(o.getTimeStamp()) || this.getTimeStamp() == null || "null".equals(this.getTimeStamp())) {
            return -1;
        } else {
            try {
                System.out.println(o.getTimeStamp());
                DateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                Calendar date1 = Calendar.getInstance();
                date1.setTime(sdf.parse(o.getTimeStamp()));
                Calendar date2 = Calendar.getInstance();
                date2.setTime(sdf.parse(this.getTimeStamp()));
                System.out.println(date1.get(Calendar.MONTH) + " - " + date2.get(Calendar.MONTH) + " - " + date1.compareTo(date2));
                return date1.compareTo(date2);
//                if(.before()) {
//                    return  1;
//                } else {
//                    return -1;
//                }
                
            } catch (ParseException ex) {
                System.out.println("Wrong date format: " + ex);
                return -1;
            }
        }
        
    }
}
