package View;

import Controller.OrderController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;

public class ManageOrderView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnAdd;
    private JButton btnBack; // Back button
    private OrderController oc = new OrderController();
    private DefaultTableModel model;

    public ManageOrderView() {
        setTitle("Order Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600);

        String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "VendorID", "VendorName", "BranchID", "Delete", "Update"};
        Object[][] data = oc.redirectGatherOrderDatarequest();

        model = new DefaultTableModel(data, columnname) {
            public boolean isCellEditable(int row, int column) {
                return column == 6 || column == 7; // Allow editing for Delete and Update columns
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
        scrollPane.setBounds(0, 40, getWidth(), getHeight() - 80);

        // Creating "Add" button at top-right corner
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdd.setBackground(Color.CYAN);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBounds(getWidth() - 200, 10, 80, 30);

        btnAdd.addActionListener(e -> {
            AddOrderView addOrderView = new AddOrderView();
            addOrderView.setVisible(true); // Make AddOrderView visible
        });

        // Creating "Back" button
        btnBack = new JButton("Back");
        btnBack.setFont(new Font("Arial", Font.BOLD, 15));
        btnBack.setBackground(Color.LIGHT_GRAY);
        btnBack.setForeground(Color.BLACK);
        btnBack.setBounds(getWidth() - 100, 10, 80, 30);

        btnBack.addActionListener(e -> {
            new DEODashboardView();
            dispose();
        });

        // Adding components to frame
        add(scrollPane);
        add(btnAdd);
        add(btnBack);

        revalidate();
        repaint();
        setVisible(true);
    }

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

                    if (row >= 0) {
                        try {
                            int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Delete?");
                            if (confirm == JOptionPane.YES_OPTION) {
                                int productId = getproductID();
                                String productName = getProductName();
                                int productQuantity = getProductQuantity();
                                int vendorId = getVendorID();
                                String vendorName = getVendorName();

                                // Ensure that no fields are null or invalid
                                if (productId != -1 && productName != null && productQuantity >= 0 && vendorId != -1 && vendorName != null) {
                                    new UpdateOrderView(productId, productName, productQuantity, vendorId, vendorName,model,row);
                                } else {
                                    JOptionPane.showMessageDialog(null, "Invalid data for update.");
                                }

                            }

                        }
                        catch(Exception ex){
                            JOptionPane.showMessageDialog(null, "Error retrieving data: " + ex.getMessage());

                        }
                    }else {
                        JOptionPane.showMessageDialog(null, "No row selected or data is incomplete.");
                    }
                }
            });
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.row = row;

            // Set the button text based on the column index (6 for Delete, 7 for Update)
            if (column == 6) {
                label = "Delete";  // Delete button
            } else if (column == 7) {
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
    public static void main(String[] args) {
        new ManageOrderView();
    }
}
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







