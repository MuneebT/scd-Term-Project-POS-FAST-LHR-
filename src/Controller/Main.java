package Controller;
import Model.BranchManagementModel;
import Model.UpdateScreenModel;
import View.BranchManagementView;
import View.CreateBranchView;
import View.LoadingScreenView;
import View.UpdateScreenView;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        try {
            DBInitializer in=new DBInitializer();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        new CreateBranchView();
//new UpdateScreenView();
//BranchManagementModel.insert_data_into_db();
    }
}
