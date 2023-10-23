package com.urmanicrobert.sitovaAnalyza.frame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends BaseFrame implements Runnable{
    private JTextField numberOfTasks;

    private void createAndShowGUI(){
        JLabel prvni = new JLabel("Kolik úkolů bude projekt obsahovat?");
        numberOfTasks = new JTextField();

        JButton submitButton = new JButton();
        submitButton.setText("Další");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Thread inputFrame = new Thread(new InputFrame(numberOfTasks.getText()));
                    MainFrame.super.frame.dispose();
                    inputFrame.start();
                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "Musíte zadat číslo");
                }
            }
        });

        JPanel jp = new JPanel();

        jp.setLayout(new GridLayout(1,2));
        jp.add(prvni);
        jp.add(numberOfTasks);

        JPanel jpForButton = new JPanel(new FlowLayout());
        jpForButton.add(submitButton);

        JPanel jpMain = new JPanel();
        jpMain.setLayout(new BorderLayout());
        jpMain.add(jp, BorderLayout.NORTH);
        jpMain.add(jpForButton, BorderLayout.SOUTH);

        super.settingsGUI(jpMain);
        // frame.pack();
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}
