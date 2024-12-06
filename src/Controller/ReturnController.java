package Controller;

import Model.ReturnDao;
import Model.Sale;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnController {
//    Sale sale=new Sale();
ReturnDao returnDao=new ReturnDao();
    public List<Sale> redirect_get_sales(String invoiceNo)
    {

        List<Sale> salesList = new ArrayList<>();
        salesList=returnDao.getSalesByInvoice(invoiceNo);
        return salesList;
    }
    public boolean redirect_update_sale(Sale sale) {
        int saleId = sale.getSaleId(); // Assuming you have a method to get the Sale ID
        int prodId = sale.getProdId();
        String prodName = sale.getProdName();
        double price = sale.getPrice();
        int quantity = sale.getQuantity();
        double totalPrice = sale.getTotalPrice();
        int invoiceNumber = sale.getInvoiceNumber();
        int branchId = sale.getBranchID(); // Assuming you have a method to get the Branch ID

        // Call the method in ReturnDao to update the sale in the database
       return returnDao.updateSale(saleId, prodId, prodName, price, quantity, totalPrice, invoiceNumber, branchId);
    }


}
