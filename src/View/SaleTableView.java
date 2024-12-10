package View;

import Controller.ReturnController;
import Model.LoggedEmp;
import Model.Sale;
import Model.SaleTableModel;
import View.CustomerElements.RoundedButton;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class SaleTableView extends JFrame {
    private JTable salesTable;
    private LoggedEmp loggedEmp = LoggedEmp.getInstance();
    private ReturnController returnController = new ReturnController();
    Color customColor = Color.decode("#415a77");

    public SaleTableView(String invoice) {
        setTitle("Manage Sales");
        setSize(1000, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Create a JPanel for the "Back" button
        JPanel backButtonPanel = new JPanel();
        RoundedButton backButton = new RoundedButton("Back");
        backButton.setBackground(customColor);
        backButton.setForeground(Color.white);

        // Set action for the back button (you can modify this logic as needed)
        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Logic for going back, such as closing this window or navigating to another screen
                new ReturnScreenView();
                dispose(); // This will close the current window; you can also navigate to another frame
            }
        });

        backButtonPanel.add(backButton);
        add(backButtonPanel, BorderLayout.EAST); // Add back button panel at the top (NORTH)

        // Fetch all sales
        List<Sale> sales = returnController.redirect_get_sales(invoice);
        System.out.println("Sales fetched: " + sales.size() + " " + sales.get(0).getProdName());

        // Create a custom table model for Sales
        SaleTableModel model = new SaleTableModel(sales);

        // Wrap the model to add action buttons
        JTable table = new JTable(new SaleButtonTableModel(model));
        table.setRowHeight(70);

        // Set custom cell renderer for buttons
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), sales));

        add(new JScrollPane(table), BorderLayout.CENTER); // Add the table at the center
        setVisible(true);
    }


    // Renderer for action buttons
    class ButtonRenderer extends JPanel implements TableCellRenderer {
        private JButton updateButton;

        public ButtonRenderer() {
            setLayout(new FlowLayout(FlowLayout.LEFT));
            updateButton = new JButton("Update");
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
        private JButton updateButton;
        private int row;
        private JTable table;
        private List<Sale> sales;
        private int prevQuantity; // To store the previous quantity before updating

        public ButtonEditor(JCheckBox checkBox, List<Sale> sales) {
            this.sales = sales;
            panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
            updateButton = new JButton("Update");

            // Add action listener to the button
            updateButton.addActionListener(this);
            panel.add(updateButton);
        }

        @Override
        public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
            this.table = table;
            this.row = row;
            prevQuantity = sales.get(row).getQuantity(); // Store the previous quantity
            return panel;
        }

        @Override
        public Object getCellEditorValue() {
            return null;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == updateButton) {
                // Perform update action
                Sale saleToUpdate = sales.get(row);
                System.out.println("Updating sale: " + saleToUpdate.getProdName());
                openUpdateDialog(saleToUpdate);
            }
            fireEditingStopped();
        }

        private void openUpdateDialog(Sale saleToUpdate) {
            // Create fields for updating sale details
            JTextField prodIdField = new JTextField(String.valueOf(saleToUpdate.getProdId()), 20);
            JTextField prodNameField = new JTextField(saleToUpdate.getProdName(), 20);
            JTextField priceField = new JTextField(String.valueOf(saleToUpdate.getPrice()), 20);
            JTextField quantityField = new JTextField(String.valueOf(saleToUpdate.getQuantity()), 20);
            JTextField totalPriceField = new JTextField(String.valueOf(saleToUpdate.getTotalPrice()), 20);
            JTextField invoiceNoField = new JTextField(String.valueOf(saleToUpdate.getInvoiceNumber()), 20);

            prodIdField.setEditable(false);
            prodNameField.setEditable(false);
            priceField.setEditable(false);
            totalPriceField.setEditable(false);
            invoiceNoField.setEditable(false);

            JPanel panel = new JPanel(new GridLayout(6, 2));
            panel.add(new JLabel("Product ID:"));
            panel.add(prodIdField);
            panel.add(new JLabel("Product Name:"));
            panel.add(prodNameField);
            panel.add(new JLabel("Price:"));
            panel.add(priceField);
            panel.add(new JLabel("Quantity:"));
            panel.add(quantityField);
            panel.add(new JLabel("Total Price:"));
            panel.add(totalPriceField);
            panel.add(new JLabel("Invoice No:"));
            panel.add(invoiceNoField);

            int option = JOptionPane.showConfirmDialog(SaleTableView.this, panel, "Update Sale", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                try {
                    String quantityText = quantityField.getText().trim();
                    String priceText = priceField.getText().trim();

                    if (quantityText.isEmpty() || priceText.isEmpty()) {
                        throw new NumberFormatException("Fields cannot be empty.");
                    }

                    double price = Double.parseDouble(priceText);
                    int quantity = Integer.parseInt(quantityText);

                    int total = (int) (price * quantity); // Cast to int if needed for total
                    System.out.println("Total is: " + total);

                    // Update sale object
                    saleToUpdate.setQuantity(quantity);
                    saleToUpdate.setTotalPrice(total);

                    // Calculate the change in quantity and add it back to the inventory
                    int returnedQuantity = prevQuantity - quantity; // Quantity returned by the customer
                    System.out.println("Quantity returned: " + returnedQuantity);

                    // Call function to update sale in the list/database and adjust inventory
                    if(updateSaleInDatabase(saleToUpdate, returnedQuantity))
                    {
                        System.out.println("Sale updated. New Quantity: " + quantity + ", New Total Price: " + total);

                    }
                    else
                    {
                        System.out.println("Sale not updated.\n");

                    }

                    if(updateInventory(saleToUpdate.getProdId(),returnedQuantity,saleToUpdate.getBranchID()))
                    {
                        System.out.println("Inventory updated. ");

                    }
                    else
                    {
                        System.out.println("Inventory not updated.\n");

                    }


                    if(updateInvoice(saleToUpdate.getInvoiceNumber(),saleToUpdate.getBranchID()))
                    {
                        System.out.println("Invoice updated. ");

                    }
                    else
                    {
                        System.out.println("Invoice not updated.\n");

                    }
                    JOptionPane.showMessageDialog(null, "Every thing up to date\n" );




                    ((AbstractTableModel) table.getModel()).fireTableRowsUpdated(row, row);

                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SaleTableView.this, "Invalid input. Please check your data.\n" + ex.getMessage());
                }
            }
        }

        // Function to update sale in the list/database and adjust inventory
        private boolean updateSaleInDatabase(Sale updatedSale, int returnedQuantity) {
            // Placeholder for actual database or data list update logic
            // For now, it updates the sales list directly
            sales.set(row, updatedSale);
            System.out.println(updatedSale.getSaleId() + " " + updatedSale.getProdId() + " " + updatedSale.getProdName() +
                    " " + updatedSale.getPrice() + " " + updatedSale.getTotalPrice() + " " + updatedSale.getInvoiceNumber() +
                    " " + updatedSale.getBranchID());
            boolean res = returnController.redirect_update_sale(updatedSale);
//            if (res) {
//                // Update the inventory by adding back the returned quantity
//                if(updateInventory(updatedSale.getProdId(), returnedQuantity,updatedSale.getBranchID()))
//                {
//                    JOptionPane.showMessageDialog(null, "Inventory Table Updated Successfully\n");
//
//                }
//                else
//                {
//                    JOptionPane.showMessageDialog(null, "Inventory Table cannot be Updated Successfully\n");
//                }
//            }
          //  System.out.println("Sale updated in the database list.");
            return res;
        }

        // Placeholder method for updating inventory logic
        private boolean updateInventory(int productId, int quantityReturned, int branchId) {
            // Add logic here to update the inventory in the database or data list
            System.out.println("Adding " + quantityReturned + " units back to the inventory for Product ID: " + productId);
            if(returnController.redirect_update_inentory(productId,quantityReturned,branchId)){
                return true;
            }
            else
            {
                return false;
            }

        }
        private boolean updateInvoice(int invoiceNo,int branchId)
        {
            double newBill=returnController.redirect_get_Total_bill_sales(invoiceNo, branchId);
            System.out.println("\n\n Total Bill is \t"+newBill);
            double gst=newBill*0.16;
            newBill=newBill+gst;
            double oldBill=returnController.redirect_get_old_bill_sales(invoiceNo,branchId);
            double balance =oldBill-newBill;
            returnController.redirect_update_invoice(invoiceNo,branchId,newBill,gst,balance);
            return true;
        }


    }

    // Wrapper table model to add an "Actions" column
    class SaleButtonTableModel extends AbstractTableModel {
        private SaleTableModel model;

        public SaleButtonTableModel(SaleTableModel model) {
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

    public static void main(String[] args) {
        new SaleTableView("1");
    }
}
