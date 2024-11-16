package View;

import Controller.EmployeeManagementController;
import Model.Employee;
import Model.EmployeeDao;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EditEmployeeView extends JFrame {
    private EmployeeDao emplyeeDao = new EmployeeDao();
    private EmployeeManagementController employeeManagementController=new EmployeeManagementController();
    private Employee employee;

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
                employeeManagementController.redirect_employee_Update(employee);
                dispose(); // Close edit window
            }
        });

        add(updateButton);
        setVisible(true);
    }
}
