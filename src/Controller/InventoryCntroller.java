package Controller;

import Model.DataEntryOperatorDAO;
import Model.Inventory;
import Model.InventoryDAO;

import java.util.LinkedList;

public class InventoryCntroller {

    public Object[][] redirect_object_array(){
        return InventoryDAO.gatherData();
    }
    public void redirect_Inventory_delete_request(int id){
        InventoryDAO.delete_data_from_inventory_db(id);
    }
    public void redirect_Inventory_update_request(int id, int quantity, int costprice, int saleprice){
        InventoryDAO.update_data_into_inventory_db(id,quantity,costprice,saleprice);
    }

    public void redirect_Inventory_Insert_request(String name, int quantity, String category, int cp, int sp,int b_id) {
        InventoryDAO.insertDataIntoInventoryDb(name, quantity, category, cp, sp,b_id);
    }

    public LinkedList<String> redirectProductConcatenatedDataRequest(){
        return InventoryDAO.concatenateProductIDandProductName();
    }
    public LinkedList<String> returnProductNames(){
        return InventoryDAO.readProductNameFromDB();
    }
    public LinkedList<String> returnListofProductCategory(){
        return InventoryDAO.readProductCategoryFromDB();
    }

    public Object[][] returnobjectArray(String productname, String productCategory) {
     
        return InventoryDAO.readSpecificDatafromInventory(productname,productCategory);

    }
    public LinkedList<Integer> returnlistofproductIDs(){return InventoryDAO.readProductIDFromDB();}

    public LinkedList<Inventory> returnAllInventory(){return InventoryDAO.getAllInventory();}
}