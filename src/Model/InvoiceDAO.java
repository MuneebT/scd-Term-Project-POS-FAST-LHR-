package Model;

import Connection.ConnectionConfigurator;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAO {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy hh:mm a");


    public int createInvoice(double totalBill, double gst, double amountPaid, double balance, int branchID) {
        String sql = "INSERT INTO Invoice (TotalBill, GST, AmountPaid, Balance, DateTime, branchID) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            pstmt.setDouble(1, totalBill);
            pstmt.setDouble(2, gst);
            pstmt.setDouble(3, amountPaid);
            pstmt.setDouble(4, balance);
            pstmt.setTimestamp(5, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.setInt(6, branchID);

            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        return generatedKeys.getInt(1);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }


    public Invoice getInvoiceById(int invoiceId, int branchID) {
        String sql = "SELECT * FROM Invoice WHERE InvoiceID = ? AND branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, invoiceId);
            pstmt.setInt(2, branchID);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                double totalBill = rs.getDouble("TotalBill");
                double gst = rs.getDouble("GST");
                double amountPaid = rs.getDouble("AmountPaid");
                double balance = rs.getDouble("Balance");
                LocalDateTime dateTime = rs.getTimestamp("DateTime").toLocalDateTime();

                return new Invoice(invoiceId, totalBill, gst, amountPaid, balance, dateTime, branchID);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public List<Invoice> getAllInvoices(int branchID) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice WHERE branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, branchID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int invoiceId = rs.getInt("InvoiceID");
                double totalBill = rs.getDouble("TotalBill");
                double gst = rs.getDouble("GST");
                double amountPaid = rs.getDouble("AmountPaid");
                double balance = rs.getDouble("Balance");
                LocalDateTime dateTime = rs.getTimestamp("DateTime").toLocalDateTime();

                invoices.add(new Invoice(invoiceId, totalBill, gst, amountPaid, balance, dateTime, branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }


    public List<Invoice> getInvoicesByDateRange(LocalDateTime startDate, LocalDateTime endDate, int branchID) {
        List<Invoice> invoices = new ArrayList<>();
        String sql = "SELECT * FROM Invoice WHERE DateTime BETWEEN ? AND ? AND branchID = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setTimestamp(1, Timestamp.valueOf(startDate));
            pstmt.setTimestamp(2, Timestamp.valueOf(endDate));
            pstmt.setInt(3, branchID);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int invoiceId = rs.getInt("InvoiceID");
                double totalBill = rs.getDouble("TotalBill");
                double gst = rs.getDouble("GST");
                double amountPaid = rs.getDouble("AmountPaid");
                double balance = rs.getDouble("Balance");
                LocalDateTime dateTime = rs.getTimestamp("DateTime").toLocalDateTime();

                invoices.add(new Invoice(invoiceId, totalBill, gst, amountPaid, balance, dateTime, branchID));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
