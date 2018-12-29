package app;

import app.models.Company;
import app.models.Employee;

import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Company company = new Company();

    public static void main(String[] args) {
        while (true) {
            printMenus();
        }
    }

    private static void printMenus() {
        String cmd = input("Command ('?' for help)");
        if (cmd.equals("?")) {
            System.out.println("---- What to do? ----\n" +
                    "[all] Show ALL Employees" +
                    "[add] Add new Employee\n" +
                    "[del] Delete Employee\n" +
                    "[sub] Show Employee's subordinates\n" +
                    "[man] Show Employee's manager\n" +
                    "[mans] Show Employee's manager and all managers subordinates\n" +
                    "[setman] Set Employee's new manager\n" +
                    "[q] Quit");
        } else if (cmd.equalsIgnoreCase("all")) {
            System.out.println("ALL EMPLOYEE'S:");
            company.getAllEmployees().forEach(System.out::println);
        } else if (cmd.equalsIgnoreCase("alln")) {
            System.out.println("ALL EMPLOYEE'S (NAMES):");
            company.getAllEmployees().forEach(employee ->
                    System.out.println(employee.getFirstName())
            );
        } else if (cmd.equalsIgnoreCase("add")) {
            String fullName = inputFullName("Enter new Employee's fullName");
            company.addEmployee(new Employee(fullName));
        } else if (cmd.equalsIgnoreCase("del")) {
            String fullName = inputFullName("Enter Employee, who will be deleted");
            company.removeEmployeeByName(fullName);
        } else if (cmd.equalsIgnoreCase("sub")) {
            String fullName = inputFullName("Enter Employee's name, whose subordinates you want to see");
            System.out.println("SUBORDINATES:");
            company.getSubordinatesOf(fullName).forEach(System.out::println);
        } else if (cmd.equalsIgnoreCase("man")) {
            String fullName = inputFullName("Employee's name");
            System.out.println(company.getManagerOf(fullName));
        } else if (cmd.equalsIgnoreCase("mans")) {
            String fullName = inputFullName("Employee's name");
            System.out.print("Employee's Manager: ");
            System.out.println(company.getManagerOf(fullName));
            System.out.println("-- Managers Subordinates --");
            company.getManagerSubordinateByEmployee(
                    company.getEmployeeByName(fullName)
            ).forEach(System.out::println);
        } else if (cmd.equalsIgnoreCase("setman")) {
            String fullName = inputFullName("Employee's name");
            String fullName2 = inputFullName("New Manager name");
            boolean success = company.setManagerFor(
                    fullName,
                    company.getEmployeeByName(fullName2));
            showSuccess(success);
        } else if (cmd.equalsIgnoreCase("q")) {
            System.out.println("OK, BYE!");
            System.exit(0);
        }
    }

    public static String input(String text) {
        System.out.print(text + ": ");
        return scanner.nextLine();
    }

    public static String inputFullName(String text) {
        while (true) {
            String fullName = input(text).trim();
            if (fullName.contains(" ")) return fullName;
            System.out.println("[!] At least two words recomended");
        }
    }

    public static void showSuccess(boolean success) {
        System.out.println(
                success ? "SUCCESS!" : "FAIL!"
        );
    }
}
