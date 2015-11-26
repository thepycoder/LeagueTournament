/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Collections;
import static java.util.regex.Pattern.matches;
import javax.swing.SwingUtilities;

/**
 *
 * @author pieddeck
 */
public class GuiSilke extends javax.swing.JFrame {

    /**
     * Creates new form GuiSilke
     */
    
    public Tournament t;
    public ArrayList<Poule> poulelist;
    public ArrayList<Bracket> bracketlist;
    public TeamTableModel model;
    public String[] registeredTeams;
    public String[] planned;
    public String[] unplanned;
    
    public GuiSilke(Tournament t) {
        
        this.t = t;
        
        setTitle("League of Legends Esports");
        
        initComponents();
        
        this.poulelist = t.getPoulelist();
        this.bracketlist = t.getBracketlist();
        
        model = new TeamTableModel(poulelist);
        jTable1.setModel(model);
        
        updateList1();
        updateList2();
        updateList3();
        updateBrackets();
        
        
        
        
        
//        registeredTeams = new String[t.getTeamlist().size()];
//        for (int i = 0; i < t.getTeamlist().size(); i++) {
//            registeredTeams[i] = t.getTeamlist().get(i).getName();
//        }
//        
//        ArrayList<String> plannedMatches = new ArrayList<>();
//        for (Match match : t.getMatchlist()) {
//            if (match.getTimeStamp() != null) {
//                if (!match.getTimeStamp().equals("null")) {
//                    //items[i] = to.getMatchlist().get(i).getMatchID();
//                    plannedMatches.add(match.getTimeStamp() + " :    " + match.getMatchID());
//                }
//            }
//        }
//        planned = plannedMatches.toArray(new String[plannedMatches.size()]);
//        
//        jList1.setModel(new javax.swing.AbstractListModel() {
//            String[] strings = registeredTeams;
//            @Override
//            public int getSize() { return strings.length; }
//            @Override
//            public Object getElementAt(int i) { return strings[i]; }
//        });
//        
//        jList2.setModel(new javax.swing.AbstractListModel() {
//            String[] strings = planned;
//            @Override
//            public int getSize() { return strings.length; }
//            @Override
//            public Object getElementAt(int i) { return strings[i]; }
//        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane3 = new javax.swing.JScrollPane();
        jTree1 = new javax.swing.JTree();
        jScrollPane9 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jScrollPane2 = new javax.swing.JScrollPane();
        jList2 = new javax.swing.JList();
        jScrollPane4 = new javax.swing.JScrollPane();
        jList3 = new javax.swing.JList();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTextPane1 = new javax.swing.JTextPane();
        jScrollPane6 = new javax.swing.JScrollPane();
        jTextPane2 = new javax.swing.JTextPane();
        jScrollPane7 = new javax.swing.JScrollPane();
        jTextPane3 = new javax.swing.JTextPane();
        jScrollPane8 = new javax.swing.JScrollPane();
        jTextPane4 = new javax.swing.JTextPane();
        jScrollPane10 = new javax.swing.JScrollPane();
        jTextPane5 = new javax.swing.JTextPane();
        jScrollPane11 = new javax.swing.JScrollPane();
        jTextPane6 = new javax.swing.JTextPane();
        jScrollPane12 = new javax.swing.JScrollPane();
        jTextPane7 = new javax.swing.JTextPane();
        jScrollPane13 = new javax.swing.JScrollPane();
        jTextPane8 = new javax.swing.JTextPane();
        jScrollPane14 = new javax.swing.JScrollPane();
        jTextPane9 = new javax.swing.JTextPane();
        jScrollPane15 = new javax.swing.JScrollPane();
        jTextPane10 = new javax.swing.JTextPane();
        jScrollPane16 = new javax.swing.JScrollPane();
        jTextPane11 = new javax.swing.JTextPane();
        jScrollPane17 = new javax.swing.JScrollPane();
        jTextPane12 = new javax.swing.JTextPane();
        jScrollPane18 = new javax.swing.JScrollPane();
        jTextPane13 = new javax.swing.JTextPane();
        jScrollPane19 = new javax.swing.JScrollPane();
        jTextPane14 = new javax.swing.JTextPane();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenu4 = new javax.swing.JMenu();
        jMenuItem4 = new javax.swing.JMenuItem();
        jMenuItem12 = new javax.swing.JMenuItem();
        jMenuItem10 = new javax.swing.JMenuItem();
        jMenuItem11 = new javax.swing.JMenuItem();
        jMenu5 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();
        jMenuItem7 = new javax.swing.JMenuItem();
        jMenuItem8 = new javax.swing.JMenuItem();

        jScrollPane3.setViewportView(jTree1);

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jTable1.setToolTipText("");
        jScrollPane9.setViewportView(jTable1);

        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "test 1", "test 2", "test 3" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane1.setViewportView(jList1);

        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane2.setViewportView(jList2);

        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = { "Item 1", "Item 2", "Item 3", "Item 4", "Item 5" };
            public int getSize() { return strings.length; }
            public Object getElementAt(int i) { return strings[i]; }
        });
        jScrollPane4.setViewportView(jList3);

        jTextPane1.setEditable(false);
        jTextPane1.setText("TBA");
        jScrollPane5.setViewportView(jTextPane1);

        jTextPane2.setEditable(false);
        jTextPane2.setText("TBA");
        jScrollPane6.setViewportView(jTextPane2);

        jTextPane3.setEditable(false);
        jTextPane3.setText("TBA");
        jScrollPane7.setViewportView(jTextPane3);

        jTextPane4.setEditable(false);
        jTextPane4.setText("TBA");
        jScrollPane8.setViewportView(jTextPane4);

        jTextPane5.setEditable(false);
        jTextPane5.setText("TBA");
        jScrollPane10.setViewportView(jTextPane5);

        jTextPane6.setEditable(false);
        jTextPane6.setText("TBA");
        jScrollPane11.setViewportView(jTextPane6);

        jTextPane7.setEditable(false);
        jTextPane7.setText("TBA");
        jScrollPane12.setViewportView(jTextPane7);

        jTextPane8.setEditable(false);
        jTextPane8.setText("TBA");
        jScrollPane13.setViewportView(jTextPane8);

        jTextPane9.setEditable(false);
        jTextPane9.setText("TBA");
        jScrollPane14.setViewportView(jTextPane9);

        jTextPane10.setEditable(false);
        jTextPane10.setText("TBA");
        jScrollPane15.setViewportView(jTextPane10);

        jTextPane11.setEditable(false);
        jTextPane11.setText("TBA");
        jScrollPane16.setViewportView(jTextPane11);

        jTextPane12.setEditable(false);
        jTextPane12.setText("TBA");
        jScrollPane17.setViewportView(jTextPane12);

        jTextPane13.setEditable(false);
        jTextPane13.setText("TBA");
        jScrollPane18.setViewportView(jTextPane13);

        jTextPane14.setEditable(false);
        jTextPane14.setText("TBA");
        jScrollPane19.setViewportView(jTextPane14);

        jMenu3.setText("Team");

        jMenuItem1.setText("Add Team");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem1);

        jMenuItem2.setText("Change Team");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem2);

        jMenuItem3.setText("Remove Team");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem3);

        jMenuBar1.add(jMenu3);

        jMenu4.setText("Match");

        jMenuItem4.setText("Plan Match");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem4);

        jMenuItem12.setText("Conclude Match");
        jMenuItem12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem12ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem12);

        jMenuItem10.setText("Change Match");
        jMenuItem10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem10ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem10);

        jMenuItem11.setText("Delete all Matches");
        jMenuItem11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem11ActionPerformed(evt);
            }
        });
        jMenu4.add(jMenuItem11);

        jMenuBar1.add(jMenu4);

        jMenu5.setText("Poule");

        jMenuItem6.setText("Regenerate into 2 pools");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem6);

        jMenuItem7.setText("Regenerate into 4 pools");
        jMenuItem7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem7ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem7);

        jMenuItem8.setText("Regenerate into 8 pools");
        jMenuItem8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem8ActionPerformed(evt);
            }
        });
        jMenu5.add(jMenuItem8);

        jMenuBar1.add(jMenu5);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.DEFAULT_SIZE, 518, Short.MAX_VALUE)
                    .addComponent(jScrollPane2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jScrollPane6)
                            .addComponent(jScrollPane7)
                            .addComponent(jScrollPane8)
                            .addComponent(jScrollPane10)
                            .addComponent(jScrollPane11)
                            .addComponent(jScrollPane12)
                            .addComponent(jScrollPane13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane14)
                            .addComponent(jScrollPane15)
                            .addComponent(jScrollPane16)
                            .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 150, Short.MAX_VALUE)
                            .addComponent(jScrollPane18, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 187, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane2)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                    .addComponent(jScrollPane4))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane9, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGap(25, 25, 25)
                                                .addComponent(jScrollPane18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGroup(layout.createSequentialGroup()
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(114, 114, 114)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jScrollPane10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(jScrollPane12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        GuiTeam gt = new GuiTeam(t, this);
        gt.show();
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        GuiPlanMatch jc = new GuiPlanMatch(t, this);
        jc.show();
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem7ActionPerformed
        t.generatePoules(t.getTeamlist(), 4);
        updateTable();
        t.resetMatches();
        t.generatePouleMatches();
        updateList2();
        updateList3();
        updateBrackets();
    }//GEN-LAST:event_jMenuItem7ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        t.generatePoules(t.getTeamlist(), 2);
        updateTable();
        t.resetMatches();
        t.generatePouleMatches();
        updateList2();
        updateList3();
        updateBrackets();
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void jMenuItem8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem8ActionPerformed
        t.generatePoules(t.getTeamlist(), 8);
        updateTable();
        t.resetMatches();
        t.generatePouleMatches();
        updateList2();
        updateList3();
        updateBrackets();
    }//GEN-LAST:event_jMenuItem8ActionPerformed

    private void jMenuItem11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem11ActionPerformed
        t.resetMatches();
        updateList2();
        updateList3();
    }//GEN-LAST:event_jMenuItem11ActionPerformed

    private void jMenuItem12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem12ActionPerformed
        GuiCompleteMatch gcm = new GuiCompleteMatch(t, this);
        gcm.show();
    }//GEN-LAST:event_jMenuItem12ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        GuiChangeTeam jc = new GuiChangeTeam(t, this);
        jc.show();
    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        GuiRemoveTeam rt = new GuiRemoveTeam (t, this);
        rt.show();
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem10ActionPerformed
        GuiChangeMatch k = new GuiChangeMatch(t);
        k.show();
    }//GEN-LAST:event_jMenuItem10ActionPerformed

        
    public void updateTable() {
        jTable1.setModel(new TeamTableModel(t.getPoulelist()));
        jTable1.repaint();
    }
    
    public void updateList1() {
        
        registeredTeams = new String[t.getTeamlist().size()];
        for (int i = 0; i < t.getTeamlist().size(); i++) {
            registeredTeams[i] = t.getTeamlist().get(i).getName();
        }
        
        jList1.setModel(new javax.swing.AbstractListModel() {
            String[] strings = registeredTeams;
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        jList1.repaint();
    }
    
    public void updateList2() {
        
        ArrayList<String> plannedMatches = new ArrayList<>();
        ArrayList<Match> matchlist = t.getMatchlist();
        Collections.sort(t.getMatchlist());
        for (Match match : matchlist) {
            if (!(match.getTimeStamp() == null || match.getTimeStamp().equals("null") || match.getCompleted().equals("yes"))) {
                    //items[i] = to.getMatchlist().get(i).getMatchID();
                plannedMatches.add(match.getTimeStamp() + " :    " + match.getMatchID());
            }
        }
        planned = plannedMatches.toArray(new String[plannedMatches.size()]);
        
        jList2.setModel(new javax.swing.AbstractListModel() {
            String[] strings = planned;
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        jList2.repaint();
    }
    
    public void updateList3() {
        
        ArrayList<String> unPlannedMatches = new ArrayList<>();
        ArrayList<Match> matchlist = t.getMatchlist();
        for (Match match : matchlist) {
            if ((match.getTimeStamp() == null || match.getTimeStamp().equals("null")) && match.getCompleted().equals("no")) {
                unPlannedMatches.add(match.getMatchID());
            }
        }
        unplanned = unPlannedMatches.toArray(new String[unPlannedMatches.size()]);
        
        jList3.setModel(new javax.swing.AbstractListModel() {
            String[] strings = unplanned;
            @Override
            public int getSize() { return strings.length; }
            @Override
            public Object getElementAt(int i) { return strings[i]; }
        });
        
        jList3.repaint();
    }
    
    public void updateBrackets() {
        ArrayList<Bracket> quarterFinals = new ArrayList<>();
        ArrayList<Bracket> semiFinals = new ArrayList<>();
        ArrayList<Bracket> finals = new ArrayList<>();
        
        for (Bracket bracket : t.getBracketlist()) {
            switch (bracket.getType()){
                case 4: quarterFinals.add(bracket);
                break;
                case 2: semiFinals.add(bracket);
                break;
                case 1: finals.add(bracket);
                break;
            }
        }
        
//        System.out.println(t.getBracketlist());
//        System.out.println(quarterFinals);
//        System.out.println(semiFinals);
        
        if (!quarterFinals.isEmpty()) {
            jTextPane1.setText(quarterFinals.get(0).getTeam1score() + " - " + quarterFinals.get(0).getTeam1Name());
            jTextPane2.setText(quarterFinals.get(0).getTeam2score() + " - " + quarterFinals.get(0).getTeam2Name());
            jTextPane3.setText(quarterFinals.get(1).getTeam1score() + " - " + quarterFinals.get(1).getTeam1Name());
            jTextPane4.setText(quarterFinals.get(1).getTeam2score() + " - " + quarterFinals.get(1).getTeam2Name());
            jTextPane5.setText(quarterFinals.get(2).getTeam1score() + " - " + quarterFinals.get(2).getTeam1Name());
            jTextPane6.setText(quarterFinals.get(2).getTeam2score() + " - " + quarterFinals.get(2).getTeam2Name());
            jTextPane7.setText(quarterFinals.get(3).getTeam1score() + " - " + quarterFinals.get(3).getTeam1Name());
            jTextPane8.setText(quarterFinals.get(3).getTeam2score() + " - " + quarterFinals.get(3).getTeam2Name());
        } else {
            jTextPane1.setText("0 - TBA");
            jTextPane2.setText("0 - TBA");
            jTextPane3.setText("0 - TBA");
            jTextPane4.setText("0 - TBA");
            jTextPane5.setText("0 - TBA");
            jTextPane6.setText("0 - TBA");
            jTextPane7.setText("0 - TBA");
            jTextPane8.setText("0 - TBA");
        }
        if (!semiFinals.isEmpty()) {
            System.out.println(semiFinals);
            jTextPane9.setText(semiFinals.get(0).getTeam1score() + " - " + semiFinals.get(0).getTeam1Name());
            jTextPane10.setText(semiFinals.get(0).getTeam2score() + " - " + semiFinals.get(0).getTeam2Name());
            jTextPane11.setText(semiFinals.get(1).getTeam1score() + " - " + semiFinals.get(1).getTeam1Name());
            jTextPane12.setText(semiFinals.get(1).getTeam2score() + " - " + semiFinals.get(1).getTeam2Name());
        } else {
            jTextPane1.setText("0 - TBA");
            jTextPane2.setText("0 - TBA");
            jTextPane3.setText("0 - TBA");
            jTextPane4.setText("0 - TBA");
        }
        if (!finals.isEmpty()) {
            jTextPane13.setText(finals.get(0).getTeam1score() + " - " + finals.get(0).getTeam1Name());
            jTextPane14.setText(finals.get(0).getTeam2score() + " - " + finals.get(0).getTeam2Name());
        } else {
            jTextPane13.setText("0 - TBA");
            jTextPane14.setText("0 - TBA");
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JList jList1;
    private javax.swing.JList jList2;
    private javax.swing.JList jList3;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenu jMenu5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem10;
    private javax.swing.JMenuItem jMenuItem11;
    private javax.swing.JMenuItem jMenuItem12;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JMenuItem jMenuItem7;
    private javax.swing.JMenuItem jMenuItem8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane15;
    private javax.swing.JScrollPane jScrollPane16;
    private javax.swing.JScrollPane jScrollPane17;
    private javax.swing.JScrollPane jScrollPane18;
    private javax.swing.JScrollPane jScrollPane19;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JScrollPane jScrollPane9;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextPane jTextPane1;
    private javax.swing.JTextPane jTextPane10;
    private javax.swing.JTextPane jTextPane11;
    private javax.swing.JTextPane jTextPane12;
    private javax.swing.JTextPane jTextPane13;
    private javax.swing.JTextPane jTextPane14;
    private javax.swing.JTextPane jTextPane2;
    private javax.swing.JTextPane jTextPane3;
    private javax.swing.JTextPane jTextPane4;
    private javax.swing.JTextPane jTextPane5;
    private javax.swing.JTextPane jTextPane6;
    private javax.swing.JTextPane jTextPane7;
    private javax.swing.JTextPane jTextPane8;
    private javax.swing.JTextPane jTextPane9;
    private javax.swing.JTree jTree1;
    // End of variables declaration//GEN-END:variables
}
