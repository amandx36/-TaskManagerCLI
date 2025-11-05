
// creating  a logic for creating updating deleting fetching logic 
package com.taskmanager.services;
import java.util.HashMap;
import java.util.Map;

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
    Project project = new Project (nextProjectId++, name.trim() , description ) ;

    // now put this on my map projects hashmap dude 
    projects.put(project.getId(),project);
    return project ;
    }

}
