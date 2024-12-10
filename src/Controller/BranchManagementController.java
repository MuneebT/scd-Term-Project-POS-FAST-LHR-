package Controller;

import Model.Branch;
import Model.BranchDAO;
import Model.BranchManagementModel;
import View.BranchManagementView;

import java.util.LinkedList;

public class BranchManagementController {
    private BranchDAO bmm=new BranchDAO();
    private Branch branches=new Branch();
    public   Object[][] return_object_Array(){
        return bmm.Insert_data_into_Array();
    }

    public void redirect_update_request(int code,String name,String city,String status,String address,String phoneno){
        bmm.update_Branch_data_into_db( code, name, city, status, address, phoneno);
    }
    public void redirect_delect_request(int code){
        bmm.delete_branch_data_from_db(code);
    }


    public LinkedList<String> return_list_of_city_names(){
        return bmm.read_city_name_from_file();
    }
    public LinkedList<String> return_list_of_branch_names(){return BranchDAO.read_branch_Names_data_from_db();}
    public LinkedList<String> return_list_of_branchcity_names(){return BranchDAO.read_branch_City_data_from_db();}

    public   Object[][] return_object_Array(String b_name,String c_name){
        return bmm.Insert_data_into_Array(b_name,c_name);
    }

    public LinkedList<Integer> returnListofBranchIDs(){
        return BranchDAO.read_branch_code_data_from_db();
    }
    public LinkedList<String> redirectConcatenatedData(){
        return branches.concatenateBranchIDandBranchName();
    }

    public LinkedList<Branch> returnAllBranches(){return BranchDAO.getAllBranches();}
}


