import java.sql.Connection;
import java.sql.DriverManager;

public class BranchManagementCode {

    public static Connection getConnection() {
        Connection conn = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/branch", "root", "");
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
