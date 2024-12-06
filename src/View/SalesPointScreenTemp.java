//package View;
//
//import Model.SaleDAO;
//import Model.InvoiceDAO;
//import Model.Inventory;
//import Model.InventoryDAO;
//
//import javax.swing.*;
//import javax.swing.table.DefaultTableModel;
//import java.awt.*;
//import java.awt.event.KeyAdapter;
//import java.awt.event.KeyEvent;
//
//public class SalesPointScreenTemp {
//
//    // Constants for styling
//    private static final Color PRIMARY_COLOR = new Color(33, 150, 243); // Blue
//    private static final Color SUCCESS_COLOR = new Color(76, 175, 80); // Green
//    private static final Color ERROR_COLOR = new Color(244, 67, 54); // Red
//    private static final Font TITLE_FONT = new Font("Roboto", Font.BOLD, 28);
//    private static final Font BUTTON_FONT = new Font("Arial", Font.BOLD, 14);
//    private static final Font FIELD_FONT = new Font("Arial", Font.PLAIN, 14);
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(SalesPointScreenTemp::createAndShowGUI);
//    }
//
//    private static void createAndShowGUI() {
//        JFrame frame = new JFrame("Billing System");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(1000, 700);
//        frame.setLayout(new BorderLayout());
//        frame.setLocationRelativeTo(null);
//
//        // Top Panel (Title)
//        frame.add(createTitlePanel(), BorderLayout.NORTH);
//
//        // Center Panel (Table)
//        DefaultTableModel tableModel = new DefaultTableModel(new String[]{"Code", "Name", "Qty", "Price", "Total"}, 0);
//        frame.add(createTablePanel(tableModel), BorderLayout.CENTER);
//
//        // Bottom Panel (Inputs and Clear Button)
//        JTextField itemCodeField = new JTextField();
//        JTextField nameField = new JTextField();
//        JTextField qtyField = new JTextField();
//        JTextField priceField = new JTextField();
//        JTextField totalField = new JTextField("0", 10);
//        JTextField paidField = new JTextField();
//        JTextField balanceField = new JTextField();
//        nameField.setEditable(false);
//        priceField.setEditable(false);
//        totalField.setEditable(false);
//        balanceField.setEditable(false);
//
//        frame.add(createBottomPanel(tableModel, itemCodeField, nameField, qtyField, priceField, totalField, paidField, balanceField), BorderLayout.SOUTH);
//
//        // Right Panel (Paid, Balance, and Finish Button)
//        frame.add(createRightPanel(totalField, paidField, balanceField, tableModel, frame), BorderLayout.EAST);
//
//        frame.setVisible(true);
//    }
//
//    private static JPanel createTitlePanel() {
//        JPanel titlePanel = new JPanel();
//        titlePanel.setBackground(PRIMARY_COLOR);
//        JLabel titleLabel = new JLabel("Billing System");
//        titleLabel.setForeground(Color.WHITE);
//        titleLabel.setFont(TITLE_FONT);
//        titlePanel.add(titleLabel);
//        return titlePanel;
//    }
//
//    private static JScrollPane createTablePanel(DefaultTableModel tableModel) {
//        JTable table = new JTable(tableModel);
//        table.setRowHeight(30);
//        table.setFont(FIELD_FONT);
//        table.getTableHeader().setBackground(PRIMARY_COLOR);
//        table.getTableHeader().setForeground(Color.WHITE);
//        return new JScrollPane(table);
//    }
//
//    private static JPanel createBottomPanel(DefaultTableModel tableModel, JTextField itemCodeField, JTextField nameField, JTextField qtyField, JTextField priceField, JTextField totalField, JTextField paidField, JTextField balanceField) {
//        JPanel bottomPanel = new JPanel(new BorderLayout());
//
//        JPanel inputPanel = new JPanel(new GridLayout(1, 5, 10, 10));
//        inputPanel.setBackground(Color.WHITE);
//
//        JButton addButton = createButton("Add Item", SUCCESS_COLOR, evt -> handleAddItem(itemCodeField, nameField, qtyField, priceField, tableModel, totalField));
//        JButton clearButton = createButton("Clear Sale", ERROR_COLOR, evt -> clearSale(tableModel, itemCodeField, nameField, qtyField, priceField, totalField, paidField, balanceField));
//
//        itemCodeField.addKeyListener(new KeyAdapter() {
//            @Override
//            public void keyPressed(KeyEvent e) {
//                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
//                    fetchProductDetails(itemCodeField, nameField, priceField);
//                }
//            }
//        });
//
//        inputPanel.add(createLabeledField("Item Code", itemCodeField));
//        inputPanel.add(createLabeledField("Name", nameField));
//        inputPanel.add(createLabeledField("Qty", qtyField));
//        inputPanel.add(createLabeledField("Price", priceField));
//        inputPanel.add(addButton);
//
//        JPanel clearPanel = new JPanel();
//        clearPanel.add(clearButton);
//
//        bottomPanel.add(inputPanel, BorderLayout.CENTER);
//        bottomPanel.add(clearPanel, BorderLayout.SOUTH);
//
//        return bottomPanel;
//    }
//
//    private static JPanel createRightPanel(JTextField totalField, JTextField paidField, JTextField balanceField, DefaultTableModel tableModel, JFrame frame) {
//        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10));
//        rightPanel.setBackground(Color.WHITE);
//
//        JButton finishButton = createButton("Finish", PRIMARY_COLOR, evt -> handleFinishSale(totalField, paidField, balanceField, tableModel, frame));
//
//        rightPanel.add(createLabeledField("Total:", totalField));
//        rightPanel.add(createLabeledField("Paid:", paidField));
//        rightPanel.add(createLabeledField("Balance:", balanceField));
//        rightPanel.add(new JLabel(""));
//        rightPanel.add(finishButton);
//
//        return rightPanel;
//    }
//
//    private static JButton createButton(String text, Color color, java.awt.event.ActionListener action) {
//        JButton button = new JButton(text);
//        button.setBackground(color);
//        button.setForeground(Color.WHITE);
//        button.setFont(BUTTON_FONT);
//        button.setFocusPainted(false);
//        button.setBorder(BorderFactory.createRaisedBevelBorder());
//        button.addActionListener(action);
//        button.addMouseListener(new java.awt.event.MouseAdapter() {
//            public void mouseEntered(java.awt.event.MouseEvent evt) {
//                button.setBackground(color.darker());
//            }
//
//            public void mouseExited(java.awt.event.MouseEvent evt) {
//                button.setBackground(color);
//            }
//        });
//        return button;
//    }
//
//    private static JPanel createLabeledField(String label, JTextField textField) {
//        JPanel panel = new JPanel(new BorderLayout());
//        panel.add(new JLabel(label), BorderLayout.NORTH);
//        panel.add(textField, BorderLayout.CENTER);
//        return panel;
//    }
//
//    private static void handleAddItem(JTextField itemCodeField, JTextField nameField, JTextField qtyField, JTextField priceField, DefaultTableModel tableModel, JTextField totalField) {
//        try {
//            String code = itemCodeField.getText();
//            String name = nameField.getText();
//            int qty = Integer.parseInt(qtyField.getText());
//            double price = Double.parseDouble(priceField.getText());
//            double total = qty * price;
//
//            // Duplicate check
//            for (int i = 0; i < tableModel.getRowCount(); i++) {
//                if (tableModel.getValueAt(i, 0).equals(code)) {
//                    JOptionPane.showMessageDialog(null, "This product is already in the sale.", "Duplicate Entry", JOptionPane.ERROR_MESSAGE);
//                    return;
//                }
//            }
//
//            tableModel.addRow(new Object[]{code, name, qty, price, total});
//            updateTotalField(tableModel, totalField);
//
//            itemCodeField.setText("");
//            nameField.setText("");
//            qtyField.setText("");
//            priceField.setText("");
//
//        } catch (NumberFormatException ex) {
//            JOptionPane.showMessageDialog(null, "Invalid input for quantity or price!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private static void updateTotalField(DefaultTableModel tableModel, JTextField totalField) {
//        double sum = 0;
//        for (int i = 0; i < tableModel.getRowCount(); i++) {
//            sum += (double) tableModel.getValueAt(i, 4);
//        }
//        totalField.setText(String.valueOf(sum));
//    }
//
//    private static void fetchProductDetails(JTextField itemCodeField, JTextField nameField, JTextField priceField) {
//        try {
//            int itemCode = Integer.parseInt(itemCodeField.getText());
//            InventoryDAO inventoryDAO = new InventoryDAO();
//            Inventory product = inventoryDAO.getProductById(itemCode);
//            if (product != null && product.getProductQuantity() > 0) {
//                nameField.setText(product.getProductName());
//                priceField.setText(String.valueOf(product.getSalePrice()));
//            } else {
//                JOptionPane.showMessageDialog(null, "Product not found or out of stock!", "Error", JOptionPane.ERROR_MESSAGE);
//            }
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(null, "Invalid item code!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private static void handleFinishSale(JTextField totalField, JTextField paidField, JTextField balanceField, DefaultTableModel tableModel, JFrame frame) {
//        try {
//            double total = Double.parseDouble(totalField.getText());
//            double paid = Double.parseDouble(paidField.getText());
//            if (paid < total) {
//                JOptionPane.showMessageDialog(frame, "Paid amount cannot be less than total!", "Error", JOptionPane.ERROR_MESSAGE);
//                return;
//            }
//            double balance = paid - total;
//            balanceField.setText(String.valueOf(balance));
//
//            SaleDAO saleDAO = new SaleDAO();
//            saleDAO.saveSale(tableModel);
//
//            JOptionPane.showMessageDialog(frame, "Sale completed successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
//
//            // Clear sale for next transaction
//            clearSale(tableModel, null, null, null, null, totalField, paidField, balanceField);
//
//        } catch (NumberFormatException e) {
//            JOptionPane.showMessageDialog(frame, "Invalid input for paid amount!", "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    private static void clearSale(DefaultTableModel tableModel, JTextField itemCodeField, JTextField nameField, JTextField qtyField, JTextField priceField, JTextField totalField, JTextField paidField, JTextField balanceField) {
//        tableModel.setRowCount(0);
//        if (itemCodeField != null) itemCodeField.setText("");
//        if (nameField != null) nameField.setText("");
//        if (qtyField != null) qtyField.setText("");
//        if (priceField != null) priceField.setText("");
//        totalField.setText("0");
//        paidField.setText("");
//        balanceField.setText("");
//    }
//}
