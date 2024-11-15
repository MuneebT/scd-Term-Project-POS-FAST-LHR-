package Controller;

import Model.Employee;
import Model.EmployeeDao;

import java.util.List;

public class EmployeeManagementController {
    EmployeeDao employeeDao=new EmployeeDao();
    public EmployeeManagementController() {
    }
    public void redirect_employee_insertion(String name,String empNo,String email,String branchCode,String salary,String designation)
    {
        employeeDao.insertEmployee(name,empNo,email,branchCode,salary,designation);
    }
    public List<Employee> redirect_get_All_employees()
    {
        List<Employee> employees= employeeDao.getAllEmployees();
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
