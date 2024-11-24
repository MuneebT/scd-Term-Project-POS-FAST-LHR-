package Controller;

import Model.Employee;
import Model.EmployeeDao;

import java.sql.SQLException;
import java.util.List;

public class EmployeeManagementController {
    EmployeeDao employeeDao=new EmployeeDao();
    public EmployeeManagementController() {
    }

    public void redirect_employee_insertion(String name,String empNo,String email,String branchCode,String salary,String designation,String password) {
        // Print the data passed to the method
        System.out.println("Redirecting employee insertion with the following details:");
        System.out.println("Name: " + name);
        System.out.println("Employee Number: " + empNo);
        System.out.println("Password: " + password);
        System.out.println("Email: " + email);
        System.out.println("Branch Code: " + branchCode);
        System.out.println("Salary: " + salary);
        System.out.println("Designation: " + designation);

        // Now call the insertEmployee method in employeeDao
        employeeDao.insertEmployee(name,empNo,email,branchCode,salary,designation,password);
    }


//     public void redirect_employee_insertion(String name,String empNo,String password,String email,String branchCode,String salary,String designation)
//     {
//         //added password parameter here by abdullah
//         employeeDao.insertEmployee(name,empNo,email,password,branchCode,salary,designation);
//     }

    public List<Employee> redirect_get_All_employees()
    {
        List<Employee> employees= employeeDao.getAllEmployees();
        for(Employee employee:employees)
        {
            System.out.println(employee.name+" "+employee.email);
        }
        return employees;
    }


    public List<Employee> redirect_get_All_BMs() throws SQLException {

        List<Employee> employees= employeeDao.getAllBMs();
        for(Employee employee:employees)
        {
            System.out.println(employee.name+" "+employee.email);
        }
        return employees;
    }
    public void redirect_employee_Update(Employee employee)
    {
        employeeDao.updateEmployee(employee);

    }
    public boolean redirect_employee_delete(String emp_no)
    {
        return employeeDao.deleteEmployee(emp_no);
    }
}
