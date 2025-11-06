
package com.taskmanager.services;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.taskmanager.exceptions.NotFoundException;
import com.taskmanager.exceptions.ValidationException;
import com.taskmanager.models.Project;
import com.taskmanager.models.Task;
import com.taskmanager.utils.DateUtils;

 public class TaskService {

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
        if (dueDate == null || !DateUtils.isfurtureDate(dueDate)) {
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

    // making a function to get the Task-by-Project
    private Task getTaskFromProject(Project project, int taskId) throws NotFoundException {
        List<Task> allTasks = project.getTasks();
        for (int i = 0; i < allTasks.size(); i++) {
            Task task = allTasks.get(i);
            if (task.getId() == taskId) {
                return task;
            }
        }

        // if nothing found than raise the exception dude !!
        throw new NotFoundException("Task with the id " + taskId + "not found dude");

    }

    // UPDATE task
    public Task updateTask(int projectId, int taskId, String title, String description, Task.Priority priority,
            LocalDate dueDate) throws NotFoundException, ValidationException {

        // get that project you have to update
        Project project = projectService.getProjectById(projectId);

        // get the task form the task id and project !!
        Task task = getTaskFromProject(project, taskId);

        // now for the type safety we can check the title and description !!
        if (title != null && !title.trim().isEmpty()) {
            if (title.length() < 3) {
                throw new ValidationException("title must be greater than 3 digits ");

            }
            task.setTitle(title.trim());
        }

        if (description != null) {
            task.setDescription(description);
        }
        if (priority != null) {
            task.setPriority(priority);
        }

        if (dueDate != null) {
            if (!DateUtils.isfurtureDate(dueDate)) {
                throw new ValidationException("Date must be in the future dude ");

            }
            task.setDueDate(dueDate);
        }
        return task;
    }

    // function To change the status to COMPLETED !!!

    public Task marksTaskComplete(int projectId, int taskId) throws NotFoundException {
        Project project = projectService.getProjectById(projectId);
        Task task = getTaskFromProject(project, taskId);
        task.setStatus(Task.TaskStatus.COMPLETED);
        return task;

    }

    // function to change the status to IN_PROGRESS
    public Task marksTaskInProgress(int projectId, int taskId) throws NotFoundException {
        Project project = projectService.getProjectById(projectId);
        Task task = getTaskFromProject(project, taskId);
        task.setStatus(Task.TaskStatus.IN_PROGRESS);
        return task;

    }
    // function to delete the task from the project dude !!

    public boolean deleteTask(int projectId, int taskId) throws NotFoundException {

        Project project = projectService.getProjectById(projectId);
        return project.removeTask(taskId);
    }

    // function to get all the task of one project dude !!!!
       public List<Task> getTasksByProject(int projectId) throws NotFoundException {
        Project project = projectService.getProjectById(projectId);
        return new ArrayList<>(project.getTasks());
    }

    // making getter and setter
    public void setNextTaskId(int id) {
        this.nextTaskId = id;
    }

    public int getNextTaskId() {
        return nextTaskId;
    }

public List<Task> getAllTasks() {
    List<Task> allTasks = new ArrayList<>();
    
    // Go through every project and collect its tasks
    for (Project project : projectService.getAllProjects()) {
        allTasks.addAll(project.getTasks());
    }

    return allTasks;
}
    

}