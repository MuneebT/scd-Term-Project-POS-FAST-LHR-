package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import Connection.ConnectionConfigurator;

import javax.swing.*;


public class BranchDAO {


    public void createBranch(String name, String city, String status, String address, String phoneno)
    {

        Connection conn= ConnectionConfigurator.getConnection();
        try{
            String sql="INSERT INTO branch (branchName,branchCity,branchStatus,branchAddress,branchPhone,noOfEmployees) Values(?,?,?,?,?,?)";
            PreparedStatement ps=conn.prepareStatement(sql);
            ps.setString(1,name);
            ps.setString(2,city);
            ps.setString(3,status);
            ps.setString(4,address);
            ps.setString(5,phoneno);
            ps.setInt(6,1);
            ps.executeUpdate();
        }
        catch (Exception e){
            new JOptionPane(e.getMessage());
        }
        finally {
            try{
                if(conn!=null){
                    conn.close();
                    conn=null;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }

    }
}
