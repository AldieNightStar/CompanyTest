package app;

import app.models.Company;
import app.models.Employee;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class CompanyTests {
    private Company company;

    @Before
    public void beforeTest() {
        company = new Company();
    }

    @Test
    public void getByFullNameTest() {
        Employee employee = new Employee("Rudolf Sim");
        company.addEmployee(employee);
        Employee foundEmployee = company.getEmployeeByName("Rudolf Sim");
        assert foundEmployee != null;
        assert foundEmployee.equals(employee);
    }

    @Test
    public void addEmployeeTest() {
        company.addEmployee(new Employee("Joe Tester"));
        Employee employee = company.getEmployeeByName("Joe", "Tester");
        assert employee != null;
        assert employee.getFirstName().equals("Joe");
        assert employee.getLastName().equals("Tester");
    }

    @Test
    public void addManagerTest() {
        Employee joeEmployee = new Employee("Joe Employee");
        company.addEmployee(joeEmployee);
        Employee joeManager = new Employee("Joe Manager");
        company.addEmployee(joeManager);
        company.setManagerFor(joeEmployee, joeManager);
        List<Employee> subordinates = company.getSubordinatesOf(joeManager.getFullName());
        assert subordinates != null;
        assert subordinates.size() > 0;
    }

    @Test
    public void getEmployeeManager() {
        Employee employee1 = new Employee("Ihor Fox");
        Employee employee2 = new Employee("Alex Manager");
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.setManagerFor(employee1, employee2);
        Employee managerOfIhorFox = company.getManagerOf("Ihor Fox");
        assert managerOfIhorFox != null;
        assert managerOfIhorFox.equals(employee2);
    }

    @Test
    public void testEmployeeSubordinates() {
        Employee manager = new Employee("Steve Man");
        company.addEmployee(manager);
        Employee employee1 = new Employee("Ihor Fox");
        Employee employee2 = new Employee("Maria DB");
        Employee employee3 = new Employee("Yarick Fich");
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.setManagerFor(employee1, manager);
        company.setManagerFor(employee2, manager);
        company.setManagerFor(employee3, manager);
        List<Employee> subordinates = company.getSubordinatesOf(manager);
        assert subordinates.contains(employee1);
        assert subordinates.contains(employee2);
        assert subordinates.contains(employee3);
    }

    @Test
    public void testEmployeesManagerSubordinate() {
        Employee employee1 = new Employee("Ihor Fox");
        Employee employee2 = new Employee("Alex Manager");
        Employee employee3 = new Employee("Vova ZilVova");
        Employee employee4 = new Employee("Andrew Romania");
        Employee employee5 = new Employee("Fill Fillip");
        Employee manager = new Employee("Manager Man");
        company.addEmployee(employee1);
        company.addEmployee(employee2);
        company.addEmployee(employee3);
        company.addEmployee(employee4);
        company.addEmployee(employee5);
        company.addEmployee(manager);
        company.setManagerFor(employee1, manager);
        company.setManagerFor(employee2, manager);
        company.setManagerFor(employee3, manager);
        company.setManagerFor(employee4, manager);
        company.setManagerFor(employee5, manager);
        List<Employee> subordinates = company.getManagerSubordinateByEmployee(employee1);
        assert subordinates.contains(employee1);
        assert subordinates.contains(employee2);
        assert subordinates.contains(employee3);
        assert subordinates.contains(employee4);
        assert subordinates.contains(employee5);
    }
}
