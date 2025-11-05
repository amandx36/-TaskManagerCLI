package com.taskmanager.models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Task {

    // public class making my own data- type using enum
    public enum TaskStatus {
        PENDING, COMPLETED, IN_PROGRESS;
    }

    public enum Priority {
        LOW, MEDIUM, HIGH, URGENT;
    }

    // making my own data-type using class !!

    private int id;
    private String title;
    private String description;
    private TaskStatus status;
    private Priority priority;
    private LocalDate dueDate;
    private LocalDate createdAt;
    private int projectId;

    // makking the constructor for that !!!!
    public Task(int id, String title, String description, Priority priority, LocalDate dueDate, int projectId) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.status = TaskStatus.PENDING;
        this.priority = priority;
        this.dueDate = dueDate;
        this.createdAt = LocalDate.now();
        this.projectId = projectId;
    }

    // making the getter and setter for getting and setting the value locally dude
    // !!!
    public int getId() {
        return id;
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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    public Priority getPriority() {
        return priority;
    }

    public void setPriority(Priority priority) {
        this.priority = priority;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public int getProjectId() {
        return projectId;
    }

    // over-writting the parent method and making my own method which is also
    // written in the parent class !!!
    @Override
    public String toString() {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyy");
        return String.format("ID: %d | %s | %s | Priority: %s | Due: %s |Status: %s ", id, title, description, priority,
                dueDate.format(formatter), status);
    }   
}