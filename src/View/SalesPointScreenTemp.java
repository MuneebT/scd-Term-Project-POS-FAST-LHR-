package View;

import Model.*;
import com.mysql.cj.log.Log;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SalesPointScreenTemp {

    public SalesPointScreenTemp()
    {
        createAndShowGUI();
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(SalesPointScreenTemp::createAndShowGUI);
    }

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Billing System");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);





        // Top Panel (Title)
        JPanel titlePanel = new JPanel(new BorderLayout());  // Use BorderLayout instead of FlowLayout
        titlePanel.setBackground(new Color(33, 150, 243)); // Blue color
        JLabel titleLabel = new JLabel("Billing System");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Roboto", Font.BOLD, 28));
        titlePanel.add(titleLabel, BorderLayout.WEST);  // Place title on the left

// Create the Back button
        JButton backButton = new JButton("Back");
        backButton.setBackground(new Color(244, 67, 54)); // Red color
        backButton.setForeground(Color.WHITE);
        backButton.setFont(new Font("Arial", Font.BOLD, 14));
        backButton.setFocusPainted(false);
        backButton.setBorder(BorderFactory.createRaisedBevelBorder());
        backButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(229, 57, 53)); // Darker Red on Hover
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                backButton.setBackground(new Color(244, 67, 54)); // Red color when not hovering
            }
        });

// Back button action listener (Closes the current window)
        backButton.addActionListener(e -> {
            LoggedEmp loggedEmp=LoggedEmp.getInstance();
            if(loggedEmp.getDesignation().equals("Branch Manager"))
            {
                new BMDashboardView();
            }
            else
            {
                new CashierDashboard();
            }


        });


        titlePanel.add(backButton, BorderLayout.EAST);  // Place back button on the right

        frame.add(titlePanel, BorderLayout.NORTH);







        String[] columnNames = {"Code", "Name", "Qty", "Price", "Total"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
        JTable table = new JTable(tableModel);
        table.setRowHeight(30);
        table.getTableHeader().setBackground(new Color(33, 150, 243)); // Blue header
        table.getTableHeader().setForeground(Color.WHITE);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        JScrollPane tableScrollPane = new JScrollPane(table);
        frame.add(tableScrollPane, BorderLayout.CENTER);




        JPanel bottomPanel = new JPanel(new BorderLayout());
        bottomPanel.setBackground(Color.WHITE);

        // Input Fields
        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 10, 10));
        inputPanel.setBackground(Color.WHITE);
        JTextField itemCodeField = new JTextField();
        JTextField nameField = new JTextField();
        nameField.setEditable(false);
        JTextField qtyField = new JTextField();
        JTextField priceField = new JTextField();
        priceField.setEditable(false);
        JButton addButton = new JButton("Add Item");

        // Style buttons
        addButton.setBackground(new Color(76, 175, 80)); // Green
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.setFocusPainted(false);
        addButton.setBorder(BorderFactory.createRaisedBevelBorder());
        addButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(67, 160, 71));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                addButton.setBackground(new Color(76, 175, 80));
            }
        });

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


        JButton clearButton = new JButton("Clear Sale");
        clearButton.setBackground(new Color(244, 67, 54)); // Red
        clearButton.setForeground(Color.WHITE);
        clearButton.setFont(new Font("Arial", Font.BOLD, 14));
        clearButton.setFocusPainted(false);
        clearButton.setBorder(BorderFactory.createRaisedBevelBorder());
        clearButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(229, 57, 53));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                clearButton.setBackground(new Color(244, 67, 54));
            }
        });
        JPanel clearPanel = new JPanel();
        clearPanel.add(clearButton);
        bottomPanel.add(clearPanel, BorderLayout.SOUTH);

        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Right Panel (Paid, Balance, and Finish Button)
        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        rightPanel.setBackground(Color.WHITE);
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalLabel = new JLabel("Total:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField totalField = new JTextField("0", 10);
        totalField.setHorizontalAlignment(JTextField.RIGHT);
        totalField.setEditable(false);

        JLabel paidLabel = new JLabel("Paid:");
        paidLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField paidField = new JTextField(10);
        paidField.setHorizontalAlignment(JTextField.RIGHT);

        JLabel balanceLabel = new JLabel("Balance:");
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 14));
        JTextField balanceField = new JTextField(10);
        balanceField.setHorizontalAlignment(JTextField.RIGHT);
        balanceField.setEditable(false);

        JButton finishButton = new JButton("Finish");
        finishButton.setBackground(new Color(33, 150, 243)); // Blue
        finishButton.setForeground(Color.WHITE);
        finishButton.setFont(new Font("Arial", Font.BOLD, 14));
        finishButton.setFocusPainted(false);
        finishButton.setBorder(BorderFactory.createRaisedBevelBorder());
        finishButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                finishButton.setBackground(new Color(30, 136, 229));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                finishButton.setBackground(new Color(33, 150, 243));
            }
        });

        rightPanel.add(totalLabel);
        rightPanel.add(totalField);
        rightPanel.add(paidLabel);
        rightPanel.add(paidField);
        rightPanel.add(balanceLabel);
        rightPanel.add(balanceField);
        rightPanel.add(new JLabel(""));
        rightPanel.add(finishButton);

        frame.add(rightPanel, BorderLayout.EAST);

        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    // Get values from the selected row
                    String code = tableModel.getValueAt(selectedRow, 0).toString();
                    String name = tableModel.getValueAt(selectedRow, 1).toString();
                    String qty = tableModel.getValueAt(selectedRow, 2).toString();
                    String price = tableModel.getValueAt(selectedRow, 3).toString();

                    // Populate input fields
                    itemCodeField.setText(code);
                    nameField.setText(name);
                    qtyField.setText(qty);
                    priceField.setText(price);

                    // Remove the selected row from the table
                    tableModel.removeRow(selectedRow);

                    // Recalculate total
                    int sum = 0;
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        sum += (int) tableModel.getValueAt(i, 4);
                    }
                    totalField.setText(String.valueOf(sum + (sum * 0.16))); // Update total with GST
                }
            }
        });


        table.setDefaultEditor(Object.class, null);



        addButton.addActionListener(e -> {
            try {
                String code = itemCodeField.getText();
                String name = nameField.getText();
                int qty = Integer.parseInt(qtyField.getText()); // Try-catch for qty input
                int price = Integer.parseInt(priceField.getText()); // Try-catch for price input
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
                    try {
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
                            totalField.setText(String.valueOf(sum + (sum * 0.16))); // Add GST
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insufficient quantity or product not found!", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Invalid number format!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input for qty or price!", "Error", JOptionPane.ERROR_MESSAGE);
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
                totalAmount = totalAmount - gst;
                double balance = paid - totalBill;

                balanceField.setText(String.format("%.2f", balance));
                LoggedEmp loggedEmp= LoggedEmp.getInstance();


                int BRANCH_CODE=Integer.parseInt(loggedEmp.getBranch());

                // Database operations
                try {
                    InvoiceDAO invoiceDAO = new InvoiceDAO();
                    int invoiceNumber = invoiceDAO.createInvoice(totalBill, gst, totalAmount, balance,BRANCH_CODE);

                    SaleDAO saleDAO = new SaleDAO();
                    for (int i = 0; i < tableModel.getRowCount(); i++) {
                        String code = (String) tableModel.getValueAt(i, 0);
                        String name = (String) tableModel.getValueAt(i, 1);
                        int qty = (int) tableModel.getValueAt(i, 2);
                        double price = Double.parseDouble(String.valueOf(tableModel.getValueAt(i, 3)));
                        double totalPrice = Double.parseDouble(String.valueOf(tableModel.getValueAt(i, 4)));

                        saleDAO.createSale(Integer.parseInt(code), name, price, qty, totalPrice, invoiceNumber,BRANCH_CODE);
                    }

                    JOptionPane.showMessageDialog(frame, "Transaction Complete!", "Success", JOptionPane.INFORMATION_MESSAGE);

                    tableModel.setRowCount(0);
                    totalField.setText("0");
                    paidField.setText("");
                    balanceField.setText("");
                } catch (Exception dbEx) {
                    JOptionPane.showMessageDialog(frame, "Database Error: " + dbEx.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Invalid input!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        frame.setVisible(true);
    }
}
