package View;

import Model.SaleDAO;
import Model.InvoiceDAO;
import Model.Sale;
import Model.Inventory;
import Model.InventoryDAO;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SalesPointScreen {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesPointScreen::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setLayout(new BorderLayout());

        // Top Panel (Title)
        JPanel titlePanel = new JPanel();
        titlePanel.setBackground(Color.BLUE);
        JLabel titleLabel = new JLabel("Billing");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        frame.add(titlePanel, BorderLayout.NORTH);

        // Center Panel (Table)
        String[] columnNames = {"Code", "Name", "Qty", "Price", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.CENTER);

        // Bottom Panel (Inputs and Clear Button)
        JPanel bottomPanel = new JPanel(new BorderLayout());

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        JTextField itemCodeField = new JTextField();
        JTextField nameField = new JTextField();
        nameField.setEditable(false); // Make name field uneditable
        JTextField qtyField = new JTextField();
        JTextField priceField = new JTextField();
        priceField.setEditable(false); // Make price field uneditable
        JButton addButton = new JButton("Add");

        // Fetch product details when Enter is pressed in itemCodeField
        itemCodeField.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    String itemCodeText = itemCodeField.getText();
                    if (!itemCodeText.isEmpty()) {
                        try {
                            int itemCode = Integer.parseInt(itemCodeText);

                            InventoryDAO inventoryDAO = new InventoryDAO();
                            Inventory product = inventoryDAO.getProductById(itemCode);

                            if (product != null) {
                                if (product.getProductQuantity() == 0) {
                                    JOptionPane.showMessageDialog(frame, "Product is out of stock!", "Error", JOptionPane.ERROR_MESSAGE);
                                } else {
                                    nameField.setText(product.getProductName());
                                    priceField.setText(String.valueOf(product.getSalePrice()));
                                }
                            } else {
                                JOptionPane.showMessageDialog(frame, "Product not found!", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (NumberFormatException ex) {
                            JOptionPane.showMessageDialog(frame, "Invalid Item Code!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(frame, "Item Code cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        inputPanel.add(new JLabel("Item Code"));
        inputPanel.add(itemCodeField);
        inputPanel.add(new JLabel("Name"));
        inputPanel.add(nameField);
        inputPanel.add(new JLabel("Qty"));
        inputPanel.add(qtyField);
        inputPanel.add(new JLabel("Price"));
        inputPanel.add(priceField);
        inputPanel.add(addButton);

        bottomPanel.add(inputPanel, BorderLayout.CENTER);

        // Clear Button
        JButton clearButton = new JButton("Clear Sale");
        JPanel clearPanel = new JPanel();
        clearPanel.add(clearButton);
        bottomPanel.add(clearPanel, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Right Panel (Paid, Balance, and Finish Button)
        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalLabel = new JLabel("Total:");
        JTextField totalField = new JTextField("0", 10);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalField.setEditable(false); // Make total field uneditable

        JLabel paidLabel = new JLabel("Paid:");
        JTextField paidField = new JTextField(10);
        paidField.setHorizontalAlignment(JTextField.RIGHT);

        JLabel balanceLabel = new JLabel("Balance:");
        JTextField balanceField = new JTextField(10);
        balanceField.setHorizontalAlignment(JTextField.RIGHT);
        balanceField.setEditable(false); // Make balance field uneditable

        JButton finishButton = new JButton("Finish");

        // Add components to right panel
        rightPanel.add(totalLabel);
        rightPanel.add(totalField);
        rightPanel.add(paidLabel);
        rightPanel.add(paidField);
        rightPanel.add(balanceLabel);
        rightPanel.add(balanceField);
        rightPanel.add(new JLabel(""));
        rightPanel.add(finishButton);

        frame.add(rightPanel, BorderLayout.EAST);

        // Add Button Action
        addButton.addActionListener(e -> {
            try {
                String code = itemCodeField.getText();
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText());
                int price = Integer.parseInt(priceField.getText());
                int total = qty * price;

                // Check for duplicate products
                boolean duplicate = false;
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    if (tableModel.getValueAt(i, 0).equals(code)) {
                        duplicate = true;
                        break;
                    }
                }

                if (duplicate) {
                    JOptionPane.showMessageDialog(frame, "This product is already in the sale table.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    InventoryDAO inventoryDAO = new InventoryDAO();
                    Inventory product = inventoryDAO.getProductById(Integer.parseInt(code));

                    if (product != null && product.getProductQuantity() >= qty) {
                        tableModel.addRow(new Object[]{code, name, qty, price, total});
                        itemCodeField.setText("");
                        nameField.setText("");
                        qtyField.setText("");
                        priceField.setText("");

                        // Update total
                        int sum = 0;
                        for (int i = 0; i < tableModel.getRowCount(); i++) {
                            sum += (int) tableModel.getValueAt(i, 4);
                        }
                        totalField.setText(String.valueOf(sum+(sum*0.16)));
                    } else {
                        JOptionPane.showMessageDialog(frame, "Insufficient quantity in inventory!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Clear Sale Button Action
        clearButton.addActionListener(e -> {
            tableModel.setRowCount(0);
            itemCodeField.setText("");
            nameField.setText("");
            qtyField.setText("");
            priceField.setText("");
            totalField.setText("0");
            paidField.setText("");
            balanceField.setText("");
        });

        // Finish Button Action
        finishButton.addActionListener(e -> {
            try {
                double totalAmount = Double.parseDouble(totalField.getText());
                double paid = Double.parseDouble(paidField.getText());

                if (paid < totalAmount) {
                    JOptionPane.showMessageDialog(frame, "Amount Paid cannot be less than Total Bill!", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double gst = totalAmount * 0.16;
                double totalBill = totalAmount;
                totalAmount=totalAmount-gst;
                double balance = paid - totalBill;

                balanceField.setText(String.format("%.2f", balance));

                InvoiceDAO invoiceDAO = new InvoiceDAO();
                int invoiceNumber = invoiceDAO.createInvoice(totalBill, gst, totalAmount, balance,1);

                SaleDAO saleDAO = new SaleDAO();
                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    String code = (String) tableModel.getValueAt(i, 0);
                    String name = (String) tableModel.getValueAt(i, 1);
                    int qty = (int) tableModel.getValueAt(i, 2);
                    double price = Double.parseDouble(String.valueOf(tableModel.getValueAt(i, 3)));
                    double totalPrice = Double.parseDouble(String.valueOf(tableModel.getValueAt(i, 4)));

                    saleDAO.createSale(Integer.parseInt(code), name, price, qty, totalPrice, invoiceNumber,1);
                }

                JOptionPane.showMessageDialog(frame, "Transaction Complete!", "Success", JOptionPane.INFORMATION_MESSAGE);

                tableModel.setRowCount(0);
                totalField.setText("0");
                paidField.setText("");
                balanceField.setText("");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}
