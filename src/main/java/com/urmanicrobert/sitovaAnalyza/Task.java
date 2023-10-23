package com.urmanicrobert.sitovaAnalyza;

import javax.swing.*;
import java.util.Arrays;
import java.util.Locale;

public class Task {
    private char taskCode;
    private String taskName;
    private String[] taskPredecessors;
    private int timeRequirement;
    private int resources;

    public Task(JTextField[] jTextFields) throws LetterOnlyException, NegativeNumberException{
        taskCode = jTextFields[0].getText().charAt(0);
        taskName = jTextFields[1].getText();

        taskPredecessors = jTextFields[2].getText().trim().toUpperCase(Locale.ROOT).split(", |, |,");
        for (String notANumber : taskPredecessors) {
            if (!((notANumber.charAt(0) >= 65 && notANumber.charAt(0) <= 90))){
                throw new LetterOnlyException("Můžete pouze zadávat malá, nebo velká písmena: " + notANumber);
            }
        }
        timeRequirement = Integer.parseInt(jTextFields[3].getText());
        resources = Integer.parseInt(jTextFields[4].getText());
        if (timeRequirement < 0 || resources < 0){
            throw new NegativeNumberException("Časová náročnost a požadované zdroje nemohou být negativní");
        }
    }

    @Override
    public String toString() {
        return "Task{" +
                "taskCode=" + taskCode +
                ", taskName='" + taskName + '\'' +
                ", tastPredecessors=" + Arrays.toString(taskPredecessors) +
                ", timeRequirement=" + timeRequirement +
                ", resources=" + resources +
                '}';
    }
}
