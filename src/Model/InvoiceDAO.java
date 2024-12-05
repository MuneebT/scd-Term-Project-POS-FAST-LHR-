package Model;

import Connection.ConnectionConfigurator;

import Model.Invoice;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    // Method to create an invoice and return the generated InvoiceID
    public int createInvoice(double totalBill, double gst, double amountPaid, double balance) {
        String sql = "INSERT INTO Invoice (TotalBill, GST, AmountPaid, Balance) VALUES (?, ?, ?, ?)";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, totalBill);
            pstmt.setDouble(2, gst);
            pstmt.setDouble(3, amountPaid);
            pstmt.setDouble(4, balance);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1); // Return the generated InvoiceID
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1; // Return -1 if insertion fails
    }

    // Method to get an invoice by its ID
    public Invoice getInvoiceById(int invoiceId) {
        String sql = "SELECT * FROM Invoice WHERE InvoiceID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoiceId);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double totalBill = rs.getDouble("TotalBill");
                double gst = rs.getDouble("GST");
                double amountPaid = rs.getDouble("AmountPaid");
                double balance = rs.getDouble("Balance");

                return new Invoice(invoiceId, totalBill, gst, amountPaid, balance);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Method to get all invoices
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice";
        try (Connection conn = ConnectionConfigurator.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                int invoiceId = rs.getInt("InvoiceID");
                double totalBill = rs.getDouble("TotalBill");
                double gst = rs.getDouble("GST");
                double amountPaid = rs.getDouble("AmountPaid");
                double balance = rs.getDouble("Balance");

                invoices.add(new Invoice(invoiceId, totalBill, gst, amountPaid, balance));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
