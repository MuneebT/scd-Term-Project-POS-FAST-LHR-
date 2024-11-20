package View;

import Controller.VendorManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class ManageVendorView extends JFrame {
    VendorManagementController vendorManagementController=new VendorManagementController();
    public ManageVendorView() throws SQLException {
        setTitle("Manage Vendors");
        setBounds(100, 100, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Column names for the table
        String[] columnNames = {"VendorID", "Name", "ContactPerson", "Phone", "Email", "Address", "City", "State_Province", "Country", "Update", "Delete"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Fetch data from database
        ResultSet resultSet = vendorManagementController.redirect_Get_All_Vendors();
        while (resultSet.next()) {
            int vendorID = resultSet.getInt("VendorID");
            String name = resultSet.getString("Name");
            String contactPerson = resultSet.getString("ContactPerson");
            String phone = resultSet.getString("Phone");
            String email = resultSet.getString("Email");
            String address = resultSet.getString("Address");
            String city = resultSet.getString("City");
            String stateProvince = resultSet.getString("State_Province");
            String country = resultSet.getString("Country");

            // Add row data and placeholders for buttons
            tableModel.addRow(new Object[]{vendorID, name, contactPerson, phone, email, address, city, stateProvince, country, "Update", "Delete"});
        }

        // Create JTable with custom cell renderers and editors for buttons
        JTable table = new JTable(tableModel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Only make the Update and Delete columns editable
                return column == 9 || column == 10;
            }

            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component c = super.prepareRenderer(renderer, row, column);
                if (column == 9 || column == 10) {
                    c.setForeground(Color.BLUE);
                    c.setFont(new Font("Arial", Font.BOLD, 12));
                }
                return c;
            }
        };

        // Add ActionListener for Update and Delete buttons
        table.getColumnModel().getColumn(9).setCellEditor(new ButtonEditor("Update", tableModel));
        table.getColumnModel().getColumn(10).setCellEditor(new ButtonEditor("Delete", tableModel));

        // Adjust column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(9).setPreferredWidth(100);
        columnModel.getColumn(10).setPreferredWidth(100);

        // Add table to JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // ButtonEditor class for handling button clicks
    private static class ButtonEditor extends DefaultCellEditor {
        VendorManagementController vendorManagementController=new VendorManagementController();
     //   UpdateVendorView updateVendorView=new UpdateVendorView();
        private final JButton button;
        private String actionType;
        private final DefaultTableModel tableModel;

        public ButtonEditor(String actionType, DefaultTableModel tableModel) {
            super(new JTextField());
            this.actionType = actionType;
            this.tableModel = tableModel;
            this.button = new JButton(actionType);
            button.setOpaque(true);

            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JButton button = (JButton) e.getSource();

                    // Get the parent component of the button
                    Component parentComponent = button.getParent();

                    // Traverse up the component hierarchy to find the JTable
                    while (parentComponent != null && !(parentComponent instanceof JScrollPane)) {
                        parentComponent = parentComponent.getParent();
                    }

                    // Once JScrollPane is found, get the table from it
                    if (parentComponent instanceof JScrollPane) {
                        JScrollPane scrollPane = (JScrollPane) parentComponent;
                        JTable table = (JTable) scrollPane.getViewport().getView();  // Get JTable from JScrollPane

                        // Get the selected row from the table
                        int row = table.getSelectedRow();
                        if (row != -1) {  // Make sure a row is selected
                            int vendorID = (int) tableModel.getValueAt(row, 0);
                            String name = (String) tableModel.getValueAt(row, 1);
                            String contactPerson = (String) tableModel.getValueAt(row, 2);
                            String phone = (String) tableModel.getValueAt(row, 3);
                            String email = (String) tableModel.getValueAt(row, 4);
                            String address = (String) tableModel.getValueAt(row, 5);
                            String city = (String) tableModel.getValueAt(row, 6);
                            String stateProvince = (String) tableModel.getValueAt(row, 7);
                            String country = (String) tableModel.getValueAt(row, 8);

                            if ("Update".equals(actionType)) {
                                handleUpdate(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
                            } else if ("Delete".equals(actionType)) {
                                handleDelete(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
                            }
                        }
                    }
                }
            });
        }

        private void handleUpdate(int vendorID, String name, String contactPerson, String phone, String email,
                                  String address, String city, String stateProvince, String country) {
            System.out.println("Update clicked for VendorID: " + vendorID);
            System.out.println("Name: " + name + ", Contact Person: " + contactPerson + ", Phone: " + phone);
            System.out.println("Email: " + email + ", Address: " + address + ", City: " + city);
            System.out.println("State/Province: " + stateProvince + ", Country: " + country);
           // updateVendorView.updateFields(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
            UpdateVendorView updateVendorView1=new UpdateVendorView(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
        }

        private void handleDelete(int vendorID, String name, String contactPerson, String phone, String email,
                                  String address, String city, String stateProvince, String country) {
            int confirmation = JOptionPane.showConfirmDialog(
                    null,
                    "Are you sure you want to delete Vendor with ID: " + vendorID + "?",
                    "Confirm Delete",
                    JOptionPane.YES_NO_OPTION
            );

            if (confirmation == JOptionPane.YES_OPTION) {
                // Code to delete the vendor from the database
                System.out.println("Deleting VendorID: " + vendorID);
                System.out.println("Name: " + name + ", Contact Person: " + contactPerson + ", Phone: " + phone);
                System.out.println("Email: " + email + ", Address: " + address + ", City: " + city);
                System.out.println("State/Province: " + stateProvince + ", Country: " + country);

                try {
                    boolean isDeleted =vendorManagementController.redirect_Delete_Vendors(vendorID);
                    if (isDeleted) {
                        JOptionPane.showMessageDialog(null,
                                "Vendor with ID: " + vendorID + " has been successfully deleted.",
                                "Delete Successful", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(null,
                                "Failed to delete Vendor with ID: " + vendorID + ".",
                                "Delete Failed", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null,
                            "Error occurred while deleting Vendor with ID: " + vendorID + ".",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                System.out.println("Delete action cancelled for VendorID: " + vendorID);
            }
        }
        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            button.setText(actionType);
            return button;
        }
    }


    public static void main(String[] args) {
        try {
            new ManageVendorView();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
