package View;

import Controller.DataEntryOperatorController;
import Controller.OrderController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ManageOrderView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnAdd;
    private OrderController oc = new OrderController();

    public ManageOrderView() {
        setTitle("Inventory Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600); // Adjusted width after removing vendor columns

        String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "VendorID", "VendorName", "Delete", "Update"};

        Object[][] data = oc.redirectGatherOrderDatarequest();

        // Add Delete and Update as button text in the data
        for (int i = 0; i < data.length; i++) {
            data[i] = new Object[]{
                    data[i][0], data[i][1], data[i][2], data[i][3], data[i][4], "Delete", "Update"
            };
        }

        DefaultTableModel model = new DefaultTableModel(data, columnname) {
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for Delete and Update columns
                return column == 5 || column == 6;
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

        // Creating "Add" button at top-right corner
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdd.setBackground(Color.CYAN);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBounds(getWidth() - 100, 10, 80, 30);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AddOrderView addOrderView = new AddOrderView();
                addOrderView.setVisible(true);  // Make AddOrderView visible
            }
        });

        // Adding components to frame
        add(scrollPane);
        add(btnAdd);

        revalidate();
        repaint();
        setVisible(true);
    }

    public void refreshTable() {
        SwingUtilities.invokeLater(() -> {
            // Fetch updated data from the controller
            Object[][] updatedData = oc.redirectGatherOrderDatarequest();

            // Update the table model
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0); // Clear existing rows
            for (Object[] row : updatedData) {
                Object[] rowData = {
                        row[0], row[1], row[2], row[3], row[4], row[5], "Delete", "Update"
                };
                model.addRow(rowData);
            }
        });
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

            // Action listener for Delete and Update buttons
            button.addActionListener(e -> {
                fireEditingStopped(); // Stop editing before action

                if (label.equals("Delete")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Delete?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the ID of the row and call delete method
                        int id = (Integer) table.getValueAt(row, 0);
                        oc.redirectOrderDeleteRequest(id);
                        ((DefaultTableModel) table.getModel()).removeRow(row); // Remove from UI
                    }
                } else if (label.equals("Update")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Update?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Placeholder for update functionality (implement as needed)
                       new UpdateOrderView(getproductID(),getProductName(),getProductQuantity(),getVendorID()
                       ,getVendorName());
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;

            // Set the button text based on the column index (5 for Delete, 6 for Update)
            if (column == 5) {
                label = "Delete";  // Delete button
            } else if (column == 6) {
                label = "Update";  // Update button
            }

            button.setText(label); // Set the text of the button
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
   public int getproductID(){
        int id= (int) table.getValueAt(table.getSelectedRow(),0);
        return id;
   }
   public String getProductName(){
        String name= (String) table.getValueAt(table.getSelectedRow(),1);
        return name;
   }
   public int getProductQuantity(){
        int quantity=(int) table.getValueAt(table.getSelectedRow(),2);
        return quantity;
   }
   public int getVendorID(){
        int id=(int)table.getValueAt(table.getSelectedRow(),3);
        return id;
   }

    public String getVendorName(){
        String name= (String) table.getValueAt(table.getSelectedRow(),4);
        return name;
    }
}
