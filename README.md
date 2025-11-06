# 🧩 TaskManagerCLI

> **A full-featured Command-Line Task & Project Manager built in Java.**  
> Manage projects, organize tasks, and track deadlines — all from your terminal with style.

---

## 🧠 Description

**TaskManagerCLI** is a lightweight, object-oriented command-line application built in Java that helps developers and productivity lovers manage projects and tasks efficiently.  
It’s built using clean architecture principles — featuring serialization-based data persistence, deadline tracking, color-coded console output, and a smooth, menu-driven interface.  

Whether you’re managing your daily to-dos or handling multi-project workflows, this CLI tool keeps your focus sharp and your terminal organized.

---

## ✨ Features

### 🗂️ Project Management
- Create, update, delete, and view projects.
- Each project automatically stores all its related tasks.
- Auto-generated project IDs for better organization.

### ✅ Task Management
- Add, edit, delete, and mark tasks as **completed** or **in-progress**.
- Task attributes include:
  - **Title**
  - **Description**
  - **Priority:** `LOW`, `MEDIUM`, `HIGH`, `URGENT`
  - **Status:** `PENDING`, `IN_PROGRESS`, `COMPLETED`
  - **Due Date**

### ⏰ Deadline Tracker
- Background thread monitors due dates in real time.
- Alerts for:
  - Tasks due **today**
  - Tasks due **tomorrow**
  - **Overdue** tasks
- Can also check manually from the menu.

### 💾 Data Persistence
- Auto-saves data using **Java serialization**.
- Files stored in the `data/` folder:

- Data automatically loads when the app starts.

### 🎨 Terminal UI
- Colored output for easy readability:
- 🟢 **Completed**
- 🟡 **In Progress**
- 🔴 **Overdue**
- ⚪ **Pending**
- Clean and structured menu layout for navigation.

---

## 🧱 Project Structure

TaskManagerCLI/
├── README.md
├── data/
│ ├── projects.dat
│ ├── tasks.dat
│ └── users.dat
└── src/
├── com/taskmanager/
│ ├── Main.java # CLI entry point
│ ├── exceptions/ # Custom exception classes
│ │ ├── FileOperationException.java
│ │ ├── NotFoundException.java
│ │ └── ValidationException.java
│ ├── models/ # Core data models
│ │ ├── Project.java
│ │ └── Task.java
│ ├── services/ # Business logic
│ │ ├── ProjectService.java
│ │ ├── TaskService.java
│ │ ├── FileHandler.java
│ │ └── DeadlineManager.java
│ └── utils/ # Helper classes
│ ├── ConsoleColors.java
│ └── DateUtils.java
└── data/
└── tasks.dat



---

## ⚙️ Installation & Run

### 🔧 Requirements
- **Java 17+**  
- Any terminal or command prompt

### ▶️ Steps

1. **Clone the Repository**
   ```bash
   git clone https://github.com/<your-username>/TaskManagerCLI.git
   cd TaskManagerCLI


Compile the Source Code
javac -d out src/com/taskmanager/**/*.java


Run the Application
java -cp out com.taskmanager.Main

| Concept                         | Description                                         |
| ------------------------------- | --------------------------------------------------- |
| **Object-Oriented Programming** | Classes for Projects, Tasks, and Services           |
| **Serialization**               | Saves and loads data automatically                  |
| **Exception Handling**          | Custom error classes for validation and file issues |
| **Enums**                       | Used for Task `Status` and `Priority`               |
| **Date Handling**               | Managed with `LocalDate` and `ChronoUnit`           |
| **Multithreading**              | Real-time deadline tracking                         |

===== TASK MANAGER CLI =====

1. ➕ Create Project
2. 📂 View All Projects
3. ✏️ Edit Project
4. 🗑️ Delete Project
5. 🧩 Add Task
6. 📋 View Tasks
7. 🛠️ Edit Task
8. ❌ Delete Task
9. ✅ Mark Task Complete
10. ⏰ Check Deadlines
11. 💾 Save Data
12. 🚪 Exit

> Choose an option:
🪄 Future Enhancements

🔐 Add user login system (users.dat ready for integration)

📊 Task filtering by date, status, or priority

📤 Export project data as JSON

🖥️ GUI version (Swing or JavaFX)

🔔 Custom notification sound support


🏁 License

This project is licensed under the MIT License.
Feel free to use, modify, and share with proper credit.



                            🔥 I don’t just write code — I orbit ideas until they ignite. ⚡  
— Aman Deep
