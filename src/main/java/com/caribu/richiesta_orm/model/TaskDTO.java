package com.caribu.richiesta_orm.model;

import java.time.LocalDateTime;

//record can be user from java 14 on, because the DTO should be immutable (data classes in other languages)
public class TaskDTO {
    // Fields
    private Integer id;
    private Integer userId;
    private String content;
    private boolean completed;
    private LocalDateTime created;
    
    // Constructors
    public TaskDTO(Integer id, Integer userId, String content, boolean completed, LocalDateTime created) {
        this.id = id;
        this.userId = userId;
        this.content = content;
        this.completed = completed;
        this.created = created;
    }
    
    // Getters and setters
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public Integer getUserId() {
        return userId;
    }
    
    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    
    public String getContent() {
        return content;
    }
    
    public void setContent(String content) {
        this.content = content;
    }
    
    public boolean isCompleted() {
        return completed;
    }
    
    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
    public LocalDateTime getCreated() {
        return created;
    }
    
    public void setCreated(LocalDateTime created) {
        this.created = created;
    }
}