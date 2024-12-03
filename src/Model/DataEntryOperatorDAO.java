package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.LinkedList;
import Connection.ConnectionConfigurator;
import com.fasterxml.jackson.core.io.ContentReference;

import javax.swing.*;

public class DataEntryOperatorDAO {

    private static final LinkedList<Integer> id = new LinkedList<>();
    private static final LinkedList<String> p_name = new LinkedList<>();
    private static final LinkedList<Integer> p_quantity = new LinkedList<>();
    private static final LinkedList<String> p_category = new LinkedList<>();
    private static final LinkedList<Integer> costprice = new LinkedList<>();
    private static final LinkedList<Integer> saleprice = new LinkedList<>();

    public static Object[][] gatherData() {
        LinkedList<Integer> p_id = readProductIDFromDB();
        LinkedList<String> productName = readProductNameFromDB();
        LinkedList<Integer> productQuantity = readProductQuantityFromDB();
        LinkedList<String> productCategory = readProductCategoryFromDB();
        LinkedList<Integer> productCostPrice = readProductCostPriceFromDB();
        LinkedList<Integer> productSalePrice = readProductSalesPriceFromDB();

        int size = p_id.size();
        Object[][] data = new Object[size][8]; // Adjusted for 8 columns (including action buttons)

        for (int i = 0; i < size; i++) {
            data[i][0] = p_id.get(i);
            data[i][1] = productName.get(i);
            data[i][2] = productQuantity.get(i);
            data[i][3] = productCategory.get(i);
            data[i][4] = productCostPrice.get(i);
            data[i][5] = productSalePrice.get(i);
            data[i][6] = "Delete";
            data[i][7] = "Update";
        }

        return data;
    }

    public static void insertDataIntoInventoryDb(String name, int quantity, String category, int cp, int sp) {
        String sql = "INSERT INTO Inventory (ProductName, ProductQuantity, ProductCategory, "
                + "CostPrice, SalePrice) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, quantity);
            ps.setString(3, category);
            ps.setInt(4, cp);
            ps.setInt(5, sp);

            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void delete_data_from_inventory_db(int id) {
        Connection temp = ConnectionConfigurator.getConnection();
        String sql = "DELETE FROM Inventory WHERE ProductID = " + id;
        try {
            PreparedStatement ps = temp.prepareStatement(sql);
            int num = ps.executeUpdate();
            if (num > 0) {
                JOptionPane.showMessageDialog(null, "Data Deleted From DB");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to Delete Data From DB");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (temp != null) {
                    temp.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static LinkedList<Integer> readProductIDFromDB() {
        return readIntegerColumn("ProductID", id);
    }

    public static LinkedList<String> readProductNameFromDB() {
        return readStringColumn("ProductName", p_name);
    }

    public static LinkedList<Integer> readProductQuantityFromDB() {
        return readIntegerColumn("ProductQuantity", p_quantity);
    }

    public static LinkedList<String> readProductCategoryFromDB() {
        return readStringColumn("ProductCategory", p_category);
    }

    public static LinkedList<Integer> readProductCostPriceFromDB() {
        return readIntegerColumn("CostPrice", costprice);
    }

    public static LinkedList<Integer> readProductSalesPriceFromDB() {
        return readIntegerColumn("SalePrice", saleprice);
    }

    private static LinkedList<Integer> readIntegerColumn(String columnName, LinkedList<Integer> list) {
        String sql = "SELECT " + columnName + " FROM Inventory";
        try (Connection conn = ConnectionConfigurator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    private static LinkedList<String> readStringColumn(String columnName, LinkedList<String> list) {
        String sql = "SELECT " + columnName + " FROM Inventory";
        try (Connection conn = ConnectionConfigurator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                list.add(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public static void update_data_into_inventory_db(int id, int quantity, int costprice, int saleprice) {
        Connection temp = ConnectionConfigurator.getConnection();
        String sql = "UPDATE Inventory SET ProductQuantity=?, CostPrice=?, SalePrice=? WHERE ProductID=?";
        try {
            PreparedStatement ps = temp.prepareStatement(sql);
            ps.setInt(1, quantity);
            ps.setInt(2, costprice);
            ps.setInt(3, saleprice);
            ps.setInt(4, id);
            int num = ps.executeUpdate();
            if (num > 0) {
                JOptionPane.showMessageDialog(null, "Data updated in DB");
            } else {
                JOptionPane.showMessageDialog(null, "Unable to update data into DB");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(null, "Error updating data: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    public static void insertdataintoOrderTable(int p_id,String p_name,int p_quantity,int v_id,String v_name){
        String sql="INSERT INTO `Order`  (ProductID,PRoductName,ProductQuantity,VendorID,VendorName) Values(?,?,?,?,?)";
        try{
            Connection temp=ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
            ps.setInt(1,p_id);
            ps.setString(2,p_name);
            ps.setInt(3,p_quantity);
            ps.setInt(4,v_id);
            ps.setString(5,v_name);
            int num=ps.executeUpdate();
            if(num>0){
                JOptionPane.showMessageDialog(null,"Data Inserted in DB");
            }
            else{
                System.out.println("Unable to insert data");
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void deleteDataFromOrderTable(int id){
        String sql="DELETE FROM `Order` WHERE ProductID="+id;
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
            int num=ps.executeUpdate();
            if(num>0){
                JOptionPane.showMessageDialog(null,"Data deleted from DB");
            }
            else{
                System.out.println("Unable to delete data");
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

    public static LinkedList<Integer> readProductIDfromOrderDB(){
        LinkedList<Integer> p_id=new LinkedList<>();
        String sql="SELECT ProductID FROM `Order`";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            Statement s= temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                p_id.add(rs.getInt(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return p_id;
    }
    public static LinkedList<String> readProductNameFromOrderDb(){
        LinkedList<String> p_name=new LinkedList<>();
        String sql="SELECT ProductName FROM `Order`";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                p_name.add(rs.getString(1));
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
        return p_name;
    }

    public static LinkedList<Integer> readProductQuantityFromOrderDB(){
        LinkedList<Integer> p_quantity=new LinkedList<>();
        String sql="SELECT ProductQuantity FROM `Order`";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            Statement s= temp.createStatement();;
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                p_quantity.add(rs.getInt(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return p_quantity;
    }

    public static LinkedList<Integer> readVendorIDFromOrderDB(){
        LinkedList<Integer> v_id=new LinkedList<>();
        String sql="SELECT VendorID FROM `Order`";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            Statement s= temp.createStatement();;
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                v_id.add(rs.getInt(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return v_id;
    }

    public static LinkedList<String> readVendorNameFromOrderDB(){
        LinkedList<String> v_name=new LinkedList<>();
        String sql="SELECT VendorName FROM `Order`";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            Statement s= temp.createStatement();;
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                v_name.add(rs.getString(1));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return v_name;
    }

    public static Object[][] gatherOrderData(){
        LinkedList<Integer> p_id=readProductIDfromOrderDB();
        LinkedList<String> p_name=readProductNameFromOrderDb();
        LinkedList<Integer> p_quantity=readProductQuantityFromOrderDB();
        LinkedList<Integer> v_id=readVendorIDFromOrderDB();
        LinkedList<String> v_name=readVendorNameFromOrderDB();
        Object data[][]=new Object[p_id.size()][7];
        for (int i = 0; i < p_id.size(); i++) {
            data[i][0] = p_id.get(i);
            data[i][1] = p_name.get(i);
            data[i][2] = p_quantity.get(i);
            data[i][3] = v_id.get(i);
            data[i][4] = v_name .get(i);
            data[i][5] = "Delete";
            data[i][6] = "Update";
        }
        return data;
    }
    public static void updateDataIntoOrderTable(int productid,String productname,int productquantity,String vendorname){
        String sql="UPDATE `Order` SET ProductName=?, ProductQuantity=?,VendorName=? WHERE ProductID="+productid;
        Connection temp=null;
        try{
        temp=ConnectionConfigurator.getConnection();
        PreparedStatement ps=temp.prepareStatement(sql);
        ps.setString(1,productname);
        ps.setInt(2,productquantity);
        ps.setString(3,vendorname);
        int num=ps.executeUpdate();
        if(num>0){
            System.out.println("Data Updated in DB");
        }
        else {
            System.out.println("Cannot Update Data in DB");
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
    public static LinkedList<String> concatenateProductIDandProductName(){
        LinkedList<Integer> productid=readProductIDFromDB();
        LinkedList<String> productname=readProductNameFromDB();
        LinkedList<Integer> productquantity=readProductQuantityFromDB();
        LinkedList<String> concatenateddate=new LinkedList<>();
        String temp;
        for(int i=0;i<productid.size();i++){
        temp=productid.get(i)+"_"+productname.get(i)+"_"+productquantity.get(i);
            concatenateddate.add(temp);
        }
        return concatenateddate;
    }

}
