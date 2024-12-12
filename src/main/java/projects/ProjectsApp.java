package projects;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

import projects.entity.Project;
import projects.exception.DbException;
import projects.service.ProjectService;

public class ProjectsApp {

    private ProjectService projectService = new ProjectService();
    
    // List of menu options that the user can choose from
    private List<String> operations = List.of("1) Add a project");

    // Scanner object to read input from the user via the console
    private Scanner scanner = new Scanner(System.in);

    // This is the main entry point for the program, where everything starts
    public static void main(String[] args) {
        // I created an instance of ProjectsApp and call processUserSelections to start
        // the program
        new ProjectsApp().processUserSelections();
    }

    // This method controls the main loop of the app where the user input is
    // processed
    private void processUserSelections() {
        // 'done' is a flag that will be set to true when the user decides to exit the program
        boolean done = false;

        // The program will keep asking for input until 'done' is set to true
        while (!done) {
            try {
                // Get the user's menu selection (either a valid number or -1 to quit)
                int selection = getUserSelection();

                // Now I used a switch to handle different menu selections
                switch (selection) {
                case -1:
                    // If the user chooses -1, we exit the program
                    done = exitMenu();
                    break;

                case 1:
                    // Handle user selection 1: create a new project
                    createProject();
                    break;

                default:
                    // If the user enters a number that's not in the menu, it prints an error message
                    System.out.println("\n" + selection + " is not a valid selection. Try again.");
                }
            } catch (Exception e) {
                // If anything goes wrong, it will be caught here
                // print the error message, then ask the user to try again
                System.out.println("\nError: " + e + " Try again.");
            }
        }
    }

    // This method handles printing out the available menu options
    private void printOperations() {
        // Print a message saying these are the available selections
        // Then print each operation (menu option) one by one
        System.out.println("\nThese are the available selections. Press the Enter key to quit:");
        operations.forEach(line -> System.out.println("  " + line));
    }

    // This method gets the user's input as a number
    private int getUserSelection() {
        // First, print out the menu options
        printOperations();

        // Ask the user for input and try to convert it into an Integer
        Integer input = getIntInput("Enter a menu selection");

        // If the input is null, we return -1 to quit
        return Objects.isNull(input) ? -1 : input;
    }

    // This method tries to read an integer from the user
    private Integer getIntInput(String prompt) {
        // To handle non-numeric input gracefully, first, we get the input as a string
        String input = getStringInput(prompt);

        // If the input is null, return null
        if (Objects.isNull(input)) {
            return null;
        }

        try {
            // Try to convert the input string to an Integer
            return Integer.valueOf(input);
        } catch (NumberFormatException e) {
            // If the input can't be converted to an integer, throw an exception
            // This helps catch and handle errors for invalid input
            throw new DbException(input + " is not a valid number.");
        }
    }

    // This method tries to read a decimal from the user
    private BigDecimal getDecimalInput(String prompt) {
        // Get the string input from the user
        String input = getStringInput(prompt);

        // If the input is null, return null
        if (Objects.isNull(input)) {
            return null;
        }

        try {
            // Convert the input to a BigDecimal and set the scale to 2
            return new BigDecimal(input).setScale(2);
        } catch (NumberFormatException e) {
            // If the input can't be converted to a valid decimal, throw a DbException
            throw new DbException(input + " is not a valid decimal number.");
        }
    }

    // This method gets input from the user as a string
    private String getStringInput(String prompt) {
        // Print the prompt and wait for the user to type something
        System.out.print(prompt + ": ");
        String input = scanner.nextLine();

        // If the user input is blank, return null
        // Otherwise, trim any extra spaces from the input and return it
        return input.isBlank() ? null : input.trim();
    }
    
    private void createProject() {
        String projectName = getStringInput("Enter the project name");
        BigDecimal estimatedHours = getDecimalInput("Enter the estimated hours");
        BigDecimal actualHours = getDecimalInput("Enter the actual hours");
        Integer difficulty = getIntInput("Enter the project difficulty (1-5)");
        String notes = getStringInput("Enter the project notes");

        Project project = new Project();

        project.setProjectName(projectName);
        project.setEstimatedHours(estimatedHours);
        project.setActualHours(actualHours);
        project.setDifficulty(difficulty);
        project.setNotes(notes);

        Project dbProject = projectService.addProject(project);
        System.out.println("You have successfully created project: " + dbProject);
    }

    // This method is called when the user chooses to exit the program
    private boolean exitMenu() {
        // Print a message saying we're exiting the application
        System.out.println("\nExiting the application.");

        // Return true to stop the loop and exit the program
        return true;
    }
}

