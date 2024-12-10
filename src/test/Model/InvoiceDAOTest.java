package test.Model;


import Connection.ConnectionConfigurator;
import Model.Invoice;
import Model.InvoiceDAO;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class InvoiceDAOTest {

    private static InvoiceDAO invoiceDAO;

    @BeforeAll
    public static void setup() {
        invoiceDAO = new InvoiceDAO();
    }

    @Test
    public void testCreateInvoice() {
        double totalBill = 1000.0;
        double gst = 180.0;
        double amountPaid = 1000.0;
        double balance = 0.0;
        int branchID = 1;

        int invoiceId = invoiceDAO.createInvoice(totalBill, gst, amountPaid, balance, branchID);

        assertNotEquals(-1, invoiceId, "Invoice creation failed. Invalid invoice ID returned.");
    }

    @Test
    public void testGetInvoiceById() {
        double totalBill = 500.0;
        double gst = 90.0;
        double amountPaid = 500.0;
        double balance = 0.0;
        int branchID = 1;

        int invoiceId = invoiceDAO.createInvoice(totalBill, gst, amountPaid, balance, branchID);

        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId, branchID);

        assertNotNull(invoice, "Invoice retrieval failed. No invoice found with the given ID.");
        assertEquals(invoiceId, invoice.getInvoiceID(), "The retrieved invoice ID does not match the created invoice ID.");
        assertEquals(totalBill, invoice.getTotalBill(), "Total bill mismatch.");
        assertEquals(gst, invoice.getGst(), "GST mismatch.");
        assertEquals(amountPaid, invoice.getAmountPaid(), "Amount paid mismatch.");
        assertEquals(balance, invoice.getBalance(), "Balance mismatch.");
    }

    @Test
    public void testGetAllInvoices() {
        invoiceDAO.createInvoice(2000.0, 360.0, 2000.0, 0.0, 1);
        invoiceDAO.createInvoice(1500.0, 270.0, 1500.0, 0.0, 1);

        List<Invoice> invoices = invoiceDAO.getAllInvoices(1);

        assertNotNull(invoices, "Failed to retrieve invoices.");
        assertTrue(invoices.size() > 0, "No invoices found for branch 1.");
    }

    @Test
    public void testGetInvoicesByDateRange() {
        LocalDateTime startDate = LocalDateTime.of(2024, 12, 1, 0, 0, 0, 0);
        LocalDateTime endDate = LocalDateTime.of(2024, 12, 31, 23, 59, 59, 999999);

        invoiceDAO.createInvoice(1000.0, 180.0, 1000.0, 0.0, 1);
        invoiceDAO.createInvoice(2000.0, 360.0, 2000.0, 0.0, 1);
        invoiceDAO.createInvoice(3000.0, 540.0, 3000.0, 0.0, 1);

        List<Invoice> invoices = invoiceDAO.getInvoicesByDateRange(startDate, endDate, 1);

        assertNotNull(invoices, "Failed to retrieve invoices.");
        assertTrue(invoices.size() > 0, "No invoices found within the date range.");
    }

    @Test
    public void testInvoiceFields() {
        double totalBill = 1200.0;
        double gst = 216.0;
        double amountPaid = 1200.0;
        double balance = 0.0;
        int branchID = 1;

        int invoiceId = invoiceDAO.createInvoice(totalBill, gst, amountPaid, balance, branchID);

        Invoice invoice = invoiceDAO.getInvoiceById(invoiceId, branchID);

        assertNotNull(invoice, "Failed to retrieve invoice.");
        assertEquals(totalBill, invoice.getTotalBill(), "Mismatch in total bill.");
        assertEquals(gst, invoice.getGst(), "Mismatch in GST.");
        assertEquals(amountPaid, invoice.getAmountPaid(), "Mismatch in amount paid.");
        assertEquals(balance, invoice.getBalance(), "Mismatch in balance.");
        assertEquals(branchID, invoice.getBranchID(), "Mismatch in branch ID.");
    }
}
