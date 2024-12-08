package View;

import Connection.InternetConnectionChecker;
import Controller.BranchManagementController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.regex.Pattern;

public class UpdateScreenView extends JFrame {
    private BranchManagementController bmc=new BranchManagementController();
    private JButton btnupdate;
    private JScrollPane scrollPane;
    private String[] citynames;
    private ImageIcon img;
    private JLabel imagelabel;
    private JLabel b_phone_no, b_name, b_address,b_city,b_status;
    private JTextField tfphoneno, tfname, tfaddress;
    private JComboBox<String> cb_status;
    private JComboBox<String> cb_cityname;

    private InternetConnectionChecker icc=new InternetConnectionChecker();
    public UpdateScreenView(int code,String name,String city,String status,String address,String phoneno,int employeecount) {


        setTitle("Update");
        setLayout(null); // Still using null layout for absolute positioning
        setBounds(100, 100, 800, 600);
        setResizable(false);
        getContentPane().setBackground(Color.WHITE);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // Image icon
        img = new ImageIcon("update.jpg");

        // Image label
        imagelabel = new JLabel(img);
        imagelabel.setBounds(0, 0, 400, 600);

        // Phone number label
        b_phone_no = new JLabel("Enter Phone No");
        b_phone_no.setForeground(Color.BLACK);
        b_phone_no.setFont(new Font("", Font.BOLD, 15));
        b_phone_no.setBounds(420, 100, 150, 30);

        // Phone number text field
        tfphoneno = new JTextField();
        tfphoneno.setBounds(580, 100, 180, 30);
        tfphoneno.setText(phoneno);

        // Branch name label
        b_name = new JLabel("Enter New Name");
        b_name.setForeground(Color.BLACK);
        b_name.setFont(new Font("", Font.BOLD, 15));
        b_name.setBounds(420, 170, 150, 30);

        // Branch name text field
        tfname = new JTextField();
        tfname.setBounds(580, 170, 180, 30);
         tfname.setText(name);

        // Address label
        b_address = new JLabel("Enter Address");
        b_address.setForeground(Color.BLACK);
        b_address.setFont(new Font("", Font.BOLD, 15));
        b_address.setBounds(420, 240, 150, 30);

        // Address text field
        tfaddress = new JTextField();
        tfaddress.setBounds(580, 240, 180, 30);
         tfaddress.setText(address);

        // branch status label
        b_status=new JLabel("Set status");
        b_status.setForeground(Color.BLACK);
        b_status.setFont(new Font("",Font.BOLD,15));
        b_status.setBounds(420,310,150,30);

        //status combp box
        String []branchstatus={"Active","InActive"};
        cb_status=new JComboBox<>(branchstatus);
        //cb_status.setSelectedIndex(0);
        cb_status.setBounds(580,310,150,30);
         cb_status.setSelectedItem(status);

        //city label
        b_city=new JLabel("Select city");
        b_city.setForeground(Color.BLACK);
        b_city.setFont(new Font("",Font.BOLD,15));
        b_city.setBounds(420,380,150,30);

        // branch address combo box
        LinkedList<String> list_citynames=bmc.return_list_of_city_names();
        copy_data(list_citynames);
        cb_cityname=new JComboBox<>(citynames);
       // cb_cityname.setSelectedIndex(-1);
        scrollPane=new JScrollPane(cb_cityname);
        cb_cityname.setBounds(580,380,150,30);
         cb_cityname.setSelectedItem(city);

        // Update button
        btnupdate = new JButton("Update");
        btnupdate.setBackground(Color.CYAN);
        btnupdate.setFont(new Font("", Font.BOLD, 14));
        btnupdate.setBounds(580, 450, 100, 40);

        // Adding components to frame
        add(imagelabel);
        add(b_phone_no);
        add(tfphoneno);
        add(b_name);
        add(tfname);
        add(b_address);
        add(tfaddress);
        add(b_city);
        add(b_status);
        add(cb_status);
        add(cb_cityname);
        add(btnupdate);

        setVisible(true);

        // Button action listener
        btnupdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String branchname=null;
                String cityname=null;
                String branchstatus=null;
                String branchaddress=null;
                String branchphoneno=null;
                boolean isconnected = icc.startChecking();
             if(isconnected){
                if (validatePhoneNumber() && validate_empty_Fields() && validate_name_data() && validate_is_status_combobox_empty()
                        && validate_is_cityname_combobox_empty()) {
                     branchname = tfname.getText();
                     cityname = (String) cb_cityname.getSelectedItem();
                     branchstatus = (String) cb_status.getSelectedItem();
                     branchaddress = tfaddress.getText();
                     branchphoneno = tfphoneno.getText();
                    bmc.redirect_update_request(code, branchname, cityname, branchstatus, branchaddress, branchphoneno);
                    JOptionPane.showMessageDialog(UpdateScreenView.this, "Data Updated Succesfully");

                    dispose();
                    new BranchManagementView();
                }
            }
             else{
                 storeupdateddataintempfile(code, branchname, cityname, branchstatus, branchaddress, branchphoneno);
               dispose();
                }

            }
        });
    }

    //copying data from citynames list to array
private void copy_data(LinkedList<String> data){
        citynames=new String[data.size()];
        for(int i=0;i<data.size();i++){
            citynames[i]=data.get(i);
        }
}
    private boolean validatePhoneNumber() {
        String phoneNo = tfphoneno.getText();
        boolean isValidLength = verify_Phone_No_length(phoneNo);
        boolean isValidData = false;

        if (isValidLength) {
            isValidData = verify_Phone_No_Data(phoneNo);
        }

        if (isValidLength && isValidData) {
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "Invalid phone number! Please enter a valid one.", "Error", JOptionPane.ERROR_MESSAGE);
            return false;
        }
    }
    private  boolean verify_Phone_No_length(String phone_no){

        if(phone_no.length()>11|| phone_no.length()<11){
            return false;
        }
        return true;
    }
    private  boolean verify_Phone_No_Data(String phone_no){
        String regex = "^[0-9]+$";
        return phone_no.matches(regex);
    }
    private boolean validate_empty_Fields(){
        String name_data,add_data,Phone_data;
        name_data=tfname.getText();
        add_data=tfaddress.getText();
        Phone_data=tfphoneno.getText();
        if(name_data.isEmpty() || add_data.isEmpty() || Phone_data.isEmpty()){
            JOptionPane.showMessageDialog(null,"Dont leave Empty Fields");
            return false;
        }
        //JOptionPane.showMessageDialog(null,"Dont Leave Field Empty");
        return true;
    }

    boolean validate_name_data(){
        String name=tfname.getText();
        String regex = "^[a-zA-Z\\s]+$";
        return Pattern.matches(regex, name);
    }

    //code to check if status combo box is empty;
    boolean validate_is_status_combobox_empty(){
        String data=(String)cb_status.getSelectedItem();
        if(data!=null){
            return true;
        }
        JOptionPane.showMessageDialog(null,"Dont leave status empty");
        return false;
    }

    //code to check if cityname combo box is empty
    boolean validate_is_cityname_combobox_empty(){
        String data=(String)cb_cityname.getSelectedItem();
        if(data!=null){
            return true;
        }
        JOptionPane.showMessageDialog(null,"Dont leave empty field");
        return false;
    }

    void storeupdateddataintempfile(int code,String branchname,String cityname,String branchstatus,String branchaddress,String branchphoneno){
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("UpdatedBranchManagement.txt",true));
            String data=code+","+branchname+","+cityname+","+branchstatus+","+branchaddress+","+branchphoneno;
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
}
