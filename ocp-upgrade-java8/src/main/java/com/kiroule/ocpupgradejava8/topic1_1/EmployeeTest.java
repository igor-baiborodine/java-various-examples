package com.kiroule.ocpupgradejava8.topic1_1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Igor Baiborodine
 */
class EmployeeTest {

    public static void main(String... args) {

        Employee emp1 = new Employee("Bender", "Rodriguez");
        Employee emp2 = new Employee("Philip", "Fry");
        Employee emp3 = new Employee("Amy", "Wong");

        List<Employee> employees = new ArrayList<>();
        employees.add(emp1);
        employees.add(emp2);
        employees.add(emp3);

        System.out.println("Before sorting: " + employees);

        Employee.FirstNameAscComparator comparator = new Employee.FirstNameAscComparator();
        Collections.sort(employees, comparator);
        System.out.println("After sorting: " + employees);
    }
}
