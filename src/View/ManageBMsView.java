package View;

import Connection.InternetConnectionChecker;
import Controller.EmployeeManagementController;
import Model.Employee;
import Model.EmployeeTableModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

public class ManageBMsView extends JFrame {
    private JTable employeeTable;
    private EmployeeManagementController employeeManagementController = new EmployeeManagementController();
    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public ManageBMsView() throws SQLException {
        setTitle("Manage Branch Managers");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        EmployeeTableModel model=null;
        boolean isconnected=icc.startChecking();
        List<Employee> employees=null;
        if(isconnected) {
             employees = employeeManagementController.redirect_get_All_BMs();
            System.out.println("BMs fetched: " + employees.size());

            // Create a custom table model
             model = new EmployeeTableModel(employees);
        }
        else{
            employees=getallemployess();
            model=new EmployeeTableModel(employees);
        }
        // Wrap the model to add action buttons
        JTable table = new JTable(new EmployeeButtonTableModel(model));
        table.setRowHeight(70);

        // Set custom cell renderer for buttons
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), employees));

        // Add table to the center
        add(new JScrollPane(table), BorderLayout.CENTER);

        // Create footer panel for the Back button
        JPanel footerPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Impact", Font.PLAIN, 16));
        backButton.setBackground(Color.decode("#415a77"));
        backButton.setForeground(Color.WHITE);
        backButton.setToolTipText("Click here to return!");

        // Back button action listener
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();// Close current window
                SADashboardView screen=new SADashboardView();
                screen.setVisible(true);
                // new Main(); // Uncomment if you want to navigate to the main page
            }
        });

        // Add Back button to the footer panel
        footerPanel.add(backButton);
        add(footerPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    public static void main(String[] args) throws SQLException {
        new ManageBMsView();
    }

    // Renderer for action buttons
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton deleteButton;
        private JButton updateButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            deleteButton = new JButton("Delete");
            updateButton = new JButton("Update");
            add(deleteButton);
            add(updateButton);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            return this;
        }
    }

    // Editor for action buttons
    class ButtonEditor extends AbstractCellEditor implements TableCellEditor, ActionListener {
        private JPanel panel;
        private JButton deleteButton;
        private JButton updateButton;
        private int row;
        private JTable table;
        private List<Employee> employees;

        public ButtonEditor(JCheckBox checkBox, List<Employee> employees) {
            this.employees = employees;
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            deleteButton = new JButton("Delete");
            updateButton = new JButton("Update");

            deleteButton.addActionListener(this);
            updateButton.addActionListener(this);

            panel.add(deleteButton);
            panel.add(updateButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        Employee employeeToDelete=null;
        Employee employeeToUpdate=null;
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == deleteButton) {
                boolean isconnected = icc.startChecking();
               if(isconnected){
                // Perform delete action
                 employeeToDelete = employees.get(row);
                System.out.println("Deleting employee: " + employeeToDelete.getName());
                employeeManagementController.redirect_employee_delete(employeeToDelete.getEmpNo());
                employees.remove(row);
                ((AbstractTableModel) table.getModel()).fireTableRowsDeleted(row, row);
            }
               else{
                   storeManageBmsdataintempfile(employeeToDelete.getEmpNo());
               }
            } else if (e.getSource() == updateButton) {
                // Perform update action
                boolean isconnected=icc.startChecking();
                if(isconnected) {
                     employeeToUpdate = employees.get(row);
                    System.out.println("Updating employee: " + employeeToUpdate.getName());
                    openUpdateDialog(employeeToUpdate);
                }
                else{
                    storeManageBMSupdatedata(employeeToUpdate);
                }
                }
            fireEditingStopped();
        }

        private void openUpdateDialog(Employee employeeToUpdate) {
            // Create a dialog to update the employee's details
            JTextField nameField = new JTextField(employeeToUpdate.getName(), 20);
            JTextField emailField = new JTextField(employeeToUpdate.getEmail(), 20);
            JTextField branchField = new JTextField(employeeToUpdate.getBranchCode(), 20);
            JTextField salaryField = new JTextField(String.valueOf(employeeToUpdate.getSalary()), 20);
            JTextField designationField = new JTextField(employeeToUpdate.getDesignation(), 20);

            JPanel panel = new JPanel(new GridLayout(5, 2));
            panel.add(new JLabel("Name:"));
            panel.add(nameField);
            panel.add(new JLabel("Email:"));
            panel.add(emailField);
            panel.add(new JLabel("Branch Code:"));
            panel.add(branchField);
            panel.add(new JLabel("Salary:"));
            panel.add(salaryField);
            panel.add(new JLabel("Designation:"));
            panel.add(designationField);

            int option = JOptionPane.showConfirmDialog(ManageBMsView.this, panel, "Update Employee", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                // Update the employee object
                employeeToUpdate.setName(nameField.getText());
                employeeToUpdate.setEmail(emailField.getText());
                employeeToUpdate.setBranchCode(branchField.getText());
                employeeToUpdate.setSalary(String.valueOf(Double.parseDouble(salaryField.getText())));
                employeeToUpdate.setDesignation(designationField.getText());

                // Update the database
                employeeManagementController.redirect_employee_Update(employeeToUpdate);

                // Refresh the table
                ((AbstractTableModel) table.getModel()).fireTableRowsUpdated(row, row);
                System.out.println("Employee updated successfully.");
            }
        }
    }

    // Wrapper table model to add an "Actions" column
    class EmployeeButtonTableModel extends AbstractTableModel {
        private EmployeeTableModel model;

        public EmployeeButtonTableModel(EmployeeTableModel model) {
            this.model = model;
        }

        @Override
        public int getRowCount() {
            return model.getRowCount();
        }

        @Override
        public int getColumnCount() {
            return model.getColumnCount() + 1; // Extra column for actions
        }

        @Override
        public String getColumnName(int column) {
            if (column < model.getColumnCount()) {
                return model.getColumnName(column);
            } else {
                return "Actions";
            }
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            if (columnIndex < model.getColumnCount()) {
                return model.getValueAt(rowIndex, columnIndex);
            } else {
                return null; // Actions column does not have a direct value
            }
        }

        @Override
        public boolean isCellEditable(int rowIndex, int columnIndex) {
            return columnIndex == model.getColumnCount();
        }

        @Override
        public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
            if (columnIndex < model.getColumnCount()) {
                model.setValueAt(aValue, rowIndex, columnIndex);
            }
        }
    }
    public void storeManageBmsdataintempfile(String num){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("ManageDeleteBM.txt",true));
            bw.write(num);
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void storeManageBMSupdatedata(Employee employeedata){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("ManageUpdateBM.txt",true));
           String data=employeedata.getName()+","+employeedata.getSalary()+","+employeedata.getEmpNo()+","+
                   employeedata.getEmail()+","+employeedata.getBranchCode()+","+employeedata.getBranchCode()+","+
                   employeedata.getDesignation();
           bw.write(data);
           bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public LinkedList<Employee> getallemployess() {
        LinkedList<Employee> employees = new LinkedList<>();
        BufferedReader br = null;

        try {
            br = new BufferedReader(new FileReader("employee.txt"));
            String line;

            while ((line = br.readLine()) != null) {
                // Split the line into parts
                String[] parts = line.split(",");

                // Ensure the line contains all the required fields
                if (parts.length == 6) {
                    String name = parts[0].trim();
                    String salary = parts[1].trim();
                    String empNo = parts[2].trim();
                    String email = parts[3].trim();
                    String branchCode = parts[4].trim();
                    String designation = parts[5].trim();

                    // Create an Employee object and add it to the list
                    Employee employee = new Employee(name, salary, empNo, email, branchCode, designation);
                    employees.add(employee);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return employees;
    }

}
