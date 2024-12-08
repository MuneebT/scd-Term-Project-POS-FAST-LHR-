package View;

import Controller.BranchManagementController;
import Controller.CategoryController;
import Controller.DataEntryOperatorController;
import Controller.InventoryCntroller;
import Model.Category;
import Connection.InternetConnectionChecker;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.StringTokenizer;

public class AddInventoryView extends JFrame {
    private JButton btnAdd;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel p_quantity, costprice, saleprice, p_name, p_category, p_branch;
    private JTextField tfquantity, tfprice, tfsaleprice, tfname, tfcategory;
    private JComboBox<String> branchComboBox;
    private JComboBox<String> category;
    private LinkedList<String> categorytype=new LinkedList<>();
    private LinkedList<Category> categories=new LinkedList<>();
    private InventoryCntroller ic = new InventoryCntroller();
    private CategoryController cc=new CategoryController();
    private InternetConnectionChecker icc=new InternetConnectionChecker();
private BranchManagementController bmc=new BranchManagementController();
    public AddInventoryView() {
        setTitle("Add Inventory");
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

        // Product Name
        p_name = new JLabel("Enter Name");
        p_name.setFont(new Font("Arial", Font.BOLD, 15));
        p_name.setBounds(420, 30, 150, 30);

        tfname = new JTextField();
        tfname.setBounds(580, 30, 180, 30);

        // Quantity
        p_quantity = new JLabel("Enter Quantity");
        p_quantity.setFont(new Font("Arial", Font.BOLD, 15));
        p_quantity.setBounds(420, 80, 150, 30);

        tfquantity = new JTextField();
        tfquantity.setBounds(580, 80, 180, 30);

        // Category
        p_category = new JLabel("Select Category");
        p_category.setFont(new Font("Arial", Font.BOLD, 15));
        p_category.setBounds(420, 130, 150, 30);

       boolean isconnected= icc.startChecking();

       if(isconnected){
        categories=cc.redirectgetAllCategoriesRequest();
        for(int i=0;i<categories.size();i++){
            categorytype.add(categories.get(i).gettype());
        }
        category= new JComboBox<>(categorytype.toArray(new String[0]));
       }
       else{
           category=loadCategoryDataintocombobox();
       }
        JScrollPane scrollPane=new JScrollPane(category);
        scrollPane.setBounds(580, 130, 180, 30);

        // Cost Price
        costprice = new JLabel("Enter Cost Price");
        costprice.setFont(new Font("Arial", Font.BOLD, 15));
        costprice.setBounds(420, 180, 150, 30);

        tfprice = new JTextField();
        tfprice.setBounds(580, 180, 180, 30);

        // Sale Price
        saleprice = new JLabel("Enter Sale Price");
        saleprice.setFont(new Font("Arial", Font.BOLD, 15));
        saleprice.setBounds(420, 230, 150, 30);

        tfsaleprice = new JTextField();
        tfsaleprice.setBounds(580, 230, 180, 30);

        // Branch Selection
        p_branch = new JLabel("Select Branch");
        p_branch.setFont(new Font("Arial", Font.BOLD, 15));
        p_branch.setBounds(420, 280, 150, 30);

        LinkedList<String> branchdata=bmc.redirectConcatenatedData();
        branchComboBox = new JComboBox<String>(branchdata.toArray(new String[0]));
        branchComboBox.setBounds(580, 280, 180, 30);


        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBackground(Color.decode("#415a77"));
        btnAdd.setForeground(Color.WHITE);
        btnAdd.setFont(new Font("Arial", Font.BOLD, 14));
        btnAdd.setBounds(580, 330, 100, 40);

        btnAdd.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean flag=icc.startChecking();
                if (validateInputs()) {
                    String name = tfname.getText();
                    int quantity = Integer.parseInt(tfquantity.getText());
                    String selectedcategory =(String) category.getSelectedItem();
                    int costPrice = Integer.parseInt(tfprice.getText());
                    int salePrice = Integer.parseInt(tfsaleprice.getText());
                    String selectedBranch = (String) branchComboBox.getSelectedItem();
                    StringTokenizer st=new StringTokenizer(selectedBranch,"_");
                    int branchid=Integer.parseInt(st.nextToken());
                    String branchname=st.nextToken();

                    if(flag) {
                        ic.redirect_Inventory_Insert_request(name, quantity, selectedcategory, costPrice, salePrice, branchid);

                        dispose();
                    }
                    else{
                        storeaddinventorydataintempfile(name, quantity, selectedcategory, costPrice, salePrice, branchid);
                   writeproductdataintofileforconcatenation(ic.returnlistofproductIDs(),ic.returnProductNames());
                    }
                }
            }
        });

        // Adding components to the frame
        add(imagelabel);
        add(p_name);
        add(tfname);
        add(p_quantity);
        add(tfquantity);
        add(p_category);
        add(scrollPane);
        add(costprice);
        add(tfprice);
        add(saleprice);
        add(tfsaleprice);
        add(p_branch);
        add(branchComboBox);
        add(btnAdd);

        setVisible(true);
    }


    // Validate Inputs
    private boolean validateInputs() {
        if (!namedoesNotContainInteger(tfname.getText())) {
            JOptionPane.showMessageDialog(null, "Product name cannot contain numbers");
            return false;
        }

        if (!isPositiveInteger(tfquantity.getText(), "Quantity")) return false;
        if (!isPositiveInteger(tfprice.getText(), "Cost Price")) return false;
        if (!isPositiveInteger(tfsaleprice.getText(), "Sale Price")) return false;

        return true;
    }

    // Validate non-numeric input
    private boolean namedoesNotContainInteger(String input) {
        return !input.matches(".*\\d.*");
    }

    // Validate positive integer input
    private boolean isPositiveInteger(String input, String fieldName) {
        try {
            int value = Integer.parseInt(input);
            if (value <= 0) {
                JOptionPane.showMessageDialog(null, fieldName + " must be a positive number");
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, fieldName + " must be a valid number");
            return false;
        }
        return true;
    }


    public String[] readCategoryDatafromfile() {
        ArrayList<String> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader("AddCategory.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return data.toArray(new String[0]); // Convert ArrayList to array
    }

    public JComboBox<String> loadCategoryDataintocombobox() {
        String[] categoryData = readCategoryDatafromfile();
        return new JComboBox<>(categoryData);
    }
    public void storeaddinventorydataintempfile(String name,int quantity,String selectedcategory,int costPrice,int salePrice,int branchid){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("addinventory.txt"));
            String data=name+","+ quantity+","+ selectedcategory+","+ costPrice+","+ salePrice+","+ branchid;
        bw.write(data);
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

    public void writeproductdataintofileforconcatenation(LinkedList<Integer> id,LinkedList<String> name){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("concatenatedproductdata.txt"));
            for(int i=0;i<id.size();i++){
                String data=id.get(i)+"_"+name.get(i);
                 bw.write(data);
                bw.newLine();
            }

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
        new AddInventoryView();
    }

}
