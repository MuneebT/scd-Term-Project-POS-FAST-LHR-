package test.Model;

import Connection.ConnectionConfigurator;
import Model.ReturnDao;
import Model.Sale;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
class ReturnDaoTest {

    ReturnDao returnDao=new ReturnDao();
    @Test
    void getSalesByInvoice() {
        String invoiceNumber = "1"; // Test invoice number

        // Call the method to test
        List<Sale> salesList = returnDao.getSalesByInvoice(invoiceNumber);

        // Assert the results
        assertNotNull(salesList, "Sales list should not be null");
        assertEquals(1, salesList.size(), "Sales list size should match the inserted test data");

        // Verify the first sale object
        Sale sale = salesList.get(0);
        assertEquals(1, sale.getSaleId(), "SaleID should match the inserted data");
        assertEquals(1, sale.getProdId(), "ProdId should match the inserted data");
        assertEquals("Product A", sale.getProdName(), "ProdName should match the inserted data");
        assertEquals(50.0, sale.getPrice(), 0.01, "Price should match the inserted data");
        assertEquals(2, sale.getQuantity(), "Quantity should match the inserted data");
        assertEquals(100.0, sale.getTotalPrice(), 0.01, "TotalPrice should match the inserted data");
        assertEquals(1, sale.getInvoiceNumber(), "InvoiceNumber should match the inserted data");
        assertEquals(1, sale.getBranchID(), "BranchID should match the inserted data");
    }

    @Test
    public void testUpdateSale_Success() {
        // Assuming there is a record with SaleID=1, ProdId=101, InvoiceNumber=1001, BranchID=1
        int saleId = 1;
        int prodId = 1;
        String prodName = "Test Product";
        double price = 50.0;
        int quantity = 2;
        double totalPrice = 100.0;
        int invoiceNumber = 1;
        int branchId = 1;

        boolean result = returnDao.updateSale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchId);
        assertTrue(result, "Sale should be updated successfully.");
    }

    @Test
    void updateInventory() {
        // Assuming the product exists in the inventory and has a valid productID and branchID
        int productId = 1;  // Example Product ID
        int quantityReturned = 5;  // The quantity being returned
        int branchId = 1;  // Example Branch ID

        boolean result = returnDao.updateInventory(productId, quantityReturned, branchId);
        assertTrue(result, "Inventory should be updated successfully.");
    }

    @Test
    void get_Total_bill_sales() {
        int invoice = 1;
        int branchId = 1;

        double totalBill = returnDao.get_Total_bill_sales(invoice, branchId);
        assertEquals(100.0, totalBill, "Total Bill should match the calculated sum from the database.");

    }


    @Test
    void update_invoice() {
        // Sample data to test with
            int invoiceNo = 1;
            int branchId = 1;
            double totalBill = 1000.0;
            double gst = 50.0;
            double balance = 1050.0;

            // Call the method to update the invoice
            boolean isUpdated = returnDao.update_invoice(invoiceNo, branchId, totalBill, gst, balance);

            // Query the database to confirm the update
            try (Connection conn = ConnectionConfigurator.getConnection()) {
                String query = "SELECT `TotalBill`, `GST`, `Balance` FROM `invoice` WHERE `InvoiceID` = ? AND `branchID` = ?";
                try (PreparedStatement stmt = conn.prepareStatement(query)) {
                    stmt.setInt(1, invoiceNo);
                    stmt.setInt(2, branchId);
                    ResultSet rs = stmt.executeQuery();
                    if (rs.next()) {
                        double updatedTotalBill = rs.getDouble("TotalBill");
                        double updatedGST = rs.getDouble("GST");
                        double updatedBalance = rs.getDouble("Balance");

                        // Assert that the values were updated correctly
                        assertEquals(totalBill, updatedTotalBill, 0.01);
                        assertEquals(gst, updatedGST, 0.01);
                        assertEquals(balance, updatedBalance, 0.01);
                    } else {
                        fail("Invoice not found in the database after update.");
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
                fail("SQLException occurred while querying the database.");
            }

            // Assert that the update was successful
            assertTrue(isUpdated, "Invoice should be updated successfully.");
        }
    }