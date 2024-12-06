package Model;

import java.sql.Connection;
import Connection.ConnectionConfigurator;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ReturnDao {
    public List<Sale> getSalesByInvoice(String invoiceNumber) {
        List<Sale> salesList = new ArrayList<>();
        String query = "SELECT * FROM `sale` WHERE `InvoiceNumber` = ?";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, invoiceNumber); // Set the parameter

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                // Create a Sale object for each record
                Sale sale = new Sale(
                        rs.getInt("SaleID"),
                        rs.getInt("ProdId"),
                        rs.getString("ProdName"),
                        rs.getDouble("Price"),
                        rs.getInt("Quantity"),
                        rs.getDouble("TotalPrice"),
                        rs.getInt("InvoiceNumber"),
                        rs.getInt("branchID")
                );
                System.out.println("Sale is \t"+sale.getSaleID()+" "+sale.getProdName()+" "+sale.getProdId()+" "+sale.getInvoiceNumber()+" "+sale.getQuantity()+" "+sale.getPrice());
                salesList.add(sale); // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesList; // Return the list of sales
    }
}
