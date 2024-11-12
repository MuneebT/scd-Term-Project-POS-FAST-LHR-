package Connection;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionConfigurator {

    public static Connection getConnection() {
        Connection conn = null;
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

        return conn;
    }
    }

