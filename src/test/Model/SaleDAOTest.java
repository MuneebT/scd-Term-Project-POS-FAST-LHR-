package test.Model;

import Connection.ConnectionConfigurator;
import Controller.LoginController;
import Model.InventoryDAO;
import Model.InvoiceDAO;
import Model.Sale;
import Model.SaleDAO;
import org.junit.jupiter.api.*;

import java.sql.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SaleDAOTest {

    private static SaleDAO saleDAO;
    private static int invoiceNumForSale;

    @BeforeAll
    public static void setup() {
        saleDAO = new SaleDAO();
        InventoryDAO inventoryDAO=new InventoryDAO();
        InvoiceDAO invoiceDAO=new InvoiceDAO();
        invoiceNumForSale=invoiceDAO.createInvoice(1000,12,1000,9,1)-1;
        InventoryDAO.insertDataIntoInventoryDb("Product A", 10, "Category A", 50, 100, 1);
        LoginController controller=new LoginController();
        try {
            controller.redirect_set_credientials("John","password123","Branch Manager","1");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    public static void tearDown() {
        String name="Product A";
        int bId=1;
        String sql = "DELETE FROM Inventory WHERE ProductName = ? AND BranchID = ?";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1,name);
            ps.setInt(2, bId);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Test
    public void testCreateSale() {
        int productId = 1;
        String productName = "Product A";
        double price = 100.0;
        int quantity = 2;
        double totalPrice = price * quantity;

        int branchID = 1;

        boolean saleCreated = saleDAO.createSale(productId, productName, price, quantity, totalPrice, invoiceNumForSale, branchID);

        assertTrue(saleCreated, "Sale creation failed.");
    }

    @Test
    public void testGetSaleById() {

        int productId = 2;
        String productName = "Product B";
        double price = 150.0;
        int quantity = 1;
        double totalPrice = price * quantity;

        int branchID = 1;


        Sale currSale= saleDAO.createSale(productId, productName, price, quantity, totalPrice, invoiceNumForSale, branchID,"s");
        Sale sale = saleDAO.getSaleById(currSale.getSaleId());


        assertNotNull(sale, "Sale retrieval failed.");
        assertEquals(productId, sale.getProdId(), "Product ID mismatch.");
        assertEquals(productName, sale.getProdName(), "Product name mismatch.");
        assertEquals(price, sale.getPrice(), "Price mismatch.");
        assertEquals(quantity, sale.getQuantity(), "Quantity mismatch.");
        assertEquals(totalPrice, sale.getTotalPrice(), "Total price mismatch.");
        assertEquals(invoiceNumForSale, sale.getInvoiceNumber(), "Invoice number mismatch.");
        assertEquals(branchID, sale.getBranchID(), "Branch ID mismatch.");
    }

    @Test
    public void testGetAllSales() {
        saleDAO.createSale(3, "Product C", 200.0, 3, 600.0, 12347, 1);
        saleDAO.createSale(4, "Product D", 250.0, 1, 250.0, 12348, 1);

        List<Sale> sales = saleDAO.getAllSales();

        assertNotNull(sales, "Failed to retrieve sales.");
        assertTrue(sales.size() > 0, "No sales found.");
    }

    @Test
    public void testSaleFields() {
        int productId = 5;
        String productName = "Product E";
        double price = 300.0;
        int quantity = 1;
        double totalPrice = price * quantity;
        int branchID = 1;

        Sale currSale = saleDAO.createSale(productId, productName, price, quantity, totalPrice, invoiceNumForSale, branchID,"e ");

        Sale sale = saleDAO.getSaleById(currSale.getSaleId());

        assertNotNull(sale, "Sale retrieval failed.");
        assertEquals(productId, sale.getProdId(), "Product ID mismatch.");
        assertEquals(productName, sale.getProdName(), "Product name mismatch.");
        assertEquals(price, sale.getPrice(), "Price mismatch.");
        assertEquals(quantity, sale.getQuantity(), "Quantity mismatch.");
        assertEquals(totalPrice, sale.getTotalPrice(), "Total price mismatch.");
        assertEquals(invoiceNumForSale, sale.getInvoiceNumber(), "Invoice number mismatch.");
        assertEquals(branchID, sale.getBranchID(), "Branch ID mismatch.");
    }
}
