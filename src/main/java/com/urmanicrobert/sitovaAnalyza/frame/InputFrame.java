package com.urmanicrobert.sitovaAnalyza.frame;

import com.urmanicrobert.sitovaAnalyza.LetterOnlyException;
import com.urmanicrobert.sitovaAnalyza.NegativeNumberException;
import com.urmanicrobert.sitovaAnalyza.Task;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class InputFrame extends MainFrame implements Runnable{

    private int numberOfTasks;
    private JTextField[][] textFieldArray;

    InputFrame(String numberOfTasks){
        this.numberOfTasks = Integer.valueOf(numberOfTasks);
        textFieldArray = new JTextField[this.numberOfTasks][5];
    }

    private void createAndShowGUI(){
        JPanel inputPannel = new JPanel(new GridLayout(numberOfTasks + 1, 5));


        JButton submitButton = new JButton("Zpracovat informace");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<Task> listOfTasks = new ArrayList<>();
                try {
                    for (JTextField[] jTextFields : textFieldArray) {
                        listOfTasks.add(new Task(jTextFields));
                    }

                } catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(frame, "Údaje jsou špatně formátovány: " + ex.getMessage());
                } catch (LetterOnlyException ex){
                    JOptionPane.showMessageDialog(frame, "Údaje jsou špatně formátovány: " + ex.getMessage());
                } catch (NegativeNumberException ex){
                    JOptionPane.showMessageDialog(frame, "Údaje jsou špatně formátovány: " + ex.getMessage());
                }
            }
        });


        inputPannel.add(new JLabel("Kód úkolu:"));
        inputPannel.add(new JLabel("<html>Celé jméno<br/>úkolu:</html>"));
        inputPannel.add(new JLabel("<html>Kódy úkolů, <br/>které bezprostředně <br/>předchází danému úkolu:</html>"));
        inputPannel.add(new JLabel("<html>Čas vykonání <br/>daného úkolu:<html>"));
        inputPannel.add(new JLabel("Počet zdrojů:"));

        for(int i = 0; i < numberOfTasks; i++){
            inputPannel.add(textFieldArray[i][0] = new JTextField(Character.toString((char)(65 + i))));
            textFieldArray[i][0].setEditable(false);
            inputPannel.add(textFieldArray[i][1] = new JTextField());
            inputPannel.add(textFieldArray[i][2] = new JTextField());
            inputPannel.add(textFieldArray[i][3] = new JTextField());
            inputPannel.add(textFieldArray[i][4] = new JTextField());
        }

        JPanel jpForButton = new JPanel(new FlowLayout());
        jpForButton.add(submitButton);

        JPanel mainPannel = new JPanel(new BorderLayout());
        mainPannel.add(inputPannel, BorderLayout.NORTH);
        mainPannel.add(jpForButton, BorderLayout.SOUTH);

        super.settingsGUI(mainPannel);
    }

    @Override
    public void run() {
        createAndShowGUI();
    }
}
