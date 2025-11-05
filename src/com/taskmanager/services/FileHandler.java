// concepts Serialization = converting Java objects → bytes → storing in a file.
// Deserialization = reading bytes → reconstructing Java objects.

package com.taskmanager.services;

import java.io.File;
import java.io.ObjectInputStream;

class FileHandler {

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

    public void saveDate(ProjectService projectService , Task taskservices ) throws FileOperationException {
        
    } 

}