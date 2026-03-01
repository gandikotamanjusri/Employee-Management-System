import java.util.*;

public class EmployeeReportGenerator {

    public static void salaryStatistics(List<Employee> employees) {
        double total = 0;
        Employee max = null, min = null;

        for (Employee e : employees) {
            total += e.getSalary();
            if (max == null || e.getSalary() > max.getSalary()) max = e;
            if (min == null || e.getSalary() < min.getSalary()) min = e;
        }

        System.out.println(" SALARY STATISTICS:");
        System.out.println("• Total Employees: " + employees.size());
        System.out.println("• Total Salary: ₹" + String.format("%,.2f", total));
        System.out.println("• Average Salary: ₹" + String.format("%,.2f", total / employees.size()));
        System.out.println("• Highest Salary: ₹" + max.getSalary() + " (" + max.getName() + ")");
        System.out.println("• Lowest Salary: ₹" + min.getSalary() + " (" + min.getName() + ")");
    }

    public static void departmentSummary(List<Employee> employees) {
        Map<String, List<Employee>> map = new HashMap<>();

        for (Employee e : employees)
            map.computeIfAbsent(e.getDepartment(), k -> new ArrayList<>()).add(e);

        System.out.println("\n DEPARTMENT SUMMARY:");
        for (String dept : map.keySet()) {
            double total = 0;
            for (Employee e : map.get(dept))
                total += e.getSalary();
            double avg = total / map.get(dept).size();
            System.out.println("• " + dept + ": " + map.get(dept).size() +
                    " employees, Average: ₹" + String.format("%,.2f", avg));
        }
    }
}