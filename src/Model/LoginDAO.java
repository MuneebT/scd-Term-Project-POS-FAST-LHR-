package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import Connection.ConnectionConfigurator;


public class LoginDAO {
    public LoginDAO() {
    }

    // Method to validate if the user exists in the logintable
    public boolean validateUser(String username, String password,String designation,String branch) throws SQLException {
        String query = "SELECT * FROM employee WHERE name = ? AND password = ? AND designation= ? AND branch_code=?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3,designation);
            ps.setString(4,branch);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if the user exists
        }



    }

    public boolean validateUserPay(String username, String password,String designation,String branch) throws SQLException {
        String query = "SELECT * FROM employee WHERE name = ? AND emp_no = ? AND designation= ? AND branch_code=?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3, designation);
            ps.setString(4, branch);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if the user exists
        }
    }



        public int getFirstTimeLoginStatus(String name, String password, String designation) throws SQLException {
        String query = "SELECT * FROM employee WHERE name = ? AND password = ? AND designation = ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, name);
            ps.setString(2, password);
            ps.setString(3, designation);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                int firstTimeLoginStatus = rs.getInt("firstPasswordChangedStatus");
                System.out.println("Login status to be returned: " + firstTimeLoginStatus);
                return firstTimeLoginStatus;
            } else {

                System.out.println("No matching record found. Returning default status 1.");
                return 1;
            }
        } catch (SQLException e) {
            System.out.println("Exception caught: " + e.getMessage());
            throw e;
        }
    }


    public void updatePassword(String currentPassword, String newPassword) throws SQLException
    {

        if (currentPassword.equals(newPassword)) {
            System.out.println("New password cannot be the same as the current password.");
            return;
        }

        LoggedEmp loggedEmp = LoggedEmp.getInstance();
        String username = loggedEmp.getUserName();
        String designation = loggedEmp.getDesignation();

        Connection conn = ConnectionConfigurator.getConnection();
        String updateQuery = "UPDATE Employee SET password = ?, firstPasswordChangedStatus = 1 " +
                "WHERE name = ? AND password = ? AND designation = ?";

        try {

            PreparedStatement ps = conn.prepareStatement(updateQuery);
            ps.setString(1, newPassword);  // Set new password
            ps.setString(2, username);    // Set username
            ps.setString(3, currentPassword); // Validate current password
            ps.setString(4, designation); // Set designation for additional security check

            int rowsUpdated = ps.executeUpdate();

            if (rowsUpdated > 0) {
                System.out.println("Password updated successfully for username: " + username);
            } else {
                System.out.println("Failed to update the password. Please check the provided details.");
            }
        } catch (SQLException e) {
            throw new RuntimeException("Error updating password: " + e.getMessage(), e);
        } finally {
            conn.close(); // Ensure the connection is closed
        }
    }
}