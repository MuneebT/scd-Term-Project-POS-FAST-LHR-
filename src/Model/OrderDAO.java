package Model;

import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import Connection.ConnectionConfigurator;
public class OrderDAO {
    public static void insertdataintoOrderTable(int p_id,String p_name,int p_quantity,int v_id,String v_name,int b_id){
        String sql="INSERT INTO `Order`  (ProductID,ProductName,ProductQuantity,VendorID,VendorName,BranchID) Values(?,?,?,?,?,?)";
        try{
            Connection temp= ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
            ps.setInt(1,p_id);
            ps.setString(2,p_name);
            ps.setInt(3,p_quantity);
            ps.setInt(4,v_id);
            ps.setString(5,v_name);
            ps.setInt(6,b_id);
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
            temp= ConnectionConfigurator.getConnection();
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
            temp= ConnectionConfigurator.getConnection();
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
            temp= ConnectionConfigurator.getConnection();
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
            temp= ConnectionConfigurator.getConnection();
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
            temp= ConnectionConfigurator.getConnection();
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
            temp= ConnectionConfigurator.getConnection();
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
    public static LinkedList<Integer> readBranchIDfromOrderTable(){
        String sql="SELECT BranchID FROM `Order`";
        LinkedList<Integer> branchid=new LinkedList<>();
        try{
            Connection temp=ConnectionConfigurator.getConnection();
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                int id=rs.getInt(1);
                branchid.add(id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return branchid;
    }

    public static Object[][] gatherOrderData(){
        LinkedList<Integer> p_id=readProductIDfromOrderDB();
        LinkedList<String> p_name=readProductNameFromOrderDb();
        LinkedList<Integer> p_quantity=readProductQuantityFromOrderDB();
        LinkedList<Integer> v_id=readVendorIDFromOrderDB();
        LinkedList<String> v_name=readVendorNameFromOrderDB();
        LinkedList<Integer> b_id=readBranchIDfromOrderTable();
        Object data[][]=new Object[p_id.size()][8];
        for (int i = 0; i < p_id.size(); i++) {
            data[i][0] = p_id.get(i);
            data[i][1] = p_name.get(i);
            data[i][2] = p_quantity.get(i);
            data[i][3] = v_id.get(i);
            data[i][4] = v_name .get(i);
            data[i][5]=b_id.get(i);
            data[i][6] = "Delete";
            data[i][7] = "Update";
        }
        return data;
    }
    public static void updateDataIntoOrderTable(int productid,String productname,int productquantity,String vendorname,int b_id){
        String sql="UPDATE `Order` SET ProductName=?, ProductQuantity=?,VendorName=?,BranchID=? WHERE ProductID="+productid;
        Connection temp=null;
        try{
            temp= ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
            ps.setString(1,productname);
            ps.setInt(2,productquantity);
            ps.setString(3,vendorname);
            ps.setInt(4,b_id);
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
    public static Object[][] readspecificOrderData(String productname, String vendorname) {
        // Initialize the required LinkedLists to store the fetched data
        LinkedList<Integer> productId = new LinkedList<>();
        LinkedList<String> p_name = new LinkedList<>();
        LinkedList<String> v_name = new LinkedList<>();
        LinkedList<Integer> v_id = new LinkedList<>();
        LinkedList<Integer> branchId = new LinkedList<>();

        // SQL query to fetch order data based on product name OR vendor name
        String sql = "SELECT ProductID, ProductName,VendorID ,VendorName, BranchID "
                + "FROM `order` WHERE ProductName = ? OR VendorName = ?";

        // Fetch the data
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productname);
            pstmt.setString(2, vendorname);

            // Execute the query and process the result
            ResultSet rs = pstmt.executeQuery();

            // Collect data from the result set
            while (rs.next()) {
                productId.add(rs.getInt("ProductID"));
                p_name.add(rs.getString("ProductName"));
                v_name.add(rs.getString("VendorName"));
                v_id.add(rs.getInt("VendorID"));
                branchId.add(rs.getInt("BranchID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Determine the size for the data array (it will be the same as the size of the fetched data)
        int size = productId.size();

        // Initialize the 2D array to store the result
        Object[][] data = new Object[size][5]; // 8 columns (OrderID, ProductName, VendorName, Quantity, TotalPrice, OrderDate, BranchID, Actions)

        // Fill the data array
        for (int i = 0; i < size; i++) {
            data[i][0] = productId.get(i);
            data[i][1] = p_name.get(i);
            data[i][2] = v_id.get(i);
            data[i][3] = v_name.get(i);
            data[i][4] = branchId.get(i);

        }

        return data;
    }

    public static LinkedList<Order> getAllOrders(){
        LinkedList<Order> orders = new LinkedList<>();
        String query = "SELECT ProductID, ProductName, ProductQuantity, VendorID, VendorName, BranchID FROM `Order`";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                int productID = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int productQuantity = rs.getInt("ProductQuantity");
                int vendorID = rs.getInt("VendorID");
                String vendorName = rs.getString("VendorName");
                int branchID = rs.getInt("BranchID");

                // Create an Order object and add it to the list
                Order order = new Order(productID, productName, productQuantity, vendorID, vendorName, branchID);
                orders.add(order);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to fetch orders from the database", e);
        }

        return orders;
    }
}
