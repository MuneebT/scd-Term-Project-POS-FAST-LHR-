package Model;

import Connection.ConnectionConfigurator;
import Model.Sale;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class SaleDAO {

    // Create Sale with branchID
    public boolean createSale(int productId, String productName, double price, int quantity, double totalPrice, int invoiceNumber, int branchID) {
        String sql = "INSERT INTO Sale (ProdId, ProdName, Price, Quantity, TotalPrice, InvoiceNumber, branchID) VALUES (?, ?, ?, ?, ?, ?, ?)";

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

    // Get Sale by ID and branchID
    public Sale getSaleById(int saleId, int branchID) {
        String sql = "SELECT * FROM Sale WHERE SaleID = ? AND branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, saleId);
            pstmt.setInt(2, branchID); // Check for specific branchID
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                int prodId = rs.getInt("ProdId");
                String prodName = rs.getString("ProdName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                int invoiceNumber = rs.getInt("InvoiceNumber");

                return new Sale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Get all sales for a specific branch
    public List<Sale> getAllSales(int branchID) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sale WHERE branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchID); // Ensure only sales from the current branch are fetched
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int saleId = rs.getInt("SaleID");
                int prodId = rs.getInt("ProdId");
                String prodName = rs.getString("ProdName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                int invoiceNumber = rs.getInt("InvoiceNumber");

                sales.add(new Sale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }

    // Get sales by date range for a specific branch
    public List<Sale> getSalesByDateRange(LocalDateTime startDate, LocalDateTime endDate, int branchID) {
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM Sale WHERE DateTime BETWEEN ? AND ? AND branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(2, Timestamp.valueOf(endDate));
            pstmt.setInt(3, branchID); // Ensure sales are fetched for the specified branch
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int saleId = rs.getInt("SaleID");
                int prodId = rs.getInt("ProdId");
                String prodName = rs.getString("ProdName");
                double price = rs.getDouble("Price");
                int quantity = rs.getInt("Quantity");
                double totalPrice = rs.getDouble("TotalPrice");
                int invoiceNumber = rs.getInt("InvoiceNumber");

                sales.add(new Sale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return sales;
    }
}
