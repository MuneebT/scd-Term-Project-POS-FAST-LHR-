package Connection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfigurator {
    static Connection conn;

    public static Connection getConnection() {
        try {
            if(conn==null|| conn.isClosed())
            {

                try {
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/POS", "root", "");
                    if (conn != null) {
                        System.out.println("Db connected");
                    } else {
                        System.out.println("Db not connected");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } catch (SQLException e) {
            System.out.println("Exception Caught in Connection Config:"+e.getMessage());
        }


        return conn;
    }
    }

