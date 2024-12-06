package View;

import Controller.ReturnController;
import Model.LoggedEmp;
import Model.Sale;
import Model.SaleTableModel;

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

    public SaleTableView(String invoice) {
        setTitle("Manage Sales");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Fetch all sales
        List<Sale> sales = returnController.redirect_get_sales(invoice); // Pass a sample invoice number here
        System.out.println("Sales fetched: " + sales.size());

        // Create a custom table model for Sales
        SaleTableModel model = new SaleTableModel(sales);

        // Wrap the model to add action buttons
        JTable table = new JTable(new SaleButtonTableModel(model));
        table.setRowHeight(70);

        // Set custom cell renderer for buttons
        table.getColumn("Actions").setCellRenderer(new ButtonRenderer());
        table.getColumn("Actions").setCellEditor(new ButtonEditor(new JCheckBox(), sales));

        add(new JScrollPane(table), BorderLayout.CENTER);
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
            // Create text fields with current values
            JTextField prodIdField = new JTextField(String.valueOf(saleToUpdate.getProdId()), 20);
            JTextField prodNameField = new JTextField(saleToUpdate.getProdName(), 20);
            JTextField priceField = new JTextField(String.valueOf(saleToUpdate.getPrice()), 20);
            JTextField quantityField = new JTextField(String.valueOf(saleToUpdate.getQuantity()), 20);
            JTextField totalPriceField = new JTextField(String.valueOf(saleToUpdate.getTotalPrice()), 20);
            JTextField invoiceNoField = new JTextField(String.valueOf(saleToUpdate.getInvoiceNumber()), 20);

            // Set the other fields as non-editable
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
                // Update only the quantity field in the sale object
                try {
                    saleToUpdate.setQuantity(Integer.parseInt(quantityField.getText()));

                    System.out.println("loggedEmp.getBranch()\t"+loggedEmp.getBranch());
                    // Optionally, update the database (commented out)
                    // returnController.redirect_sale_Update(saleToUpdate);

                    // Refresh the table to reflect changes
                    ((AbstractTableModel) table.getModel()).fireTableRowsUpdated(row, row);
                    System.out.println("Sale updated successfully.");
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(SaleTableView.this, "Invalid input. Please check your data.");
                }
            }
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



