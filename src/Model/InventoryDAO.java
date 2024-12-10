package Model;
import java.sql.Connection;
import javax.swing.*;
import java.sql.*;
import java.util.LinkedList;
import Connection.ConnectionConfigurator;
import Controller.BranchManagementController;

public class InventoryDAO {
    private static final LinkedList<Integer> id = new LinkedList<>();
    private static final LinkedList<String> p_name = new LinkedList<>();
    private static final LinkedList<Integer> p_quantity = new LinkedList<>();
    private static final LinkedList<String> p_category = new LinkedList<>();
    private static final LinkedList<Integer> costprice = new LinkedList<>();
    private static final LinkedList<Integer> saleprice = new LinkedList<>();
    private static final LinkedList<Integer> branchid = new LinkedList<>();

    public static Object[][] gatherData() {
        LinkedList<Integer> p_id = readProductIDFromDB();
        LinkedList<String> productName = readProductNameFromDB();
        LinkedList<Integer> productQuantity = readProductQuantityFromDB();
        LinkedList<String> productCategory = readProductCategoryFromDB();
        LinkedList<Integer> productCostPrice = readProductCostPriceFromDB();
        LinkedList<Integer> productSalePrice = readProductSalesPriceFromDB();
        LinkedList<Integer> branchId = readBranchIDsfromInventory();
        int size = p_id.size();
        Object[][] data = new Object[size][9]; // Adjusted for 8 columns (including action buttons)

        for (int i = 0; i < size; i++) {
            data[i][0] = p_id.get(i);
            data[i][1] = productName.get(i);
            data[i][2] = productQuantity.get(i);
            data[i][3] = productCategory.get(i);
            data[i][4] = productCostPrice.get(i);
            data[i][5] = productSalePrice.get(i);
            data[i][6] = branchId.get(i);
            data[i][7] = "Delete";
            data[i][8] = "Update";

        }

        return data;
    }

    public static void insertDataIntoInventoryDb(String name, int quantity, String category, int cp, int sp, int b_id) {
        String sql = "INSERT INTO Inventory (ProductName, ProductQuantity, ProductCategory, "
                + "CostPrice, SalePrice,BranchID) VALUES (?, ?, ?, ?, ?,?)";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, name);
            ps.setInt(2, quantity);
            ps.setString(3, category);
            ps.setInt(4, cp);
            ps.setInt(5, sp);
            ps.setInt(6, b_id);
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

    public static LinkedList<Integer> readBranchIDsfromInventory() {

        String sql = "SELECT BranchID FROM Inventory";
        Connection temp = null;
        try {
            temp = ConnectionConfigurator.getConnection();
            Statement s = temp.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                branchid.add(rs.getInt(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return branchid;
    }

    public static LinkedList<String> concatenateProductIDandProductName() {
        LinkedList<Integer> productid = readProductIDFromDB();
        LinkedList<String> productname = readProductNameFromDB();
        LinkedList<Integer> productquantity = readProductQuantityFromDB();
        LinkedList<String> concatenateddate = new LinkedList<>();
        String temp;
        for (int i = 0; i < productid.size(); i++) {
            temp = productid.get(i) + "_" + productname.get(i) + "_" + productquantity.get(i);
            concatenateddate.add(temp);
        }
        return concatenateddate;
    }


    public Inventory getProductById(int productId) {

        Inventory product = null;
        LoggedEmp loggedEmp = LoggedEmp.getInstance();
        int branchID = Integer.parseInt(loggedEmp.getBranch());

        String query = "SELECT ProductID, ProductName, ProductQuantity, ProductCategory, CostPrice, SalePrice FROM Inventory WHERE ProductID = ? AND BranchID=?";
        try (Connection conn = ConnectionConfigurator.getConnection(); // Get connection
             PreparedStatement pstmt = conn.prepareStatement(query)) {

            pstmt.setInt(1, productId);
            pstmt.setInt(2, branchID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int id = rs.getInt("ProductID");
                String productName = rs.getString("ProductName");
                int productQuantity = rs.getInt("ProductQuantity");
                String productCategory = rs.getString("ProductCategory");
                int costPrice = rs.getInt("CostPrice");
                int salePrice = rs.getInt("SalePrice");

                product = new Inventory(id, productName, productQuantity, productCategory, costPrice, salePrice);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }


    public static boolean reduceProductQuantity ( int productId, int quantitySold){
        LoggedEmp loggedEmp = LoggedEmp.getInstance();
        int branchID = Integer.parseInt(loggedEmp.getBranch());
        String sql = "UPDATE Inventory SET ProductQuantity = ProductQuantity - ? WHERE ProductID = ? AND BranchID=?";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, quantitySold);
            pstmt.setInt(2, productId);
            pstmt.setInt(3, branchID);

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Return true if update is successful
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if update fails
    }

    public static Object[][] readSpecificDatafromInventory (String productname, String productcategory){
        // Initialize the required LinkedLists to store the fetched data
        LinkedList<Integer> p_id = new LinkedList<>();
        LinkedList<String> p_name = new LinkedList<>();
        LinkedList<Integer> p_quantity = new LinkedList<>();
        LinkedList<String> p_category = new LinkedList<>();
        LinkedList<Integer> costprice = new LinkedList<>();
        LinkedList<Integer> saleprice = new LinkedList<>();
        LinkedList<Integer> branchId = new LinkedList<>();


        String sql = "SELECT ProductID, ProductName, ProductQuantity, ProductCategory, CostPrice, SalePrice, BranchID "
                + "FROM Inventory WHERE ProductName = ? OR ProductCategory = ?";

        // Fetch the data
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, productname);
            pstmt.setString(2, productcategory);

            // Execute the query and process the result
            ResultSet rs = pstmt.executeQuery();

            // Collect data from the result set
            while (rs.next()) {
                p_id.add(rs.getInt("ProductID"));
                p_name.add(rs.getString("ProductName"));
                p_quantity.add(rs.getInt("ProductQuantity"));
                p_category.add(rs.getString("ProductCategory"));
                costprice.add(rs.getInt("CostPrice"));
                saleprice.add(rs.getInt("SalePrice"));
                branchId.add(rs.getInt("BranchID"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        int size = p_id.size();


        Object[][] data = new Object[size][7];

        // Fill the data array
        for (int i = 0; i < size; i++) {
            data[i][0] = p_id.get(i);
            data[i][1] = p_name.get(i);
            data[i][2] = p_quantity.get(i);
            data[i][3] = p_category.get(i);
            data[i][4] = costprice.get(i);
            data[i][5] = saleprice.get(i);
            data[i][6] = branchId.get(i);
        }

        return data;
    }
    public static LinkedList<Inventory> getAllInventory () {
        String sql = "SELECT * FROM Inventory";
        Connection temp = null;
        LinkedList<Inventory> inventory = new LinkedList<>();
        try {
            temp = ConnectionConfigurator.getConnection();
            Statement s = temp.createStatement();
            ResultSet rs = s.executeQuery(sql);
            while (rs.next()) {
                int id = rs.getInt(1);
                String name = rs.getString(2);
                int quantity = rs.getInt(3);
                String category = rs.getString(4);
                int costprice = rs.getInt(5);
                int saleprice = rs.getInt(6);
                inventory.add(new Inventory(id, name, quantity, category, costprice, saleprice));
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
        return inventory;
    }
}