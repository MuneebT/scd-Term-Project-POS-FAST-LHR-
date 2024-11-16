package Model;

import java.math.BigDecimal;

public class Employee {
    public String name;
    public String salary;
    public String empNo;
    public String email;
    public String branchCode;
    public String designation;
    public Employee()
    {

    }

    public Employee(String name, String salary, String empNo, String email, String branchCode, String designation) {
        this.name = name;
        this.salary = salary;
        this.empNo = empNo;
        this.email = email;
        this.branchCode = branchCode;
        this.designation = designation;
    }

    public Employee(int id, String empNo, String name, String email, String branchCode, BigDecimal salary, String designation) {
        this.name = name;
        this.salary = String.valueOf(salary);
        this.empNo = empNo;
        this.email = email;
        this.branchCode = branchCode;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getEmpNo() {
        return empNo;
    }

    public void setEmpNo(String empNo) {
        this.empNo = empNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}
