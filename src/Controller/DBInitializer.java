package Controller;

import Connection.ConnectionConfigurator;

import java.io.IOException;
import java.sql.*;

public class DBInitializer {
    DBInitializer() throws SQLException {
        makeSureBranchTableExists();
        makeSureLoginTableExists();
        makeSureEmployeeTableExists();
        makeSureVendorTableExists();

        makeSureInventoryTableExists();
        makeSureOrderTableExists();
    }

    void makeSureBranchTableExists() throws SQLException {
        // this creates branch table in DB if not exists
        Connection conn = ConnectionConfigurator.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS branch ( branchID INT PRIMARY KEY AUTO_INCREMENT, branchName VARCHAR(100) NOT NULL, branchCity VARCHAR(50) NOT NULL, branchStatus VARCHAR(20) NOT NULL, branchAddress VARCHAR(200), branchPhone VARCHAR(15), noOfEmployees INT DEFAULT 0 );";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }


    }
    void makeSureLoginTableExists() throws SQLException {
        Connection conn = ConnectionConfigurator.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS Login ( " +
                "id INT PRIMARY KEY AUTO_INCREMENT, " +
                "name VARCHAR(100) NOT NULL, " +
                "password VARCHAR(100) NOT NULL, " +
                "designation VARCHAR(50) NOT NULL " +
                ");";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }


    

    void makeSureEmployeeTableExists() throws SQLException {
        Connection conn = ConnectionConfigurator.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS Employee (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    emp_no varchar(50) UNIQUE ,\n" +
                "    name VARCHAR(100) NOT NULL,\n" +
                "password VARCHAR(100) NOT NULL, " +
                "    email VARCHAR(100),\n" +
                "    branch_code VARCHAR(50),\n" +
                "    salary DECIMAL(10, 2),\n" +
                "    designation VARCHAR(50)\n" +
                ");";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }

    }
    void makeSureVendorTableExists() throws SQLException {
        Connection conn = ConnectionConfigurator.getConnection();
        String query = "CREATE TABLE IF NOT EXISTS Vendor (\n" +
                "    VendorID INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    Name VARCHAR(100) NOT NULL,\n" +
                "    ContactPerson VARCHAR(100),\n" +
                "    Phone VARCHAR(15),\n" +
                "    Email VARCHAR(100),\n" +
                "    Address VARCHAR(255),\n" +
                "    City VARCHAR(50),\n" +
                "    State_Province VARCHAR(50),\n" +
                "    Country VARCHAR(50)\n" +
                ");";
        try {
            PreparedStatement ps = conn.prepareStatement(query);
            ps.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            conn.close();
        }
    }


    void makeSureInventoryTableExists() throws SQLException {
        String sql = "CREATE TABLE IF NOT EXISTS Inventory ("
                + "    ProductID INT PRIMARY KEY AUTO_INCREMENT,"
                + "    ProductName VARCHAR(100) NOT NULL,"
                + "    ProductQuantity INT DEFAULT 0,"
                + "    ProductCategory VARCHAR(50),"
                + "    CostPrice INT DEFAULT 0,"
                + "    SalePrice INT DEFAULT 0"
                + ");";

        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the Inventory table", e);
        }
    }

    void makeSureOrderTableExists() throws SQLException{
        String sql = "CREATE TABLE IF NOT EXISTS `Order` (" +
                "ProductID INT," +
                "ProductName VARCHAR(255)," +
                "ProductQuantity INT," +
                "VendorID INT," +
                "VendorName VARCHAR(255)," +
                "CONSTRAINT FK_Product FOREIGN KEY (ProductID) REFERENCES Inventory(ProductID) ON DELETE NO ACTION ON UPDATE NO ACTION," +
                "CONSTRAINT FK_Vendor FOREIGN KEY (VendorID) REFERENCES Vendor(VendorID) ON DELETE NO ACTION ON UPDATE NO ACTION);";
        try (Connection conn = ConnectionConfigurator.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Failed to create the Inventory table", e);
        }
    }

    }


