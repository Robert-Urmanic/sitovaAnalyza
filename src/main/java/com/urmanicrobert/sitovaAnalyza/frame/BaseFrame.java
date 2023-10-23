package com.urmanicrobert.sitovaAnalyza.frame;

import javax.swing.*;

public class BaseFrame{
    protected JFrame frame = new JFrame("Síťová analýza");

    public void settingsGUI(JPanel jpMain){
        // frame.getRootPane().setDefaultButton();
        frame.add(jpMain);
        JScrollPane scrollPane = new JScrollPane(jpMain);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        frame.add(scrollPane);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800,500);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
