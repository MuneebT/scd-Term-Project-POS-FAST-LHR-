package Model;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.util.List;

public class EmployeeTableModel extends AbstractTableModel {
    private List<Employee> employees;
    private final String[] columnNames = {"Emp No", "Name", "Email", "Salary", "Branch Code", "Designation"};

    public EmployeeTableModel(List<Employee> employees) {
        this.employees = employees;
    }

    @Override
    public int getRowCount() {
        return employees.size();
    }

    @Override
    public int getColumnCount() {
        return columnNames.length;
    }

    @Override
    public String getColumnName(int columnIndex) {
        return columnNames[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Employee employee = employees.get(rowIndex);
        switch (columnIndex) {
            case 0: return employee.getEmpNo();
            case 1: return employee.getName();
            case 2: return employee.getEmail();
            case 3: return employee.getSalary();
            case 4: return employee.getBranchCode();
            case 5: return employee.getDesignation();
           default: return null;
        }
    }

    public Employee getEmployeeAt(int rowIndex) {
        return employees.get(rowIndex);
    }

    public void removeEmployeeAt(int rowIndex) {
        employees.remove(rowIndex);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return columnIndex == 6 || columnIndex == 7;  // Make only "Delete" and "Update" columns editable
    }

    @Override
    public void fireTableDataChanged() {
        super.fireTableDataChanged();  // Refreshes the table after data changes
    }

    @Override
    public void fireTableRowsUpdated(int firstRow, int lastRow) {
        super.fireTableRowsUpdated(firstRow, lastRow);  // Refreshes the table after a specific row is updated
    }
}
