package Controller;

import Model.ReturnDao;
import Model.Sale;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class ReturnController {
//    Sale sale=new Sale();

    public List<Sale> redirect_get_sales(String invoiceNo)
    {
        ReturnDao returnDao=new ReturnDao();
        List<Sale> salesList = new ArrayList<>();
        salesList=returnDao.getSalesByInvoice(invoiceNo);
        return salesList;
    }

}
