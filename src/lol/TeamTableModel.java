package lol;


import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Temp
 */
public class TeamTableModel extends AbstractTableModel {

        private ArrayList<Poule> poulelist;

        public TeamTableModel(ArrayList<Poule> poulelist) {
            this.poulelist = poulelist;
        }

        @Override
        public int getRowCount() {
            return poulelist.get(0).getTeams().size(); //aantal rijen is het aantal teams in de eerste poule, deze zal altijd het meeste teams bevatten
        }

        @Override
        public int getColumnCount() {
            return poulelist.size();
        }

        @Override
        public String getColumnName(int column) {
            String name = poulelist.get(column).getName();
            return name;
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            Class type = String.class;
            return type;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Poule poule = poulelist.get(columnIndex);
            Object value;
            if (rowIndex == poule.getTeams().size()) {
                value = "";
            } else {
                value = poule.getSortedTeams().get(rowIndex).getPouleWins() + " - " + poule.getSortedTeams().get(rowIndex);
            }
            
            return value;
        }            
    }        
