package Model;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class BranchManagementModel {
private static Connection temp;
    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/SCD", "root", "");
            if (conn != null) {
                System.out.println("Db connected");
            } else {
                System.out.println("Db not connected");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return conn;
    }
    public static void insert_data_into_db(){
        temp=getConnection();
        try{
            String sql="INSERT INTO branchmanagement (Code,Name,City,Status,Address,Phoneno,NoofEmployees) Values(?,?,?,?,?,?,?))";
            PreparedStatement ps=temp.prepareStatement(sql);
          ps.setInt(1,123);
          ps.setString(2,"Faisal Town");
          ps.setString(3,"Lahore");
          ps.setString(4,"Active");
          ps.setString(5,"Iqbal Town");
          ps.setString(6,"03338189990");
          ps.setInt(7,1);
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(temp!=null){
                    temp=null;
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
    }
}

