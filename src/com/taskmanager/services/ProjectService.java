
// creating  a logic for creating updating deleting fetching logic 
package com.taskmanager.services;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


import com.taskmanager.exceptions.NotFoundException;
import com.taskmanager.exceptions.ValidationException;

// import com.taskmanager.models.Project;
import com.taskmanager.models.Project;

public class ProjectService {

    // Map to store projects (id -> Project)
    private Map<Integer, Project> projects = new HashMap<>();

    // To assign unique IDs
    private int nextProjectId = 1;

    // 🔹 Create a new project
    public Project createProject(String name, String description) throws ValidationException {

        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("You must enter a project name!");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("You must enter a description!");
        }
        if (name.trim().length() < 3) {
            throw new ValidationException("Project name must be at least 3 characters long!");
        }

        // Create and store the project
        Project project = new Project(nextProjectId++, name.trim(), description.trim());
        projects.put(project.getId(), project);

        return project;
    }

    // 🔹 Get project by ID
    public Project getProjectById(int id) throws NotFoundException {
        Project project = projects.get(id);
        if (project == null) {
            throw new NotFoundException("Project with ID " + id + " not found!");
        }
        return project;
    }

    // 🔹 Update an existing project
    public Project updateProject(int id, String name, String description)
            throws NotFoundException, ValidationException {

        Project project = getProjectById(id); // reuse existing function

        // Only update name if provided
        if (name != null && !name.trim().isEmpty()) {
            if (name.trim().length() < 3) {
                throw new ValidationException("Project name must be at least 3 characters long!");
            }
            project.setName(name.trim());
        }

        // Only update description if provided
        if (description != null && !description.trim().isEmpty()) {
            project.setDescription(description.trim());
        }

        return project;
    }

    // 🔹 Get all projects (sorted by ID)
    public List<Project> getAllProjects() {
        return projects.values().stream()
                .sorted(Comparator.comparing(Project::getId))
                .collect(Collectors.toList());
    }

    // 🔹 Delete a project by ID
    public boolean deleteProject(int id) throws NotFoundException {
        Project project = getProjectById(id);
        projects.remove(project.getId());
        return true;
    }

    // 🔹 File handling helpers
    public Map<Integer, Project> getProjectsMap() {
        return projects;
    }

    public void setProjectsMap(Map<Integer, Project> projects) {
        this.projects = projects;
    }

    public void setNextProjectId(int id) {
        this.nextProjectId = id;
    }

    public int getNextProjectId() {
        return nextProjectId;
    }
}
