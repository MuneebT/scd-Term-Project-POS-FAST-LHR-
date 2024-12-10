package Controller;

import Model.DataEntryOperatorDAO;
import Model.Order;
import Model.OrderDAO;

import java.util.LinkedList;

public class OrderController {
    public  void redirectOrderInsertRequest(int p_id,String p_name,int p_quantity,int v_id,String v_name,int b_id){
        OrderDAO.insertdataintoOrderTable( p_id, p_name, p_quantity, v_id, v_name,b_id);
    }
    public void redirectOrderDeleteRequest(int id){
        OrderDAO.deleteDataFromOrderTable(id);
    }
    public Object[][] redirectGatherOrderDatarequest(){
        return OrderDAO.gatherOrderData();
    }

    public  void redirectOrderUpdateRequest(int productid,String productname,int productquantity,String vendorname,int b_id){
        OrderDAO.updateDataIntoOrderTable( productid, productname, productquantity, vendorname,b_id);
    }

    public LinkedList<String> returnProductNames(){

        return OrderDAO.readProductNameFromOrderDb();

    }

    public LinkedList<String> returnVendorNames(){
        return OrderDAO.readVendorNameFromOrderDB();
    }

    public Object[][] returnspecificorderdata(String productname,String vendorname){
        return OrderDAO.readspecificOrderData(productname,vendorname);
    }

    public LinkedList<Order> returnALlOrders(){return OrderDAO.getAllOrders();}

}

