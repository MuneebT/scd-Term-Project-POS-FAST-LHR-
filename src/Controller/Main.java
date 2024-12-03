package Controller;
import Model.BranchDAO;
import Model.BranchManagementModel;
import Model.DataEntryOperatorDAO;
import Model.VendorDAO;
import View.*;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
      
        try {
            DBInitializer in=new DBInitializer();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        //new splashView();
        // new CreateBranchView();
//new BranchManagementView();
//new UpdateScreenView();
//BranchDAO.insert_data_into_db();
//BranchManagementModel bmm=BranchManagementModel.getInstance();
//bmm.update_Branch_data_into_db(124,"Dha lahore","Faisilabad","Active","Mall road muree","03338222333");
//new SearchScreenView();

//        //Inventory func calls
    //    DataEntryOperatorDAO.insertDataIntoInventoryDb("shampoo",12,"Cosmetic"
      //          ,1200,1300);
//new ManageInventoryView();

//        VendorDAO.insertVendor("Ali","03338189990","03338164142","ali@gmail.com"
  //      ,"GUjranwala","Lahore","Punjab","Pakistan");
        //Order Functionallity Call order
       // DataEntryOperatorDAO.insertdataintoOrderTable(2,"Oil",4,1,"Ali");
    new ManageOrderView();
    }
}
