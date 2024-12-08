package View;

import Connection.InternetConnectionChecker;
import Controller.EmployeeManagementController;
import Model.Employee;
import Model.EmployeeDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class EditEmployeeView extends JFrame {
    private EmployeeDao emplyeeDao = new EmployeeDao();
    private EmployeeManagementController employeeManagementController=new EmployeeManagementController();
    private Employee employee;
    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public EditEmployeeView(Employee employee) {
        this.employee = employee;

        setTitle("Edit Employee");
        setSize(400, 400);
        setLayout(new GridLayout(0, 2));

        // Pre-fill fields with employee data
        add(new JLabel("Name:"));
        JTextField nameField = new JTextField(employee.getName());
        add(nameField);

        add(new JLabel("Email:"));
        JTextField emailField = new JTextField(employee.getEmail());
        add(emailField);

        add(new JLabel("Branch Code:"));
        JTextField branchCodeField = new JTextField(employee.getBranchCode());
        add(branchCodeField);

        add(new JLabel("Salary:"));
        JTextField salaryField = new JTextField(employee.getSalary().toString());
        add(salaryField);

        add(new JLabel("Designation:"));
        JTextField designationField = new JTextField(employee.getDesignation());
        add(designationField);

        JButton updateButton = new JButton("Update");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean isconnected=icc.startChecking();
                if(isconnected) {
                    employeeManagementController.redirect_employee_Update(employee);
                    dispose(); // Close edit window
                }
                else{
                    storeeditempoyeedataintempfile(employee);
                }
            }
        });

        add(updateButton);
        setVisible(true);
    }
    public void storeeditempoyeedataintempfile(Employee employee){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("editemployee.txt",true));
            String data=employee.getName()+","+employee.getSalary()+","+employee.getEmpNo()+","+employee.getEmail()
                    +","+employee.getBranchCode()+","+employee.getDesignation();
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
    }
}
