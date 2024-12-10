package test.Model;
import Controller.EmployeeManagementController;
import Model.Employee;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
public class EmployeeDAOTest {
    private EmployeeManagementController emc=new EmployeeManagementController();
    @Test
    public void TestgetAllemployeesdoesnotreturnnull(){
        List<Employee> employees=emc.redirect_get_All_employees();
        Assertions.assertNotNull(employees,"List of employees is not null");
    }
    @Test
    public void getAllemployeesisnotempty(){
        List<Employee> employees=emc.redirect_get_All_employees();
        Assertions.assertFalse(employees.isEmpty(),"Employees list is not empty");
    }

    @Test
    public void TestgetAllemployeesFirstemployee(){
        List<Employee> employees=emc.redirect_get_All_employees();
        Employee firstemployee=employees.get(0);

        Assertions.assertEquals("E1001",firstemployee.getEmpNo());
        Assertions.assertEquals("John",firstemployee.getName());
        Assertions.assertEquals("johndoe@example.com",firstemployee.getEmail());
        Assertions.assertEquals("1",firstemployee.getBranchCode());
        Assertions.assertEquals("50000.00",firstemployee.getSalary());
        Assertions.assertEquals("Branch Manager",firstemployee.getDesignation());
    }


}
