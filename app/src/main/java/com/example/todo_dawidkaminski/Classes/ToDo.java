package com.example.todo_dawidkaminski.Classes;

import java.util.Date;

public class ToDo {

    private int id;
    private String title;
    private String description;
    private Boolean completed;
    private ToDoDueDate due;

    public ToDo(){ }

    public ToDo (int id, String title, String description, Boolean completed, ToDoDueDate due) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.completed = completed;
        this.due = due;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getCompletionStatus() {
        return completed;
    }

    public void setCompletionStatus(Boolean completed) {
        this.completed = completed;
    }

    public ToDoDueDate getDueDate() { return due; }

    public void setDueDate(ToDoDueDate due) { this.due = due; };
}
