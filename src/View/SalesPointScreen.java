package View;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

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
        JTextField qtyField = new JTextField();
        JTextField priceField = new JTextField();
        JButton addButton = new JButton("Add");

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
        JPanel rightPanel = new JPanel(new GridLayout(4, 2, 10, 10)); // Ensure alignment in a grid format
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel totalLabel = new JLabel("Total:");
        JTextField totalField = new JTextField("0", 10);
        totalField.setHorizontalAlignment(JTextField.RIGHT); // Align text to the right

        JLabel paidLabel = new JLabel("Paid:");
        JTextField paidField = new JTextField(10);
        paidField.setHorizontalAlignment(JTextField.RIGHT);

        JLabel balanceLabel = new JLabel("Balance:");
        JTextField balanceField = new JTextField(10);
        balanceField.setHorizontalAlignment(JTextField.RIGHT);

        JButton finishButton = new JButton("Finish");

        // Add components to right panel
        rightPanel.add(totalLabel);
        rightPanel.add(totalField);
        rightPanel.add(paidLabel);
        rightPanel.add(paidField);
        rightPanel.add(balanceLabel);
        rightPanel.add(balanceField);
        rightPanel.add(new JLabel("")); // Spacer
        rightPanel.add(finishButton);

        frame.add(rightPanel, BorderLayout.EAST);

        // Add Button Action
        addButton.addActionListener(e -> {
            String code = itemCodeField.getText();
            String name = nameField.getText();
            int qty = Integer.parseInt(qtyField.getText());
            int price = Integer.parseInt(priceField.getText());
            int total = qty * price;

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
            totalField.setText(String.valueOf(sum));
        });

        // Clear Sale Button Action
        clearButton.addActionListener(e -> {
            tableModel.setRowCount(0); // Clear table
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
            JOptionPane.showMessageDialog(frame, "Transaction Complete!", "Success", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.setVisible(true);
    }
}
