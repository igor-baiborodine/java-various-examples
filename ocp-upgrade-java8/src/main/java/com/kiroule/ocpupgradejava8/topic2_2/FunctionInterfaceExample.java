package com.kiroule.ocpupgradejava8.topic2_2;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @author Igor Baiborodine
 */
public class FunctionInterfaceExample {

    public static void main(String... args) {

        Employee emp1 = new Employee("Bender", "Rodriguez");
        Employee emp2 = new Employee("Philip", "Fry");
        Employee emp3 = new Employee("Amy", "Wong");

        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);

        for (Employee employee : employees) {
            System.out.println(employee.getDisplayName(
                    e -> "Display name: " + e.getFirstName() + " " + e.getLastName()
            ));
        }
    }
}

class Employee {

    private String firstName;
    private String lastName;

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getDisplayName(Function<Employee, String> f) {
        return f.apply(this);
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}



