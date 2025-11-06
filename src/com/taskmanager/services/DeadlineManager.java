package com.taskmanager.services;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.taskmanager.models.Task;

public class DeadlineManager {

    // making object so we  access TaskService anywhere
    private TaskService taskService;

    // empty list to store all the task 
    private List<String> reminders = new ArrayList<>();

    // ScheduledExecutorService this run task automatically at fixed interval of time-using threads 
    private ScheduledExecutorService scheduler;

        //  Auto thread to check deadlines periodically
    private void startAutoReminder() {
        scheduler = Executors.newScheduledThreadPool(1);
        // 1 one thread runs 
        // Run check every 1 minute
        scheduler.scheduleAtFixedRate(() -> {
            checkUpcomingDeadlines();
        }, 0, 1, TimeUnit.MINUTES);

        // 0 means initial delay 
        // 1 means  gap of how many minutes !!! 

    }


    // Constructor to receive TaskService object
    public DeadlineManager(TaskService taskService) {
        this.taskService = taskService;
        startAutoReminder(); //  calling the function 
    }


    // Background check method — used by auto thread
    private void checkUpcomingDeadlines() {
        try {
            List<Task> allTasks = taskService.getAllTasks();
            List<String> newReminders = new ArrayList<>();

            for (Task task : allTasks) {
                if (task.getStatus() != Task.TaskStatus.COMPLETED) {
                    long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), task.getDueDate());

                    if (daysUntilDue == 1) {
                        newReminders.add("Task '" + task.getTitle() + "' is due TOMORROW!");
                    } else if (daysUntilDue == 0) {
                        newReminders.add("Task '" + task.getTitle() + "' is due TODAY!");
                    } else if (daysUntilDue < 0) {
                        newReminders.add(" Task '" + task.getTitle() + "' is " + Math.abs(daysUntilDue) + " days OVERDUE!");
                    }
                }
            }

            // Add only new reminders (to avoid repeating same prints)
            for (String reminder : newReminders) {
                if (!reminders.contains(reminder)) {
                    reminders.add(reminder);
                    System.out.println(reminder); // auto print in background
                }
            }

        } catch (Exception e) {
            System.err.println("Error in auto reminder: " + e.getMessage());
        }
    }

    // method (when user calls it directly)
    public void checkDeadlines() {
        reminders.clear();
        List<Task> allTasks = taskService.getAllTasks();

        for (Task task : allTasks) {
            if (task.getStatus() != Task.TaskStatus.COMPLETED) {
                long daysUntilDue = ChronoUnit.DAYS.between(LocalDate.now(), task.getDueDate());

                if (daysUntilDue == 1) {
                    reminders.add(" Task '" + task.getTitle() + "' is due TOMORROW!");
                } else if (daysUntilDue == 0) {
                    reminders.add(" Task '" + task.getTitle() + "' is due TODAY!");
                } else if (daysUntilDue < 0) {
                    reminders.add(" Task '" + task.getTitle() + "' is " + Math.abs(daysUntilDue) + " days OVERDUE!");
                }
            }
        }

        if (reminders.isEmpty()) {
            System.out.println(" No upcoming deadlines!");
        } else {
            System.out.println("\n DEADLINE REMINDERS:");
            reminders.forEach(System.out::println);
        }
    }

    // Count overdue tasks
    public int getOverdueCount() {
        int count = 0;
        List<Task> allTasks = taskService.getAllTasks();

        for (Task task : allTasks) {
            if (task.getStatus() != Task.TaskStatus.COMPLETED &&
                task.getDueDate().isBefore(LocalDate.now())) {
                count++;
            }
        }
        return count;
    }

   
    public void shutdown() {
        if (scheduler != null) {
            scheduler.shutdown();
        }
    }
}
