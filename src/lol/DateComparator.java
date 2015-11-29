/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Sonck
 */
class DateComparator implements Comparator<String> {

    @Override
    public int compare(String a, String b) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy MM dd");
            Date c = sdf.parse(a);
            Date d = sdf.parse(b);
            
            if(c.before(d)) {
                return -1;
            } else {
                return 1;
            }
            
        } catch (ParseException ex) {
            System.out.println("Wrong dateformat! " + ex);
            return 0;
        }
    }
}
