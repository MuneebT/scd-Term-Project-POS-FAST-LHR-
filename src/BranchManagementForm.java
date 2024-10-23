import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;

public class BranchManagementForm extends JFrame {
    private JTable bmtable;
    private JScrollPane bmScroll;

    public BranchManagementForm() {
        setTitle("Branch Management");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);
        setBounds(100, 100, 800, 600);

        // Table column names
        String[] columnNames = { "Code", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees", "Delete", "Update" };

        // Sample data for the table
        Object[][] data = {
                { "B001", "Branch 1", "New York", "Active", "123 Street", "123-456-7890", 50, "Delete", "Update" },
                { "B002", "Branch 2", "Los Angeles", "Inactive", "456 Avenue", "098-765-4321", 40, "Delete", "Update" },
                { "B003", "Branch 3", "Chicago", "Active", "789 Boulevard", "111-222-3333", 60, "Delete", "Update" }
        };

        // Create the table with data and column names
        bmtable = new JTable(new DefaultTableModel(data, columnNames));

        // Set custom renderer and editor for the last two columns (buttons)
        bmtable.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        bmtable.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox(), "Delete"));

        bmtable.getColumn("Update").setCellRenderer(new ButtonRenderer());
        bmtable.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox(), "Update"));

        // Scroll pane
        bmScroll = new JScrollPane(bmtable);
        bmScroll.setBounds(0, 50, getWidth(), getHeight());

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
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            if (isPushed) {
                // Handle Delete or Update action based on the action type
                if ("Delete".equals(actionType)) {
                    JOptionPane.showMessageDialog(button, "Deleted row: " + label);
                    // Add deletion logic here (e.g., remove the row from the table model)
                    ((DefaultTableModel) bmtable.getModel()).removeRow(bmtable.getSelectedRow());
                } else if ("Update".equals(actionType)) {
                    JOptionPane.showMessageDialog(button, "Update action for row: " + label);
                    // Add update logic here (e.g., open an update form or dialog)

                }
            }
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }

        @Override
        protected void fireEditingStopped() {
            super.fireEditingStopped();
        }
    }

    public static void main(String[] args) {
        new BranchManagementForm();
    }
}
