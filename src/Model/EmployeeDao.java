package Model;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import Connection.ConnectionConfigurator;

public class EmployeeDao {

    public EmployeeDao() {
    }


    // Method to insert employee into the database
   // name,empNo,email,password,branchCode,salary,designation
    public void insertEmployee(String name, String empNo, String email,String password, String branchCode, String salary, String designation) {

        String insertSQL = "INSERT INTO employee (emp_no, name, email, branch_code, salary, designation, password) VALUES (?, ?, ?, ?, ?, ?, ?)";
        System.out.println("Salary is\t"+salary+"\n");
        System.out.println("Branch code"+branchCode+"\n");
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(insertSQL)) {

            // Validate and convert salary to BigDecimal
            BigDecimal salaryDecimal;
            try {
                salaryDecimal = new BigDecimal(salary.trim());
            } catch (NumberFormatException e) {

                System.out.println("Invalid salary format: " + salary);
                e.printStackTrace();
                return; // Exit the method if salary is invalid
            }

            // Set parameters for the prepared statement


            pstmt.setString(1, empNo);            // emp_no
            pstmt.setString(2, name);             // name
            pstmt.setString(3, email);            // email
            pstmt.setString(4, branchCode);       // branch_code
            pstmt.setBigDecimal(5, new java.math.BigDecimal(salary)); // salary
            pstmt.setString(6, designation);// designation
            pstmt.setString(7,password);


            // Execute the insert operation
            int rowsInserted = pstmt.executeUpdate();

            // Check if the insertion was successful
            if (rowsInserted > 0) {
                System.out.println("Employee inserted successfully!");
            } else {
                System.out.println("Failed to insert employee.");
            }
        } catch (SQLException e) {
            System.out.println("SQL error: " + e.getMessage());
            e.printStackTrace();
        } catch (NumberFormatException e) {
            System.out.println("Error: Invalid salary format.\n");
            e.printStackTrace();
            System.out.println("\nk");
        }
    }

    // Method to fetch all employees
    public List<Employee> getAllEmployees() {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee";

        try (Connection conn = ConnectionConfigurator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("emp_no"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("branch_code"),
                        rs.getBigDecimal("salary"),
                        rs.getString("designation")
                );
                System.out.println(employee.name + "\n");
                employeeList.add(employee);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return employeeList;
    }



    // Method to fetch all employees with designation "Branch Manager"


    public List<Employee> getAllBMs() throws SQLException {
        List<Employee> employeeList = new ArrayList<>();
        String query = "SELECT * FROM employee WHERE designation=?;";
        Connection conn=null;

        try {
            conn = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query);
             stmt.setString(1,"Branch Manager");
             ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Employee employee = new Employee(
                        rs.getInt("id"),
                        rs.getString("emp_no"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getString("branch_code"),
                        rs.getBigDecimal("salary"),
                        rs.getString("designation")
                );
                System.out.println(employee.name+"\n");
                employeeList.add(employee);

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        finally {
            if(conn!=null)
            {
                conn.close();
            }

        }
        return employeeList;
    }




    // Method to update an existing employee's details
    public void updateEmployee(Employee employee) {
        String query = "UPDATE employee SET name = ?, email = ?, branch_code = ?, salary = ?, designation = ? WHERE emp_no = ?";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setString(1, employee.name);
            pstmt.setString(2, employee.email);
            pstmt.setString(3, employee.branchCode);
            pstmt.setBigDecimal(4, new BigDecimal(employee.salary)); // Convert salary to BigDecimal
            pstmt.setString(5, employee.designation);
            pstmt.setString(6, employee.empNo);

            int rowsUpdated = pstmt.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Employee record updated successfully.");
            } else {
                System.out.println("No record found with emp_no: " + employee.empNo);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Method to delete an employee by emp_no
    public boolean deleteEmployee(String empNo) {
        String query = "DELETE FROM employee WHERE emp_no = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, empNo);
            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
