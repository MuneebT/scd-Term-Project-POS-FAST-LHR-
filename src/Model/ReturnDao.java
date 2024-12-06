package Model;
import java.sql.ResultSetMetaData;

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
        System.out.println("Invoice is" +invoiceNumber+"\n");
        String query = "SELECT * FROM `sale` WHERE `InvoiceNumber` = ?";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, Integer.parseInt(invoiceNumber)); // Set the parameter
            ResultSet rs = stmt.executeQuery();

            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();
            System.out.println("Column Count: " + columnCount);
            for (int i = 1; i <= columnCount; i++) {
                System.out.println("Column " + i + ": " + metaData.getColumnName(i));
            }


            while (rs.next()) {
                // Create a Sale object for each record
                System.out.println("Row Data:");
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(metaData.getColumnName(i) + " = " + rs.getObject(i) + ", \n");
                }
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
                System.out.println("Sale is \t"+sale.getSaleId()+" "+sale.getProdName()+" "+sale.getProdId()+" "+sale.getInvoiceNumber()+" "+sale.getQuantity()+" "+sale.getPrice());
                salesList.add(sale); // Add to the list
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return salesList; // Return the list of sales
    }



    public boolean updateSale(int saleId, int prodId, String prodName, double price, int quantity, double totalPrice, int invoiceNumber, int branchId) {
        String sql = "UPDATE `sale` SET `SaleID`=?, `ProdId`=?, `ProdName`=?, `Price`=?, `Quantity`=?, `TotalPrice`=?, `InvoiceNumber`=?, `branchID`=? WHERE `ProdId`=? AND `InvoiceNumber`=? AND `branchID`=?";

        try (Connection connection = ConnectionConfigurator.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setInt(1, saleId);
            preparedStatement.setInt(2, prodId);
            preparedStatement.setString(3, prodName);
            preparedStatement.setDouble(4, price);
            preparedStatement.setInt(5, quantity);
            preparedStatement.setDouble(6, totalPrice);
            preparedStatement.setInt(7, invoiceNumber);
            preparedStatement.setInt(8, branchId);
            preparedStatement.setInt(9, prodId); // Used for the WHERE clause
            preparedStatement.setInt(10, invoiceNumber); // Used for the WHERE clause
            preparedStatement.setInt(11, branchId); // Used for the WHERE clause

            int rowsUpdated = preparedStatement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Sale updated successfully.");
                return true;
            } else {
                System.out.println("No matching records found to update.");

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
