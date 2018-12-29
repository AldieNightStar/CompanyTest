package app.models;

import app.special.Finder;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class Company {
    private List<Employee> employees = new ArrayList<>();
    private Finder finder;
    private boolean madeChanges; // For lazy Finder updates

    public void addEmployee(Employee employee) {
        if (employees.add(employee)) {
            madeChanges = true;
        }
    }

    public Employee getEmployeeByName(String fullName) throws IllegalArgumentException {
        String[] arr = splitCredentials(fullName);
        return getEmployeeByName(arr[0], arr[1]);
    }

    public Employee getEmployeeByName(String firstName, String lastName) {
        for (Employee employee : employees) {
            if (employee.getFirstName().equalsIgnoreCase(firstName)
                    && employee.getLastName().equalsIgnoreCase(lastName)) {
                return employee;
            }
        }
        return null;
    }

    public List<Employee> getAllEmployees() {
        return Collections.unmodifiableList(employees);
    }

    public void removeEmployeeByName(String fullName) {
        Employee employee = getEmployeeByName(fullName);
        if (employees.remove(employee)) {
            madeChanges = true;
        }
    }

    public Employee getManagerOf(String firstName, String secondName) {
        Employee employee = getEmployeeByName(firstName, secondName);
        return employee.getManager();
    }

    public Employee getManagerOf(String fullName) {
        String[] arr = splitCredentials(fullName);
        return getManagerOf(arr[0], arr[1]);
    }

    public boolean setManagerFor(Employee employee, Employee newManager) {
        if (newManager == null) {
            return false;
        }
        employee.setManager(newManager);
        madeChanges = true;
        return true;
    }

    public boolean setManagerFor(String firstName, String lastName, Employee newManager) {
        Employee employee = getEmployeeByName(firstName, lastName);
        return setManagerFor(employee, newManager);
    }

    public boolean setManagerFor(String fullName, Employee newManager) {
        String[] arr = splitCredentials(fullName);
        return setManagerFor(arr[0], arr[1], newManager);
    }

    private String[] splitCredentials(String fullName) {
        String[] arr = fullName.split(Pattern.quote(" "), 2);
        if (arr.length < 2) {
            return new String[]{fullName, ""};
        }
        return arr;
    }

    public Finder finder() {
        if (finder == null) finder = new Finder(this);
        if (madeChanges) {
            finder.reload();
            madeChanges = false;
        }
        return finder;
    }

    public List<Employee> getSubordinatesOf(String firstName, String lastName) {
        Employee employee = getEmployeeByName(firstName, lastName);
        return finder().getSubordinatesOf(employee);
    }

    public List<Employee> getSubordinatesOf(String fullName) {
        String[] arr = splitCredentials(fullName);
        return getSubordinatesOf(arr[0], arr[1]);
    }

    public List<Employee> getSubordinatesOf(Employee manager) {
        return finder().getSubordinatesOf(manager);
    }

    public List<Employee> getManagerSubordinateByEmployee(Employee employee) {
        Employee manager = employee.getManager();
        if (manager == null) return Collections.emptyList();
        return getSubordinatesOf(manager);
    }
}
