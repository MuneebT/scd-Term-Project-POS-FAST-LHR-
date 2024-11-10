package Controller;

import Model.BranchManagementModel;
import View.BranchManagementView;

public class BranchManagementController {
    private BranchManagementModel bmm=BranchManagementModel.getInstance();

 public   Object[][] return_object_Array(){
        return bmm.Insert_data_into_Array();
    }

public void redirect_update_request(int code,String name,String city,String status,String address,String phoneno){
     bmm.update_Branch_data_into_db( code, name, city, status, address, phoneno);
}
public void redirect_delect_request(int code){
     bmm.delete_branch_data_from_db(code);
}

}
