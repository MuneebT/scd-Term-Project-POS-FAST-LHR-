package Model;

import Controller.*;
import com.mysql.cj.x.protobuf.MysqlxCrud;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class FileManagerModel {
    private BranchManagementController bmc=new BranchManagementController();
    private InventoryCntroller ic=new InventoryCntroller();
    private EmployeeManagementController emc=new EmployeeManagementController();
    private OrderController oc=new OrderController();
    private CategoryController cc=new CategoryController();

    public void storeBranchCredentials(){
        BufferedWriter bw=null;
        LinkedList<Branch> branches=bmc.returnAllBranches();
        try{
            bw=new BufferedWriter(new FileWriter("branch.txt",true));
            for(int i=0;i<branches.size();i++){
                String data=branches.get(i).getBranchID()+","+branches.get(i).getBranchName()+","+
                        branches.get(i).getBranchCity()+","+branches.get(i).getBranchStatus()+","+branches.get(i).getBranchAddress()
                        +","+branches.get(i).getBranchPhone()+","+branches.get(i).getNoOfEmployees();
           bw.write(data);
           bw.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void storeinventorydata(){
        BufferedWriter bw=null;
        LinkedList<Inventory> inventory=ic.returnAllInventory();
        try{
            bw=new BufferedWriter(new FileWriter("inventory.txt",true));
            for(int i=0;i<inventory.size();i++){
                String data=inventory.get(i).getProductId()+","+inventory.get(i).getProductName()+","+
                        inventory.get(i).getProductQuantity()+","+inventory.get(i).getCostPrice()+","+
                        inventory.get(i).getSalePrice();
                bw.write(data);
                bw.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void storeemployeedata(){
        List<Employee> employees=emc.redirect_get_All_employees();
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("employee.txt",true));
            for(int i=0;i<employees.size();i++){
                String data=employees.get(i).getName()+","+employees.get(i).getSalary()+","+employees.get(i).getEmpNo()
                        +","+employees.get(i).getEmail()+","+employees.get(i).getBranchCode()+","+employees.get(i).getDesignation();
                bw.write(data);
                bw.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    public void storeOrderdata(){
      LinkedList<Order> orders=oc.returnALlOrders();
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("order.txt",true));
            for(int i=0;i<orders.size();i++){
                String data=orders.get(i).getProductID()+","+orders.get(i).getProductName()+","+orders.get(i).getProductQuantity()
                        +","+orders.get(i).getVendorID()+","+orders.get(i).getVendorName()+","+orders.get(i).getBranchID();
                bw.write(data);
                bw.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    public void storeCategorydata(){
        LinkedList<Category> categories=cc.redirectgetAllCategoriesRequest();
        BufferedWriter bw=null;
        try{
            bw=new BufferedWriter(new FileWriter("category.txt",true));
            for(int i=0;i<categories.size();i++){
                String data=categories.get(i).id+","+categories.get(i).gettype();
                bw.write(data);
                bw.newLine();
            }
        }
        catch (IOException e){
            e.printStackTrace();
        }
        finally {
            try{
                if(bw!=null){
                    bw.close();
                }
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

}
