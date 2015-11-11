/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.io.IOException;

/**
 *
 * @author Temp
 */
public class Tournament {
    
    private static String matchID = "2370414822";
    
    public static void main(String[] args) throws IOException {
        ApiHandler api = new ApiHandler();
        System.out.println(api.getMatch(matchID));
    }
}
