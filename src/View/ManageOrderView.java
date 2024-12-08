package View;

import Connection.InternetConnectionChecker;
import Controller.DataEntryOperatorController;
import Controller.OrderController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.LinkedList;

public class ManageOrderView extends JFrame {
    private JTable table;
    private JScrollPane scrollPane;
    private JButton btnAdd;
    private OrderController oc = new OrderController();
    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public ManageOrderView() {
        setTitle("Inventory Management");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);
        setLayout(null);
        setBounds(100, 100, 900, 600); // Adjusted width after removing vendor columns

        String[] columnname = {"ProductID", "ProductName", "ProdctQuantity", "VendorID", "VendorName","BranchID", "Delete", "Update"};

        Object[][] data=null;
        boolean isconnected=icc.startChecking();
        if(isconnected) {
             data = oc.redirectGatherOrderDatarequest();
        }
        else{
        data=readorderdatafromfile();
        }


        DefaultTableModel model = new DefaultTableModel(data, columnname) {
            public boolean isCellEditable(int row, int column) {
                // Allow editing only for Delete and Update columns
                return column == 6 || column == 7;
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
                            oc.redirectOrderDeleteRequest(id);
                            ((DefaultTableModel) table.getModel()).removeRow(row); // Remove from UI
                        }
                        else{
                            storedatatodeleteintempfile(id);
                        }
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


                                     new UpdateOrderView(productId, productName, productQuantity, vendorId, vendorName);


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

    public Object[][] readorderdatafromfile() {
        BufferedReader br = null;
        Object[][] data = null;

        try {
            br = new BufferedReader(new FileReader("order.txt"));

            // Read all lines into a list
            LinkedList<String[]> lines = new LinkedList<>();
            String line;

            while ((line = br.readLine()) != null) {
                // Split each line by comma and add to the list
                String[] parts = line.split(",");
                if (parts.length == 6) { // Ensure line contains exactly six parts
                    lines.add(parts);
                }
            }

            // Convert the LinkedList to an Object[][]
            data = new Object[lines.size()][8];
            for (int i = 0; i < lines.size(); i++) {
                data[i][0] = Integer.parseInt(lines.get(i)[0]);
                data[i][1] = lines.get(i)[1];
                data[i][2] = Integer.parseInt(lines.get(i)[2]);
                data[i][3] = Integer.parseInt(lines.get(i)[3]);
                data[i][4] = lines.get(i)[4];
                data[i][5] = Integer.parseInt(lines.get(i)[5]);
                data[i][6]="Delete";
                data[i][7]="Update";
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
    public void storedatatodeleteintempfile(int id){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("deletemanageorderview.txt",true));
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

    public static void main(String[] args) {
        new ManageOrderView();
    }

}
