package Controller;

import Model.DataEntryOperatorDAO;

import java.util.LinkedList;

public class DataEntryOperatorController {
    public Object[][] redirect_object_array(){
       return DataEntryOperatorDAO.gatherData();
    }
    public void redirect_Inventory_delete_request(int id){
        DataEntryOperatorDAO.delete_data_from_inventory_db(id);
    }
    public void redirect_Inventory_update_request(int id, int quantity, int costprice, int saleprice){
        DataEntryOperatorDAO.update_data_into_inventory_db(id,quantity,costprice,saleprice);
    }

    public void redirect_Inventory_Insert_request(String name, int quantity, String category, int cp, int sp) {
        DataEntryOperatorDAO.insertDataIntoInventoryDb(name, quantity, category, cp, sp);
    }

    public  void redirectOrderInsertRequest(int p_id,String p_name,int p_quantity,int v_id,String v_name){
         DataEntryOperatorDAO.insertdataintoOrderTable( p_id, p_name, p_quantity, v_id, v_name);
    }
    public void redirectOrderDeleteRequest(int id){
        DataEntryOperatorDAO.deleteDataFromOrderTable(id);
    }
    public Object[][] redirectGatherOrderDatarequest(){
        return DataEntryOperatorDAO.gatherOrderData();
    }

    public LinkedList<String> redirectProductConcatenatedDataRequest(){
        return DataEntryOperatorDAO.concatenateProductIDandProductName();
    }
    public  void redirectOrderUpdateRequest(int productid,String productname,int productquantity,String vendorname){
        DataEntryOperatorDAO.updateDataIntoOrderTable( productid, productname, productquantity, vendorname);
    }
}
