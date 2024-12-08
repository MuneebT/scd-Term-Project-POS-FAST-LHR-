package View;

import Connection.InternetConnectionChecker;
import Controller.InventoryCntroller;
import Controller.OrderController;
import Controller.VendorManagementController;
import Controller.BranchManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class AddOrderView extends JFrame {
    private JButton btnAdd;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel lblProductID;
    private JComboBox<String> comboProductID;
    private JLabel lblVendorID;
    private JComboBox<String> comboVendorID;
    private JLabel lblBranchID;
    private JComboBox<String> comboBranchID;

    private OrderController oc = new OrderController();
    private InventoryCntroller ic = new InventoryCntroller();
    private VendorManagementController vmc = new VendorManagementController();
    private BranchManagementController bmc = new BranchManagementController();

    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public AddOrderView() {
        setTitle("Add Order");
        setLayout(null); // Absolute positioning
        setBounds(100, 100, 800, 600);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("src/resources/login.png");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Product ID ComboBox
        lblProductID = new JLabel("Select Product ID");
        lblProductID.setFont(new Font("Arial", Font.BOLD, 15));
        lblProductID.setBounds(420, 50, 150, 30);

        boolean flag1=icc.startChecking();

        if(flag1) {
            LinkedList<String> productdata = ic.redirectProductConcatenatedDataRequest();
            comboProductID = new JComboBox<>(productdata.toArray(new String[0]));
            comboProductID.setBounds(580, 50, 180, 30);
        }
        else{
            comboProductID=loaddataintoproductcombobox();
            comboProductID.setBounds(580, 50, 180, 30);
        }

        // Vendor ID ComboBox
        lblVendorID = new JLabel("Select Vendor ID");
        lblVendorID.setFont(new Font("Arial", Font.BOLD, 15));
        lblVendorID.setBounds(420, 120, 150, 30);

        boolean flag=icc.startChecking();
        if(flag) {
            LinkedList<String> vendordata = vmc.redirectConcatenatedVendordata();
            comboVendorID = new JComboBox<>(vendordata.toArray(new String[0]));
            comboVendorID.setBounds(580, 120, 180, 30);
        }
        else{
            comboVendorID=loaddataintovendorcombobox();
            comboVendorID.setBounds(580, 120, 180, 30);
        }

        // Branch ID ComboBox
        lblBranchID = new JLabel("Select Branch ID");
        lblBranchID.setFont(new Font("Arial", Font.BOLD, 15));
        lblBranchID.setBounds(420, 190, 150, 30);

        boolean isconnected=icc.startChecking();

        if(isconnected) {
            LinkedList<String> branchdata = bmc.redirectConcatenatedData();
            comboBranchID = new JComboBox<>(branchdata.toArray(new String[0]));
            comboBranchID.setBounds(580, 190, 180, 30);
        }
        else{
            comboBranchID=loaddataintocombobox();
            comboBranchID.setBounds(580, 190, 180, 30);
        }


        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setForeground(Color.white);
        btnAdd.setBackground(Color.decode("#415a77"));
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBounds(580, 300, 100, 40);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Process the selected data
                String productID = (String) comboProductID.getSelectedItem();
                String vendorID = (String) comboVendorID.getSelectedItem();
                String branchID = (String) comboBranchID.getSelectedItem();

                StringTokenizer st = new StringTokenizer(productID, "_");
                int productid = Integer.parseInt(st.nextToken());
                String productname = st.nextToken();
                int productquantity = Integer.parseInt(st.nextToken());

                StringTokenizer st1 = new StringTokenizer(vendorID, "_");
                int vendorid = Integer.parseInt(st1.nextToken());
                String vendorname = st1.nextToken();

                StringTokenizer st2 = new StringTokenizer(branchID, "_");
                int branchid = Integer.parseInt(st2.nextToken());
                String branchname = st2.nextToken();

                oc.redirectOrderInsertRequest(productid, productname, productquantity, vendorid, vendorname, branchid);
                dispose();
            }
        });

        // Adding components to the frame
        add(imagelabel);
        add(lblProductID);
        add(comboProductID);
        add(lblVendorID);
        add(comboVendorID);
        add(lblBranchID);
        add(comboBranchID);
        add(btnAdd);

        setVisible(true);
    }


    public LinkedList<String> returnconcatenatedBranchdata(){
        BufferedReader br=null;
       LinkedList<String> concatenateddata=new LinkedList<>();
        try{
            br=new BufferedReader(new FileReader("concatenatedbranchdata.txt"));
            String line;
            while((line=br.readLine())!=null){
                concatenateddata.add(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br!=null){
                    br.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return concatenateddata;
    }
    public JComboBox<String> loaddataintocombobox(){
        String[] data= returnconcatenatedBranchdata().toArray(new String[0]);
        return new JComboBox<>(data);
    }
    public LinkedList<String> readvendorconcatenateddatafromfile(){
        LinkedList<String> concatenateddata=new LinkedList<>();
        BufferedReader br=null;
        try{
            br=new BufferedReader(new FileReader("concatenatedvendordata.txt"));
            String line;
            while((line=br.readLine())!=null){
                concatenateddata.add(line);
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br!=null){
                    br.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return concatenateddata;
    }
    public JComboBox<String> loaddataintovendorcombobox(){
        String [] data=readvendorconcatenateddatafromfile().toArray(new String[0]);
        return new JComboBox<>(data);
    }

    public LinkedList<String> returnconcatenatedproductdata(){
        LinkedList<String> concatenateddata=new LinkedList<>();
        BufferedReader br=null;
        try{
            br=new BufferedReader(new FileReader("concatenatedproductdata.txt"));
            String line;
            while((line=br.readLine())!=null){
                concatenateddata.add(line);

            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(br!=null){
                    br.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
        return concatenateddata;
    }

    public JComboBox<String> loaddataintoproductcombobox(){
        String []data=returnconcatenatedproductdata().toArray(new String[0]);
        return new JComboBox<>(data);
    }
    public static void main(String[] args) {
        new AddOrderView();
    }

}
