package View;

import Controller.DataEntryOperatorController;
import Controller.InventoryCntroller;
import Controller.OrderController;
import Controller.VendorManagementController;
import Model.DataEntryOperatorDAO;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class AddOrderView extends JFrame {
    private JButton btnAdd;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel p_quantity;
    private JTextField tfquantity;

    private JLabel lblProductID;
    private JComboBox<String> comboProductID;

    private JLabel lblVendorID;
    private JComboBox<String> comboVendorID;

    private OrderController oc = new OrderController();
    private InventoryCntroller ic=new InventoryCntroller();
  private VendorManagementController vmc=new VendorManagementController();
    public AddOrderView() {
        setTitle("Add Order");
        setLayout(null); // Absolute positioning
        setBounds(100, 100, 800, 600); // Adjusted height after removing vendor fields
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Product ID ComboBox
        lblProductID = new JLabel("Select Product ID");
        lblProductID.setFont(new Font("Arial", Font.BOLD, 15));
        lblProductID.setBounds(420, 50, 150, 30);

        LinkedList<String> productdata=ic.redirectProductConcatenatedDataRequest();
        comboProductID = new JComboBox<>( productdata.toArray(new String[0]));
        comboProductID.setBounds(580, 50, 180, 30);
        // Vendor ID ComboBox
        lblVendorID = new JLabel("Select Vendor ID");
        lblVendorID.setFont(new Font("Arial", Font.BOLD, 15));
        lblVendorID.setBounds(420, 120, 150, 30);

       LinkedList<String> vendordata = vmc.redirectConcatenatedVendordata();
        comboVendorID = new JComboBox<>( vendordata.toArray(new String[0]));
        comboVendorID.setBounds(580, 120, 180, 30);

        // Quantity
//        p_quantity = new JLabel("Enter Quantity");
//        p_quantity.setFont(new Font("Arial", Font.BOLD, 15));
//        p_quantity.setBounds(420, 180, 150, 30);
//
//        tfquantity = new JTextField();
//        tfquantity.setBounds(580, 180, 180, 30);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBackground(Color.CYAN);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBounds(580, 300, 100, 40);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                    // Here, you can process the data, e.g., pass the data to the controller
                    String productID = (String) comboProductID.getSelectedItem();
                    String vendorID = (String) comboVendorID.getSelectedItem();

                    StringTokenizer st=new StringTokenizer(productID,"_");
                    int productid=Integer.parseInt(st.nextToken());
                    String productname=st.nextToken();
                      int productquantity=Integer.parseInt(st.nextToken());
                    StringTokenizer st1=new StringTokenizer(vendorID,"_");
                    int vendorid=Integer.parseInt(st1.nextToken());
                    String vendorname=st1.nextToken();

                    oc.redirectOrderInsertRequest(productid,productname,productquantity,vendorid,vendorname);
                    dispose();

            }
        });

        // Adding components to the frame
        add(imagelabel);
        add(lblProductID);
        add(comboProductID);
        add(lblVendorID);
        add(comboVendorID);
//        add(p_quantity);
//        add(tfquantity);
        add(btnAdd);

        setVisible(true);
    }

    // Validate Inputs

}
