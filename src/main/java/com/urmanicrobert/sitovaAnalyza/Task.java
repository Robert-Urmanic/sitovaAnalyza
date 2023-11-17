package com.urmanicrobert.sitovaAnalyza;

import javax.swing.*;
import java.util.*;

public class Task {
    private char taskCode;
    private String taskName;
    private List<String> taskPredecessors;
    private List<String> taskPredecessorsDuplicate;
    private int timeRequirement;
    private int resources;
    private int earliestStart = 0;
    private int earliestFinish = 0;
    private int latestStart = 0;
    private int latestFinish = 0;
    private int breakBeforeStart = 0;

    public static void main(String[] args){
    }

    public Task(JTextField[] jTextFields) throws LetterOnlyException, NegativeNumberException{
        taskCode = jTextFields[0].getText().charAt(0);
        taskName = jTextFields[1].getText();

        // TODO (asi vyřešeno) Exception in thread "AWT-EventQueue-0" java.lang.StringIndexOutOfBoundsException: String index out of range: 0 když nepošleme žádné údaje, asi by to chtělo další exception do bloku
        // TODO nesmí být více úkolů, než písmen a abeceděd
        // TODO vyjimka háčky čárky
        // TODO kontrol délky trvání času a počtu pracovníků
        if (jTextFields[2].getText().length() > 0){
            // System.out.println("This has length more than 0: " + jTextFields[2].getText() + " nama: " + jTextFields[0].getText());
            taskPredecessors = new ArrayList<>(Arrays.asList(jTextFields[2].getText().trim().toUpperCase(Locale.ROOT).split(", |, |,")));
            taskPredecessorsDuplicate = new ArrayList<>(Arrays.asList(jTextFields[2].getText().trim().toUpperCase(Locale.ROOT).split(", |, |,")));
            for (String notANumber : taskPredecessors) {
                // TODO .length check maybe not needed
                if (notANumber.length() > 0 && !((notANumber.charAt(0) >= 65 && notANumber.charAt(0) <= 90))){
                    throw new LetterOnlyException("Můžete pouze zadávat malá, nebo velká písmena: " + notANumber);
                }
            }
        } else taskPredecessors = new ArrayList<>();

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
                ", taskPredecessorsDuplicate=" + taskPredecessorsDuplicate +
                ", timeRequirement=" + timeRequirement +
                ", earliestStart=" + earliestStart +
                ", earliestFinish=" + earliestFinish +
                '}';
    }

    public static HashMap<Integer, List<Task>> uzloUzlovaMatice(List<Task> listOfTasks){
        HashMap<Integer, List<Task>> mapOfTasksOrderedByRank = new HashMap<>();
        List<Task> taskRank = new LinkedList<>();
        List<String> taskRankString = new LinkedList<>();

        int i = 0;
        while (!listOfTasks.isEmpty()){
            for (int j = 0; j < listOfTasks.size(); j++) {
                if (listOfTasks.get(j).taskPredecessors.isEmpty()){
                    taskRankString.add(String.valueOf(listOfTasks.get(j).taskCode));
                    taskRank.add(listOfTasks.get(j));
                }
            }
            listOfTasks.removeAll(taskRank);

            for (Task task: listOfTasks){
                if (!task.taskPredecessors.isEmpty()) {
                    task.taskPredecessors.removeAll(taskRankString);
                }
            }
            mapOfTasksOrderedByRank.put(i, new ArrayList<>(taskRank));
            taskRank.clear();
            taskRankString.clear();
            i++;
        }
        return mapOfTasksOrderedByRank;
    }

    public static Map<Integer, List<Task>> setTimes(Map<Integer, List<Task>> mapOfTasksOrderedByRank){ // TODO spíš return map než void
        for (int i = 0; i < mapOfTasksOrderedByRank.size(); i++) {
            for (Task taskFromMap : mapOfTasksOrderedByRank.get(i)){
                if(taskFromMap.taskPredecessorsDuplicate == null){
                    taskFromMap.earliestFinish = taskFromMap.timeRequirement;
                } else {
                    for (int j = 0; j < i; j++) {
                        for (Task taskFromMapPredecessorCheck : mapOfTasksOrderedByRank.get(j)){
                            System.out.println("Počet predecessor: " + taskFromMap.taskPredecessorsDuplicate.size());
                            for(int k = 0; k < taskFromMap.taskPredecessorsDuplicate.size(); k++) {
                                if (taskFromMapPredecessorCheck.taskCode == taskFromMap.taskPredecessorsDuplicate.get(k).charAt(0)){
                                    System.out.println("taskFromMapPredecessorCheck.earliestFinish: " + taskFromMapPredecessorCheck.earliestFinish);
                                    System.out.println("taskFromMap.earliestStart: " + taskFromMap.earliestStart);
                                }
                                if (taskFromMapPredecessorCheck.taskCode == taskFromMap.taskPredecessorsDuplicate.get(k).charAt(0) && taskFromMapPredecessorCheck.earliestFinish > taskFromMap.earliestStart) {
                                    taskFromMap.earliestStart = taskFromMapPredecessorCheck.earliestFinish;
                                    taskFromMap.earliestFinish = taskFromMap.earliestStart + taskFromMap.timeRequirement;
                                }
                            }
                        }
                    }
                }
            }
        }
        return mapOfTasksOrderedByRank;
    }
}
