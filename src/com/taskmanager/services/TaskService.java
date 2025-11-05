
package com.taskmanager.services;

import java.time.LocalDate;

import com.taskmanager.exceptions.NotFoundException;
import com.taskmanager.exceptions.ValidationException;
import com.taskmanager.models.Project;
import com.taskmanager.models.Task;
import com.taskmanager.utils.DateUtils;

class TaskService {

    private ProjectService projectService;

    // now making the int variable for indexing dude
    private int nextTaskId = 1;

    // making the constructor to link this project services
    public TaskService(ProjectService projectService) {
        this.projectService = projectService;

    }

    // now creating the own task
    public Task addTaskToProject(int projectId, String title, String description, Task.Priority priority,
            LocalDate dueDate) throws ValidationException, NotFoundException {

        // simple check for title dude !!
        if (title == null || title.trim().isEmpty()) {
            throw new ValidationException("You must have to enter the title  ");

        }
        if (dueDate == null || DateUtils.isfurtureDate(dueDate)) {
            throw new ValidationException("Due date must be in future");
        }
        // find the project where task will be added
        Project project = projectService.getProjectById(projectId);

        // now you have to create a task dude !!!

        Task task = new Task(nextTaskId++, title.trim(), description, priority, dueDate, projectId);

        // add task to the project's task title ;
        project.addTask(task);
        return task;
    }

    // 

}