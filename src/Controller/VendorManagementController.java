package Controller;

import Model.VendorDAO;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;

public class VendorManagementController {
    VendorDAO vendorDAO=new VendorDAO();
    public ResultSet redirect_Get_All_Vendors() throws SQLException {
        return vendorDAO.getAllVendors();
    }
    public Boolean redirect_Delete_Vendors(int VendorID) throws SQLException {
        return vendorDAO.deleteVendor(VendorID);
    }
    public Boolean redirect_Update_Vendors(String vendorID, String name, String contactPerson, String phone, String email,
                                           String address, String city, String stateProvince, String country)
    {
        return vendorDAO.updateVendor(vendorID, name, contactPerson, phone, email, address, city, stateProvince, country);
    }
    public Boolean redirect_insert_Vendors( String name, String contactPerson, String phone, String email,
                                           String address, String city, String stateProvince, String country)
    {
        return vendorDAO.insertVendor( name, contactPerson, phone, email, address, city, stateProvince, country);
    }


    public LinkedList<String> redirectConcatenatedVendordata(){
      return  VendorDAO.concatenateVendorIDandVendorname();
    }

    public LinkedList<Integer> returnlistofvendorids(){return VendorDAO.readVendorIDFromVendorTable();}

    public LinkedList<String> returnlistofvendornames(){return VendorDAO.readVendorNameFromVendorTable();}

}
