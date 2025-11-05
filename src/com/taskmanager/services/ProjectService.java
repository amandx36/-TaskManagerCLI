
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

    // defining the data type for storing the data - type INteger for unique id and
    // Project data type
    private Map<Integer, Project> projects = new HashMap<>();

    private int nextProjectId = 1;
    // used a unique id dude !!!

    // creating a function for creating task dude !!!

    public Project createProject(String name, String description) throws ValidationException {

        if (name == null || name.trim().isEmpty()) {
            throw new ValidationException("YOU must have to enter the name");
        }
        if (description == null || description.trim().isEmpty()) {
            throw new ValidationException("YOU must have to enter the description bro");
        }
        if (name.length() < 3) {
            throw new ValidationException("NAME must be greater than 3 ");
        }

        // creating the object of our project !!
        Project project = new Project(nextProjectId++, name.trim(), description);

        // now put this on my map projects hashmap dude
        projects.put(project.getId(), project);
        return project;
    }

    // making a function to get the project by id dude
    public Project getProjectById(int id)throws NotFoundException{
        Project project   =  projects.get(id);
        if (project==null){
            throw new NotFoundException("project with this id" +id+" not found ");
        }
        return project ;

    }

    // now making a function to update the specific product dude !!!!!
    public Project updateProject (int id , String name ,  String description) throws NotFoundException , ValidationException {

        // making a Project data type and put the previous data type so that i can update 
        Project project  =  getProjectById(id) ;
        if (name!=null || !name.trim().isEmpty()){
            if (name.length()<3){
                throw new ValidationException("You must have to enter the  of lenghth more than 3 words ");
            }
            project.setName(name.trim());

        }

        // now its time for the validation 
        if (description != null){
            project.setDescription(description);
        }
        return project ;
    }
    
    // function to get all the products by  id is 
    public List<Project> getAllProjects (int id ) throws NotFoundException{
        // “Take all projects from the map → convert to a stream →
// sort them by ID → and return them as a list.” steam help us to implement the method on the list like filter sorting and much more dude 
        return projects.values().stream().sorted(Comparator.comparing(Project::getId)).collect(Collectors.toList()) ;
    }
    // function for deleting the project with the id 

    public boolean deleteProject (int id ) throws NotFoundException{
        Project project = getProjectById(id) ;
        if (project==null) {
            throw new NotFoundException("No project with id "+id+"found");
        }
        projects.remove(id);
        return true ;

    }


    // function for the file handling dude !!! 
    
    public Map<Integer, Project> getProjectsMap() {
        return projects;
    }

    public void setProjectsMap(Map<Integer, Project> projects) {
        this.projects = projects;
    }

    public void setNextProjectId(int id) {
        this.nextProjectId = id;
    }

}
