package com.urmanicrobert.sitovaAnalyza;

import javax.swing.*;
import java.util.*;

public class Task {
    private char taskCode;
    private String taskName;
    private List<String> taskPredecessors;
    private int timeRequirement;
    private int resources;

    public Task(JTextField[] jTextFields) throws LetterOnlyException, NegativeNumberException{
        taskCode = jTextFields[0].getText().charAt(0);
        taskName = jTextFields[1].getText();

        // TODO (asi vyřešeno) Exception in thread "AWT-EventQueue-0" java.lang.StringIndexOutOfBoundsException: String index out of range: 0 když nepošleme žádné údaje, asi by to chtělo další exception do bloku

        if (jTextFields[2].getText().length() > 0){
            // System.out.println("This has length more than 0: " + jTextFields[2].getText() + " nama: " + jTextFields[0].getText());
            taskPredecessors = new ArrayList<>(Arrays.asList(jTextFields[2].getText().trim().toUpperCase(Locale.ROOT).split(", |, |,")));
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
                ", taskName='" + taskName + '\'' +
                ", taskPredecessors=" + taskPredecessors +
                ", timeRequirement=" + timeRequirement +
                ", resources=" + resources +
                '}';
    }

    public static HashMap<Integer, List<Task>> uzloUzlovaMatice(List<Task> listOfTasks){
        HashMap<Integer, List<Task>> mapOfTasksOrderedByRank = new HashMap<>();
        List<Task> taskRank = new LinkedList<>();
        List<String> taskRankString = new LinkedList<>();
        List<Task> copyList = new ArrayList<>(listOfTasks);

        int i = 0;
        while (!listOfTasks.isEmpty()){
            System.out.println("Size of list of tasks: " + listOfTasks.size());
            System.out.println("HERE IS LIST OF TASKS: " + listOfTasks);
            for (int j = 0; j < listOfTasks.size(); j++) {
                if (listOfTasks.get(j).taskPredecessors.isEmpty()){
                    System.out.println("This task has no predecessors: " + listOfTasks.get(j).taskCode);
                    taskRankString.add(String.valueOf(listOfTasks.get(j).taskCode));
                    taskRank.add(listOfTasks.get(j));
                }
            }
            System.out.println("This list has no predecessors: " + taskRank);
            System.out.println("HERE IS A LIST OF TASKS BEFORE REMOVING EMPTY PREDECESSOR TASK: " + listOfTasks);
            listOfTasks.removeAll(taskRank);
            System.out.println("HERE IS A LIST OF TASKS AFTER REMOVING EMPTY PREDECESSOR TASK: " + listOfTasks);

            for (Task task: listOfTasks){
                System.out.println("Task in foreach loop: " + task);
                if (!task.taskPredecessors.isEmpty()) {
                    System.out.println("we are in the thing");
                    // task.taskPredecessors.remove(String.valueOf(listOfTasks.get(j).taskCode));
                    task.taskPredecessors.removeAll(taskRankString);
                    System.out.println("taskCode of predecessors to remove: " + taskRank);
                }
            }

            System.out.println("This list has no predecessors: " + taskRank);
            mapOfTasksOrderedByRank.put(i, new ArrayList<>(taskRank));
            System.out.println(mapOfTasksOrderedByRank);
            taskRank.clear();
            i++;
        }

        System.out.println(mapOfTasksOrderedByRank);

        //for (Task task: listOfTasks) {
        //    for (Task task: listOfTasks) {
        //        if (task.taskPredecessors == null){
//
        //        }
        //    }
        //}


        return mapOfTasksOrderedByRank;
    }
}
