package View;

import Controller.BranchManagementController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class BranchManagementView extends JFrame {
    public JTable bmtable;
    private DefaultTableModel dtm1;
    private BranchManagementController bmc = new BranchManagementController();
    private JScrollPane bmScroll;
    private JButton createBranchButton;
    private JButton backButton;

    public BranchManagementView() {
        setTitle("Branch Management");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(100, 100, 800, 600);

        // Add header panel
        JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        headerPanel.setBounds(0, 0, getWidth(), 50);
        headerPanel.setBackground(new Color(0, 120, 215));

        // Title label
        JLabel titleLabel = new JLabel("Manage Branches");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.WHITE);

        // Create Branch button
        createBranchButton = new JButton("Create");
        createBranchButton.setPreferredSize(new Dimension(100, 30));
        createBranchButton.setBackground(Color.WHITE);
        createBranchButton.setForeground(new Color(0, 120, 215));
        createBranchButton.setFont(new Font("Arial", Font.PLAIN, 14));
        createBranchButton.addActionListener(e -> {

            new CreateBranchView();

        });

        // Back button
        backButton = new JButton("Back");
        backButton.setPreferredSize(new Dimension(100, 30));
        backButton.setBackground(Color.WHITE);
        backButton.setForeground(new Color(0, 120, 215));
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        backButton.addActionListener(e -> {
            // Action for Back button
            JOptionPane.showMessageDialog(this, "Navigating back...");
            dispose(); // Close the current window
            new SADashboardView();
        });

        headerPanel.add(titleLabel);
        headerPanel.add(Box.createHorizontalStrut(20)); // Spacing
        headerPanel.add(createBranchButton);
        headerPanel.add(Box.createHorizontalStrut(10)); // Spacing
        headerPanel.add(backButton);
        add(headerPanel);


        String[] columnNames = { "ID", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees", "Delete", "Update" };

        Object[][] data1 = bmc.return_object_Array();


        bmtable = new JTable(new DefaultTableModel(data1, columnNames));


        bmtable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        bmtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));

        bmtable.getColumn("Update").setCellRenderer(new ButtonRenderer());
        bmtable.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), "Update"));

        // Scroll pane
        bmScroll = new JScrollPane(bmtable);
        bmScroll.setBounds(0, 50, getWidth(), getHeight() - 100);
        add(bmScroll);

        setVisible(true);
    }

    // Custom renderer for rendering buttons in the table
    class ButtonRenderer extends JButton implements TableCellRenderer {
        public ButtonRenderer() {
            setOpaque(true);
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            setText((value == null) ? "" : value.toString());
            return this;
        }
    }

    // Custom editor for handling button actions in the table
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private String actionType;
        private int currentRow;

        public ButtonEditor(JCheckBox checkBox, String actionType) {
            super(checkBox);
            this.actionType = actionType;
            button = new JButton();
            button.setOpaque(true);
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    fireEditingStopped();
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            currentRow = row;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {

                if ("Delete".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to delete this data?");
                    if (response == 0) {
                        DefaultTableModel model = (DefaultTableModel) bmtable.getModel();
                        int code = (int) model.getValueAt(currentRow, 0);
                        System.out.println("Row to delete=" + currentRow);
                        System.out.println("code=" + code);
                        model.removeRow(currentRow);
                        bmc.redirect_delect_request(code);
                        JOptionPane.showMessageDialog(null, "Data Deleted from DB");
                    } else {
                        System.out.println("Data not deleted");
                    }
                } else if ("Update".equals(actionType)) {
                    int response = JOptionPane.showConfirmDialog(null, "Do you want to update the data?");
                    if (response == 0) {
                        dispose();

                        new UpdateScreenView(get_branch_id(), get_Branch_name(), get_branch_city(), get_branch_status(), get_branch_address(), get_branch_phoneno(), get_branch_employee_count());
                    } else {
                        System.out.println("Data not updated");
                    }
                }
            }
            isPushed = false;
            return label;
        }
    }


    public int get_branch_id() {
        Object num = bmtable.getValueAt(bmtable.getSelectedRow(), 0);
        return num.hashCode();
    }


    public String get_Branch_name() {
        Object name = bmtable.getValueAt(bmtable.getSelectedRow(), 1);
        return name.toString();
    }


    public String get_branch_city() {
        Object city = bmtable.getValueAt(bmtable.getSelectedRow(), 2);
        return city.toString();
    }


    public String get_branch_status() {
        Object status = bmtable.getValueAt(bmtable.getSelectedRow(), 3);
        return status.toString();
    }


    public String get_branch_address() {
        Object address = bmtable.getValueAt(bmtable.getSelectedRow(), 4);
        return address.toString();
    }


    public String get_branch_phoneno() {
        Object phoneno = bmtable.getValueAt(bmtable.getSelectedRow(), 5);
        return phoneno.toString();
    }

    public int get_branch_employee_count() {
        Object employeecount = bmtable.getValueAt(bmtable.getSelectedRow(), 6);
        return employeecount.hashCode();
    }

    public static void main(String[] args) {
        new BranchManagementView();
    }
}
