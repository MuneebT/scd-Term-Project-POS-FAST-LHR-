package Connection;

import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionConfigurator  {
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

    public static boolean isInternetConnected() {
        try {
            // Define a reliable URL to check for internet connection (Google's public URL)
            URL url = new URL("http://www.google.com");

            // Open a connection to the URL
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();

            // Set a timeout of 3000 milliseconds (3 seconds)
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(3000);

            // Attempt to connect (returns HTTP_OK if connection is successful)
            urlConnection.connect();

            // If successful, return true
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {
                return true;
            }
        } catch (Exception e) {
            // If an error occurs (e.g., no connection), return false
            e.printStackTrace();
        }
        return false;
    }


}

