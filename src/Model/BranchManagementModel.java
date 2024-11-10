package Model;

import javax.swing.*;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

public class BranchManagementModel {
    private static BranchManagementModel bmm=null;
private BranchManagementModel(){
    bmm=null;
}
    public static BranchManagementModel getInstance(){
        if(bmm==null){
            bmm=new BranchManagementModel();
        }
        return bmm;
    }

private static Connection temp;
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SCD", "root", "");
            if (conn != null) {
                System.out.println("Db connected");
            } else {
                System.out.println("Db not connected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    public static void insert_data_into_db(){
        temp = getConnection();
        try {
            // Removed the extra parenthesis at the end of the SQL statement
            String sql = "INSERT INTO branchmanagement (Code, Name, City, Status, Address, Phoneno, NoofEmployees) VALUES (?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = temp.prepareStatement(sql);

            // Set parameters for the prepared statement
            ps.setInt(1, 124);
            ps.setString(2, "Model Town");
            ps.setString(3, "Gujranwala");
            ps.setString(4, "InActive");
            ps.setString(5, "Ferozpur road");
            ps.setString(6, "03338164142");
            ps.setInt(7, 1);

            // Use executeUpdate() for INSERT statements
            int rowsInserted = ps.executeUpdate();

            if (rowsInserted > 0) {
                System.out.println("A new record was inserted successfully!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //reading branch codes from db
  public static LinkedList<Integer> read_branch_code_data_from_db(){
        LinkedList<Integer> BranchCodes=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
            int code=rs.getInt(1);
            BranchCodes.add(code);
            }
        }
        catch (Exception e){
         e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchCodes;
  }

      //reading branch names from db
    public static LinkedList<String> read_branch_Names_data_from_db(){
        LinkedList<String> BranchNames=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                String name=rs.getString(2);
                BranchNames.add(name);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchNames;
    }

    //read branch city data from db

    public static LinkedList<String> read_branch_City_data_from_db(){
        LinkedList<String> BranchCity=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                String city=rs.getString(3);
                BranchCity.add(city);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchCity;
    }

    //read branch status from db

    public static LinkedList<String> read_branch_Status_data_from_db(){
        LinkedList<String> BranchStatus=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                String status=rs.getString(4);
                BranchStatus.add(status);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchStatus;
    }

    //read branch address from db

    public static LinkedList<String> read_branch_Address_data_from_db(){
        LinkedList<String> BranchAddress=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                String Address=rs.getString(5);
                BranchAddress.add(Address);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchAddress;
    }

    //read branch phone no from db

    public static LinkedList<String> read_branch_Phoneno_data_from_db(){
        LinkedList<String> BranchPhoneno=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                String Phoneno=rs.getString(6);
                BranchPhoneno.add(Phoneno);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchPhoneno;
    }

    //read branch no of employees from db

    public static LinkedList<Integer> read_branch_noofemployees_data_from_db(){
        LinkedList<Integer> BranchEmployeeCount=new LinkedList<>();
        temp=getConnection();
        try{

            String sql="SELECT Code, Name, City, Status, Address, Phoneno, NoofEmployees FROM branchmanagement ";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                int noofemployees=rs.getInt(7);
                BranchEmployeeCount.add(noofemployees);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try {
                if (temp != null) {
                    temp.close(); // Properly close the connection instead of setting it to null
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchEmployeeCount;
    }

    public Object[][] Insert_data_into_Array(){
         LinkedList<Integer> BranchCodes=read_branch_code_data_from_db();
         LinkedList<String> BranchNames=read_branch_Names_data_from_db();
         LinkedList<String> BranchCity=read_branch_City_data_from_db();
         LinkedList<String> BranchStatus=read_branch_Status_data_from_db();
         LinkedList<String> BranchAddress=read_branch_Address_data_from_db();
         LinkedList<String> BranchPhoneno=read_branch_Phoneno_data_from_db();
         LinkedList<Integer> BranchEmployeeCount=read_branch_noofemployees_data_from_db();

         Object[][] data=new Object[BranchCodes.size()][9];
         for(int i=0;i<BranchCodes.size();i++){
             for(int j=0;j<1;j++){
                 data[i][0]=BranchCodes.get(i);
                 data[i][1]=BranchNames.get(i);
                 data[i][2]=BranchCity.get(i);
                 data[i][3]=BranchStatus.get(i);
                 data[i][4]=BranchAddress.get(i);
                 data[i][5]=BranchPhoneno.get(i);
                 data[i][6]=BranchEmployeeCount.get(i);
                 data[i][7]="Delete";
                 data[i][8]="Update";
             }
         }
         return data;
    }

    //update branch data into db

    public void update_Branch_data_into_db(int code,String name,String city,String status,String address,String phoneno){
        temp=getConnection();
        try{
            String sql="UPDATE branchmanagement SET Name=?, City=?, Status=?, Address=?, Phoneno=? WHERE Code="+code;
            PreparedStatement ps=temp.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,city);
            ps.setString(3,status);
            ps.setString(4,address);
            ps.setString(5,phoneno);
           int num= ps.executeUpdate();
           if(num>0){
               System.out.println("Data updated into db");
              // JOptionPane.showMessageDialog(null,"Data Updated in Database");
           }
           else{
               System.out.println("Error");
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(temp!=null){
                    temp.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    //delete data from branch table

    public void delete_branch_data_from_db(int code) {
        temp = getConnection();
        try {
            // Use a parameterized query for safety
            String sql = "DELETE FROM branchmanagement WHERE Code = ?";
            PreparedStatement ps = temp.prepareStatement(sql);
            ps.setInt(1, code);  // Set code as a parameter

            int num = ps.executeUpdate();  // Execute the delete operation

            if (num > 0) {
                System.out.println("Data deleted from DB successfully.");
            } else {
                System.out.println("Data not deleted - no matching record found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (temp != null) {
                    temp.close();  // Ensure the connection is properly closed
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

