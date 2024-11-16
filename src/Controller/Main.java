package Controller;
import Model.BranchDAO;
import Model.BranchManagementModel;
import View.*;


import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
      
        try {
            DBInitializer in=new DBInitializer();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        new splashView();
        // new CreateBranchView();
//new BranchManagementView();
      
//new UpdateScreenView();
//BranchDAO.insert_data_into_db();
//BranchManagementModel bmm=BranchManagementModel.getInstance();
//bmm.update_Branch_data_into_db(124,"Dha lahore","Faisilabad","Active","Mall road muree","03338222333");
//new SearchScreenView();
    }
}
