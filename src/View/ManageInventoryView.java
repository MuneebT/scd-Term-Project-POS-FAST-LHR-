package View;

import Connection.InternetConnectionChecker;
import Controller.DataEntryOperatorController;
import Controller.InventoryCntroller;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class ManageInventoryView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnAdd;
    private InventoryCntroller ic = new InventoryCntroller();

    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public ManageInventoryView() {
        setTitle("Inventory Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600); // Adjusted width after removing vendor columns

        String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "ProdctCategory", "CostPrice",
                "SalePrice","BranchID", "Delete", "Update"};
boolean isconnected=icc.startChecking();
        Object[][] data=null;
if(isconnected) {
     data = ic.redirect_object_array();
}
else{
    data=readinventorydata();
}
        DefaultTableModel model = new DefaultTableModel(data, columnname) {
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

        // Creating "Add" button at top-right corner
        btnAdd = new JButton("Add");
        btnAdd.setFont(new Font("Arial", Font.BOLD, 15));
        btnAdd.setBackground(Color.CYAN);
        btnAdd.setForeground(Color.BLACK);
        btnAdd.setBounds(getWidth() - 100, 10, 80, 30);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new AddInventoryView();
            }
        });

        // Adding components to frame
        add(scrollPane);
        add(btnAdd);

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
                fireEditingStopped();

                if (label.equals("Delete")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Delete?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the ID of the row and call delete method
                        int id = (Integer) table.getValueAt(row, 0);
                        boolean isconnected=icc.startChecking();
                        if(isconnected) {
                            ic.redirect_Inventory_delete_request(id);
                            ((DefaultTableModel) table.getModel()).removeRow(row); // Remove from UI
                        }
                        else{
                            storeinventorydeletedata(id);
                        }
                    }
                } else if (label.equals("Update")) {
                    int confirm = JOptionPane.showConfirmDialog(null, "Do you want to Update?");
                    if (confirm == JOptionPane.YES_OPTION) {
                        // Get the data from the selected row
                        int id = (Integer) table.getValueAt(row, 0);
                        int quantity = (Integer) table.getValueAt(row, 2);
                        int costPrice = (Integer) table.getValueAt(row, 4);
                        int salePrice = (Integer) table.getValueAt(row, 5);

                            new UpdateInventoryView(id, quantity, costPrice, salePrice);


                    }
                }
            });
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
    public void storeinventorydeletedata(int id){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("deletemanageinventoryview.txt",true));
            bw.write(id);
            bw.newLine();
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public Object[][] readinventorydata() {
        BufferedReader br = null;
        Object[][] data = null;
        try {
            br = new BufferedReader(new FileReader("inventory.txt"));

            // Read all lines into a list
            LinkedList<String[]> lines = new LinkedList<>();
            String line;

            while ((line = br.readLine()) != null) {
                // Split each line by comma and add to the list
                String[] parts = line.split(",");
                if (parts.length == 5) { // Ensure line contains exactly five parts
                    lines.add(parts);
                }
            }

            // Convert the LinkedList to an Object[][]
            data = new Object[lines.size()][7];
            for (int i = 0; i < lines.size(); i++) {
                data[i][0] = Integer.parseInt(lines.get(i)[0]); // Product ID
                data[i][1] = lines.get(i)[1];                  // Product Name
                data[i][2] = Integer.parseInt(lines.get(i)[2]); // Product Quantity
                data[i][3] = Double.parseDouble(lines.get(i)[3]); // Cost Price
                data[i][4] = Double.parseDouble(lines.get(i)[4]); // Sale Price
                data[i][5]="Delete";
                data[i][6]="Update";
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

}