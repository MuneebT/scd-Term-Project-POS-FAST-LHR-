package View;

import Controller.BranchManagementController;
import Controller.InventoryCntroller;
import Controller.OrderController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class SearchOrderView extends JFrame {
    private JTable table;
    private OrderController oc=new OrderController();
    private JScrollPane sp;
    private JButton btnsearch;

    private JComboBox<String> citynames, branchnames;

    public SearchOrderView() {
        setTitle("Search Screen");
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(100, 100, 800, 600);
        setLayout(null);  // Using null layout

        // Set up search field combo box for Branch Names
        LinkedList<String> productnamedata = oc.returnProductNames();
        String[] data = copy_data(productnamedata);
        branchnames = new JComboBox<>(data);
        branchnames.setForeground(Color.GRAY);
        JScrollPane sp1 = new JScrollPane(branchnames);
        sp1.setBounds(20, 20, 200, 30);

        // Set up search field combo box for City Names
        LinkedList<String> productCategory = oc.returnVendorNames();
        String[] vendorname = copy_data(productCategory);
        citynames = new JComboBox<>(vendorname);
        citynames.setForeground(Color.GRAY);
        JScrollPane cityscroll = new JScrollPane(citynames);
        cityscroll.setBounds(550, 20, 200, 30);

        // Set up the table model and table
        String[] fields = {"ProductID", "ProductName", "ProductQUantity", "VendorID", "VendorName", "BranchID"};
        Object[][] data1 = oc.redirectGatherOrderDatarequest(); // Initial data
        table = new JTable(new DefaultTableModel(data1, fields));
        sp = new JScrollPane(table);
        sp.setBounds(0, 70, getWidth(), getHeight());

        // Search button settings
        btnsearch = new JButton("Search");
        btnsearch.setFont(new Font("Arial", Font.BOLD, 14));
        btnsearch.setForeground(Color.GRAY);
        btnsearch.setBounds(350, 20, 100, 30);
        btnsearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String productname = branchnames.getSelectedItem().toString();
                String vendorname = citynames.getSelectedItem().toString();

                Object[][] updatedData = oc.returnspecificorderdata(productname,vendorname);

                reloadTable(updatedData);
            }
        });


        add(sp1);
        add(cityscroll);
        add(btnsearch);
        add(sp);

        setVisible(true);
    }


    private void reloadTable(Object[][] data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setDataVector(data, new Object[]{"ID", "Name", "City", "Status", "Address", "PhoneNo", "No of Employees"});
    }

    private String[] copy_data(LinkedList<String> data) {
        String[] copieddata = new String[data.size()+1];
        copieddata[0]=" ";
        for (int i = 0; i < data.size(); i++) {
            copieddata[i+1] = data.get(i);
        }
        return copieddata;
    }
}
