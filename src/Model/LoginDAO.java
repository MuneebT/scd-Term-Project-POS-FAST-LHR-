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
    public boolean validateUser(String username, String password,String designation) throws SQLException {
        String query = "SELECT * FROM Login WHERE name = ? AND password = ? AND designation= ?";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, username);
            ps.setString(2, password);
            ps.setString(3,designation);
            ResultSet rs = ps.executeQuery();
            return rs.next(); // Return true if the user exists
        }


    }
}
