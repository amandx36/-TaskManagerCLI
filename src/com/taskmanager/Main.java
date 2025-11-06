package com.taskmanager;

import com.taskmanager.services.*;
import com.taskmanager.models.*;
import com.taskmanager.utils.*;


import java.time.LocalDate;
import java.util.List;
import java.util.Scanner;

public class Main {
    
    private static Scanner scanner = new Scanner(System.in);
    private static ProjectService projectService = new ProjectService();
    private static TaskService taskService = new TaskService(projectService);
    private static FileHandler fileHandler = new FileHandler();
    private static DeadlineManager deadlineManager = new DeadlineManager(taskService);

    public static void main(String[] args) {
        System.out.println(ConsoleColors.CYAN_BOLD + "\n Welcome to Task Manager CLI!" + ConsoleColors.RESET);

        // Load previous saved data (if any)
        try {
            fileHandler.loadData(projectService, taskService);
        } catch (Exception e) {
            System.out.println(" No saved data found. Starting fresh!");
        }

        // Save data automatically when program exits
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                fileHandler.saveData(projectService, taskService);
                deadlineManager.shutdown();
                System.out.println("\n💾 Data saved before exit!");
            } catch (Exception e) {
                System.out.println("Error during shutdown: " + e.getMessage());
            }
        }));

        // Keep showing menu until user exits
        while (true) {
            showMainMenu();
            int choice = getIntInput(" Enter choice: ");

            switch (choice) {
                case 1 -> createProject();
                case 2 -> listProjects();
                case 3 -> updateProject();
                case 4 -> deleteProject();
                case 5 -> createTask();
                case 6 -> listTasks();
                case 7 -> updateTask();
                case 8 -> deleteTask();
                case 9 -> markTaskComplete();
                case 10 -> checkDeadlines();
                case 11 -> saveData();
                case 12 -> {
                    System.out.println(ConsoleColors.CYAN + "\n Goodbye! Your data is safe!" + ConsoleColors.RESET);
                    break;
                  
                }
                default -> System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // 🧭 Show menu
    private static void showMainMenu() {
        int overdueCount = deadlineManager.getOverdueCount();
        String overdueAlert = overdueCount > 0 ? ConsoleColors.RED + " (" + overdueCount + " overdue!)" + ConsoleColors.RESET : "";

        System.out.println(ConsoleColors.BLUE_BOLD + "\n===== MAIN MENU =====" + ConsoleColors.RESET);
        System.out.println("1. ➕ Create Project");
        System.out.println("2. 📂 List Projects");
        System.out.println("3. ✏️ Edit Project");
        System.out.println("4. 🗑️ Delete Project");
        System.out.println("5. 🧩 Add Task");
        System.out.println("6. 📋 List Tasks");
        System.out.println("7. 🛠️ Edit Task");
        System.out.println("8. ❌ Delete Task");
        System.out.println("9. ✅ Mark Task Complete");
        System.out.println("10. ⏰ Check Deadlines" + overdueAlert);
        System.out.println("11. 💾 Save Data");
        System.out.println("12. 🚪 Exit");
    }

    // 🎯 Project operations
    private static void createProject() {
        System.out.println(ConsoleColors.GREEN_BOLD + "\nCreate New Project" + ConsoleColors.RESET);

        System.out.print("Enter project name: ");
        String name = scanner.nextLine();

        System.out.print("Enter description: ");
        String description = scanner.nextLine();

        try {
            Project project = projectService.createProject(name, description);
            System.out.println(" Created: " + project.getName());
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private static void listProjects() {
        List<Project> projects = projectService.getAllProjects();
        System.out.println(ConsoleColors.YELLOW_BOLD + "\n📋 Projects List" + ConsoleColors.RESET);

        if (projects.isEmpty()) {
            System.out.println("No projects yet. Create one first!");
            return;
        }
        projects.forEach(p -> System.out.println(p));
    }

    private static void updateProject() {
        listProjects();
        int id = getIntInput("Enter project ID to edit: ");

        System.out.print("New name (press Enter to skip): ");
        String name = scanner.nextLine();

        System.out.print("New description (press Enter to skip): ");
        String desc = scanner.nextLine();

        try {
            Project updated = projectService.updateProject(id, name, desc);
            System.out.println("Updated: " + updated.getName());
        } catch (Exception e) {
            System.out.println("Error " + e.getMessage());
        }
    }

    private static void deleteProject() {
        listProjects();
        int id = getIntInput("Enter project ID to delete: ");
        try {
            if (projectService.deleteProject(id)) {
                System.out.println(" Deleted successfully!");
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // 🧠 Task operations
    private static void createTask() {
        listProjects();
        int pid = getIntInput("Enter project ID: ");

        System.out.print("Task title: ");
        String title = scanner.nextLine();

        System.out.print("Description: ");
        String desc = scanner.nextLine();

        System.out.println("Priority: 1-Low, 2-Medium, 3-High, 4-Urgent");
        Task.Priority priority = getPriority(getIntInput("Choose: "));

        System.out.print("Due date (DD-MM-YYYY): ");
        String date = scanner.nextLine();

        try {
            LocalDate due = DateUtils.parseDate(date);
            Task task = taskService.addTaskToProject(pid, title, desc, priority, due);
            System.out.println(" Task added: " + task.getTitle());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void listTasks() {
        listProjects();
        int pid = getIntInput("Enter project ID: ");

        try {
            List<Task> tasks = taskService.getTasksByProject(pid);
            System.out.println(ConsoleColors.YELLOW_BOLD + "\n Tasks:" + ConsoleColors.RESET);

            if (tasks.isEmpty()) {
                System.out.println("No tasks in this project!");
                return;
            }

            tasks.forEach(t -> System.out.println(getTaskColor(t) + t + ConsoleColors.RESET));
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private static void updateTask() {
        listProjects();
        int pid = getIntInput("Project ID: ");
        int tid = getIntInput("Task ID: ");

        System.out.print("New title (skip = Enter): ");
        String title = scanner.nextLine();

        System.out.print("New description (skip = Enter): ");
        String desc = scanner.nextLine();

        System.out.println("Priority: 1-Low, 2-Med, 3-High, 4-Urgent (0=skip)");
        int p = getIntInput("Choose: ");
        Task.Priority pr = p == 0 ? null : getPriority(p);

        System.out.print("New due date (DD-MM-YYYY) or skip: ");
        String d = scanner.nextLine();
        LocalDate due = d.isEmpty() ? null : DateUtils.parseDate(d);

        try {
            Task task = taskService.updateTask(pid, tid, title, desc, pr, due);
            System.out.println("Updated: " + task.getTitle());
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    private static void deleteTask() {
        listProjects();
        int pid = getIntInput("Project ID: ");
        int tid = getIntInput("Task ID: ");
        try {
            if (taskService.deleteTask(pid, tid)) {
                System.out.println("Task deleted!");
            }
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    //  Mark task as complete
    private static void markTaskComplete() {
        listProjects();
        int pid = getIntInput("Project ID: ");
        int tid = getIntInput("Task ID: ");
        try {
            Task task = taskService.marksTaskComplete(pid, tid);
            System.out.println(" Task marked as complete: " + task.getTitle());
        } catch (Exception e) {
            System.out.println( e.getMessage());
        }
    }

    //  Check deadlines manually
    private static void checkDeadlines() {
        deadlineManager.checkDeadlines();
    }

    //  Save data manually
    private static void saveData() {
        try {
            fileHandler.saveData(projectService, taskService);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    // Helper methods
    private static int getIntInput(String msg) {
        while (true) {
            try {
                System.out.print(msg);
                return Integer.parseInt(scanner.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.println(" Enter a valid number!");
            }
        }
    }

    private static Task.Priority getPriority(int c) {
        return switch (c) {
            case 1 -> Task.Priority.LOW;
            case 2 -> Task.Priority.MEDIUM;
            case 3 -> Task.Priority.HIGH;
            case 4 -> Task.Priority.URGENT;
            default -> Task.Priority.MEDIUM;
        };
    }

    private static String getTaskColor(Task t) {
        return switch (t.getStatus()) {
            case COMPLETED -> ConsoleColors.GREEN;
            case IN_PROGRESS -> ConsoleColors.YELLOW;
            default -> t.getDueDate().isBefore(LocalDate.now()) ? ConsoleColors.RED : ConsoleColors.WHITE;
        };
    }
}