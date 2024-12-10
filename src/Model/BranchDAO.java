package Model;

import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

import Connection.ConnectionConfigurator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.swing.*;


public class BranchDAO {


    public void createBranch(String name, String city, String status, String address, String phoneno) {

        Connection conn = ConnectionConfigurator.getConnection();
        try {
            String sql = "INSERT INTO branch (`branchName,branchCity,branchStatus,branchAddress,branchPhone,noOfEmployees) Values(?,?,?,?,?,?)";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, name);
            ps.setString(2, city);
            ps.setString(3, status);
            ps.setString(4, address);
            ps.setString(5, phoneno);
            ps.setInt(6, 1);
            ps.executeUpdate();
        } catch (Exception e) {
            new JOptionPane(e.getMessage());
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                    conn = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private static Connection temp;

    public static void insert_data_into_db(){
        temp = ConnectionConfigurator.getConnection();
        try {
            // Removed the extra parenthesis at the end of the SQL statement
            String sql = "INSERT INTO branch ( branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees) VALUES ( ?, ?, ?, ?, ?, ?)";
            PreparedStatement ps = temp.prepareStatement(sql);

            // Set parameters for the prepared statement
            //ps.setInt(1, 124);
            ps.setString(1, "Etihad Town Branch");
            ps.setString(2, "Lahore");
            ps.setString(3, "InActive");
            ps.setString(4, "Shimla Town");
            ps.setString(5, "03338222333");
            ps.setInt(6, 1);

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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql="SELECT branchID , branchName , branchCity , branchStatus , branchAddress , branchPhone , noOfEmployees FROM branch ";
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
        temp=ConnectionConfigurator.getConnection();
        try{
            String sql="UPDATE branch SET branchName=?, branchCity=?, branchStatus=?, branchAddress=?, branchPhone=? WHERE branchID="+code;
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
        temp = ConnectionConfigurator.getConnection();
        try {
            // Use a parameterized query for safety
            String sql = "DELETE FROM branch WHERE branchID = ?";
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
    //read city names from json file
    public  LinkedList<String> read_city_name_from_file(){
        ObjectMapper mapper = new ObjectMapper();
        LinkedList<String> cityname=new LinkedList<>();
        try {
            // Read JSON file as an array of JSON nodes
            JsonNode citiesArray = mapper.readTree(new File("cities.json"));

            // Iterate through the array and print each city name
            for (JsonNode cityNode : citiesArray) {
                //System.out.println(cityNode.get("name").asText());
                cityname.add(cityNode.get("name").asText());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return cityname;
    }

    ///search specific branch code from db
    public static LinkedList<Integer> read_branch_code_data_from_db(String BranchName,String BranchCity){
        LinkedList<Integer> BranchCodes=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{
            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";

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
                    temp.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchCodes;
    }

    //search specific branch name
    public static LinkedList<String> read_branch_Names_data_from_db(String BranchName,String BranchCity){
        LinkedList<String> BranchNames=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{
            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";

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

    //search specific city names from db
    public static LinkedList<String> read_branch_City_data_from_db(String BranchName,String cityname){
        LinkedList<String> BranchCity=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{
            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + cityname + "'";
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

    //read specific branch status from db
    public static LinkedList<String> read_branch_Status_data_from_db(String BranchName,String BranchCity){
        LinkedList<String> BranchStatus=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";
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

    //read specific branch address from db
    public static LinkedList<String> read_branch_Address_data_from_db(String BranchName,String BranchCity){
        LinkedList<String> BranchAddress=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";
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

    //read specific branch phone no from db;
    public static LinkedList<String> read_branch_Phoneno_data_from_db(String BranchName,String BranchCity){
        LinkedList<String> BranchPhoneno=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";
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
                    temp.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return BranchPhoneno;
    }

    //read specific no of employees data from db
    public static LinkedList<Integer> read_branch_noofemployees_data_from_db(String BranchName,String BranchCity){
        LinkedList<Integer> BranchEmployeeCount=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try{

            String sql = "SELECT branchID, branchName, branchCity, branchStatus, branchAddress, branchPhone, noOfEmployees "
                    + "FROM branch WHERE branchName = '" + BranchName + "' OR branchCity = '" + BranchCity + "'";
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

    public Object[][] Insert_data_into_Array(String b_name,String c_name){
        LinkedList<Integer> BranchCodes=read_branch_code_data_from_db(b_name,c_name);
        LinkedList<String> BranchNames=read_branch_Names_data_from_db(b_name,c_name);
        LinkedList<String> BranchCity=read_branch_City_data_from_db(b_name,c_name);
        LinkedList<String> BranchStatus=read_branch_Status_data_from_db(b_name,c_name);
        LinkedList<String> BranchAddress=read_branch_Address_data_from_db(b_name,c_name);
        LinkedList<String> BranchPhoneno=read_branch_Phoneno_data_from_db(b_name,c_name);
        LinkedList<Integer> BranchEmployeeCount=read_branch_noofemployees_data_from_db(b_name,c_name);

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
            }
        }
        return data;
    }
    public static LinkedList<Branch> getAllBranches(){
        LinkedList<Branch> branches=new LinkedList<>();
        temp=ConnectionConfigurator.getConnection();
        try {
            String sql="SELECT * FROM branch";
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while  (rs.next()){
                int id=rs.getInt(1);
                String name=rs.getString(2);
                String city=rs.getString(3);
                String status=rs.getString(4);
                String address=rs.getString(5);
                String phoneno=rs.getString(6);
                int noofemployees=rs.getInt(7);
                branches.add(new Branch(id,name,city,status,address,phoneno,noofemployees));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return branches;
    }






}