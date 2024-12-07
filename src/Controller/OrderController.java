package Controller;

import Model.DataEntryOperatorDAO;
import Model.OrderDAO;

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
}
