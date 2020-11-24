package com.cmpt276.iteration1practicalparent.Model;

import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

public class TaskItem {
    private int taskImage;
    private String taskName;
    private String taskDescription;
    private int idOfChild;
    private int indexOfChildForTask;

    public TaskItem(int task_Image, String task_Name, String task_Description, int id_Of_Child, int indexOfChild){
        taskImage = task_Image;
        taskName = task_Name;
        taskDescription = task_Description;
        idOfChild = id_Of_Child;
        indexOfChildForTask = indexOfChild;
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

    public int getIdOfChild(){
        return idOfChild;
    }

    public void setChildForTask(int id_Of_Child){
        idOfChild = id_Of_Child;
    }

    public int getIndexOfChildForTask(){
        return indexOfChildForTask;
    }

    public void setIndexOfChildForTask(int indexOfChildForTask) {
        this.indexOfChildForTask = indexOfChildForTask;
    }
}
