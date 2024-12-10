package Model;

import Connection.ConnectionConfigurator;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayDao {
    public BigDecimal return_pay(String name,String password,String designation,String branch) throws SQLException {
        String query = "SELECT salary FROM employee WHERE name = ? AND emp_no = ? AND designation = ? AND branch_code = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, designation);
            ps.setString(4, branch);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getBigDecimal("salary"); // Return the salary if a match is found
            }
            return BigDecimal.ZERO; // Return 0.0 if no match is found
        }

    }
    public boolean getStatus(String name, String password, String designation, String branch) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        try {
            conn = ConnectionConfigurator.getConnection();

            // Query to fetch employee details, including the `paid` attribute
            String query = "SELECT paid FROM Employee " +
                    "WHERE name = ? AND emp_no = ? AND designation = ? AND branch_code = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, designation);
            ps.setString(4, branch);

            rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getBoolean("paid"); // Return the value of `paid`
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error while fetching employee paid status.", e);
        } finally {
            try {
                if (rs != null) rs.close();
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
        return false; // Default to false if no matching employee is found
    }


    public boolean setPaidStatus(String name, String password, String designation, String branch) {
        Connection conn = null;
        PreparedStatement ps = null;

        try {
            conn = ConnectionConfigurator.getConnection();

            String query = "UPDATE Employee SET paid = TRUE " +
                    "WHERE name = ? AND emp_no = ? AND designation = ? AND branch_code = ?";
            ps = conn.prepareStatement(query);
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, designation);
            ps.setString(4, branch);

            int rowsUpdated = ps.executeUpdate();

            // If at least one row is updated, return true
            return rowsUpdated > 0;
        } catch (SQLException e) {
            throw new RuntimeException("Error while updating payment status.", e);
        } finally {
            try {
                if (ps != null) ps.close();
                if (conn != null) conn.close();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

}
