// concepts Serialization = converting Java objects → bytes → storing in a file.
// Deserialization = reading bytes → reconstructing Java objects.

package com.taskmanager.services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

import com.taskmanager.exceptions.FileOperationException;
import com.taskmanager.models.Project;

public class FileHandler {

    // assigning the path
    public static final String DATA_FILE = "data/tasks.dat";

    // cross verify is the folder is exist or not dude

    public FileHandler() {
        File dataDir = new File("data");
        if (!dataDir.exists()) {
            dataDir.mkdirs();

        }
    }

    // making a function to load the data !!!

    // ObjectOutputStream :) riting (saving) entire Java objects to a file in binary
    // form. t’s part of Java’s serialization mechanism.

    // ObjectInputStream :) Used for reading (loading) objects from a binary file.
    // It performs the reverse of serialization — called deserialization.


        private int getNextProjectId(ProjectService projectService) {
        // Get highest ID from all projects and add 1
        return projectService.getAllProjects().stream()
                .mapToInt(Project::getId)
                .max()
                .orElse(0) + 1;
    }

    public void saveData(ProjectService projectService, TaskService taskService) throws FileOperationException {
        try {
            // Open file to write (ObjectOutputStream = saves full objects)
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(DATA_FILE));

            // Create map to store all data together
            Map<String, Object> data = new HashMap<>();
            data.put("projects", projectService.getProjectsMap());   // store all projects
            data.put("nextProjectId", getNextProjectId(projectService)); // next project ID
            data.put("nextTaskId", taskService.getNextTaskId());     // next task ID

            
            
            // Write map into file
            oos.writeObject(data);
            oos.close();

            System.out.println("Data saved successfully!");

        } catch (IOException e) {
            // wrap into custom exception
            throw new FileOperationException("Error saving data");
         
        }
    }
    
    
 @SuppressWarnings("unchecked")
    public void loadData(ProjectService projectService, TaskService taskService) throws FileOperationException {
        try {
            // Open file to read
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(DATA_FILE));

            // Read map of data
            Map<String, Object> data = (Map<String, Object>) ois.readObject();

            // Restore data into services
            projectService.setProjectsMap((Map<Integer, Project>) data.get("projects"));
            projectService.setNextProjectId((Integer) data.get("nextProjectId"));
            taskService.setNextTaskId((Integer) data.get("nextTaskId"));

            ois.close();
            System.out.println(" Data loaded successfully!");

        } catch (FileNotFoundException e) {
            // File not found on first run — not an error
            System.out.println("No saved data found. Starting fresh.");
        } catch (IOException | ClassNotFoundException e) {
            // Any file or data reading issue
            throw new FileOperationException("Error loading data");
        }
    }





    
}