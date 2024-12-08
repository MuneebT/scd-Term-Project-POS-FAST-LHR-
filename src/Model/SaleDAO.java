package Model;

import Connection.ConnectionConfigurator;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    public boolean createSale(int productId, String productName, double price, int quantity, double totalPrice, int invoiceNumber, int branchID) {
        String sql = "INSERT INTO Sale (ProdId, ProdName, Price, Quantity, TotalPrice, InvoiceNumber, BranchID) VALUES (?, ?, ?, ?, ?, ?, ?)";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            // Add sale record
            pstmt.setInt(1, productId);
            pstmt.setString(2, productName);
            pstmt.setDouble(3, price);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, totalPrice);
            pstmt.setInt(6, invoiceNumber);
            pstmt.setInt(7, branchID); // Set branchID

            int rowsAffected = pstmt.executeUpdate();

            // Reduce inventory if sale is successful
            InventoryDAO inventoryDAO = new InventoryDAO();
            if (rowsAffected > 0) {
                return InventoryDAO.reduceProductQuantity(productId, quantity);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Return false if sale or inventory update fails
    }

    public Sale getSaleById(int saleId) {
        String sql = "SELECT * FROM Sale WHERE SaleID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, saleId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int prodId = rs.getInt("ProdId");
                String prodName = rs.getString("ProdName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                int invoiceNumber = rs.getInt("InvoiceNumber");
                int branchID = rs.getInt("BranchID"); // Retrieve branchID

                return new Sale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Sale> getAllSales() {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sale";
        try (Connection conn = ConnectionConfigurator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int saleId = rs.getInt("SaleID");
                int prodId = rs.getInt("ProdId");
                String prodName = rs.getString("ProdName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                int invoiceNumber = rs.getInt("InvoiceNumber");
                int branchID = rs.getInt("BranchID"); // Retrieve branchID

                sales.add(new Sale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}
