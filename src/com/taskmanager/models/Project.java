
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Project {

    private int id;
    private String name;
    private String description;
    private LocalDate createdAt;
    // making the custom data type Task of Task ;
    private List<Task> tasks;

    // making the custom data type dude !!!
    public Project(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.createdAt = LocalDate.now();
        this.tasks = new ArrayList<>();
    }

    // adding getter and setter
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public List<Task> getTasks() {
        return tasks;
    }

    // making the fucntion which the add the task in task list dude dude

    public void addTask(Task newWork) {
        tasks.add(newWork);

    }

    // function to remove the task dude !!
    public boolean removeTask(int taskId) {
        // remove from array
        // tasks = [Task1, Task2, Task3, Task4];

        return tasks.removeIf(task -> task.getId() == taskId);
        // here is the lambda expression to filter the tasks in the tasks list filter by
        // id
    }

    // toString() ek built-in method hai jo Object class (Java ke sabse base class)
    // mein hota hai.

    // if you write System.out.println(Project ) than you got the address dude !!
    // com.taskmanager.models.Project@4e50df2e

    // so you have to override the default behavior dude !!!
    public String toString() {
        return String.format("Project ID: %d | %s | %s | Tasks: %d",
                id, name, description, tasks.size());
    }

}