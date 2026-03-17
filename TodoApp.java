import java.util.ArrayList;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Main Class - Application Entry Point
 * Filename: TodoApp.java
 */
public class TodoApp {
    private static final Scanner scanner = new Scanner(System.in);
    private static final TaskManager manager = new TaskManager();

    public static void main(String[] args) {
        boolean running = true;

        while (running) {
            displayHeader();
            displayMenu();
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.print("\n➤ Enter task description: ");
                    String desc = scanner.nextLine();
                    manager.addTask(desc);
                    break;
                case "2":
                    manager.viewTasks();
                    break;
                case "3":
                    manager.viewTasks();
                    System.out.print("\n➤ Enter task number to complete: ");
                    manager.completeTask(parseInput() - 1);
                    break;
                case "4":
                    manager.viewTasks();
                    System.out.print("\n➤ Enter task number to delete: ");
                    manager.deleteTask(parseInput() - 1);
                    break;
                case "5":
                    running = false;
                    displayExitMessage();
                    break;
                default:
                    System.out.println("\n❌ Invalid option. Please try again.");
            }
        }
        scanner.close();
    }

    private static void displayHeader() {
        System.out.println("\n============================================");
        System.out.println("      PROFESSIONAL TO-DO LIST MANAGER       ");
        System.out.println("           MADE BY URSHITA ROY              ");
        System.out.println("============================================");
    }

    private static void displayMenu() {
        System.out.println(" 1. [+] Add New Task");
        System.out.println(" 2. [☰] View All Tasks");
        System.out.println(" 3. [✓] Mark Task as Completed");
        System.out.println(" 4. [✘] Delete a Task");
        System.out.println(" 5. [!] Exit Application");
        System.out.print("\n👉 Choose an option: ");
    }

    private static void displayExitMessage() {
        System.out.println("\n--------------------------------------------");
        System.out.println("       THANK YOU FOR USING THE APP!         ");
        System.out.println("           MADE BY URSHITA ROY              ");
        System.out.println("--------------------------------------------");
    }

    private static int parseInput() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}

/**
 * Task Class - Data Model
 */
class Task {
    private String description;
    private boolean isCompleted;
    private String creationDate;

    public Task(String description) {
        this.description = description;
        this.isCompleted = false;
        
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMM-yyyy HH:mm");
        this.creationDate = now.format(formatter);
    }

    public String getDescription() { return description; }
    public void markAsCompleted() { this.isCompleted = true; }

    @Override
    public String toString() {
        String status = isCompleted ? "[✓] DONE  " : "[ ] PENDING";
        return String.format("%-10s | %-20s | %s", status, description, creationDate);
    }
}

/**
 * TaskManager Class - Logic Handling
 */
class TaskManager {
    private final ArrayList<Task> tasks = new ArrayList<>();

    public void addTask(String description) {
        if (!description.trim().isEmpty()) {
            tasks.add(new Task(description));
            System.out.println("✅ Task added successfully!");
        } else {
            System.out.println("⚠️ Error: Task cannot be empty.");
        }
    }

    public void viewTasks() {
        if (tasks.isEmpty()) {
            System.out.println("\n📭 Your list is currently empty.");
            return;
        }
        System.out.println("\nID  STATUS     | DESCRIPTION          | CREATED ON");
        System.out.println("--- -----------|----------------------|-----------------");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i));
        }
    }

    public void completeTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            tasks.get(index).markAsCompleted();
            System.out.println("🎯 Task marked as finished!");
        } else {
            System.out.println("❌ Error: Task not found.");
        }
    }

    public void deleteTask(int index) {
        if (index >= 0 && index < tasks.size()) {
            Task removed = tasks.remove(index);
            System.out.println("🗑️ Removed: " + removed.getDescription());
        } else {
            System.out.println("❌ Error: Invalid task number.");
        }
    }
}
