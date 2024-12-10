package Model;

import Connection.ConnectionConfigurator;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.LinkedList;

public class CategoryDAO {


    public static LinkedList<Category> getAllCategories(){
        LinkedList<Category> categories=new LinkedList<>();
        String sql="SELECT * FROM Category";
        Connection temp=null;
        try{
            temp= ConnectionConfigurator.getConnection();
            Statement s=temp.createStatement();
            ResultSet rs=s.executeQuery(sql);
            while(rs.next()){
                int id=rs.getInt(1);
                String type=rs.getString(2);
                categories.add(new Category(id,type));
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        finally {
            try{
                if(temp!=null){
                    temp.close();
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return categories;
    }
    public static void insertTypeintoCategoryTable(String type){
        String sql="INSERT INTO Category (CategoryType) Values(?)";
        Connection temp=null;
        try{
            temp=ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
            ps.setString(1,type);
           int num= ps.executeUpdate();
           if(num>0){
               JOptionPane.showMessageDialog(null,"Data inserted into DB");
           }
           else {
               System.out.println("Unable to insert data");
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void deleteCategory(int id){
        String sql="DELETE FROM Category WHERE CategoryID="+id;
        try{
            Connection temp=ConnectionConfigurator.getConnection();
            PreparedStatement ps=temp.prepareStatement(sql);
           boolean flag= ps.execute();
           if(flag){
               JOptionPane.showMessageDialog(null,"Data Deleted from DB");
           }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
