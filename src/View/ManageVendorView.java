package View;

import Controller.VendorManagementController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.LinkedList;

import Connection.InternetConnectionChecker;
public class ManageVendorView extends JFrame {
    VendorManagementController vendorManagementController = new VendorManagementController();
private static InternetConnectionChecker icc=new InternetConnectionChecker();
    public ManageVendorView() throws SQLException {
        setTitle("Manage Vendors");
        setBounds(100, 100, 1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create JPanel for the top section
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE);

        // Create the "Add Vendor" button
        JButton addVendorButton = new JButton("+ Add Vendor");
        addVendorButton.setFont(new Font("Arial", Font.BOLD, 14));
        addVendorButton.setBackground(Color.decode("#415a77"));
        addVendorButton.setForeground(Color.WHITE);

        // Add ActionListener to the "Add Vendor" button
        addVendorButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Open the Add Vendor View (you need to create this view)
                new AddVendorView();
            }
        });

        // Create the "Back" button
        JButton backButton = new JButton("Back");
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setBackground(Color.decode("#2a2a72"));
        backButton.setForeground(Color.WHITE);

        // Add ActionListener to the "Back" button
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Close the current window and navigate back
                dispose();
                new DEODashboardView();
            }
        });

        // Add buttons to the top panel
        topPanel.add(addVendorButton);
        topPanel.add(backButton);

        // Column names for the table
        String[] columnNames = {"VendorID", "Name", "ContactPerson", "Phone", "Email", "Address", "City", "State_Province", "Country", "Update", "Delete"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

        // Fetch data from database
        boolean isconnected=icc.startChecking();
        if(isconnected) {
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
        }
        else{
            tableModel.addRow(readvendordatafromfile());
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

        // Add topPanel and table to the frame
        add(topPanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);
    }

    // ButtonEditor class for handling button clicks (no changes needed here)
    private static class ButtonEditor extends DefaultCellEditor {
        VendorManagementController vendorManagementController = new VendorManagementController();
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
                                boolean isconnected=icc.startChecking();
                                if(isconnected) {
                                    handleDelete(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
                                }
                                else{
                                    storedeletemanagevendordata(vendorID);
                                }
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
            UpdateVendorView updateVendorView1 = new UpdateVendorView(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
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
                try {
                    boolean isDeleted = vendorManagementController.redirect_Delete_Vendors(vendorID);
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
                            "Error occurred while deleting Vendor.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            return button;
        }
    }
    public Object[][] readvendordatafromfile() {
        BufferedReader br = null;
        Object[][] vendorData = null;

        try {
            br = new BufferedReader(new FileReader("vendor.txt"));
            LinkedList<String[]> tempData = new LinkedList<>();
            String line;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length == 9) { // Ensure the line matches vendor schema
                    tempData.add(data);
                }
            }

            // Convert LinkedList to 2D Object array
            int rowCount = tempData.size();
            int columnCount = 9; // Columns in vendor schema
            vendorData = new Object[rowCount][columnCount + 2]; // +2 for Update and Delete columns

            for (int i = 0; i < rowCount; i++) {
                // Copy data to the row
                System.arraycopy(tempData.get(i), 0, vendorData[i], 0, columnCount);

                // Add placeholders for buttons
                vendorData[i][columnCount] = "Update"; // Column 9
                vendorData[i][columnCount + 1] = "Delete"; // Column 10
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

        return vendorData;
    }
    public static void storedeletemanagevendordata(int id) {
        BufferedWriter bw = null;
        try {
            // Open the file in append mode
            bw = new BufferedWriter(new FileWriter("deletemanagevendordata.txt", true));

            // Write the ID followed by a new line
            bw.write(String.valueOf(id));
            bw.newLine();

            System.out.println("Vendor ID " + id + " has been stored in deletemanagevendordata.txt");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Failed to store Vendor ID: " + id);
        } finally {
            try {
                if (bw != null) {
                    bw.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws SQLException {
        new ManageVendorView();
    }

}
