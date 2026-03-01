import java.text.SimpleDateFormat;
import java.util.*;

public class EmployeeManagementSystem {

    static Scanner sc = new Scanner(System.in);
    static List<Employee> employees = new ArrayList<>();
    static Map<String, Employee> employeeMap = new HashMap<>();

    public static void main(String[] args) {
        loadFromFile();

        while (true) {
            System.out.println("\n=== EMPLOYEE MANAGEMENT SYSTEM ===");
            System.out.println("1. Add New Employee");
            System.out.println("2. View All Employees");
            System.out.println("3. Search Employee");
            System.out.println("4. Update Employee");
            System.out.println("5. Delete Employee");
            System.out.println("6. Generate Reports");
            System.out.println("7. Save to File");
            System.out.println("8. Load from File");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice = Integer.parseInt(sc.nextLine());

            switch (choice) {
                case 1 -> addEmployee();
                case 2 -> viewAll();
                case 3 -> searchEmployee();
                case 4 -> updateEmployee();
                case 5 -> deleteEmployee();
                case 6 -> reports();
                case 7 -> saveToFile();
                case 8 -> loadFromFile();
                case 9 -> System.exit(0);
            }
        }
    }

    static void addEmployee() {
        System.out.println("\n=== ADD NEW EMPLOYEE ===");
        System.out.print("Enter Employee ID: ");
        String id = sc.nextLine();

        if (employeeMap.containsKey(id)) {
            System.out.println("Employee already exists!");
            return;
        }

        System.out.print("Enter Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Department: ");
        String dept = sc.nextLine();
        System.out.print("Enter Position: ");
        String pos = sc.nextLine();
        System.out.print("Enter Salary: ");
        double sal = Double.parseDouble(sc.nextLine());

        Employee e = new Employee(id, name, dept, pos, sal);
        employees.add(e);
        employeeMap.put(id, e);

        System.out.println(" Employee added successfully!");
        saveToFile();
    }

    static void viewAll() {
        System.out.println("\n=== ALL EMPLOYEES ===");
        System.out.printf("%-8s %-20s %-15s %-15s %-10s %-12s\n",
                "ID", "Name", "Department", "Position", "Salary", "Join Date");
        System.out.println("-".repeat(80));

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        for (Employee e : employees) {
            System.out.printf("%-8s %-20s %-15s %-15s ₹%-9.2f %-12s\n",
                    e.getId(), e.getName(), e.getDepartment(),
                    e.getPosition(), e.getSalary(), sdf.format(e.getJoinDate()));
        }
    }

    static void searchEmployee() {
        System.out.println("\n=== SEARCH EMPLOYEE ===");
        System.out.println("1. Search by ID");
        System.out.println("2. Search by Name");
        System.out.println("3. Search by Department");
        System.out.print("Enter choice: ");
        int ch = Integer.parseInt(sc.nextLine());

        if (ch == 3) {
            System.out.print("Enter Department: ");
            String dept = sc.nextLine();
            List<Employee> results = new ArrayList<>();

            for (Employee e : employees)
                if (e.getDepartment().equalsIgnoreCase(dept))
                    results.add(e);

            System.out.println("\n🔍 Search Results (" + results.size() + " employees found):\n");
            for (Employee e : results)
                System.out.println(e);
        }
    }

    static void updateEmployee() {
        System.out.print("Enter ID to update: ");
        String id = sc.nextLine();
        Employee e = employeeMap.get(id);

        if (e == null) {
            System.out.println("Employee not found!");
            return;
        }

        System.out.print("New Name: ");
        e.setName(sc.nextLine());
        System.out.print("New Department: ");
        e.setDepartment(sc.nextLine());
        System.out.print("New Position: ");
        e.setPosition(sc.nextLine());
        System.out.print("New Salary: ");
        e.setSalary(Double.parseDouble(sc.nextLine()));

        System.out.println("Updated successfully!");
    }

    static void deleteEmployee() {
        System.out.print("Enter ID to delete: ");
        String id = sc.nextLine();

        Employee e = employeeMap.remove(id);
        if (e != null) {
            employees.remove(e);
            System.out.println("Deleted successfully!");
        } else {
            System.out.println("Employee not found!");
        }
    }

    static void reports() {
        System.out.println("\n=== EMPLOYEE REPORTS ===");
        System.out.println("1. Department-wise Summary");
        System.out.println("2. Salary Statistics");
        System.out.println("3. Employee Count by Position");
        System.out.print("Enter choice: ");

        int c = Integer.parseInt(sc.nextLine());

        if (c == 2) {
            EmployeeReportGenerator.salaryStatistics(employees);
            EmployeeReportGenerator.departmentSummary(employees);
        }
    }

    static void saveToFile() {
        try {
            EmployeeFileHandler.save(employees);
            System.out.println("Employee data saved to file.");
        } catch (Exception e) {
            System.out.println("Save failed.");
        }
    }

    static void loadFromFile() {
        try {
            employees = EmployeeFileHandler.load();
            employeeMap.clear();
            for (Employee e : employees)
                employeeMap.put(e.getId(), e);
            System.out.println("Loaded " + employees.size() + " employees from file.");
        } catch (Exception e) {
            System.out.println("No existing employee data found.");
        }
    }
}