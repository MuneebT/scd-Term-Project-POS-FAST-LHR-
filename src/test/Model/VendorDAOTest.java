package test.Model;

import Connection.ConnectionConfigurator;
import Model.VendorDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

import static org.junit.jupiter.api.Assertions.*;

public class VendorDAOTest {

    private VendorDAO vendorDAO;

    @BeforeEach
    public void setUp() {
        vendorDAO = new VendorDAO();  // Create an instance of the class to be tested
    }

    @Test
    public void testGetAllVendors() {
        try {
            ResultSet resultSet = vendorDAO.getAllVendors();
            assertNotNull(resultSet);  // Ensure the ResultSet is not null

            // Check if the ResultSet contains data (at least one row)
            boolean hasResults = resultSet.next();
            assertTrue(hasResults, "Expected some vendors to be returned.");
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException occurred while fetching vendors.");
        }
    }

    @Test
    public void testDeleteVendor_Success() {
        int vendorID = 1; // Use a valid vendorID for testing deletion

        try {
            boolean isDeleted = vendorDAO.deleteVendor(vendorID);
            assertTrue(isDeleted, "Vendor should be deleted successfully.");

            // Verify the vendor is no longer in the database
            try (Connection conn = ConnectionConfigurator.getConnection()) {
                String query = "SELECT * FROM Vendor WHERE VendorID = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, vendorID);
                    ResultSet rs = stmt.executeQuery();
                    assertFalse(rs.next(), "Vendor should no longer exist in the database.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException occurred while deleting vendor.");
        }
    }

    @Test
    public void testUpdateVendor_Success() {
        // Use valid data for an existing vendor to test the update method
        String vendorID = "1"; // Example VendorID
        String name = "Updated Vendor Name";
        String contactPerson = "John Doe";
        String phone = "1234567890";
        String email = "updatedemail@example.com";
        String address = "New Address";
        String city = "New City";
        String stateProvince = "New State";
        String country = "New Country";

        boolean isUpdated = vendorDAO.updateVendor(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
        assertTrue(isUpdated, "Vendor should be updated successfully.");

        // Verify that the changes are reflected in the database
        try (Connection conn = ConnectionConfigurator.getConnection()) {
            String query = "SELECT * FROM Vendor WHERE VendorID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, vendorID);
                ResultSet rs = stmt.executeQuery();
                if (rs.next()) {
                    assertEquals(name, rs.getString("Name"));
                    assertEquals(contactPerson, rs.getString("ContactPerson"));
                    assertEquals(phone, rs.getString("Phone"));
                    assertEquals(email, rs.getString("Email"));
                    assertEquals(address, rs.getString("Address"));
                    assertEquals(city, rs.getString("City"));
                    assertEquals(stateProvince, rs.getString("State_Province"));
                    assertEquals(country, rs.getString("Country"));
                } else {
                    fail("Vendor with the specified ID was not found.");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException occurred while querying the updated vendor.");
        }
    }

    @Test
    public void testInsertVendor_Success() {
        String name = "New Vendor";
        String contactPerson = "Jane Smith";
        String phone = "0987654321";
        String email = "newvendor@example.com";
        String address = "123 Vendor St";
        String city = "Vendor City";
        String stateProvince = "Vendor State";
        String country = "Vendor Country";

        boolean isInserted = vendorDAO.insertVendor(name, contactPerson, phone, email, address, city, stateProvince, country);
        assertTrue(isInserted, "Vendor should be inserted successfully.");

        // Verify that the new vendor exists in the database
        try (Connection conn = ConnectionConfigurator.getConnection()) {
            String query = "SELECT * FROM Vendor WHERE Name = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, name);
                ResultSet rs = stmt.executeQuery();
                assertTrue(rs.next(), "Inserted vendor should exist in the database.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            fail("SQLException occurred while verifying inserted vendor.");
        }
    }

    @Test
    public void testReadVendorIDFromVendorTable() {
        LinkedList<Integer> vendorIDs = VendorDAO.readVendorIDFromVendorTable();
        assertNotNull(vendorIDs, "Vendor IDs should not be null.");
        assertTrue(vendorIDs.size() > 0, "Vendor IDs list should contain at least one entry.");
    }

    @Test
    public void testReadVendorNameFromVendorTable() {
        LinkedList<String> vendorNames = VendorDAO.readVendorNameFromVendorTable();
        assertNotNull(vendorNames, "Vendor names should not be null.");
        assertTrue(vendorNames.size() > 0, "Vendor names list should contain at least one entry.");
    }

    @Test
    public void testConcatenateVendorIDandVendorname() {
        LinkedList<String> concatenatedData = VendorDAO.concatenateVendorIDandVendorname();
        assertNotNull(concatenatedData, "Concatenated data should not be null.");
        assertTrue(concatenatedData.size() > 0, "Concatenated data should contain at least one entry.");

        // Verify that concatenated data is in the correct format
        for (String data : concatenatedData) {
            assertTrue(data.contains("_"), "Concatenated data should contain '_'.");
        }
    }
}
