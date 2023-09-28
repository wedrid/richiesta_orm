package com.caribu.richiesta_orm.model;

import java.util.List;

public class TasksList {
    // Fields
    private List<TaskDTO> tasks;
    
    // Constructor
    public TasksList(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
    
    // Getter and setter
    public List<TaskDTO> getTasks() {
        return tasks;
    }
    
    public void setTasks(List<TaskDTO> tasks) {
        this.tasks = tasks;
    }
}