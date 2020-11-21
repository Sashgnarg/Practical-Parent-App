package com.cmpt276.iteration1practicalparent.Model;

import com.cmpt276.iteration1practicalparent.UI.ConfigureChildren.ConfigureChildren;

public class TaskItem {
    private int taskImage;
    private String taskName;
    private String taskDescription;
    private ConfigureChildrenItem taskChild;
    private int indexOfChildForTask;

    public TaskItem(int task_Image, String task_Name, String task_Description, ConfigureChildrenItem task_Child, int indexOfChild){
        taskImage = task_Image;
        taskName = task_Name;
        taskDescription = task_Description;
        taskChild = task_Child;
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

    public ConfigureChildrenItem getChildForTask(){
        return taskChild;
    }

    public int getIndexOfChildForTask(){
        return indexOfChildForTask;
    }

    public void setIndexOfChildForTask(int indexOfChildForTask) {
        this.indexOfChildForTask = indexOfChildForTask;
    }
}
