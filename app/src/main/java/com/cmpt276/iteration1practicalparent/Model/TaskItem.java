package com.cmpt276.iteration1practicalparent.Model;

public class TaskItem {
    private int taskImage;
    private String taskName;
    private String taskDescription;

    public TaskItem(int task_Image, String task_Name, String task_Description){
        taskImage = task_Image;
        taskName = task_Name;
        taskDescription = task_Description;
    }

    public int getTaskImage(){
        return taskImage;
    }

    public String getTaskName() {
        return taskName;
    }

    public String getTaskDescription(){
        return taskDescription;
    }

}
