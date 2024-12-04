package Model;
import java.sql.Connection;
import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import Connection.ConnectionConfigurator;

public class InventoryDAO {
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