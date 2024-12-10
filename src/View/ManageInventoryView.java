package View;

import Controller.InventoryCntroller;
import Model.LoggedEmp;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageInventoryView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnAdd, btnBack;
    private InventoryCntroller ic = new InventoryCntroller();
    private DefaultTableModel model;

    public ManageInventoryView() {
        setTitle("Inventory Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600); // Adjusted width after removing vendor columns

        String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "ProdctCategory", "CostPrice",
                "SalePrice", "BranchID", "Delete", "Update"};

        Object[][] data = ic.redirect_object_array();

        // Create DefaultTableModel
        model = new DefaultTableModel(data, columnname) {
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for Delete and Update columns
                return column == 7 || column == 8;
            }
        };

        table = new JTable(model);

        // Add custom renderers and editors for Delete and Update buttons
        table.getColumn("Delete").setCellRenderer(new ButtonRenderer());
        table.getColumn("Update").setCellRenderer(new ButtonRenderer());
        table.getColumn("Delete").setCellEditor(new ButtonEditor(new JCheckBox()));
        table.getColumn("Update").setCellEditor(new ButtonEditor(new JCheckBox()));

        // Creating scroll pane for the table
        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 40, getWidth(), getHeight() - 40);

        // Creating "Add" button
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdd.setBackground(Color.decode("#415a77"));
        btnAdd.setForeground(Color.white);
        btnAdd.setBounds(getWidth() - 190, 10, 80, 30);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddInventoryView();
                dispose();
            }
        });

        // Creating "Back" button
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 15));
        btnBack.setBackground(Color.decode("#415a77"));
        btnBack.setForeground(Color.white);
        btnBack.setBounds(getWidth() - 100, 10, 80, 30);

        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                LoggedEmp loggedEmp=LoggedEmp.getInstance();
                if(loggedEmp.getDesignation().equals("Branch Manager"))
                {
                    new BMDashboardView();
                }
                else if(loggedEmp.getDesignation().equals("Data Entry Operator"))
                {
                    new DEODashboardView();

                }
                else if(loggedEmp.getDesignation().equals("Cashier"))
                {
                    new CashierDashboard();
                }
            }
        });

        // Adding components to frame
        add(scrollPane);
        add(btnAdd);
        add(btnBack);

        revalidate();
        repaint();
        setVisible(true);
    }

    // ButtonRenderer to render buttons in the Delete and Update columns
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

    // ButtonEditor to handle Delete and Update actions
    class ButtonEditor extends DefaultCellEditor {
        protected JButton button;
        private String label;
        private boolean isPushed;
        private int row;

        public ButtonEditor(JCheckBox checkBox) {
            super(checkBox);
            button = new JButton();
            button.setOpaque(true);

            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing before action

                if (label.equals("Delete")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Delete?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the ID of the row and call delete method
                        int id = (Integer) table.getValueAt(row, 0);
                        ic.redirect_Inventory_delete_request(id);
                        ((DefaultTableModel) table.getModel()).removeRow(row); // Remove from UI
                    }
                } else if (label.equals("Update")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Update?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the data from the selected row
                        int id = (Integer) table.getValueAt(row, 0);
                        int quantity = (Integer) table.getValueAt(row, 2);
                        int costPrice = (Integer) table.getValueAt(row, 4);
                        int salePrice = (Integer) table.getValueAt(row, 5);

                        // Updated to exclude vendor parameters
                        new UpdateInventoryView(id, quantity, costPrice, salePrice, this, model, row);
                        refreshtable();
                    }
                }
            });
        }

        public void refreshtable() {
            String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "ProdctCategory", "CostPrice",
                    "SalePrice", "BranchID", "Delete", "Update"};
            Object[][] data = ic.redirect_object_array();
            model = new DefaultTableModel(data, columnname) {
                public boolean isCellEditable(int row, int column) {
                    // Allow editing only for Delete and Update columns
                    return column == 7 || column == 8;
                }
            };

            table = new JTable(model);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;
            label = (value == null) ? "" : value.toString();
            button.setText(label);
            isPushed = true;
            return button;
        }

        @Override
        public Object getCellEditorValue() {
            isPushed = false;
            return label;
        }

        @Override
        public boolean stopCellEditing() {
            isPushed = false;
            return super.stopCellEditing();
        }
    }

    public static void main(String[] args) {
        new ManageInventoryView();
    }
}
