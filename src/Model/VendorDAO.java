package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Connection.ConnectionConfigurator;
import java.sql.ResultSet;
import java.sql.SQLException;

public class VendorDAO {
    public VendorDAO()
    {
    }
    public ResultSet getAllVendors() throws SQLException {
        String query = "SELECT * FROM Vendor";
        Connection conn = ConnectionConfigurator.getConnection();
        PreparedStatement ps = conn.prepareStatement(query);
        return ps.executeQuery();
    }
    public boolean deleteVendor(int vendorID) throws SQLException {
        String query = "DELETE FROM Vendor WHERE VendorID = ?";
        try (
                Connection conn = ConnectionConfigurator.getConnection();
              PreparedStatement preparedStatement = conn.prepareStatement(query)) {
            preparedStatement.setInt(1, vendorID);
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0; // Return true if at least one row was deleted
        } catch (SQLException e) {
            e.printStackTrace();
            throw e; // Re-throw exception to be handled by the caller
        }
    }
    public boolean updateVendor(String vendorID, String name, String contactPerson, String phone, String email,
                                String address, String city, String stateProvince, String country) {
        // Connection and statement initialization
        String query = "UPDATE `vendor` SET `Name` = ?, `ContactPerson` = ?, `Phone` = ?, `Email` = ?, " +
                "`Address` = ?, `City` = ?, `State_Province` = ?, `Country` = ? WHERE `VendorID` = ?";

        try (Connection connection = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters
            stmt.setString(1, name);
            stmt.setString(2, contactPerson);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, stateProvince);
            stmt.setString(8, country);
            stmt.setString(9, vendorID);  // vendorID for identifying which vendor to update

            int rowsUpdated = stmt.executeUpdate();
            return rowsUpdated > 0;  // Return true if the update was successful
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions as appropriate
            return false;
        }
    }
    public boolean insertVendor( String name, String contactPerson, String phone, String email,
                                String address, String city, String stateProvince, String country) {
        // Connection and statement initialization
        String query = "INSERT INTO `vendor` ( `Name`, `ContactPerson`, `Phone`, `Email`, " +
                "`Address`, `City`, `State_Province`, `Country`) VALUES ( ?, ?, ?, ?, ?, ?, ?, ?)";

        try (Connection connection = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = connection.prepareStatement(query)) {

            // Set the parameters
            //stmt.setString(1, vendorID);  // vendorID to insert as a new entry
            stmt.setString(1, name);
            stmt.setString(2, contactPerson);
            stmt.setString(3, phone);
            stmt.setString(4, email);
            stmt.setString(5, address);
            stmt.setString(6, city);
            stmt.setString(7, stateProvince);
            stmt.setString(8, country);

            int rowsInserted = stmt.executeUpdate();
            return rowsInserted > 0;  // Return true if the insert was successful
        } catch (SQLException e) {
            e.printStackTrace();  // Handle exceptions as appropriate
            return false;
        }
    }

}
