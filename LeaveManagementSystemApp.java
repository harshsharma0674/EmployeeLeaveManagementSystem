import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Employee {
    private String employeeId;
    private String name;
    private int leaveBalance;

    Employee(String employeeId, String name, int leaveBalance) {
        this.employeeId = employeeId;
        this.name = name;
        this.leaveBalance = leaveBalance;
    }

    String getEmployeeId() {
        return employeeId;
    }

    String getName() {
        return name;
    }

    int getLeaveBalance() {
        return leaveBalance;
    }

    void deductLeaveBalance(int days) {
        leaveBalance -= days;
    }

    void addLeaveBalance(int days) {
        leaveBalance += days;
    }
}

class LeaveRequest {
    private String requestId;
    private Employee employee;
    private int days;
    private boolean approved;

    LeaveRequest(String requestId, Employee employee, int days) {
        this.requestId = requestId;
        this.employee = employee;
        this.days = days;
        this.approved = false;
    }

    String getRequestId() {
        return requestId;
    }

    Employee getEmployee() {
        return employee;
    }

    int getDays() {
        return days;
    }

    boolean isApproved() {
        return approved;
    }

    void approveRequest() {
        this.approved = true;
    }
}

class LeaveManagementSystem {
    private List<Employee> employees;
    private List<LeaveRequest> leaveRequests;

    LeaveManagementSystem() {
        employees = new ArrayList<>();
        leaveRequests = new ArrayList<>();
    }

    void addEmployee(String employeeId, String name, int leaveBalance) {
        Employee employee = new Employee(employeeId, name, leaveBalance);
        employees.add(employee);
    }

    Employee findEmployeeById(String employeeId) {
        for (Employee employee : employees) {
            if (employee.getEmployeeId().equals(employeeId)) {
                return employee;
            }
        }
        return null;
    }

    void submitLeaveRequest(String employeeId, int days) {
        Employee employee = findEmployeeById(employeeId);
        if (employee != null) {
            if (employee.getLeaveBalance() >= days) {
                String requestId = "REQ" + (leaveRequests.size() + 1);
                LeaveRequest leaveRequest = new LeaveRequest(requestId, employee, days);
                leaveRequests.add(leaveRequest);
                employee.deductLeaveBalance(days);
                System.out.println("Leave request submitted successfully. Request ID: " + requestId);
            } else {
                System.out.println("Insufficient leave balance. Leave request not submitted.");
            }
        } else {
            System.out.println("Employee not found. Leave request not submitted.");
        }
    }

    void displayLeaveRequests() {
        System.out.println("Leave Requests:");
        for (LeaveRequest leaveRequest : leaveRequests) {
            System.out.println("Request ID: " + leaveRequest.getRequestId() +
                    ", Employee: " + leaveRequest.getEmployee().getName() +
                    ", Days: " + leaveRequest.getDays() +
                    ", Status: " + (leaveRequest.isApproved() ? "Approved" : "Pending"));
        }
    }

    void approveLeaveRequest(String requestId) {
        for (LeaveRequest leaveRequest : leaveRequests) {
            if (leaveRequest.getRequestId().equals(requestId) && !leaveRequest.isApproved()) {
                leaveRequest.approveRequest();
                leaveRequest.getEmployee().addLeaveBalance(leaveRequest.getDays());
                System.out.println("Leave request approved. Leave balance updated.");
                return;
            }
        }
        System.out.println("Invalid request ID or request already approved.");
    }
}

public class LeaveManagementSystemApp {
    public static void main(String[] args) {
        LeaveManagementSystem leaveManagementSystem = new LeaveManagementSystem();
        leaveManagementSystem.addEmployee("E001", "John Doe", 20);
        leaveManagementSystem.addEmployee("E002", "Jane Doe", 15);

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n1. Submit Leave Request\n2. Display Leave Requests\n3. Approve Leave Request\n4. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter your employee ID: ");
                    String employeeId = scanner.next();
                    System.out.print("Enter the number of days for leave: ");
                    int days = scanner.nextInt();
                    leaveManagementSystem.submitLeaveRequest(employeeId, days);
                    break;
                case 2:
                    leaveManagementSystem.displayLeaveRequests();
                    break;
                case 3:
                    System.out.print("Enter the request ID to approve: ");
                    String requestId = scanner.next();
                    leaveManagementSystem.approveLeaveRequest(requestId);
                    break;
                case 4:
                    System.out.println("Exiting the leave management system. Goodbye!");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
            }
        }
    }
}