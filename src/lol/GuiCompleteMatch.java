/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lol;

import java.awt.event.ItemEvent;
import java.util.ArrayList;
import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

/**
 *
 * @author vsonck
 */
public class GuiCompleteMatch extends javax.swing.JFrame {

    /**
     * Creates new form GuiCompleteMatch
     */
    
    public Tournament t;
    public String[] items;
    public StartGui parent;
    
    public GuiCompleteMatch(Tournament t, StartGui parent) {
        this.parent = parent;
        this.t = t;
        
        initComponents();
        
        setTitle("Matchplanner");
        
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        
        ArrayList<String> matches = new ArrayList<>();
        
        for (Match match : t.getMatchlist()) {
            if (match.getCompleted().equals("no")) {
                //items[i] = to.getMatchlist().get(i).getMatchID();
                matches.add(match.getMatchID());
            }
        }
        items = matches.toArray(new String[matches.size()]);
        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(items));
        
        String[] matchID = jComboBox1.getSelectedItem().toString().split("_");
        jButton2.setText(matchID[1] + " forfeited");
        jButton3.setText(matchID[2] + " forfeited");
        
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jComboBox1.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jComboBox1ItemStateChanged(evt);
            }
        });

        jLabel1.setText("Match to end");

        jButton1.setText("End Match");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Team 1 forfaited");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Team 2 forfaited");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(37, 37, 37)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(132, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addGap(32, 32, 32))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.t.completeMatch(jComboBox1.getSelectedItem().toString());
        parent.updateList1();
        parent.updateTable();
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        String matchID = jComboBox1.getSelectedItem().toString();
        t.forfaitMatch(matchID, matchID.split("_")[1]);
        parent.updateList1();
        parent.updateList2();
        parent.updateList3();
        parent.updateTable();
        parent.updateBrackets();
        this.dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String matchID = jComboBox1.getSelectedItem().toString();
        t.forfaitMatch(matchID, matchID.split("_")[2]);
        parent.updateList1();
        parent.updateList2();
        parent.updateList3();
        parent.updateTable();
        parent.updateBrackets();
        this.dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox1ItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jComboBox1ItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED)
        {
            String[] matchID = jComboBox1.getSelectedItem().toString().split("_");
            jButton2.setText(matchID[1] + " forfeited");
            jButton3.setText(matchID[2] + " forfeited");
        }
    }//GEN-LAST:event_jComboBox1ItemStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    // End of variables declaration//GEN-END:variables
}
