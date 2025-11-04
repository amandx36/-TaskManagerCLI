package com.taskmanager.exceptions;

public class FileOperationException extends Exception{
    public FileOperationException (String message ){
        // calling the parent const through super so i can save the property error in exception class and use the function normally e.getMessage() otherwise i got the null error !!! 
        super(message);
    }
}