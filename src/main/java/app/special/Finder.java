package app.special;

import app.models.Company;
import app.models.Employee;

import java.util.*;

public class Finder {
    private Map<Employee, List<Employee>> managers;
    private Company company;

    public Finder(Company company) {
        this.company = company;
        reload();
    }

    public List<Employee> getSubordinatesOf(Employee employee) {
        List<Employee> list = managers.get(employee);
        if (list == null) return Collections.emptyList();
        return list;
    }

    public void reload() {
        managers = new HashMap<>();
        company.getAllEmployees().forEach(employee -> {
            addSubordinateToMap(employee.getManager(), employee);
        });
    }
    
    private void addSubordinateToMap(Employee manager, Employee employee) {
        if (manager == null) return;
        List<Employee> employees = managers.get(manager);
        if (employees == null) {
            employees = new ArrayList<>();
            managers.put(manager, employees);
        }
        employees.add(employee);
    }
}
