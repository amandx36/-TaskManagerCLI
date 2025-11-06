package com.taskmanager.exceptions;

public class FileOperationException extends Exception {
    // Extending and making our own custom exception and also

    public FileOperationException(String message) {
        // calling the parent const through super
        // so i can save the error in exception class and use the function normally
        // e.getMessage()
        // otherwise i got the null error !!!
        super(message);
    }
}
